package scripts;

import org.tribot.api.General;

public class debug {

  public static boolean rightClick = true;

  public static void log(Object x) {
    boolean debug = true;

    if (debug)
      General.println(x);
  }
}
