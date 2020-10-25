package classes;

import java.awt.Point;
import java.util.*;

public class Map {

	private Room[][] roomArray;
	private Tile[][] tileArray;
	
	public Map(int currentLevel) {
		
		
		//this is only a vague guide for the number of rooms to generated
		//the actual number of rooms will probably be a bit higher.
		int numRooms = (int)(currentLevel / 2) + 5;
		roomArray = new Room[numRooms * 2][numRooms * 2];
		//numRooms also acts as a center of sorts for the map to start at.
		int centerIndex = numRooms;

		
		
		//first, generate the rooms: use an ArrayList to keep track of rooms to be generated
		ArrayList<Point> roomsToGenerate = new ArrayList<Point>();
		
		int roomsCompleted = 0;
		roomsToGenerate.add(new Point(centerIndex, centerIndex));
		
		
		while(roomsToGenerate.size() > 0) {
			
			
			
			//make a new room
			Point currentPoint = roomsToGenerate.get(0);
			roomsToGenerate.remove(0);
			ExitDir[] exits = GenerateRandomExits(currentPoint, numRooms - roomsCompleted);
			roomsCompleted++;
			
			//we'll put between one and three enemies in any given room
			int enemiesToPlace = (int)(Math.random() * 3) + 1; //between 1 and 3 enemies
			int shopFactor = (int)(Math.random() * 2 * numRooms);
			boolean shouldGenerateShop = false;
			if (shopFactor == 0) {
				shouldGenerateShop = true;
			}
			
			//if This is the first room completed, generate player. 
			if (roomsCompleted == 1) {
				roomArray[currentPoint.x][currentPoint.y] = new Room(exits, enemiesToPlace, shouldGenerateShop, true);
			}
			else if (roomsCompleted >= numRooms && roomsToGenerate.isEmpty()) {
				roomArray[currentPoint.x][currentPoint.y] = new Room(exits, enemiesToPlace, shouldGenerateShop, false, true);
			}
			else {
				roomArray[currentPoint.x][currentPoint.y] = new Room(exits, enemiesToPlace, shouldGenerateShop);
					
			}
			
			//generate exit in last room generated
			
			
			
			
			//debugging
			System.out.println("\nNew Iteration");
			System.out.print("Point (" + currentPoint.x  + ", " + currentPoint.y + ") ");
			for (int k = 0; k < exits.length; k++) {
				System.out.print(exits[k]);
			}
			System.out.println();
			
			//add the rooms that must be attached to be generated			
			for (int i = 0; i < exits.length; i++) {
				if (exits[i] == ExitDir.DOWN && roomArray[currentPoint.x + 1][currentPoint.y] == null) {
					
					boolean isInArray = false;
					for (int p = 0; p < roomsToGenerate.size(); p++) {
						if ((currentPoint.x + 1 == roomsToGenerate.get(p).x && currentPoint.y == roomsToGenerate.get(p).y)) {
							isInArray = true;
						}
					}
					if (!isInArray) {
						roomsToGenerate.add(new Point(currentPoint.x + 1, currentPoint.y));
					}

				}
				else if (exits[i] == ExitDir.UP && roomArray[currentPoint.x - 1][currentPoint.y] == null) {
					
					boolean isInArray = false;
					for (int p = 0; p < roomsToGenerate.size(); p++) {
						if ((currentPoint.x - 1 == roomsToGenerate.get(p).x && currentPoint.y == roomsToGenerate.get(p).y)) {
							isInArray = true;
						}
					}
					if (!isInArray) {
						roomsToGenerate.add(new Point(currentPoint.x - 1, currentPoint.y));
					}
					
				}
				else if (exits[i] == ExitDir.LEFT && roomArray[currentPoint.x][currentPoint.y - 1] == null) {
					
					boolean isInArray = false;
					for (int p = 0; p < roomsToGenerate.size(); p++) {
						if ((currentPoint.x == roomsToGenerate.get(p).x && currentPoint.y == roomsToGenerate.get(p).y - 1)) {
							isInArray = true;
						}
					}		
					if (!isInArray) {
						roomsToGenerate.add(new Point(currentPoint.x, currentPoint.y - 1));
					}
				}
				else if (exits[i] == ExitDir.RIGHT && roomArray[currentPoint.x][currentPoint.y + 1] == null) {
					
					boolean isInArray = false;

					for (int p = 0; p < roomsToGenerate.size(); p++) {
						if ((currentPoint.x == roomsToGenerate.get(p).x && currentPoint.y == roomsToGenerate.get(p).y + 1)) {
							isInArray = true; 
						}
					}	
					if (!isInArray) {
						roomsToGenerate.add(new Point(currentPoint.x, currentPoint.y + 1));
					}
				}
			}
		}
		
		constructTileArray();
		
		
	}
	
