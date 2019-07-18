package pl.bullcube.ULTUX;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PlayerStatus {
    private ItemStack[] inventory;
    private GameMode gameMode;
    private int experienceLevel;
    private double health;
    private int hunger;


    public ItemStack[] getInventory() {
        return inventory;
    }

    public GameMode getGameMode() {
        return gameMode;
    }

    public int getExperienceLevel() {
        return experienceLevel;
    }

    public double getHealth() {
        return health;
    }

    public int getHunger() {
        return hunger;
    }

    public PlayerStatus(Player player) {
        this.inventory = player.getInventory().getContents();
        this.gameMode = player.getGameMode();
        this.experienceLevel = player.getTotalExperience();
        this.health = player.getHealth();
        this.hunger = player.getFoodLevel();
    }

    public void getPlayerStatus(Player player){
        player.getInventory().setContents(inventory);
        player.setGameMode(gameMode);
        player.setExp(experienceLevel);
        player.setHealth(health);
        player.setFoodLevel(hunger);
    }
}
