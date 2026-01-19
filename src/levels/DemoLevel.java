package levels;

import engine.*;
import enemies.*;
import java.awt.*;

/**
 * Demo Level - Shows off LevelBuilder features
 * This level demonstrates all the easy building methods
 */
public class DemoLevel extends GameLevel {
    
    public DemoLevel() {
        super("Demo Level - LevelBuilder Showcase", 16, 12);
        startX = 100;
        startY = 100;
    }
    
    @Override
    public void setupMap() {
        // Fill background with grass
        builder.fillBackground(0);
        
        // Create border walls
        builder.createBorder(1);
        
        // Create a room in the center
        builder.createRoom(6, 4, 5, 4, 3, 1);
        builder.createDoor(8, 4, 0);  // Door at top
        
        // Create paths to the room
        builder.createVerticalCorridor(8, 1, 4, 3);
        
        // Add a pond using circle
        builder.createCircle(3, 9, 1, 2);
        
        // Scatter some obstacles (trees/rocks)
        builder.createScatteredTiles(11, 2, 4, 4, 1, 0.25);
        
        // Create a small checkerboard area
        builder.createCheckerboard(2, 2, 3, 3, 0, 3);
        
        // Optional: Use sprites for tiles (uncomment to try)
        // setTileSpriteOrColor(0, "tiles/grass.png", new Color(34, 139, 34));
        // setTileSpriteOrColor(1, "tiles/wall.png", new Color(139, 69, 19));
    }
    
    @Override
    public void setupEnemies() {
        // Add some enemies to battle
        Slime slime1 = new Slime();
        slime1.setPosition(250, 350);
        addEnemy(slime1);
        
        Bat bat1 = new Bat();
        bat1.setPosition(450, 250);
        addEnemy(bat1);
        
        Slime slime2 = new Slime();
        slime2.setPosition(550, 450);
        addEnemy(slime2);
    }
    
    @Override
    protected Color getTileColor(int tileType) {
        switch (tileType) {
            case 0: return new Color(34, 139, 34);    // Grass green
            case 1: return new Color(139, 69, 19);    // Wall brown
            case 2: return new Color(70, 130, 180);   // Water blue
            case 3: return new Color(210, 180, 140);  // Path tan
            default: return Color.GRAY;
        }
    }
}
