package engine;

import java.awt.Color;

/**
 * Chest - Interactive container holding an Item
 */
public class Chest extends Interactable {
    private Item itemInside;
    private boolean opened = false;
    
    private java.awt.image.BufferedImage spriteClosed;
    private java.awt.image.BufferedImage spriteOpen;
    
    public Chest(int x, int y, Item itemInside) {
        super("Treasure Chest", x, y);
        this.itemInside = itemInside;
        
        // Try to load custom sprites, fall back to shape sprites
        this.spriteClosed = SpriteManager.loadSprite("chest_closed.png");
        if (this.spriteClosed == null) {
            this.spriteClosed = SpriteManager.createShapeSprite(Color.ORANGE, "diamond");
        }
        
        this.spriteOpen = SpriteManager.loadSprite("chest_open.png");
        if (this.spriteOpen == null) {
            this.spriteOpen = SpriteManager.createShapeSprite(new Color(80, 80, 80), "diamond");
        }
        
        this.sprite = this.spriteClosed;
    }
    
    @Override
    public void onInteract(Player player, GamePanel gp) {
        if (opened) {
            gp.showDialogue("This chest is already empty.");
        } else {
            opened = true;
            
            // Change sprite to open state
            this.sprite = this.spriteOpen;
            
            // Add item to player inventory
            player.addItem(itemInside);
            
            // Play SFX
            SoundManager.playSE("chest.wav");
            
            gp.showDialogue("Opened the chest!\nFound: " + itemInside.getName() + "\n" + itemInside.getDescription());
        }
    }
    
    public boolean isOpened() {
        return opened;
    }
}
