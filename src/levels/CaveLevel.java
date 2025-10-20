package levels;

import engine.*;
import enemies.*;
import java.awt.*;

/**
 * Example Level 2 - Cave Zone
 * Shows different tileset and enemy placement
 */
public class CaveLevel extends GameLevel {
    
    public CaveLevel() {
        super("Cave Zone", 16, 12);
        startX = 100;
        startY = 500;
    }
    
    @Override
    public void setupMap() {
        // Fill with cave floor (darker ground)
        fillRect(0, 0, mapWidth, mapHeight, 4);
        
        // Create cave walls
        for (int x = 0; x < mapWidth; x++) {
            setTile(x, 0, 1);
            setTile(x, mapHeight - 1, 1);
        }
        for (int y = 0; y < mapHeight; y++) {
            setTile(0, y, 1);
            setTile(mapWidth - 1, y, 1);
        }
        
        // Create some rock formations
        fillRect(3, 3, 2, 3, 1);
        fillRect(12, 2, 2, 2, 1);
        fillRect(7, 8, 3, 2, 1);
        
        // Underground pools
        setTile(5, 9, 2);
        setTile(6, 9, 2);
        setTile(5, 10, 2);
        setTile(6, 10, 2);
    }
    
    @Override
    public void setupEnemies() {
        // Stronger enemies in the cave
        Bat bat1 = new Bat();
        bat1.setPosition(300, 150);
        addEnemy(bat1);
        
        Bat bat2 = new Bat();
        bat2.setPosition(500, 350);
        addEnemy(bat2);
        
        // Mix in some slimes
        Slime slime = new Slime();
        slime.setPosition(600, 200);
        addEnemy(slime);
    }
    
    @Override
    protected Color getTileColor(int tileType) {
        switch (tileType) {
            case 0: return new Color(40, 40, 40);     // Dark floor
            case 1: return new Color(80, 80, 80);     // Cave walls (gray)
            case 2: return new Color(30, 60, 100);    // Dark water
            case 4: return new Color(50, 50, 50);     // Cave floor
            default: return Color.GRAY;
        }
    }
}
