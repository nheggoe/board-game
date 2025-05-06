package edu.ntnu.idi.bidata.boardgame.games.monopoly.model.tile;

public sealed interface MonopolyTile
    permits CornerMonopolyTile, OwnableMonopolyTile, TaxMonopolyTile {}
