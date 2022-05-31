package com.noahnewmanmack.polis.enums;

/**
 * Types of diplomatic agreements to be held within the server between factions.<br>
 * <b>Military Alliance: </b> Agreed offensive and defensive obligation for two factions' wars.<br>
 * <b>Defensive Alliance: </b> Agreed defensive obligation for two factions' wars.<br>
 * <b>Guarantee: </b> One way defensive guarantee.<br>
 * <b>Subsidies: </b> One way monetary payment from one faction to another.<br>
 * <b>Tributary: </b> Monetary Exchange for security.<br>
 * <b>Warn: </b> One way prevention of offensive conflict. If warned party declares, warning party joins on other side. <br>
 */
public enum DiplomaticRelationship {
  MILITARY_ALLIANCE, DEFENSIVE_ALLIANCE, GUARANTEE, SUBSIDIES, TRIBUTARY, WARN
}
