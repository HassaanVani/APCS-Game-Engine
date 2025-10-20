// TEMPLATE FOR STUDENT LEVELS
// Copy this file and replace "StudentName" with your actual name
// Then implement your level in the start() method!

import core.Level;
import core.Player;
import core.CombatSystem;
import java.util.Scanner;

/**
 * Replace this with your level name and description
 * Example: The Haunted Castle, The Desert Ruins, The Ice Caverns, etc.
 */
public class StudentName_Level extends Level {
    private Scanner scanner;
    private CombatSystem combat;
    
    public StudentName_Level(Scanner scanner, CombatSystem combat) {
        super(
            "YOUR LEVEL NAME HERE",  // Name of your level
            "YOUR LEVEL DESCRIPTION HERE"  // Brief description
        );
        this.scanner = scanner;
        this.combat = combat;
    }
    
    @Override
    public void start(Player player) {
        // TODO: Implement your level here!
        
        // You can:
        // 1. Print story text with System.out.println()
        // 2. Create enemies and start combat: combat.startCombat(player, enemy)
        // 3. Give items to player: player.addItem(new YourItem())
        // 4. Give gold: player.addGold(amount)
        // 5. Create choices for the player
        // 6. Check if player died: if (!player.isAlive()) return;
        
        // Example structure:
        System.out.println("Your story begins here...");
        
        // Example enemy encounter:
        // YourEnemy enemy = new YourEnemy();
        // if (!combat.startCombat(player, enemy)) {
        //     return; // Player died
        // }
        
        // Example reward:
        // player.addItem(new YourItem());
        // player.addGold(50);
        
        // When level is complete, call:
        complete();
    }
    
    // Helper method for user input
    private void waitForEnter() {
        System.out.print("\nPress ENTER to continue...");
        scanner.nextLine();
    }
    
    // Helper method for number choices
    private int getIntInput(int min, int max) {
        while (true) {
            try {
                String input = scanner.nextLine().trim();
                int choice = Integer.parseInt(input);
                if (choice >= min && choice <= max) {
                    return choice;
                }
                System.out.print("Please enter " + min + "-" + max + ": ");
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Enter a number: ");
            }
        }
    }
}

// ============================================================
// TEMPLATE FOR STUDENT ENEMIES
// ============================================================

/*
import core.Enemy;
import core.Player;

public class StudentName_Enemy extends Enemy {
    
    public StudentName_Enemy() {
        super(
            "Enemy Name",     // Name
            50,               // Health
            10,               // Attack Power
            3,                // Defense
            30,               // Experience Reward
            15                // Gold Reward
        );
    }
    
    @Override
    public void takeTurn(Player player) {
        // What does your enemy do on its turn?
        System.out.println(name + " attacks!");
        player.takeDamage(attack());
    }
    
    @Override
    public String getDescription() {
        return "Describe your enemy here!";
    }
}
*/

// ============================================================
// TEMPLATE FOR STUDENT ITEMS
// ============================================================

/*
import core.Item;
import core.Player;

public class StudentName_Item extends Item {
    
    public StudentName_Item() {
        super(
            "Item Name",           // Name
            "Item description",    // Description
            true                   // Is it consumable? (true = single use)
        );
    }
    
    @Override
    public void use(Player player) {
        // What happens when player uses this item?
        // Examples:
        // player.heal(30);
        // player.setAttackPower(player.getAttackPower() + 5);
        // player.setDefense(player.getDefense() + 3);
        
        System.out.println("Item effect happens here!");
    }
}
*/
