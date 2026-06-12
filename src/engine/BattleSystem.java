package engine;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Pokemon-style turn-based battle system
 */
public class BattleSystem {
    private GamePanel gamePanel;
    private Player player;
    private Enemy enemy;
    private BattleState state = BattleState.INTRO;
    private String message = "";
    private int messageTimer = 0;
    private int selectedAction = 0; // 0=Fight, 1=Item, 2=Run
    private int selectedItemIndex = 0;
    
    // Combat Overhaul moves & QTE properties
    public static class BattleMove {
        public String name;
        public int damagePower;
        public int mpCost;
        public double accuracy;
        
        public BattleMove(String name, int damagePower, int mpCost, double accuracy) {
            this.name = name;
            this.damagePower = damagePower;
            this.mpCost = mpCost;
            this.accuracy = accuracy;
        }
    }
    
    private static final BattleMove[] PLAYER_MOVES = {
        new BattleMove("Slash", 15, 0, 1.00),
        new BattleMove("Heavy Strike", 32, 5, 0.75),
        new BattleMove("Guard", 0, 0, 1.00),
        new BattleMove("Heal Spell", -30, 8, 1.00)
    };
    
    private int selectedMoveIndex = 0;
    private BattleMove currentChosenMove = null;
    private double qteValue = 0.0;
    private double qteSpeed = 0.06;
    private boolean playerGuarding = false;
    
    private enum BattleState {
        INTRO,
        PLAYER_TURN,
        MOVE_SELECT,
        QTE_INPUT,
        ITEM_SELECT,
        ENEMY_TURN,
        VICTORY,
        DEFEAT
    }
    
    public BattleSystem(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }
    
    public void startBattle(Player player, Enemy enemy) {
        this.player = player;
        this.enemy = enemy;
        this.state = BattleState.INTRO;
        this.message = "A wild " + enemy.getName() + " appeared!";
        this.messageTimer = 60;
        this.selectedAction = 0;
        this.playerGuarding = false;
        this.selectedMoveIndex = 0;
    }
    
