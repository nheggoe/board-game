package edu.ntnu.idi.bidata;

import java.util.ArrayList;
import java.util.List;


public class Board {

  private final List<Tiles> tiles;

  public Board(){
    tiles = new ArrayList<>();
    initializeBoard();
  }

  public void initializeBoard(){
    tiles.add(new Tiles(0, "Start"));
    tiles.add(new Tiles(1, "A"));
    tiles.add(new Tiles(2, "B"));
    tiles.add(new Tiles(3, "C"));
  }


  public Tiles getTile(int position){
    if(position < 0 || position >= tiles.size()){
      throw new IllegalArgumentException();
    }
    return tiles.get(position);
  }




}