	public void constructTileArray() {
		
		int leftIndex = roomArray.length;
		int rightIndex = 0;
		int topIndex = roomArray.length;
		int bottomIndex = 0; 
		
		//fetches rightmost and leftmost indices of room array
		//this can be used later to make the map look prettier (and more efficient)
		for (int row = 0; row < roomArray.length; row++) {
			for (int col = 0; col < roomArray.length; col++) {
				if (roomArray[row][col] != null) {
					if (row < topIndex)
						topIndex = row;
					if (row > bottomIndex)
						bottomIndex = row;
					if (col < leftIndex)
						leftIndex = col;
					if (col > rightIndex)
						rightIndex = col;
				} 	
			}
		}
		//unused code above
		
		
		tileArray = new Tile[roomArray.length * 10][roomArray.length * 10];
		
		for (int row = 0; row < tileArray.length; row++) {
			for (int col = 0; col < tileArray.length; col++) {
				int roomRow = (row - (row % 10)) / 10;
				int roomCol = (col - (col % 10)) / 10; 
				if (roomArray[roomRow][roomCol] == null) {
					tileArray[row][col] = new Tile(true);
				}
				else { //not null
					Tile[][] miniGrid = roomArray[roomRow][roomCol].tileGrid;
					tileArray[row][col] = miniGrid[row % 10][col % 10];
				}
				
			}
		}
	}
	
	public void printTileGrid() {
		for (Tile[] tiles : tileArray) {
			for (int col = 0; col < tileArray.length; col++) {

				if (tiles[col].isWallHere())
					System.out.print("w");
				else if (tiles[col].isShopHere())
					System.out.print("S");
				else if (tiles[col].isEnemyHere())
					System.out.print("E");
				else if (tiles[col].isPlayerHere())
					System.out.print("P");
				else if (tiles[col].isExitHere())
					System.out.print("X");
				else
					System.out.print(" ");

			}
			System.out.println();
		}
	}

	private ArrayList<Integer> guiMapBoundaries() {
		//get the location we want to show on the map
		int startingRow = Integer.MAX_VALUE;
		int startingColumn = Integer.MAX_VALUE;
		int endingRow = Integer.MIN_VALUE;
		int endingColumn = Integer.MIN_VALUE;
		//this is the expand distance just to give the sense of boundaries
		final int EXPAND_DISTANCE = 5;
		for (int row = 0; row < tileArray.length; row++) {
			for (int col = 0; col < tileArray.length; col++) {
				if (tileArray[row][col].isInDoor() && row <= startingRow)
					startingRow = row;
				if (tileArray[row][col].isInDoor() && col <= startingColumn)
					startingColumn = col;
				if (tileArray[row][col].isInDoor() && row >= endingRow)
					endingRow = row;
				if (tileArray[row][col].isInDoor() && col >= endingColumn)
					endingColumn = col;
			}
		}

		startingRow -= EXPAND_DISTANCE;
		startingColumn -= EXPAND_DISTANCE;
		endingRow += EXPAND_DISTANCE;
		endingColumn += EXPAND_DISTANCE;

		System.out.println("starting row: " + startingRow);
		System.out.println("starting column: " + startingColumn);
		System.out.println("ending row: " + endingRow);
		System.out.println("ending column: " + endingColumn);
		ArrayList<Integer> result = new ArrayList<>(Arrays.asList(startingRow, startingColumn, endingRow, endingColumn));

		return result;
	}

