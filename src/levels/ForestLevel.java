package levels;

import engine.*;
import enemies.*;
import java.awt.*;

/**
 * Example Level 1 - Forest Zone
 * Demonstrates a level with tile-based map
 */
public class ForestLevel extends GameLevel {
    
    public ForestLevel() {
        super("Forest Zone", 16, 12);  // 16x12 tiles (fits screen perfectly)
        startX = 100;
        startY = 100;
    }
    
    @Override
    public void setupMap() {
        // Fill entire map with grass (tile type 0)
        fillRect(0, 0, mapWidth, mapHeight, 0);
        
        // Create border walls (tile type 1)
        for (int x = 0; x < mapWidth; x++) {
            setTile(x, 0, 1);  // Top wall
            setTile(x, mapHeight - 1, 1);  // Bottom wall
        }
        for (int y = 0; y < mapHeight; y++) {
            setTile(0, y, 1);  // Left wall
            setTile(mapWidth - 1, y, 1);  // Right wall
        }
        
        // Add some obstacles (trees/rocks)
        setTile(5, 5, 1);
        setTile(6, 5, 1);
        setTile(10, 7, 1);
        setTile(11, 7, 1);
        setTile(8, 3, 1);
        
        // Add a small pond (tile type 2)
        setTile(12, 8, 2);
        setTile(13, 8, 2);
        setTile(12, 9, 2);
        setTile(13, 9, 2);
        
        // Add a path (tile type 3)
        for (int x = 2; x < 14; x++) {
            setTile(x, 6, 3);
        }
    }
    
    @Override
    public void setupEnemies() {
        // Place some enemies around the level
        Slime slime1 = new Slime();
        slime1.setPosition(200, 300);
        addEnemy(slime1);
        
        Slime slime2 = new Slime();
        slime2.setPosition(400, 200);
        addEnemy(slime2);
        
        Slime slime3 = new Slime();
        slime3.setPosition(600, 400);
        addEnemy(slime3);
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
