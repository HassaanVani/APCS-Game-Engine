package enemies;

import engine.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Bat extends Enemy implements Flyable {
    
    public Bat() {
        super("Bat", 25, 12, 3, 30, 12, .2);
        setCustomSprite(new Color(80, 60, 100), "triangle");
        setEncounterType(EncounterType.HYBRID);
    }
    
    @Override
    public String performBattleAction(Player player) {
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
