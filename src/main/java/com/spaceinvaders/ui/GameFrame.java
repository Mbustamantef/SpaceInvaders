package com.spaceinvaders.ui;

import javax.swing.*;

public class GameFrame extends JFrame {

  public GameFrame() {
    this.add(new GamePanel());
    this.setTitle("Space Invaders - Java 21");
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setResizable(false);
    this.pack();
    this.setVisible(true);
    this.setLocationRelativeTo(null);
  }
}