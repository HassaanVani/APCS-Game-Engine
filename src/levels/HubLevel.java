package levels;

import engine.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class HubLevel extends GameLevel {
    private static final int DOORS_PER_ROW = 4;
    private static final int DOOR_SPACING_X = 3;
    private static final int DOOR_SPACING_Y = 2;
    
    public HubLevel() {
        super("Central Hub", calculateWidth(), calculateHeight());
        startX = GamePanel.TILE_SIZE * 2;
        startY = GamePanel.TILE_SIZE * 2;
        bgmFilename = "hub.wav";
    }
    
    private static int calculateWidth() {
        int maxDoorX = 0;
        for (LevelRegistry.LevelEntry entry : LevelRegistry.getAll()) {
            if (entry.doorX > maxDoorX) {
                maxDoorX = entry.doorX;
            }
        }
        int levelCount = LevelRegistry.count();
        int cols = Math.min(levelCount, DOORS_PER_ROW);
        int gridWidth = 4 + cols * DOOR_SPACING_X;
        return Math.max(16, Math.max(gridWidth, maxDoorX + 3));
    }
    
    private static int calculateHeight() {
        int maxDoorY = 0;
        for (LevelRegistry.LevelEntry entry : LevelRegistry.getAll()) {
            if (entry.doorY > maxDoorY) {
                maxDoorY = entry.doorY;
            }
        }
        int levelCount = LevelRegistry.count();
        int rows = (int) Math.ceil(levelCount / (double) DOORS_PER_ROW);
        int gridHeight = 6 + rows * DOOR_SPACING_Y;
        return Math.max(12, Math.max(gridHeight, maxDoorY + 3));
    }
    
    @Override
    public void setupMap() {
        builder.fillBackground(3);
        builder.createBorder(1);
        
        int cols = Math.min(LevelRegistry.count(), DOORS_PER_ROW);
        for (int x = 2; x < 2 + cols * DOOR_SPACING_X; x++) {
            setTile(x, 1, 3);
        }
        
        setupDoors();
        
        // Starter chests in Central Hub
        Item sword = new Item("Training Sword", "An old wooden sword. Press C to swing.", Item.ItemType.SWORD, 0);
        Chest chestSword = new Chest(2 * GamePanel.TILE_SIZE, 3 * GamePanel.TILE_SIZE, sword);
        addInteractable(chestSword);
        
        Item bow = new Item("Short Bow", "A basic bow. Press X to shoot arrows.", Item.ItemType.BOW, 0);
        Chest chestBow = new Chest(4 * GamePanel.TILE_SIZE, 3 * GamePanel.TILE_SIZE, bow);
        addInteractable(chestBow);
        
        Item arrows = new Item("Quiver of Arrows", "Contains 15 arrows.", Item.ItemType.ARROW_AMMO, 15);
        Chest chestArrows = new Chest(6 * GamePanel.TILE_SIZE, 3 * GamePanel.TILE_SIZE, arrows);
        addInteractable(chestArrows);
        
        // Signpost next to the chests
        Signpost controlsSign = new Signpost(
            1 * GamePanel.TILE_SIZE,
            3 * GamePanel.TILE_SIZE,
            "=== ENGINE WEAPON GUIDE ===\n" +
            "1. Open the chests next to this sign!\n" +
            "2. Acquire the Sword and Bow.\n" +
            "3. Controls:\n" +
            "   - Press C: Swing Sword in the overworld!\n" +
            "   - Press X: Shoot Bow (requires arrows)!"
        );
        addInteractable(controlsSign);

        // Guide NPC next to the spawn point
        NPC guide = new NPC(
            "Guide Bob",
            3 * GamePanel.TILE_SIZE,
            2 * GamePanel.TILE_SIZE,
            new Color(255, 100, 100),
            "Welcome! Open the chests below to equip your sword and bow before entering a level.\n" +
            "Press C to slash overworld enemies directly.\n" +
            "Press X to shoot your bow."
        );
        addInteractable(guide);
    }
    
    private void setupDoors() {
        List<LevelRegistry.LevelEntry> levels = LevelRegistry.getAll();
        
        int gridIndex = 0;
        for (LevelRegistry.LevelEntry entry : levels) {
            int doorX;
            int doorY;
            
            if (entry.doorX >= 0 && entry.doorY >= 0) {
                // Use specified coordinates from annotation
                doorX = entry.doorX * GamePanel.TILE_SIZE;
                doorY = entry.doorY * GamePanel.TILE_SIZE;
            } else {
                // Fall back to grid positioning
                int col = gridIndex % DOORS_PER_ROW;
                int row = gridIndex / DOORS_PER_ROW;
                
                doorX = GamePanel.TILE_SIZE * (2 + col * DOOR_SPACING_X);
                doorY = GamePanel.TILE_SIZE * (4 + row * DOOR_SPACING_Y);
                gridIndex++;
            }
            
            Door door = new Door(doorX, doorY, entry.name, entry.doorColor);
            doors.add(door);
        }
    }
    
    @Override
    public void setupEnemies() {}
    
    @Override
    public void render(Graphics2D g2) {
        super.render(g2);
        
        g2.setFont(new Font("Arial", Font.BOLD, 16));
        String title = "LEVEL SELECT";
        FontMetrics fm = g2.getFontMetrics();
        int textX = (mapWidth * GamePanel.TILE_SIZE - fm.stringWidth(title)) / 2;
        
        g2.setColor(Color.BLACK);
        g2.drawString(title, textX + 2, GamePanel.TILE_SIZE + 22);
        g2.setColor(Color.WHITE);
        g2.drawString(title, textX, GamePanel.TILE_SIZE + 20);
        
        g2.setFont(new Font("Arial", Font.PLAIN, 12));
        String sub = "Walk into a door to enter | ESC to return here";
        fm = g2.getFontMetrics();
        textX = (mapWidth * GamePanel.TILE_SIZE - fm.stringWidth(sub)) / 2;
        g2.setColor(new Color(200, 200, 200));
        g2.drawString(sub, textX, GamePanel.TILE_SIZE + 38);
    }
    
    
    @Override
    protected Color getTileColor(int tileType) {
        switch (tileType) {
            case 0: return new Color(100, 100, 120);
            case 1: return new Color(50, 50, 60);
            case 2: return new Color(255, 215, 0);
            case 3: return new Color(70, 70, 90);
            default: return Color.GRAY;
        }
    }
}
