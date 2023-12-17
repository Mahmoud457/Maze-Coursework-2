/*
 Preamble: I heavily deviated from the design steps in order to achieve the customer requests. My program can be summarised as the following set of instructions:
    1. Check each square around the robot and place the passages and beenbefores in their respective arraylist. 2. The robot will randomly move in the direction of
    a passage square, if available, and record the cardinal direction in the path arraylist. 3. If no possible passages available, the program will backtrack.
    4. My backtrack method works backwards in the path arraylist and moves the robot in the opposite direction allowing it to backtrack through its path.
    5. This will occur until there are new passages to go down, stopping the backtracking process.
    This meets the design steps without requiring a junction object as described by the design steps thus making my soloution simpler, and in my opinion more elegant,
    and superior from a memory standpoint.  The robot will always find the junction however, in the worst case scenario, it will have to search the entire maze before it reaches
    the goal. Hence the number of steps could equal the number of non wall squares. 
    Thus this indicates that the steps in Big-O notation is O(N) where N is the number of non wall squares in the maze. I did not include any junction recording as that was not 
    a customer request and thus not required.
 */



import java.util.ArrayList;
import uk.ac.warwick.dcs.maze.logic.IRobot;

public class Ex1 {
    int heading = IRobot.NORTH;
    ArrayList<Integer> path = new ArrayList<>(); //Current route without any deadends.
    ArrayList<Integer> passageDirections = new ArrayList<>();
    int[] directions = {IRobot.AHEAD, IRobot.BEHIND, IRobot.RIGHT, IRobot.LEFT};
    int[] cardinalDirections = {IRobot.NORTH, IRobot.SOUTH, IRobot.EAST, IRobot.WEST};

    public void controlRobot(IRobot robot){
        senseSurroundings(robot, cardinalDirections);
        if (passageDirections.size() != 0){ //Checks if there are any available passages. 
            heading = passageDirections.get(genRandNum(passageDirections.size()));
            path.add(heading);
        }
        else{ //If not begin backtracking. Backtracking goes back through the path and sets the heading to the opposite direction. This will happen until new passages are found.
            
            heading = backtrack(path.get(path.size()-1));
            path.remove(path.size()-1);
        }

        passageDirections.clear();
        robot.setHeading(heading);
    }
    public void senseSurroundings(IRobot robot, int[] possDirections){ //Senses the surroundings and adds the relevant passages and beenbefores to relevant array, replacement for counting exits.
        for(int dir: possDirections){
            int lookDir = lookHeading(robot, dir);
            if(lookDir == IRobot.PASSAGE){
                passageDirections.add(dir);
            }
            
        }
    }
  
    public int IndexOf(int[] array, int element){ 
        for(int i = 0; i<array.length; i++ ){
            if(array[i] == element){
                return i;
            }
        }
        return -1;
    }
    public int lookHeading(IRobot robot, int cardinalDir){ //Looks for cardinal directions.
        robot.setHeading(IRobot.NORTH);
        return robot.look(directions[IndexOf(cardinalDirections, cardinalDir)]);
    }
    public int genRandNum(int upperBound){
        return (int) Math.floor(Math.random()*upperBound);
    }
    public int backtrack(int dir){ //Returns opposite direction of a given direction.
        switch(dir) {
            case IRobot.NORTH: 
                return IRobot.SOUTH;
            case IRobot.EAST:
                return IRobot.WEST;
            case IRobot.SOUTH:
                return IRobot.NORTH;
            case IRobot.WEST:
                return IRobot.EAST;
        }
        return -1;
    }

}
