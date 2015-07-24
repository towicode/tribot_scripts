package scripts.thiever;

import org.tribot.api.General;

public class Seed {

  private static final int A = 1812433253;
  private static int x;
  private static int y;
  private static int z;
  private static int w;

  private static Integer react_time;

  enum Gender {
    MALE, FEMALE, OTHER
  }

  enum Race {
    BLACK, WHITE, HISPANIC, ASIAN, OTHER
  }

  enum Country {
    USA, UK, CANADA, NETHERLANDS, SWEDEN, OTHER
  }

  enum Email {
    GMAIL, YAHOO, HOTMAIL, OTHER
  }

  public static void setSeed(String seed) {

    Integer seed_int = seed.hashCode();

    x = seed_int;
    y = A * (x ^ (x >>> 30)) + 1;
    z = A * (y ^ (y >>> 30)) + 2;
    w = A * (z ^ (z >>> 30)) + 3;

    boolean y_negative = y < 0;
    boolean z_negative = z < 0;
    boolean w_negative = w < 0;

    // need to degrade from non-negative
    if (y_negative)
      y = y * -1;
    if (z_negative)
      z = z * -1;
    if (w_negative)
      w = w * -1;

    // degrade
    while (y > 1000)
      y = y / 10;

    while (z > 1000)
      z = z / 10;

    while (w > 1000)
      w = w / 10;

    // turn back to negative.
    if (y_negative)
      y = y * -1;
    if (z_negative)
      z = z * -1;
    if (w_negative)
      w = w * -1;

  }

  public static boolean isFemale() {
    return y >= 0;
  }

  public static boolean isGmailUser() {
    return Math.abs(z) > 500;
  }

  public static boolean isYahooUser() {
    return Math.abs(z) < 500;
  }

  public static boolean isAmerican() {
    return Math.abs(w) < 539;
  }

  public static Country getCountry() {
    int num = Math.abs(w);

    if (num > 461)
      return Country.USA;
    else if (num > 345)
      return Country.UK;
    else if (num > 302)
      return Country.CANADA;
    else if (num > 263)
      return Country.NETHERLANDS;
    else if (num > 241)
      return Country.SWEDEN;
    else
      return Country.OTHER;
  }

  public static Integer getStartingReactionTime() {

    if (react_time != null)
      return react_time;
    int num = Math.abs(y);
    float divisor_fract = Math.abs(w);
    float divisor = 1 + (divisor_fract / 1000);

    while (num > 420)
      num = (int) (num / divisor);

    while (num < 221)
      num = (int) (num * divisor);

    react_time = num;
    return num;
  }
}
