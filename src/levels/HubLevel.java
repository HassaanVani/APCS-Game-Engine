package levels;

import engine.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Hub/Corridor Level - Main area where players can enter different level rooms
 * This is where all levels connect!
 */
public class HubLevel extends GameLevel {
    private ArrayList<Door> doors = new ArrayList<>();
    
    public HubLevel() {
        super("Central Hub", 16, 12);
        startX = GamePanel.TILE_SIZE * 2;
        startY = GamePanel.TILE_SIZE * 6;
    }
    
    @Override
    public void setupMap() {
        // Create a long corridor
        // Fill with floor tiles
        fillRect(0, 0, mapWidth, mapHeight, 3);
        
        // Top and bottom walls
        for (int x = 0; x < mapWidth; x++) {
            setTile(x, 0, 1);
            setTile(x, 1, 1);
            setTile(x, 10, 1);
            setTile(x, 11, 1);
        }
        
        // Side walls
        for (int y = 0; y < mapHeight; y++) {
            setTile(0, y, 1);
            setTile(15, y, 1);
        }
        
        // Add decorative elements
        setTile(3, 3, 2);
        setTile(3, 8, 2);
        setTile(12, 3, 2);
        setTile(12, 8, 2);
        
        // Setup doors to student levels
        setupDoors();
    }
    
    private void setupDoors() {
        // Door to Forest Level (left side)
        Door forestDoor = new Door(
            GamePanel.TILE_SIZE * 4,
            GamePanel.TILE_SIZE * 5,
            "Forest",
            new Color(34, 139, 34)  // Green
        );
        doors.add(forestDoor);
        
        // Door to Cave Level (middle)
        Door caveDoor = new Door(
            GamePanel.TILE_SIZE * 8,
            GamePanel.TILE_SIZE * 5,
            "Cave",
            new Color(80, 80, 80)   // Gray
        );
        doors.add(caveDoor);
        
        Door studentDoor1 = new Door(
            GamePanel.TILE_SIZE * 12,
            GamePanel.TILE_SIZE * 5,
            "Student 1",
            new Color(200, 100, 255)
        );
        doors.add(studentDoor1);
    }
    
    @Override
    public void setupEnemies() {
        // No enemies in the hub - this is a safe zone
    }
    
    @Override
    public void render(Graphics2D g2) {
        // Render the map first
        super.render(g2);
        
        // Then render doors on top
        for (Door door : doors) {
            door.render(g2);
        }
        
        // Draw instructions at top
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.BOLD, 14));
        String instructions = "Walk into a door to enter that level!";
        FontMetrics fm = g2.getFontMetrics();
        int textX = (GamePanel.SCREEN_WIDTH - fm.stringWidth(instructions)) / 2;
        g2.setColor(Color.BLACK);
        g2.drawString(instructions, textX + 2, 32);
        g2.setColor(Color.WHITE);
        g2.drawString(instructions, textX, 30);
    }
    
    /**
     * Check if player is touching a door
     */
    public Door checkDoorCollision(Player player) {
        for (Door door : doors) {
            if (door.checkPlayerCollision(player)) {
                return door;
            }
        }
        return null;
    }
    
    @Override
    protected Color getTileColor(int tileType) {
        switch (tileType) {
            case 0: return new Color(100, 100, 120);  // Stone floor
            case 1: return new Color(60, 60, 70);     // Dark walls
            case 2: return new Color(255, 215, 0);    // Gold decorations
            case 3: return new Color(80, 80, 100);    // Corridor floor
            default: return Color.GRAY;
        }
    }
}
