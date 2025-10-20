package core;

import java.util.ArrayList;

/**
 * Player class - the protagonist controlled by the user
 */
public class Player extends Entity {
    private int experience;
    private int level;
    private ArrayList<Item> inventory;
    private int gold;
    
    public Player(String name) {
        super(name, 100, 15, 5);
        this.experience = 0;
        this.level = 1;
        this.inventory = new ArrayList<>();
        this.gold = 0;
    }
    
    /**
     * Add an item to inventory
     */
    public void addItem(Item item) {
        inventory.add(item);
        System.out.println("Added " + item.getName() + " to inventory!");
    }
    
    /**
     * Use an item from inventory
     */
    public boolean useItem(int index) {
        if (index >= 0 && index < inventory.size()) {
            Item item = inventory.get(index);
            item.use(this);
            if (item.isConsumable()) {
                inventory.remove(index);
            }
            return true;
        }
        return false;
    }
    
    /**
     * Display inventory
     */
    public void showInventory() {
        System.out.println("\n=== INVENTORY ===");
        if (inventory.isEmpty()) {
            System.out.println("Empty");
        } else {
            for (int i = 0; i < inventory.size(); i++) {
                System.out.println((i + 1) + ". " + inventory.get(i).getName() + " - " + inventory.get(i).getDescription());
            }
        }
        System.out.println("Gold: " + gold);
    }
    
    /**
     * Gain experience and potentially level up
     */
    public void gainExperience(int exp) {
        experience += exp;
        System.out.println("Gained " + exp + " experience!");
        
        int expNeeded = level * 100;
        if (experience >= expNeeded) {
            levelUp();
        }
    }
    
    /**
     * Level up the player
     */
    private void levelUp() {
        level++;
        experience = 0;
        
        maxHealth += 20;
        health = maxHealth;
        attackPower += 5;
        defense += 2;
        
        System.out.println("\n*** LEVEL UP! ***");
        System.out.println("You are now level " + level + "!");
        System.out.println("Health: " + maxHealth + " | Attack: " + attackPower + " | Defense: " + defense);
    }
    
    /**
     * Add gold
     */
    public void addGold(int amount) {
        gold += amount;
        System.out.println("Gained " + amount + " gold!");
    }
    
    // Getters
    public int getLevel() { return level; }
    public int getExperience() { return experience; }
    public int getGold() { return gold; }
    public ArrayList<Item> getInventory() { return inventory; }
}
