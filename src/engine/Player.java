package engine;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Player character controlled by keyboard
 */
public class Player extends Entity {
    private KeyHandler keyHandler;
    private int speed = 4;
    
    // Stats
    private int level = 1;
    private int experience = 0;
    private int gold = 0;
    
    public Player(KeyHandler keyHandler) {
        super("Player", 100, 15, 10);
        this.keyHandler = keyHandler;
        
        worldX = 100;
        worldY = 100;
        
        createDefaultSprite();
    }
    
    private void createDefaultSprite() {
        // Create a simple colored square as default sprite
        sprite = new BufferedImage(GamePanel.TILE_SIZE, GamePanel.TILE_SIZE, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = sprite.createGraphics();
        g2.setColor(new Color(50, 150, 255)); // Blue player
        g2.fillRect(4, 4, GamePanel.TILE_SIZE - 8, GamePanel.TILE_SIZE - 8);
        g2.setColor(Color.WHITE);
        g2.drawRect(4, 4, GamePanel.TILE_SIZE - 8, GamePanel.TILE_SIZE - 8);
        g2.dispose();
    }
    
    public void update() {
        // Save old position for collision detection
        int oldX = worldX;
        int oldY = worldY;
        
        // Movement
        if (keyHandler.upPressed) {
            worldY -= speed;
            direction = Direction.UP;
        } else if (keyHandler.downPressed) {
            worldY += speed;
            direction = Direction.DOWN;
        } else if (keyHandler.leftPressed) {
            worldX -= speed;
            direction = Direction.LEFT;
        } else if (keyHandler.rightPressed) {
            worldX += speed;
            direction = Direction.RIGHT;
        }
        
        // Store old position for potential collision rollback
        this.oldX = oldX;
        this.oldY = oldY;
    }
    
    public void render(Graphics2D g2) {
        g2.drawImage(sprite, worldX, worldY, null);
    }
    
    public void setPosition(int x, int y) {
        this.worldX = x;
        this.worldY = y;
    }
    
    public void rollbackPosition() {
        worldX = oldX;
        worldY = oldY;
    }
    
    public void gainExperience(int exp) {
        experience += exp;
        System.out.println("Gained " + exp + " experience!");
        
        // Level up check
        int expNeeded = level * 100;
        if (experience >= expNeeded) {
            levelUp();
        }
    }
    
    private void levelUp() {
        level++;
        experience = 0;
        maxHealth += 20;
        health = maxHealth;
        attackPower += 5;
        defense += 2;
        System.out.println("Level up! Now level " + level);
    }
    
    public void addGold(int amount) {
        gold += amount;
    }
    
    // Getters
    public int getLevel() { return level; }
    public int getExperience() { return experience; }
    public int getGold() { return gold; }
    public int getSpeed() { return speed; }
    public int getWorldX() { return worldX; }
    public int getWorldY() { return worldY; }
}
