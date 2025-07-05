package com.spaceinvaders.core;

import com.spaceinvaders.entities.*;
import com.spaceinvaders.factory.EntityFactory;
import com.spaceinvaders.strategy.InvaderMovement;
import com.spaceinvaders.strategy.PlayerMovement;
import com.spaceinvaders.enums.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameEngine {
  private static final int SCREEN_WIDTH = 800;
  private static final int SCREEN_HEIGHT = 600;

  private Player player;
  private List<Invader> invaders;
  private List<Bullet> bullets;
  private List<PowerUp> powerUps;
  private GameManager gameManager;
  private Random random;

  public GameEngine() {
    gameManager = GameManager.getInstance();
    random = new Random();
    initializeGame();
  }

  private void initializeGame() {
    player = EntityFactory.createPlayer(SCREEN_WIDTH / 2, SCREEN_HEIGHT - 50);
    invaders = new ArrayList<>();
    bullets = new ArrayList<>();
    powerUps = new ArrayList<>();

    createInvaderFormation();
    gameManager.startGame();
  }

  private void createInvaderFormation() {
    com.spaceinvaders.strategy.InvaderMovement.resetGlobalDirection();

    for (int row = 0; row < 5; row++) {
      for (int col = 0; col < 10; col++) {
        int x = 50 + col * 60;
        int y = 50 + row * 40;

        InvaderType type = switch (row) {
          case 0, 1 -> InvaderType.FAST;
          case 2, 3 -> InvaderType.MEDIUM;
          default -> InvaderType.BASIC;
        };

        invaders.add(EntityFactory.createInvader(x, y, type));
      }
    }

    System.out.println("Created " + invaders.size() + " invaders");
  }

  public void update() {
    if (!gameManager.isGameRunning()) {
      return;
    }

    updatePlayer();
    updateInvaders();
    updateBullets();
    updatePowerUps();
    checkCollisions();
    checkWinCondition();
    cleanupDestroyedEntities();
  }

  private void updatePlayer() {
    player.move();
  }

  private void updateInvaders() {
    boolean shouldChangeDirection = false;
    int currentDirection = com.spaceinvaders.strategy.InvaderMovement.getGlobalDirection();

    // Verificar si algún invasor está en el borde
    for (Invader invader : invaders) {
      if (invader.isActive()) {
        int currentX = invader.getX();
        int nextX = currentX + (1 * currentDirection);

        if ((currentDirection == 1 && nextX + invader.getWidth() >= SCREEN_WIDTH - 10) ||
            (currentDirection == -1 && nextX <= 10)) {
          shouldChangeDirection = true;
          System.out.println("Should change direction! Invader at X: " + currentX + ", Direction: " + currentDirection);
          break;
        }
      }
    }

    // Si necesitan cambiar dirección
    if (shouldChangeDirection) {
      com.spaceinvaders.strategy.InvaderMovement.changeGlobalDirection();

      // Bajar todos los invasores
      for (Invader invader : invaders) {
        if (invader.isActive()) {
          invader.setY(invader.getY() + 20);
        }
      }
    } else {
      // Mover horizontalmente
      for (Invader invader : invaders) {
        if (invader.isActive()) {
          invader.move();
        }
      }
    }

    // Disparo de invasores
    for (Invader invader : invaders) {
      if (invader.isActive()) {
        Bullet bullet = invader.tryShoot();
        if (bullet != null) {
          bullets.add(bullet);
        }
      }
    }
  }

  private void updateBullets() {
    for (Bullet bullet : bullets) {
      if (bullet.isActive()) {
        bullet.move();

        if (bullet.isOutOfBounds(SCREEN_HEIGHT)) {
          bullet.destroy();
        }
      }
    }
  }

  private void updatePowerUps() {
    for (PowerUp powerUp : powerUps) {
      if (powerUp.isActive()) {
        powerUp.move();

        if (powerUp.isOutOfBounds(SCREEN_HEIGHT)) {
          powerUp.destroy();
        }
      }
    }
  }

  private void checkCollisions() {
    for (Bullet bullet : bullets) {
      if (!bullet.isActive()) continue;

      if (bullet.getType() == BulletType.PLAYER) {
        for (Invader invader : invaders) {
          if (invader.isActive() && bullet.collidesWith(invader)) {
            bullet.destroy();
            invader.destroy();

            if (random.nextDouble() < 0.1) {
              spawnPowerUp(invader.getX(), invader.getY());
            }
            break;
          }
        }
      } else {
        if (player.isActive() && bullet.collidesWith(player)) {
          bullet.destroy();
          player.takeDamage(bullet.getDamage());

          if (!player.isActive()) {
            gameManager.loseLife();
          }
        }
      }
    }

    for (PowerUp powerUp : powerUps) {
      if (powerUp.isActive() && player.isActive() && powerUp.collidesWith(player)) {
        powerUp.applyEffect(player);
      }
    }
  }

  private void spawnPowerUp(int x, int y) {
    PowerUpType[] types = PowerUpType.values();
    PowerUpType randomType = types[random.nextInt(types.length)];
    powerUps.add(EntityFactory.createPowerUp(x, y, randomType));
  }

  private void cleanupDestroyedEntities() {
    invaders.removeIf(invader -> !invader.isActive());
    bullets.removeIf(bullet -> !bullet.isActive());
    powerUps.removeIf(powerUp -> !powerUp.isActive());
  }

  private void checkWinCondition() {
    long activeInvaders = invaders.stream().filter(GameObject::isActive).count();

    if (activeInvaders == 0) {
      gameManager.nextLevel();

      try {
        Thread.sleep(1500);
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }

      createInvaderFormation();

      for (Invader invader : invaders) {
        if (invader.movementStrategy instanceof InvaderMovement) {
          InvaderMovement movement = (InvaderMovement) invader.movementStrategy;
          movement.setSpeed(Math.min(5, gameManager.getLevel()));
        }
      }
    }
  }

  public void handleInput(String input) {
    if (!gameManager.isGameRunning() || !player.isActive()) {
      return;
    }

    if (player.movementStrategy instanceof PlayerMovement) {
      PlayerMovement movement = (PlayerMovement) player.movementStrategy;

      switch (input.toLowerCase()) {
        case "left":
          movement.moveLeft(player);
          break;
        case "right":
          movement.moveRight(player, SCREEN_WIDTH);
          break;
        case "shoot":
          Bullet bullet = player.shoot();
          if (bullet != null) {
            bullets.add(bullet);
          }
          break;
      }
    }
  }

  public Player getPlayer() { return player; }
  public List<Invader> getInvaders() { return invaders; }
  public List<Bullet> getBullets() { return bullets; }
  public List<PowerUp> getPowerUps() { return powerUps; }
  public GameManager getGameManager() { return gameManager; }
}