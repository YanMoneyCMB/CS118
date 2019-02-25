import uk.ac.warwick.dcs.maze.logic.IRobot;
import java.util.Random;
import java.util.ArrayList;

/* Spec : 
 1. Reverse only at dead ends
 2. At corners turn left or right so as to avoid collisions
 3. At junctions it should turn, if possible, to move into a square which hasn't been explored, choose randomly if more than one are unexplored. 
 If not possible, random directions w/out collisions.
 4. Same as 3 on crossroads
*/
public class Ex2 {
  private int pollRun = 0;    
  private RobotData robotData;
  private int explorerMode; //1 = explore, 0 = backtrack
  public void reset() { //resets data when reset button is pressed
      robotData.resetJunctionCounter();
      pollRun = 0;
      explorerMode = 1; //so the robot starts in the explorer mode when reset button is                     pressed
  }
    
public void controlRobot(IRobot robot) {    //main method
    // on the first move of the first run of a new maze
    int exits = nonWallExits(robot);
    int direction = IRobot.AHEAD;
    if ((robot.getRuns() == 0) && (pollRun == 0)) {
        robotData = new RobotData();        //reset the data store
    }
    explorerMode = 1;                       //robot starts in the explorer mode
     if ((exits == 3) && (passageExits(robot) == 2)) {
        robotData.recordJunction(robot);
        robotData.printJunction(robot);
    } 
    if ((exits == 4) && (passageExits(robot) == 3)) {
        robotData.recordJunction(robot);
        robotData.printJunction(robot);
    } 
    if (passageExits(robot) != 0) {
       direction = exploreControl(robot);
    }
    if (passageExits(robot) == 0){
        direction = backtrackControl(robot);
    }
    robot.face(direction);
      pollRun++;
}
    
  private int exploreControl(IRobot robot) {    //explore
    int exits = nonWallExits(robot);
    int edirection = IRobot.AHEAD;
    if (exits < 2) { //deadend
      edirection = deadEnd(robot);
        explorerMode = 0;
    }
    else if (exits == 2) { //corridor/corners
        edirection = CorridororCorner(robot);
    }
    else if (exits == 3) { //junctions
      edirection = Junction(robot);
	}
    else if (exits == 4) { //crossroads
      edirection = Crossroads(robot);
	}
     return edirection; 
  }
 private int backtrackControl (IRobot robot) {
     int bdirection = IRobot.AHEAD;     //initialise backtrack direction
     int initialHeading = robotData.searchJunction(robot); //get the initial Heading
     int nonwall = nonWallExits (robot);     //determine junction/deadend/corridor
     int reversedHeading = IRobot.NORTH; //will be used to store reversed initialHeading
     if(nonwall > 2) {
         if(passageExits(robot) == 0) { //explored junction/crossroad
             //reverse initial heading and set it
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
             int i = robotData.JuncList.size() - 1;
             robotData.JuncList.remove(i);  //removing the last junction used
             robot.setHeading(reversedHeading);
             //initialised backtrack direction is still AHEAD & the robot is already set to the right direction
         }
         else { //not fully explored junction
             explorerMode = 1;
                 bdirection = exploreControl(robot);
         }
     }
     else if (nonwall == 1) {
        bdirection = deadEnd(robot); //normal stuff
     }
     else if (nonwall == 2){
        bdirection = CorridororCorner(robot); //normal stuff
     }
         
     return bdirection;
 }
    
