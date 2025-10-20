package examples;

import core.Enemy;
import core.Player;

/**
 * Example Enemy implementation - Goblin
 * Students can use this as a template for creating their own enemies
 */
public class Goblin extends Enemy {
    
    public Goblin() {
        super("Goblin", 30, 8, 2, 25, 10);
    }
    
    @Override
    public void takeTurn(Player player) {
        // Simple AI - just attack
        System.out.println(name + " attacks with a rusty dagger!");
        player.takeDamage(attack());
    }
    
    @Override
    public String getDescription() {
        return "A small, green creature with beady eyes and sharp teeth.";
    }
}
