package pl.bullcube.ULTUX;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

public class EventHandlers implements Listener {

    @EventHandler
    public void PlayerTeleportEvent(PlayerTeleportEvent e){
        Player player = e.getPlayer();
        if (e.getFrom().getWorld().getName().equals("flat") && !e.getFrom().getWorld().getName().equals(e.getTo().getWorld().getName())){
            player.sendMessage(ChatColor.GOLD+"Wracasz na "+ChatColor.GREEN+ "normalny "+ChatColor.GOLD+"świat...");
            Main.flatInventories.put(player.getUniqueId().toString(), new PlayerStatus(player));
            player.getEnderChest().clear();
            player.getInventory().clear();
            player.setGameMode(GameMode.SURVIVAL);
            if (Main.worldInventories.containsKey(player.getUniqueId().toString())) {
                Main.worldInventories.get(player.getUniqueId().toString()).getPlayerStatus(player, false);
                Main.worldInventories.remove(player.getUniqueId().toString());
            }
        }



        else if (e.getTo().getWorld().getName().equals("flat") && !e.getFrom().getWorld().getName().equals(e.getTo().getWorld().getName())){
            player.sendMessage(ChatColor.GOLD+"Własnie jesteś teleportowany na świat "+ChatColor.GREEN+ "superpłaski"+ChatColor.GOLD+"...");
            Main.worldInventories.put(player.getUniqueId().toString(), new PlayerStatus(player));
            player.getInventory().clear();
            player.getEnderChest().clear();
            player.setGameMode(GameMode.CREATIVE);
            if (Main.flatInventories.containsKey(player.getUniqueId().toString())) {
                Main.flatInventories.get(player.getUniqueId().toString()).getPlayerStatus(player, false);
                Main.flatInventories.remove(player.getUniqueId().toString());
            }
        }
    }
}
