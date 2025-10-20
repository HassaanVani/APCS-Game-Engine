package core;

import gui.*;
import java.util.ArrayList;

/**
 * Main game engine for GUI version
 * Manages levels and game flow with Swing interface
 */
public class GameEngineGUI implements GUICallback {
    private Player player;
    private ArrayList<LevelGUI> levels;
    private int currentLevelIndex;
    private CombatSystemGUI combatSystem;
    private GameWindow window;
    
    public GameEngineGUI() {
        this.combatSystem = new CombatSystemGUI(this);
        this.levels = new ArrayList<>();
        this.currentLevelIndex = 0;
    }
    
    /**
     * Add a level to the game - POLYMORPHISM in action!
     */
    public void addLevel(LevelGUI level) {
        level.setGUICallback(this);
        levels.add(level);
    }
    
    /**
     * Start the game
     */
    public void start() {
        // Create GUI window
        window = new GameWindow(this);
        window.setVisible(true);
        
        // Show welcome screen
        displayWelcome();
    }
    
    private void displayWelcome() {
        displaySeparator();
        displayText("WELCOME TO THE TURN-BASED RPG ENGINE");
        displayText("A Polymorphism Learning Platform");
        displaySeparator();
        displayText("\nEnter your hero's name:");
        
        clearButtons();
        
        // Name input dialog
        javax.swing.SwingUtilities.invokeLater(() -> {
            String name = javax.swing.JOptionPane.showInputDialog(
                window, 
                "Enter your hero's name:", 
                "Character Creation",
                javax.swing.JOptionPane.QUESTION_MESSAGE
            );
            
            if (name == null || name.trim().isEmpty()) {
                name = "Hero";
            }
            
            player = new Player(name);
            displayText("\nWelcome, " + name + "!");
            updateStats();
            
            waitForButton("Begin Adventure", () -> {
                clearButtons();
                startNextLevel();
            });
        });
    }
    
    /**
     * Start the next level
     */
    private void startNextLevel() {
        if (currentLevelIndex >= levels.size()) {
            displayVictory();
            return;
        }
        
        if (!player.isAlive()) {
            displayText("\n\nGame Over.");
            return;
        }
        
        LevelGUI currentLevel = levels.get(currentLevelIndex);
        
        clearText();
        displayText("Progress: Level " + (currentLevelIndex + 1) + " of " + levels.size());
        displayText("");
        
        currentLevel.displayIntro();
        
        // Start level in separate thread to not block GUI
        new Thread(() -> {
            try {
                currentLevel.start(player);
                
                // Check if level was completed
                javax.swing.SwingUtilities.invokeLater(() -> {
                    if (currentLevel.isCompleted()) {
                        currentLevelIndex++;
                        if (currentLevelIndex < levels.size()) {
                            displayText("\nPreparing next level...");
                            waitForButton("Continue", () -> {
                                clearButtons();
                                startNextLevel();
                            });
                        } else {
                            displayVictory();
                        }
                    } else {
                        displayText("\nLevel failed. Game Over.");
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
    
    private void displayVictory() {
        clearText();
        displaySeparator();
        displayText("*** CONGRATULATIONS! ***");
        displayText("You have completed all levels!");
        displaySeparator();
        displayText("\nFinal Stats:");
        displayText(player.getName());
        displayText("Level: " + player.getLevel());
        displayText("HP: " + player.getHealth() + "/" + player.getMaxHealth());
        displayText("Attack: " + player.getAttackPower());
        displayText("Defense: " + player.getDefense());
        displayText("Gold: " + player.getGold());
        displaySeparator();
        
        waitForButton("Exit Game", () -> {
            System.exit(0);
        });
    }
    
    // GUICallback implementation
    
    @Override
    public void displayText(String text) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            window.getGamePanel().displayText(text);
        });
    }
    
    @Override
    public void clearText() {
        javax.swing.SwingUtilities.invokeLater(() -> {
            window.getGamePanel().clearText();
        });
    }
    
    @Override
    public void displaySeparator() {
        javax.swing.SwingUtilities.invokeLater(() -> {
            window.getGamePanel().displaySeparator();
        });
    }
    
    @Override
    public void waitForButton(String buttonText, Runnable callback) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            javax.swing.JButton button = window.getGamePanel().addButton(buttonText);
            button.addActionListener(e -> {
                callback.run();
            });
        });
    }
    
    @Override
    public void showChoices(String[] choices, ChoiceCallback callback) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            clearButtons();
            for (int i = 0; i < choices.length; i++) {
                final int index = i;
                javax.swing.JButton button = window.getGamePanel().addButton((i + 1) + ". " + choices[i]);
                button.addActionListener(e -> {
                    callback.onChoice(index);
                });
            }
        });
    }
    
    @Override
    public void updateStats() {
        javax.swing.SwingUtilities.invokeLater(() -> {
            window.updateStats(player);
        });
    }
    
    @Override
    public void clearButtons() {
        javax.swing.SwingUtilities.invokeLater(() -> {
            window.getGamePanel().clearButtons();
        });
    }
    
    // Getters
    public Player getPlayer() { return player; }
    public CombatSystemGUI getCombatSystem() { return combatSystem; }
}