  private int nonWallExits (IRobot robot) {    //non wall exits
    int nonwalls=0;
    if(robot.look(IRobot.AHEAD) != IRobot.WALL){
      nonwalls++;
      }
    if(robot.look(IRobot.RIGHT) != IRobot.WALL) {
      nonwalls++;
      }
    if(robot.look(IRobot.LEFT) != IRobot.WALL){
      nonwalls++;
      }
    if(robot.look(IRobot.BEHIND)!= IRobot.WALL){
      nonwalls++;
      }
      return nonwalls;
  }
    private int passageExits (IRobot robot) {   //passage exit method
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
    private int beenbeforeExits (IRobot robot) {   //passage exit method
        int been = 0;
        if(robot.look(IRobot.AHEAD) == IRobot.BEENBEFORE){
            been++;
        }
        if(robot.look(IRobot.RIGHT) == IRobot.BEENBEFORE) {
            been++;
        }
        if(robot.look(IRobot.LEFT) == IRobot.BEENBEFORE){
            been++;
        }
        if(robot.look(IRobot.BEHIND) == IRobot.BEENBEFORE){
            been++;
        }
        return been;
    }
    
     private int randInt(int min, int max) {    //efficient random method
            Random rand = new Random();
            int randomNum = rand.nextInt((max-min) + 1) + min;
            return randomNum;
        }

    private int deadEnd (IRobot robot) {    //deadend method
       int deadEnd = IRobot.BEHIND;
        if (robot.look(IRobot.BEHIND) == IRobot.WALL) {
            if (robot.look(IRobot.LEFT) != IRobot.WALL) {
                deadEnd = IRobot.LEFT;
            }
            if (robot.look(IRobot.RIGHT) != IRobot.WALL) {
                deadEnd = IRobot.RIGHT;
            }
            if (robot.look(IRobot.AHEAD) != IRobot.WALL) {
                deadEnd = IRobot.AHEAD;
            }
        }
        return deadEnd;
    }
    private int CorridororCorner (IRobot robot) {   //Corridor/corner method
        int CorridororCorner = IRobot.AHEAD;
        if (robot.look(IRobot.AHEAD) != IRobot.WALL) {
             CorridororCorner = IRobot.AHEAD;
        }
        if (robot.look(IRobot.RIGHT) != IRobot.WALL) {
             CorridororCorner = IRobot.RIGHT;
        }
        if (robot.look(IRobot.LEFT) != IRobot.WALL) {
             CorridororCorner = IRobot.LEFT;
        }
        return CorridororCorner;
    }
    private int Junction (IRobot robot) {       //junction method
        
        int randirection = IRobot.AHEAD;
        if (robot.look(IRobot.AHEAD) == IRobot.PASSAGE) {
            randirection = IRobot.AHEAD;
        }
        else if (robot.look(IRobot.RIGHT) == IRobot.PASSAGE) {
            randirection = IRobot.RIGHT;
        }
        else if (robot.look(IRobot.LEFT) == IRobot.PASSAGE) {
            randirection = IRobot.LEFT;
        }
        //cases where robot chooses between passages.
        else if (robot.look(IRobot.AHEAD) == IRobot.PASSAGE && robot.look(IRobot.LEFT) == IRobot.PASSAGE){
            int n = randInt(0,1);
            switch (n) {
                case 0: randirection = IRobot.AHEAD;
                    break;
                case 1: randirection = IRobot.LEFT; 
                    break;
            }
        }
     else if (robot.look(IRobot.AHEAD) == IRobot.PASSAGE && robot.look(IRobot.RIGHT) == IRobot.PASSAGE){
            int n = randInt(0,1);
            switch (n) {
                case 0: randirection = IRobot.AHEAD;
                    break;
                case 1: randirection = IRobot.RIGHT; 
                    break;
            }
        }
     else if (robot.look(IRobot.RIGHT) == IRobot.PASSAGE && robot.look(IRobot.LEFT) ==IRobot.PASSAGE){
            int n = randInt(0,1);
            switch (n) {
                case 0: randirection = IRobot.RIGHT;
                    break;
                case 1: randirection = IRobot.LEFT; 
                    break;
            }
        }    
     else if (passageExits(robot) == 0) {              //all explored.
            do {
                int  n = randInt(1,3);
                switch (n) {
                    case 1: randirection = IRobot.AHEAD;
                        break;
                    case 2: randirection = IRobot.LEFT;
                        break;
                    case 3: randirection = IRobot.RIGHT;
                        break;
                }
            } while (robot.look(randirection) == IRobot.WALL) ;
        }
        return randirection;
        
    }
    private int Crossroads (IRobot robot) {     //crossroad method
        
        int randirection = IRobot.AHEAD; //used randirection as a direction variable here
        if (robot.look(IRobot.AHEAD) == IRobot.PASSAGE) {
            randirection = IRobot.AHEAD;
        }
        else if (robot.look(IRobot.RIGHT) == IRobot.PASSAGE) {
            randirection = IRobot.RIGHT;
        }
        else if (robot.look(IRobot.LEFT) == IRobot.PASSAGE) {
            randirection = IRobot.LEFT;
        }
        //choose randomly between passages
        else if (robot.look(IRobot.AHEAD) == IRobot.PASSAGE && robot.look(IRobot.LEFT) == IRobot.PASSAGE){
            int n = randInt(0,1);
            switch (n) {
                case 0: randirection = IRobot.AHEAD;
                    break;
                case 1: randirection = IRobot.LEFT; 
                    break;
            }
        }
     else if (robot.look(IRobot.AHEAD) == IRobot.PASSAGE && robot.look(IRobot.RIGHT) == IRobot.PASSAGE){
            int n = randInt(0,1);
            switch (n) {
                case 0: randirection = IRobot.AHEAD;
                    break;
                case 1: randirection = IRobot.RIGHT; 
                    break;
            }
        }
     else if (robot.look(IRobot.RIGHT) == IRobot.PASSAGE && robot.look(IRobot.LEFT) ==IRobot.PASSAGE){
            int n = randInt(0,1);
            switch (n) {
                case 0: randirection = IRobot.RIGHT;
                    break;
                case 1: randirection = IRobot.LEFT; 
                    break;
            }
        } 
        else if (passageExits(robot) == 0) {              //all explored.
            do {
                int n = randInt(1,3);
                switch (n) {
                    case 1: randirection = IRobot.AHEAD;
                        break;
                    case 2: randirection = IRobot.LEFT;
                        break;
                    case 3: randirection = IRobot.RIGHT;
                        break;
                }
            } while (robot.look(randirection) == IRobot.WALL) ;
        }    
        return randirection;
    }
    
    
}

class Junc {
    private int arrived;
    Junc (int arrived) { //Constructor Method
        this.arrived = arrived;
    }
    public int getArrived() {
        return arrived;
    }
}

class RobotData {
    private Junc Junc;
    private static int maxJunctions = 10000; //Max number likely to occur
    private static int junctionCounter = 0;
    public ArrayList<Junc> JuncList;    //declare the array
    RobotData() {
       JuncList = new ArrayList<Junc>();  
    }
    public void resetJunctionCounter() {    //reset
        junctionCounter = 0;
    }
    public void recordJunction(IRobot robot) {  //put info about junction in array;
         Junc newJunc = new Junc(robot.getHeading());
        JuncList.add(newJunc);
        junctionCounter++;
    }
    public void printJunction(IRobot robot) {
        int juncNum = JuncList.size();      //kind of like counting junctions
        int arrived = robot.getHeading();
        String heading = "North";

        switch(arrived) {
            
            case IRobot.NORTH: heading = "NORTH";
                break;
            case IRobot.SOUTH: heading = "SOUTH";
                break;
            case IRobot.WEST: heading = "WEST";
                break;
            case IRobot.EAST: heading = "EAST";
                break;
        }

        System.out.println("Junction " + juncNum + " Heading: " + heading);
    }


     public int searchJunction(IRobot robot) {
        int initHeading = IRobot.NORTH;   // initialising initial Heading
        int i;
        if (JuncList.size() == 0){
            
        }
        else if (JuncList.size() != 0) {
            i = JuncList.size() - 1;            //i is a position variable
            Junc newJunc = JuncList.get(i);     //get last junction recorded
            initHeading = newJunc.getArrived();    //get the initial heading
            
        }
        return initHeading;     //returns the initial heading, which will be reversed in the Backtrack method.
     }
}
    
    
    
