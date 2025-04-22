package edu.ntnu.idi.bidata.boardgame.backend.model.tile;

public sealed interface Tile permits CornerTile, OwnableTile, TaxTile {}
