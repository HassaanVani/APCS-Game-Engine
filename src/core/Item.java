package core;

/**
 * Abstract Item class - create unique items for levels
 * Demonstrates polymorphism through different item effects
 */
public abstract class Item {
    protected String name;
    protected String description;
    protected boolean consumable;
    
    public Item(String name, String description, boolean consumable) {
        this.name = name;
        this.description = description;
        this.consumable = consumable;
    }
    
    /**
     * Use the item on the player
     * Override this for different effects
     */
    public abstract void use(Player player);
    
    // Getters
    public String getName() { return name; }
    public String getDescription() { return description; }
    public boolean isConsumable() { return consumable; }
}
