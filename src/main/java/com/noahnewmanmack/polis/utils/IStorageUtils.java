package com.noahnewmanmack.polis.utils;

import com.noahnewmanmack.polis.actor.character.ICharacter;
import com.noahnewmanmack.polis.actor.faction.IFaction;
import java.util.UUID;

public interface IStorageUtils {

  void loadFromFile();
  void saveToFile();

  boolean createFaction(UUID uuid, String name, String desc, int balance) throws IllegalArgumentException;
  boolean createCharacter(UUID uuid, String name, String desc, int balance, int pocket) throws IllegalArgumentException;
  boolean deleteFaction(UUID uuid) throws IllegalArgumentException;
  boolean deleteCharacter(UUID uuid) throws IllegalArgumentException;

  IFaction getFaction(UUID uuid);
  ICharacter getCharacter(UUID uuid);




}