	public int guiMapLineNum()
	{
		return Math.abs(guiMapBoundaries().get(2) - guiMapBoundaries().get(0));
	}

	public String guiMap()
	{
		//start printing the result we want

		//count is for debugging
		int count = 0;
		//result is the string we return for the map
		String result = "";
		for (int row = guiMapBoundaries().get(0); row < guiMapBoundaries().get(2); row ++) {
			for (int col = guiMapBoundaries().get(1); col < guiMapBoundaries().get(3); col++) {
				if (tileArray[row][col].isWallHere())
					result = result.concat("W");
				else if (tileArray[row][col].isShopHere())
					result = result.concat("S");
				else if (tileArray[row][col].isEnemyHere())
					result = result.concat("E");
				else if (tileArray[row][col].isPlayerHere())
					result = result.concat("P");
				else if (tileArray[row][col].isExitHere())
					result = result.concat("X");
				else
					result = result.concat(" ");
			}
			result = result.concat("\n");

		}
		return result;
	}
	
	private ExitDir[] GenerateRandomExits(Point currentRoomLoc, int newRoomsPossible){
		//first search neighbors to see what exits have to exist
		ArrayList<ExitDir> possibleExits = new ArrayList<ExitDir>();
		possibleExits.add(ExitDir.UP);
		possibleExits.add(ExitDir.DOWN);
		possibleExits.add(ExitDir.LEFT);
		possibleExits.add(ExitDir.RIGHT);

		ArrayList<ExitDir> exits = new ArrayList<ExitDir>();
		ArrayList<ExitDir> badExits = new ArrayList<ExitDir>();
		
		//one room lower
		if (roomArray[currentRoomLoc.x + 1][currentRoomLoc.y] != null) {
			if (roomArray[currentRoomLoc.x + 1][currentRoomLoc.y].exitDirections.contains(ExitDir.UP)){
				exits.add(ExitDir.DOWN);
			}
			else {
				badExits.add(ExitDir.DOWN);
			}
		}
		//one room higher
		if (roomArray[currentRoomLoc.x - 1][currentRoomLoc.y] != null) {
			if (roomArray[currentRoomLoc.x - 1][currentRoomLoc.y].exitDirections.contains(ExitDir.DOWN)){
				exits.add(ExitDir.UP);
			}
			else {
				badExits.add(ExitDir.UP);
			}
		}
		//one room right
		if (roomArray[currentRoomLoc.x][currentRoomLoc.y + 1] != null) {
			if (roomArray[currentRoomLoc.x][currentRoomLoc.y + 1].exitDirections.contains(ExitDir.LEFT)){
				exits.add(ExitDir.RIGHT);
			}			
			else {
				badExits.add(ExitDir.RIGHT);
			}
		}
		//one room left
		if (roomArray[currentRoomLoc.x][currentRoomLoc.y - 1] != null) {
			if (roomArray[currentRoomLoc.x][currentRoomLoc.y - 1].exitDirections.contains(ExitDir.RIGHT)){
				exits.add(ExitDir.LEFT);
			}
			else {
				badExits.add(ExitDir.LEFT);
			}
		}
		
		//if we don't want new rooms, return what we need to have
		if (exits.size() == 4 || newRoomsPossible < 1) {
			ExitDir[] exitsToReturn = new ExitDir[exits.size()];
			for (int i = 0; i < exits.size(); i++) {
				exitsToReturn[i] = exits.get(i);
			}
			return exitsToReturn;		
		}
		
		//first, figure out what exits we want to consider randomly generating
		ArrayList<ExitDir> exitsToConsider = new ArrayList<ExitDir>();
		
		for (int i = 0; i < possibleExits.size(); i++) {
			ExitDir toConsider = possibleExits.get(i);
			if (!exits.contains(toConsider) && !badExits.contains(toConsider)) {
				exitsToConsider.add(toConsider);
			}
		}
		
		//make sure we generate at least one new exit. 
		int newRoomsAdded = 0;
		
		int guaranteedToGenerate = (int)(Math.random() * exitsToConsider.size());
		exits.add(exitsToConsider.get(guaranteedToGenerate));
		newRoomsAdded++;
		
		for (int i = 0; i < exitsToConsider.size(); i++) {
			if (newRoomsAdded < newRoomsPossible) {
				
				if (i == guaranteedToGenerate && !exits.contains(exitsToConsider.get(guaranteedToGenerate))) {
					exits.add(exitsToConsider.get(i));
					newRoomsAdded++;
				}
				else if (Math.random() < .50 && !exits.contains(exitsToConsider.get(i)))  {
					exits.add(exitsToConsider.get(i));
					newRoomsAdded++;
				}
			}
		}
		
		ExitDir[] exitsToReturn = new ExitDir[exits.size()];
		for (int i = 0; i < exits.size(); i++) {
			exitsToReturn[i] = exits.get(i);
		}
		return exitsToReturn;
	}
	
