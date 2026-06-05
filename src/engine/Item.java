package engine;

/**
 * Item - Represents collectible and usable items in the game
 */
public class Item {
    public enum ItemType {
        POTION,        // Restores health
        POTION_MANA,   // Restores mana (MP)
        BUFF_ATTACK,   // Permanently increases attack power
        BUFF_DEFENSE,  // Permanently increases defense
        SWORD,         // Unlocks sword swing in overworld
        BOW,           // Unlocks bow & arrow firing
        ARROW_AMMO     // Refills arrow count
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
            case POTION_MANA:
                player.setMana(player.getMana() + value);
                System.out.println("Restored " + value + " MP.");
                break;
            case BUFF_ATTACK:
                player.increaseAttack(value);
                break;
            case BUFF_DEFENSE:
                player.increaseDefense(value);
                break;
            case SWORD:
                player.setHasSword(true);
                System.out.println("Equipped Sword! Press C to swing.");
                break;
            case BOW:
                player.setHasBow(true);
                System.out.println("Equipped Bow! Press X to shoot.");
                break;
            case ARROW_AMMO:
                player.addArrows(value);
                System.out.println("Gained " + value + " arrows.");
                break;
        }
    }
}
