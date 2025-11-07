package engine;

/**
 * LevelBuilder - Helper class with simplified methods for students to build levels
 * Provides easy-to-use methods for common level design patterns
 */
public class LevelBuilder {
    private final GameLevel level;
    
    public LevelBuilder(GameLevel level) {
        this.level = level;
    }
    
    /**
     * Create a border wall around the entire map
     * @param wallType Tile type for walls (usually 1)
     */
    public void createBorder(int wallType) {
        for (int x = 0; x < level.getMapWidth(); x++) {
            level.setTile(x, 0, wallType);  // Top
            level.setTile(x, level.getMapHeight() - 1, wallType);  // Bottom
        }
        for (int y = 0; y < level.getMapHeight(); y++) {
            level.setTile(0, y, wallType);  // Left
            level.setTile(level.getMapWidth() - 1, y, wallType);  // Right
        }
    }
    
    /**
     * Create a rectangular room
     * @param x Starting X position
     * @param y Starting Y position
     * @param width Room width
     * @param height Room height
     * @param floorType Tile type for floor
     * @param wallType Tile type for walls
     */
    public void createRoom(int x, int y, int width, int height, int floorType, int wallType) {
        // Fill floor
        level.fillRect(x, y, width, height, floorType);
        
        // Add walls
        for (int i = x; i < x + width; i++) {
            level.setTile(i, y, wallType);  // Top wall
            level.setTile(i, y + height - 1, wallType);  // Bottom wall
        }
        for (int j = y; j < y + height; j++) {
            level.setTile(x, j, wallType);  // Left wall
            level.setTile(x + width - 1, j, wallType);  // Right wall
        }
    }
    
    /**
     * Create a horizontal corridor
     * @param startX Starting X position
     * @param endX Ending X position
     * @param y Y position
     * @param pathType Tile type for path
     */
    public void createHorizontalCorridor(int startX, int endX, int y, int pathType) {
        for (int x = startX; x <= endX; x++) {
            level.setTile(x, y, pathType);
        }
    }
    
    /**
     * Create a vertical corridor
     * @param x X position
     * @param startY Starting Y position
     * @param endY Ending Y position
     * @param pathType Tile type for path
     */
    public void createVerticalCorridor(int x, int startY, int endY, int pathType) {
        for (int y = startY; y <= endY; y++) {
            level.setTile(x, y, pathType);
        }
    }
    
    /**
     * Create a door/opening in a wall
     * @param x X position
     * @param y Y position
     * @param floorType Tile type to place (makes it walkable)
     */
    public void createDoor(int x, int y, int floorType) {
        level.setTile(x, y, floorType);
    }
    
    /**
     * Create a checkerboard pattern
     * @param x Starting X
     * @param y Starting Y
     * @param width Width
     * @param height Height
     * @param type1 First tile type
     * @param type2 Second tile type
     */
    public void createCheckerboard(int x, int y, int width, int height, int type1, int type2) {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int tileType = ((i + j) % 2 == 0) ? type1 : type2;
                level.setTile(x + i, y + j, tileType);
            }
        }
    }
    
    /**
     * Create a circle of tiles
     * @param centerX Center X position
     * @param centerY Center Y position
     * @param radius Radius in tiles
     * @param tileType Tile type to use
     */
    public void createCircle(int centerX, int centerY, int radius, int tileType) {
        for (int x = centerX - radius; x <= centerX + radius; x++) {
            for (int y = centerY - radius; y <= centerY + radius; y++) {
                double distance = Math.sqrt(Math.pow(x - centerX, 2) + Math.pow(y - centerY, 2));
                if (distance <= radius) {
                    level.setTile(x, y, tileType);
                }
            }
        }
    }
    
    /**
     * Create a random scattered pattern (like rocks or trees)
     * @param x Starting X
     * @param y Starting Y
     * @param width Width of area
     * @param height Height of area
     * @param tileType Tile type to scatter
     * @param density Probability (0.0 to 1.0) of placing a tile
     */
    public void createScatteredTiles(int x, int y, int width, int height, int tileType, double density) {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (Math.random() < density) {
                    level.setTile(x + i, y + j, tileType);
                }
            }
        }
    }
    
    /**
     * Create a maze-like pattern
     * @param x Starting X
     * @param y Starting Y
     * @param width Width
     * @param height Height
     * @param wallType Wall tile type
     * @param pathType Path tile type
     */
    public void createMaze(int x, int y, int width, int height, int wallType, int pathType) {
        // Fill with walls
        level.fillRect(x, y, width, height, wallType);
        
        // Create paths in a grid pattern
        for (int i = 1; i < width; i += 2) {
            for (int j = 1; j < height; j += 2) {
                level.setTile(x + i, y + j, pathType);
                
                // Random direction for path
                if (Math.random() < 0.5 && i + 1 < width) {
                    level.setTile(x + i + 1, y + j, pathType);
                } else if (j + 1 < height) {
                    level.setTile(x + i, y + j + 1, pathType);
                }
            }
        }
    }
    
    /**
     * Fill background with a tile type (convenience method)
     * @param tileType Tile type to fill entire map with
     */
    public void fillBackground(int tileType) {
        level.fillRect(0, 0, level.getMapWidth(), level.getMapHeight(), tileType);
    }
    
    /**
     * Create a diagonal line
     * @param startX Starting X
     * @param startY Starting Y
     * @param endX Ending X
     * @param endY Ending Y
     * @param tileType Tile type
     */
    public void createDiagonalLine(int startX, int startY, int endX, int endY, int tileType) {
        int dx = Math.abs(endX - startX);
        int dy = Math.abs(endY - startY);
        int sx = startX < endX ? 1 : -1;
        int sy = startY < endY ? 1 : -1;
        int err = dx - dy;
        
        int x = startX;
        int y = startY;
        
        while (true) {
            level.setTile(x, y, tileType);
            
            if (x == endX && y == endY) break;
            
            int e2 = 2 * err;
            if (e2 > -dy) {
                err -= dy;
                x += sx;
            }
            if (e2 < dx) {
                err += dx;
                y += sy;
            }
        }
    }
}
