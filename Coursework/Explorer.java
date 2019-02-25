import uk.ac.warwick.dcs.maze.logic.IRobot;
import java.util.Stack;
import java.util.ArrayList;
import java.util.Random;

public class Explorer {
  public static int pollRun = 0;
  private RobotData robotData;


  public void controlRobot(IRobot robot) {

    if ((robot.getRuns() == 0) && (pollRun == 0)) {
  	    robotData = new RobotData(); //reset the data store
  	}

    if (robot.getRuns() == 0) {
      if (passagesExits(robot) > 0) {
          int rand = randInt(0,3);
        do {
          int randomHeading = IRobot.NORTH + rand;
          robot.setHeading(randomHeading);
        } while (robot.look(IRobot.AHEAD) != IRobot.PASSAGE);
        robotData.saveJunc(robot);
      } 
        else if (passagesExits(robot) == 0 ) {
          int initialHeading = robotData.findJunc();
          int reversedHeading = 0;
          switch (initialHeading){
                 case IRobot.NORTH:     reversedHeading = IRobot.SOUTH;
                     break;
                 case IRobot.SOUTH:     reversedHeading = IRobot.NORTH;
                     break; 
                 case IRobot.EAST:      reversedHeading = IRobot.WEST;
                     break;
                 case IRobot.WEST:      reversedHeading = IRobot.EAST;
                     break;
          }
        robot.setHeading(reversedHeading);
        //print some shit idk
      
        }
    } 
    if (robot.getRuns() != 0) {
      //robotData.reverseJunc();
        if (pollRun == 0) {
        java.util.Collections.reverse(robotData.Junc);
        }

        robot.setHeading(robotData.Junc.pop());
    }
        pollRun++;
  }

  public int passagesExits (IRobot robot) {   //passage exit method
        int passage = 0;
        if(robot.look(IRobot.AHEAD) == IRobot.PASSAGE){
            passage++;
        }
        if(robot.look(IRobot.RIGHT) == IRobot.PASSAGE) {
            passage++;
        }
        if(robot.look(IRobot.LEFT) == IRobot.PASSAGE){
            passage++;
        }
        if(robot.look(IRobot.BEHIND) == IRobot.PASSAGE){
            passage++;
        }
        return passage;
    }

  public void reset() { //reset JuncCounter
      pollRun = 0;
  }
  private int randInt(int min, int max) {    //efficient random method
      Random rand = new Random();
      int randomNum = rand.nextInt((max-min) + 1) + min;
      return randomNum;
        }


class RobotData {

    public Stack<Integer> Junc; // Stack of Junctions

    public RobotData() {
    Junc = new Stack<>();
    }

    public void saveJunc(IRobot robot) { // Store junctions in the data
  Junc.push(robot.getHeading());
  System.out.println("Junc : " + Junc.size());
    }

    public int findJunc() {
      System.out.println("Junc " + Junc.size() + " removed ");
  return Junc.pop();
    }

    public void clearJunc() { //reset junc stack
        Junc = new Stack<>();
    }
}
}