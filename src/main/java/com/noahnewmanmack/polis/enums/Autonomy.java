package com.noahnewmanmack.polis.enums;

/**
 * The given level of autonomy a sub-faction has within its overarching sovereign faction.<br>
 * <b>In Name Only: </b> Lord has only formal rule over subject. 5% tax; cannot revoke property.
 * <b>High Autonomy:</b> Lord cannot revoke land or remove and appoint leadership without cause or consent.
 * May declare internal warfare with other high autonomy subjects. 10% tax.<br>
 * <b>Medium Autonomy:</b> Lord cannot revoke land or remove and appoint leadership without cause or consent. 30% tax.<br>
 * <b>Low Autonomy:</b> Lord cannot remove or appoint leadership without cause or consent. 50% tax.<br>
 * <b>Complete Control:</b> Lord can do whatever they please with subject without cause or consent. 95% tax. <br>
 */
public enum Autonomy {


  // signifies the level of autonomy a sub-faction has in the overarching faction.
  IN_NAME_ONLY(0), VERY_HIGH_AUTONOMY(.1), HIGH_AUTONOMY(.15),
  MEDIUM_AUTONOMY(.2), LOW_AUTONOMY(.25), VERY_LOW_AUTONOMY(.3),
  CONTROLLING(.5), COMPLETE_CONTROL(1);

  /*
   autonomy effects the following:
   1. Ability to appoint leadership with or without consent.
   2. Ability to effect taxation with or without consent.
   3. Ability to summon for conflict with or without consent.
   4. Ability to revoke property, with or without consent.
   */

  public double tax_rate;
  Autonomy(double rate) {
    this.tax_rate = rate;
  }

}
