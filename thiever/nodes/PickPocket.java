package scripts.thiever.nodes;

import java.util.Arrays;

import org.tribot.api.General;
import org.tribot.api2007.NPCs;
import org.tribot.api2007.Player;
import org.tribot.api2007.ext.Filters;
import org.tribot.api2007.types.RSNPC;

import scripts.thiever.api;
import scripts.thiever.console;
import scripts.thiever.abstracts.Node;
import scripts.thiever.enums.Name;

public class PickPocket extends Node {

  public final Name name = Name.PICK_POCKET;
  private final String WARRIOR = "Warrior";

  @Override
  public void execute() {
    console.log("42");

    RSNPC[] warriors = NPCs.getAll(Filters.NPCs.nameContains(WARRIOR));

    if (warriors.length <= 0)
      return;
    

    RSNPC warrior = Arrays.stream(warriors).findFirst().get();

    api.interact(warrior, "Pickpocket");

    General.sleep(300, 400);

  }

  @Override
  public boolean validate() {
/*    console.log("here");
    console.log(Player.getAnimation() <= 0);
    console.log(!(Player.isMoving()));*/
    return (Player.getAnimation() <= 0) && !(Player.isMoving());
  }

}
