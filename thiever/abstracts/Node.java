package scripts.thiever.abstracts;

import scripts.thiever.enums.Name;

public abstract class Node {
  
  public Name name;
  
  public abstract void execute();

  public abstract boolean validate();

}