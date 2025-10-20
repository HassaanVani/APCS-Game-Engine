// TEMPLATE FOR STUDENT LEVELS (GUI VERSION)
// Copy this file and replace "StudentName" with your actual name
// Then implement your level in the start() method!

import core.*;
import gui.*;
import examples.*; // For using example enemies and items

/**
 * Replace this with your level name and description
 * Example: The Haunted Castle, The Desert Ruins, The Ice Caverns, etc.
 */
public class StudentName_LevelGUI extends LevelGUI {
    private CombatSystemGUI combat;
    private Player player;
    
    public StudentName_LevelGUI(CombatSystemGUI combat) {
        super(
            "YOUR LEVEL NAME HERE",      // Name of your level
            "YOUR LEVEL DESCRIPTION"     // Brief description
        );
        this.combat = combat;
    }
    
    @Override
    public void start(Player player) {
        this.player = player;
        
        // TODO: Implement your level here!
        
        // The GUI provides these methods:
        // gui.displayText("text")       - Show text to player
        // gui.displaySeparator()        - Show a line separator
        // gui.clearText()               - Clear all text
        // gui.updateStats()             - Refresh player stats display
        
        // Example: Show story text
        gui.displayText("Your adventure begins here...");
        gui.displayText("You enter a mysterious place...");
        
        // Example: Wait for player to click continue
        gui.waitForButton("Continue", () -> {
            gui.clearButtons();
            continueStory();  // Call your next method
        });
    }
    
    // Example method - continue your story
    private void continueStory() {
        gui.displayText("\nYou continue forward...");
        
        // Example: Give player a choice
        String[] choices = {
            "Go left",
            "Go right",
            "Turn back"
        };
        
        gui.showChoices(choices, (choiceIndex) -> {
            gui.clearButtons();
            handleChoice(choiceIndex);
        });
    }
    
    // Example: Handle player choice
    private void handleChoice(int choice) {
        if (choice == 0) {
            // Left path
            gui.displayText("\nYou go left...");
            // Continue your story
        } else if (choice == 1) {
            // Right path  
            gui.displayText("\nYou go right...");
            startCombat(); // Example: start a battle
        } else {
            // Turn back
            gui.displayText("\nYou turn back...");
        }
    }
    
    // Example: Start a combat encounter
    private void startCombat() {
        gui.displayText("\nAn enemy appears!");
        
        // Create an enemy (you can use examples or create your own)
        Goblin enemy = new Goblin();
        
        // Start combat - callback is called when combat ends
        combat.startCombat(player, enemy, (playerWon) -> {
            if (!playerWon) {
                return; // Player died - level ends
            }
            
            // Player won the battle!
            afterCombat();
        });
    }
    
    // Example: After combat
    private void afterCombat() {
        gui.displayText("\nYou found treasure!");
        player.addItem(new HealthPotion());
        player.addGold(50);
        gui.updateStats();
        
        // Finish the level
        gui.waitForButton("Continue", () -> {
            gui.clearButtons();
            finishLevel();
        });
    }
    
    // Example: Finish the level
    private void finishLevel() {
        gui.displayText("\nYou completed the challenge!");
        complete(); // Mark level as complete
    }
}

// ============================================================
// TEMPLATE FOR STUDENT ENEMIES (same as console version)
// ============================================================

/*
import core.Enemy;
import core.Player;

class StudentName_Enemy extends Enemy {
    
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
// TEMPLATE FOR STUDENT ITEMS (same as console version)
// ============================================================

/*
import core.Item;
import core.Player;

class StudentName_Item extends Item {
    
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

// ============================================================
// KEY DIFFERENCES FROM CONSOLE VERSION:
// ============================================================
//
// 1. Extend LevelGUI instead of Level
// 2. Use gui.displayText() instead of System.out.println()
// 3. Use gui.waitForButton() instead of waiting for Enter
// 4. Use gui.showChoices() instead of Scanner input
// 5. Use CALLBACKS - code continues in lambda functions
// 6. Call gui.updateStats() to refresh the stats display
//
// ============================================================
// IMPORTANT: Understanding Callbacks
// ============================================================
//
// GUI uses callbacks instead of sequential code:
//
// WRONG (doesn't work with GUI):
//   int choice = getChoice();
//   if (choice == 1) { ... }
//
// RIGHT (works with GUI):
//   gui.showChoices(choices, (choiceIndex) -> {
//       if (choiceIndex == 0) { ... }
//   });
//
// The code in {...} runs LATER when player clicks
//
