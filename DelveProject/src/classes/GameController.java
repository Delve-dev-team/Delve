package classes;

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
        return (map.getTileArray()[map.getPlayerPosition().getRowPosition()][map.getPlayerPosition().getColumnPosition()].containsObjectOfType("Exit"));
    }

    public boolean isShopReach()  //shows if the Player reaches the exit of that level
    {
        return (map.getTileArray()[map.getPlayerPosition().getRowPosition()][map.getPlayerPosition().getColumnPosition()].containsObjectOfType("Shop"));
    }

    public boolean canPlayerAttack()
    {
        return false;
    }
    //action methods
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
}

