package scripts;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import org.tribot.api.General;
import org.tribot.api.input.Mouse;
import org.tribot.api2007.ChooseOption;
import org.tribot.api2007.Game;
import org.tribot.api2007.Inventory;
import org.tribot.script.EnumScript;
import org.tribot.script.interfaces.Painting;

public class LazyCooker extends EnumScript<States>implements Painting {

  String[] needed = { "Tinderbox", "Bronze axe" };

  private States getState() {
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
    
    final Rectangle PAINT_RECT = new Rectangle(7, 345, 510, 129);
    g.setColor(Color.BLACK);
    g.fillRect(PAINT_RECT.x, PAINT_RECT.y, PAINT_RECT.width, PAINT_RECT.height);
    g.setColor(Color.WHITE);
    g.drawString("Lazy Cooker v1.0", 17, 365);

  }
}
