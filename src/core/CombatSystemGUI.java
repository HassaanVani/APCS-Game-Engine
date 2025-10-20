package core;

import gui.GUICallback;

/**
 * Handles turn-based combat for GUI version
 */
public class CombatSystemGUI {
    private GUICallback gui;
    private Player player;
    private Enemy enemy;
    private boolean playerTurn;
    private Runnable onCombatComplete;
    private boolean playerWon;
    
    public CombatSystemGUI(GUICallback gui) {
        this.gui = gui;
    }
    
    /**
     * Start a combat encounter
     * Callback is called when combat ends with true if player wins, false if loses
     */
    public void startCombat(Player player, Enemy enemy, CombatCallback callback) {
        this.player = player;
        this.enemy = enemy;
        this.playerTurn = true;
        
        gui.displaySeparator();
        gui.displayText("*** BATTLE START ***");
        gui.displayText(enemy.getDescription());
        gui.displayText(enemy.getName() + " - HP: " + enemy.getHealth() + "/" + enemy.getMaxHealth() + 
                       " | ATK: " + enemy.getAttackPower() + " | DEF: " + enemy.getDefense());
        gui.displaySeparator();
        
        this.onCombatComplete = () -> callback.onCombatEnd(playerWon);
        
        playerTurn();
    }
    
    /**
     * Handle player's turn in combat
     */
    private void playerTurn() {
        if (!player.isAlive() || !enemy.isAlive()) {
            endCombat();
            return;
        }
        
        gui.displayText("\n--- Your Turn ---");
        gui.updateStats();
        gui.clearButtons();
        
        String[] choices = {"Attack", "Use Item", "Defend"};
        gui.showChoices(choices, (choiceIndex) -> {
            gui.clearButtons();
            handlePlayerAction(choiceIndex);
        });
    }
    
    private void handlePlayerAction(int action) {
        switch (action) {
            case 0: // Attack
                int damage = player.attack();
                gui.displayText(player.getName() + " attacks!");
                enemy.takeDamage(damage);
                gui.displayText(enemy.getName() + " takes " + damage + " damage! (" + 
                              enemy.getHealth() + "/" + enemy.getMaxHealth() + " HP)");
                
                if (!enemy.isAlive()) {
                    endCombat();
                } else {
                    enemyTurn();
                }
                break;
                
            case 1: // Use Item
                if (player.getInventory().isEmpty()) {
                    gui.displayText("No items to use!");
                    playerTurn(); // Retry
                } else {
                    showItemSelection();
                }
                break;
                
            case 2: // Defend
                int originalDefense = player.getDefense();
                player.setDefense(originalDefense * 2);
                gui.displayText(player.getName() + " takes a defensive stance!");
                gui.displayText("Defense temporarily doubled!");
                enemyTurn();
                // Reset defense after enemy turn
                player.setDefense(originalDefense);
                break;
        }
    }
    
    private void showItemSelection() {
        gui.displayText("\n=== INVENTORY ===");
        String[] itemChoices = new String[player.getInventory().size() + 1];
        for (int i = 0; i < player.getInventory().size(); i++) {
            itemChoices[i] = player.getInventory().get(i).getName() + " - " + 
                            player.getInventory().get(i).getDescription();
        }
        itemChoices[player.getInventory().size()] = "Cancel";
        
        gui.showChoices(itemChoices, (choiceIndex) -> {
            gui.clearButtons();
            if (choiceIndex < player.getInventory().size()) {
                player.useItem(choiceIndex);
                gui.updateStats();
                if (!enemy.isAlive()) {
                    endCombat();
                } else {
                    enemyTurn();
                }
            } else {
                playerTurn(); // Cancel - retry turn
            }
        });
    }
    
    /**
     * Enemy's turn
     */
    private void enemyTurn() {
        if (!player.isAlive() || !enemy.isAlive()) {
            endCombat();
            return;
        }
        
        gui.displayText("\n--- Enemy Turn ---");
        enemy.takeTurn(player);
        gui.updateStats();
        
        if (!player.isAlive()) {
            endCombat();
        } else {
            // Continue to next player turn
            gui.waitForButton("Continue", () -> {
                gui.clearButtons();
                playerTurn();
            });
        }
    }
    
    /**
     * End combat and determine winner
     */
    private void endCombat() {
        if (enemy.isAlive()) {
            // Player died
            gui.displaySeparator();
            gui.displayText("*** DEFEAT ***");
            gui.displayText("You have been defeated...");
            gui.displaySeparator();
            playerWon = false;
        } else {
            // Player won
            enemy.onDefeat(player);
            gui.updateStats();
            playerWon = true;
        }
        
        gui.waitForButton("Continue", () -> {
            gui.clearButtons();
            if (onCombatComplete != null) {
                onCombatComplete.run();
            }
        });
    }
}
