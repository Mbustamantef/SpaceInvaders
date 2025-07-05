package com.spaceinvaders.core;


public class GameManager {
  private static GameManager instance;
  private int score;
  private int lives;
  private boolean gameRunning;
  private int level;

  private GameManager() {
    score = 0;
    lives = 3;
    gameRunning = false;
    level = 1;
  }

  public static GameManager getInstance() {
    if (instance == null) {
      instance = new GameManager();
    }
    return instance;
  }

  public void startGame() {
    gameRunning = true;
    score = 0;
    lives = 3;
    level = 1;
  }

  public void endGame() {
    gameRunning = false;
  }

  public void addScore(int points) {
    score += points;
  }

  public void loseLife() {
    lives--;
    if (lives <= 0) {
      endGame();
    }
  }

  public void nextLevel() {
    level++;
  }

  public int getScore() {
    return score;
  }

  public int getLives() {
    return lives;
  }

  public boolean isGameRunning() {
    return gameRunning;
  }

  public int getLevel() {
    return level;
  }
}