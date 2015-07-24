package scripts.thiever;

import org.tribot.api2007.Camera;
import org.tribot.api2007.types.RSNPC;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSTile;

public class api {

  public static boolean interact(Object obj, String action) {

    if (obj instanceof RSObject) {
      RSObject object = (RSObject) obj;

      RSTile position = object.getPosition();

      if (object == null || position == null)
        return false;

      if (!object.isOnScreen())
        Camera.turnToTile(position);

      if (!object.isOnScreen())
        Walker.walkToTile(position);

      return object.click(action);

    } else if (obj instanceof RSNPC) {

      RSNPC npc = (RSNPC) obj;
      RSTile position = npc.getPosition();

      if (npc == null || position == null)
        return false;

      if (!npc.isOnScreen())
        Camera.turnToTile(position);

      if (!npc.isOnScreen())
        Walker.walkToTile(position);

      return npc.click(action);

    }
    return false;
  }
}
