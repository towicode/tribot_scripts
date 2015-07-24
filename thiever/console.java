package scripts.thiever;

import org.tribot.api.General;

public class console {

  public static void log(Object... x) {

    // return;

    for (Object y : x)
      General.println(y);
  }
}
