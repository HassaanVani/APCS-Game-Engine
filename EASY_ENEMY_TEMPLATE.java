// ============================================================
// EASY ENEMY TEMPLATE - Streamlined for Students
// Copy this file and customize it to create your own enemy!
// ============================================================

package enemies;

import engine.*;
import java.awt.*;

/**
 * YOUR ENEMY NAME HERE
 * Example: FireSlime, IceGolem, DesertScorpion
 */
public class YourName_Enemy extends Enemy {
    public YourName_Enemy() {
        super(
            "Enemy Name",    // Name shown in battle
            50,              // Max Health (HP)
            12,              // Attack Power
            5,               // Defense
            30,              // Experience reward
            15,              // Gold reward
            0.5              // Run/escape chance (0.0 to 1.0)
        );
        
        // OPTION 1: Use a colored shape sprite (no image file needed!)
        setCustomSprite(Color.RED, "circle");
        // Shapes: "circle", "triangle", "diamond", "square"
        
        // OPTION 2: Try to load an image, use color if not found
        // setSpriteOrFallback("enemies/myenemy.png", Color.RED);
        
        // OPTION 3: Load a specific image file
        // setCustomSprite("enemies/myenemy.png");
    }
    
    @Override
    public String performBattleAction(Player player) {
        // What does your enemy do in battle?
        
        // SIMPLE ATTACK:
        int damage = attack();
        player.takeDamage(damage);
        return name + " attacks for " + damage + " damage!";
        
        // RANDOM MISS CHANCE:
        // if (Math.random() < 0.2) {  // 20% chance to miss
        //     return name + " missed!";
        // }
        // int damage = attack();
        // player.takeDamage(damage);
        // return name + " attacks for " + damage + " damage!";
        
        // SPECIAL ABILITIES:
        // if (Math.random() < 0.3) {  // 30% chance for special
        //     int bigDamage = attack() * 2;
        //     player.takeDamage(bigDamage);
        //     return name + " uses POWER ATTACK for " + bigDamage + " damage!";
        // } else {
        //     int damage = attack();
        //     player.takeDamage(damage);
        //     return name + " attacks for " + damage + " damage!";
        // }
    }
    
    @Override
    public String getDescription() {
        return "Describe your enemy here! What makes it unique?";
        // Example: "A fiery slime that burns everything it touches!"
    }
}
