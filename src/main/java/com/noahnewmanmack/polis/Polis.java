package com.noahnewmanmack.polis;


import com.noahnewmanmack.polis.actor.character.ICharacter;
import com.noahnewmanmack.polis.actor.faction.IFaction;
import com.noahnewmanmack.polis.utils.StorageUtils;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeMap;
import org.bukkit.plugin.java.JavaPlugin;


public final class Polis extends JavaPlugin {

  @Override
  public void onEnable() {
    File actorFile = new File(this.getDataFolder().getAbsolutePath().concat("/actors.json"));

    try {
      if (actorFile.createNewFile()) {
        this.getServer().getConsoleSender().sendMessage("Successfully created new Actors actorFile!");
      } else { this.getServer().getConsoleSender().sendMessage("Importing Existing Actors.json"); }
    } catch (IOException e) {
      this.getServer().getConsoleSender().sendMessage("Input/Output Error!");
      e.printStackTrace();
    }

    
    StorageUtils storageUtils = new StorageUtils(actorFile);
  }

  @Override
  public void onDisable() {
    // Plugin shutdown logic
  }


}
