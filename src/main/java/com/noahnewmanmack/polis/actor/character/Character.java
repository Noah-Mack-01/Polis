package com.noahnewmanmack.polis.actor.character;

import com.noahnewmanmack.polis.actor.AbstractActor;
import java.util.UUID;


public class Character extends AbstractActor implements ICharacter {

  // Character should be forced to a default bank template starting at Central Hub
  int pocketChange;

  public Character(UUID uuid, String name, String desc) {
    super(uuid, name, desc);
    this.pocketChange = 0;
    //TODO: Custom treasury linked to central spawn bank.
    // Should have low interest rates or high tariffs to motivate players to find other place to put money.
  }

  @Override
  public double transact(double amount) throws IllegalArgumentException {
    if (amount > this.bankBalance) {
      throw new IllegalArgumentException("Overdrawn by: " + (amount - this.bankBalance));
    }
    this.pocketChange += amount;
    this.bankBalance -= amount;
    return this.pocketChange;
  }

  @Override
  public int pocketToPocket(int amount, ICharacter character) throws IllegalArgumentException {
    if (amount > this.pocketChange) {
      throw new IllegalArgumentException("Overdrawn by: " + (amount - this.pocketChange));
    }

    if (character == null) { throw new IllegalArgumentException("Invalid Counterparty!"); }

    else {
      this.pocketChange -= amount;
      character.addToPocket(amount);
    }

    return this.pocketChange;
  }

  @Override
  public int addToPocket(int amount) throws IllegalArgumentException {
    if (-1 * amount > this.pocketChange) {
      throw new IllegalArgumentException("Overdrawn by: " + (amount - this.pocketChange));
    }
    else {
      this.pocketChange += amount;
    }
    return this.pocketChange;
  }
}
