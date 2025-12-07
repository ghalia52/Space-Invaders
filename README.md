# 🚀 Space Invaders - Design Patterns Project

A Java implementation of the classic Space Invaders game demonstrating five software design patterns.

## 📋 Overview

This academic project showcases practical applications of design patterns in game development. Built using Java Swing, it features dynamic power-ups, organized enemy formations, multiple game states, and an extensible projectile system.

**Academic Year:** 2025-2026  
**Course:** Design Patterns  
**Language:** Java

## 🏗️ Design Patterns

### 1. State Pattern
Manages game states and their transitions.

**Classes:** `State`, `StateMenu`, `StateGame`, `StateWon`, `StateLost`, `Board`

- Menu state for game start
- Game state for active gameplay
- Won/Lost states for game endings
- Smooth transitions between states

### 2. Decorator Pattern
Adds power-ups to the player's ship dynamically.

**Classes:** `Ship`, `BasicShip`, `ShipDecorator`, `SpeedBoostDecorator`, `TripleShotDecorator`, `RapidFireDecorator`, `ShieldDecorator`

- Speed boost for faster movement
- Triple shot for multiple projectiles
- Rapid fire for increased fire rate
- Shield for temporary protection

### 3. Composite Pattern
Organizes game objects in a tree structure.

**Classes:** `GameComponent`, `GameObject`, `GameGroup`, `AlienFormation`, `GameScene`, `AlienObject`, `PlayerObject`

- GameScene manages four groups: players, enemies, projectiles, power-ups
- AlienFormation controls 5x11 rectangular formation (55 aliens)
- Synchronized alien movement with boundary detection
- Uniform handling of individual and grouped objects

### 4. Factory Pattern
Creates different projectile types.

**Classes:** `ProjectileFactory`, `Projectile`, `NormalShot`, `PowerShot`, `AlienBomb`

- Normal shots for standard attacks
- Power shots for enhanced damage
- Alien bombs for enemy attacks

### 5. Movement Pattern
Handles player movement using strategy-like approach.

**Classes:** `Move`, `MoveLeft`, `MoveRight`, `MoveDown`, `MovementProvider`

- Encapsulated movement logic
- Easy to extend with new movement types

## 📁 Project Structure

```
src/
├── entities/          # Game entities (Player, Alien, Projectiles)
├── game/              # Core game logic (Board)
├── movement/          # Movement strategies
├── patterns/
│   ├── composite/     # Composite pattern classes
│   ├── decorator/     # Decorator pattern classes
│   ├── factory/       # Factory pattern classes
│   └── state/         # State pattern classes
├── ui/                # User interface components
└── utils/             # Utilities (Logger, Constants, Strings)
```

## 🚀 Getting Started

### Prerequisites
- Java JDK 11 or higher
- Any Java IDE (IntelliJ IDEA, Eclipse, VS Code)

### Installation

1. Clone the repository
   ```bash
   git clone https://github.com/ghalia52/space-invaders.git
   cd space-invaders
   ```

2. Open the project in your IDE

3. Run `Main.java` from the utils package

## 🎮 Gameplay

### Controls
- `←` or `A` - Move Left (bounded at X = 30)
- `→` or `D` - Move Right (bounded at X = 770)
- `SPACE` - Fire (300ms cooldown between shots)
- `ESC` - Pause/Resume

### Game Mechanics
- **Player Speed:** 8 pixels per frame
- **Projectile Speed:** 15 pixels upward per frame
- **Alien Formation:** 5 rows × 11 columns = 55 aliens
- **Alien Speed:** 1 pixel per frame (horizontal movement + descent)
- **Score per Alien:** 10 points
- **Game Loop:** 60 FPS (16ms update interval)

### Objective
- **Destroy all 55 aliens** to win the game
- **Avoid letting aliens reach Y > 500** (player level)
- **Maximize your score** - Each alien is worth 10 points
- **Use strategic movement** - Dodge while attacking

## ✨ Features

- **5x11 Alien Formation** - 55 aliens in organized rectangular pattern
- **Dynamic Movement** - Aliens move horizontally and descend when reaching edges
- **Collision Detection** - Accurate projectile-alien collision system
- **Score System** - Earn 10 points per alien destroyed
- **Victory Condition** - Destroy all aliens to win
- **Game Over Condition** - Lose if aliens reach player position (Y > 500)
- **Pause Functionality** - Pause/resume with ESC key
- **Smooth Controls** - Responsive keyboard input with boundary detection
- **Visual Effects** - Gradient background, stars, glowing projectiles, and ship effects
- **Comprehensive Logging** - Track all game events and pattern usage
- **60 FPS Game Loop** - Smooth animation with 16ms update timer
- **Shot Cooldown** - 300ms cooldown between shots for balanced gameplay

## 🛠️ Technologies

- Java SE 11+
- Java Swing (GUI)
- AWT Graphics (Rendering)

## 📝 Logging

The game logs important events to `game.log`:
- State transitions
- Decorator applications
- Composite operations
- Factory creations
- Game events

## 👥 Authors

 **Ghalia Benaissa** & **Ghada Mhadhbi** 



*Developed with ☕ for Design Patterns Course 2025-2026*