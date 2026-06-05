// TEMPLATE FOR STUDENT LEVELS - 2D RPG VERSION
// Copy this file and replace "StudentName" with your actual name

package levels;

import engine.*;
import enemies.*; // Import your custom enemies
import java.awt.*;

/**
 * Your Level Name Here
 * Example: Volcano Zone, Ice Cave, Desert Temple, etc.
 */
@RegisteredLevel(name = "Your Level Name", color = "#4B0082", doorX = 6, doorY = 6)
public class StudentName_Level extends GameLevel {
    
    public StudentName_Level() {
        super("Your Level Name", 16, 12);  // 16x12 tiles (Set higher to make multi-screen snap maps!)
        
        // Set where player starts (in pixels)
        startX = 100;
        startY = 100;
    }
    
    @Override
    public void setupMap() {
        // TILE TYPES:
        // -1 or 9 = Void / Empty space (solid black, no grid lines drawn - useful for non-rectangular shape maps)
        // 0 = Grass/Floor (walkable)
        // 1 = Wall/Obstacle (blocks movement)
        // 2 = Water (blocks movement)
        // 3 = Path (walkable)
        // 4+ = Custom tiles (you define colors below)
        
        // TILE SPRITES & CUSTOM IMAGES:
        // You MUST place your custom sprite assets inside the project's "sprites/" directory!
        // Load custom tile textures like this:
        // setTileSprite(0, "grass.png");
        // setTileSprite(1, "wall.png");
        
        // MULTI-SCREEN SNAP CAMERAS:
        // Design multi-screen maps to make your project look highly polished and professional.
        // Set map width and height to multiples of 16 and 12 (e.g. super("My Level", 32, 24) for 2x2 screens).
        // The camera snaps to screens automatically as the player crosses boundaries!

        // Example: Fill entire map with grass
        fillRect(0, 0, mapWidth, mapHeight, 0);
        
        // Example: Create border walls
        for (int x = 0; x < mapWidth; x++) {
            setTile(x, 0, 1);  // Top wall
            setTile(x, mapHeight - 1, 1);  // Bottom wall
        }
        for (int y = 0; y < mapHeight; y++) {
            setTile(0, y, 1);  // Left wall
            setTile(mapWidth - 1, y, 1);  // Right wall
        }
        
        // Example: Add some obstacles
        setTile(5, 5, 1);
        setTile(6, 5, 1);
        
        // Example: Create a room
        fillRect(3, 3, 5, 5, 3);  // Floor
        setTile(3, 3, 1);  // Wall corners
        setTile(7, 3, 1);
        setTile(3, 7, 1);
        setTile(7, 7, 1);
        
        // ============================================================
        // CHESTS, ITEMS, BARRIERS, EXITS, AND NPC ABILITIES
        // ============================================================
        
        // 1. Create a chest with a training sword
        Item sword = new Item("Training Sword", "A basic wooden sword. Press C to swing.", Item.ItemType.SWORD, 0);
        Chest swordChest = new Chest(
            4 * GamePanel.TILE_SIZE, 
            4 * GamePanel.TILE_SIZE, 
            sword
        );
        addInteractable(swordChest);

        // 2. Create a chest with a mana (MP) potion
        Item manaPotion = new Item("Mana Potion", "Restores 15 MP. Use in battle.", Item.ItemType.POTION_MANA, 15);
        Chest manaChest = new Chest(
            4 * GamePanel.TILE_SIZE,
            6 * GamePanel.TILE_SIZE,
            manaPotion
        );
        addInteractable(manaChest);

        // 3. Create a Rest Zone (restores both HP and MP fully when interacted with)
        RestZone restZone = new RestZone(
            10 * GamePanel.TILE_SIZE,
            4 * GamePanel.TILE_SIZE
        );
        addInteractable(restZone);

        // 4. Create an exit door back to Central Hub (acts as "end of level" marker)
        // By default, it falls back to a colored square. You can also specify a custom door sprite file:
        // Door exitDoor = new Door(x, y, "Central Hub", Color.GOLD, "my_door_sprite.png");
        Door exitDoor = new Door(
            14 * GamePanel.TILE_SIZE,
            9 * GamePanel.TILE_SIZE,
            "Central Hub",
            Color.GOLD
        );
        addDoor(exitDoor);

        // 5. Create a barrier in front of the exit door
        // This blocks the player until ALL enemies in the level are defeated!
        EnemyBarrier barrier = new EnemyBarrier(
            13 * GamePanel.TILE_SIZE,
            9 * GamePanel.TILE_SIZE
        );
        addInteractable(barrier);
        
        // 6. Create an NPC wizard who speaks when interacted with
        NPC wizard = new NPC(
            "Elder Eldrin",
            6 * GamePanel.TILE_SIZE,
            4 * GamePanel.TILE_SIZE,
            new Color(147, 112, 219),
            "Greetings! Open the chest to acquire a Sword. Defeat the slimes!\n" +
            "Stand in the blue Rest Zone to fully restore your health and mana!"
        );
        addInteractable(wizard);
    }
    
    @Override
    public void setupEnemies() {
        // Example: Add a slime enemy
        Slime slime = new Slime();
        slime.setPosition(200, 200);  // X, Y position in pixels
        addEnemy(slime);
    }
    
    @Override
    protected Color getTileColor(int tileType) {
        // Define colors for each tile type
        switch (tileType) {
            case 0: return new Color(34, 139, 34);    // Green grass
            case 1: return new Color(139, 69, 19);    // Brown wall
            case 2: return new Color(70, 130, 180);   // Blue water
            case 3: return new Color(210, 180, 140);  // Tan path
            default: return Color.GRAY;
        }
    }
}

// ============================================================
// TEMPLATE FOR CUSTOM ENEMIES
// ============================================================

/*
package enemies;

import engine.*;
import java.awt.*;

public class StudentName_Enemy extends Enemy {
    
    public StudentName_Enemy() {
        super(
            "Enemy Name",   // Name
            50,             // Max Health
            12,             // Attack Power
            5,              // Defense
            40,             // EXP Reward
            20,             // Gold Reward
            0.5             // Run/escape chance
        );
        
        // Use a simple shape for appearance
        setCustomSprite(Color.RED, "circle");
    }
    
    @Override
    public String performBattleAction(Player player) {
        // Simple attack action during turn-based combat
        int damage = attack();
        player.takeDamage(damage);
        return name + " attacks for " + damage + " damage!";
    }
    
    @Override
    public String getDescription() {
        return "Describe your enemy here!";
    }
}
*/

// ============================================================
// TIPS FOR CREATING YOUR LEVEL
// ============================================================
//
// 1. PLANNING:
//    - Draw your map on graph paper (16x12 grid)
//    - Plan where enemies and NPCs will be
//    - Think about the theme (forest, cave, desert, etc.)
//
// 2. MAP DESIGN:
//    - Always create border walls (prevents player from leaving)
//    - Leave open spaces for player to move
//    - Create paths and rooms
//
// 3. ENEMY PLACEMENT:
//    - Don't place enemies at spawn point (startX, startY)
//    - Space them out
//
// 4. TESTING:
//    - Test your level frequently by walking around
//    - Check that chests give items and NPCs talk when you press SPACE/ENTER
//
// ============================================================
// LOADING CUSTOM SPRITES (ADVANCED)
// ============================================================
//
// To load an image file for players, tiles, or enemies:
// Use the SpriteManager to load, cache, and automatically scale the image:
//
//   sprite = SpriteManager.loadSprite("my_sprite.png");
//
// - Put your sprite images in the "sprites" folder in your project.
// - Sprites are automatically scaled to the correct tile size (48x48).
//
