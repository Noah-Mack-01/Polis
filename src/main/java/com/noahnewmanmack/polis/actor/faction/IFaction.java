package com.noahnewmanmack.polis.actor.faction;

import com.noahnewmanmack.polis.actor.AbstractActor;
import com.noahnewmanmack.polis.actor.IActor;
import com.noahnewmanmack.polis.enums.Autonomy;
import com.noahnewmanmack.polis.enums.DiplomaticRelationship;
import java.util.Collection;
import java.util.UUID;

public interface IFaction extends IActor {

/*
 Current Functionality
   - can add or detract from a list of subordinates and assign a level of autonomy to them.
   - can consolidate all subordinates into an unaffiliated class to transform the faction's subordinates from characters to subfactions
   - can
 */


  /**
   * Adds a subordinate to the map of IActors.
   * @param subordinate the subject of the actor.
   * @param autonomy the level of autonomy a subject has.
   * @return the prior autonomy value being overwritten, or null.
   */
  IFaction insertSubordinate(IActor subordinate, Autonomy autonomy);


  /**
   * Extends HashMap functionality for the list of IActor subordinates.
   * @param subordinate the IActor key being removed.
   * @return the overwritten value of the removed key or null if DNE.
   */
  Autonomy removeSubordinates(IActor subordinate);

  Double taxIncome(double transaction, IActor subordinate);

  //TODO: figure out ActorBuilder<T Extends ActorAbstract>
  IFaction consolidate();

  /**
   * Establishes or modifies a diplomatic relationship between two actors.
   * @param partner diplomatic partner.
   * @param relationship relationship type being formed.
   * @throws IllegalArgumentException when trying to remove a relationship or actor that DNE.
   */
  DiplomaticRelationship insertDiplomacy(IFaction partner, DiplomaticRelationship relationship);


  /**
   * removes a relationship from the list of relationships that the faction has.
   * @param partner the partner whose relationship we're removing.
   * @return relationship that was removed.
   */
  DiplomaticRelationship removeDiplomacy(IFaction partner);

  IFaction disband();

  Boolean Rival(IFaction rival);

  void declareWar(IFaction enemy);

  Collection<UUID> getSubordinateIDs();

}
