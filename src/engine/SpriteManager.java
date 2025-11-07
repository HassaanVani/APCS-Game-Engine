package engine;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

/**
 * SpriteManager - Simplifies loading and managing sprites for students
 * Handles image loading, caching, and provides fallback colors
 */
public class SpriteManager {
    private static HashMap<String, BufferedImage> spriteCache = new HashMap<>();
    private static final String SPRITES_DIR = "sprites/";
    
    /**
     * Load a sprite from file with automatic caching
     * @param filename Name of the sprite file (e.g., "player.png")
     * @return BufferedImage or null if not found
     */
    public static BufferedImage loadSprite(String filename) {
        // Check cache first
        if (spriteCache.containsKey(filename)) {
            return spriteCache.get(filename);
        }
        
        try {
            BufferedImage sprite = ImageIO.read(new File(SPRITES_DIR + filename));
            spriteCache.put(filename, sprite);
            System.out.println("Loaded sprite: " + filename);
            return sprite;
        } catch (IOException e) {
            System.err.println("Could not load sprite: " + filename);
            return null;
        }
    }
    
    /**
     * Create a simple colored square sprite
     * @param color The color of the sprite
     * @param size Size in pixels (default: TILE_SIZE)
     * @return BufferedImage sprite
     */
    public static BufferedImage createColoredSprite(Color color, int size) {
        BufferedImage sprite = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = sprite.createGraphics();
        
        // Fill with color
        g2.setColor(color);
        g2.fillRect(4, 4, size - 8, size - 8);
        
        // Add border
        g2.setColor(Color.WHITE);
        g2.drawRect(4, 4, size - 8, size - 8);
        
        g2.dispose();
        return sprite;
    }
    
    /**
     * Create a simple colored square sprite with default TILE_SIZE
     */
    public static BufferedImage createColoredSprite(Color color) {
        return createColoredSprite(color, GamePanel.TILE_SIZE);
    }
    
    /**
     * Create a sprite with a simple shape
     * @param color Main color
     * @param shape "circle", "triangle", "diamond", "square"
     * @return BufferedImage sprite
     */
    public static BufferedImage createShapeSprite(Color color, String shape) {
        int size = GamePanel.TILE_SIZE;
        BufferedImage sprite = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = sprite.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        g2.setColor(color);
        int padding = 6;
        int drawSize = size - padding * 2;
        
        switch (shape.toLowerCase()) {
            case "circle":
                g2.fillOval(padding, padding, drawSize, drawSize);
                g2.setColor(Color.WHITE);
                g2.drawOval(padding, padding, drawSize, drawSize);
                break;
                
            case "triangle":
                int[] xPoints = {size/2, padding, size - padding};
                int[] yPoints = {padding, size - padding, size - padding};
                g2.fillPolygon(xPoints, yPoints, 3);
                g2.setColor(Color.WHITE);
                g2.drawPolygon(xPoints, yPoints, 3);
                break;
                
            case "diamond":
                int[] xDiamond = {size/2, padding, size/2, size - padding};
                int[] yDiamond = {padding, size/2, size - padding, size/2};
                g2.fillPolygon(xDiamond, yDiamond, 4);
                g2.setColor(Color.WHITE);
                g2.drawPolygon(xDiamond, yDiamond, 4);
                break;
                
            default: // square
                g2.fillRect(padding, padding, drawSize, drawSize);
                g2.setColor(Color.WHITE);
                g2.drawRect(padding, padding, drawSize, drawSize);
                break;
        }
        
        g2.dispose();
        return sprite;
    }
    
    /**
     * Create a tile sprite (for map tiles)
     * @param color Tile color
     * @param pattern Optional pattern: "grass", "brick", "stone", "water"
     * @return BufferedImage sprite
     */
    public static BufferedImage createTileSprite(Color color, String pattern) {
        int size = GamePanel.TILE_SIZE;
        BufferedImage sprite = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = sprite.createGraphics();
        
        // Fill base color
        g2.setColor(color);
        g2.fillRect(0, 0, size, size);
        
        // Add pattern
        g2.setColor(new Color(0, 0, 0, 30)); // Semi-transparent black
        
        switch (pattern.toLowerCase()) {
            case "grass":
                // Random grass blades
                for (int i = 0; i < 8; i++) {
                    int x = (int)(Math.random() * size);
                    int y = (int)(Math.random() * size);
                    g2.drawLine(x, y, x, y + 3);
                }
                break;
                
            case "brick":
                // Brick pattern
                g2.drawRect(0, 0, size, size/2);
                g2.drawRect(0, size/2, size/2, size/2);
                g2.drawRect(size/2, size/2, size/2, size/2);
                break;
                
            case "stone":
                // Stone texture
                g2.drawOval(5, 5, 10, 10);
                g2.drawOval(25, 15, 8, 8);
                g2.drawOval(15, 30, 12, 12);
                break;
                
            case "water":
                // Wave pattern
                for (int y = 0; y < size; y += 8) {
                    g2.drawLine(0, y, size, y);
                }
                break;
        }
        
        // Add subtle border
        g2.setColor(new Color(0, 0, 0, 50));
        g2.drawRect(0, 0, size - 1, size - 1);
        
        g2.dispose();
        return sprite;
    }
    
    /**
     * Create a simple tile sprite with just color
     */
    public static BufferedImage createTileSprite(Color color) {
        return createTileSprite(color, "none");
    }
    
    /**
     * Clear the sprite cache (useful for reloading)
     */
    public static void clearCache() {
        spriteCache.clear();
    }
    
    /**
     * Get a sprite or create a fallback colored square
     * @param filename Sprite filename to try loading
     * @param fallbackColor Color to use if file not found
     * @return BufferedImage (never null)
     */
    public static BufferedImage getSpriteOrFallback(String filename, Color fallbackColor) {
        BufferedImage sprite = loadSprite(filename);
        if (sprite == null) {
            return createColoredSprite(fallbackColor);
        }
        return sprite;
    }
}
