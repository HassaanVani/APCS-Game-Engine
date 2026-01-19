import engine.*;
import levels.*;
import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {
        Levels.registerAll();
        
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("APCS 2D RPG Engine");
        
        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);
        window.pack();
        
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        
        HubLevel hub = new HubLevel();
        gamePanel.setHubLevel(hub);
        
        for (LevelRegistry.LevelEntry entry : LevelRegistry.getAll()) {
            gamePanel.registerLevel(entry.name, entry.create());
        }
        
        gamePanel.setLevel(hub);
        gamePanel.startGameThread();
        
        System.out.println("=".repeat(60));
        System.out.println("APCS 2D RPG ENGINE");
        System.out.println("=".repeat(60));
        System.out.println("Controls:");
        System.out.println("  WASD / Arrow Keys - Move");
        System.out.println("  SPACE - Confirm in battle");
        System.out.println("  Left/Right - Select battle action");
        System.out.println("  ESC - Return to hub");
        System.out.println();
        System.out.println("Registered " + LevelRegistry.count() + " levels.");
        System.out.println("=".repeat(60));
    }
}
