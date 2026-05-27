package engine;

import java.awt.Color;

/**
 * Signpost - Simple interactive overworld object that displays static text
 */
public class Signpost extends Interactable {
    private String text;
    
    public Signpost(int x, int y, String text) {
        super("Signpost", x, y);
        this.text = text;
        
        // programmatically generate signpost sprite: brown square
        this.sprite = SpriteManager.createShapeSprite(new Color(139, 90, 43), "square");
    }
    
    @Override
    public void onInteract(Player player, GamePanel gp) {
        gp.showDialogue(text);
    }
}
