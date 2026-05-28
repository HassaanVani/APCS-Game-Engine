package enemies;

import engine.*;
import java.awt.Color;

/**
 * WaterSlime - A swimmable enemy that can navigate through water tiles
 */
public class WaterSlime extends Enemy implements Swimmable {
    
    public WaterSlime() {
        // Stats: Name, MaxHealth, AttackPower, Defense, ExpReward, GoldReward, RunChance
        super("Water Slime", 45, 8, 3, 20, 8, 0.5);
        
        // Deep blue circle representation
        setCustomSprite(new Color(50, 100, 220), "circle");
    }
    
    @Override
    public String performBattleAction(Player player) {
        int damage = attack();
        player.takeDamage(damage);
        return name + " splashes water at you dealing " + damage + " damage!";
    }
    
    @Override
    public String getDescription() {
        return "A wet, blue slime that moves through ponds and lakes.";
    }
}
