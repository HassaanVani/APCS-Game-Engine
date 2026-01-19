package levels;

import engine.*;
import enemies.*;
import java.awt.*;

/**
 * Example Multi-Screen Level - Large Forest
 * Demonstrates Pokemon-style screen transitions
 * This level is 2 screens wide x 2 screens tall (32x24 tiles)
 */
public class LargeForestLevel extends GameLevel {
    
    public LargeForestLevel() {
        super("Large Forest", 32, 24);  // 2x2 screens
        startX = 100;
        startY = 100;
    }
    
    @Override
    public void setupMap() {
        // Fill entire map with grass
        builder.fillBackground(0);
        
        // Create border walls around entire map
        builder.createBorder(1);
        
        // SCREEN 1 (top-left): Starting area with scattered trees
        builder.createScatteredTiles(2, 2, 13, 9, 1, 0.15);
        
        // SCREEN 2 (top-right): Dense forest
        builder.createScatteredTiles(18, 2, 12, 9, 1, 0.35);
        
        // SCREEN 3 (bottom-left): Pond area
        builder.createCircle(8, 16, 3, 2);  // Large pond
        builder.createScatteredTiles(2, 13, 13, 9, 1, 0.1);
        
        // SCREEN 4 (bottom-right): Clearing with path
        builder.createRoom(20, 15, 8, 6, 3, 1);  // Clearing
        builder.createHorizontalCorridor(16, 27, 18, 3);  // Path to clearing
        
        // Connect areas with paths
        builder.createVerticalCorridor(8, 2, 22, 3);  // Vertical path through middle
        builder.createHorizontalCorridor(2, 28, 12, 3);  // Horizontal path
    }
    
    @Override
    public void setupEnemies() {
        // Spread enemies across all screens
        
        // Screen 1 (top-left)
        Slime slime1 = new Slime();
        slime1.setPosition(200, 200);
        addEnemy(slime1);
        
        // Screen 2 (top-right)
        Bat bat1 = new Bat();
        bat1.setPosition(900, 150);
        addEnemy(bat1);
        
        Slime slime2 = new Slime();
        slime2.setPosition(1100, 300);
        addEnemy(slime2);
        
        // Screen 3 (bottom-left)
        Bat bat2 = new Bat();
        bat2.setPosition(300, 800);
        addEnemy(bat2);
        
        // Screen 4 (bottom-right)
        Slime slime3 = new Slime();
        slime3.setPosition(1000, 900);
        addEnemy(slime3);
        
        Bat bat3 = new Bat();
        bat3.setPosition(1200, 1000);
        addEnemy(bat3);
    }
    
    @Override
    protected Color getTileColor(int tileType) {
        switch (tileType) {
            case 0: return new Color(60, 179, 113);   // Forest green
            case 1: return new Color(101, 67, 33);    // Tree brown
            case 2: return new Color(64, 164, 223);   // Water blue
            case 3: return new Color(189, 183, 107);  // Path
            default: return Color.GRAY;
        }
    }
}
