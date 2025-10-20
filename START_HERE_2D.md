# 🎮 START HERE - 2D RPG Engine

## ⚡ Quick Start (30 Seconds)

**Want to see it in action RIGHT NOW?**

### Windows:
```
1. Double-click: compile-2d.bat
2. Double-click: run-2d.bat
3. Use WASD to move, walk into green slimes to battle!
```

### Command Line:
```bash
javac src/engine/*.java src/enemies/*.java src/levels/*.java src/Main2D.java
java -cp src Main2D
```

---

## 🎯 What Is This Project?

A **real 2D top-down RPG game** (like Zelda/Pokemon) where:

✅ Players **move around** with keyboard (WASD)  
✅ **Tile-based maps** with walls and obstacles  
✅ Walk into enemies to trigger **Pokemon-style turn-based battles**  
✅ Each student creates **ONE level** with custom map and enemies  
✅ **Polymorphism** connects all levels into one game  

---

## 🎮 How to Play

### Exploration
- **WASD** or **Arrow Keys** - Move your character
- Walk around the map
- Avoid or seek out enemies (green/purple blobs)

### Battle
- Walk into an enemy to start battle
- **Left/Right Arrows** - Choose Fight or Run
- **SPACE** - Confirm action
- Defeat enemies to gain EXP and gold!

---

## 📚 I'm a Student - What Do I Do?

### Step 1: Understand the Goal
You're creating **ONE level** for the class game. Your level should have:
- A tile-based map (16x12 grid)
- Enemies placed around the map
- A theme (forest, cave, volcano, etc.)

### Step 2: Copy the Template
```
Copy: STUDENT_TEMPLATE_2D.java
To: src/levels/YourName_Level.java
```

### Step 3: Read the Documentation
- **STUDENT_TEMPLATE_2D.java** - Has all the code examples
- **README_2D.md** - Complete reference guide
- **PROJECT_SUMMARY_2D.md** - Big picture overview

### Step 4: Design Your Map
On graph paper, draw a 16x12 grid:
- Mark where walls go (tile type 1)
- Mark walkable areas (tile type 0)
- Plan enemy positions
- Choose your theme colors

### Step 5: Implement in Code
```java
@Override
public void setupMap() {
    // Create your map layout
    fillRect(0, 0, mapWidth, mapHeight, 0); // Fill with grass
    
    // Add walls around border
    for (int x = 0; x < mapWidth; x++) {
        setTile(x, 0, 1);  // Top wall
        setTile(x, 11, 1); // Bottom wall
    }
    // ... more tiles
}

@Override
public void setupEnemies() {
    Slime slime = new Slime();
    slime.setPosition(200, 200);
    addEnemy(slime);
}
```

### Step 6: Test Your Level
```bash
# In Main2D.java, change to your level:
GameLevel level1 = new YourName_Level();

# Then compile and run:
compile-2d.bat
run-2d.bat
```

---

## 👨‍🏫 I'm a Teacher - How Do I Use This?

### Week 1: Introduction
1. Run the example game in class
2. Explain polymorphism using the code
3. Show how `GameLevel` is extended
4. Students plan their levels on paper

### Week 2-3: Implementation
1. Students copy template
2. Implement `setupMap()` and `setupEnemies()`
3. Test individually
4. Debug and refine

### Week 4: Integration
1. Collect all student level files
2. Place in `src/levels/`
3. Can switch between levels in `Main2D.java`
4. Test complete game

### Week 5: Showcase
1. Play through all levels as class
2. Discuss unique features
3. Celebrate polymorphism in action!

### Teaching Points
- **Polymorphism:** All levels extend `GameLevel`, engine treats same
- **Abstract methods:** `setupMap()`, `setupEnemies()` must be implemented
- **Method overriding:** Each level has different implementation
- **Code reuse:** One engine, many levels

---

## 🎨 What Can Students Customize?

### Map Design
- **Tile layout** - Where walls, water, paths go
- **Tile colors** - RGB values for each tile type
- **Theme** - Forest, cave, desert, ice, volcano, etc.
- **Obstacles** - Strategic placement for difficulty

### Enemies
- **Sprites** - Draw custom enemy appearance
- **Stats** - Health, attack, defense values
- **Battle AI** - Attack patterns, special abilities
- **Rewards** - EXP and gold amounts

### Advanced (Optional)
- **Custom tile types** - New terrain types
- **Special enemies** - Bosses with unique mechanics
- **Load image sprites** - Use PNG files instead of code
- **Sound effects** - Add audio (requires extra libraries)

