package classes;
 
import sun.awt.image.ImageWatched;

import java.util.LinkedList;
import java.util.Queue;

public class Tile {

	private boolean exit = false;
	private Queue<Player> player;
	private Queue<Shop> shop;
	private boolean wall = false;
	private Queue<Enemy> enemy;
	public Tile(boolean isTileWalled) {
		enemy = new LinkedList<>();
		player = new LinkedList<>();
		shop = new LinkedList<>();
		wall = isTileWalled;
	}
	
	public Tile(Enemy enemy) {
		this.enemy = new LinkedList<>();
		this.enemy.add(enemy);
	}
	public void addPlayer(Player player)
	{
		if (!wall)
			this.player.add(player);
	}
	public Shop removeShop()
	{
		if (!wall)
			return shop.remove();
		else
			return null;
	}
	public void addShop(Shop shop)
	{
		if (!wall)
			this.shop.add(shop);
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
			this.enemy.add(enemy);
	}
	public Player removePlayer()
	{
		if (!wall)
			return player.remove();
		else
			return null;
	}
	public Enemy removeEnemy()
	{
		if (!wall)
			return enemy.remove();
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
			enemy.clear();
	}

	public boolean isPlayerHere()
	{
		return !player.isEmpty();
	}
	public boolean isShopHere()
	{
		return !shop.isEmpty();
	}
	public boolean isWallHere()
	{
		return wall;
	}

	public boolean isEnemyHere()
	{
		return !enemy.isEmpty();
	}

	public boolean isExitHere() {
		return exit;
	}
	public boolean isEmpty() {
		return (!isEnemyHere() && !isPlayerHere() && !isShopHere() && !isExitHere() && !isWallHere());
	}
	public boolean isInDoor()
	{
		return isEnemyHere() || isPlayerHere() || isExitHere() || isShopHere() || isEmpty();
	}

	public Player getPlayer() {
		return player.peek();
	}

	public Enemy getEnemy()
	{
		return enemy.peek();
	}

	public Shop getShop() {
		return shop.peek();
	}
}
