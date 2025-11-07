package engine;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Abstract GameLevel class - THE KEY POLYMORPHISM CONCEPT
 * Each student creates their own level by extending this class
 * The game engine can load and run any level that extends this
 */
public abstract class GameLevel {
    protected String levelName;
    protected GamePanel gamePanel;
    
    // Level dimensions (in tiles)
    protected int mapWidth;
    protected int mapHeight;
    
    // Tile map (2D array of tile types)
    protected int[][] tileMap;
    
    // Tile sprites (optional - can use sprites instead of colors)
    protected HashMap<Integer, BufferedImage> tileSprites = new HashMap<>();
    
    // Enemies in this level
    protected ArrayList<Enemy> enemies = new ArrayList<>();
    
    // Start position for player
    protected int startX, startY;
    
    // LevelBuilder helper for students
    protected LevelBuilder builder;
    
    public GameLevel(String levelName, int mapWidth, int mapHeight) {
        this.levelName = levelName;
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
        this.tileMap = new int[mapWidth][mapHeight];
        this.builder = new LevelBuilder(this);
    }
    
    /**
     * Initialize the level map layout
     * POLYMORPHISM: Each level has different implementation
     */
    public abstract void setupMap();
    
    /**
     * Add enemies to the level
     */
    public abstract void setupEnemies();
    
    /**
     * Called by game engine to initialize everything
     */
    public void initialize(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        setupMap();
        setupEnemies();
    }
    
    /**
     * Render the level (tiles, enemies, etc)
     */
    public void render(Graphics2D g2) {
        // Draw tiles
        renderTiles(g2);
        
        // Draw enemies
        for (Enemy enemy : enemies) {
            if (!enemy.isDefeated()) {
                enemy.render(g2);
            }
        }
    }
    
    /**
     * Render all tiles on the map
     */
    protected void renderTiles(Graphics2D g2) {
        for (int x = 0; x < mapWidth; x++) {
            for (int y = 0; y < mapHeight; y++) {
                int tileType = tileMap[x][y];
                
                // Check if sprite exists for this tile type
                if (tileSprites.containsKey(tileType)) {
                    BufferedImage sprite = tileSprites.get(tileType);
                    g2.drawImage(sprite, x * GamePanel.TILE_SIZE, y * GamePanel.TILE_SIZE, null);
                } else {
                    // Fall back to color rendering
                    Color tileColor = getTileColor(tileType);
                    g2.setColor(tileColor);
                    g2.fillRect(x * GamePanel.TILE_SIZE, y * GamePanel.TILE_SIZE, 
                               GamePanel.TILE_SIZE, GamePanel.TILE_SIZE);
                }
                
                // Draw grid lines
                g2.setColor(new Color(0, 0, 0, 50));
                g2.drawRect(x * GamePanel.TILE_SIZE, y * GamePanel.TILE_SIZE, 
                           GamePanel.TILE_SIZE, GamePanel.TILE_SIZE);
            }
        }
    }
    
    /**
     * Get color for tile type - can be overridden for custom tiles
     */
    protected Color getTileColor(int tileType) {
        switch (tileType) {
            case 0: return new Color(34, 139, 34);   // Grass (green)
            case 1: return new Color(139, 69, 19);   // Wall (brown)
            case 2: return new Color(70, 130, 180);  // Water (blue)
            case 3: return new Color(210, 180, 140); // Path (tan)
            default: return Color.GRAY;
        }
    }
    
    /**
     * Check if a tile is solid (blocks movement)
     * Override to define walkable areas
     */
    public boolean isTileSolid(int tileX, int tileY) {
        if (tileX < 0 || tileX >= mapWidth || tileY < 0 || tileY >= mapHeight) {
            return true; // Out of bounds
        }
        int tileType = tileMap[tileX][tileY];
        return tileType == 1 || tileType == 2; // Walls and water are solid
    }
    
    /**
     * Check collisions with level tiles
     */
    public void checkCollisions(Player player) {
        Rectangle playerBox = player.getCollisionBox();
        
        // Check all four corners of player's collision box
        int leftTile = (playerBox.x) / GamePanel.TILE_SIZE;
        int rightTile = (playerBox.x + playerBox.width) / GamePanel.TILE_SIZE;
        int topTile = (playerBox.y) / GamePanel.TILE_SIZE;
        int bottomTile = (playerBox.y + playerBox.height) / GamePanel.TILE_SIZE;
        
        // Check if any corner is in a solid tile
        if (isTileSolid(leftTile, topTile) || isTileSolid(rightTile, topTile) ||
            isTileSolid(leftTile, bottomTile) || isTileSolid(rightTile, bottomTile)) {
            player.rollbackPosition();
        }
    }
    
    /**
     * Check if player touched an enemy (random encounter)
     */
    public Enemy checkEnemyEncounter(Player player) {
        for (Enemy enemy : enemies) {
            if (!enemy.isDefeated() && player.intersects(enemy)) {
                return enemy;
            }
        }
        return null;
    }
    
    /**
     * Called when an enemy is defeated
     */
    public void onEnemyDefeated(Enemy enemy) {
        enemy.onDefeat(gamePanel.getPlayer());
    }
    
    /**
     * Add an enemy to this level
     */
    protected void addEnemy(Enemy enemy) {
        enemies.add(enemy);
    }
    
    /**
     * Set tile at position
     */
    protected void setTile(int x, int y, int tileType) {
        if (x >= 0 && x < mapWidth && y >= 0 && y < mapHeight) {
            tileMap[x][y] = tileType;
        }
    }
    
    /**
     * Fill rectangle with tile type
     */
    protected void fillRect(int x, int y, int width, int height, int tileType) {
        for (int i = x; i < x + width && i < mapWidth; i++) {
            for (int j = y; j < y + height && j < mapHeight; j++) {
                setTile(i, j, tileType);
            }
        }
    }
    
    /**
     * Set a sprite for a tile type (optional - for image-based tiles)
     * @param tileType The tile type number
     * @param sprite The sprite image to use
     */
    protected void setTileSprite(int tileType, BufferedImage sprite) {
        tileSprites.put(tileType, sprite);
    }
    
    /**
     * Set a sprite for a tile type using SpriteManager
     * @param tileType The tile type number
     * @param filename Sprite filename
     */
    protected void setTileSprite(int tileType, String filename) {
        BufferedImage sprite = SpriteManager.loadSprite(filename);
        if (sprite != null) {
            tileSprites.put(tileType, sprite);
        }
    }
    
    /**
     * Set a sprite for a tile type with fallback color
     * @param tileType The tile type number
     * @param filename Sprite filename to try
     * @param fallbackColor Color to use if sprite not found
     */
    protected void setTileSpriteOrColor(int tileType, String filename, Color fallbackColor) {
        BufferedImage sprite = SpriteManager.getSpriteOrFallback(filename, fallbackColor);
        tileSprites.put(tileType, sprite);
    }
    
    // Getters
    public String getLevelName() { return levelName; }
    public int getStartX() { return startX; }
    public int getStartY() { return startY; }
    public int getMapWidth() { return mapWidth; }
    public int getMapHeight() { return mapHeight; }
    public LevelBuilder getBuilder() { return builder; }
}
