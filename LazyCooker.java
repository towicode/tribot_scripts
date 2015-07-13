package scripts;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.input.Mouse;
import org.tribot.api2007.ChooseOption;
import org.tribot.api2007.Game;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Skills;
import org.tribot.api2007.Skills.SKILLS;
import org.tribot.script.EnumScript;
import org.tribot.script.interfaces.Painting;

public class LazyCooker extends EnumScript<States>implements Painting {

  String[] needed = { "Tinderbox", "Bronze axe" };
  long start_time;
  final Rectangle PAINT_RECT = new Rectangle(7, 345, 510, 129);
  final Rectangle SKILL_RECT = new Rectangle(17, 395, 440, 25);

  private States getState() {
    General.sleep(60, 70);
    if (Inventory.getAll().length >= 25) {
      if (Inventory.getCount("Raw trout")
          + (Inventory.getCount("Raw salmon")) > 0)
        return States.COOKING_FISH;
      else {
        Inventory.dropAllExcept(needed);
        return States.PICKING_FISH;
      }
    }
    return States.PICKING_FISH;

  }

  @Override
  public States getInitialState() {

    println("hi");
    start_time = Timing.currentTimeMillis();
    return getState();
  }

  @Override
  public States handleState(States state) {
    println(state);

    if (Game.isUptext("->")) {
      Mouse.moveBox(267, 143, 297, 180);
      Mouse.click(3);
      General.sleep(1000, 1200);
      ChooseOption.select("Cancel");
    }
    switch (state) {
    case COOKING_FISH:
      Cooking.CookFish();
      break;
    case PICKING_FISH:
      Pickup.allFish();
    }
    return getState();
  }

  @Override
  public void onPaint(Graphics g) {

    long passed_time = Timing.currentTimeMillis() - start_time;
    String time = Timing.msToString(passed_time);
    double percent = Skills.getPercentToNextLevel(SKILLS.COOKING);
    percent = percent / 100;
    double width = SKILL_RECT.width * percent;
    int int_width = (int) width;

    g.setColor(Color.BLACK);
    g.fillRect(PAINT_RECT.x, PAINT_RECT.y, PAINT_RECT.width, PAINT_RECT.height);
    g.setColor(Color.WHITE);
    g.drawString("Lazy Cooker v1.01", 17, 365);
    g.drawString("Time Ran: " + time, 17, 380);
    g.fillRect(SKILL_RECT.x, SKILL_RECT.y, SKILL_RECT.width, SKILL_RECT.height);
    g.setColor(Color.green);
    g.fillRect(SKILL_RECT.x, SKILL_RECT.y, int_width, SKILL_RECT.height);

  }
}