    public void update() {
        if (messageTimer > 0) {
            messageTimer--;
            return;
        }
        
        KeyHandler keys = gamePanel.getKeyHandler();
        
        switch (state) {
            case INTRO:
                if (keys.spacePressed || keys.enterPressed) {
                    state = BattleState.PLAYER_TURN;
                    message = "What will you do?";
                    keys.spacePressed = false;
                    keys.enterPressed = false;
                }
                break;
                
            case PLAYER_TURN:
                // Navigate menu (FIGHT, ITEM, RUN)
                if (keys.leftPressed) {
                    selectedAction = (selectedAction - 1 + 3) % 3;
                    keys.leftPressed = false;
                } else if (keys.rightPressed) {
                    selectedAction = (selectedAction + 1) % 3;
                    keys.rightPressed = false;
                }
                
                // Select action
                if (keys.spacePressed || keys.enterPressed) {
                    keys.spacePressed = false;
                    keys.enterPressed = false;
                    
                    if (selectedAction == 0) {
                        // Go to move select state
                        state = BattleState.MOVE_SELECT;
                        selectedMoveIndex = 0;
                    } else if (selectedAction == 1) {
                        // Items
                        if (player.getInventory().isEmpty()) {
                            message = "No items in inventory!";
                            messageTimer = 45;
                        } else {
                            state = BattleState.ITEM_SELECT;
                            selectedItemIndex = 0;
                        }
                    } else {
                        // Run
                        if (Math.random() < enemy.getRunChance()) {
                            SoundManager.playSE("run.wav");
                            message = "Got away safely!";
                            messageTimer = 60;
                            gamePanel.endBattle(false);
                        } else {
                            SoundManager.playSE("hit.wav");
                            message = "Can't escape!";
                            messageTimer = 40;
                            state = BattleState.ENEMY_TURN;
                        }
                    }
                }
                break;
                
            case MOVE_SELECT:
                // Grid navigation for moves (2x2 grid)
                if (keys.upPressed || keys.downPressed) {
                    selectedMoveIndex ^= 1; // Toggle row (0<->1, 2<->3)
                    keys.upPressed = false;
                    keys.downPressed = false;
                } else if (keys.leftPressed || keys.rightPressed) {
                    selectedMoveIndex ^= 2; // Toggle col (0<->2, 1<->3)
                    keys.leftPressed = false;
                    keys.rightPressed = false;
                }
                
                // Cancel
                if (keys.escapePressed) {
                    state = BattleState.PLAYER_TURN;
                    message = "What will you do?";
                    keys.escapePressed = false;
                }
                
                // Confirm selection
                if (keys.spacePressed || keys.enterPressed) {
                    keys.spacePressed = false;
                    keys.enterPressed = false;
                    
                    BattleMove move = PLAYER_MOVES[selectedMoveIndex];
                    if (player.getMana() < move.mpCost) {
                        SoundManager.playSE("hit.wav");
                        message = "Not enough MP!";
                        messageTimer = 45;
                    } else {
                        player.useMana(move.mpCost);
                        currentChosenMove = move;
                        
                        if (move.name.equals("Guard")) {
                            playerGuarding = true;
                            SoundManager.playSE("buff.wav");
                            message = "You raised your shield! Defense doubled next turn.";
                            messageTimer = 60;
                            state = BattleState.ENEMY_TURN;
                        } else if (move.name.equals("Heal Spell")) {
                            player.heal(30);
                            SoundManager.playSE("heal.wav");
                            message = "Cast Heal Spell! Restored 30 HP.";
                            messageTimer = 60;
                            state = BattleState.ENEMY_TURN;
                        } else {
                            // Damage deal -> enter QTE timing
                            state = BattleState.QTE_INPUT;
                            qteValue = 0.0;
                            qteSpeed = move.name.equals("Slash") ? 0.05 : 0.08;
                        }
                    }
                }
                break;
                
            case QTE_INPUT:
                // Oscillate slider value
                qteValue += qteSpeed;
                if (qteValue > 1.0) {
                    qteValue = 1.0;
                    qteSpeed = -qteSpeed;
                } else if (qteValue < 0.0) {
                    qteValue = 0.0;
                    qteSpeed = -qteSpeed;
                }
                
                // Stop input
                if (keys.spacePressed || keys.enterPressed) {
                    keys.spacePressed = false;
                    keys.enterPressed = false;
                    
                    double diff = Math.abs(qteValue - 0.5);
                    double multiplier = 0.5;
                    String qteResult = "Weak Strike...";
                    
                    if (diff < 0.08) {
                        multiplier = 1.5;
                        qteResult = "CRITICAL HIT!";
                        SoundManager.playSE("victory.wav"); // critical hit sound
                    } else if (diff < 0.25) {
                        multiplier = 1.0;
                        qteResult = "Good Hit!";
                        SoundManager.playSE("hit.wav");
                    } else {
                        if (currentChosenMove.name.equals("Heavy Strike")) {
                            multiplier = 0.0;
                            qteResult = "Missed!";
                            SoundManager.playSE("hit.wav");
                        } else {
                            SoundManager.playSE("hit.wav");
                        }
                    }
                    
                    int finalDamage = (int)(currentChosenMove.damagePower * multiplier);
                    if (finalDamage > 0) {
                        int actualDamage = Math.max(1, finalDamage - enemy.getDefense());
                        enemy.takeDamage(finalDamage);
                        message = qteResult + " You dealt " + actualDamage + " damage!";
                    } else {
                        message = currentChosenMove.name + " " + qteResult;
                    }
                    
                    messageTimer = 60;
                    
                    if (!enemy.isAlive()) {
                        state = BattleState.VICTORY;
                        SoundManager.playSE("victory.wav");
                        message = enemy.getName() + " defeated! Gained " + enemy.getExpReward() + " EXP!";
                    } else {
                        state = BattleState.ENEMY_TURN;
                    }
                }
                break;
                
            case ITEM_SELECT:
                int itemCount = player.getInventory().size();
                
                // Scroll inventory
                if (keys.upPressed) {
                    selectedItemIndex = (selectedItemIndex - 1 + itemCount) % itemCount;
                    keys.upPressed = false;
                } else if (keys.downPressed) {
                    selectedItemIndex = (selectedItemIndex + 1) % itemCount;
                    keys.downPressed = false;
                }
                
                // Cancel
                if (keys.escapePressed) {
                    state = BattleState.PLAYER_TURN;
                    message = "What will you do?";
                    keys.escapePressed = false;
                }
                
                // Confirm Use
                if (keys.spacePressed || keys.enterPressed) {
                    keys.spacePressed = false;
                    keys.enterPressed = false;
                    
                    if (itemCount > 0) {
                        Item item = player.getInventory().get(selectedItemIndex);
                        item.use(player);
                        player.removeItem(item);
                        
                        // Play heal or stat SE
                        if (item.getType() == Item.ItemType.POTION) {
                            SoundManager.playSE("heal.wav");
                        } else {
                            SoundManager.playSE("buff.wav");
                        }
                        
                        message = "Used " + item.getName() + "!";
                        messageTimer = 60;
                        state = BattleState.ENEMY_TURN;
                    } else {
                        state = BattleState.PLAYER_TURN;
                    }
                }
                break;
                
            case ENEMY_TURN:
                enemyAttack();
                break;
                
            case VICTORY:
                if (keys.spacePressed || keys.enterPressed) {
                    gamePanel.endBattle(true);
                    keys.spacePressed = false;
                    keys.enterPressed = false;
                }
                break;
                
            case DEFEAT:
                if (keys.spacePressed || keys.enterPressed) {
                    gamePanel.endBattle(false);
                    keys.spacePressed = false;
                    keys.enterPressed = false;
                }
                break;
        }
    }
    
