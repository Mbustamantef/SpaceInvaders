package com.spaceinvaders.entities;

import com.spaceinvaders.core.GameManager;
import com.spaceinvaders.enums.PowerUpType;

public class PowerUp extends GameObject {
  private PowerUpType type;

  public PowerUp(int x, int y, PowerUpType type) {
    super(x, y, 20, 20);
    this.type = type;
  }

  public void applyEffect(Player player) {
    switch (type) {
      case HEALTH:
        player.heal(25);
        break;
      case FIRE_RATE:
        player.upgradeFireRate();
        break;
      case EXTRA_LIFE:
        GameManager.getInstance().addScore(0);
        break;
    }
    destroy();
  }

  public PowerUpType getType() {
    return type;
  }

  public boolean isOutOfBounds(int screenHeight) {
    return y > screenHeight;
  }
}