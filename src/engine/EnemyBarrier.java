package engine;

import java.awt.Color;

/**
 * EnemyBarrier - A solid gate that remains closed (solid) until all enemies in the level are defeated.
 * Once all enemies are defeated, it opens (becomes non-solid and changes appearance).
 */
public class EnemyBarrier extends Interactable {
    private boolean opened = false;
    
    public EnemyBarrier(int x, int y) {
        super("Enemy Barrier", x, y);
        this.solid = true;
        // Closed gate appearance: a solid red square shape
        this.sprite = SpriteManager.createShapeSprite(new Color(220, 50, 50), "square");
    }
    
    @Override
    public void update(GameLevel level) {
        if (opened) return;
        
        // Check if all enemies in the level are defeated
        boolean allDefeated = true;
        for (Enemy enemy : level.getEnemies()) {
            if (!enemy.isDefeated()) {
                allDefeated = false;
                break;
            }
        }
        
        if (allDefeated) {
            opened = true;
            this.solid = false; // Player can walk through it now!
            // Open gate appearance: green circle shape
            this.sprite = SpriteManager.createShapeSprite(new Color(50, 220, 50), "circle");
            
            // Play a sound effect to alert the player
            SoundManager.playSE("chest.wav"); 
            System.out.println("The barrier has opened!");
        }
    }
    
    @Override
    public void onInteract(Player player, GamePanel gp) {
        if (opened) {
            gp.showDialogue("The barrier is open. The path is clear!");
        } else {
            gp.showDialogue("The barrier is sealed by a mysterious force.\nDefeat all enemies in the level to open it!");
        }
    }
    
    public boolean isOpened() {
        return opened;
    }
}
