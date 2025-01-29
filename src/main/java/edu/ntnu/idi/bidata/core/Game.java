package edu.ntnu.idi.bidata.core;

import java.util.Scanner;

public class Game {
  private boolean running;

  public Game() {
    // default constructor
  }

  public void run(){
    running = true;
    System.out.println("Do you want to exit? (y/n)");
    Scanner scanner = new Scanner(System.in);
    while (running) {
      if (validateExitString(scanner.nextLine())) {
        this.terminate();
      }
    }
  }

  private boolean validateExitString(String s) {
    return s.equalsIgnoreCase("yes") || s.equalsIgnoreCase("y");
  }

  private void terminate() {
    running = false;
  }
}
