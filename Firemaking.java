package scripts;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api.types.generic.Filter;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Objects;
import org.tribot.api2007.Player;
import org.tribot.api2007.Players;
import org.tribot.api2007.ext.Filters;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSPlayer;

public class Firemaking {

  private static Filter<RSPlayer> makingFire = new Filter<RSPlayer>() {

    @Override
    public boolean accept(RSPlayer o) {
      return o.getAnimation() == 730;
    }

  };

  private static Condition lighting_fire = new Condition() {

    @Override
    public boolean active() {
      General.sleep(200, 300);
      return Player.getAnimation() > 0 || checkFire();
    }

  };

  public static boolean makeFire() {

    while (!isFireGoing()) {

      if (checkFire())
        return true;

      Woodcutting.cutWood();

      if (checkFire())
        return true;

      RSItem wood[] = Inventory.find(Filters.Items.nameEquals("Logs"));

      if (wood == null || wood.length < 1) {
        General.println("Wood Chopping failed");
        return false;
      }

      RSItem tinderboxes[] = Inventory
          .find(Filters.Items.nameEquals("TinderBox"));

      if (tinderboxes == null || tinderboxes.length < 1) {
        General.println("This script requires a tinderbox dumbass");
        return false;
      }

      RSItem log = wood[0];
      RSItem tinderbox = tinderboxes[0];
      log.click("Use");
      General.sleep(100, 200);
      tinderbox.click("Tinderbox");

      if (!Timing.waitCondition(lighting_fire, 4000)) {

        return false;

      }

      while (Player.getAnimation() > 0) {
        General.sleep(300, 500);
        if (isFireGoing())
          break;
      }

      if (Timing.waitCondition(new Condition() {

        @Override
        public boolean active() {
          General.sleep(200, 400);
          return isFireGoing();
        }

      }, 2400))

        if (checkFire())
          return true;

    }
    return false;

  }

  public static boolean checkFire() {
    if (isFireGoing())
      return true;
    while (lightingFire()) {
      General.sleep(300, 400);
      if (isFireGoing())
        return true;
    }
    return false;
  }

  private static boolean lightingFire() {

    RSPlayer[] fireMakers = Players.getAll(makingFire);
    return (fireMakers != null && fireMakers.length > 0);
  }

  public static boolean isFireGoing() {

    RSObject[] fire = Objects.getAll(7, Filters.Objects.nameContains("Fire"));

    return (fire != null && fire.length > 0);

  }


}
