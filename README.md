# Space Invaders - Java 21 Implementation

A classic Space Invaders game implemented in Java 21 demonstrating the application of three fundamental design patterns: **Singleton**, **Strategy**, and **Factory**.

## 🎮 Game Features

- **Classic Space Invaders gameplay** with modern Java 21 features
- **Visual interface** using Java Swing with 2D graphics
- **Progressive difficulty** - enemies get faster each level
- **Power-ups system** with health, fire rate, and extra life bonuses
- **Real-time UI** displaying score, lives, level, and enemy count
- **Smooth controls** with keyboard input handling

## 🏗️ Design Patterns Implementation

### 1. Singleton Pattern - `GameManager`
```java
public static GameManager getInstance() {
    if (instance == null) {
        instance = new GameManager();
    }
    return instance;
}
```
**Purpose**: Manages global game state (score, lives, level) with a single instance accessible throughout the application.

### 2. Strategy Pattern - `MovementStrategy`
```java
public interface MovementStrategy {
    void move(GameObject gameObject);
}
```
**Implementations**:
- `PlayerMovement` - Input-controlled movement
- `InvaderMovement` - Automatic formation movement with direction changes
- `BulletMovement` - Linear movement with speed and direction

**Purpose**: Allows dynamic behavior changes for game entities without modifying their core code.

### 3. Factory Pattern - `EntityFactory`
```java
public static Player createPlayer(int x, int y) {
    Player player = new Player(x, y);
    player.setMovementStrategy(new PlayerMovement());
    return player;
}
```
**Purpose**: Centralizes object creation and automatically configures appropriate movement strategies.

## 📁 Project Structure

```
SpaceInvaders/
├── src/main/java/com/spaceinvaders/
│   ├── SpaceInvaders.java                 # Main class
│   ├── core/
│   │   ├── GameManager.java              # Singleton - Game state management
│   │   └── GameEngine.java               # Main game logic
│   ├── entities/
│   │   ├── GameObject.java               # Base class for all game entities
│   │   ├── Player.java                   # Player spaceship
│   │   ├── Invader.java                  # Enemy invaders
│   │   ├── Bullet.java                   # Projectiles
│   │   └── PowerUp.java                  # Collectible bonuses
│   ├── strategy/
│   │   ├── MovementStrategy.java         # Strategy pattern interface
│   │   ├── PlayerMovement.java           # Player movement implementation
│   │   ├── InvaderMovement.java          # Invader movement implementation
│   │   └── BulletMovement.java           # Bullet movement implementation
│   ├── factory/
│   │   └── EntityFactory.java            # Factory pattern implementation
│   ├── enums/
│   │   ├── InvaderType.java              # Types of invaders
│   │   ├── BulletType.java               # Types of bullets
│   │   └── PowerUpType.java              # Types of power-ups
│   └── ui/
│       ├── GamePanel.java                # Game rendering and input
│       └── GameFrame.java                # Main window
```

## 🚀 Getting Started

### Prerequisites
- **Java 21** or higher
- Any Java IDE (IntelliJ IDEA, Eclipse, VS Code)
- Or command line with `javac` and `java`

### Compilation and Execution

#### Using Command Line
```bash
# Navigate to project root
cd SpaceInvaders

# Compile all Java files
javac -d build src/main/java/com/spaceinvaders/*.java src/main/java/com/spaceinvaders/**/*.java

# Run the game
java -cp build com.spaceinvaders.SpaceInvaders
```

#### Using Gradle (if build.gradle present)
```bash
# Compile and run
./gradlew run

# Or compile and run main class
./gradlew :SpaceInvaders.main()
```

## 🎯 Game Controls

| Key | Action |
|-----|--------|
| **A** / **←** | Move spaceship left |
| **D** / **→** | Move spaceship right |
| **SPACE** | Shoot |
| **Q** | Quit game |
| **R** | Restart (after game over) |

## 🎮 Gameplay

1. **Objective**: Destroy all invaders to advance to the next level
2. **Player Health**: Displayed as a health bar above the spaceship
3. **Invader Formation**: 5 rows of invaders move in classic formation pattern
4. **Power-ups**: Randomly drop when invaders are destroyed
    - 🟢 **Health** (+): Restores 25 health points
    - 🟠 **Fire Rate** (F): Increases shooting speed
    - 🟣 **Extra Life** (L): Bonus points
5. **Progressive Difficulty**: Each level increases invader speed

## 🎨 Visual Elements

- **Player Spaceship**: Cyan triangular ship with health bar
- **Invaders**: Color-coded by type
    - 🟢 Green: Basic invaders (10 points)
    - 🟡 Yellow: Medium invaders (20 points)
    - 🔴 Red: Fast invaders (30 points)
- **Bullets**: Cyan (player) vs Red (invaders)
- **Power-ups**: Colored circles with symbols
- **UI**: Real-time score, lives, level, and invader count

## 🏆 Design Pattern Benefits

### Singleton (GameManager)
- ✅ Centralized game state management
- ✅ Global access without global variables
- ✅ Single source of truth for game data

### Strategy (MovementStrategy)
- ✅ Interchangeable behaviors at runtime
- ✅ Easy to extend with new movement types
- ✅ Separation of concerns
- ✅ Open/Closed principle compliance

### Factory (EntityFactory)
- ✅ Consistent object creation
- ✅ Automatic configuration of dependencies
- ✅ Maintainable and scalable code
- ✅ Encapsulation of object creation logic

## 🔧 Technical Features

- **Java 21 Features**: Pattern matching in switch expressions
- **Clean Architecture**: Separated concerns with package organization
- **Performance**: Optimized rendering at 20 FPS
- **Memory Management**: Efficient object cleanup and reuse
- **Exception Handling**: Robust error handling throughout

## 🤝 Contributing

This project serves as an educational example of design pattern implementation. Feel free to:

1. Fork the repository
2. Add new features or patterns
3. Improve the game mechanics
4. Enhance the visual design
5. Submit pull requests

## 📚 Learning Objectives

This implementation demonstrates:
- **Design Pattern Application** in real-world scenarios
- **Object-Oriented Programming** principles
- **Java 21 Modern Features** usage
- **Game Development** fundamentals
- **Clean Code** practices
- **Software Architecture** design

## 📄 License

This project is created for educational purposes. Feel free to use, modify, and distribute for learning and teaching design patterns.

---

**Built with ❤️ using Java 21 and Design Patterns**