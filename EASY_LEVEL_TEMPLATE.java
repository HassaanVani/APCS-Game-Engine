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
public class YourName_Level extends GameLevel {
    
    public YourName_Level() {
        super("Your Level Name", 16, 12);  // 16x12 tiles
        startX = 100;  // Where player starts (X position)
        startY = 100;  // Where player starts (Y position)
    }
    
    @Override
    public void setupMap() {
        // STEP 1: Fill background
        builder.fillBackground(0);  // 0 = grass/floor
        
        // STEP 2: Create border walls
        builder.createBorder(1);  // 1 = wall
        
        // STEP 3: Add your design!
        // Here are some easy methods you can use:
        
        // Create a room
        // builder.createRoom(3, 3, 6, 5, 0, 1);
        //   (x, y, width, height, floorType, wallType)
        
        // Create a path
        // builder.createHorizontalCorridor(2, 10, 6, 3);
        //   (startX, endX, y, pathType)
        
        // Create scattered obstacles (like trees or rocks)
        // builder.createScatteredTiles(2, 2, 12, 8, 1, 0.1);
        //   (x, y, width, height, tileType, density 0.0-1.0)
        
        // Create a circle (like a pond)
        // builder.createCircle(8, 6, 2, 2);
        //   (centerX, centerY, radius, tileType)
        
        // EXAMPLE: Simple level with a room and path
        builder.createRoom(5, 4, 6, 4, 3, 1);  // Room in center
        builder.createDoor(8, 4, 0);  // Door at top of room
        builder.createVerticalCorridor(8, 1, 4, 3);  // Path to door
    }
    
    @Override
    public void setupEnemies() {
        // Add enemies to your level
        // Don't place them at the start position!
        
        // EXAMPLE: Add a slime
        Slime slime = new Slime();
        slime.setPosition(300, 300);  // X, Y in pixels
        addEnemy(slime);
        
        // Add more enemies:
        // Slime slime2 = new Slime();
        // slime2.setPosition(400, 200);
        // addEnemy(slime2);
    }
    
    @Override
    protected Color getTileColor(int tileType) {
        // Define colors for your tiles
        switch (tileType) {
            case 0: return new Color(34, 139, 34);    // Green grass
            case 1: return new Color(139, 69, 19);    // Brown wall
            case 2: return new Color(70, 130, 180);   // Blue water
            case 3: return new Color(210, 180, 140);  // Tan path
            
            // Add your own colors:
            // case 4: return new Color(255, 100, 0);  // Orange lava
            // case 5: return new Color(200, 200, 255); // Light blue ice
            
            default: return Color.GRAY;
        }
    }
}

// ============================================================
// HELPFUL LEVELBUILDER METHODS
// ============================================================
//
// builder.fillBackground(tileType)
//   - Fill entire map with one tile type
//
// builder.createBorder(wallType)
//   - Create walls around the edge
//
// builder.createRoom(x, y, width, height, floorType, wallType)
//   - Create a rectangular room
//
// builder.createHorizontalCorridor(startX, endX, y, pathType)
//   - Create a horizontal path
//
// builder.createVerticalCorridor(x, startY, endY, pathType)
//   - Create a vertical path
//
// builder.createDoor(x, y, floorType)
//   - Create an opening in a wall
//
// builder.createCircle(centerX, centerY, radius, tileType)
//   - Create a circular area
//
// builder.createScatteredTiles(x, y, width, height, tileType, density)
//   - Randomly place tiles (density: 0.0 to 1.0)
//
// builder.createCheckerboard(x, y, width, height, type1, type2)
//   - Create a checkerboard pattern
//
// builder.createMaze(x, y, width, height, wallType, pathType)
//   - Create a simple maze
//
// builder.createDiagonalLine(startX, startY, endX, endY, tileType)
//   - Create a diagonal line
//
// ============================================================
// TILE TYPES (You can define your own!)
// ============================================================
//
// 0 = Grass/Floor (walkable)
// 1 = Wall (blocks movement)
// 2 = Water (blocks movement)
// 3 = Path (walkable)
// 4+ = Your custom tiles!
//
// ============================================================
