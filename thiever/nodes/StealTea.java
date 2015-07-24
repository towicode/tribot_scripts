package scripts.thiever.nodes;

import java.util.Arrays;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Objects;
import org.tribot.api2007.Player;
import org.tribot.api2007.ext.Filters;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSObject;

import scripts.thiever.LazyThieving;
import scripts.thiever.api;
import scripts.thiever.abstracts.Node;
import scripts.thiever.enums.Name;

public class StealTea extends Node {

  public final Name name = Name.STEAL_TEA;

  private final String TEA_STALL = "Tea stall";

  @Override
  public void execute() {

    RSItem[] inventory = Inventory.getAll();

    if (inventory == null)
      return;

    int number_of_items = inventory.length;

    RSObject[] tea_stalls = Objects.find(15,
        Filters.Objects.nameEquals(TEA_STALL));
    if (tea_stalls.length <= 0)
      return;

    // TODO human reaction time.
    General.sleep(220, 300);

    RSObject tea_stall = Arrays.stream(tea_stalls).findFirst().get();

    api.interact(tea_stall, "Steal");

    if (Timing.waitCondition(new Condition() {

      @Override
      public boolean active() {
        General.sleep(200, 400);

        RSItem[] inventory = Inventory.getAll();

        if (inventory == null)
          return false;

        int items_post = inventory.length;

        return items_post > number_of_items;

      }
    }, 2000)) {
      // we failed to get a tea, so let's make sure we're in the right place.
      LazyThieving.addNode(name.LOCATION_HANDLER);
    }

  }

  @Override
  public boolean validate() {
    return !Inventory.isFull();
  }
}
