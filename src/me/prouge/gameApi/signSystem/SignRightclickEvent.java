package me.prouge.gameApi.signSystem;

import me.prouge.gameApi.gameManagement.GameManager;
import me.prouge.gameApi.Main;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;


public class SignRightclickEvent implements Listener {
    private Main main;
    public SignRightclickEvent(Main main){
        this.main = main;
    }
    @EventHandler
    public void onPlayerClickSign(PlayerInteractEvent event){
        Player p = event.getPlayer();
        if(event.getClickedBlock().getType() == Material.SIGN ||event.getClickedBlock().getType() == Material.SIGN_POST ||event.getClickedBlock().getType() == Material.WALL_SIGN){
            if(event.getAction() == Action.RIGHT_CLICK_BLOCK){
                Sign sign = (Sign) event.getClickedBlock().getState();
                for(String arena : main.getGameManager().arenaHashMap.keySet()){
                    GameManager gameManager = main.getGameManager().arenaHashMap.get(arena);
                    if(gameManager.getSign() == sign){
                        //UPDATESIGN


                    }

                }


            }
        }
    }


}
