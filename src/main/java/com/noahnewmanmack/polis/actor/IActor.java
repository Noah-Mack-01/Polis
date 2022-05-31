package com.noahnewmanmack.polis.actor;

import com.noahnewmanmack.polis.actor.faction.IFaction;
import com.noahnewmanmack.polis.enums.Autonomy;
import java.util.UUID;


public interface IActor {

  /**
   * Transfers monetary value from one party to another.
   * @param amt amount being transferred; negative if receiving.
   * @param counterparty counterparty receiving or giving money.
   * @return current monetary balance of treasury.
   * @throws IllegalArgumentException if counterparty doesn't exist and amt != 0.
   */
  double transfer(double amt, IActor counterparty) throws IllegalArgumentException;

  /** Only if non-zero sum. See above. */
  double receive(double amt);

  /**
   * Actor swears fealty to another actor.
   * @param lord the organization being sworn to.
   * @param autonomy the level of autonomy being sworn in at.
   * @throws IllegalArgumentException if lord parameter is a character or DNE; must be a group.
   */
  void swearFealty(IFaction lord, Autonomy autonomy) throws IllegalArgumentException;

  void swearFealty(IFaction lord) throws IllegalArgumentException;

  /**
   * Returns whether a given actor is sovereign of the object actor.
   * @param faction who may or may not be subordinate.
   * @return true if parameter actor is subordinate of object actor.
   * @throws IllegalArgumentException if faction DNE.
   */
  boolean isLesser(IFaction faction) throws IllegalArgumentException;

  /**
   * Returns the highest authority which the actor answers to, whatever that faction is.
   * @return the sovereign authority controlling the actor.
   */
  IActor topOfChain();

  /**
   * Returns whether the given lord is the direct leader of the actor.
   * @param lord the given lord.
   * @return true if it is the leader of the actor.
   */
  boolean isDirectLesser(IFaction lord);

  /**
   * Returns whether the given actor answers to the same direct leader as an actor.
   * @param actor given opposing actor.
   * @return true if they have the same leader.
   */
  boolean isPeer(IActor actor);

  UUID getID();
  String getName() throws IllegalArgumentException;
  String getDesc() throws IllegalArgumentException;

}
