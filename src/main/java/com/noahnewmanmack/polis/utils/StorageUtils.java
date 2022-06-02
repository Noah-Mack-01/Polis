package com.noahnewmanmack.polis.utils;

import com.noahnewmanmack.polis.actor.character.Character;
import com.noahnewmanmack.polis.actor.character.ICharacter;
import com.noahnewmanmack.polis.actor.faction.Faction;
import com.noahnewmanmack.polis.actor.faction.IFaction;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Paths;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class StorageUtils {

  private static List<IFaction> factionList;
  private static List<ICharacter> charactersList;
  private static JSONObject storageJSON;


  /**
   * Loads the JSON from a given filepath filled with factions and characters.
   * @param filePath the filepath containing our data.
   * @throws IOException if we cannot either create a new file despite one not being found.
   */
  public static void loadFromFile(String filePath) throws IOException {
    factionList = new ArrayList<>();
    charactersList = new ArrayList<>();
    String contents;
    File file = new File(filePath);

    try {
      contents = Files.readString(Paths.get(filePath));
      System.out.println("Contents: " + contents);
      if (contents.contains("{")) {storageJSON = new JSONObject(contents);}
      else { storageJSON = new JSONObject(); }
    }

    catch (FileNotFoundException e) {
      if (!file.createNewFile()) { throw new IOException("Cannot create new file!"); }
      storageJSON = new JSONObject();
    }

    build();

  }

  private static void build() {
    JSONArray factionArray = storageJSON.getJSONArray("Factions");

    // "Factions" : [{"UUID": "","Name" : "",..."Balance":XXX,
    //     "Subordinates" : ["UUID1", "UUID2", "UUID3"]}, {Faction2}]

    for (int i = 0; i < factionArray.length(); i++) {
      JSONObject obj = factionArray.getJSONObject(i); // faction object.
      UUID uuid = UUID.fromString(obj.getString("UUID"));
      String name = obj.getString("Name"), desc = obj.getString("Description");
      double balance = obj.getDouble("Balance");
      createFaction(uuid,name,desc,balance);
    }


    JSONArray characterArray = storageJSON.getJSONArray("Characters");
    for (int i = 0; i < characterArray.length(); i++) {
      JSONObject obj = characterArray.getJSONObject(i); // faction object.
      UUID uuid = UUID.fromString(obj.getString("UUID"));
      String name = obj.getString("Name"), desc = obj.getString("Description");
      double balance = obj.getDouble("Balance");
      double pocket = obj.getDouble("Pocket");
      createFaction(uuid,name,desc,balance);
    }
  }

  public static void saveToFile() {

  }


  /**
   * Creates a faction to add to our list of existing factions. Loaded from JSON.
   * @param uuid the Unique ID of the faction.
   * @param name name of the faction.
   * @param desc description for the faction.
   * @param balance balance of the faction's treasury.
   * @return true if it was created, false if a faction already exists with its UUID.
   * @throws IllegalArgumentException if a negative balance is input.
   */
  public static boolean createFaction(UUID uuid, String name, String desc, double balance)
      throws IllegalArgumentException {

    if (balance < 0) { throw new IllegalArgumentException("No Negative Balance!"); }

    if (getFaction(uuid) == null) {
      IFaction faction = new Faction(uuid, name, desc);
      faction.receive(balance);
      factionList.add(faction);
      return true;
    } return false;
  }


  /**
   * Creates a character to add to our list of existing characters. Loaded from JSON.
   * @param uuid the Unique ID of the character
   * @param name name of the character
   * @param desc description for the character.
   * @param balance balance of the characters off-person account.
   * @param pocket the on-person account value of the person.
   * @return true if it was created, false if a faction already exists with its UUID.
   * @throws IllegalArgumentException if a negative balance or pocket is input.
   */
  public static boolean createCharacter(UUID uuid, String name, String desc, int balance, int pocket)
      throws IllegalArgumentException {

    if (pocket < 0 || balance < 0) { throw new IllegalArgumentException("No Negative Balance!"); }

    if (getCharacter(uuid) == null) {
      ICharacter character = new Character(uuid, name, desc);
      character.addToPocket(pocket);
      character.receive(balance);
      charactersList.add(character);
      return true;
    } return false;
  }

  public static boolean deleteFaction(UUID uuid) throws IllegalArgumentException {
    IFaction faction = getFaction(uuid);
    if (faction == null) { return false; }
    factionList.remove(faction);
    faction.disband();
    return true;
  }

  public static boolean deleteCharacter(UUID uuid) throws IllegalArgumentException {
    ICharacter character = getCharacter(uuid);
    if (character == null) { return false; }
    charactersList.remove(character);
    character.swearFealty(null);
    return true;
  }


  public static IFaction getFaction(UUID uuid) throws IllegalArgumentException {
    if (uuid == null) { throw new IllegalArgumentException("Invalid UUID"); }

    for (IFaction fac : factionList) {
      if (fac.getID().equals(uuid)) { return fac; }
    } return null;
  }

  public static ICharacter getCharacter(UUID uuid) throws IllegalArgumentException {
    if (uuid == null) { throw new IllegalArgumentException("Invalid UUID"); }

    for (ICharacter character : charactersList) {
      if (character.getID().equals(uuid)) { return character; }
    } return null;
  }
}
