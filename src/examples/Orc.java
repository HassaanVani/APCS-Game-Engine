package examples;

import core.Enemy;
import core.Player;

/**
 * Example Enemy implementation - Orc (stronger enemy)
 * Demonstrates how to create more complex enemy behavior
 */
public class Orc extends Enemy {
    private boolean enraged = false;
    
    public Orc() {
        super("Orc Warrior", 50, 12, 5, 50, 25);
    }
    
    @Override
    public void takeTurn(Player player) {
        // More complex AI - enrages when health is low
        if (health < maxHealth / 2 && !enraged) {
            enraged = true;
            attackPower += 5;
            System.out.println(name + " becomes enraged! Attack increased!");
        }
        
        if (enraged) {
            System.out.println(name + " attacks with fury!");
        } else {
            System.out.println(name + " swings a massive axe!");
        }
        
        player.takeDamage(attack());
    }
    
    @Override
    public String getDescription() {
        return "A massive orc wielding a battle axe. Looks very dangerous!";
    }
}
