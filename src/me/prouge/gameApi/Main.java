package me.prouge.gameApi;

import me.prouge.gameApi.commands.SetupCMD;
import me.prouge.gameApi.gameManagement.FileManager;
import me.prouge.gameApi.gameManagement.GameManager;
import me.prouge.gameApi.listeners.SetupEvents;
import me.prouge.gameApi.signSystem.SignRightclickEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private Main main;
    private GameManager gameManager;
    private FileManager fileManager;
    private SignRightclickEvent signRightclickEvent;
    private SetupCMD setupCMD;

    public void onEnable(){
        main = this;
        gameManager = new GameManager(null,null,null, null);
        fileManager = new FileManager(main);
        fileManager.loadArena();
        setupCMD = new SetupCMD(main);

        registerEvents();
        getCommand("bw").setExecutor(new SetupCMD(main));

    }


    public void registerEvents(){
        getServer().getPluginManager().registerEvents(new SignRightclickEvent(main), this);
        getServer().getPluginManager().registerEvents(new SetupEvents(main), this);
    }


    public void onDisable(){
        fileManager.saveArena();
    }


    public SetupCMD getSetupCMD(){
        return setupCMD;
    }
    public GameManager getGameManager() {
        return gameManager;
    }
    public FileManager getFileManager() {
        return fileManager;
    }
}
