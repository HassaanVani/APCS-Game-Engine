# Quick Reference Card - APCS Game Engine

## LevelBuilder Methods (One-Liners)

```java
builder.fillBackground(0);                                    // Fill entire map
builder.createBorder(1);                                      // Create border walls
builder.createRoom(x, y, width, height, floor, wall);        // Create room
builder.createHorizontalCorridor(startX, endX, y, type);     // Horizontal path
builder.createVerticalCorridor(x, startY, endY, type);       // Vertical path
builder.createDoor(x, y, floorType);                         // Create opening
builder.createCircle(centerX, centerY, radius, type);        // Create circle
builder.createScatteredTiles(x, y, w, h, type, density);     // Random tiles
builder.createCheckerboard(x, y, w, h, type1, type2);        // Checkerboard
builder.createMaze(x, y, w, h, wallType, pathType);          // Simple maze
builder.createDiagonalLine(x1, y1, x2, y2, type);            // Diagonal line
```

## Sprite Methods (One-Liners)

```java
// For Enemies (in constructor)
setCustomSprite(Color.RED, "circle");                        // Shape sprite
setSpriteOrFallback("enemies/name.png", Color.RED);          // Image + fallback
setCustomSprite("enemies/name.png");                         // Image only

// For Tiles (in setupMap)
setTileSprite(0, "tiles/grass.png");                         // Image for tile
setTileSpriteOrColor(0, "tiles/grass.png", Color.GREEN);     // Image + fallback

// For Player
player.setCustomSprite("players/hero.png");                  // Image
player.setCustomSprite(Color.BLUE, "circle");                // Shape
```

## SpriteManager Methods

```java
SpriteManager.createColoredSprite(Color.RED);                // Colored square
SpriteManager.createShapeSprite(Color.BLUE, "circle");       // Shape sprite
SpriteManager.createTileSprite(Color.GREEN, "grass");        // Tile with pattern
SpriteManager.loadSprite("filename.png");                    // Load image
SpriteManager.getSpriteOrFallback("file.png", Color.RED);    // Load with fallback
```

## Tile Types

```
0 = Grass/Floor (walkable)
1 = Wall (blocks)
2 = Water (blocks)
3 = Path (walkable)
4+ = Custom tiles
```

## Enemy Stats Quick Guide

```
        Health  Attack  Defense  EXP  Gold
Weak    20-40   5-10    2-5      10   5
Normal  50-80   12-18   5-10     30   15
Strong  100+    20-30   12-20    60   35
Boss    200+    35+     25+      150  100
```

## Battle Action Patterns

```java
// Simple attack
int damage = attack();
player.takeDamage(damage);
return name + " attacks for " + damage + " damage!";

// With miss chance (20%)
if (Math.random() < 0.2) return name + " missed!";

// With critical hit (15%)
if (Math.random() < 0.15) {
    int crit = attack() * 3;
    player.takeDamage(crit);
    return name + " CRITICAL HIT for " + crit + "!";
}

// Health-based behavior
if (health < maxHealth / 2) {
    // Desperate when low health
}
```

## Color Reference (RGB)

```java
new Color(R, G, B)  // R, G, B from 0-255

// Common colors
Color.RED           // (255, 0, 0)
Color.GREEN         // (0, 255, 0)
Color.BLUE          // (0, 0, 255)
Color.YELLOW        // (255, 255, 0)
Color.ORANGE        // (255, 200, 0)
Color.PURPLE        // (128, 0, 128)
Color.CYAN          // (0, 255, 255)
Color.MAGENTA       // (255, 0, 255)
Color.WHITE         // (255, 255, 255)
Color.BLACK         // (0, 0, 0)
Color.GRAY          // (128, 128, 128)

// Nature colors
new Color(34, 139, 34)     // Forest green
new Color(139, 69, 19)     // Brown
new Color(70, 130, 180)    // Steel blue
new Color(210, 180, 140)   // Tan
new Color(255, 100, 0)     // Orange (lava)
new Color(200, 200, 255)   // Light blue (ice)
```

## Shape Options

```
"circle"    // Round sprite
"triangle"  // Triangle (good for flying enemies)
"diamond"   // Diamond shape
"square"    // Square (default)
```

## Tile Pattern Options

```
"grass"     // Grass texture
"brick"     // Brick pattern
"stone"     // Stone texture
"water"     // Wave pattern
"none"      // Solid color
```

## Map Coordinates

```
Map size: 16 tiles wide × 12 tiles tall
Tile size: 48 pixels
Screen size: 768 × 576 pixels

Position conversion:
Tile X → Pixel X: tileX * 48
Pixel X → Tile X: pixelX / 48
```

## Common Patterns

### Simple Level
```java
builder.fillBackground(0);
builder.createBorder(1);
builder.createRoom(5, 4, 6, 4, 3, 1);
```

### Level with Path
```java
builder.fillBackground(0);
builder.createBorder(1);
builder.createHorizontalCorridor(2, 14, 6, 3);
```

### Natural Level
```java
builder.fillBackground(0);
builder.createBorder(1);
builder.createCircle(8, 6, 2, 2);  // Pond
builder.createScatteredTiles(2, 2, 12, 8, 1, 0.15);  // Trees
```

### Dungeon Level
```java
builder.fillBackground(1);  // Start with walls
builder.createRoom(3, 3, 4, 4, 0, 1);
builder.createRoom(9, 6, 4, 4, 0, 1);
builder.createHorizontalCorridor(6, 10, 7, 0);
```

## File Structure

```
APCS-Game-Engine/
├── src/
│   ├── Main.java
│   ├── engine/
│   │   ├── GameLevel.java
│   │   ├── LevelBuilder.java
│   │   ├── SpriteManager.java
│   │   ├── Player.java
│   │   └── Enemy.java
│   ├── levels/
│   │   └── YourLevel.java
│   └── enemies/
│       └── YourEnemy.java
├── sprites/
│   ├── players/
│   ├── enemies/
│   └── tiles/
├── EASY_LEVEL_TEMPLATE.java
├── EASY_ENEMY_TEMPLATE.java
└── STUDENT_GUIDE.md
```

## Getting Started Checklist

- [ ] Copy `EASY_LEVEL_TEMPLATE.java`
- [ ] Rename file and class
- [ ] Design map on paper (16×12 grid)
- [ ] Use `builder` methods to create map
- [ ] Add enemies with `addEnemy()`
- [ ] Define tile colors in `getTileColor()`
- [ ] Test your level!
- [ ] (Optional) Add sprites

## Need Help?

1. Check `STUDENT_GUIDE.md` for detailed explanations
2. Look at `DemoLevel.java` for examples
3. Review `ForestLevel.java` and `CaveLevel.java`
4. Ask your teacher or classmates!
