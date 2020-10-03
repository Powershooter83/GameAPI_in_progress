package me.prouge.gameApi.commands;

import me.prouge.gameApi.Main;
import me.prouge.gameApi.gameManagement.GameManager;
import me.prouge.gameApi.utils.ItemBuilder;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SetupCMD implements CommandExecutor {
    Inventory inventory = Bukkit.createInventory(null, 27, "§c§lSetup");
    Inventory arenaListInventory = Bukkit.createInventory(null, 45, "§c§lArenen");
    private Main main;
    public SetupCMD(Main main){
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            if(player.hasPermission("gameAPI.setup")){
                openSetupInventory(player);





            }


        }return false;


    }

    public void openSetupInventory(Player player){
        for(int i = 0; i < 27; i++){
            inventory.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE).setDyeColor(DyeColor.GRAY).setName("§b").
                    toItemStack());
        }

        inventory.setItem(9, new ItemBuilder(Material.NAME_TAG).setName("Arena erstellen").toItemStack());
        inventory.setItem(10, new ItemBuilder(Material.CHEST).setName("Arena liste").toItemStack());



        player.openInventory(inventory);
    }






    ArrayList<ItemStack> items = new ArrayList<>();
    public void openAllArenas(Player player){
        int itemCount = 0;
        items.clear();
        for(String arena : main.getGameManager().arenaHashMap.keySet()) {
            items.add(new ItemBuilder(Material.BED).setName(arena).toItemStack());
            itemCount++;
        }
        if(itemCount < 44){
            int currentSlot = 0;
            for(ItemStack itemStack : items){
                arenaListInventory.setItem(currentSlot, itemStack);
                currentSlot++;
            }
            player.openInventory(arenaListInventory);
            return;
        }
        infiniteInventory(player);
    }


    public void openArenaSettings(Player player, String arena){
        if(main.getGameManager().arenaHashMap.get(arena) == null){
            GameManager gameManager = new GameManager(arena, new ArrayList<Player>(),
                    new HashMap<String, Location>(), null);
            main.getGameManager().arenaHashMap.put(arena, gameManager);
            player.sendMessage("§cBedwars §7» Die Arena §8[§c" + arena + "§8] §7wurde erfolgreich erstellt!");
            openSettingsInventory(player, arena);



        }else{
            player.sendMessage("§cBedwars §7» Die Arena §8[§c" + arena + "§8] §7exestiert bereits.");
        }



    }
    public HashMap<Player, Integer> currentInv = new HashMap<>();
    public HashMap<String, Inventory> inventories= new HashMap<>();
    public void infiniteInventory(Player player) {
        currentInv.put(player, 1);
        int invSize = 45;
        int neededInventories = ((int) Math.ceil(items.size() / invSize-8)); //Die Inventar zahl ausrechnen
        if(neededInventories == 0){//falls die Zahl null sein sollte, auf 1 Runden.
            neededInventories++;
        }
        if(items.size() <= invSize-1){ //Überprüfen ob die items in ein Inventar passen würden
            Inventory inventory = Bukkit.createInventory(null, invSize, "§c§lArenen");
            int slot = 0;
            for(ItemStack item : items){
                inventory.setItem(slot, item);
                slot++;
            }
        }else {
            ArrayList<ItemStack> toRemove = new ArrayList<>(); //Zum entfernen von items aus der Array
            for (int i = 0; i >= neededInventories; i++) { //Durch loopen bis alle Inventare erstellt wurden
                Inventory inventory = Bukkit.createInventory(null, invSize, "§c§lArenen");
                int slot = 0; //item Position setzen
                for (ItemStack item : items) {
                    if (slot == invSize - 1) {
                        inventory.setItem(slot, item);
                        toRemove.add(item);
                        inventories.put("inv_" + i, inventory); //das Inventar zu der HashMap hinzufügen
                    }
                    slot++;
                }
                for (ItemStack item : toRemove) { //Die Items von der ArrayList entfernen
                    items.remove(item);
                }
                toRemove.clear();
            }
            player.openInventory(inventories.get("inv_1")); //Inventar 1 öffnen

        }
    }







    public void openSettingsInventory(Player player, String arena){
        Inventory setingsInventorys = Bukkit.createInventory(null, 27, "§cMenu §8» §8" + arena);
        for(int i = 0; i < 6; i++){
            setingsInventorys.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE).setName("§b").
                    setDyeColor(DyeColor.GRAY).toItemStack());
        }

        for(int i = 18; i < 24; i++){
            setingsInventorys.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE).setName("§b").
                    setDyeColor(DyeColor.GRAY).toItemStack());
        }

        setingsInventorys.setItem(9, new ItemBuilder(Material.NAME_TAG).
                setName("§cArena umbennenen").toItemStack());

        setingsInventorys.setItem(10, new ItemBuilder(Material.FIREBALL).
                setName("§8» §7Wartelobby setzen").setLore("§8➥ §7Setze den Wartelobby Spawn!").toItemStack());

        setingsInventorys.setItem(11, new ItemBuilder(Material.BARRIER).
                setName("§cSpectator setzen").toItemStack());

        setingsInventorys.setItem(12, new ItemBuilder(Material.SIGN).
                setName("§cJoin sign setzen").toItemStack());

        setingsInventorys.setItem(13, new ItemBuilder(Material.IRON_AXE).
                setName("§cTeam erstellen / bearbeiten").toItemStack());
        setingsInventorys.setItem(14, new ItemBuilder(Material.INK_SACK).setDyeColor(DyeColor.ORANGE).
                setName("§cArena löschen").toItemStack());


        setingsInventorys.setItem(15, new ItemBuilder(Material.SKULL_ITEM).
                setName("§cMax spieler").toItemStack());
        setingsInventorys.setItem(16, new ItemBuilder(Material.SKULL_ITEM).
                setName("§cMin spieler").toItemStack());
        setingsInventorys.setItem(17, new ItemBuilder(Material.SKULL_ITEM).
                setName("§cStart spieler").toItemStack());



        for(int i = 6; i < 9; i++){
            setingsInventorys.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE).setName("§c+ 1").
                    setDyeColor(DyeColor.LIME).toItemStack());
        }
        for(int i = 24; i < 27; i++){
            setingsInventorys.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE).setName("§c- 1").
                    setDyeColor(DyeColor.RED).toItemStack());
        }
        player.openInventory(setingsInventorys);





    }

}
