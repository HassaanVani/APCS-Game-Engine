package core;

import java.util.Scanner;

/**
 * Handles turn-based combat between player and enemies
 */
public class CombatSystem {
    private Scanner scanner;
    
    public CombatSystem(Scanner scanner) {
        this.scanner = scanner;
    }
    
    /**
     * Start a combat encounter
     * Returns true if player wins, false if player loses
     */
    public boolean startCombat(Player player, Enemy enemy) {
        System.out.println("\n*** BATTLE START ***");
        System.out.println(enemy.getDescription());
        enemy.displayStatus();
        System.out.println();
        
        while (player.isAlive() && enemy.isAlive()) {
            // Player turn
            playerTurn(player, enemy);
            
            if (!enemy.isAlive()) {
                enemy.onDefeat(player);
                return true;
            }
            
            // Enemy turn
            System.out.println("\n--- Enemy Turn ---");
            enemy.takeTurn(player);
            
            if (!player.isAlive()) {
                System.out.println("\n*** DEFEAT ***");
                System.out.println("You have been defeated...");
                return false;
            }
            
            System.out.println();
        }
        
        return player.isAlive();
    }
    
    /**
     * Handle player's turn in combat
     */
    private void playerTurn(Player player, Enemy enemy) {
        System.out.println("--- Your Turn ---");
        player.displayStatus();
        System.out.println("\n1. Attack");
        System.out.println("2. Use Item");
        System.out.println("3. Defend");
        System.out.print("Choose action: ");
        
        int choice = getIntInput(1, 3);
        
        switch (choice) {
            case 1: // Attack
                int damage = player.attack();
                System.out.println(player.getName() + " attacks!");
                enemy.takeDamage(damage);
                break;
                
            case 2: // Use Item
                player.showInventory();
                if (player.getInventory().isEmpty()) {
                    System.out.println("No items to use!");
                    playerTurn(player, enemy); // Retry turn
                } else {
                    System.out.print("Choose item (0 to cancel): ");
                    int itemChoice = getIntInput(0, player.getInventory().size());
                    if (itemChoice > 0) {
                        player.useItem(itemChoice - 1);
                    } else {
                        playerTurn(player, enemy); // Retry turn
                    }
                }
                break;
                
            case 3: // Defend
                int originalDefense = player.getDefense();
                player.setDefense(originalDefense * 2);
                System.out.println(player.getName() + " takes a defensive stance!");
                System.out.println("Defense temporarily doubled!");
                break;
        }
    }
    
    /**
     * Get validated integer input
     */
    private int getIntInput(int min, int max) {
        while (true) {
            try {
                String input = scanner.nextLine().trim();
                int choice = Integer.parseInt(input);
                if (choice >= min && choice <= max) {
                    return choice;
                }
                System.out.print("Please enter a number between " + min + " and " + max + ": ");
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a number: ");
            }
        }
    }
}
