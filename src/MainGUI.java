import core.*;
import examples.*;

/**
 * Main class for GUI version - Entry point for the game
 * 
 * This demonstrates POLYMORPHISM - the engine treats all levels the same
 * through the LevelGUI interface, even though each level is unique!
 */
public class MainGUI {
    public static void main(String[] args) {
        // Set look and feel to system default
        try {
            javax.swing.UIManager.setLookAndFeel(
                javax.swing.UIManager.getSystemLookAndFeelClassName()
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Create the game engine
        GameEngineGUI engine = new GameEngineGUI();
        
        engine.addLevel(new ForestLevelGUI(engine.getCombatSystem()));
        engine.addLevel(new CaveLevelGUI(engine.getCombatSystem()));
        engine.start();
    }
}
