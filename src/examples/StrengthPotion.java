package examples;

import core.Item;
import core.Player;

/**
 * Example Item - Strength Potion
 * Demonstrates a different item effect (stat boost)
 */
public class StrengthPotion extends Item {
    
    public StrengthPotion() {
        super("Strength Potion", "Permanently increases Attack by 3", true);
    }
    
    @Override
    public void use(Player player) {
        player.setAttackPower(player.getAttackPower() + 3);
        System.out.println("Attack increased to " + player.getAttackPower() + "!");
    }
}
