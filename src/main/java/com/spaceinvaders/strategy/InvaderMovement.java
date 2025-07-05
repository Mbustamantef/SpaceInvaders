package com.spaceinvaders.strategy;

import com.spaceinvaders.entities.GameObject;

public class InvaderMovement implements MovementStrategy {
  private int speed = 1;
  private static int globalDirection = 1;

  @Override
  public void move(GameObject gameObject) {
    gameObject.setX(gameObject.getX() + (speed * globalDirection));
  }

  public static void changeGlobalDirection() {
    globalDirection *= -1;
    System.out.println("Direction changed to: " + (globalDirection == 1 ? "RIGHT" : "LEFT"));
  }

  public static void resetGlobalDirection() {
    globalDirection = 1;
    System.out.println("Direction reset to RIGHT");
  }

  public void setSpeed(int speed) {
    this.speed = speed;
  }

  public static int getGlobalDirection() {
    return globalDirection;
  }
}