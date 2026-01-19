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
            15               // Gold reward
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

// ============================================================
// SPRITE OPTIONS
// ============================================================
//
// 1. COLORED SHAPES (Easiest - No files needed!)
//    setCustomSprite(Color.RED, "circle");
//    setCustomSprite(Color.BLUE, "triangle");
//    setCustomSprite(Color.GREEN, "diamond");
//    setCustomSprite(Color.YELLOW, "square");
//
// 2. IMAGE WITH FALLBACK (Recommended)
//    setSpriteOrFallback("enemies/myenemy.png", Color.RED);
//    - Tries to load image, uses color if not found
//
// 3. IMAGE ONLY
//    setCustomSprite("enemies/myenemy.png");
//    - Only works if image file exists
//
// ============================================================
// CREATING CUSTOM SPRITES PROGRAMMATICALLY
// ============================================================
//
// You can also create sprites without image files:
//
// BufferedImage mySprite = SpriteManager.createColoredSprite(Color.ORANGE);
// sprite = mySprite;
//
// Or with shapes:
// sprite = SpriteManager.createShapeSprite(Color.PURPLE, "circle");
//
// ============================================================
// BATTLE ACTION IDEAS
// ============================================================
//
// 1. Basic Attack
//    int damage = attack();
//    player.takeDamage(damage);
//    return name + " attacks!";
//
// 2. Random Miss
//    if (Math.random() < 0.2) return name + " missed!";
//
// 3. Critical Hit
//    if (Math.random() < 0.15) {
//        int crit = attack() * 3;
//        player.takeDamage(crit);
//        return name + " CRITICAL HIT for " + crit + "!";
//    }
//
// 4. Weak Attack
//    if (Math.random() < 0.3) {
//        int weak = attack() / 2;
//        player.takeDamage(weak);
//        return name + " weak attack for " + weak + ".";
//    }
//
// 5. Defend (No damage)
//    if (Math.random() < 0.25) {
//        return name + " is defending!";
//    }
//
// 6. Health-Based Behavior
//    if (health < maxHealth / 2) {
//        // Desperate attack when low health
//        int damage = attack() * 2;
//        player.takeDamage(damage);
//        return name + " desperate attack for " + damage + "!";
//    }
//
// ============================================================
// STATS GUIDE
// ============================================================
//
// Health: How much damage enemy can take
//   - Weak: 20-40
//   - Normal: 50-80
//   - Strong: 100-150
//   - Boss: 200+
//
// Attack Power: Base damage dealt
//   - Weak: 5-10
//   - Normal: 12-18
//   - Strong: 20-30
//   - Boss: 35+
//
// Defense: Reduces damage taken
//   - Weak: 2-5
//   - Normal: 5-10
//   - Strong: 12-20
//   - Boss: 25+
//
// Experience: Given to player when defeated
//   - Weak: 10-20
//   - Normal: 25-50
//   - Strong: 60-100
//   - Boss: 150+
//
// Gold: Money given to player when defeated
//   - Weak: 5-15
//   - Normal: 15-30
//   - Strong: 35-60
//   - Boss: 100+
//
// ============================================================
