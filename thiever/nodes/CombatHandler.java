package scripts.thiever.nodes;

import org.tribot.api2007.Combat;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSPlayer;

import scripts.thiever.abstracts.Node;

public class CombatHandler extends Node {

  @Override
  public void execute() {
   

  }

  @Override
  public boolean validate() {
    RSPlayer player = Player.getRSPlayer();

    if (player == null)
      return false;

    return player.isInCombat();
  }

}
