package com.noahnewmanmack.polis;



import com.noahnewmanmack.polis.utils.StorageUtils;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.bukkit.plugin.java.JavaPlugin;


public final class Polis extends JavaPlugin {

  File actorFile = new File(this.getDataFolder().getAbsolutePath().concat("/actors.json"));

  @Override
  public void onEnable() {
    StorageUtils.loadFileFromPlugin(actorFile);
    try {
      if (actorFile.createNewFile()) {
        this.getServer().getConsoleSender().sendMessage("Successfully created new Actors actorFile!");
      } else { this.getServer().getConsoleSender().sendMessage("Importing Existing Actors.json"); }
    } catch (IOException e) {
      this.getServer().getConsoleSender().sendMessage("Input/Output Error!");
      e.printStackTrace();
    }
  }

  @Override
  public void onDisable() {

    // WRITING TO ACTOR JSON FILE ON SHUTDOWN.
    try {
      new FileWriter(actorFile).append(StorageUtils.build().toString());
    }
    catch (IOException e) {
      this.getServer().getConsoleSender().sendMessage("Input/Output Error!");
      e.printStackTrace();
    }




  }


}
