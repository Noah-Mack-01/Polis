package com.noahnewmanmack.polis.utils;


import com.noahnewmanmack.polis.Polis;
import com.noahnewmanmack.polis.actor.IActor;
import com.noahnewmanmack.polis.actor.character.Character;
import com.noahnewmanmack.polis.actor.character.ICharacter;
import com.noahnewmanmack.polis.actor.faction.Faction;
import com.noahnewmanmack.polis.actor.faction.IFaction;
import com.noahnewmanmack.polis.enums.Autonomy;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * <b></b>CRUD management for the Faction & Characters of a Server. <br>
 * <b>Method:</b> createFaction - builds a faction using the contructor by generating a compliant uuid. <br>
 * <b></b>
 */
public class StorageUtils {

  private static String activePath;
  private static ArrayList<IFaction> factionList = new ArrayList<>();
  private static ArrayList<ICharacter> characters = new ArrayList<>();
  private static Map<UUID, IActor> usedIDs = new TreeMap<>();
  private static String contents;


  /*
   * JSON STRUCTURE:
   * {
   * "Factions" : [{
   *    "UUID" : 10203, "Name" : "XXX", "Desc" : "Blah", "Lord" : 10222,
   *    "Subordinates":[{"UUID": 1022, "Autonomy":"H"}, {}, {}], "Balance" : 555
   *  },{},{}],
   * Characters : [{
   *    "UUID" : 10203, "Name" : "XXX", "Desc" : "Blah", "Lord" : 10203,
   *    "Balance" : 40,
   * },{},{}]}
   */

  public StorageUtils(File file) {
    try {
      contents = new String(Files.readAllBytes(file.toPath()));
    } catch (IOException e) { e.printStackTrace(); }
  }

  public static void load() {
    // Total json as loaded on startup.
    JSONObject gross_set = new JSONObject(contents);

    // from this, we'd like to get ever unique ID we can and make it a registered actor.
    // Let's start with getting our factions.
    JSONArray factions = gross_set.getJSONArray("Factions");

    JSONArray characters = gross_set.getJSONArray("Characters");
    for (int i = 0; i < characters.length(); i++) {
      JSONObject character = characters.getJSONObject(i);
      createCharacter(UUID.fromString(character.getString("UUID")),
          character.getString("Name"), character.getString("Desc"),
          character.getDouble("Balance"), character.getDouble("Treasury"));
    }

    for (int i = 0; i < factions.length(); i++) {
      JSONObject faction = factions.getJSONObject(i);
      createFaction(
          UUID.fromString(faction.getString("UUID")),
          faction.getString("Name"), faction.getString("Desc"),
          faction.getDouble("Treasury"));
    }

    for (int i = 0; i < factions.length(); i++) {
      JSONObject faction = factions.getJSONObject(i);
      UUID uuid = UUID.fromString(faction.getString("UUID")); // Faction UUID
      JSONArray subs = faction.getJSONArray("Subordinates"); // subordinate UUIDs
      populateSubordinates(uuid, subs);
    }
  }

  public static void build() {

    // build


  }

  public static IFaction createFaction(UUID uuid, String name,
      String description, double treasury) {
    IFaction f = new Faction(uuid, name, description);
    f.receive(treasury);
    factionList.add(f);
    usedIDs.put(uuid,f);
    return f;
  }

  public static ICharacter createCharacter(UUID uuid, String name,
      String description, double balance, double treasury) {
    ICharacter c = new Character(uuid, name, description);
    c.receive(treasury + balance);
    c.transact(balance);
    characters.add(c);
    usedIDs.put(uuid,c);
    return c;
  }

  // can update by finding an actor or faction and preparing the appropriate updates to it.

  /**
   * After consolidb
   * @param faction
   * @param subordinates
   */
  private static void populateSubordinates(UUID faction, JSONArray subordinates) {
    IFaction fac = (IFaction) findActor(faction);
    for (int i = 0; i < subordinates.length(); i++) {
      JSONObject index = subordinates.getJSONObject(i); // {"UUID":"XXXX", "Autonomy":"H"}
      UUID sub = UUID.fromString(index.getString("UUID"));
      Autonomy auto;
      switch(index.getString("Autonomy")) {
        case("INO"):
          auto = Autonomy.IN_NAME_ONLY;
        case("VH"):
          auto = Autonomy.VERY_HIGH_AUTONOMY;
          break;
        case("H"):
          auto = Autonomy.HIGH_AUTONOMY;
          break;
        case("M"):
          auto = Autonomy.MEDIUM_AUTONOMY;
          break;
        case("L"):
          auto = Autonomy.LOW_AUTONOMY;
          break;
        case("VL"):
          auto = Autonomy.VERY_LOW_AUTONOMY;
          break;
        case("C"):
          auto = Autonomy.CONTROLLING;
          break;
        default:
          auto = Autonomy.COMPLETE_CONTROL;
          break;
        }
      usedIDs.get(sub).swearFealty(fac, auto);
    }
  }

  public static IFaction findFaction(UUID uuid) {
    IActor a = findActor(uuid);
    if (a instanceof Faction) { return (IFaction) a; }
    return null;
  }

  public static ICharacter findCharacter(UUID uuid) {
    IActor a = findActor(uuid);
    if (a instanceof Character) { return (ICharacter) a; }
    return null;
  }

  public static boolean deleteCharacter(UUID character) {
    return characters.remove(deleteActor(character));
  }

  public static boolean deleteFaction(UUID faction) {
    IFaction f =(IFaction) deleteActor(faction);
    return factionList.remove(f);
  }

  private static IActor findActor(UUID uuid) {
    return usedIDs.get(uuid);
  }

  private static IActor deleteActor(UUID uuid) {
    return usedIDs.remove(uuid);
  }





}
