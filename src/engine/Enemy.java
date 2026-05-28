package engine;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Abstract Enemy class - extend this for custom enemies
 * POLYMORPHISM: Each enemy type has different implementation
 */
public abstract class Enemy extends Entity {
    protected int expReward;
    protected int goldReward;
    protected boolean defeated = false;
    private double runChance = .99;
    protected boolean invisible = false;
    protected int decisionCooldown = 0;
    protected Direction currentMoveDir = null;
    
    public enum AIBehavior {
        WANDER,
        CHASE
    }
    protected AIBehavior aiBehavior = AIBehavior.WANDER;
    
    public Enemy(String name, int maxHealth, int attackPower, int defense, int expReward, int goldReward, double runChance) {
        super(name, maxHealth, attackPower, defense);
        this.expReward = expReward;
        this.goldReward = goldReward;
        this.runChance = runChance;
        // Create default enemy sprite if not overridden
        if (sprite == null) {
            createDefaultSprite();
        }
    }
    
    protected void createDefaultSprite() {
        // Default enemy appearance (red square)
        sprite = SpriteManager.createColoredSprite(new Color(200, 50, 50));
    }
    
    /**
     * Set a custom sprite for the enemy
     * @param filename Sprite filename (e.g., "slime.png")
     */
    protected void setCustomSprite(String filename) {
        BufferedImage customSprite = SpriteManager.loadSprite(filename);
        if (customSprite != null) {
            sprite = customSprite;
        }
    }
    
    /**
     * Set a custom sprite using a color and shape
     * @param color Sprite color
     * @param shape "circle", "triangle", "diamond", "square"
     */
    protected void setCustomSprite(Color color, String shape) {
        sprite = SpriteManager.createShapeSprite(color, shape);
    }
    
    /**
     * Set sprite using SpriteManager with fallback
     * @param filename Sprite filename to try
     * @param fallbackColor Color to use if file not found
     */
    protected void setSpriteOrFallback(String filename, Color fallbackColor) {
        sprite = SpriteManager.getSpriteOrFallback(filename, fallbackColor);
    }
    
    /**
     * Define enemy AI behavior during battle
     * Override for unique strategies
     */
    public abstract String performBattleAction(Player player);
    
    /**
     * Get description shown in battle
     */
    public abstract String getDescription();
    
    /**
     * Called when enemy is defeated
     */
    public void onDefeat(Player player) {
        defeated = true;
        player.gainExperience(expReward);
        player.addGold(goldReward);
    }
    
    public void setPosition(int x, int y) {
        this.worldX = x;
        this.worldY = y;
    }
    
    public void update(Player player, GameLevel level) {
        // Save current position for collision rollback
        oldX = worldX;
        oldY = worldY;
        
        if (decisionCooldown <= 0) {
            if (aiBehavior == AIBehavior.CHASE) {
                currentMoveDir = findPathDirection(player, level);
                if (currentMoveDir == null) {
                    chooseBiasedDirection(player);
                }
            } else {
                chooseBiasedDirection(player);
            }
            decisionCooldown = 20 + (int)(Math.random() * 40); // 20 to 60 frames
        } else {
            decisionCooldown--;
        }
        
        // Move in the chosen direction
        if (currentMoveDir != null) {
            switch (currentMoveDir) {
                case UP:
                    worldY -= speed;
                    direction = Direction.UP;
                    break;
                case DOWN:
                    worldY += speed;
                    direction = Direction.DOWN;
                    break;
                case LEFT:
                    worldX -= speed;
                    direction = Direction.LEFT;
                    break;
                case RIGHT:
                    worldX += speed;
                    direction = Direction.RIGHT;
                    break;
            }
            animTick++;
            bobOffset = (int)(Math.sin(animTick * 0.25) * 4);
        } else {
            bobOffset = 0;
            animTick = 0;
        }
        
        // Check tile/interactable collisions
        level.checkCollisions(this);
        
        // If we collided and got rolled back, reset cooldown so we choose a new direction immediately
        if (worldX == oldX && worldY == oldY && currentMoveDir != null) {
            decisionCooldown = 0;
        }
    }
    
