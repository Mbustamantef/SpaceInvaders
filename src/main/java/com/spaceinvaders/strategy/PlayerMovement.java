package com.spaceinvaders.strategy;

import com.spaceinvaders.entities.GameObject;

public class PlayerMovement implements MovementStrategy {
  private int speed = 5;

  @Override
  public void move(GameObject gameObject) {
    // No movement by default, controlled by input
  }

  public void moveLeft(GameObject gameObject) {
    gameObject.setX(Math.max(0, gameObject.getX() - speed));
  }

  public void moveRight(GameObject gameObject, int screenWidth) {
    gameObject.setX(Math.min(screenWidth - gameObject.getWidth(),
        gameObject.getX() + speed));
  }
}