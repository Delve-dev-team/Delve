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
            int distance = map.shortestPath(map.getTileArray(),map.getPlayerPosition(),position);
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

    public static Map getMap() {
        return map;
    }
}

