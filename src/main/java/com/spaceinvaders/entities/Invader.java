package com.spaceinvaders.entities;

import com.spaceinvaders.factory.EntityFactory;
import com.spaceinvaders.core.GameManager;
import com.spaceinvaders.enums.InvaderType;

public class Invader extends GameObject {

  public Object movementStrategy;
  private InvaderType type;
  private int points;
  private double shootChance;

  public Invader(int x, int y, InvaderType type) {
    super(x, y, 30, 20);
    this.type = type;
    setStatsBasedOnType();
  }

  private void setStatsBasedOnType() {
    switch (type) {
      case BASIC:
        points = 10;
        shootChance = 0.001;
        break;
      case MEDIUM:
        points = 20;
        shootChance = 0.002;
        break;
      case FAST:
        points = 30;
        shootChance = 0.003;
        break;
    }
  }

  public Bullet tryShoot() {
    if (Math.random() < shootChance) {
      return EntityFactory.createInvaderBullet(x + width/2, y + height);
    }
    return null;
  }

  public int getPoints() {
    return points;
  }

  public InvaderType getType() {
    return type;
  }

  public void destroy() {
    super.destroy();
    GameManager.getInstance().addScore(points);
  }
}