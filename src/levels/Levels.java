package levels;

import engine.LevelRegistry;
import engine.RegisteredLevel;
import engine.GameLevel;
import java.awt.Color;
import java.io.File;
import java.util.function.Supplier;
import java.util.HashSet;
import java.util.Set;

public class Levels {
    
    public static void registerAll() {
        // Automatic scanner: finds and registers any class with @RegisteredLevel annotation
        try {
            File dir = new File("src/levels");
            if (!dir.exists()) {
                dir = new File("levels"); // Fallback for compiled environments
            }
            
            Set<String> registered = new HashSet<>();
            if (dir.exists() && dir.isDirectory()) {
                for (File file : dir.listFiles()) {
                    String filename = file.getName();
                    if (filename.endsWith("Level.java") || filename.endsWith("Level.class")) {
                        String className = filename.substring(0, filename.lastIndexOf('.'));
                        
                        if (!registered.add(className)) {
                            continue;
                        }
                        
                        // Skip non-level files
                        if (className.equals("Levels") || className.equals("GameLevel") || className.equals("HubLevel")) {
                            continue;
                        }
                        
                        try {
                            Class<?> clazz = Class.forName("levels." + className);
                            if (GameLevel.class.isAssignableFrom(clazz) && clazz.isAnnotationPresent(RegisteredLevel.class)) {
                                RegisteredLevel anno = clazz.getAnnotation(RegisteredLevel.class);
                                
                                // Parse annotation color (named or hex) or auto-generate
                                Color color = parseColor(anno.color(), anno.name());
                                
                                // Create level factory
                                Supplier<GameLevel> factory = () -> {
                                    try {
                                        return (GameLevel) clazz.getDeclaredConstructor().newInstance();
                                    } catch (Exception e) {
                                        throw new RuntimeException("Failed to instantiate " + className, e);
                                    }
                                };
                                
                                // Register in registry
                                LevelRegistry.register(anno.name(), color, anno.doorX(), anno.doorY(), factory);
                                System.out.println("Auto-Registered Level: " + anno.name() + " (Door: " + anno.doorX() + ", " + anno.doorY() + ")");
                            }
                        } catch (ClassNotFoundException e) {
                            // Class not loaded yet, safe to skip
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error scanning levels: " + e.getMessage());
        }
    }
    
    private static Color parseColor(String hexOrEmpty, String name) {
        if (hexOrEmpty == null || hexOrEmpty.isEmpty()) {
            // Auto generate color from name hash
            int hash = Math.abs(name.hashCode());
            return new Color(50 + (hash % 150), 50 + ((hash / 150) % 150), 50 + ((hash / 22500) % 150));
        }
        try {
            return Color.decode(hexOrEmpty);
        } catch (NumberFormatException e) {
            return Color.GREEN; // Fallback
        }
    }
}
