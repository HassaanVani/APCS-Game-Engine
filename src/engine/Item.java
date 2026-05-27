package engine;

/**
 * Item - Represents collectible and usable items in the game
 */
public class Item {
    public enum ItemType {
        POTION,        // Restores health
        BUFF_ATTACK,   // Permanently increases attack power
        BUFF_DEFENSE   // Permanently increases defense
    }
    
    private String name;
    private String description;
    private ItemType type;
    private int value; // HP restored or stat points increased
    
    public Item(String name, String description, ItemType type, int value) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.value = value;
    }
    
    public String getName() { return name; }
    public String getDescription() { return description; }
    public ItemType getType() { return type; }
    public int getValue() { return value; }
    
    /**
     * Use the item on a player
     */
    public void use(Player player) {
        switch (type) {
            case POTION:
                player.heal(value);
                System.out.println("Healed player for " + value + " HP.");
                break;
            case BUFF_ATTACK:
                player.increaseAttack(value);
                break;
            case BUFF_DEFENSE:
                player.increaseDefense(value);
                break;
        }
    }
}
