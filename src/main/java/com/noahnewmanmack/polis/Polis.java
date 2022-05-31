package com.noahnewmanmack.polis;


import com.noahnewmanmack.polis.actor.character.ICharacter;
import com.noahnewmanmack.polis.actor.faction.IFaction;
import com.noahnewmanmack.polis.utils.StorageUtils;
import java.util.ArrayList;
import java.util.TreeMap;
import org.bukkit.plugin.java.JavaPlugin;


public final class Polis extends JavaPlugin {

  @Override
  public void onEnable() {
    StorageUtils storageUtils = new StorageUtils(this);
  }

  @Override
  public void onDisable() {
    // Plugin shutdown logic
  }


}
