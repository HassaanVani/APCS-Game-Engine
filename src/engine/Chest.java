package engine;

import java.awt.Color;

/**
 * Chest - Interactive container holding an Item
 */
public class Chest extends Interactable {
    private Item itemInside;
    private boolean opened = false;
    
    public Chest(int x, int y, Item itemInside) {
        super("Treasure Chest", x, y);
        this.itemInside = itemInside;
        
        // Initial sprite: closed chest (orange diamond shape)
        this.sprite = SpriteManager.createShapeSprite(Color.ORANGE, "diamond");
    }
    
    @Override
    public void onInteract(Player player, GamePanel gp) {
        if (opened) {
            gp.showDialogue("This chest is already empty.");
        } else {
            opened = true;
            
            // Change sprite to open state color (dark gray box)
            this.sprite = SpriteManager.createShapeSprite(new Color(80, 80, 80), "diamond");
            
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
