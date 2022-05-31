package com.noahnewmanmack.polis.actor.character;

import com.noahnewmanmack.polis.actor.IActor;


public interface ICharacter extends IActor {

  /*
  Current functionality:
    A character has all functionality of an actor.
    Character has its own functional pocket.
      can take from other accounts and put money into his pocket
      can transfer money from his pocket to another pocket.
      can receive money from ambiguous source.
   */

  /*
  Desired Functionality
   */

  /**
   * Pulls from and deposits to a bank account.
   * @param amount amount to pull out from the treasury.
   * @return balance remaining in pocket.
   * @throws IllegalArgumentException if either pocket or treasury is overdrawn.
   */
  double transact(double amount) throws IllegalArgumentException;
  /**
   * Transfer an amount of money from one character to another.
   * @param amount the amount being transferred.
   * @param character the character being transferred to.
   * @return balance of pocket.
   * @throws IllegalArgumentException if character doesn't exist, or amount is negative, or if overdrawn.
   */
  int pocketToPocket(int amount, ICharacter character) throws IllegalArgumentException;

  /**
   * Adds or subtracts a defined amount to characters pocket.
   * @param amount money being added or withdrawn.
   * @return current pocket change.
   * @throws IllegalArgumentException on overdraw/
   */
  int addToPocket(int amount) throws IllegalArgumentException;




}
