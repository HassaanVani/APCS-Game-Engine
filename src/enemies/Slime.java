package enemies;

import engine.*;
import java.awt.*;

/**
 * Slime Enemy - Basic enemy type
 * Demonstrates using shape sprites
 */
public class Slime extends Enemy {
    
    public Slime() {
        super("Slime", 40, 10, 4, 25, 10, .5);
        
        // Use shape sprite for simple, clean look
        setCustomSprite(new Color(100, 200, 100), "circle");
        
        // Alternative: Try to load custom sprite, fall back to green circle
        // setSpriteOrFallback("enemies/slime.png", new Color(100, 200, 100));
    }
    
    @Override
    public String performBattleAction(Player player) {
        int damage = attack();
        player.takeDamage(damage);
        return name + " bounces at you for " + damage + " damage!";
    }
    
    @Override
    public String getDescription() {
        return "A bouncy green slime creature!";
    }
}
