package com.noahnewmanmack.polis.actor;

import com.noahnewmanmack.polis.actor.faction.IFaction;
import com.noahnewmanmack.polis.enums.Autonomy;
import java.util.UUID;

public abstract class AbstractActor implements IActor {

  protected UUID id;
  protected String name, desc;
  protected IFaction lord;
  protected int bankBalance;

  public AbstractActor(UUID uuid,String name, String desc) {
    this.name = name;
    this.desc = desc;
    this.bankBalance = 0;
    this.id = UUID.randomUUID() ;
  }

  @Override
  public double transfer(double amt, IActor counterparty) throws IllegalArgumentException {

    // check for valid amount
    if (amt < 0) {
      throw new IllegalArgumentException("Cannot transfer negative money.");
    }

    // check for valid actor
    if (counterparty == null) {
      throw new IllegalArgumentException("Cannot transfer to null counterparty.");
    }

    // check for overdraw
    if (amt > this.bankBalance) {
      throw new IllegalArgumentException("Cannot Overdraw! Overdrawing by: "
          + (amt - this.bankBalance));
    }

    this.receive(-amt);
    counterparty.receive(amt);
    return this.bankBalance;
  }

  @Override
  public double receive(double amt) throws IllegalArgumentException {
    if (this.bankBalance < -amt) { throw new IllegalArgumentException("Overdraw!"); }

    double returnAmt = amt;

    if (amt > 0 && this.lord != null) {
      double taxed = this.lord.taxIncome(returnAmt, this);
      System.out.println("Taxed Amount: " + taxed);
      returnAmt -= taxed;
      this.lord.receive(taxed);
    }

    return this.bankBalance += returnAmt;
  }

  @Override
  public void swearFealty(IFaction lord, Autonomy autonomy) {
    if (this.lord != null) { this.lord.removeSubordinates(this); }
    if (lord == null) { this.lord = null; }
    else {
      this.lord = lord.insertSubordinate(this,autonomy);
      // this is bad. We need to figure out how to pass it the variable we need.
    }
  }

  @Override
  public void swearFealty(IFaction lord) {
    this.swearFealty(lord, Autonomy.HIGH_AUTONOMY);
  }


  @Override
  public boolean isLesser(IFaction faction) throws IllegalArgumentException {
    if (faction == null) { return false; }
    return faction.equals(this.lord) || this.lord.isLesser(faction);
  }


  @Override
  public IActor topOfChain() {
    if (this.lord == null) {
      return this;
    } else { return this.lord.topOfChain(); }
  }

  @Override
  public boolean isDirectLesser(IFaction lord) {
    return (lord.equals(this.lord));
  }

  @Override
  public boolean isPeer(IActor actor) {
    return (actor.isDirectLesser(this.lord));
  }

}
