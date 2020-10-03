package me.prouge.gameApi.listeners;

import me.prouge.gameApi.Main;
import me.prouge.gameApi.gameManagement.GameManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;

public class SetupEvents implements Listener {
    private Main main;
    public SetupEvents(Main main){
        this.main = main;
    }
    @EventHandler
    public void onInventoryRightclick(InventoryClickEvent event){
        if(event.getInventory().getTitle().equalsIgnoreCase("§c§lSetup")){
            event.setCancelled(true);
            if(event.getSlot() == 9){
                event.getWhoClicked().sendMessage("§cBedwars §7» Schreibe den Namen der Arena in den Chat!");
                playersInSetup.add((Player) event.getWhoClicked());
                event.getWhoClicked().closeInventory();
            }
            if(event.getSlot() == 10){
                main.getSetupCMD().openAllArenas((Player) event.getWhoClicked());
            }
        }
        if(event.getInventory().getTitle().equalsIgnoreCase("§c§lArenen")){
            event.setCancelled(true);
            if(event.getSlot() == 44){
                Player p = (Player) event.getWhoClicked();
                int invOpen = main.getSetupCMD().currentInv.get(p);
                invOpen++;
                p.closeInventory();
                p.openInventory(main.getSetupCMD().inventories.get("inv_" + invOpen));

            }
        }

        if(event.getInventory().getTitle().startsWith("§cMenu §8»")){
            event.setCancelled(true);
            if(event.getSlot() == 14){
                String[] arena = event.getInventory().getTitle().split("§8");
                Player p = (Player) event.getWhoClicked();
                playersArenaDelete.put(p, arena[2]);
                p.sendMessage("§cBedwars §7» Möchtest du die Arena §8[§c" + arena[2] + "§8] §7wirklich löschen?");
                p.sendMessage("§cBedwars §7» Schreibe um zu bestätigen JA in den Chat");

            }

        }
        else{

        }


    }
    HashMap<Player, String> playersArenaDelete = new HashMap<>();
    ArrayList<Player> playersInSetup = new ArrayList<>();

    @EventHandler
    public void onChatWriteEvent(AsyncPlayerChatEvent event) {
        if(playersInSetup.contains(event.getPlayer())){
            event.setCancelled(true);
            playersInSetup.remove(event.getPlayer());
            main.getSetupCMD().openArenaSettings(event.getPlayer(), event.getMessage());
        }if(playersArenaDelete.get(event.getPlayer()) != null){
            if(event.getMessage().equalsIgnoreCase("ja")){
                event.getPlayer().sendMessage("§cBedwars §7» Die Arena §8[§c" + playersArenaDelete.get(event.getPlayer())
                + "§8] §7wurde erfolgreich gelöscht!");
                GameManager gameManager = main.getGameManager().arenaHashMap.get(playersArenaDelete.get(event.getPlayer()));
                main.getFileManager().removeArena(playersArenaDelete.get(event.getPlayer()), gameManager);
                playersArenaDelete.remove(event.getPlayer());
            }else{
                event.getPlayer().sendMessage("§cBedwars §7» Die Arena §8[§c" + playersArenaDelete.get(event.getPlayer())
                        + "§8] §7wurde nicht gelöscht!");
                playersArenaDelete.remove(event.getPlayer());
            }

        }


    }




}
