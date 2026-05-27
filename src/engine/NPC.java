package engine;

import java.awt.Color;

/**
 * NPC - Non-Player Character in the overworld that players can talk to
 */
public class NPC extends Interactable {
    private String dialogue;
    
    public NPC(String name, int x, int y, Color color, String dialogue) {
        super(name, x, y);
        this.dialogue = dialogue;
        
        // programmatically generate NPC appearance (circle shape)
        this.sprite = SpriteManager.createShapeSprite(color, "circle");
    }
    
    @Override
    public void onInteract(Player player, GamePanel gp) {
        gp.showDialogue(name + ":\n\"" + dialogue + "\"");
    }
}
