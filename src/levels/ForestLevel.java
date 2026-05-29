package levels;

import engine.*;
import enemies.*;
import java.awt.*;

/**
 * Example Level 1 - Forest Zone
 * Demonstrates a level with tile-based map
 */
@RegisteredLevel(name = "Forest", color = "#228B22", doorX = 3, doorY = 4)
public class ForestLevel extends GameLevel {
    
    public ForestLevel() {
        super("Forest Zone", 16, 12);  // 16x12 tiles (fits screen perfectly)
        startX = 100;
        startY = 100;
        bgmFilename = "forest.wav";
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
        
        // Add interactables
        // 1. Signpost giving instructions
        Signpost sign = new Signpost(
            3 * GamePanel.TILE_SIZE, 
            4 * GamePanel.TILE_SIZE, 
            "=== DIRECTION SIGN ===\nFollow the path east to find the cavern.\nWatch out for the aggressive bats!"
        );
        addInteractable(sign);
        
        // 2. Treasure chest with a Sword
        Item sword = new Item("Training Sword", "An old wooden sword. Press C to swing.", Item.ItemType.SWORD, 0);
        Chest chestSword = new Chest(
            2 * GamePanel.TILE_SIZE, 
            2 * GamePanel.TILE_SIZE, 
            sword
        );
        addInteractable(chestSword);
        
        // 3. Treasure chest with a Bow
        Item bow = new Item("Short Bow", "A basic bow. Press X to shoot arrows.", Item.ItemType.BOW, 0);
        Chest chestBow = new Chest(
            2 * GamePanel.TILE_SIZE, 
            9 * GamePanel.TILE_SIZE, 
            bow
        );
        addInteractable(chestBow);
        
        // 4. Treasure chest with Arrow Ammo
        Item arrows = new Item("Quiver of Arrows", "Contains 10 arrows.", Item.ItemType.ARROW_AMMO, 10);
        Chest chestArrows = new Chest(
            14 * GamePanel.TILE_SIZE, 
            2 * GamePanel.TILE_SIZE, 
            arrows
        );
        addInteractable(chestArrows);
        
        // 5. Friendly Wizard NPC
        NPC wizard = new NPC(
            "Elder Eldrin",
            7 * GamePanel.TILE_SIZE,
            3 * GamePanel.TILE_SIZE,
            new Color(147, 112, 219), // Purple wizard robes
            "Greetings! Find weapons in the chests.\nWooden Slimes (green) can be slashed directly in the overworld (C).\nBlue Water Slimes are HYBRID: you can weaken them in the overworld,\nbut touching them starts turn-based combat!"
        );
        addInteractable(wizard);
    }
    
    @Override
    public void setupEnemies() {
        // Place some enemies around the level
        Slime slime1 = new Slime();
        slime1.setPosition(200, 300);
        slime1.setEncounterType(Enemy.EncounterType.OVERWORLD_ACTION);
        addEnemy(slime1);
        
        Slime slime2 = new Slime();
        slime2.setPosition(400, 200);
        slime2.setEncounterType(Enemy.EncounterType.HYBRID);
        addEnemy(slime2);
        
        Slime slime3 = new Slime();
        slime3.setPosition(600, 400);
        slime3.setInvisible(true);
        slime3.setEncounterType(Enemy.EncounterType.TURN_BASED);
        addEnemy(slime3);
        
        // Pathfinding Chase AI Bat
        Bat bat = new Bat();
        bat.setPosition(500, 300);
        bat.setAIBehavior(Enemy.AIBehavior.CHASE);
        bat.setEncounterType(Enemy.EncounterType.TURN_BASED);
        addEnemy(bat);
        
        // Swimmable Water Slime inside the pond
        WaterSlime waterSlime = new WaterSlime();
        waterSlime.setPosition(12 * GamePanel.TILE_SIZE, 8 * GamePanel.TILE_SIZE);
        waterSlime.setEncounterType(Enemy.EncounterType.HYBRID);
        addEnemy(waterSlime);
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
