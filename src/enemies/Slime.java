package enemies;

import engine.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Example Enemy - Slime
 * Weak enemy, good for beginners
 */
public class Slime extends Enemy {
    
    public Slime() {
        super("Slime", 30, 8, 2, 25, 10);
        createCustomSprite();
    }
    
    private void createCustomSprite() {
        sprite = new BufferedImage(GamePanel.TILE_SIZE, GamePanel.TILE_SIZE, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = sprite.createGraphics();
        
        // Draw a green blob
        g2.setColor(new Color(100, 200, 100));
        g2.fillOval(8, 12, 32, 28);
        
        // Highlight
        g2.setColor(new Color(150, 255, 150, 180));
        g2.fillOval(12, 14, 12, 10);
        
        // Eyes
        g2.setColor(Color.BLACK);
        g2.fillOval(16, 22, 4, 4);
        g2.fillOval(26, 22, 4, 4);
        
        g2.dispose();
    }
    
    @Override
    public String performBattleAction(Player player) {
        // Simple attack
        int damage = attack();
        player.takeDamage(damage);
        return name + " bounces at you for " + damage + " damage!";
    }
    
    @Override
    public String getDescription() {
        return "A bouncy green slime creature!";
    }
}
