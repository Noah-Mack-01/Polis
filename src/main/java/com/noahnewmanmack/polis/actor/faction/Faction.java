package com.noahnewmanmack.polis.actor.faction;

import com.noahnewmanmack.polis.actor.AbstractActor;
import com.noahnewmanmack.polis.actor.IActor;
import com.noahnewmanmack.polis.actor.character.ICharacter;
import com.noahnewmanmack.polis.enums.Autonomy;
import com.noahnewmanmack.polis.enums.DiplomaticRelationship;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;


public class Faction extends AbstractActor implements IFaction {

  private Map<IActor, Autonomy> subordinates;
  private IFaction feeder;
  private Map<IFaction, DiplomaticRelationship> diplomacy;
  private List<IFaction> rivals;

  public Faction(UUID uuid, String name, String desc) {
    super(uuid, name, desc);
    this.subordinates = new HashMap<>();
    this.rivals = new ArrayList<>();
  }

  @Override
  public IFaction insertSubordinate(IActor subordinate, Autonomy autonomy) {
    if (subordinate == null) { throw new IllegalArgumentException("Invalid Actor!"); }
    if (this.feeder == null && subordinate instanceof IFaction) {
      consolidate();
      this.subordinates.put(subordinate, autonomy);
      return this;
    }
    else if (this.feeder != null && subordinate instanceof ICharacter) {
      this.feeder.insertSubordinate(subordinate, autonomy);
      return this.feeder;
    }
    else {
      this.subordinates.put(subordinate, autonomy);
      return this;
    }
  }

  @Override
  public Autonomy removeSubordinates(IActor subordinate) {
    return this.subordinates.remove(subordinate);
  }

  @Override
  public Double taxIncome(double transaction, IActor subordinate) {
    return transaction * this.subordinates.get(subordinate).tax_rate;
  }

  @Override
  public IFaction consolidate() throws IllegalArgumentException {

    Faction unaffiliated = new Faction(UUID.randomUUID(), "Citizens of " + this.name, "");
    for (IActor a: subordinates.keySet()) {
      // change loyalty of all of the subjects to this fake subfaction.
      a.swearFealty(unaffiliated, subordinates.get(a));
    }

    this.subordinates.put(unaffiliated, Autonomy.COMPLETE_CONTROL);
    this.feeder = unaffiliated;
    this.feeder.swearFealty(this,Autonomy.COMPLETE_CONTROL);
    return this.feeder;

    // steps:
    // take all actors, make a default class.

  }

  @Override
  public DiplomaticRelationship insertDiplomacy(IFaction partner, DiplomaticRelationship relationship) {
    return this.diplomacy.put(partner, relationship);
  }

  @Override
  public DiplomaticRelationship removeDiplomacy(IFaction partner) {
    return this.diplomacy.remove(partner);
  }


  @Override
  public Boolean Rival(IFaction rival) throws IllegalArgumentException {
    if (rival == null) { throw new IllegalArgumentException("Null faction cannot be rival!"); }
    if (this.rivals.contains(rival)) { this.rivals.remove(rival); }
    else if (this.rivals.size() < 3) { this.rivals.add(rival); }
    else { throw new IllegalArgumentException("Too Many Rivals! Remove one before adding."); }
    return true;
  }

  @Override
  public void declareWar(IFaction enemy) {
    // check for enemy being a lesser member of its hierarchy.
    // check for enemy diplomatic relationships.
    // construct war, add it to the list of items.
  }

  @Override
  public IFaction disband() {
    if (this.lord == null) {
      for (IActor a : subordinates.keySet()) { a.swearFealty(null); }
    }
    else {
      for (IActor a : subordinates.keySet()) {
        a.swearFealty(this.lord);
      }
    }
    return this.lord;
  }

  @Override
  public Collection<UUID> getSubordinateIDs() {
    Collection<UUID> output = new ArrayList<>();
    for (IActor actor : subordinates.keySet()) {
      output.add(actor.getID());
    }
    return output;
  }


}
