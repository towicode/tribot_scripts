package scripts.thiever.nodes;

import org.tribot.api2007.Player;
import org.tribot.api2007.Skills;
import org.tribot.api2007.Skills.SKILLS;
import org.tribot.api2007.types.RSPlayer;

import scripts.thiever.LazyThieving;
import scripts.thiever.abstracts.Node;
import scripts.thiever.enums.Name;

public class LevelHandler extends Node {

  public Integer last_level;

  @Override
  public void execute() {

    Integer current_level = Skills.getCurrentLevel(SKILLS.THIEVING);

    if (current_level < 5) {
      LazyThieving.addNode(Name.PICK_POCKET);
    } else if (current_level < 25) {
      LazyThieving.removeNode(Name.PICK_POCKET);
      LazyThieving.addNode(Name.STEAL_TEA);
      LazyThieving.addNode(Name.DROPPER);
    }

    else if (current_level < 38) {
      LazyThieving.removeNode(Name.STEAL_TEA);
      LazyThieving.removeNode(Name.DROPPER);
      LazyThieving.addNode(Name.PICK_POCKET);
      LazyThieving.addNode(Name.EAT_FOOD);
    }

  }

  @Override
  public boolean validate() {

    RSPlayer player = Player.getRSPlayer();

    if (player == null)
      return false;

    Integer current_level = Skills.getCurrentLevel(SKILLS.THIEVING);

    if (last_level == null)
      last_level = current_level;

    return (current_level > last_level);

  }

}
