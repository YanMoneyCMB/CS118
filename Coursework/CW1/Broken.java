/*
 * File:    Broken	.java
 * Created: 7 September 2001
 * Author:  Stephen Jarvis
 */

import uk.ac.warwick.dcs.maze.logic.IRobot;

public class Broken {
  public void controlRobot(IRobot robot) {
    int heading = headingController(robot);
    ControlTest.test(heading, robot);
    robot.setHeading(heading);

  }
    public void reset() {
        ControlTest.printResults();
    }
          //is Target North?  
    private byte isTargetNorth(IRobot robot) {
            byte result_y = 0;
            if (robot.getLocation().y < robot.getTargetLocation().y) {
                result_y = 1;
                //System.out.println("Target is North");
            } 
            else if (robot.getLocation().y > robot.getTargetLocation().y) {
                result_y = -1;
                //System.out.println("Target is South");
            } 
            else if (robot.getLocation().y == robot.getTargetLocation().y) {
                result_y = 0;
               // System.out.println("Target is same latitude y");
            } 
                    //returning 1 for "yes", -1 for "no", and 0 for "same latitude".
            return result_y;  
    }
    
   //is Target East?
    private byte isTargetEast(IRobot robot) {
            byte result_x = 0;
            if (robot.getLocation().x < robot.getTargetLocation().x) {
                result_x = 1;
                //System.out.println("Target is East");
            } 
            else if (robot.getLocation().x > robot.getTargetLocation().x) {
                result_x = -1;
                //System.out.println("Target is West");
            } 
            else if (robot.getLocation().x == robot.getTargetLocation().x) {
                result_x = 0;
                //System.out.println("Target is on the same latitude x");
            } 
            //returning 1 for "yes", -1 for "no", and 0 for "same latitude".
          return result_x;
        }
    //What's around
       public int lookHeading(IRobot robot, int a) {
 int initial = robot.getHeading();
 int x;
           int randno;
        robot.setHeading(a);
       x = robot.look(IRobot.AHEAD);
    robot.setHeading(initial);
    return x;
        }
    //headingController
    public int headingController(IRobot robot) {
      
     int heading = IRobot.NORTH;
     int n = lookHeading(robot, IRobot.NORTH);
     int e = lookHeading(robot, IRobot.EAST);
     int s = lookHeading(robot, IRobot.SOUTH);
     int w = lookHeading(robot, IRobot.WEST);
     int x = isTargetEast(robot);
     int y = isTargetNorth(robot);
     int random_direction;
     int randno;
        do {
            
        random_direction = (int) Math.round(Math.random()*1);
            
            if (x == 0 && y == 1)   //Target is north
            { if (n != IRobot.WALL) {
             heading = IRobot.NORTH;   
            }
             else if (n == IRobot.WALL){
                 do {         
       randno = (int) Math.round(Math.random()*3);

       if ( randno == 0)
            heading = IRobot.EAST;
       else if (randno == 1)
            heading = IRobot.WEST;
       else 
            heading = IRobot.SOUTH;
    } while (lookHeading(robot, heading) == IRobot.WALL);
             }
            }
                

                
            else if (x == 0 && y == -1 && s != IRobot.WALL) //Target is south
            {
                heading = IRobot.SOUTH;
            }
                else if (x == 0 && y == -1 && s == IRobot.WALL) {
                     do {         
       randno = (int) Math.round(Math.random()*3);

       if ( randno == 0)
            heading = IRobot.EAST;
       else if (randno == 1)
            heading = IRobot.WEST;
       else if (randno == 2)
            heading = IRobot.NORTH;
       else 
            heading = IRobot.SOUTH;
    } while (lookHeading(robot, heading) == IRobot.WALL);
                }
            else if (x == 1 && y == 0 && e != IRobot.WALL) //Target is EAST
            {
                heading = IRobot.EAST;
            }
                else if (x == 1 && y == 0 && e == IRobot.WALL) {
                     do {         
       randno = (int) Math.round(Math.random()*3);

       if ( randno == 0)
            heading = IRobot.EAST;
       else if (randno == 1)
            heading = IRobot.WEST;
       else if (randno == 2)
            heading = IRobot.NORTH;
       else 
            heading = IRobot.SOUTH;
    } while (lookHeading(robot, heading) == IRobot.WALL);
                }
            else if (x == -1 && y == 0 && w != IRobot.WALL) //Target is west
            {
                heading = IRobot.WEST;
            }
                else if (x == -1 && y == 0 && w == IRobot.WALL) {
                     do {         
       randno = (int) Math.round(Math.random()*3);

       if ( randno == 0)
            heading = IRobot.EAST;
       else if (randno == 1)
            heading = IRobot.WEST;
       else if (randno == 2)
            heading = IRobot.NORTH;
       else 
            heading = IRobot.SOUTH;
    } while (lookHeading(robot, heading) == IRobot.WALL);
                }
            if (x == -1 && y == 1) //Target is north-west
            {  
                if (n != IRobot.WALL) {
                heading = IRobot.NORTH;
            }
              else  if ( w != IRobot.WALL) {
                    heading = IRobot.WEST;
                }
            else if (n != IRobot.WALL && w != IRobot.WALL) {
                 switch (random_direction) {
                     case 0:    heading = IRobot.NORTH;
                     case 1:    heading = IRobot.WEST;
                         break;
                 }
             }
                if (w == IRobot.WALL && n == IRobot.WALL) {
                     do {
       randno = (int) Math.round(Math.random()*3);

       if ( randno == 0)
            heading = IRobot.EAST;
       else if (randno == 1)
            heading = IRobot.WEST;
       else if (randno == 2)
            heading = IRobot.NORTH;
       else 
            heading = IRobot.SOUTH;
    } while (lookHeading(robot, heading) == IRobot.WALL);
                    
                }
                
            }
            if (x == 1 && y == 1) //Target is north-east
            {   if (n != IRobot.WALL) {
                heading = IRobot.NORTH;
            }
               else if (e != IRobot.WALL) {
                    heading = IRobot.EAST;
                }
            else if (n != IRobot.WALL && e != IRobot.WALL) {
                    switch (random_direction) {
                        case 1:     heading = IRobot.NORTH;
                        case 0:     heading = IRobot.EAST;
                            break;
                    }
                }
                if (n == IRobot.WALL && e == IRobot.WALL) {
                     do {
       randno = (int) Math.round(Math.random()*3);

       if ( randno == 0)
            heading = IRobot.EAST;
       else if (randno == 1)
            heading = IRobot.WEST;
       else if (randno == 2)
            heading = IRobot.NORTH;
       else 
            heading = IRobot.SOUTH;
    } while (lookHeading(robot, heading) == IRobot.WALL);
                    
                }
                
            }
            if (x == 1 && y == -1) //Target is south-east
            {   if (s != IRobot.WALL) {
                heading = IRobot.SOUTH;
            }
               else if (e != IRobot.WALL) {
                    heading = IRobot.EAST;
                }
                else if (s != IRobot.WALL && e != IRobot.WALL) {
                    switch (random_direction) {
                        case 1:     heading = IRobot.SOUTH;
                        case 0:     heading = IRobot.EAST;
                            break;
                    }
                }
            else if (s == IRobot.WALL && e == IRobot.WALL) {
               
                     do {
       randno = (int) Math.round(Math.random()*3);

       if ( randno == 0)
            heading = IRobot.EAST;
       else if (randno == 1)
            heading = IRobot.WEST;
       else if (randno == 2)
            heading = IRobot.NORTH;
       else 
            heading = IRobot.SOUTH;
    } while (lookHeading(robot, heading) == IRobot.WALL);
                    
                }
                
            }
            if (x == -1 && y == -1) //Target is south-west
            {   if (s != IRobot.WALL) {
                heading = IRobot.SOUTH;
            }
              else if (w != IRobot.WALL) {
                    heading = IRobot.WEST;
                }
            else if (s != IRobot.WALL && w != IRobot.WALL) {
                switch(random_direction) {
                    case 1:     heading = IRobot.SOUTH;
                    case 0:     heading = IRobot.WEST;
                }
            }
             else if (w == IRobot.WALL && s == IRobot.WALL) {
                     do {
       randno = (int) Math.round(Math.random()*3);

       if ( randno == 0)
            heading = IRobot.EAST;
       else if (randno == 1)
            heading = IRobot.WEST;
       else if (randno == 2)
            heading = IRobot.NORTH;
       else 
            heading = IRobot.SOUTH;
    } while (lookHeading(robot, heading) == IRobot.WALL);
                    
                }
                
            }
            
            
        } while(lookHeading(robot, heading) == IRobot.WALL);
        
        return heading;
    }
}


