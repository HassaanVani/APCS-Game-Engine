package examples;

import core.Level;
import core.Player;
import core.CombatSystem;
import java.util.Scanner;

/**
 * Example Level 1 - The Dark Forest
 * Demonstrates level structure with combat encounters and item rewards
 */
public class ForestLevel extends Level {
    private Scanner scanner;
    private CombatSystem combat;
    
    public ForestLevel(Scanner scanner, CombatSystem combat) {
        super("The Dark Forest", "A mysterious forest filled with dangerous creatures...");
        this.scanner = scanner;
        this.combat = combat;
    }
    
    @Override
    public void start(Player player) {
        System.out.println("You enter a dark forest. The trees loom overhead.");
        System.out.println("You hear rustling in the bushes...\n");
        
        waitForEnter();
        
        // First encounter - Goblin
        System.out.println("A goblin jumps out from behind a tree!");
        Goblin goblin = new Goblin();
        
        if (!combat.startCombat(player, goblin)) {
            // Player died
            return;
        }
        
        // Reward
        System.out.println("\nYou find a potion on the goblin's body.");
        player.addItem(new HealthPotion());
        
        waitForEnter();
        
        // Story continues
        System.out.println("\nYou continue deeper into the forest...");
        System.out.println("The trees grow darker and more twisted.");
        
        // Choice event
        System.out.println("\nYou come to a fork in the path:");
        System.out.println("1. Take the left path (looks safer)");
        System.out.println("2. Take the right path (looks dangerous)");
        System.out.print("Choose: ");
        
        int choice = getIntInput(1, 2);
        
        if (choice == 1) {
            // Safer path - find item
            System.out.println("\nYou find a clearing with an abandoned campsite.");
            System.out.println("There's a potion here!");
            player.addItem(new HealthPotion());
        } else {
            // Dangerous path - harder enemy but better reward
            System.out.println("\nYou encounter an orc patrol!");
            Orc orc = new Orc();
            
            if (!combat.startCombat(player, orc)) {
                return;
            }
            
            System.out.println("\nYou find the orc's treasure!");
            player.addItem(new StrengthPotion());
            player.addGold(50);
        }
        
        waitForEnter();
        
        // Final encounter
        System.out.println("\nYou reach the edge of the forest.");
        System.out.println("But a final goblin blocks your path!");
        
        Goblin finalGoblin = new Goblin();
        if (!combat.startCombat(player, finalGoblin)) {
            return;
        }
        
        System.out.println("\nYou emerge from the forest victorious!");
        complete();
    }
    
    private void waitForEnter() {
        System.out.print("\nPress ENTER to continue...");
        scanner.nextLine();
    }
    
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
