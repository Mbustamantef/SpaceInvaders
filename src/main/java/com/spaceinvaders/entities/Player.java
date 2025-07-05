package com.spaceinvaders.entities;

import com.spaceinvaders.factory.EntityFactory;

public class Player extends GameObject {
  private int health;
  private int fireRate;
  private long lastShotTime;

  public Player(int x, int y) {
    super(x, y, 40, 30);
    this.health = 100;
    this.fireRate = 300;
    this.lastShotTime = 0;
  }

  public boolean canShoot() {
    return System.currentTimeMillis() - lastShotTime >= fireRate;
  }

  public Bullet shoot() {
    if (canShoot()) {
      lastShotTime = System.currentTimeMillis();
      return EntityFactory.createPlayerBullet(x + width/2, y);
    }
    return null;
  }

  public void takeDamage(int damage) {
    health -= damage;
    if (health <= 0) {
      destroy();
    }
  }

  public void heal(int amount) {
    health = Math.min(100, health + amount);
  }

  public int getHealth() {
    return health;
  }

  public void upgradeFireRate() {
    fireRate = Math.max(100, fireRate - 50);
  }
}