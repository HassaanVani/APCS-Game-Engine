package examples;

import core.Level;
import core.Player;
import core.CombatSystem;
import java.util.Scanner;

/**
 * Example Level 2 - The Ancient Cave
 * Shows a different level structure with puzzle elements
 */
public class CaveLevel extends Level {
    private Scanner scanner;
    private CombatSystem combat;
    
    public CaveLevel(Scanner scanner, CombatSystem combat) {
        super("The Ancient Cave", "A mysterious cave with ancient secrets and treasures...");
        this.scanner = scanner;
        this.combat = combat;
    }
    
    @Override
    public void start(Player player) {
        System.out.println("You stand before a dark cave entrance.");
        System.out.println("Strange symbols are carved into the stone.");
        
        waitForEnter();
        
        System.out.println("\nYou enter the cave. It's dark and cold.");
        System.out.println("You light a torch and begin exploring...");
        
        // Exploration section
        System.out.println("\nYou find three passages:");
        System.out.println("1. A passage with claw marks on the walls");
        System.out.println("2. A passage with strange glowing crystals");
        System.out.println("3. A passage with water dripping from the ceiling");
        System.out.print("Which do you explore? ");
        
        int choice = getIntInput(1, 3);
        
        switch (choice) {
            case 1:
                // Combat
                System.out.println("\nAn orc ambushes you from the shadows!");
                Orc orc = new Orc();
                if (!combat.startCombat(player, orc)) {
                    return;
                }
                player.addGold(30);
                break;
                
            case 2:
                // Treasure
                System.out.println("\nThe crystals illuminate a hidden treasure chest!");
                player.addItem(new HealthPotion());
                player.addItem(new StrengthPotion());
                player.addGold(100);
                System.out.println("You found great treasures!");
                break;
                
            case 3:
                // Trap
                System.out.println("\nYou trigger a trap! Water floods the passage!");
                System.out.println("You escape but take damage in the process.");
                player.takeDamage(15);
                break;
        }
        
        waitForEnter();
        
        // Continue deeper
        System.out.println("\nYou continue deeper into the cave...");
        System.out.println("You find ancient inscriptions on the walls.");
        System.out.println("They tell of a great treasure guarded by a fierce warrior.");
        
        waitForEnter();
        
        // Boss encounter
        System.out.println("\nYou enter a massive chamber.");
        System.out.println("An orc chieftain sits on a throne of bones!");
        System.out.println("He rises to face you!");
        
        Orc boss = new Orc(); // Could make a special boss class
        boss.setHealth(boss.getMaxHealth() + 20); // Make it stronger
        
        if (!combat.startCombat(player, boss)) {
            return;
        }
        
        System.out.println("\nBehind the throne, you find an ancient treasure!");
        player.addGold(200);
        player.gainExperience(100);
        
        System.out.println("\nYou exit the cave, victorious and wealthy!");
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
