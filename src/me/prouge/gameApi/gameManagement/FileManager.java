package me.prouge.gameApi.gameManagement;


import me.prouge.gameApi.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class FileManager {
    private Main main;
    public FileManager(Main main){
        this.main = main;
    }

    public void loadArena(){
        File file = getFile();
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        for(String arena : config.getConfigurationSection("").getKeys(false)){
            Bukkit.getConsoleSender().sendMessage("§cBedwars §7» Die Arena §8[§c" + arena + "§8] §7wird geladen...");
            main.getGameManager().arenaHashMap.put(arena, new GameManager(arena, new ArrayList<Player>(), new HashMap<String, Location>(), null));
            if(config.getConfigurationSection(arena + ".gameLocations") != null){
                for(String locName: config.getConfigurationSection(arena + ".gameLocations").getKeys(false)) {
                    Location loc = (Location) config.get(arena + ".gameLocations" + "." + locName);
                    main.getGameManager().arenaHashMap.get(arena).gameLocations.put(locName, loc);
                }
            }
            main.getGameManager().teamHashMap.put(arena, new ArrayList<Team>());
            if(config.get(arena + ".team") != null){
                for(String team : config.getConfigurationSection(arena + ".team").getKeys(false)){
                    String prefix = (String) config.get(arena + ".team" + "." + team + ".prefix");
                    Team t = new Team(team, prefix,
                            new ArrayList<Player>(),
                            new HashMap<String, Location>());

                    for(String locName: config.getConfigurationSection(arena + ".team" + "." + team + ".locations").getKeys(false)) {
                        Location loc = (Location) config.get(arena + ".team" + "." + team + ".locations" + "." + locName);
                        t.locations.put(locName, loc);

                    }
                    main.getGameManager().teamHashMap.get(arena).add(t);
                    Bukkit.getConsoleSender().sendMessage("§cBedwars §7» ➥ Das Team §c" + t.name + " §7wurde erfolgreich geladen...");

                }
            }
            if(main.getGameManager().arenaHashMap.get(arena) != null){
                Bukkit.getConsoleSender().sendMessage("§cBedwars §7» Die Arena §8[§c" + arena + "§8] §7wurde erfolgreich geladen...");

            }

          }

        




    }

    public File getFile(){
        if(!main.getDataFolder().exists()){
            main.getDataFolder().mkdirs();
        }
        File file = new File(main.getDataFolder(), "arenas.yml");
        if(!file.exists()){
            try{
                file.createNewFile();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        return file;
    }

    public void removeArena(String arena, GameManager gameManager) {
        File file = getFile();
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        for (String arenaName : config.getConfigurationSection("").getKeys(false)) {
            if (arenaName.equals(arena)) {
                config.getConfigurationSection(arena).set(null, null);
                main.getGameManager().teamHashMap.remove(gameManager.name);
                main.getGameManager().arenaHashMap.remove(gameManager);

            }

        }
    }


    public void saveArena(){
        File file = getFile();

        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        GameManager gameManager = main.getGameManager();
        HashMap<String, GameManager> arenaHashMap = gameManager.arenaHashMap;
        HashMap<String, ArrayList<Team>> teamHashMap = gameManager.teamHashMap;

        for(String arena : arenaHashMap.keySet()){
            Bukkit.getConsoleSender().sendMessage("§cBedwars §7» Die Arena §8[§c" + arena + "§8] §7wurde gespeichert...");
            if(config.get(arena)== null){
                config.set(arena, "Noch keine Werte!");
            }
            for(String locName : arenaHashMap.get(arena).gameLocations.keySet()){
                config.set(arena + ".gameLocations" + "." + locName, arenaHashMap.get(arena).gameLocations.get(locName));
            }

        }

        for(String arena : teamHashMap.keySet()){
            for(Team t : teamHashMap.get(arena)){
                config.set(arena + ".team" + "." + t.name + ".prefix", t.prefix);
                for(String locName : t.locations.keySet()){
                    config.set(arena + ".team" + "." + t.name + ".locations" + "." + locName,
                            t.locations.get(locName));


                }


                

            }
        }

        save(file, config);



    }

    public void save(File file, YamlConfiguration config){
        try{
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
