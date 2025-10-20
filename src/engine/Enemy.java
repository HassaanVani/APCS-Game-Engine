package engine;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Abstract Enemy class - students extend this for custom enemies
 * POLYMORPHISM: Each student creates different enemy types
 */
public abstract class Enemy extends Entity {
    protected int expReward;
    protected int goldReward;
    protected boolean defeated = false;
    
    public Enemy(String name, int maxHealth, int attackPower, int defense, int expReward, int goldReward) {
        super(name, maxHealth, attackPower, defense);
        this.expReward = expReward;
        this.goldReward = goldReward;
        
        // Create default enemy sprite if not overridden
        if (sprite == null) {
            createDefaultSprite();
        }
    }
    
    protected void createDefaultSprite() {
        // Default enemy appearance (red square)
        sprite = new BufferedImage(GamePanel.TILE_SIZE, GamePanel.TILE_SIZE, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = sprite.createGraphics();
        g2.setColor(new Color(200, 50, 50)); // Red enemy
        g2.fillRect(4, 4, GamePanel.TILE_SIZE - 8, GamePanel.TILE_SIZE - 8);
        g2.setColor(Color.WHITE);
        g2.drawRect(4, 4, GamePanel.TILE_SIZE - 8, GamePanel.TILE_SIZE - 8);
        g2.dispose();
    }
    
    /**
     * Define enemy AI behavior during battle
     * Students can override for unique strategies
     */
    public abstract String performBattleAction(Player player);
    
    /**
     * Get description shown in battle
     */
    public abstract String getDescription();
    
    /**
     * Called when enemy is defeated
     */
    public void onDefeat(Player player) {
        defeated = true;
        player.gainExperience(expReward);
        player.addGold(goldReward);
    }
    
    public void setPosition(int x, int y) {
        this.worldX = x;
        this.worldY = y;
    }
    
    public void render(Graphics2D g2) {
        if (!defeated && sprite != null) {
            g2.drawImage(sprite, worldX, worldY, null);
        }
    }
    
    // Getters
    public int getExpReward() { return expReward; }
    public int getGoldReward() { return goldReward; }
    public boolean isDefeated() { return defeated; }
    public int getWorldX() { return worldX; }
    public int getWorldY() { return worldY; }
}
