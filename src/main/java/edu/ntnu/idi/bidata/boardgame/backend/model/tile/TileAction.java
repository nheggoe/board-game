package edu.ntnu.idi.bidata.boardgame.backend.model.tile;


import edu.ntnu.idi.bidata.boardgame.backend.model.Player;

@FunctionalInterface
public interface TileAction {

  void execute(Player player);

}
