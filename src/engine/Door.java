package engine;

import java.awt.*;

/**
 * Door/Portal that transitions to another level
 */
public class Door {
    private int x, y;
    private int width = GamePanel.TILE_SIZE;
    private int height = GamePanel.TILE_SIZE;
    private String targetLevelName;
    private Color color;
    
    public Door(int x, int y, String targetLevelName, Color color) {
        this.x = x;
        this.y = y;
        this.targetLevelName = targetLevelName;
        this.color = color;
    }
    
    public void render(Graphics2D g2) {
        // Draw door with pulsing effect
        int pulse = (int) (Math.sin(System.currentTimeMillis() / 300.0) * 10 + 10);
        
        // Outer glow
        g2.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), 100));
        g2.fillRect(x - pulse/2, y - pulse/2, width + pulse, height + pulse);
        
        // Door
        g2.setColor(color);
        g2.fillRect(x, y, width, height);
        
        // Border
        g2.setColor(Color.WHITE);
        g2.drawRect(x, y, width, height);
        
        // Label
        g2.setFont(new Font("Arial", Font.BOLD, 10));
        g2.setColor(Color.WHITE);
        FontMetrics fm = g2.getFontMetrics();
        String text = targetLevelName;
        int textX = x + (width - fm.stringWidth(text)) / 2;
        int textY = y + height + 15;
        
        // Text shadow
        g2.setColor(Color.BLACK);
        g2.drawString(text, textX + 1, textY + 1);
        g2.setColor(Color.WHITE);
        g2.drawString(text, textX, textY);
    }
    
    public boolean checkPlayerCollision(Player player) {
        Rectangle doorBox = new Rectangle(x, y, width, height);
        return doorBox.intersects(player.getCollisionBox());
    }
    
    public String getTargetLevelName() {
        return targetLevelName;
    }
}
