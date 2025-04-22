package edu.ntnu.idi.bidata.boardgame.backend.model.tile;

import edu.ntnu.idi.bidata.boardgame.backend.model.property.Property;
import edu.ntnu.idi.bidata.boardgame.backend.util.OutputHandler;

/**
 * The {@link PropertyTile} class is a specialized type of {@link Tile} that represents a property
 * on the game board. A property tile includes a {@link Property} that can be owned or purchased by
 * players.
 *
 * <p>When a player lands on this tile, they may have the opportunity to purchase the associated
 * property.
 *
 * @author Nick Heggø
 * @version 2025.04.18
 */
public class PropertyTile extends Tile {

  private Property property;

  public PropertyTile(int position, Property property) {
    super(position, property.getName());
    setProperty(property);
    setTileAction(
        player ->
            OutputHandler.getInstance()
                .println(
                    "Do you want to purchase %s for %d amount?"
                        .formatted(getProperty().getName(), getProperty().getValue())));
  }

  public Property getProperty() {
    return property;
  }

  public void setProperty(Property property) {
    this.property = property;
  }
}
