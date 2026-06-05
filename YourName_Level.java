// ============================================================
// EASY LEVEL TEMPLATE - Copy this file to create your level!
// ============================================================
// 
// STEP 1: Copy this file to src/levels/
// STEP 2: Rename it to YourName_Level.java (e.g., John_Level.java)
// STEP 3: Change the class name to match the filename
// STEP 4: Customize your level below!
//
// ============================================================

package levels;

import engine.*;
import enemies.*;
import java.awt.*;

// Set your custom level attributes here. doorX and doorY determine where the entry door appears in the Hub Level.
// Leaving out the @RegisteredLevel annotation or copying old levels will fallback to auto-registration.
@RegisteredLevel(name = "Your Level Name", color = "#4682B4", doorX = 5, doorY = 5)
public class YourName_Level extends GameLevel {
    
    public YourName_Level() {
        super("Your Level Name", 16, 12);  // Name, Width, Height in tiles (Set higher for multi-screen snap maps!)
        startX = 100;  // Player start X (pixels)
        startY = 100;  // Player start Y (pixels)
    }
    
    @Override
    public void setupMap() {
        // TILE SPRITES & CUSTOM IMAGES:
        // You MUST place your custom sprite assets inside the project's "sprites/" directory!
        // To use custom tile textures, load them like this:
        // setTileSprite(0, "grass.png");
        // setTileSprite(1, "wall.png");

        builder.fillBackground(0);  // Fill with grass
        builder.createBorder(1);    // Add walls around edge
        
        // NONRECTANGULAR LAYOUTS (VOID TILES):
        // To create caves, islands, or custom non-rectangular layouts, use Void Tiles (type -1 or 9).
        // Void tiles automatically render as solid black blocks and do not display grid lines.
        // For example:
        // setTile(4, 4, -1);
        
        // MULTI-SCREEN SNAP CAMERAS:
        // Design multi-screen maps to make your project look highly polished and professional.
        // Set map width and height to multiples of 16 and 12 (e.g. super("My Level", 32, 24) for 2x2 screens).
        // The camera snaps to screens automatically as the player crosses boundaries!
        
        // Example: Simple room with path
        builder.createRoom(5, 4, 6, 4, 3, 1);
        builder.createDoor(8, 4, 0);
        builder.createVerticalCorridor(8, 1, 4, 3);
        
        // ============================================================
        // INTERACTABLE ABILITIES (NPCs, Chests, Barriers, and Exit Doors)
        // ============================================================
        
        // Example 1: Chest containing a Sword
        Item trainingSword = new Item("Training Sword", "A basic wooden sword. Press C to swing.", Item.ItemType.SWORD, 0);
        Chest swordChest = new Chest(
            2 * GamePanel.TILE_SIZE, 
            2 * GamePanel.TILE_SIZE, 
            trainingSword
        );
        addInteractable(swordChest);

        // Example 2: Chest containing a Mana Potion (restores MP)
        Item manaPotion = new Item("Mana Potion", "Restores 15 MP. Use in battle.", Item.ItemType.POTION_MANA, 15);
        Chest manaChest = new Chest(
            14 * GamePanel.TILE_SIZE,
            2 * GamePanel.TILE_SIZE,
            manaPotion
        );
        addInteractable(manaChest);

        // Example 3: Rest Zone (restores both HP and MP fully when interacted with)
        RestZone restZone = new RestZone(
            2 * GamePanel.TILE_SIZE,
            9 * GamePanel.TILE_SIZE
        );
        addInteractable(restZone);
        
        // Example 4: Exit Door back to Central Hub (acts as "end of level" marker)
        // By default, it falls back to a colored square. You can also specify a custom door sprite file:
        // Door exitDoor = new Door(x, y, "Central Hub", Color.GOLD, "my_door_sprite.png");
        Door exitDoor = new Door(
            14 * GamePanel.TILE_SIZE,
            9 * GamePanel.TILE_SIZE,
            "Central Hub",
            Color.GOLD
        );
        addDoor(exitDoor);

        // Example 5: Enemy Barrier (solid block that opens after all slimes/bats in the level are defeated)
        EnemyBarrier barrier = new EnemyBarrier(
            13 * GamePanel.TILE_SIZE,
            9 * GamePanel.TILE_SIZE
        );
        addInteractable(barrier);
        
        // Example 6: Talkative NPC
        NPC wizard = new NPC(
            "Elder Eldrin",
            7 * GamePanel.TILE_SIZE,
            3 * GamePanel.TILE_SIZE,
            new Color(147, 112, 219), // Color of the NPC avatar
            "Welcome, hero! Remember to equip your Sword and Bow in the Hub Level first.\n" +
            "Press C to swing sword, X to shoot bow. Stand in the blue Rest Zone to fully heal!"
        );
        addInteractable(wizard);
    }
    
    @Override
    public void setupEnemies() {
        // Add enemies - don't place at player start!
        Slime slime = new Slime();
        slime.setPosition(300, 300);
        addEnemy(slime);
    }
    
    @Override
    protected Color getTileColor(int tileType) {
        switch (tileType) {
            case 0: return new Color(34, 139, 34);    // Green grass
            case 1: return new Color(139, 69, 19);    // Brown wall
            case 2: return new Color(70, 130, 180);   // Blue water
            case 3: return new Color(210, 180, 140);  // Tan path
            default: return Color.GRAY;
        }
    }
}
