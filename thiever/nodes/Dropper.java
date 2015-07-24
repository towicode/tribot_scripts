package scripts.thiever.nodes;

import org.tribot.api2007.Inventory;

import scripts.thiever.abstracts.Node;

public class Dropper extends Node {

  @Override
  public void execute() {
    Inventory.dropAllExcept(0);
  }

  @Override
  public boolean validate() {
    return Inventory.isFull();
  }

}
