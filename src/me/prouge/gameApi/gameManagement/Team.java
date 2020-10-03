package me.prouge.gameApi.gameManagement;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;

public class Team {

    String name;
    String prefix;
    public ArrayList<Player> playersInTeam;
    public HashMap<String, Location> locations;
    ArrayList<String> updateActionbarTeams = new ArrayList<>();


    public Team(String name,
                String prefix,
                ArrayList<Player> playersInTeam,
                HashMap<String, Location> locations){
        this.name = name;
        this.prefix = prefix;
        this.playersInTeam = playersInTeam;
        this.locations = locations;
    }





    public ArrayList<Player> getPlayersInTeam(){
        return this.playersInTeam;
    }

    public void addPlayerToTeam(Player player){
        this.playersInTeam.add(player);
    }
    public void removePlayerFromTeam(Player player){
        this.playersInTeam.remove(player);
    }

    public void broadcastTeamMessage(String message){
        this.playersInTeam.forEach(player -> player.sendMessage(message));

    }
    public void broadcastTeamMessageWithoutPlayer(Player p, String message){
        this.playersInTeam.stream().filter(player -> player != p).forEach(player -> player.sendMessage(message));
    }

    public void teleportTeam(Location loc){
        this.playersInTeam.forEach(player -> player.teleport(loc));
    }

    public Location getLocation(String name){
        return this.locations.get(name);
    }

    public void sendActionbarOneTime(String message){
        PacketPlayOutChat packet = new PacketPlayOutChat(IChatBaseComponent.
                ChatSerializer.a("{\"text\":\"" +
                message.replace("&", "§") + "\"}"), (byte) 2);
        this.playersInTeam.forEach(player -> ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet));

    }

    public void sendActionbarUnlimited(String message){
            PacketPlayOutChat packet = new PacketPlayOutChat(IChatBaseComponent.
                    ChatSerializer.a("{\"text\":\"" +
                    message.replace("&", "§") + "\"}"), (byte) 2);
            this.playersInTeam.forEach(player -> ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet));
            updateActionbarTeams.add(this.name);
    }


}
