package com.spaceinvaders.factory;

import com.spaceinvaders.entities.*;
import com.spaceinvaders.strategy.*;
import com.spaceinvaders.enums.*;

public class EntityFactory {

  public static Player createPlayer(int x, int y) {
    Player player = new Player(x, y);
    player.setMovementStrategy(new PlayerMovement());
    return player;
  }

  public static Invader createInvader(int x, int y, InvaderType type) {
    Invader invader = new Invader(x, y, type);
    invader.setMovementStrategy(new InvaderMovement());
    return invader;
  }

  public static Bullet createPlayerBullet(int x, int y) {
    Bullet bullet = new Bullet(x, y, BulletType.PLAYER);
    bullet.setMovementStrategy(new BulletMovement(8, -1));
    return bullet;
  }

  public static Bullet createInvaderBullet(int x, int y) {
    Bullet bullet = new Bullet(x, y, BulletType.INVADER);
    bullet.setMovementStrategy(new BulletMovement(4, 1));
    return bullet;
  }

  public static PowerUp createPowerUp(int x, int y, PowerUpType type) {
    PowerUp powerUp = new PowerUp(x, y, type);
    powerUp.setMovementStrategy(new BulletMovement(2, 1));
    return powerUp;
  }
}