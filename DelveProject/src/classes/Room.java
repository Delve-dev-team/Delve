package classes;

import java.util.ArrayList;

public class Room {

	
	public Tile[][] tileGrid = new Tile[10][10];
	ArrayList<ExitDir> exitDirections; 
	
	
	public Room(ExitDir[] exitsToGenerate, int numEnemies, boolean includeShop) {
		exitDirections = new ArrayList<ExitDir>();
		for (int i = 0; i < exitsToGenerate.length; i++) {
			exitDirections.add(exitsToGenerate[i]);
		}
		
		assignTileGrid();
		generateExits();
		
		for (int i = 0; i < numEnemies; i++) {
			addEnemy();
		}
		
		if (includeShop) {
			addShop();
		}
		
	}
	
	public Room(ExitDir[] exitsToGenerate, int numEnemies, boolean includeShop, boolean includePlayer) {
		exitDirections = new ArrayList<ExitDir>();
		for (int i = 0; i < exitsToGenerate.length; i++) {
			exitDirections.add(exitsToGenerate[i]);
		}
		
		assignTileGrid();
		generateExits();
		
		for (int i = 0; i < numEnemies; i++) {
			addEnemy();
		}
		
		if (includeShop) {
			addShop();
		}
		
		if (includePlayer) {
			addPlayer();
		}
		
	}
	
	public Room(ExitDir[] exitsToGenerate, int numEnemies, boolean includeShop, boolean includePlayer, boolean includeExit) {
		exitDirections = new ArrayList<ExitDir>();
		for (int i = 0; i < exitsToGenerate.length; i++) {
			exitDirections.add(exitsToGenerate[i]);
		}
		
		assignTileGrid();
		generateExits();
		
		for (int i = 0; i < numEnemies; i++) {
			addEnemy();
		}
		
		if (includeShop) {
			addShop();
		}
		
		if (includeExit) {
			addExit();
		}
		
	}
	
	//This can be modified later to make more interesting rooms. 
	private void assignTileGrid(){
		
		int rand = (int)((Math.random() -.000001) * 7);
		
		//hallway
		if (rand == 1 || rand == 6) {
			for (int row = 0; row < 10; row++) {
				for (int col = 0; col < 10; col++) {	
					
					if (row == 4 && col == 4) {
						Tile tile = new Tile();
						tileGrid[row][col] = tile;

					}
					else if (row == 4 && col == 5) {
						Tile tile = new Tile();
						tileGrid[row][col] = tile;

					}
					else if (row == 5 && col == 4) {
						Tile tile = new Tile();
						tileGrid[row][col] = tile;

					}
					else if (row == 5 && col == 5) {
						Tile tile = new Tile();
						tileGrid[row][col] = tile;

					}
					else {
						Tile tile = new Tile(new Wall());
						tileGrid[row][col] = tile;
					}
				}
			}
		}
		//round room
		else if (rand == 2) {
			for (int row = 0; row < 10; row++) {
				for (int col = 0; col < 10; col++) {

					if (Math.sqrt((row - 4.5) * (row - 4.5) + (col - 4.5) * (col - 4.5)) < 5) {
						Tile tile = new Tile();
						tileGrid[row][col] = tile;
					} else {
						Tile tile = new Tile(new Wall());
						tileGrid[row][col] = tile;
					}

				}
			}
		}
		//ring
		else if (rand == 3) {
			for (int row = 0; row < 10; row++) {
				for (int col = 0; col < 10; col++) {

					double distance = Math.sqrt((row - 4.5) * (row - 4.5) + (col - 4.5) * (col - 4.5));
					if (distance > 3 & distance < 5) {
						Tile tile = new Tile();
						tileGrid[row][col] = tile;
					} else {
						Tile tile = new Tile(new Wall());
						tileGrid[row][col] = tile;
					}

				}
			}
		}
		//square room
		else {
			for (int row = 0; row < 10; row++) {
				for (int col = 0; col < 10; col++) {

					if (2 <= row && row <= 7 && 2 <= col && col <= 7) {
						Tile tile = new Tile();
						tileGrid[row][col] = tile;
					} else {
						Tile tile = new Tile(new Wall());
						tileGrid[row][col] = tile;
					}

				}
			}
		}
	}
	
	public void printRoom() {
		for (int row = 0; row < 10; row++) {
			for (int col = 0; col < 10; col++) {
				
				if ((tileGrid[row][col].containsObjectOfType("Wall")))
					System.out.print("w");
				else if ((tileGrid[row][col].containsObjectOfType("Shop")))
					System.out.print("S");
				else if ((tileGrid[row][col].containsObjectOfType("Enemy")))
					System.out.print("E");
				else if ((tileGrid[row][col].containsObjectOfType("Player")))
					System.out.print("P");
				else if ((tileGrid[row][col].containsObjectOfType("Exit")))
					System.out.print("X");
				else
					System.out.print(" ");
				
			}
			System.out.println();
		}
	}
	
