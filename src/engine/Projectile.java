package engine;

import java.awt.*;

/**
 * Projectile - represents a flying arrow shot from a bow
 */
public class Projectile {
    private int worldX, worldY;
    private int speed = 8;
    private int vx, vy; // direction multipliers (-1, 0, 1)
    private int damage = 10;
    private boolean active = true;
    private int size = 8;
    
    public Projectile(int startX, int startY, Entity.Direction direction, int damage) {
        this.worldX = startX;
        this.worldY = startY;
        this.damage = damage;
        
        switch (direction) {
            case UP:
                vx = 0; vy = -1;
                break;
            case DOWN:
                vx = 0; vy = 1;
                break;
            case LEFT:
                vx = -1; vy = 0;
                break;
            case RIGHT:
                vx = 1; vy = 0;
                break;
        }
    }
    
    public void update(GameLevel level, Player player) {
        worldX += vx * speed;
        worldY += vy * speed;
        
        // Check map boundaries
        int tileX = worldX / GamePanel.TILE_SIZE;
        int tileY = worldY / GamePanel.TILE_SIZE;
        if (tileX < 0 || tileX >= level.getMapWidth() || tileY < 0 || tileY >= level.getMapHeight()) {
            active = false;
            return;
        }
        
        // Check solid tile collision
        if (level.isTileSolid(tileX, tileY)) {
            active = false;
            return;
        }
        
        // Check enemy collision
        Rectangle collisionBox = getCollisionBox();
        for (Enemy enemy : level.getEnemies()) {
            if (!enemy.isDefeated() && 
                (enemy.getEncounterType() == Enemy.EncounterType.OVERWORLD_ACTION || 
                 enemy.getEncounterType() == Enemy.EncounterType.HYBRID)) {
                 
                if (enemy.getCollisionBox().intersects(collisionBox)) {
                    enemy.takeDamage(damage);
                    SoundManager.playSE("hit.wav");
                    active = false;
                    
                    if (!enemy.isAlive()) {
                        level.onEnemyDefeated(enemy);
                    }
                    break;
                }
            }
        }
    }
    
    public Rectangle getCollisionBox() {
        return new Rectangle(worldX, worldY, size, size);
    }
    
    public void render(Graphics2D g2) {
        if (!active) return;
        
        g2.setColor(Color.YELLOW);
        // Draw standard simple arrow shape
        if (vx != 0) {
            g2.fillRect(worldX, worldY + 2, 12, 4); // horizontal arrow body
            g2.fillRect(vx > 0 ? worldX + 8 : worldX, worldY, 4, 8); // head
        } else {
            g2.fillRect(worldX + 2, worldY, 4, 12); // vertical arrow body
            g2.fillRect(worldX, vy > 0 ? worldY + 8 : worldY, 8, 4); // head
        }
    }
    
    public boolean isActive() {
        return active;
    }
}
