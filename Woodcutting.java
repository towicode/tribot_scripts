package scripts;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Camera;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Objects;
import org.tribot.api2007.Player;
import org.tribot.api2007.ext.Filters;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSObject;

public class Woodcutting {

  public static boolean cutWood() {
    
    if (Firemaking.checkFire())
      return true;

    RSItem[] raw_inv = Inventory.find(Filters.Items.nameContains("Raw"));
    RSItem[] logs = Inventory.find(Filters.Items.nameEquals("Logs"));
    RSObject[] trees = Objects.findNearest(7,
        Filters.Objects.nameEquals("Tree"));

    if (Inventory.isFull()) {
      
      if (Firemaking.checkFire())
        return true;

      if (raw_inv.length > 0) {
        RSItem fish_to_drop = raw_inv[0];
        fish_to_drop.click("Drop");
      }
    }

    if (raw_inv.length <= 0)
      return true;

    if (logs.length > 0)
      return true;

    if (Firemaking.checkFire())
      return true;

    if (trees == null || trees.length < 1) {
      General.println(
          "We didn't find the tree, so we're gonna try to walk towards one");
      Walking.walkToTree();
      General.sleep(600, 700);
      return cutWood();

    }

    RSObject tree = trees[0];

    if (!tree.isOnScreen()) {
      Camera.turnToTile(tree.getPosition());
      if (!tree.isClickable()) {
        Walking.walkToTree();
      }
    }
    
    if (Firemaking.checkFire())
      return true;


    tree.click("Chop");

    Timing.waitCondition(new Condition() {

      @Override
      public boolean active() {
        General.sleep(200, 300);
        return Player.getAnimation() > 0
            || Inventory.find(Filters.Items.nameEquals("Logs")).length > 0;
      }

    }, 3000);

    while (Player.getAnimation() > 0)
      General.sleep(300);

    return (Inventory.find(Filters.Items.nameEquals("Logs")).length > 0);

  }

}
