package edu.ntnu.idi.bidata.boardgame.backend.model.tile;

import edu.ntnu.idi.bidata.boardgame.backend.core.GameEngine;
import edu.ntnu.idi.bidata.boardgame.backend.model.Player;
import edu.ntnu.idi.bidata.boardgame.backend.model.ownable.InsufficientFundsException;
import edu.ntnu.idi.bidata.boardgame.backend.model.ownable.Ownable;
import edu.ntnu.idi.bidata.boardgame.backend.model.ownable.Property;
import edu.ntnu.idi.bidata.boardgame.backend.model.ownable.Railroad;
import edu.ntnu.idi.bidata.boardgame.backend.model.ownable.Utility;
import edu.ntnu.idi.bidata.boardgame.backend.util.InputHandler;
import edu.ntnu.idi.bidata.boardgame.backend.util.OutputHandler;
import edu.ntnu.idi.bidata.boardgame.backend.util.StringFormatter;

@FunctionalInterface
public interface TileAction {

  void execute(Player player);

  static TileAction of(Tile tile) {
    return switch (tile) {
      case OwnableTile(Ownable ownable) -> ownableAction(ownable);
      case TaxTile(int amount) -> player -> player.pay(amount);
      case CornerTile cornerTile -> getCornerTileAction(cornerTile);
    };
  }

  private static TileAction getCornerTileAction(CornerTile tile) {
    return switch (tile) {
      case GoToJailTile unused -> player -> GameEngine.getInstance().goToJail(player);
      case FreeParkingTile unused -> player -> OutputHandler.getInstance().println("Free parking");
      case JailTile unused -> player -> OutputHandler.getInstance().println("Visiting Jail");
      case StartTile unused -> player -> OutputHandler.getInstance().println("On start Tile");
    };
  }

  private static TileAction ownableAction(Ownable ownable) {
    return player -> {
      if (!player.isOwnerOf(ownable) && !confirmPurchase(player, ownable)) {
        player.pay(ownable.rent());
      } else {
        OutputHandler.getInstance().println("Welcome owner");
      }
    };
  }

  private static boolean confirmPurchase(Player player, Ownable ownable) {
    if (player.hasSufficientFunds(ownable.price())) {
      String prompt =
          switch (ownable) {
            case Property(String name, var color, int price) ->
                "Do you want to purchase %s of %s category for $%d?"
                    .formatted(name, StringFormatter.formatEnum(color), price);
            case Railroad(int price) -> "Do you want to purchase railroad for $" + price + " ?";
            case Utility(String name, int price) ->
                "Do you want to purchase %s for $%d?".formatted(name, price);
          };
      OutputHandler.getInstance().println(prompt);
      if (InputHandler.getInstance().nextLine().equals("yes")) {
        return processPurchase(player, ownable);
      }
    }
    return false;
  }

  private static boolean processPurchase(Player player, Ownable ownable) {
    try {
      player.purchase(ownable);
      return true;
    } catch (InsufficientFundsException e) {
      OutputHandler.getInstance().println(e.getMessage());
      return false;
    }
  }
}
