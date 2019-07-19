package pl.bullcube.ULTUX;

import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.HashMap;



public class Main extends JavaPlugin {

    public static HashMap<String, PlayerStatus> worldInventories = new HashMap<>();

    public static HashMap<String, PlayerStatus> flatInventories = new HashMap<>();

    static {
        ConfigurationSerialization.registerClass(PlayerStatus.class, "PlayerStatus");
    }

    static Plugin plugin = null;
    private World flat;


    @Override
    public void onDisable() {


        worldInventories.forEach((uid, status) -> {
            getConfig().set("world."+uid, status);
        });
        flatInventories.forEach((uid, status) ->{
            getConfig().set("flat."+uid, status);
        });

        saveConfig();

        Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED+"Plugin disabled!");

    }

    @Override
    public void onEnable() {
        plugin = this;
        saveDefaultConfig();
        saveConfig();
        createWorld();
        deserializeConfig();
        Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_GREEN+"Plugin enabled!");
        Bukkit.getServer().getPluginManager().registerEvents(new EventHandlers(), plugin);

    }

    private void createWorld(){
        WorldCreator worldgen = new WorldCreator("flat");
        worldgen.type(WorldType.FLAT);
        worldgen.generateStructures(true);
        flat = worldgen.createWorld();
    }

    public void deserializeConfig(){
        ConfigurationSection flat = getConfig().getConfigurationSection("flat");
        if (flat != null){
            for (String uid : flat.getKeys(false)){
                flatInventories.put(uid, (PlayerStatus) flat.get(uid));
            }
        }
        ConfigurationSection world = getConfig().getConfigurationSection("world");
        if (world != null){
        for (String uid : world.getKeys(false)){
            worldInventories.put(uid, (PlayerStatus) world.get(uid));

        }}


    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("flat")){
            if (sender instanceof Player){
                Player player = (Player) sender;


                if (player.getLocation().getWorld().getName().equals("flat")){
//                    player.sendMessage(ChatColor.GOLD+"Wracasz na "+ChatColor.GREEN+ "normalny "+ChatColor.GOLD+"świat...");
//                    flatInventories.put(player.getUniqueId().toString(), new PlayerStatus(player));
//                    player.getEnderChest().clear();
//                    player.getInventory().clear();
//                    if (worldInventories.containsKey(player.getUniqueId().toString())){
//                        worldInventories.get(player.getUniqueId().toString()).getPlayerStatus(player, true);
//                        worldInventories.remove(player.getUniqueId().toString());
//                    }
//                    else player.teleport(getServer().getWorld("world").getSpawnLocation());
                    if (worldInventories.containsKey(player.getUniqueId().toString())) {
                        player.teleport(worldInventories.get(player.getUniqueId().toString()).getLocation());
                    }
                    else {
                        player.teleport(Bukkit.getWorld("world").getSpawnLocation());
                    }



                } else{
//                    player.sendMessage(ChatColor.GOLD+"Własnie jesteś teleportowany na świat "+ChatColor.GREEN+ "superpłaski"+ChatColor.GOLD+"...");
//
//                    worldInventories.put(player.getUniqueId().toString(), new PlayerStatus(player));
//                    player.getInventory().clear();
//                    player.getEnderChest().clear();
//                    if (flatInventories.containsKey(player.getUniqueId().toString())){
//                        flatInventories.get(player.getUniqueId().toString()).getPlayerStatus(player, true);
//                        flatInventories.remove(player.getUniqueId().toString());
//                    }
//                    else player.teleport(flat.getSpawnLocation());
                    if (flatInventories.containsKey(player.getUniqueId().toString())) {
                        Location location = flatInventories.get(player.getUniqueId().toString()).getLocation();
                        location.setWorld(Bukkit.getServer().getWorld("flat"));
                        player.teleport(location);
                    }
                    else {
                        player.teleport(flat.getSpawnLocation());
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
