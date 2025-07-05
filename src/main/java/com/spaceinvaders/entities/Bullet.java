package com.spaceinvaders.entities;

import com.spaceinvaders.enums.BulletType;

public class Bullet extends GameObject {
  private BulletType type;
  private int damage;

  public Bullet(int x, int y, BulletType type) {
    super(x, y, 4, 10);
    this.type = type;
    this.damage = (type == BulletType.PLAYER) ? 25 : 10;
  }

  public boolean isOutOfBounds(int screenHeight) {
    return y < 0 || y > screenHeight;
  }

  public int getDamage() {
    return damage;
  }

  public BulletType getType() {
    return type;
  }
}