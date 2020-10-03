package me.prouge.gameApi.gameManagement;

import org.bukkit.Location;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;

public class GameManager {

    ArrayList<Player> playersArray;
    HashMap<String, Location> gameLocations;
    String name;
    Sign sign;


    public HashMap<String, GameManager> arenaHashMap = new HashMap<>();

    public HashMap<String, ArrayList<Team>> teamHashMap = new HashMap<>();

    public GameManager(String name, ArrayList<Player> playersArray,
                       HashMap<String, Location> gameLocations, Sign sign){
        this.name = name;
        this.playersArray = playersArray;
        this.gameLocations = gameLocations;
        this.sign = sign;

    }

    //=========================================================================================================
    //Sign methods
    //=========================================================================================================

    public Sign getSign(){
        return this.getSign();
    }




    //=========================================================================================================
    //Player Team methods
    //=========================================================================================================

    public Team getPlayersTeam(Player player){
        for(String arena : teamHashMap.keySet()){
            for(Team t : teamHashMap.get(arena)){
                if(t.getPlayersInTeam().contains(player)){
                    return t;
                }
            }

        }
        return null;

    }



    public void addTeam(Team team){
        if(teamHashMap.get(this.name) == null){
            teamHashMap.put(this.name, new ArrayList<Team>());
            teamHashMap.get(this.name).add(team);
        }else{
            teamHashMap.get(this.name).add(team);
        }
    }



    //=========================================================================================================
    //Location methods
    //=========================================================================================================

    public Location getLocation(String name){
        return this.gameLocations.get(name);
    }


    //=========================================================================================================
    //External methods
    //=========================================================================================================

    public GameManager findArenaFromPlayer(Player player){
        for(String arena : arenaHashMap.keySet()){
            GameManager gameManager = arenaHashMap.get(arena);
            if(gameManager.checkPlayer(player)){
                return gameManager;
            }
        }return null;


    }


    //=========================================================================================================
    //Player methods
    //=========================================================================================================
    public boolean checkPlayer(Player player){
        return this.playersArray.contains(player);
    }


    public void addPlayer(Player player){
        if(!this.playersArray.contains(player)) {
            this.playersArray.add(player);
        }
    }

    public void removePlayer(Player player){
        this.playersArray.remove(player);
    }


}
