package examples;

import core.*;
import examples.*;

/**
 * Example Level 1 - The Dark Forest (GUI Version)
 * This demonstrates how students should structure their levels for GUI
 */
public class ForestLevelGUI extends LevelGUI {
    private CombatSystemGUI combat;
    private Player player;
    
    public ForestLevelGUI(CombatSystemGUI combat) {
        super("The Dark Forest", "A mysterious forest filled with dangerous creatures...");
        this.combat = combat;
    }
    
    @Override
    public void start(Player player) {
        this.player = player;
        
        gui.displayText("You enter a dark forest. The trees loom overhead.");
        gui.displayText("You hear rustling in the bushes...");
        
        gui.waitForButton("Continue", () -> {
            gui.clearButtons();
            encounterGoblin();
        });
    }
    
    private void encounterGoblin() {
        gui.displayText("\nA goblin jumps out from behind a tree!");
        
        Goblin goblin = new Goblin();
        combat.startCombat(player, goblin, (playerWon) -> {
            if (!playerWon) {
                return; // Player died
            }
            
            afterFirstBattle();
        });
    }
    
    private void afterFirstBattle() {
        gui.displayText("\nYou find a potion on the goblin's body.");
        player.addItem(new HealthPotion());
        gui.updateStats();
        
        gui.waitForButton("Continue", () -> {
            gui.clearButtons();
            showChoice();
        });
    }
    
    private void showChoice() {
        gui.displayText("\nYou continue deeper into the forest...");
        gui.displayText("The trees grow darker and more twisted.");
        gui.displayText("\nYou come to a fork in the path:");
        
        String[] choices = {
            "Take the left path (looks safer)",
            "Take the right path (looks dangerous)"
        };
        
        gui.showChoices(choices, (choiceIndex) -> {
            gui.clearButtons();
            if (choiceIndex == 0) {
                saferPath();
            } else {
                dangerousPath();
            }
        });
    }
    
    private void saferPath() {
        gui.displayText("\nYou find a clearing with an abandoned campsite.");
        gui.displayText("There's a potion here!");
        player.addItem(new HealthPotion());
        gui.updateStats();
        
        gui.waitForButton("Continue", () -> {
            gui.clearButtons();
            finalEncounter();
        });
    }
    
    private void dangerousPath() {
        gui.displayText("\nYou encounter an orc patrol!");
        
        Orc orc = new Orc();
        combat.startCombat(player, orc, (playerWon) -> {
            if (!playerWon) {
                return;
            }
            
            gui.displayText("\nYou find the orc's treasure!");
            player.addItem(new StrengthPotion());
            player.addGold(50);
            gui.updateStats();
            
            gui.waitForButton("Continue", () -> {
                gui.clearButtons();
                finalEncounter();
            });
        });
    }
    
    private void finalEncounter() {
        gui.displayText("\nYou reach the edge of the forest.");
        gui.displayText("But a final goblin blocks your path!");
        
        Goblin finalGoblin = new Goblin();
        combat.startCombat(player, finalGoblin, (playerWon) -> {
            if (!playerWon) {
                return;
            }
            
            gui.displayText("\nYou emerge from the forest victorious!");
            complete();
        });
    }
}
