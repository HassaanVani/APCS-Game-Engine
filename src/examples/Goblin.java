package examples;

import core.Enemy;
import core.Player;

/**
 * Example Enemy implementation - Goblin
 */
public class Goblin extends Enemy {
    
    public Goblin() {
        super("Goblin", 30, 8, 2, 25, 10);
    }
    
    @Override
    public void takeTurn(Player player) {
        System.out.println(name + " attacks with a rusty dagger!");
        player.takeDamage(attack());
    }
    
    @Override
    public String getDescription() {
        return "A small, green creature with beady eyes and sharp teeth.";
    }
}