    /**
     * Pathfinding algorithm using Breadth-First Search (BFS) on tile coordinates
     * Returns the Direction of the first grid step towards the player.
     */
    private Direction findPathDirection(Player player, GameLevel level) {
        int startX = (this.worldX + GamePanel.TILE_SIZE / 2) / GamePanel.TILE_SIZE;
        int startY = (this.worldY + GamePanel.TILE_SIZE / 2) / GamePanel.TILE_SIZE;
        int targetX = (player.getWorldX() + GamePanel.TILE_SIZE / 2) / GamePanel.TILE_SIZE;
        int targetY = (player.getWorldY() + GamePanel.TILE_SIZE / 2) / GamePanel.TILE_SIZE;
        
        if (startX == targetX && startY == targetY) {
            return null;
        }
        
        int mapW = level.getMapWidth();
        int mapH = level.getMapHeight();
        
        if (startX < 0 || startX >= mapW || startY < 0 || startY >= mapH ||
            targetX < 0 || targetX >= mapW || targetY < 0 || targetY >= mapH) {
            return null;
        }
        
        java.util.Queue<int[]> queue = new java.util.LinkedList<>();
        int[][][] parent = new int[mapW][mapH][2];
        boolean[][] visited = new boolean[mapW][mapH];
        
        queue.add(new int[]{startX, startY});
        visited[startX][startY] = true;
        
        boolean found = false;
        int[] dirsX = {0, 0, -1, 1}; // UP, DOWN, LEFT, RIGHT
        int[] dirsY = {-1, 1, 0, 0};
        
        while (!queue.isEmpty()) {
            int[] curr = queue.poll();
            int cx = curr[0];
            int cy = curr[1];
            
            if (cx == targetX && cy == targetY) {
                found = true;
                break;
            }
            
            for (int i = 0; i < 4; i++) {
                int nx = cx + dirsX[i];
                int ny = cy + dirsY[i];
                
                if (nx >= 0 && nx < mapW && ny >= 0 && ny < mapH) {
                    if (!visited[nx][ny] && !level.isTileSolidForEntity(nx, ny, this)) {
                        visited[nx][ny] = true;
                        parent[nx][ny] = new int[]{cx, cy};
                        queue.add(new int[]{nx, ny});
                    }
                }
            }
        }
        
        if (!found) {
            return null; // No path found
        }
        
        // Backtrack path to find the first step from start
        int px = targetX;
        int py = targetY;
        while (true) {
            int[] par = parent[px][py];
            if (par[0] == startX && par[1] == startY) {
                int dx = px - startX;
                int dy = py - startY;
                if (dx == 1) return Direction.RIGHT;
                if (dx == -1) return Direction.LEFT;
                if (dy == 1) return Direction.DOWN;
                if (dy == -1) return Direction.UP;
                break;
            }
            px = par[0];
            py = par[1];
        }
        return null;
    }
    
    private void chooseBiasedDirection(Player player) {
        double weightUp = 1.0;
        double weightDown = 1.0;
        double weightLeft = 1.0;
        double weightRight = 1.0;
        double weightStop = 0.8; // Weight to remain stationary
        
        int dx = player.getWorldX() - this.worldX;
        int dy = player.getWorldY() - this.worldY;
        
        // Add bias towards the player's direction
        double bias = 1.5;
        if (dx > 0) {
            weightRight += bias;
        } else if (dx < 0) {
            weightLeft += bias;
        }
        
        if (dy > 0) {
            weightDown += bias;
        } else if (dy < 0) {
            weightUp += bias;
        }
        
        double totalWeight = weightUp + weightDown + weightLeft + weightRight + weightStop;
        double rand = Math.random() * totalWeight;
        
        if (rand < weightUp) {
            currentMoveDir = Direction.UP;
        } else if (rand < weightUp + weightDown) {
            currentMoveDir = Direction.DOWN;
        } else if (rand < weightUp + weightDown + weightLeft) {
            currentMoveDir = Direction.LEFT;
        } else if (rand < weightUp + weightDown + weightLeft + weightRight) {
            currentMoveDir = Direction.RIGHT;
        } else {
            currentMoveDir = null; // Stationary
        }
    }
    
    public void render(Graphics2D g2) {
        if (!defeated && !invisible && sprite != null) {
            g2.drawImage(sprite, worldX, worldY + bobOffset, null);
        }
    }
    
    // Getters
    public int getExpReward() { return expReward; }
    public int getGoldReward() { return goldReward; }
    public boolean isDefeated() { return defeated; }
    public int getWorldX() { return worldX; }
    public int getWorldY() { return worldY; }
    public double getRunChance() {return runChance; }
    public boolean isInvisible() { return invisible; }
    public void setInvisible(boolean invisible) { this.invisible = invisible; }
    public AIBehavior getAIBehavior() { return aiBehavior; }
    public void setAIBehavior(AIBehavior aiBehavior) { this.aiBehavior = aiBehavior; }
}
