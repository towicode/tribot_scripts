package scripts;

import org.tribot.api2007.types.RSTile;

public class Walking {

  final static RSTile PTILE1 = new RSTile(3104, 3433, 0);
  final static RSTile PTILE2 = new RSTile(3109, 3433, 0);

  public static void walkToFish() {
    RSTile[] path = org.tribot.api2007.Walking
        .generateStraightScreenPath(PTILE2);
    if (path == null || path.length == 0)
      return;
    org.tribot.api2007.Walking.walkScreenPath(path);
  }

  public static void walk(RSTile position) {
    RSTile[] path = org.tribot.api2007.Walking
        .generateStraightScreenPath(position.getPosition());
    org.tribot.api2007.Walking.walkScreenPath(path);

  }

}
