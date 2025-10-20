package core;

/**
 * Abstract base class for all entities in the game (Player, Enemies, NPCs)
 * Demonstrates polymorphism - different entities share common behavior
 */
public abstract class Entity {
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
    
    /**
     * Take damage from an attack
     * @param damage the amount of raw damage
     */
    public void takeDamage(int damage) {
        int actualDamage = Math.max(1, damage - defense);
        health -= actualDamage;
        if (health < 0) health = 0;
        System.out.println(name + " takes " + actualDamage + " damage! (" + health + "/" + maxHealth + " HP)");
    }
    
    /**
     * Heal this entity
     */
    public void heal(int amount) {
        health = Math.min(maxHealth, health + amount);
        System.out.println(name + " heals " + amount + " HP! (" + health + "/" + maxHealth + " HP)");
    }
    
    /**
     * Check if entity is alive
     */
    public boolean isAlive() {
        return health > 0;
    }
    
    /**
     * Perform an attack - can be overridden for special attacks
     */
    public int attack() {
        return attackPower;
    }
    
    /**
     * Display entity's status
     */
    public void displayStatus() {
        System.out.println(name + " - HP: " + health + "/" + maxHealth + " | ATK: " + attackPower + " | DEF: " + defense);
    }
    
    // Getters
    public String getName() { return name; }
    public int getHealth() { return health; }
    public int getMaxHealth() { return maxHealth; }
    public int getAttackPower() { return attackPower; }
    public int getDefense() { return defense; }
    
    // Setters
    public void setHealth(int health) { 
        this.health = Math.max(0, Math.min(health, maxHealth)); 
    }
    public void setAttackPower(int attackPower) { this.attackPower = attackPower; }
    public void setDefense(int defense) { this.defense = defense; }
}
