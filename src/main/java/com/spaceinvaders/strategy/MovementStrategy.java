package com.spaceinvaders.strategy;

import com.spaceinvaders.entities.GameObject;

public interface MovementStrategy {
  void move(GameObject gameObject);
}