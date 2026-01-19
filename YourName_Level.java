// ============================================================
// EASY LEVEL TEMPLATE - Copy this file to create your level!
// ============================================================
// 
// STEP 1: Copy this file to src/levels/
// STEP 2: Rename it to YourName_Level.java (e.g., John_Level.java)
// STEP 3: Change the class name to match the filename
// STEP 4: Customize your level below!
// STEP 5: Register in Levels.java (add ONE line - see bottom)
//
// ============================================================

package levels;

import engine.*;
import enemies.*;
import java.awt.*;

public class YourName_Level extends GameLevel {
    
    public YourName_Level() {
        super("Your Level Name", 16, 12);  // Name, Width, Height in tiles
        startX = 100;  // Player start X (pixels)
        startY = 100;  // Player start Y (pixels)
    }
    
    @Override
    public void setupMap() {
        builder.fillBackground(0);  // Fill with grass
        builder.createBorder(1);    // Add walls around edge
        
        // Add your design here! Examples:
        // builder.createRoom(3, 3, 6, 5, 0, 1);  // Room with floor 0, walls 1
        // builder.createCircle(8, 6, 2, 2);      // Circular pond
        // builder.createScatteredTiles(2, 2, 12, 8, 1, 0.1);  // Random rocks
        
        // Example: Simple room with path
        builder.createRoom(5, 4, 6, 4, 3, 1);
        builder.createDoor(8, 4, 0);
        builder.createVerticalCorridor(8, 1, 4, 3);
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

// ============================================================
// HOW TO REGISTER YOUR LEVEL
// ============================================================
//
// Open src/levels/Levels.java and add this line in registerAll():
//
//   LevelRegistry.register("Your Name's Level", new Color(R, G, B), YourName_Level::new);
//
// Example:
//   LevelRegistry.register("John's Dungeon", new Color(100, 50, 150), John_Level::new);
//
// That's it! Your level will appear in the hub automatically.
//
// ============================================================
// BUILDER METHODS QUICK REFERENCE
// ============================================================
//
// builder.fillBackground(tileType)
// builder.createBorder(wallType)
// builder.createRoom(x, y, width, height, floorType, wallType)
// builder.createHorizontalCorridor(startX, endX, y, pathType)
// builder.createVerticalCorridor(x, startY, endY, pathType)
// builder.createDoor(x, y, floorType)
// builder.createCircle(centerX, centerY, radius, tileType)
// builder.createScatteredTiles(x, y, width, height, tileType, density)
// builder.createCheckerboard(x, y, width, height, type1, type2)
// builder.createMaze(x, y, width, height, wallType, pathType)
//
// ============================================================
