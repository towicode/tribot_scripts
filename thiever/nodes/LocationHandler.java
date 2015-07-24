package scripts.thiever.nodes;

import scripts.thiever.abstracts.Node;

public class LocationHandler extends Node {

  private static Integer current_level;

  @Override
  public void execute() {
    // TODO Auto-generated method stub

  }

  @Override
  public boolean validate() {
    return false;
  }

  public static void setCurrentLevel(Integer x) {
    current_level = x;

  }

  private boolean inCorrectArea() {
    return true;
  }

}