	public void printRoomArray(){
		for (int row = 0; row < roomArray.length; row++) {
			for (int col = 0; col < roomArray.length; col++) {
				if (roomArray[row][col] != null) {
					System.out.print("R");
				}
				else {
					System.out.print(" ");
				}
			}
			System.out.println();
		}
	}

	public ObjectPosition getPlayerPosition()
	{
		ObjectPosition playerPosition = null;
		for (int row = 0; row < tileArray.length; row++) {
			for (int col = 0; col < tileArray[0].length; col++) {
				if ((tileArray[row][col].isPlayerHere()))
					playerPosition = ObjectPosition.of(row, col, this);
			}
		}
		return playerPosition;
	}


	public ArrayList<ObjectPosition> getEnemiesPositions()
	{
		ArrayList<ObjectPosition> enemiesPosition = new ArrayList<>();
		for (int row = 0; row < tileArray.length; row++) {
			for (int col = 0; col < tileArray[0].length; col++) {
				if ((tileArray[row][col].isEnemyHere()))
						enemiesPosition.add(ObjectPosition.of(row, col, this));
			}
		}
		return enemiesPosition;
	}
	public int numberOfEnemies()
	{
		return this.getEnemiesPositions().size();
	}
	public Room[][] getRoomArray() {
		return roomArray;
	}

	public Tile[][] getTileArray() {
		return tileArray;
	}

	//a depth first search method that determine the shortest distance between Player and Enemy
	public int shortestPath(Tile[][] map, ObjectPosition startingPosition, ObjectPosition endPosition) {

		//setting the start positions
		int startingRow = startingPosition.getRowPosition();
		int startingColumn = startingPosition.getColumnPosition();

		//set the distance
		int distance = 0;

		//set the list for possible paths
		Queue<ObjectPosition> nextToVisit = new LinkedList<>();
		nextToVisit.offer(ObjectPosition.of(startingRow,startingColumn,this));
		Queue<ObjectPosition> temp = new LinkedList<>();

		//set the boolean map
		boolean[][] visited = new boolean[map.length][map[0].length];
		visited[startingRow][startingColumn] = true;

		//for debugging
		int loopcount = 0;

		while (!nextToVisit.isEmpty()) {
			//first read the starting position row and column
			ObjectPosition position = nextToVisit.poll();
			int row = position.getRowPosition();
			int col = position.getColumnPosition();

			//check if the position is already the ending position
			if (position.equals(endPosition)) {
				visited[row][col] = true;
				return distance;
			}
			//check if going up is a option
			if (row > 0 && !map[row - 1][col].isWallHere() && !visited[row - 1][col]) {
				temp.offer(ObjectPosition.of(row - 1,col,this));
				visited[row - 1][col] = true;
			}
			//check if going down is a option
			if (row < map.length - 1 && !map[row + 1][col].isWallHere()&& !visited[row + 1][col]) {
				temp.offer(ObjectPosition.of(row + 1,col,this));
				visited[row + 1][col] = true;
			}
			//check if going left is a option
			if (col > 0 && !map[row][col - 1].isWallHere()  && !visited[row][col - 1]) {
				temp.offer(ObjectPosition.of(row ,col - 1,this));
				visited[row][col - 1] = true;
			}
			//check if going right is a option
			if (col < map[0].length - 1 && !map[row][col + 1].isWallHere() && !visited[row][col + 1]) {
				temp.offer(ObjectPosition.of(row ,col + 1,this));
				visited[row][col + 1] = true;
			}

			//if there are no where to go next and the there are temp options, what to go next become temp options, refresh the temp and iterate distance
			if (nextToVisit.isEmpty() && !temp.isEmpty())
			{
				nextToVisit = temp;
				temp = new LinkedList<>();
				distance++;
			}

		}
		//if the end position is visited return the distance, if it is not, means there's no path to the end position, return -1
		if (visited[endPosition.getRowPosition()][endPosition.getColumnPosition()]) {
			return distance;
		}
		else {
			return -1;
		}
	}

