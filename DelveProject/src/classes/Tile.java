package classes;
 
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Tile {

	private boolean exit = false;
	private List<Player> player;
	private Shop shop;
	private boolean wall = false;
	private Queue<Enemy> enemies;
	public Tile(boolean isTileWalled) {
		enemies = new LinkedList<>();
		player = new LinkedList<>();
		wall = isTileWalled;
	}
	
	public Tile(Enemy enemy) {
		enemies = new LinkedList<>();
		enemies.add(enemy);
	}
	public void removePlayer()
	{
		if (!wall)
			player.clear();
	}
	public void addPlayer(Player player)
	{
		if (!wall)
			this.player.add(player);
	}
	public void removeShop()
	{
		if (!wall)
			shop = null;
	}
	public void addShop(Shop shop)
	{
		if (!wall)
			this.shop = shop;
	}

	public void removeWall()
	{
		wall = false;
	}
	public void addExit()
	{
		if (!wall)
			exit = true;
	}
	public void removeExit()
	{
		if (!wall)
			exit = false;
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
		return !player.isEmpty();
	}
	public boolean isShopHere()
	{
		return this.shop != null;
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

	public Queue<Enemy> getEnemies() {
		return enemies;
	}

	public Player getPlayer() {
		return player.get(0);
	}

	public Shop getShop() {
		return shop;
	}
}
