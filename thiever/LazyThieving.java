package scripts.thiever;

import java.util.LinkedList;

import org.tribot.api.General;
import org.tribot.api2007.types.RSArea;
import org.tribot.script.Script;

import scripts.thiever.abstracts.Node;
import scripts.thiever.enums.Name;
import scripts.thiever.nodes.Dropper;
import scripts.thiever.nodes.EatFood;
import scripts.thiever.nodes.PickPocket;
import scripts.thiever.nodes.StealTea;

public class LazyThieving extends Script {

  static LinkedList<Node> nodeList;

  @Override
  public void run() {
    nodeList = new LinkedList<>();
/*    addNode(Name.STEAL_TEA);
    addNode(Name.DROPPER);*/
    
    addNode(Name.PICK_POCKET);
    
    while (true){
      General.sleep(200,300);
      
      for (Node node : nodeList){
        if (node.validate())
          node.execute();
      }
    }
    
    

  }

  public static void removeNode(Name name) {
    nodeList.parallelStream().filter(s -> s.name.equals(name))
        .forEach(s -> nodeList.remove(s));
  }

  public static void addNode(Name name) {

    switch (name) {
    case EAT_FOOD:
      nodeList.add(new EatFood());
      break;
    case STEAL_TEA:
      nodeList.add(new StealTea());
      break;
    case PICK_POCKET:
      nodeList.add(new PickPocket());
      break;
    case DROPPER:
      nodeList.add(new Dropper());
      break;
    default:
      break;
    }
  }

  public static Integer nodeLength() {
    return nodeList.size();
  }
}