	//move enemies one block into a random direction
	public void moveEnemies() {
		for (ObjectPosition position: this.getEnemiesPositions())
		{
			Random random = new Random();
			boolean readyToGO = false;
			int row = position.getRowPosition();
			int col = position.getColumnPosition();
			int direction;
			while (!readyToGO)
			{
				direction = random.nextInt(4);
				switch (direction)
				{
					//going up
					case 0:
						if (isLegalForEnemies(row - 1,col))
						{
							getTileArray()[row - 1][col].addEnemy(getTileArray()[row][col].removeEnemy());
							readyToGO = true;
						}
					break;

						//going down
					case 1:
						if (isLegalForEnemies(row + 1,col))
						{
							getTileArray()[row + 1][col].addEnemy(getTileArray()[row][col].removeEnemy());
							readyToGO = true;
						}
						break;

						//going left
					case 2:
						if (isLegalForEnemies(row, col - 1))
						{
							getTileArray()[row][col - 1].addEnemy(getTileArray()[row][col].removeEnemy());
							readyToGO = true;
						}
						break;

						//going right
					case 3:
						if (isLegalForEnemies(row, col + 1))
						{
							getTileArray()[row][col + 1].addEnemy(getTileArray()[row][col].removeEnemy());
							readyToGO = true;
						}
						break;
				}
			}

		}

	}

	public void movePlayer(Direction direction)
	{
		int row = getPlayerPosition().getRowPosition();
		int col = getPlayerPosition().getColumnPosition();
		switch (direction)
		{
			//going up
			case UP:
				if (isLegalForPlayer(row - 1, col))
					getTileArray()[row - 1][col].addPlayer(getTileArray()[row][col].removePlayer());
				else
					System.out.println("can not go up");
				break;

			//going down
			case DOWN:
				if (isLegalForPlayer(row + 1, col))
					getTileArray()[row + 1][col].addPlayer(getTileArray()[row][col].removePlayer());
				else
					System.out.println("can not go down");
				break;

			//going left
			case LEFT:
				if (isLegalForPlayer(row, col - 1))
					getTileArray()[row][col - 1].addPlayer(getTileArray()[row][col].removePlayer());
				else
					System.out.println("can not go left");
				break;

			//going right
			case RIGHT:
				if (isLegalForPlayer(row, col + 1))
					getTileArray()[row][col + 1].addPlayer(getTileArray()[row][col].removePlayer());
				else
					System.out.println("can not go right");
				break;
		}
	}

	private boolean isLegalForEnemies(int row, int col){
		return getTileArray()[row][col].isEmpty();
	}

	private boolean isLegalForPlayer(int row, int col) {
		return !getTileArray()[row][col].isEnemyHere() && !getTileArray()[row][col].isWallHere() && !getTileArray()[row][col].isPlayerHere();
	}

	public Player getPlayer()
	{
		for (Tile[] tiles : tileArray) {
			for (int col = 0; col < tileArray[0].length; col++) {
				if ((tiles[col].isPlayerHere()))
					return tiles[col].getPlayer();
			}
		}
		return null;
	}

	public List<Enemy> getEnemies(){
		List<Enemy> enemies = new ArrayList<>();
		for (Tile[] tiles : tileArray) {
			for (int col = 0; col < tileArray[0].length; col++) {
				if ((tiles[col].isPlayerHere()))
						enemies.add(tiles[col].getEnemy());
			}
		}
		return enemies;
	}


}
