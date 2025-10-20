# Student Quick Start Guide 🎮

Welcome! You're going to create your own level for our class RPG game!

## 🚀 Getting Started (5 Minutes)

### Step 1: Play the Example Game
First, see what you're creating:

```powershell
# Compile the game
javac src/core/*.java src/examples/*.java src/Main.java

# Run it
java -cp src Main
```

Or just double-click `compile.bat` then `run.bat`!

### Step 2: Understand the Goal

You need to create **ONE level** for the game. Your level should:
- Tell a story
- Have at least one enemy to fight
- Be completable
- Be fun!

## 📝 Creating Your Level

### Step 1: Copy the Template

1. Copy `STUDENT_TEMPLATE.java` to `src/YourName_Level.java`
   - Example: `src/Alice_Level.java`
2. Open it in your editor

### Step 2: Rename the Class

Change this line:
```java
public class StudentName_Level extends Level {
```

To:
```java
public class YourName_Level extends Level {  // Use YOUR name!
```

### Step 3: Add Your Level Info

```java
super(
    "The Haunted Mansion",  // Your level name
    "A spooky mansion filled with ghosts..."  // Description
);
```

### Step 4: Implement Your Level

Fill in the `start()` method with your story and challenges!

## 🎯 Simple Example

Here's a super simple level to get you started:

```java
@Override
public void start(Player player) {
    // 1. Welcome message
    System.out.println("Welcome to the Spooky Forest!");
    System.out.println("You hear strange noises...");
    waitForEnter();
    
    // 2. Combat
    System.out.println("A zombie appears!");
    Zombie zombie = new Zombie();  // You'll create this
    if (!combat.startCombat(player, zombie)) {
        return;  // Player died - game over
    }
    
    // 3. Reward
    System.out.println("You find a treasure chest!");
    player.addItem(new HealthPotion());
    player.addGold(50);
    
    // 4. Finish
    System.out.println("You escape the forest!");
    complete();  // Mark level as complete
}
```

## 🎨 Making It Better

### Add Choices
```java
System.out.println("You see two paths:");
System.out.println("1. Go left");
System.out.println("2. Go right");
System.out.print("Choose: ");
int choice = getIntInput(1, 2);

if (choice == 1) {
    System.out.println("You find treasure!");
    player.addGold(100);
} else {
    System.out.println("You encounter a monster!");
    // Combat here
}
```

### Create Custom Enemies

At the bottom of your file, add:

```java
// Your custom enemy class
class Zombie extends Enemy {
    public Zombie() {
        super("Zombie", 40, 10, 3, 30, 15);
        // name, health, attack, defense, exp, gold
    }
    
    @Override
    public void takeTurn(Player player) {
        System.out.println(name + " attacks with rotting hands!");
        player.takeDamage(attack());
    }
    
    @Override
    public String getDescription() {
        return "A shambling undead creature!";
    }
}
```

### Create Custom Items

```java
class MagicSword extends Item {
    public MagicSword() {
        super("Magic Sword", "Increases attack power", true);
    }
    
    @Override
    public void use(Player player) {
        player.setAttackPower(player.getAttackPower() + 10);
        System.out.println("Attack increased by 10!");
    }
}
```

## 🔧 Testing Your Level

### Step 1: Add to Main.java

In `src/Main.java`, add your level:

```java
engine.addLevel(new ForestLevel(engine.getScanner(), engine.getCombatSystem()));
engine.addLevel(new CaveLevel(engine.getScanner(), engine.getCombatSystem()));
engine.addLevel(new YourName_Level(engine.getScanner(), engine.getCombatSystem()));  // Add this!
```

### Step 2: Compile and Test

```powershell
javac src/core/*.java src/examples/*.java src/YourName_Level.java src/Main.java
java -cp src Main
```

### Step 3: Debug Common Issues

**"Cannot find symbol"** → Check your class name and imports
**"Game crashes"** → Did you check if player died?
**"Level never ends"** → Did you call `complete()`?

## ✅ Checklist Before Submitting

- [ ] Level compiles without errors
- [ ] Level can be completed
- [ ] Player death is handled properly
- [ ] At least one combat encounter
- [ ] Some kind of reward (items or gold)
- [ ] Your code has comments
- [ ] You've tested it at least twice

## 🎓 What You're Learning

This project teaches **POLYMORPHISM**:

- Your level extends the `Level` class
- The game engine calls `start()` on your level
- Your level behaves differently than others
- But the engine treats them all the same!

This is the power of polymorphism! 🧙‍♂️

## 💡 Creative Ideas

### Themes
- Haunted castle
- Underwater temple
- Space station
- Desert pyramid
- Enchanted forest
- Ice cave
- Volcano dungeon
- Sky fortress

### Mechanics
- Puzzle to solve
- Boss battle
- Multiple endings
- Secret treasure
- Trap to avoid
- NPC dialogue
- Mini-game

### Enemies
- Undead (zombies, skeletons)
- Beasts (wolves, bears)
- Mythical (dragons, griffins)
- Elemental (fire, ice)
- Robots
- Aliens

## 🆘 Need Help?

1. **Read the examples** - `ForestLevel.java` and `CaveLevel.java`
2. **Check the template** - `STUDENT_TEMPLATE.java`
3. **Read the main README** - More detailed info
4. **Ask your teacher** - That's what they're there for!

## 🎉 Have Fun!

This is YOUR level. Make it unique, creative, and fun!

The best part? Your level will be part of a game that the whole class plays together! 🎮

---

**Pro Tip:** Start simple and add complexity gradually. A simple level that works is better than a complex level that crashes!

Good luck, and happy coding! 🚀