---

## 🏗️ Project Structure

```
Key Files You Need to Know:

📂 src/
  ├── Main2D.java                 ← Run this to start game
  │
  ├── engine/                     ← Don't modify (game engine)
  │   ├── GameLevel.java          ← Students extend THIS
  │   ├── Enemy.java              ← Students extend THIS
  │   └── (other engine files)
  │
  ├── levels/                     ← Students add levels HERE
  │   ├── ForestLevel.java        ← Example 1
  │   ├── CaveLevel.java          ← Example 2
  │   └── YourLevel.java          ← Your level goes here!
  │
  └── enemies/                    ← Students add enemies HERE
      ├── Slime.java              ← Example enemy
      └── YourEnemy.java          ← Your enemy goes here!

📄 STUDENT_TEMPLATE_2D.java       ← Copy this!
📄 README_2D.md                   ← Full documentation
📄 PROJECT_SUMMARY_2D.md          ← Overview
```

---

## 🎓 How This Teaches Polymorphism

### The Concept
```java
// In Main2D.java:
GameLevel level1 = new ForestLevel();    // Student A's level
GameLevel level2 = new CaveLevel();      // Student B's level  
GameLevel level3 = new VolcanoLevel();   // Student C's level

// Engine works the same for ALL:
gamePanel.setLevel(level1);  // Calls setupMap() and setupEnemies()
```

### Why It Matters
- **Different implementations** - Each level is unique
- **Same interface** - All extend `GameLevel`
- **Engine doesn't care** - Treats all levels identically
- **Runtime binding** - Correct version called automatically

This is **polymorphism in action!**

---

## 🔧 Troubleshooting

### Game won't compile
- Check all files are in correct folders
- Verify `package` statements match folder names
- Look for typos in class names

### Player can't move
- Make sure border walls aren't blocking spawn point
- Check `startX` and `startY` are in walkable area
- Verify `isTileSolid()` is correct

### Enemies don't show up
- Call `addEnemy()` in `setupEnemies()`
- Check enemy positions are on screen (0-768, 0-576)
- Make sure enemies have sprites

### Battle doesn't trigger
- Verify enemy has collision box
- Check player intersects enemy position
- Make sure enemy isn't already defeated

---

## 📊 Tile Reference

| Type | Use | Walkable | Default Color |
|------|-----|----------|---------------|
| 0 | Grass/Floor | ✅ Yes | Green |
| 1 | Wall/Rock | ❌ No | Brown |
| 2 | Water | ❌ No | Blue |
| 3 | Path | ✅ Yes | Tan |
| 4+ | Custom | You decide | You define |

---

## 🎯 Success Checklist

Before submitting your level:

- [ ] Level compiles without errors
- [ ] Player spawns in walkable area
- [ ] Can move around the map
- [ ] Can't walk through walls
- [ ] Enemies appear on map
- [ ] Battle triggers when touching enemy
- [ ] Can defeat enemies
- [ ] Map has a clear theme
- [ ] Tile colors match theme
- [ ] Tested multiple times

---

## 🚀 Next Steps

1. ✅ **Run the game** - See it in action
2. ✅ **Read STUDENT_TEMPLATE_2D.java** - Understand the code
3. ✅ **Look at examples** - ForestLevel.java and CaveLevel.java
4. ✅ **Plan your level** - Draw on graph paper
5. ✅ **Implement** - Copy template and code your level
6. ✅ **Test** - Run and debug
7. ✅ **Submit** - Give your level file to teacher

---

## 💡 Tips for Success

### Map Design
- Start simple, add complexity later
- Leave plenty of walkable space
- Create a border around entire map
- Test as you build

### Enemy Placement
- Don't put enemies at spawn point
- Space them out
- 2-5 enemies is good for one level
- Consider difficulty progression

### Creativity
- Choose a unique theme
- Use colors that match your theme
- Add interesting obstacles
- Make it fun to explore!

---

## 📞 Need Help?

1. **Read the template** - STUDENT_TEMPLATE_2D.java has examples
2. **Check examples** - ForestLevel.java and CaveLevel.java
3. **Read docs** - README_2D.md has complete API
4. **Ask teacher** - They're there to help!

---

## 🎉 Have Fun!

This is **your level** in **your class's game**!

Be creative, test thoroughly, and make something awesome! 🚀

---

**The game is ready to run RIGHT NOW!**

**Just double-click `run-2d.bat` and start playing!** 🎮
