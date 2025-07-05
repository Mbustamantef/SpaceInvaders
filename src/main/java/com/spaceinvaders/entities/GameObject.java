package com.spaceinvaders.entities;

import com.spaceinvaders.strategy.MovementStrategy;

public abstract class GameObject {
  protected int x;
  protected int y;
  protected int width;
  protected int height;
  protected boolean active;
  public MovementStrategy movementStrategy;

  public GameObject(int x, int y, int width, int height) {
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
    this.active = true;
  }

  public void setMovementStrategy(MovementStrategy strategy) {
    this.movementStrategy = strategy;
  }

  public void move() {
    if (movementStrategy != null) {
      movementStrategy.move(this);
    }
  }

  public boolean collidesWith(GameObject other) {
    return x < other.x + other.width &&
        x + width > other.x &&
        y < other.y + other.height &&
        y + height > other.y;
  }

  public void destroy() {
    active = false;
  }

  public int getX() { return x; }
  public int getY() { return y; }
  public int getWidth() { return width; }
  public int getHeight() { return height; }
  public boolean isActive() { return active; }

  public void setX(int x) { this.x = x; }
  public void setY(int y) { this.y = y; }
  public void setPosition(int x, int y) { this.x = x; this.y = y; }
}