package enemies;

import engine.*;
import java.awt.*;

public class Slime extends Enemy {
    
    public Slime() {
        super("Slime", 40, 10, 4, 25, 10, .5);
        setCustomSprite(new Color(100, 200, 100), "circle");
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
