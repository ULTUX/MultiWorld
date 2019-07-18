package pl.bullcube.ULTUX;

import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public class Main extends JavaPlugin {

    private JavaPlugin plugin = this;
    private World flat;
    HashMap<String, PlayerStatus> worldInventories = new HashMap<>();
    HashMap<String, PlayerStatus> flatInventories = new HashMap<>();

    public JavaPlugin getPluginInstance(){
        return plugin;
    }
    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED+"Plugin disabled!");

    }

    @Override
    public void onEnable() {
        Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_GREEN+"Plugin enabled!");
        createWorld();
    }

    private void createWorld(){
        WorldCreator worldgen = new WorldCreator("flat");
        worldgen.type(WorldType.FLAT);
        worldgen.generateStructures(true);
        flat = worldgen.createWorld();
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("flat")){
            if (sender instanceof Player){
                Player player = (Player) sender;
                if (player.getLocation().getWorld().getName().equals("flat")){
                    player.teleport(getServer().getWorld("world").getSpawnLocation());
                    player.sendMessage(ChatColor.GOLD+"Wracasz na "+ChatColor.GREEN+ "normalny "+ChatColor.GOLD+"świat...");

                    flatInventories.put(player.getUniqueId().toString(), new PlayerStatus(player));
                    player.getInventory().clear();
                    if (worldInventories.containsKey(player.getUniqueId().toString())){
                        worldInventories.get(player.getUniqueId().toString()).getPlayerStatus(player);
                        worldInventories.remove(player.getUniqueId().toString());
                    }


                } else{
                    player.teleport(flat.getSpawnLocation());
                    player.sendMessage(ChatColor.GOLD+"Własnie jesteś teleportowany na świat "+ChatColor.GREEN+ "superpłaski"+ChatColor.GOLD+"...");

                    worldInventories.put(player.getUniqueId().toString(), new PlayerStatus(player));
                    player.getInventory().clear();
                    if (flatInventories.containsKey(player.getUniqueId().toString())){
                        flatInventories.get(player.getUniqueId().toString()).getPlayerStatus(player);
                        flatInventories.remove(player.getUniqueId().toString());
                    }
                }
                return true;
            }
            else {
                sender.sendMessage(ChatColor.DARK_RED+"To musi być gracz!");
            }
        }
        return false;

    }
}
