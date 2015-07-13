package scripts;

import java.awt.Point;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.input.Mouse;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Camera;
import org.tribot.api2007.ChooseOption;
import org.tribot.api2007.Game;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Objects;
import org.tribot.api2007.Player;
import org.tribot.api2007.ext.Filters;
import org.tribot.api2007.types.RSInterfaceChild;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSTile;

public class Cooking {

  private static final int COOKING_INTERFACE = 307;
  static Condition InterfaceIsUp = new Condition() {

    @Override
    public boolean active() {
      General.sleep(100, 300);

      return Interfaces.isInterfaceValid(COOKING_INTERFACE);
    }
  };
  static Condition Cooking = new Condition() {

    @Override
    public boolean active() {
      General.sleep(100, 300);

      return !Player.isMoving() && Player.getAnimation() > 0;
    }
  };

  public static void CookFish() {

    RSItem[] fish = Inventory.find(Filters.Items.nameContains("Raw "));

    if (fish.length < 1)
      return;

    RSItem closest_fish = fish[0];

    if (!Firemaking.isFireGoing()) {
      Firemaking.makeFire();
    }

    RSObject[] fire = Objects.getAll(7, Filters.Objects.nameContains("Fire"));
    if (fire.length < 1)
      return;

    RSObject closest_fire = fire[0];

    Camera.turnToTile(closest_fire.getAllTiles()[0]);

    if (!closest_fire.isOnScreen()) {
      Camera.turnToTile(closest_fire.getPosition());
      if (!closest_fire.isClickable()) {
        debug.log("walking to fire");
        Walking.walk(closest_fire.getPosition());
      }
    }

    closest_fish.click("Use");
    General.sleep(300, 400);

    if (!Game.isUptext("use"))
      return;

    if (debug.rightClick)
      clickFire(closest_fire);
    else
      closest_fire.click("Fire");

    if (!Timing.waitCondition(InterfaceIsUp, 4000)) {
      General.println("For some reason we never got to the cooking interface");
    }
    General.sleep(2000, 2200);

    RSInterfaceChild cooking = Interfaces.get(COOKING_INTERFACE, 3);
    if (cooking == null)
      return;

    if (!Firemaking.isFireGoing())
      return;

    cooking.click("Cook All");

    if (Timing.waitCondition(Cooking, 2000)) {
      while (Player.getAnimation() > 0 || secondCheck()) {
        General.sleep(300, 400);
      }
    }
  }

  private static boolean secondCheck() {
    General.sleep(400, 500);
    boolean check1 = Player.getAnimation() > 0;
    General.sleep(600, 700);
    boolean check2 = Player.getAnimation() > 0;

    return check1 || check2;
  }

  public static void clickFire(RSObject closest_fire) {

    RSTile fire = closest_fire.getPosition();

    if (fire == null)
      return;

    Point fire_hover_point = fire.getHumanHoverPoint();

    if (fire_hover_point == null)
      return;

    Mouse.move(fire_hover_point);
    General.sleep(100, 120);
    Mouse.click(3);
    General.sleep(200, 220);
    ChooseOption.select("Fire");

  }
}
