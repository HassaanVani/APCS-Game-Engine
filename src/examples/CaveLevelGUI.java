package examples;

import core.*;
import examples.*;

/**
 * Example Level 2 - The Ancient Cave (GUI Version)
 * Shows a different level structure with puzzle elements
 */
public class CaveLevelGUI extends LevelGUI {
    private CombatSystemGUI combat;
    private Player player;
    
    public CaveLevelGUI(CombatSystemGUI combat) {
        super("The Ancient Cave", "A mysterious cave with ancient secrets and treasures...");
        this.combat = combat;
    }
    
    @Override
    public void start(Player player) {
        this.player = player;
        
        gui.displayText("You stand before a dark cave entrance.");
        gui.displayText("Strange symbols are carved into the stone.");
        
        gui.waitForButton("Enter Cave", () -> {
            gui.clearButtons();
            explorePassages();
        });
    }
    
    private void explorePassages() {
        gui.displayText("\nYou enter the cave. It's dark and cold.");
        gui.displayText("You light a torch and begin exploring...");
        gui.displayText("\nYou find three passages:");
        
        String[] choices = {
            "A passage with claw marks on the walls",
            "A passage with strange glowing crystals",
            "A passage with water dripping from the ceiling"
        };
        
        gui.showChoices(choices, (choiceIndex) -> {
            gui.clearButtons();
            handlePassageChoice(choiceIndex);
        });
    }
    
    private void handlePassageChoice(int choice) {
        switch (choice) {
            case 0: // Claw marks - combat
                gui.displayText("\nAn orc ambushes you from the shadows!");
                Orc orc = new Orc();
                combat.startCombat(player, orc, (playerWon) -> {
                    if (!playerWon) return;
                    player.addGold(30);
                    gui.updateStats();
                    continueDeeper();
                });
                break;
                
            case 1: // Crystals - treasure
                gui.displayText("\nThe crystals illuminate a hidden treasure chest!");
                player.addItem(new HealthPotion());
                player.addItem(new StrengthPotion());
                player.addGold(100);
                gui.updateStats();
                gui.displayText("You found great treasures!");
                gui.waitForButton("Continue", () -> {
                    gui.clearButtons();
                    continueDeeper();
                });
                break;
                
            case 2: // Water - trap
                gui.displayText("\nYou trigger a trap! Water floods the passage!");
                gui.displayText("You escape but take damage in the process.");
                player.takeDamage(15);
                gui.updateStats();
                gui.waitForButton("Continue", () -> {
                    gui.clearButtons();
                    continueDeeper();
                });
                break;
        }
    }
    
    private void continueDeeper() {
        gui.displayText("\nYou continue deeper into the cave...");
        gui.displayText("You find ancient inscriptions on the walls.");
        gui.displayText("They tell of a great treasure guarded by a fierce warrior.");
        
        gui.waitForButton("Continue", () -> {
            gui.clearButtons();
            bossEncounter();
        });
    }
    
    private void bossEncounter() {
        gui.displayText("\nYou enter a massive chamber.");
        gui.displayText("An orc chieftain sits on a throne of bones!");
        gui.displayText("He rises to face you!");
        
        Orc boss = new Orc();
        boss.setHealth(boss.getMaxHealth() + 20); // Make it stronger
        
        combat.startCombat(player, boss, (playerWon) -> {
            if (!playerWon) return;
            
            gui.displayText("\nBehind the throne, you find an ancient treasure!");
            player.addGold(200);
            player.gainExperience(100);
            gui.updateStats();
            
            gui.displayText("\nYou exit the cave, victorious and wealthy!");
            complete();
        });
    }
}
