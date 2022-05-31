package com.noahnewmanmack.polis.enums;

/**
 * Types of potential wars to be held on the server.<br>
 * <b>Conquest:</b> Take a certain number of properties; seize assets of any bank located on said property.<br>
 * <b>Subjugation:</b> Take over as sovereign with total control of a faction.<br>
 * <b>Pillage:</b> Take a percentage of a faction's wealth, relics, renown while not impacting actual territory or membership.<br>
 * <b>Succession:</b> Install a character as the leader of a target faction. Remove current leadership from faction.  <br>
 * <b>Rebellion:</b> Remove any sovereign a given faction has and any control it has over shared property.<br>
 * <b>Eradication:</b> Kill 20% of a faction's characters, render land uninhabitable for a period of time, and remove the faction from existence.
 */
public enum WarType {
  CONQUEST, SUBJUGATION, SUCCESSION, PILLAGE, REBELLION, LIBERTY, PEASANTS, ERADICATION
}