    private void playerAttack() {
        int damage = player.attack();
        int actualDamage = Math.max(1, damage - enemy.getDefense());
        enemy.takeDamage(damage);
        SoundManager.playSE("hit.wav");
        message = "You dealt " + actualDamage + " damage!";
        messageTimer = 60;
        
        if (!enemy.isAlive()) {
            state = BattleState.VICTORY;
            SoundManager.playSE("victory.wav");
            message = enemy.getName() + " defeated! Gained " + enemy.getExpReward() + " EXP!";
        } else {
            state = BattleState.ENEMY_TURN;
        }
    }
    
    private void enemyAttack() {
        int oldDefense = player.getDefense();
        if (playerGuarding) {
            player.increaseDefense(oldDefense);
        }
        
        String action = enemy.performBattleAction(player);
        SoundManager.playSE("hit.wav");
        message = action;
        messageTimer = 60;
        
        if (playerGuarding) {
            player.increaseDefense(-oldDefense);
            playerGuarding = false;
        }
        
        if (!player.isAlive()) {
            state = BattleState.DEFEAT;
            SoundManager.playSE("defeat.wav");
            message = "You were defeated!";
        } else {
            state = BattleState.PLAYER_TURN;
        }
    }
    
    public void render(Graphics2D g2) {
        // Draw battle background
        g2.setColor(new Color(40, 40, 60));
        g2.fillRect(0, 0, GamePanel.SCREEN_WIDTH, GamePanel.SCREEN_HEIGHT);
        
        // Draw enemy sprite (larger)
        if (enemy != null && enemy.getSprite() != null) {
            int enemyX = GamePanel.SCREEN_WIDTH - 200;
            int enemyY = 100;
            g2.drawImage(enemy.getSprite(), enemyX, enemyY, 
                        GamePanel.TILE_SIZE * 2, GamePanel.TILE_SIZE * 2, null);
            
            // Enemy health bar
            drawHealthBar(g2, enemy, enemyX, enemyY - 30);
        }
        
        // Draw player sprite
        if (player != null && player.getSprite() != null) {
            int playerX = 100;
            int playerY = GamePanel.SCREEN_HEIGHT - 250;
            g2.drawImage(player.getSprite(), playerX, playerY, 
                        GamePanel.TILE_SIZE * 2, GamePanel.TILE_SIZE * 2, null);
            
            // Player health bar (includes MP bar inside drawHealthBar)
            drawHealthBar(g2, player, playerX, playerY - 30);
        }
        
        // Draw message box
        drawMessageBox(g2);
        
        // Draw action menu if player's turn
        if (state == BattleState.PLAYER_TURN) {
            drawActionMenu(g2);
        }
        
        // Draw move selection menu if selecting move
        if (state == BattleState.MOVE_SELECT) {
            drawMoveMenu(g2);
        }
        
        // Draw inventory menu if selecting item
        if (state == BattleState.ITEM_SELECT) {
            drawInventoryMenu(g2);
        }
    }
    
