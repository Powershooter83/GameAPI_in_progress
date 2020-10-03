package me.prouge.gameApi.commands;

import me.prouge.gameApi.gameManagement.GameManager;
import me.prouge.gameApi.Main;
import me.prouge.gameApi.gameManagement.Team;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;

public class TestCMD implements CommandExecutor {

    private Main main;
    public TestCMD(Main main){
        this.main = main;
    }





    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        GameManager gameManager = main.getGameManager();
        if(sender instanceof Player){
            Player p = (Player) sender;
            if(args.length == 2){
                if(args[0].equalsIgnoreCase("create")){
                    gameManager.arenaHashMap.put(args[1], new GameManager(args[0], new ArrayList<Player>(),
                            new HashMap<String, Location>(), null));
                    System.out.println("Created");
                    if(gameManager.arenaHashMap.isEmpty()){
                        System.out.println("ERRORß");
                    }
                }
            }
            if(args.length ==2){
                String arena = args[0];
                if(gameManager.arenaHashMap.get(arena) != null){
                    if(gameManager.teamHashMap.get(arena) == null){
                        gameManager.teamHashMap.put(arena, new ArrayList<Team>());
                        Team t = new Team(args[1], "Blau", new ArrayList<Player>()
                                ,new HashMap<String, Location>());
                        t.locations.put("Spawn", ((Player) sender).getLocation());
                        gameManager.teamHashMap.get(arena).add(t);
                    }else{
                        Team t = new Team(args[1], "Blau", new ArrayList<Player>()
                                ,new HashMap<String, Location>());
                        t.locations.put("Spawn", ((Player) sender).getLocation());
                        gameManager.teamHashMap.get(arena).add(t);

                    }
                }
            }

        }

        return false;
    }
}
