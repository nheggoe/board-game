package edu.ntnu.idi.bidata;

public class Tiles {
  private String name;
  private int position;

  public Tiles(int position, String name) {
   setName(name);
   setPosition(position);
  }

  public int getPosition() {
    return position;
  }

  public void setPosition(int position) {
    this.position = position;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
      this.name = name;
  }
}
