package engine;

import java.awt.Color;

/**
 * RestZone - An interactive zone that fully restores Player HP and MP.
 */
public class RestZone extends Interactable {
    private int hpRestoreAmount;
    private int mpRestoreAmount;
    
    public RestZone(int x, int y) {
        super("Rest Zone", x, y);
        this.hpRestoreAmount = 9999; // fully heal
        this.mpRestoreAmount = 9999; // fully restore MP
        
        // Visual representation: a blue circle shape
        this.sprite = SpriteManager.createShapeSprite(new Color(50, 200, 255), "circle");
        this.solid = false; // Player can stand on top of it to interact
    }
    
    public RestZone(int x, int y, int hpRestoreAmount, int mpRestoreAmount) {
        super("Rest Zone", x, y);
        this.hpRestoreAmount = hpRestoreAmount;
        this.mpRestoreAmount = mpRestoreAmount;
        
        this.sprite = SpriteManager.createShapeSprite(new Color(50, 200, 255), "circle");
        this.solid = false;
    }
    
    @Override
    public void onInteract(Player player, GamePanel gp) {
        player.heal(hpRestoreAmount);
        player.setMana(player.getMana() + mpRestoreAmount);
        SoundManager.playSE("heal.wav");
        gp.showDialogue("Rest Zone activated!\nHP and MP fully restored.");
    }
}
