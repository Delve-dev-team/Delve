package classes;

import javafx.application.Application;

import java.util.ArrayList;

//this class provides the basic logic for the main method in GUI class to properly function
public class GameController {
    //private fields:
    private static int currentLevel;
    private static Map map;

    public GameController()
    {
        currentLevel = 1;
        map = new Map(currentLevel);
        //Create and Assign enemyClasses
    }
    public static void main(String[] args)
    {
        Application.launch(GUI.class);
    }
    //boolean method(methods that returns a boolean value)
    public boolean isGameOver()
    {
        return getMap().getPlayer().getHP() <= 0;
    }

    public boolean isExitReach()  //shows if the Player reaches the exit of that level
    {
        return (map.getTileArray()[map.getPlayerPosition().getRowPosition()][map.getPlayerPosition().getColumnPosition()].isExitHere());
    }

    public boolean isShopReach()  //shows if the Player reaches the exit of that level
    {
        return (map.getTileArray()[map.getPlayerPosition().getRowPosition()][map.getPlayerPosition().getColumnPosition()].isShopHere());
    }

    public boolean isThereAvailableTarget()
    {
        return !availableTargets().isEmpty();
    }

    //action methods
    //this method returns a list of positions of the targets that the player can attack, GUI can use these position to show where those available targets are.
    public ArrayList<ObjectPosition> availableTargets()
    {
        ArrayList<ObjectPosition> validTargets = new ArrayList<>();
        for (ObjectPosition position: map.getEnemiesPositions())
        {

        }

        return validTargets;
    }

    public void enterNextLevel()
    {
        currentLevel ++;
        map = new Map(currentLevel);
    }

    //getter methods
    public static int getCurrentLevel() {
        return currentLevel;
    }

    // This thing is some vector math that java isn't really built to do, but w/e :P
    public static boolean canXAttackY(ObjectPosition xPosition, ObjectPosition yPosition, double attackRange) {

        int xRowPosition = xPosition.getRowPosition();
        int yRowPosition = yPosition.getRowPosition();

        int xColPosition = xPosition.getColumnPosition();
        int yColPosition = yPosition.getColumnPosition();

    	//if not within range just return false immediately
    	if (Math.sqrt((yRowPosition - xRowPosition) * (yRowPosition - xRowPosition) + (yColPosition - xColPosition) * (yColPosition - xColPosition)) > attackRange) {
    		return false;
    	} 
    	
    	//interpolate linearly between source and destination to look for obstacles (walls)
    	int iterations = 2 * (Math.abs(xRowPosition - yRowPosition) + Math.abs(xColPosition - yColPosition));
    	double stepSize = 1 / iterations;
    	int t = 0;
    	
    	for (int i = 0; i < iterations; i++) {
    		
    		double nextRow = xRowPosition + t * (yRowPosition - xRowPosition);
    		double nextCol = xColPosition + t * (yColPosition - xColPosition);
    		
    		int adjustedRow = (int)(nextRow + .5f);
    		int adjustedCol = (int)(nextCol + .5f);
    		
    		if (map.getTileArray()[adjustedRow][adjustedCol].isWallHere()) {
    			return false;
    		}
    		
    		//raise t by a proportionate amount
    		t += stepSize;
    		
    	}
    	return true;
    }
    
    public static Map getMap() {
        return map;
    }
}

