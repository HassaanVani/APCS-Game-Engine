# Quick Reference Card 📋

## Essential Code Snippets

### Basic Level Structure
```java
public class MyLevel extends Level {
    private Scanner scanner;
    private CombatSystem combat;
    
    public MyLevel(Scanner scanner, CombatSystem combat) {
        super("Level Name", "Level Description");
        this.scanner = scanner;
        this.combat = combat;
    }
    
    @Override
    public void start(Player player) {
        // Your code here
        complete(); // Don't forget this!
    }
}
```

### Combat Encounter
```java
MyEnemy enemy = new MyEnemy();
if (!combat.startCombat(player, enemy)) {
    return; // Player died
}
```

### Player Actions
```java
player.addItem(new HealthPotion());
player.addGold(50);
player.gainExperience(30);
player.heal(20);
player.displayStatus();
```

### User Input
```java
// Wait for Enter
System.out.print("Press ENTER to continue...");
scanner.nextLine();

// Get a choice
System.out.println("1. Option A");
System.out.println("2. Option B");
System.out.print("Choose: ");
int choice = getIntInput(1, 2);
```

### Creating an Enemy
```java
class MyEnemy extends Enemy {
    public MyEnemy() {
        super("Name", health, attack, defense, exp, gold);
    }
    
    @Override
    public void takeTurn(Player player) {
        System.out.println(name + " attacks!");
        player.takeDamage(attack());
    }
    
    @Override
    public String getDescription() {
        return "Enemy description";
    }
}
```

### Creating an Item
```java
class MyItem extends Item {
    public MyItem() {
        super("Name", "Description", true); // true = consumable
    }
    
    @Override
    public void use(Player player) {
        player.heal(30); // or other effect
    }
}
```

## Common Patterns

### Story Sequence
```java
System.out.println("Story text...");
waitForEnter();
System.out.println("More story...");
```

### Choice Branching
```java
int choice = getIntInput(1, 2);
if (choice == 1) {
    // Path A
} else {
    // Path B
}
```

### Multiple Enemies
```java
for (int i = 0; i < 3; i++) {
    Goblin goblin = new Goblin();
    if (!combat.startCombat(player, goblin)) {
        return;
    }
}
```

## Important Player Methods

| Method | What It Does |
|--------|-------------|
| `player.getHealth()` | Get current HP |
| `player.getMaxHealth()` | Get max HP |
| `player.getAttackPower()` | Get attack stat |
| `player.getDefense()` | Get defense stat |
| `player.getLevel()` | Get player level |
| `player.getGold()` | Get gold amount |
| `player.isAlive()` | Check if alive |
| `player.addItem(item)` | Give item |
| `player.addGold(amount)` | Give gold |
| `player.gainExperience(exp)` | Give XP |
| `player.heal(amount)` | Restore HP |
| `player.takeDamage(amount)` | Deal damage |
| `player.displayStatus()` | Show stats |

## Recommended Enemy Stats

| Enemy Type | Health | Attack | Defense | Exp | Gold |
|------------|--------|--------|---------|-----|------|
| Weak | 20-30 | 5-8 | 1-2 | 15-25 | 5-10 |
| Normal | 30-50 | 8-12 | 2-5 | 25-50 | 10-25 |
| Strong | 50-80 | 12-18 | 5-8 | 50-100 | 25-50 |
| Boss | 80-120 | 18-25 | 8-12 | 100-200 | 50-100 |

## Common Mistakes to Avoid

❌ Forgetting to call `complete()`
❌ Not checking if player died: `if (!player.isAlive()) return;`
❌ Not handling player death in combat: `if (!combat.startCombat(...)) return;`
❌ Making enemies too hard or too easy
❌ Infinite loops with no escape
❌ Forgetting to import packages

## Checklist

Before submitting:
- [ ] Level compiles without errors
- [ ] Level is completable
- [ ] Player death is handled
- [ ] `complete()` is called
- [ ] At least one combat
- [ ] Some reward given
- [ ] Code has comments
- [ ] Tested at least twice

## Get Help

1. Look at `ForestLevel.java` example
2. Look at `CaveLevel.java` example
3. Read `STUDENT_QUICKSTART.md`
4. Check `STUDENT_TEMPLATE.java`
5. Ask your teacher!

---

**Keep this handy while coding!** 📌
