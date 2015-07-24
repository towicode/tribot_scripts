package scripts.thiever;

import org.tribot.api2007.WebWalking;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSTile;

public class Walker {

  private static boolean female = Seed.isFemale();

  static RSTile tea_top_left = new RSTile(3269, 3413);
  static RSTile tea_bot_right = new RSTile(3267, 3409);
  static RSArea tea_area = new RSArea(tea_top_left, tea_bot_right);

  public static void walkToTea() {

    WebWalking.walkTo(tea_area.getRandomTile());

  }

  public static void walkToBank() {
    WebWalking.walkToBank();
  }

  public static void walkToTile(RSTile position) {
    // TODO Auto-generated method stub

  }


}