	//this will generate an exit in the directions given by exitDirections 
	//always generates a 2-width line on the 4-5 coordinate of the room
	private void generateExits() {
		
		if (exitDirections.contains(ExitDir.LEFT)) {
			int col = 0; 
			int row = 4;
			while (!tileGrid[row][col].elements.isEmpty()) {
				tileGrid[row][col].removeAll();
				col++;
			}
			
			col = 0;
			row = 5;
			
			while (!tileGrid[row][col].elements.isEmpty()) {
				tileGrid[row][col].removeAll();
				col++;
			}
		}
		
		if (exitDirections.contains(ExitDir.RIGHT)) {
			int col = 9; 
			int row = 4;
			while (!tileGrid[row][col].elements.isEmpty()) {
				tileGrid[row][col].removeAll();
				col--;
			}
			
			col = 9;
			row = 5;
			
			while (!tileGrid[row][col].elements.isEmpty()) {
				tileGrid[row][col].removeAll();
				col--;
			}
		}
	
		if (exitDirections.contains(ExitDir.DOWN)) {
			int col = 4; 
			int row = 9;
			while (!tileGrid[row][col].elements.isEmpty()) {
				tileGrid[row][col].removeAll();
				row--;
			}
			
			col = 5;
			row = 9;
			
			while (!tileGrid[row][col].elements.isEmpty()) {
				tileGrid[row][col].removeAll();
				row--;
			}
		}
		
		if (exitDirections.contains(ExitDir.UP)) {
			int col = 4; 
			int row = 0;
			while (!tileGrid[row][col].elements.isEmpty()) {
				tileGrid[row][col].removeAll();
				row++;
			}
			
			col = 5;
			row = 0;
			
			while (!tileGrid[row][col].elements.isEmpty()) {
				tileGrid[row][col].removeAll();
				row++;
			}
		}
		
	}
	
	//these addEnemy and addShop methods are used for room generation uwu
	private void addEnemy() {
		
		//gets num of empty tiles
		int numEmptyTiles = 0; 
		for(int row = 0; row < 10; row++) {
			for (int col = 0; col < 10; col++) {
				if (tileGrid[row][col].elements.size() == 0)
					numEmptyTiles++;
			}
		}
		
		int random = (int)(Math.random() * numEmptyTiles); 
		int numTilesAccessed = 0;
		for (int row = 0; row < 10; row++) {
			for (int col = 0; col < 10; col++) {
				
				if (tileGrid[row][col].elements.size() == 0) {
					if (random == numTilesAccessed) {
						//TODO change this to make the enemy scale somehow. 
						tileGrid[row][col].elements.add(new Enemy());
					}
					numTilesAccessed++;			
				}
				
			}
		}
		
	}
	
	//works the same as addEnemy, but adds a shop instead
	private void addShop() {
		// gets num of empty tiles
		int numEmptyTiles = 0;
		for (int row = 0; row < 10; row++) {
			for (int col = 0; col < 10; col++) {
				if (tileGrid[row][col].elements.size() == 0)
					numEmptyTiles++;
			}
		}

		int random = (int) (Math.random() * numEmptyTiles);
		int numTilesAccessed = 0;
		for (int row = 0; row < 10; row++) {
			for (int col = 0; col < 10; col++) {

				if (tileGrid[row][col].elements.size() == 0) {
					if (random == numTilesAccessed) {
						// TODO change this to make the shop change somehow.
						tileGrid[row][col].elements.add(new Shop());
					}
					numTilesAccessed++;
				}

			}
		}
	}
	
	private void addPlayer() {
		// gets num of empty tiles
		int numEmptyTiles = 0;
		for (int row = 0; row < 10; row++) {
			for (int col = 0; col < 10; col++) {
				if (tileGrid[row][col].elements.size() == 0)
					numEmptyTiles++;
			}
		}

		int random = (int) (Math.random() * numEmptyTiles);
		int numTilesAccessed = 0;
		for (int row = 0; row < 10; row++) {
			for (int col = 0; col < 10; col++) {

				if (tileGrid[row][col].elements.size() == 0) {
					if (random == numTilesAccessed) {
						tileGrid[row][col].elements.add(new Player());
					}
					numTilesAccessed++;
				}

			}
		}
	}
	
	private void addExit() {
		// gets num of empty tiles
		int numEmptyTiles = 0;
		for (int row = 0; row < 10; row++) {
			for (int col = 0; col < 10; col++) {
				if (tileGrid[row][col].elements.size() == 0)
					numEmptyTiles++;
			}
		}

		int random = (int) (Math.random() * numEmptyTiles);
		int numTilesAccessed = 0;
		for (int row = 0; row < 10; row++) {
			for (int col = 0; col < 10; col++) {

				if (tileGrid[row][col].elements.size() == 0) {
					if (random == numTilesAccessed) {
						tileGrid[row][col].elements.add(new Exit());
					}
					numTilesAccessed++;
				}

			}
		}
	}
	
}
