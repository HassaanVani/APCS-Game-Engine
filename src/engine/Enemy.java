package engine;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Abstract Enemy class - extend this for custom enemies
 * POLYMORPHISM: Each enemy type has different implementation
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
        sprite = SpriteManager.createColoredSprite(new Color(200, 50, 50));
    }
    
    /**
     * Set a custom sprite for the enemy
     * @param filename Sprite filename (e.g., "slime.png")
     */
    protected void setCustomSprite(String filename) {
        BufferedImage customSprite = SpriteManager.loadSprite(filename);
        if (customSprite != null) {
            sprite = customSprite;
        }
    }
    
    /**
     * Set a custom sprite using a color and shape
     * @param color Sprite color
     * @param shape "circle", "triangle", "diamond", "square"
     */
    protected void setCustomSprite(Color color, String shape) {
        sprite = SpriteManager.createShapeSprite(color, shape);
    }
    
    /**
     * Set sprite using SpriteManager with fallback
     * @param filename Sprite filename to try
     * @param fallbackColor Color to use if file not found
     */
    protected void setSpriteOrFallback(String filename, Color fallbackColor) {
        sprite = SpriteManager.getSpriteOrFallback(filename, fallbackColor);
    }
    
    /**
     * Define enemy AI behavior during battle
     * Override for unique strategies
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
