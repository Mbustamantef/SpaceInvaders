package com.spaceinvaders.ui;

import com.spaceinvaders.core.GameEngine;
import com.spaceinvaders.core.GameManager;
import com.spaceinvaders.entities.*;
import com.spaceinvaders.enums.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GamePanel extends JPanel implements ActionListener {
  private static final int PANEL_WIDTH = 800;
  private static final int PANEL_HEIGHT = 600;
  private static final int DELAY = 50;

  private GameEngine gameEngine;
  private Timer timer;
  private boolean[] keys;

  public GamePanel() {
    this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
    this.setBackground(Color.BLACK);
    this.setFocusable(true);
    this.addKeyListener(new MyKeyAdapter());

    keys = new boolean[256];
    gameEngine = new GameEngine();
    timer = new Timer(DELAY, this);
    timer.start();
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    draw(g);
  }

  public void draw(Graphics g) {
    Graphics2D g2d = (Graphics2D) g;
    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    drawPlayer(g2d);
    drawInvaders(g2d);
    drawBullets(g2d);
    drawPowerUps(g2d);
    drawUI(g2d);

    if (!gameEngine.getGameManager().isGameRunning()) {
      drawGameOver(g2d);
    }
  }

  private void drawPlayer(Graphics2D g2d) {
    Player player = gameEngine.getPlayer();
    if (player.isActive()) {
      g2d.setColor(Color.CYAN);

      int x = player.getX();
      int y = player.getY();
      int w = player.getWidth();
      int h = player.getHeight();

      int[] xPoints = {x + w/2, x, x + w/4, x + 3*w/4, x + w};
      int[] yPoints = {y, y + h, y + 3*h/4, y + 3*h/4, y + h};
      g2d.fillPolygon(xPoints, yPoints, 5);

      g2d.setColor(Color.WHITE);
      g2d.drawPolygon(xPoints, yPoints, 5);

      drawHealthBar(g2d, player);
    }
  }

  private void drawHealthBar(Graphics2D g2d, Player player) {
    int barWidth = 40;
    int barHeight = 4;
    int x = player.getX();
    int y = player.getY() - 10;

    g2d.setColor(Color.RED);
    g2d.fillRect(x, y, barWidth, barHeight);

    g2d.setColor(Color.GREEN);
    int healthWidth = (int) ((player.getHealth() / 100.0) * barWidth);
    g2d.fillRect(x, y, healthWidth, barHeight);

    g2d.setColor(Color.WHITE);
    g2d.drawRect(x, y, barWidth, barHeight);
  }

  private void drawInvaders(Graphics2D g2d) {
    for (Invader invader : gameEngine.getInvaders()) {
      if (invader.isActive()) {
        Color color = switch (invader.getType()) {
          case BASIC -> Color.GREEN;
          case MEDIUM -> Color.YELLOW;
          case FAST -> Color.RED;
        };

        g2d.setColor(color);

        int x = invader.getX();
        int y = invader.getY();
        int w = invader.getWidth();
        int h = invader.getHeight();

        g2d.fillRect(x + 5, y, w - 10, h/3);
        g2d.fillRect(x, y + h/3, w, h/3);
        g2d.fillRect(x + 3, y + 2*h/3, 6, h/3);
        g2d.fillRect(x + w - 9, y + 2*h/3, 6, h/3);

        g2d.setColor(Color.WHITE);
        g2d.drawRect(x, y, w, h);
      }
    }
  }

  private void drawBullets(Graphics2D g2d) {
    for (Bullet bullet : gameEngine.getBullets()) {
      if (bullet.isActive()) {
        if (bullet.getType() == BulletType.PLAYER) {
          g2d.setColor(Color.CYAN);
        } else {
          g2d.setColor(Color.RED);
        }

        g2d.fillOval(bullet.getX(), bullet.getY(), bullet.getWidth(), bullet.getHeight());
      }
    }
  }

  private void drawPowerUps(Graphics2D g2d) {
    for (PowerUp powerUp : gameEngine.getPowerUps()) {
      if (powerUp.isActive()) {
        Color color = switch (powerUp.getType()) {
          case HEALTH -> Color.GREEN;
          case FIRE_RATE -> Color.ORANGE;
          case EXTRA_LIFE -> Color.MAGENTA;
        };

        g2d.setColor(color);
        g2d.fillOval(powerUp.getX(), powerUp.getY(), powerUp.getWidth(), powerUp.getHeight());

        g2d.setColor(Color.WHITE);
        g2d.drawOval(powerUp.getX(), powerUp.getY(), powerUp.getWidth(), powerUp.getHeight());

        String symbol = switch (powerUp.getType()) {
          case HEALTH -> "+";
          case FIRE_RATE -> "F";
          case EXTRA_LIFE -> "L";
        };

        g2d.setFont(new Font("Arial", Font.BOLD, 12));
        FontMetrics fm = g2d.getFontMetrics();
        int textX = powerUp.getX() + (powerUp.getWidth() - fm.stringWidth(symbol)) / 2;
        int textY = powerUp.getY() + (powerUp.getHeight() + fm.getAscent()) / 2;
        g2d.drawString(symbol, textX, textY);
      }
    }
  }

  private void drawUI(Graphics2D g2d) {
    GameManager gm = gameEngine.getGameManager();
    g2d.setColor(Color.WHITE);
    g2d.setFont(new Font("Arial", Font.BOLD, 16));

    g2d.drawString("Score: " + gm.getScore(), 10, 25);
    g2d.drawString("Lives: " + gm.getLives(), 10, 45);
    g2d.drawString("Level: " + gm.getLevel(), 10, 65);

    long activeInvaders = gameEngine.getInvaders().stream()
        .filter(invader -> invader.isActive())
        .count();
    g2d.drawString("Invaders: " + activeInvaders, 10, 85);

    g2d.setFont(new Font("Arial", Font.PLAIN, 12));
    g2d.drawString("WASD to move, SPACE to shoot, Q to quit", 10, PANEL_HEIGHT - 10);

    if (activeInvaders == 0 && gm.isGameRunning()) {
      drawLevelComplete(g2d);
    }
  }

  private void drawLevelComplete(Graphics2D g2d) {
    g2d.setColor(new Color(0, 255, 0, 100));
    g2d.fillRect(0, 0, PANEL_WIDTH, PANEL_HEIGHT);

    g2d.setColor(Color.WHITE);
    g2d.setFont(new Font("Arial", Font.BOLD, 36));
    String levelComplete = "LEVEL COMPLETE!";
    FontMetrics fm = g2d.getFontMetrics();
    int x = (PANEL_WIDTH - fm.stringWidth(levelComplete)) / 2;
    int y = PANEL_HEIGHT / 2;
    g2d.drawString(levelComplete, x, y);

    g2d.setFont(new Font("Arial", Font.BOLD, 18));
    String nextLevel = "Preparing Level " + (gameEngine.getGameManager().getLevel() + 1) + "...";
    fm = g2d.getFontMetrics();
    x = (PANEL_WIDTH - fm.stringWidth(nextLevel)) / 2;
    g2d.drawString(nextLevel, x, y + 50);
  }

  private void drawGameOver(Graphics2D g2d) {
    g2d.setColor(new Color(0, 0, 0, 150));
    g2d.fillRect(0, 0, PANEL_WIDTH, PANEL_HEIGHT);

    g2d.setColor(Color.WHITE);
    g2d.setFont(new Font("Arial", Font.BOLD, 48));
    String gameOver = "GAME OVER";
    FontMetrics fm = g2d.getFontMetrics();
    int x = (PANEL_WIDTH - fm.stringWidth(gameOver)) / 2;
    int y = PANEL_HEIGHT / 2;
    g2d.drawString(gameOver, x, y);

    g2d.setFont(new Font("Arial", Font.BOLD, 24));
    String score = "Final Score: " + gameEngine.getGameManager().getScore();
    fm = g2d.getFontMetrics();
    x = (PANEL_WIDTH - fm.stringWidth(score)) / 2;
    g2d.drawString(score, x, y + 60);

    g2d.setFont(new Font("Arial", Font.PLAIN, 16));
    String restart = "Press R to restart or Q to quit";
    fm = g2d.getFontMetrics();
    x = (PANEL_WIDTH - fm.stringWidth(restart)) / 2;
    g2d.drawString(restart, x, y + 100);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (gameEngine.getGameManager().isGameRunning()) {
      handleInput();
      gameEngine.update();
    }
    repaint();
  }

  private void handleInput() {
    if (keys[KeyEvent.VK_A] || keys[KeyEvent.VK_LEFT]) {
      gameEngine.handleInput("left");
    }
    if (keys[KeyEvent.VK_D] || keys[KeyEvent.VK_RIGHT]) {
      gameEngine.handleInput("right");
    }
    if (keys[KeyEvent.VK_SPACE]) {
      gameEngine.handleInput("shoot");
    }
  }

  private class MyKeyAdapter extends KeyAdapter {
    @Override
    public void keyPressed(KeyEvent e) {
      keys[e.getKeyCode()] = true;

      if (e.getKeyCode() == KeyEvent.VK_Q) {
        System.exit(0);
      }

      if (e.getKeyCode() == KeyEvent.VK_R && !gameEngine.getGameManager().isGameRunning()) {
        gameEngine = new GameEngine();
      }
    }

    @Override
    public void keyReleased(KeyEvent e) {
      keys[e.getKeyCode()] = false;
    }
  }
}