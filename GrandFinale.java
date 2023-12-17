/*
 I implemented a stack suggested in Route B and built upon that. My program can be summarised with the following set of instructions; 1. On the first run the program will explore like normal. 
 2. When backtracking, any unnessecary directions will be removed from the path ArrayList, hence the program will only end with a direct path to the goal. 
 3. On subsuequent runs, the program will follow the previous direct path. The solution however isn't perfect.
 One issue is that the program would not find the fastest path, only a  path, this is especially significant when dealing with loopy mazes.
 */


import java.util.ArrayList;
import uk.ac.warwick.dcs.maze.logic.IRobot;
public class GrandFinale {
    int heading = IRobot.NORTH;
    ArrayList<Integer> path = new ArrayList<>(); //Current route taken without and deadends. 
    ArrayList<Integer> passageDirections = new ArrayList<>() ; //Directions which are passages. 
    int[] directions = {IRobot.AHEAD, IRobot.BEHIND, IRobot.RIGHT, IRobot.LEFT};
    int[] cardinalDirections = {IRobot.NORTH, IRobot.SOUTH, IRobot.EAST, IRobot.WEST};
    RobotData robotData = new RobotData();

    public void controlRobot(IRobot robot){
        if(robot.getRuns() == 0){
            robotData.clear();
        }
        
        if((robotData.returnPathLength() > 0)&&(robot.getRuns() > 0)){ //Following a route from a previous run.
            heading = robotData.returnNextDirection();
            path.add(heading);
        }
        
        else { //Explorer Section of the program
            senseSurroundings(robot, cardinalDirections);
        if (passageDirections.size() != 0){
            heading = passageDirections.get(genRandNum(passageDirections.size()));
            path.add(heading);
        }
        else{ //backtracks and removes unecessary direction from path arraylist. 
            heading = backtrack(path.get(path.size()-1));
            path.remove(path.size()-1);
        }
    }

        
        passageDirections.clear();
        robot.setHeading(heading);
    }
    public void senseSurroundings(IRobot robot, int[] possDirections){ //Looks in 4 directions around robot and places passages in perspective arraylist. 
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
    public int lookHeading(IRobot robot, int cardinalDir){//Looks for cardinal directions.
        robot.setHeading(IRobot.NORTH);
        return robot.look(directions[IndexOf(cardinalDirections, cardinalDir)]);
    }
    public int genRandNum(int upperBound){
        return (int) Math.floor(Math.random()*upperBound);
    }
    public int backtrack(int dir){ //Returns the opposite cardinal direction for a given  cardinal direction.
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
    public void reset(){

        if(path.size() > robotData.returnOriginalLength()){ //If Robot has made more progress will update path in robotData.
            robotData.setPath(path);
        }
        else{ //Otherwise will reset it to previous route. 
            robotData.backOriginal();
        }
        path.clear();
    }

}

class RobotData{ //Class where I save the past routes. I use getters and setters. 
    private ArrayList<Integer> path = new ArrayList<>();
    private ArrayList<Integer> originalPath = new ArrayList<>();


    public void addPath(int dir){
        path.add(dir);
    }
    public void setPath(ArrayList<Integer> directions){
        path.clear();
        for(int i: directions){
            path.add(i);
        }

        setOriginal();
        
    }
    public void setOriginal(){
        originalPath.clear();
        for(int i: path){
            System.out.println(originalPath.size());
            originalPath.add(i);
        }

    }
    public int returnNextDirection(){
        int dir = path.get(0);
        path.remove(0);
        return dir;
    }
    public int returnPathLength(){
        return path.size();
    }
    public int returnOriginalLength(){
        return originalPath.size();
    }
    public void backOriginal(){
        path.clear();
        for(int i: originalPath){
            path.add(i);
        }
    }
    public void clear(){
        path.clear();
        originalPath.clear();
    }
    public void printPath(){
        System.out.println(path);
    }
    

}
