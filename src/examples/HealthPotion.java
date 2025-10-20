package examples;

import core.Item;
import core.Player;

/**
 * Example Item implementation - Health Potion
 * Students can use this as a template for creating their own items
 */
public class HealthPotion extends Item {
    private int healAmount;
    
    public HealthPotion() {
        super("Health Potion", "Restores 30 HP", true);
        this.healAmount = 30;
    }
    
    @Override
    public void use(Player player) {
        player.heal(healAmount);
    }
}
