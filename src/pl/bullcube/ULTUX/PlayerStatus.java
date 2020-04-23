package pl.bullcube.ULTUX;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@SerializableAs("PlayerStatus")
public class PlayerStatus implements ConfigurationSerializable {
    private World world;

    public World getWorld() {
        return world;
    }

    private ItemStack[] inventory;
    private double health;
    private int hunger;
    private float exp;
    private int level;
    private Location location;
    private ItemStack[] enderchestInventory;
    private String uid;

    public int getLevel() {
        return level;
    }

    public Location getLocation() {
        return location;
    }

    public ItemStack[] getInventory() {
        return inventory;
    }


    public float getExp() {
        return exp;
    }

    public double getHealth() {
        return health;
    }

    public String getUid() {
        return uid;
    }

    public int getHunger() {
        return hunger;
    }

    public ItemStack[] getEnderchestInventory() {
        return enderchestInventory;
    }

    public PlayerStatus(Player player) {
        this.inventory = player.getInventory().getContents();
        this.exp = player.getExp();
        this.level = player.getLevel();
        this.health = player.getHealth();
        this.hunger = player.getFoodLevel();
        this.location = player.getLocation();
        this.world = player.getWorld();
        this.location.setWorld(Bukkit.getServer().getWorld("world"));
        this.enderchestInventory = player.getEnderChest().getContents();
        this.uid = player.getUniqueId().toString();
    }

    public PlayerStatus(ItemStack[] inventory, double health, int hunger, float exp, int level, Location location, ItemStack[] enderchestInventory, String uid) {
        this.inventory = inventory;
        this.health = health;
        this.hunger = hunger;
        this.exp = exp;
        this.level = level;
        this.location = location;
        this.enderchestInventory = enderchestInventory;
        this.uid = uid;
    }

    @Override
    public String toString() {
        return "PlayerStatus{" +
                "inventory=" + Arrays.toString(inventory) +
                ", health=" + health +
                ", hunger=" + hunger +
                ", exp=" + exp +
                ", level=" + level +
                ", location=" + location +
                ", enderchestInventory=" + Arrays.toString(enderchestInventory) +
                ", uid='" + uid + '\'' +
                '}';
    }

    public void getPlayerStatus(Player player, boolean teleport){
        player.getInventory().setContents(inventory);
        player.setExp(exp);
        player.setLevel(level);
        player.setHealth(health);
        player.setFoodLevel(hunger);
        if (teleport) player.teleport(location);
        player.getEnderChest().setContents(enderchestInventory);
    }

    @Override
    public Map<String, Object> serialize() {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("inventory", inventory);
        hashMap.put("exp", exp);
        hashMap.put("level", level);
        hashMap.put("health", health);
        hashMap.put("hunger", hunger);
        location.setWorld(Bukkit.getServer().getWorld("world"));
        hashMap.put("location", location);
        hashMap.put("enderchest", enderchestInventory);
        hashMap.put("uid", uid);
        return hashMap;
    }

    public static PlayerStatus deserialize(Map<String, Object> map){
        ArrayList list = (ArrayList) map.get("inventory");
        ItemStack[] inventory = new ItemStack[list.size()];
        for (int i = 0; i < list.size(); i++){
            inventory[i] = (ItemStack) list.get(i);
        }
        double health = (double)map.get("health");
        int hunger = (int)map.get("hunger");
        float exp = ((Double) map.get("exp")).floatValue();
        int level = (int)map.get("level");
        Location location = (Location) map.get("location");

        ArrayList ench = (ArrayList) map.get("enderchest");
        ItemStack[] enderchestInventory = new ItemStack[ench.size()];
        for (int i = 0; i < ench.size(); i++){
            enderchestInventory[i] = (ItemStack) ench.get(i);
        }
        String uid = (String) map.get("uid");
        PlayerStatus ps = new PlayerStatus(inventory, health, hunger, exp, level, location, enderchestInventory, uid);
        return ps;
    }
}
