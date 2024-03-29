package classes;

import javafx.application.Application;

import java.util.ArrayList;
import java.util.List;

//this class provides the basic logic for the main method in GUI class to properly function
public class GameController {
    //private fields:
    private static int currentLevel;
    private static Map map;

    public GameController()
    {
        currentLevel = 1;
        map = new Map(currentLevel);
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
            int distance = map.shortestPath(map.getTileArray(),map.getPlayerPosition(), position);
            if (getMap().getPlayer().getAttackRange() >= distance && distance != -1)
                validTargets.add(position);
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

        int row1 = xPosition.getRowPosition();
        int row2 = yPosition.getRowPosition();

        int col1 = xPosition.getColumnPosition();
        int col2 = yPosition.getColumnPosition();
    	//if not within range just return false immediately
    	if (Math.sqrt((row2 - row1) * (row2 - row1) + (col2 - col1) * (col2 - col1)) > attackRange) {
    		return false;
    	} 
    	
    	//interpolate linearly between source and destination to look for obstacles (walls)
    	int iterations = 6 * (Math.abs(row1 - row2) + Math.abs(col1 - col2));
    	double stepSize = 1 / iterations;
    	int t = 0;
    	
    	for (int i = 0; i < iterations; i++) {
    		
    		double nextRow = row1 + t * (row2 - row1);
    		double nextCol = col1 + t * (col2 - col1);
    		
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

    public void enemiesMoveIfNotAttack(Map map) {
        ArrayList<ObjectPosition> enemyPositions = map.getEnemiesPositions();
        List<Enemy> enemies = map.getEnemies();
        for (int i = 0; i < enemyPositions.size(); i++) {
            if (canXAttackY(enemyPositions.get(i), map.getPlayerPosition(), 10)) {
                enemies.get(i).attack(map);
            } else {
                map.moveEnemies(enemyPositions.get(i));
            }
        }
    }

    public void exitReachedNextLevel() {
        if (isExitReach()) {
            enterNextLevel();
        }
    }
}

