package core;

/**
 * Abstract Enemy class - students will extend this to create unique enemies
 * Demonstrates polymorphism - each student can create different enemy types
 */
public abstract class Enemy extends Entity {
    protected int expReward;
    protected int goldReward;
    
    public Enemy(String name, int maxHealth, int attackPower, int defense, int expReward, int goldReward) {
        super(name, maxHealth, attackPower, defense);
        this.expReward = expReward;
        this.goldReward = goldReward;
    }
    
    /**
     * Determine enemy's action during combat
     * Students can override this for unique AI behavior
     */
    public abstract void takeTurn(Player player);
    
    /**
     * Called when enemy is defeated
     */
    public void onDefeat(Player player) {
        System.out.println(name + " has been defeated!");
        player.gainExperience(expReward);
        player.addGold(goldReward);
    }
    
    /**
     * Get a description of the enemy (students can override)
     */
    public abstract String getDescription();
    
    // Getters
    public int getExpReward() { return expReward; }
    public int getGoldReward() { return goldReward; }
}
