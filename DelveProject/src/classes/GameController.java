package classes;

import java.util.ArrayList;

//this class provides the basic logic for the main method in GUI class to properly function
public class GameController {
    //private fields:
    private static int currentLevel;
    private Player player;
    private Map map;
    private Enemy enemy;

    public GameController()
    {
        currentLevel = 1;
        this.player = new Player();
        this.map = new Map(currentLevel);
        this.enemy = new Enemy(currentLevel);
    }
    //boolean method(methods that returns a boolean value)
    public boolean isGameOver()
    {
        return player.getHP() <= 0;
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
        return !AvailableTargets().isEmpty();
    }

    //action methods
    //this method returns a list of positions of the targets that the player can attack, GUI can use these position to show where those available targets are.
    public ArrayList<ObjectPosition> AvailableTargets()
    {
        ArrayList<ObjectPosition> validTargets = new ArrayList<>();
        for (ObjectPosition position: map.getEnemiesPositions())
        {
            System.out.println("Player position: " + "row " + map.getPlayerPosition().getRowPosition() + "column " + map.getPlayerPosition().getColumnPosition());
            System.out.println("enemy positions: " + "row " + position.getRowPosition() + "column " + position.getColumnPosition());
            int distance = map.shortestPath(map.getTileArray(),map.getPlayerPosition(),position);
            System.out.println("distance from player: " + distance);
            if (player.getAttackRange() >= distance && distance != -1)
                validTargets.add(position);
        }

        return validTargets;
    }

    //this method calculate how many monsters will attack player this round
    public int amountOfAttacksFromMonster()
    {
        int count = 0;
        for (ObjectPosition position: map.getEnemiesPositions())
        {
            int distance = map.shortestPath(map.getTileArray(),position,map.getPlayerPosition());
            if (enemy.getAttackRange() >= distance && distance != -1)
                count ++;
        }
        return count;
    }
    public void enterNextLevel()
    {
        currentLevel ++;
        map = new Map(currentLevel);
        enemy = new Enemy(currentLevel);
    }

    //getter methods
    public static int getCurrentLevel() {
        return currentLevel;
    }

    public Map getMap() {
        return map;
    }

    public Player getPlayer() {
        return player;
    }

    public Enemy getEnemy() {
        return enemy;
    }
}

