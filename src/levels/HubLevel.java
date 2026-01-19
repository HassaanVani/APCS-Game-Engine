package levels;

import engine.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class HubLevel extends GameLevel {
    private ArrayList<Door> doors = new ArrayList<>();
    private static final int DOORS_PER_ROW = 4;
    private static final int DOOR_SPACING_X = 3;
    private static final int DOOR_SPACING_Y = 2;
    
    public HubLevel() {
        super("Central Hub", calculateWidth(), calculateHeight());
        startX = GamePanel.TILE_SIZE * 2;
        startY = GamePanel.TILE_SIZE * 2;
    }
    
    private static int calculateWidth() {
        int levelCount = LevelRegistry.count();
        int cols = Math.min(levelCount, DOORS_PER_ROW);
        return Math.max(16, 4 + cols * DOOR_SPACING_X);
    }
    
    private static int calculateHeight() {
        int levelCount = LevelRegistry.count();
        int rows = (int) Math.ceil(levelCount / (double) DOORS_PER_ROW);
        return Math.max(12, 6 + rows * DOOR_SPACING_Y);
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
    }
    
    private void setupDoors() {
        List<LevelRegistry.LevelEntry> levels = LevelRegistry.getAll();
        
        for (int i = 0; i < levels.size(); i++) {
            LevelRegistry.LevelEntry entry = levels.get(i);
            
            int col = i % DOORS_PER_ROW;
            int row = i / DOORS_PER_ROW;
            
            int doorX = GamePanel.TILE_SIZE * (2 + col * DOOR_SPACING_X);
            int doorY = GamePanel.TILE_SIZE * (4 + row * DOOR_SPACING_Y);
            
            Door door = new Door(doorX, doorY, entry.name, entry.doorColor);
            doors.add(door);
        }
    }
    
    @Override
    public void setupEnemies() {}
    
    @Override
    public void render(Graphics2D g2) {
        super.render(g2);
        
        for (Door door : doors) {
            door.render(g2);
        }
        
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
            case 0: return new Color(100, 100, 120);
            case 1: return new Color(50, 50, 60);
            case 2: return new Color(255, 215, 0);
            case 3: return new Color(70, 70, 90);
            default: return Color.GRAY;
        }
    }
}
