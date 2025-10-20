package engine;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Base class for all entities (Player, Enemies, NPCs)
 */
public abstract class Entity {
    // Position in game world
    protected int worldX, worldY;
    protected int oldX, oldY; // For collision rollback
    
    // Collision box (relative to sprite position)
    protected Rectangle solidArea = new Rectangle(8, 8, 32, 32);
    
    // Sprite and animation
    protected BufferedImage sprite;
    protected BufferedImage[] walkSprites = new BufferedImage[4]; // up, down, left, right
    protected int spriteCounter = 0;
    protected int spriteNum = 1;
    
    // Direction
    protected Direction direction = Direction.DOWN;
    
    public enum Direction {
        UP, DOWN, LEFT, RIGHT
    }
    
    // Stats
    protected String name;
    protected int health;
    protected int maxHealth;
    protected int attackPower;
    protected int defense;
    
    public Entity(String name, int maxHealth, int attackPower, int defense) {
        this.name = name;
        this.maxHealth = maxHealth;
        this.health = maxHealth;
        this.attackPower = attackPower;
        this.defense = defense;
    }
    
    public void takeDamage(int damage) {
        int actualDamage = Math.max(1, damage - defense);
        health -= actualDamage;
        if (health < 0) health = 0;
    }
    
    public void heal(int amount) {
        health = Math.min(maxHealth, health + amount);
    }
    
    public boolean isAlive() {
        return health > 0;
    }
    
    public int attack() {
        return attackPower;
    }
    
    // Collision detection
    public Rectangle getCollisionBox() {
        return new Rectangle(worldX + solidArea.x, worldY + solidArea.y, 
                           solidArea.width, solidArea.height);
    }
    
    public boolean intersects(Entity other) {
        return getCollisionBox().intersects(other.getCollisionBox());
    }
    
    // Getters and setters
    public String getName() { return name; }
    public int getHealth() { return health; }
    public int getMaxHealth() { return maxHealth; }
    public int getAttackPower() { return attackPower; }
    public int getDefense() { return defense; }
    public void setHealth(int health) { this.health = Math.max(0, Math.min(health, maxHealth)); }
    public BufferedImage getSprite() { return sprite; }
    public void setSprite(BufferedImage sprite) { this.sprite = sprite; }
}
