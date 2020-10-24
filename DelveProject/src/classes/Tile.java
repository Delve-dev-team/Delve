package classes;
 
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Tile {

	private boolean exit = false;
	private boolean player = false;
	private boolean shop = false;
	private boolean wall = false;
	private Queue<Enemy> enemies;
	public Tile(boolean isTileWalled) {
		enemies = new LinkedList<>();
		wall = isTileWalled;
	}
	
	public Tile(Enemy enemy) {
		enemies = new LinkedList<>();
		enemies.add(enemy);
	}
	public void removePlayer()
	{
		if (!wall)
			player = false;
	}
	public void addPlayer()
	{
		if (!wall)
			player = true;
	}
	public void removeShop()
	{
		if (!wall)
			shop = false;
	}
	public void addShop()
	{
		if (!wall)
			shop = true;
	}
	public void removeExit()
	{
		if (!wall)
			shop = false;
	}
	public void removeWall()
	{
		wall = false;
	}
	public void addExit()
	{
		if (!wall)
			shop = true;
	}
	public void addEnemy(Enemy enemy)
	{
		if (!wall)
			enemies.add(enemy);
	}
	public Enemy removeOneEnemy()
	{
		if (!wall)
			return enemies.poll();
		else
			return null;
	}
	public void removeAll()
	{
		if (!wall)
			removeExit();
			removePlayer();
			removeShop();
			removeWall();
			enemies.clear();
	}

	public boolean isPlayerHere()
	{
		return player;
	}
	public boolean isShopHere()
	{
		return shop;
	}
	public boolean isWallHere()
	{
		return wall;
	}
	public boolean isEnemyHere()
	{
		return !enemies.isEmpty();
	}
	public boolean isExitHere()
	{
		return exit;
	}
	public boolean isEmpty()
	{
		return (!isEnemyHere() && !isPlayerHere() && !isShopHere() && !isExitHere() && !isWallHere());
	}
	
	
}
