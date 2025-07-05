package com.spaceinvaders.strategy;

import com.spaceinvaders.entities.GameObject;

public class BulletMovement implements MovementStrategy {
  private int speed;
  private int direction;

  public BulletMovement(int speed, int direction) {
    this.speed = speed;
    this.direction = direction;
  }

  @Override
  public void move(GameObject gameObject) {
    gameObject.setY(gameObject.getY() + (speed * direction));
  }
}