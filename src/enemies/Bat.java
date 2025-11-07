package enemies;

import engine.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Example Enemy - Bat
 * Flying enemy, slightly stronger than slime
 */
public class Bat extends Enemy {
    
    public Bat() {
        super("Bat", 25, 12, 3, 30, 12);
        
        // Use triangle shape for bat (flying enemy)
        setCustomSprite(new Color(80, 60, 100), "triangle");
        
        // Alternative: Load custom sprite with fallback
        // setSpriteOrFallback("enemies/bat.png", new Color(80, 60, 100));
    }
    
    @Override
    public String performBattleAction(Player player) {
        // Bat has a chance to dodge and counter-attack
        if (Math.random() < 0.3) {
            return name + " dodged your attack!";
        } else {
            int damage = attack();
            player.takeDamage(damage);
            return name + " swoops down for " + damage + " damage!";
        }
    }
    
    @Override
    public String getDescription() {
        return "A swift cave bat with glowing red eyes!";
    }
}
