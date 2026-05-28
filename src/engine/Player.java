package engine;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Player character controlled by keyboard
 */
public class Player extends Entity {
    private KeyHandler keyHandler;
    
    // Stats
    private int level = 1;
    private int experience = 0;
    private int gold = 0;
    private java.util.ArrayList<Item> inventory = new java.util.ArrayList<>();
    
    // Combat Overhaul stats
    private int mana = 30;
    private int maxMana = 30;
    private boolean hasSword = false;
    private boolean hasBow = false;
    private int arrows = 0;
    private int invincibilityFrames = 0;
    private int swordSwingActiveFrames = 0;
    
    public Player(KeyHandler keyHandler) {
        super("Player", 100, 15, 10);
        this.keyHandler = keyHandler;
        this.speed = 4;
        
        worldX = 100;
        worldY = 100;
        
        createDefaultSprite();
    }
    
    private void createDefaultSprite() {
        // Try to load custom sprite, fall back to default
        sprite = SpriteManager.getSpriteOrFallback("player.png", new Color(50, 150, 255));
    }
    
    /**
     * Set a custom sprite for the player
     * @param filename Sprite filename (e.g., "player.png")
     */
    public void setCustomSprite(String filename) {
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
    public void setCustomSprite(Color color, String shape) {
        sprite = SpriteManager.createShapeSprite(color, shape);
    }
    
    public void update() {
        if (invincibilityFrames > 0) {
            invincibilityFrames--;
        }
        if (swordSwingActiveFrames > 0) {
            swordSwingActiveFrames--;
        }
        
        // Save old position for collision detection
        int oldX = worldX;
        int oldY = worldY;
        
        boolean moving = false;
        
        // Movement
        if (keyHandler.upPressed) {
            worldY -= speed;
            direction = Direction.UP;
            moving = true;
        } else if (keyHandler.downPressed) {
            worldY += speed;
            direction = Direction.DOWN;
            moving = true;
        } else if (keyHandler.leftPressed) {
            worldX -= speed;
            direction = Direction.LEFT;
            moving = true;
        } else if (keyHandler.rightPressed) {
            worldX += speed;
            direction = Direction.RIGHT;
            moving = true;
        }
        
        if (moving) {
            animTick++;
            bobOffset = (int)(Math.sin(animTick * 0.25) * 4);
        } else {
            bobOffset = 0;
            animTick = 0;
        }
        
        // Store old position for potential collision rollback
        this.oldX = oldX;
        this.oldY = oldY;
    }
    
    public void render(Graphics2D g2) {
        g2.drawImage(sprite, worldX, worldY + bobOffset, null);
    }
    
    public void setPosition(int x, int y) {
        this.worldX = x;
        this.worldY = y;
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
        maxMana += 10;
        mana = maxMana;
        attackPower += 5;
        defense += 2;
        System.out.println("Level up! Now level " + level);
    }
    
    public void addGold(int amount) {
        gold += amount;
    }
    
    @Override
    public void takeDamage(int damage) {
        if (invincibilityFrames == 0) {
            super.takeDamage(damage);
            triggerInvincibility(60); // 1 second (60 frames) of invincibility
        }
    }
    
    // Getters and Setters
    public int getMana() { return mana; }
    public void setMana(int mana) { this.mana = Math.max(0, Math.min(mana, maxMana)); }
    public void useMana(int amount) { setMana(mana - amount); }
    public int getMaxMana() { return maxMana; }
    public void setMaxMana(int maxMana) { this.maxMana = maxMana; }
    
    public boolean hasSword() { return hasSword; }
    public void setHasSword(boolean hasSword) { this.hasSword = hasSword; }
    
    public boolean hasBow() { return hasBow; }
    public void setHasBow(boolean hasBow) { this.hasBow = hasBow; }
    
    public int getArrows() { return arrows; }
    public void setArrows(int arrows) { this.arrows = arrows; }
    public void addArrows(int amount) { this.arrows += amount; }
    
    public int getInvincibilityFrames() { return invincibilityFrames; }
    public void triggerInvincibility(int frames) { this.invincibilityFrames = frames; }
    
    public int getSwordSwingActiveFrames() { return swordSwingActiveFrames; }
    public void startSwordSwing() { this.swordSwingActiveFrames = 15; }
    
    public int getLevel() { return level; }
    public int getExperience() { return experience; }
    public int getGold() { return gold; }
    public int getSpeed() { return speed; }
    public int getWorldX() { return worldX; }
    public int getWorldY() { return worldY; }
    
    public void addItem(Item item) {
        inventory.add(item);
    }
    
    public void removeItem(Item item) {
        inventory.remove(item);
    }
    
    public java.util.ArrayList<Item> getInventory() {
        return inventory;
    }
    
    public void increaseAttack(int amount) {
        attackPower += amount;
    }
    
    public void increaseDefense(int amount) {
        defense += amount;
    }
}
