package engine;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

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
    protected List<Enemy> enemies = new CopyOnWriteArrayList<>();
    
    // Projectiles in this level
    protected List<Projectile> projectiles = new CopyOnWriteArrayList<>();
    
    // Interactable objects in this level
    protected List<Interactable> interactables = new CopyOnWriteArrayList<>();
    
    // Start position for player
    protected int startX, startY;
    
    public void addProjectile(Projectile p) {
        projectiles.add(p);
    }
    
    // LevelBuilder helper for students
    protected LevelBuilder builder;
    
    // Level specific background music
    protected String bgmFilename = "overworld.wav";
    
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
    
    public void update() {
        for (Enemy enemy : enemies) {
            if (!enemy.isDefeated()) {
                enemy.update(gamePanel.getPlayer(), this);
            }
        }
        
        // Update projectiles
        for (int i = 0; i < projectiles.size(); i++) {
            Projectile p = projectiles.get(i);
            p.update(this, gamePanel.getPlayer());
            if (!p.isActive()) {
                projectiles.remove(i);
                i--;
            }
        }
    }
    
    public void render(Graphics2D g2) {
        // Draw tiles
        renderTiles(g2);
        
        // Draw interactables
        for (Interactable obj : interactables) {
            obj.render(g2);
        }
        
        // Draw projectiles
        for (Projectile p : projectiles) {
            p.render(g2);
        }
        
        // Draw enemies
        for (Enemy enemy : enemies) {
            if (!enemy.isDefeated()) {
                enemy.render(g2);
            }
        }
    }
    
    public ArrayList<Enemy> getEnemies() {
        return new ArrayList<>(enemies);
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
     * Check if a tile is solid for a specific entity, taking Flyable/Swimmable interfaces into account
     */
    public boolean isTileSolidForEntity(int tileX, int tileY, Entity entity) {
        // Out of bounds is solid for everyone
        if (tileX < 0 || tileX >= mapWidth || tileY < 0 || tileY >= mapHeight) {
            return true;
        }
        
        // Flyable check: can cross anything except outer map borders
        if (entity instanceof Flyable) {
            return tileX == 0 || tileX == mapWidth - 1 || tileY == 0 || tileY == mapHeight - 1;
        }
        
        int tileType = tileMap[tileX][tileY];
        
        // Swimmable check: can cross water (tileType 2) but not walls (tileType 1)
        if (entity instanceof Swimmable) {
            return tileType == 1;
        }
        
        // Default check: walls (1) and water (2) are solid
        return tileType == 1 || tileType == 2;
    }
    
    /**
     * Check collisions with level tiles
     */
    public void checkCollisions(Entity entity) {
        Rectangle entityBox = entity.getCollisionBox();
        
        // Check all four corners of entity's collision box
        int leftTile = (entityBox.x) / GamePanel.TILE_SIZE;
        int rightTile = (entityBox.x + entityBox.width) / GamePanel.TILE_SIZE;
        int topTile = (entityBox.y) / GamePanel.TILE_SIZE;
        int bottomTile = (entityBox.y + entityBox.height) / GamePanel.TILE_SIZE;
        
        // Check if any corner is in a solid tile
        if (isTileSolidForEntity(leftTile, topTile, entity) || isTileSolidForEntity(rightTile, topTile, entity) ||
            isTileSolidForEntity(leftTile, bottomTile, entity) || isTileSolidForEntity(rightTile, bottomTile, entity)) {
            entity.rollbackPosition();
            return;
        }
        
        // Check collision with solid interactables
        for (Interactable obj : interactables) {
            if (obj != entity && obj.isSolid() && entity.intersects(obj)) {
                entity.rollbackPosition();
                break;
            }
        }
    }
    
    protected void addInteractable(Interactable obj) {
        interactables.add(obj);
    }
    
    /**
     * Check if player touched an enemy (random encounter)
     */
    public Enemy checkEnemyEncounter(Player player) {
        for (Enemy enemy : enemies) {
            if (!enemy.isDefeated() && player.intersects(enemy)) {
                if (enemy.getEncounterType() == Enemy.EncounterType.OVERWORLD_ACTION) {
                    if (player.getInvincibilityFrames() == 0) {
                        player.takeDamage(enemy.getTouchDamage());
                        SoundManager.playSE("hit.wav");
                        System.out.println("Ouch! Touched overworld enemy: " + enemy.getName());
                    }
                    player.rollbackPosition();
                } else {
                    // TURN_BASED or HYBRID triggers turn-based screen
                    return enemy;
                }
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
     * Get the player instance
     */
    public Player getPlayer() {
        return gamePanel != null ? gamePanel.getPlayer() : null;
    }

    /**
     * Set a custom sprite for the player in this level
     * @param filename Sprite filename (e.g., "player.png")
     */
    public void setPlayerSprite(String filename) {
        Player player = getPlayer();
        if (player != null) {
            player.setCustomSprite(filename);
        }
    }

    /**
     * Set a custom sprite for the player using a color and shape
     * @param color Sprite color
     * @param shape "circle", "triangle", "diamond", "square"
     */
    public void setPlayerSprite(Color color, String shape) {
        Player player = getPlayer();
        if (player != null) {
            player.setCustomSprite(color, shape);
        }
    }

    /**
     * Revert the player sprite back to the default image/color
     */
    public void resetPlayerSprite() {
        Player player = getPlayer();
        if (player != null) {
            player.createDefaultSprite();
        }
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
    public String getBGMFilename() { return bgmFilename; }
    public String getLevelName() { return levelName; }
    public int getStartX() { return startX; }
    public int getStartY() { return startY; }
    public int getMapWidth() { return mapWidth; }
    public int getMapHeight() { return mapHeight; }
    public LevelBuilder getBuilder() { return builder; }
}
