// ============================================================
// EASY LEVEL TEMPLATE - Streamlined for Students
// Copy this file and customize it to create your own level!
// ============================================================

package levels;

import engine.*;
import enemies.*;
import java.awt.*;

/**
 * YOUR LEVEL NAME HERE
 * Example: VolcanoLevel, IceCaveLevel, DesertTempleLevel
 */
@RegisteredLevel(name = "Your Level Name", color = "#FF8C00", doorX = 4, doorY = 4)
public class YourName_Level extends GameLevel {
    
    public YourName_Level() {
        super("Your Level Name", 16, 12);  // 16x12 tiles (Set higher to make multi-screen maps!)
        startX = 100;  // Where player starts (X position)
        startY = 100;  // Where player starts (Y position)
    }
    
    @Override
    public void setupMap() {
        // STEP 1: Define Tile Sprites (Optional - Customize your graphics!)
        // Ensure you put custom PNG sprites in the project's "sprites/" folder.
        // setTileSprite(0, "grass.png");  // Set sprite for tile type 0
        // setTileSprite(1, "wall.png");   // Set sprite for tile type 1
        
        // STEP 2: Fill background
        builder.fillBackground(0);  // 0 = grass/floor
        
        // STEP 3: Create border walls
        builder.createBorder(1);  // 1 = wall
        
        // STEP 4: Add your design!
        // To build non-rectangular level shapes, place Void Tiles (type -1 or 9).
        // Void tiles automatically render as solid black blocks and do not display grid lines.
        // For example:
        // setTile(5, 5, -1); // Place a void tile
        
        // To make your level look extremely professional and earn bonus marks,
        // design a multi-screen level! Just increase the grid width and height
        // to multiples of 16 and 12 (e.g., 32x24 for a 2x2 screen, or 48x36 for 3x3).
        // The camera will automatically scroll/snap as you walk across screen edges!
        
        // EXAMPLE: Simple level with a room and path
        builder.createRoom(5, 4, 6, 4, 3, 1);  // Room in center
        builder.createDoor(8, 4, 0);  // Door at top of room
        builder.createVerticalCorridor(8, 1, 4, 3);  // Path to door
        
        // STEP 5: Add NPC abilities, chests, barriers and exit doors
        // Example A: Add a chest containing a health potion
        Item healthPotion = new Item("Health Potion", "Restores 50 HP. Use in battle.", Item.ItemType.POTION, 50);
        Chest potionChest = new Chest(
            12 * GamePanel.TILE_SIZE, 
            2 * GamePanel.TILE_SIZE, 
            healthPotion
        );
        addInteractable(potionChest);
        
        // Example B: Add a chest containing a mana (MP) potion
        Item manaPotion = new Item("Mana Potion", "Restores 15 MP. Use in battle.", Item.ItemType.POTION_MANA, 15);
        Chest manaChest = new Chest(
            14 * GamePanel.TILE_SIZE,
            2 * GamePanel.TILE_SIZE,
            manaPotion
        );
        addInteractable(manaChest);
        
        // Example C: Add a Rest Zone that fully restores HP and MP when interacted with
        RestZone restZone = new RestZone(
            8 * GamePanel.TILE_SIZE,
            6 * GamePanel.TILE_SIZE
        );
        addInteractable(restZone);
        
        // Example D: Add an exit door back to the Central Hub (acts as "end of level" marker)
        // Uses pixel coordinates (tile coordinate * TILE_SIZE).
        // By default, it falls back to a colored square. You can also specify a custom door sprite file:
        // Door exitDoor = new Door(x, y, "Central Hub", Color.GOLD, "my_door_sprite.png");
        Door exitDoor = new Door(
            14 * GamePanel.TILE_SIZE,
            9 * GamePanel.TILE_SIZE,
            "Central Hub",
            Color.GOLD
        );
        addDoor(exitDoor);

        // Example E: Add a barrier in front of the exit door
        // This blocks the player until ALL enemies in the level are defeated!
        EnemyBarrier barrier = new EnemyBarrier(
            13 * GamePanel.TILE_SIZE,
            9 * GamePanel.TILE_SIZE
        );
        addInteractable(barrier);
        
        // Example F: Add an NPC with custom robes and dialogue
        NPC villager = new NPC(
            "Villager Toby",
            10 * GamePanel.TILE_SIZE,
            9 * GamePanel.TILE_SIZE,
            Color.GREEN,
            "Beware of the bats nearby! Make sure you equip your Sword (C) and Bow (X) in the central hub before fighting!"
        );
        addInteractable(villager);
    }
    
    @Override
    public void setupEnemies() {
        // Add enemies to your level
        // Don't place them at the start position!
        
        // EXAMPLE: Add a slime
        Slime slime = new Slime();
        slime.setPosition(300, 300);  // X, Y in pixels
        addEnemy(slime);
    }
    
    @Override
    protected Color getTileColor(int tileType) {
        // Define colors for your tiles
        switch (tileType) {
            case 0: return new Color(34, 139, 34);    // Green grass
            case 1: return new Color(139, 69, 19);    // Brown wall
            case 2: return new Color(70, 130, 180);   // Blue water
            case 3: return new Color(210, 180, 140);  // Tan path
            default: return Color.GRAY;
        }
    }
}
