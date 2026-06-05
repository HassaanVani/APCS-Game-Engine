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
            Set<File> directoriesToScan = new HashSet<>();
            
            // 1. Try class loader resource directory (very reliable across IDE / build outputs)
            try {
                java.net.URL resource = Levels.class.getResource("");
                if (resource != null && resource.getProtocol().equals("file")) {
                    directoriesToScan.add(new File(resource.toURI()));
                }
            } catch (Exception e) {
                // Ignore class loader resource lookup failures
            }
            
            // 2. Fall back to relative filesystem paths
            directoriesToScan.add(new File("src/levels"));
            directoriesToScan.add(new File("levels"));
            
            Set<String> registered = new HashSet<>();
            
            for (File dir : directoriesToScan) {
                if (dir.exists() && dir.isDirectory()) {
                    File[] files = dir.listFiles();
                    if (files == null) continue;
                    
                    for (File file : files) {
                        if (!file.isFile()) continue;
                        
                        String filename = file.getName();
                        if (filename.endsWith(".java") || filename.endsWith(".class")) {
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
                                if (GameLevel.class.isAssignableFrom(clazz) && !clazz.isInterface() && !java.lang.reflect.Modifier.isAbstract(clazz.getModifiers())) {
                                    boolean hasAnnotation = clazz.isAnnotationPresent(RegisteredLevel.class);
                                    String levelName;
                                    Color color;
                                    int doorX;
                                    int doorY;
                                    
                                    if (hasAnnotation) {
                                        RegisteredLevel anno = clazz.getAnnotation(RegisteredLevel.class);
                                        levelName = anno.name();
                                        color = parseColor(anno.color(), anno.name());
                                        doorX = anno.doorX();
                                        doorY = anno.doorY();
                                    } else {
                                        // Migration fallback: register using class name
                                        levelName = className.replace("Level", "").replaceAll("(?<=\\p{L})(?=\\p{N})|(?<=\\p{N})(?=\\p{L})|(?<=\\p{Ll})(?=\\p{Lu})", " ").trim();
                                        color = parseColor("", levelName);
                                        doorX = -1; // Auto position
                                        doorY = -1;
                                        System.out.println("Migrated Level detected: " + className + " (auto-registering as '" + levelName + "')");
                                    }
                                    
                                    // Create level factory
                                    Supplier<GameLevel> factory = () -> {
                                        try {
                                            return (GameLevel) clazz.getDeclaredConstructor().newInstance();
                                        } catch (Exception e) {
                                            throw new RuntimeException("Failed to instantiate " + className, e);
                                        }
                                    };
                                    
                                    // Register in registry
                                    LevelRegistry.register(levelName, color, doorX, doorY, factory);
                                    System.out.println("Auto-Registered Level: " + levelName + " (Door: " + doorX + ", " + doorY + ")");
                                }
                            } catch (Throwable t) {
                                // Catch Throwable to handle ClassNotFoundException, NoClassDefFoundError, LinkageError, etc. gracefully
                                System.out.println("Skipped scanning levels." + className + " due to load warning: " + t.getMessage());
                            }
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
