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
        super("Bat", 40, 12, 3, 35, 15);
        createCustomSprite();
    }
    
    private void createCustomSprite() {
        sprite = new BufferedImage(GamePanel.TILE_SIZE, GamePanel.TILE_SIZE, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = sprite.createGraphics();
        
        // Draw a bat shape
        g2.setColor(new Color(60, 40, 80));
        
        // Body
        g2.fillOval(18, 20, 12, 16);
        
        // Wings
        int[] xPoints = {18, 8, 18};
        int[] yPoints = {24, 28, 32};
        g2.fillPolygon(xPoints, yPoints, 3);
        
        int[] xPoints2 = {30, 40, 30};
        g2.fillPolygon(xPoints2, yPoints, 3);
        
        // Eyes (red)
        g2.setColor(Color.RED);
        g2.fillOval(20, 24, 3, 3);
        g2.fillOval(26, 24, 3, 3);
        
        g2.dispose();
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
