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
public class StudentName_Level extends GameLevel {
    
    public StudentName_Level() {
        super("Your Level Name", 16, 12);  // 16x12 tiles (fits screen)
        
        // Set where player starts (in pixels)
        startX = 100;
        startY = 100;
    }
    
    @Override
    public void setupMap() {
        // TODO: Design your map here!
        
        // TILE TYPES:
        // 0 = Grass/Floor (walkable)
        // 1 = Wall/Obstacle (blocks movement)
        // 2 = Water (blocks movement)
        // 3 = Path (walkable)
        // 4+ = Custom tiles (you define colors below)
        
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
        
        // TIP: Draw your map on graph paper first!
    }
    
    @Override
    public void setupEnemies() {
        // TODO: Place your enemies!
        
        // Example: Add a slime enemy
        Slime slime = new Slime();
        slime.setPosition(200, 200);  // X, Y position in pixels
        addEnemy(slime);
        
        // Example: Add your custom enemy
        // YourEnemy enemy = new YourEnemy();
        // enemy.setPosition(400, 300);
        // addEnemy(enemy);
        
        // TIP: Don't place enemies too close to the start position!
    }
    
    @Override
    protected Color getTileColor(int tileType) {
        // Define colors for each tile type
        switch (tileType) {
            case 0: return new Color(34, 139, 34);    // Green grass
            case 1: return new Color(139, 69, 19);    // Brown wall
            case 2: return new Color(70, 130, 180);   // Blue water
            case 3: return new Color(210, 180, 140);  // Tan path
            
            // Add your custom tile colors:
            // case 4: return new Color(255, 0, 0);   // Red lava
            // case 5: return new Color(200, 200, 255); // Ice
            
            default: return Color.GRAY;
        }
    }
    
    // OPTIONAL: Override collision detection for special tiles
    /*
    @Override
    public boolean isTileSolid(int tileX, int tileY) {
        if (tileX < 0 || tileX >= mapWidth || tileY < 0 || tileY >= mapHeight) {
            return true; // Out of bounds
        }
        int tileType = tileMap[tileX][tileY];
        
        // Define which tiles block movement
        return tileType == 1 || tileType == 2; // Walls and water
    }
    */
}

// ============================================================
// TEMPLATE FOR CUSTOM ENEMIES
// ============================================================

/*
package enemies;

import engine.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class StudentName_Enemy extends Enemy {
    
    public StudentName_Enemy() {
        super(
            "Enemy Name",   // Name
            50,             // Max Health
            12,             // Attack Power
            5,              // Defense
            40,             // EXP Reward
            20              // Gold Reward
        );
        createCustomSprite();
    }
    
    private void createCustomSprite() {
        sprite = new BufferedImage(GamePanel.TILE_SIZE, GamePanel.TILE_SIZE, 
                                  BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = sprite.createGraphics();
        
        // Draw your enemy sprite here!
        // Example: A red square
        g2.setColor(Color.RED);
        g2.fillRect(8, 8, 32, 32);
        
        g2.dispose();
    }
    
    @Override
    public String performBattleAction(Player player) {
        // What does your enemy do in battle?
        int damage = attack();
        player.takeDamage(damage);
        return name + " attacks for " + damage + " damage!";
        
        // You can add special abilities:
        // if (Math.random() < 0.3) {
        //     return name + " missed!";
        // }
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
//    - Plan where enemies will be
//    - Think about the theme (forest, cave, desert, etc.)
//
// 2. MAP DESIGN:
//    - Always create border walls (prevents player from leaving)
//    - Leave open spaces for player to move
//    - Create paths and rooms
//    - Don't make it too crowded
//
// 3. ENEMY PLACEMENT:
//    - Don't place enemies at spawn point (startX, startY)
//    - Space them out
//    - Consider difficulty progression
//    - 2-5 enemies is good for one level
//
// 4. TILE COLORS:
//    - Use RGB colors: new Color(Red, Green, Blue)
//    - Values range from 0-255
//    - Make walkable/non-walkable tiles visually distinct
//
// 5. TESTING:
//    - Test your level frequently
//    - Make sure player can move around
//    - Check that enemies work
//    - Adjust difficulty as needed
//
// ============================================================
// LOADING CUSTOM SPRITES (ADVANCED)
// ============================================================
//
// To load an image file for tiles or enemies:
/*
import javax.imageio.ImageIO;
import java.io.File;

try {
    BufferedImage img = ImageIO.read(new File("sprites/mysprite.png"));
    // Use img as your sprite
} catch (Exception e) {
    e.printStackTrace();
}
*/
//
// Put your sprite images in a "sprites" folder in your project
// Sprites should be 48x48 pixels for best results
//
// ============================================================
// HELPER METHODS YOU CAN USE
// ============================================================
//
// setTile(x, y, tileType)          - Set one tile
// fillRect(x, y, width, height, tileType) - Fill rectangle
// addEnemy(enemy)                  - Add enemy to level
//
// ============================================================
