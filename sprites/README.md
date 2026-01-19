# Sprites Directory

This directory is for storing custom sprite images for your game.

## Directory Structure

- **players/** - Player character sprites
- **enemies/** - Enemy sprites
- **tiles/** - Tile/background sprites

## Sprite Guidelines

### Image Format
- Use PNG format (supports transparency)
- Recommended size: 48x48 pixels (matches TILE_SIZE)
- Can use other sizes, but 48x48 works best

### Naming Convention
- Use lowercase with underscores: `my_sprite.png`
- Be descriptive: `fire_slime.png`, `grass_tile.png`
- Avoid spaces in filenames

## How to Use Sprites

### For Players
```java
// In your level or game setup
player.setCustomSprite("players/hero.png");

// Or use a shape sprite
player.setCustomSprite(Color.BLUE, "circle");
```

### For Enemies
```java
// In your enemy constructor
setSpriteOrFallback("enemies/slime.png", Color.GREEN);

// Or
setCustomSprite(Color.RED, "triangle");
```

### For Tiles
```java
// In your level's setupMap() method
setTileSprite(0, "tiles/grass.png");
setTileSprite(1, "tiles/wall.png");

// With fallback color
setTileSpriteOrColor(2, "tiles/water.png", Color.BLUE);
```

## Creating Sprites

### Option 1: Draw Your Own
- Use any image editor (Paint, GIMP, Photoshop, etc.)
- Create a 48x48 pixel image
- Save as PNG
- Place in appropriate subfolder

### Option 2: Use SpriteManager (No Files Needed!)
```java
// Create colored sprites programmatically
BufferedImage sprite = SpriteManager.createColoredSprite(Color.RED);

// Create shape sprites
BufferedImage circle = SpriteManager.createShapeSprite(Color.BLUE, "circle");
BufferedImage triangle = SpriteManager.createShapeSprite(Color.GREEN, "triangle");
BufferedImage diamond = SpriteManager.createShapeSprite(Color.YELLOW, "diamond");

// Create tile sprites with patterns
BufferedImage grass = SpriteManager.createTileSprite(Color.GREEN, "grass");
BufferedImage brick = SpriteManager.createTileSprite(Color.BROWN, "brick");
BufferedImage water = SpriteManager.createTileSprite(Color.BLUE, "water");
```

### Option 3: Download Free Sprites
- [OpenGameArt.org](https://opengameart.org/)
- [Itch.io](https://itch.io/game-assets/free)
- [Kenney.nl](https://kenney.nl/assets)

Make sure sprites are free to use and properly licensed!

## Examples

See the student templates for examples of how to use sprites in your levels and enemies.
