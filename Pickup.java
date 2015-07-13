package scripts;

import java.awt.Point;
import java.util.Arrays;

import org.tribot.api.General;
import org.tribot.api.input.Mouse;
import org.tribot.api.types.generic.Filter;
import org.tribot.api2007.Camera;
import org.tribot.api2007.GroundItems;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Players;
import org.tribot.api2007.ext.Filters;
import org.tribot.api2007.types.RSGroundItem;
import org.tribot.api2007.types.RSItemDefinition;
import org.tribot.api2007.types.RSTile;

public class Pickup {

  final static RSTile PTILE1 = new RSTile(3109, 3432, 0);
  final static RSTile PTILE2 = new RSTile(3109, 3433, 0);
  final static RSTile PTILE3 = new RSTile(3109, 3434, 0);
  final static RSTile PTILE4 = new RSTile(3103, 3425, 0);
  final static RSTile PTILE5 = new RSTile(3103, 3424, 0);

  static Filter<RSGroundItem> fisha = new Filter<RSGroundItem>() {

    @Override
    public boolean accept(RSGroundItem o) {
      RSItemDefinition g_item = o.getDefinition();
      if (g_item == null)
        return false;
      String g_name = g_item.getName();
      return (g_name.equals("Raw trout") || g_name.equals("Raw salmon"));
    }
  };

  public static void allFish() {

    while (Inventory.getAll().length <= 25) {
      RSGroundItem[] fish = GroundItems.findNearest(fisha);

      if (fish.length < 1) {
        General.sleep(300, 400);
        RSTile hover_tile = getPopulatedTile();
        Camera.turnToTile(hover_tile);

        if (!hover_tile.isOnScreen()) {
          debug.log("walking to fish tile");
          Walking.walkToFish();
          Camera.turnToTile(hover_tile);
        }

        Point human_hover_point = hover_tile.getHumanHoverPoint();
        if (human_hover_point == null)
          continue;

        if (Mouse.getPos().distance(human_hover_point) > 30) {
          debug.log("hovering over spot most likely for fish");
          Mouse.move(hover_tile.getHumanHoverPoint());
        }
        continue;
      }

      RSGroundItem nearest_fish = fish[0];

      if (!nearest_fish.isClickable()) {
        Camera.turnToTile(nearest_fish.getPosition());
        if (!nearest_fish.isClickable()) {
          debug.log("walking to nearest fish");
          Walking.walk(nearest_fish.getPosition());
        }
      }

      nearest_fish.click("Take" + " Raw");
      General.sleep(150, 350);

    }
  }

  private static RSTile getPopulatedTile() {

    int[] player_numbers = new int[5];

    player_numbers[0] = Players.find(Filters.Players.tileEquals(PTILE1)).length;
    player_numbers[1] = Players.find(Filters.Players.tileEquals(PTILE2)).length;
    player_numbers[2] = Players.find(Filters.Players.tileEquals(PTILE3)).length;
    player_numbers[3] = Players.find(Filters.Players.tileEquals(PTILE4)).length;
    player_numbers[4] = Players.find(Filters.Players.tileEquals(PTILE5)).length;

    int max = Arrays.stream(player_numbers).max().getAsInt();

    if (max == player_numbers[0]) {
      debug.log("Most Players on tile 1");
      return PTILE1;
    } else if (max == player_numbers[1]) {
      debug.log("Most Players on tile 2");
      return PTILE2;
    } else if (max == player_numbers[2]) {
      debug.log("Most Players on tile 3");
      return PTILE3;
    } else if (max == player_numbers[3]) {
      debug.log("Most Players on tile 4");
      return PTILE4;
    } else if (max == player_numbers[4]) {
      debug.log("Most Players on tile 5");
      return PTILE5;
    }

    return PTILE1;

  }

}
