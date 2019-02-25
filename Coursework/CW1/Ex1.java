/* 
 * File:    DumboController.java
 * Created: 17 September 2002, 00:34
 * Author:  Stephen Jarvis
 */

import uk.ac.warwick.dcs.maze.logic.IRobot;

public class Ex1
{
    
    public void controlRobot(IRobot robot) {

	int randno;
	int direction;
    String maze_state; // Record the environment robot is in
    String robot_move; //Record where is the robot moving

	// Select a random number
    do {
	randno = (int) Math.round(Math.random()*3);
	  if (randno == 0)
	    direction = IRobot.LEFT;
	  else if (randno == 1)
	    direction = IRobot.RIGHT;
	  else if (randno == 2)
	    direction = IRobot.BEHIND;
	  else 
	    direction = IRobot.AHEAD;	
	          ;
	         
      }
      while (robot.lok(direction) == IRobot.WALL);
			/* Face the robot in this direction */   
	
        if (robot.look(IRobot.AHEAD) == IRobot.WALL && robot.look(IRobot.LEFT) == IRobot.WALL && robot.look(IRobot.RIGHT) == IRobot.WALL)	//dead end
            maze_state = "at a dead end";
        else if (robot.look(IRobot.AHEAD) != IRobot.WALL && robot.look(IRobot.LEFT) == IRobot.WALL && robot.look(IRobot.RIGHT) == IRobot.WALL) //corridor
            maze_state = "down a corridor";
        else if (robot.look(IRobot.AHEAD) != IRobot.WALL && robot.look(IRobot.LEFT) != IRobot.WALL && robot.look(IRobot.RIGHT) != IRobot.WALL && robot.look(IRobot.BEHIND) != IRobot.WALL)                                                  //crossroad  
            maze_state = "at a crossroad";
        else 	
            maze_state = "at a junction";
        
        robot.face(direction);
        
        switch (direction) {
            case IRobot.LEFT: robot_move = " left " ; break;
            case IRobot.RIGHT: robot_move = " right "; break;
            case IRobot.BEHIND: robot_move = " backwards "; break;
            default: robot_move = " forward "; break;
        }
        System.out.println("I'm going" + robot_move + maze_state) ;
    }

}
