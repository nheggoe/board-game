package edu.ntnu.idi.bidata.boardgame.games.monopoly.model.ownable;

public record Railroad(int price) implements Ownable {

  @Override
  public int rent() {
    // todo Fix
    // var game = GameEngine.getInstance().getGame().orElseThrow();
    // var owner =
    //     game.stream().filter(p -> p.getOwnedAssets().contains(this)).findFirst().orElse(null);
    //
    // if (owner == null) {
    //   return 25;
    // }
    //
    // long railroadsOwned =
    //     owner.getOwnedAssets().stream().filter(Railroad.class::isInstance).count();
    //
    // return switch ((int) railroadsOwned) {
    //   case 2 -> 50;
    //   case 3 -> 100;
    //   case 4 -> 200;
    //   default -> 25;
    // };
    return 25;
  }
}
