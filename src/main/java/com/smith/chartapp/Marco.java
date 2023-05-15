package com.smith.chartapp;

import javax.swing.JFrame;

public class Marco extends JFrame {

  Marco() {
    setTitle("La carta de Smith");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setResizable(false);
    add(new Graficos());
    pack();
    setLocationRelativeTo(null);
    setVisible(true);
  }
}