    private void drawHealthBar(Graphics2D g2, Entity entity, int x, int y) {
        int barWidth = 150;
        int barHeight = 16;
        
        // Name
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.BOLD, 14));
        g2.drawString(entity.getName(), x, y - 5);
        
        // Background
        g2.setColor(Color.DARK_GRAY);
        g2.fillRect(x, y, barWidth, barHeight);
        
        // Health
        g2.setColor(Color.GREEN);
        int healthWidth = (int) ((entity.getHealth() / (double) entity.getMaxHealth()) * barWidth);
        g2.fillRect(x, y, healthWidth, barHeight);
        
        // Border
        g2.setColor(Color.WHITE);
        g2.drawRect(x, y, barWidth, barHeight);
        
        // Text
        g2.setFont(new Font("Arial", Font.PLAIN, 11));
        g2.setColor(Color.WHITE);
        String hpText = "HP: " + entity.getHealth() + "/" + entity.getMaxHealth();
        g2.drawString(hpText, x + 5, y + 12);
        
        // Draw MP bar if it's the Player
        if (entity instanceof Player) {
            Player p = (Player) entity;
            int mpY = y + 20;
            // Background
            g2.setColor(Color.DARK_GRAY);
            g2.fillRect(x, mpY, barWidth, barHeight);
            
            // Mana
            g2.setColor(new Color(50, 150, 255));
            int manaWidth = (int) ((p.getMana() / (double) p.getMaxMana()) * barWidth);
            g2.fillRect(x, mpY, manaWidth, barHeight);
            
            // Border
            g2.setColor(Color.WHITE);
            g2.drawRect(x, mpY, barWidth, barHeight);
            
            // Text
            g2.setColor(Color.WHITE);
            String mpText = "MP: " + p.getMana() + "/" + p.getMaxMana();
            g2.drawString(mpText, x + 5, mpY + 12);
        }
    }
    
    private void drawMessageBox(Graphics2D g2) {
        int boxX = 50;
        int boxY = GamePanel.SCREEN_HEIGHT - 150;
        int boxWidth = GamePanel.SCREEN_WIDTH - 100;
        int boxHeight = 100;
        
        // Box background
        g2.setColor(new Color(0, 0, 0, 200));
        g2.fillRect(boxX, boxY, boxWidth, boxHeight);
        
        // Border
        g2.setColor(Color.WHITE);
        g2.drawRect(boxX, boxY, boxWidth, boxHeight);
        
        if (state == BattleState.QTE_INPUT) {
            g2.setFont(new Font("Consolas", Font.BOLD, 14));
            g2.setColor(Color.WHITE);
            g2.drawString("TIME YOUR STRIKE! Press SPACE:", boxX + 20, boxY + 30);
            
            int sliderX = boxX + 20;
            int sliderY = boxY + 45;
            int sliderW = boxWidth - 40; // 628 wide
            int sliderH = 22;
            
            // Draw background bar (gray)
            g2.setColor(Color.DARK_GRAY);
            g2.fillRect(sliderX, sliderY, sliderW, sliderH);
            
            // Draw Good zone (yellow) - center 50%
            g2.setColor(new Color(220, 220, 50));
            g2.fillRect(sliderX + (int)(sliderW * 0.25), sliderY, (int)(sliderW * 0.50), sliderH);
            
            // Draw Critical zone (green) - center 16%
            g2.setColor(new Color(50, 200, 50));
            g2.fillRect(sliderX + (int)(sliderW * 0.42), sliderY, (int)(sliderW * 0.16), sliderH);
            
            // Draw border
            g2.setColor(Color.WHITE);
            g2.drawRect(sliderX, sliderY, sliderW, sliderH);
            
            // Draw moving cursor indicator
            int indicatorX = sliderX + (int)(qteValue * sliderW);
            g2.setColor(Color.RED);
            g2.setStroke(new BasicStroke(4));
            g2.drawLine(indicatorX, sliderY - 4, indicatorX, sliderY + sliderH + 4);
        } else if (state == BattleState.MOVE_SELECT) {
            // Background is drawn, choices drawn by drawMoveMenu.
        } else {
            // Message text
            g2.setFont(new Font("Consolas", Font.PLAIN, 18));
            g2.setColor(Color.WHITE);
            
            // Calculate max text width to avoid overlapping with buttons if they are shown
            int maxTextWidth = (state == BattleState.PLAYER_TURN) ? 320 : (boxWidth - 40);
            
            java.util.List<String> lines = GamePanel.wrapText(message, g2.getFontMetrics(), maxTextWidth);
            int textX = boxX + 20;
            int textY = boxY + 35;
            for (String line : lines) {
                g2.drawString(line, textX, textY);
                textY += 24;
            }
            
            // "Press SPACE to continue" hint
            if (state == BattleState.INTRO || state == BattleState.VICTORY || state == BattleState.DEFEAT) {
                g2.setFont(new Font("Consolas", Font.ITALIC, 11));
                g2.drawString("Press SPACE to continue", boxX + boxWidth - 175, boxY + boxHeight - 12);
            }
        }
    }
    
    private void drawMoveMenu(Graphics2D g2) {
        int boxX = 50;
        int boxY = GamePanel.SCREEN_HEIGHT - 150;
        
        g2.setFont(new Font("Consolas", Font.BOLD, 15));
        g2.setColor(Color.WHITE);
        
        for (int i = 0; i < PLAYER_MOVES.length; i++) {
            BattleMove move = PLAYER_MOVES[i];
            int col = i / 2;
            int row = i % 2;
            
            int x = boxX + 40 + col * 310;
            int y = boxY + 38 + row * 34;
            
            String text = move.name + " (" + move.mpCost + " MP)";
            if (i == selectedMoveIndex) {
                g2.setColor(new Color(100, 150, 255));
                g2.drawString("> " + text, x, y);
            } else {
                g2.setColor(Color.WHITE);
                g2.drawString("  " + text, x, y);
            }
        }
    }
    
    private void drawActionMenu(Graphics2D g2) {
        int boxX = 50;
        int boxY = GamePanel.SCREEN_HEIGHT - 150;
        int boxWidth = GamePanel.SCREEN_WIDTH - 100;
        int boxHeight = 100;
        
        int buttonWidth = 90;
        int buttonHeight = 40;
        int spacing = 10;
        
        // Align action menu on the right side of the bottom panel to prevent overlapping
        int menuX = boxX + boxWidth - 20 - (buttonWidth * 3 + spacing * 2); // 408
        int menuY = boxY + (boxHeight - buttonHeight) / 2; // 456
        
        // Fight button
        drawButton(g2, "FIGHT", menuX, menuY, buttonWidth, buttonHeight, selectedAction == 0);
        
        // Item button
        drawButton(g2, "ITEM", menuX + buttonWidth + spacing, menuY, buttonWidth, buttonHeight, selectedAction == 1);
        
        // Run button
        drawButton(g2, "RUN", menuX + (buttonWidth + spacing) * 2, menuY, buttonWidth, buttonHeight, selectedAction == 2);
    }
    
    private void drawInventoryMenu(Graphics2D g2) {
        int boxX = 50;
        int boxY = GamePanel.SCREEN_HEIGHT - 280;
        int boxWidth = 300;
        int boxHeight = 120;
        
        // Dark translucent inventory background
        g2.setColor(new Color(0, 0, 0, 220));
        g2.fillRect(boxX, boxY, boxWidth, boxHeight);
        
        // White border
        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(2));
        g2.drawRect(boxX, boxY, boxWidth, boxHeight);
        
        g2.setFont(new Font("Consolas", Font.BOLD, 14));
        g2.drawString("INVENTORY (ESC to back):", boxX + 15, boxY + 25);
        
        java.util.ArrayList<Item> inv = player.getInventory();
        int itemCount = inv.size();
        
        int startIdx = Math.max(0, selectedItemIndex - 2);
        int endIdx = Math.min(itemCount, startIdx + 3);
        
        int itemY = boxY + 50;
        for (int i = startIdx; i < endIdx; i++) {
            Item item = inv.get(i);
            if (i == selectedItemIndex) {
                g2.setColor(new Color(100, 150, 255));
                g2.drawString("> " + item.getName(), boxX + 20, itemY);
            } else {
                g2.setColor(Color.WHITE);
                g2.drawString("  " + item.getName(), boxX + 20, itemY);
            }
            itemY += 20;
        }
    }
    
    private void drawButton(Graphics2D g2, String text, int x, int y, int width, int height, boolean selected) {
        // Background
        if (selected) {
            g2.setColor(new Color(100, 150, 255));
        } else {
            g2.setColor(new Color(60, 60, 80));
        }
        g2.fillRect(x, y, width, height);
        
        // Border
        g2.setColor(Color.WHITE);
        g2.drawRect(x, y, width, height);
        
        // Text
        g2.setFont(new Font("Arial", Font.BOLD, 16));
        FontMetrics fm = g2.getFontMetrics();
        int textX = x + (width - fm.stringWidth(text)) / 2;
        int textY = y + (height + fm.getAscent()) / 2 - 2;
        g2.drawString(text, textX, textY);
    }
    
    public void handleMouseMove(int x, int y) {
        if (messageTimer > 0) return;
        
        switch (state) {
            case PLAYER_TURN:
                // Hover Fight
                if (x >= 408 && x <= 498 && y >= 456 && y <= 496) {
                    selectedAction = 0;
                }
                // Hover Item
                else if (x >= 508 && x <= 598 && y >= 456 && y <= 496) {
                    selectedAction = 1;
                }
                // Hover Run
                else if (x >= 608 && x <= 698 && y >= 456 && y <= 496) {
                    selectedAction = 2;
                }
                break;
                
            case MOVE_SELECT:
                int boxX = 50;
                int boxY = GamePanel.SCREEN_HEIGHT - 150;
                for (int i = 0; i < PLAYER_MOVES.length; i++) {
                    int col = i / 2;
                    int row = i % 2;
                    int xMin = boxX + 40 + col * 310;
                    int xMax = xMin + 250;
                    int yMin = boxY + 38 + row * 34 - 20;
                    int yMax = yMin + 30;
                    
                    if (x >= xMin && x <= xMax && y >= yMin && y <= yMax) {
                        selectedMoveIndex = i;
                        break;
                    }
                }
                break;
                
            case ITEM_SELECT:
                int invBoxX = 50;
                int invBoxY = GamePanel.SCREEN_HEIGHT - 280;
                java.util.ArrayList<Item> inv = player.getInventory();
                int itemCount = inv.size();
                int startIdx = Math.max(0, selectedItemIndex - 2);
                int endIdx = Math.min(itemCount, startIdx + 3);
                
                for (int i = startIdx; i < endIdx; i++) {
                    int slot = i - startIdx;
                    int xMin = invBoxX + 20;
                    int xMax = invBoxX + 280;
                    int yMin = invBoxY + 50 + slot * 20 - 15;
                    int yMax = yMin + 20;
                    
                    if (x >= xMin && x <= xMax && y >= yMin && y <= yMax) {
                        selectedItemIndex = i;
                        break;
                    }
                }
                break;
        }
    }
    
    public void handleMouseClick(int x, int y) {
        if (messageTimer > 0) return;
        
        switch (state) {
            case INTRO:
                state = BattleState.PLAYER_TURN;
                message = "What will you do?";
                break;
                
            case PLAYER_TURN:
                // Check if clicked FIGHT
                if (x >= 408 && x <= 498 && y >= 456 && y <= 496) {
                    selectedAction = 0;
                    state = BattleState.MOVE_SELECT;
                    selectedMoveIndex = 0;
                }
                // Check if clicked ITEM
                else if (x >= 508 && x <= 598 && y >= 456 && y <= 496) {
                    selectedAction = 1;
                    if (player.getInventory().isEmpty()) {
                        message = "No items in inventory!";
                        messageTimer = 45;
                    } else {
                        state = BattleState.ITEM_SELECT;
                        selectedItemIndex = 0;
                    }
                }
                // Check if clicked RUN
                else if (x >= 608 && x <= 698 && y >= 456 && y <= 496) {
                    selectedAction = 2;
                    if (Math.random() < enemy.getRunChance()) {
                        SoundManager.playSE("run.wav");
                        message = "Got away safely!";
                        messageTimer = 60;
                        gamePanel.endBattle(false);
                    } else {
                        SoundManager.playSE("hit.wav");
                        message = "Can't escape!";
                        messageTimer = 40;
                        state = BattleState.ENEMY_TURN;
                    }
                }
                break;
                
            case MOVE_SELECT:
                // Check if clicked on a move
                int boxX = 50;
                int boxY = GamePanel.SCREEN_HEIGHT - 150;
                boolean clickedMove = false;
                
                for (int i = 0; i < PLAYER_MOVES.length; i++) {
                    int col = i / 2;
                    int row = i % 2;
                    int xMin = boxX + 40 + col * 310;
                    int xMax = xMin + 250;
                    int yMin = boxY + 38 + row * 34 - 20;
                    int yMax = yMin + 30;
                    
                    if (x >= xMin && x <= xMax && y >= yMin && y <= yMax) {
                        selectedMoveIndex = i;
                        clickedMove = true;
                        
                        BattleMove move = PLAYER_MOVES[selectedMoveIndex];
                        if (player.getMana() < move.mpCost) {
                            SoundManager.playSE("hit.wav");
                            message = "Not enough MP!";
                            messageTimer = 45;
                        } else {
                            player.useMana(move.mpCost);
                            currentChosenMove = move;
                            
                            if (move.name.equals("Guard")) {
                                playerGuarding = true;
                                SoundManager.playSE("buff.wav");
                                message = "You raised your shield! Defense doubled next turn.";
                                messageTimer = 60;
                                state = BattleState.ENEMY_TURN;
                            } else if (move.name.equals("Heal Spell")) {
                                player.heal(30);
                                SoundManager.playSE("heal.wav");
                                message = "Cast Heal Spell! Restored 30 HP.";
                                messageTimer = 60;
                                state = BattleState.ENEMY_TURN;
                            } else {
                                state = BattleState.QTE_INPUT;
                                qteValue = 0.0;
                                qteSpeed = move.name.equals("Slash") ? 0.05 : 0.08;
                            }
                        }
                        break;
                    }
                }
                
                // If clicked outside the move box, cancel/go back
                if (!clickedMove) {
                    if (x < 50 || x > 718 || y < 426 || y > 526) {
                        state = BattleState.PLAYER_TURN;
                        message = "What will you do?";
                    }
                }
                break;
                
            case ITEM_SELECT:
                int invBoxX = 50;
                int invBoxY = GamePanel.SCREEN_HEIGHT - 280;
                int invBoxWidth = 300;
                int invBoxHeight = 120;
                
                java.util.ArrayList<Item> inv = player.getInventory();
                int itemCount = inv.size();
                int startIdx = Math.max(0, selectedItemIndex - 2);
                int endIdx = Math.min(itemCount, startIdx + 3);
                
                boolean clickedItem = false;
                for (int i = startIdx; i < endIdx; i++) {
                    int slot = i - startIdx;
                    int xMin = invBoxX + 20;
                    int xMax = invBoxX + 280;
                    int yMin = invBoxY + 50 + slot * 20 - 15;
                    int yMax = yMin + 20;
                    
                    if (x >= xMin && x <= xMax && y >= yMin && y <= yMax) {
                        selectedItemIndex = i;
                        clickedItem = true;
                        
                        Item item = inv.get(selectedItemIndex);
                        item.use(player);
                        player.removeItem(item);
                        
                        if (item.getType() == Item.ItemType.POTION) {
                            SoundManager.playSE("heal.wav");
                        } else {
                            SoundManager.playSE("buff.wav");
                        }
                        
                        message = "Used " + item.getName() + "!";
                        messageTimer = 60;
                        state = BattleState.ENEMY_TURN;
                        break;
                    }
                }
                
                // Cancel if click is outside the item select box
                if (!clickedItem) {
                    if (x < invBoxX || x > invBoxX + invBoxWidth || y < invBoxY || y > invBoxY + invBoxHeight) {
                        state = BattleState.PLAYER_TURN;
                        message = "What will you do?";
                    }
                }
                break;
                
            case QTE_INPUT:
                // Stop input on click (behaves like SPACE)
                double diff = Math.abs(qteValue - 0.5);
                double multiplier = 0.5;
                String qteResult = "Weak Strike...";
                
                if (diff < 0.08) {
                    multiplier = 1.5;
                    qteResult = "CRITICAL HIT!";
                    SoundManager.playSE("victory.wav");
                } else if (diff < 0.25) {
                    multiplier = 1.0;
                    qteResult = "Good Hit!";
                    SoundManager.playSE("hit.wav");
                } else {
                    if (currentChosenMove.name.equals("Heavy Strike")) {
                        multiplier = 0.0;
                        qteResult = "Missed!";
                        SoundManager.playSE("hit.wav");
                    } else {
                        SoundManager.playSE("hit.wav");
                    }
                }
                
                int finalDamage = (int)(currentChosenMove.damagePower * multiplier);
                if (finalDamage > 0) {
                    int actualDamage = Math.max(1, finalDamage - enemy.getDefense());
                    enemy.takeDamage(finalDamage);
                    message = qteResult + " You dealt " + actualDamage + " damage!";
                } else {
                    message = currentChosenMove.name + " " + qteResult;
                }
                
                messageTimer = 60;
                
                if (!enemy.isAlive()) {
                    state = BattleState.VICTORY;
                    SoundManager.playSE("victory.wav");
                    message = enemy.getName() + " defeated! Gained " + enemy.getExpReward() + " EXP!";
                } else {
                    state = BattleState.ENEMY_TURN;
                }
                break;
                
            case VICTORY:
                gamePanel.endBattle(true);
                break;
                
            case DEFEAT:
                gamePanel.endBattle(false);
                break;
        }
    }
    
    public Enemy getCurrentEnemy() {
        return enemy;
    }
}
