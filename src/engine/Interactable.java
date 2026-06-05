package engine;

import java.awt.*;

/**
 * Abstract Interactable class - extend this to create interactive overworld objects
 * Inherits from Entity to reuse position, collision boxes, and sprite management
 */
public abstract class Interactable extends Entity {
    protected boolean solid = true;
    
    public Interactable(String name, int x, int y) {
        // Interactables are entities but do not participate in standard battles (stats set to 1)
        super(name, 1, 0, 0);
        this.worldX = x;
        this.worldY = y;
        // Default solid area is a full 48x48 tile
        this.solidArea = new Rectangle(0, 0, GamePanel.TILE_SIZE, GamePanel.TILE_SIZE);
    }
    
    /**
     * Trigger interaction logic
     */
    public abstract void onInteract(Player player, GamePanel gp);
    
    /**
     * Optional update loop for interactable entities (e.g., barriers opening)
     */
    public void update(GameLevel level) {
        // Do nothing by default
    }
    
    public void render(Graphics2D g2) {
        if (sprite != null) {
            g2.drawImage(sprite, worldX, worldY, GamePanel.TILE_SIZE, GamePanel.TILE_SIZE, null);
        }
    }
    
    public boolean isSolid() {
        return solid;
    }
}
