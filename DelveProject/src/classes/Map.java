package classes;

import javax.swing.text.Position;
import java.awt.Point;
import java.util.*;

public class Map {

	private Room[][] roomArray;
	private Tile[][] tileArray;
	
	public Map(int currentLevel) {
		
		
		//this is only a vague guide for the number of rooms to generated
		//the actual number of rooms will probably be a bit higher.
		int numRooms = (int)(currentLevel / 2) + 3;
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
					tileArray[row][col] = new Tile();
					tileArray[row][col].add(new Wall());
				}
				else { //not null
					Tile[][] miniGrid = roomArray[roomRow][roomCol].tileGrid;
					tileArray[row][col] = miniGrid[row % 10][col % 10];
				}
				
			}
		}
	}
	
	public void printTileGrid() {
		for (int row = 0; row < tileArray.length; row++) {
			for (int col = 0; col < tileArray.length; col++) {
				
				if ((tileArray[row][col].containsObjectOfType("Wall")))
					System.out.print("w");
				else if ((tileArray[row][col].containsObjectOfType("Shop")))
					System.out.print("S");
				else if ((tileArray[row][col].containsObjectOfType("Enemy")))
					System.out.print("E");
				else if ((tileArray[row][col].containsObjectOfType("Player")))
					System.out.print("P");
				else if ((tileArray[row][col].containsObjectOfType("Exit")))
					System.out.print("X");
				else
					System.out.print(" ");
				
			}
			System.out.println();
		}
	}
	
	private ExitDir[] GenerateRandomExits(Point currentRoomLoc, int newRoomsPossible) {
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
			for (int col = 0; col < tileArray.length; col++) {
				if ((tileArray[row][col].containsObjectOfType("Player")))
					playerPosition = ObjectPosition.of(col, row, this);
			}
		}
		return playerPosition;
	}

	public ArrayList<ObjectPosition> getEnemiesPosition()
	{
		ArrayList<ObjectPosition> enemiesPosition = new ArrayList<>();
		for (int row = 0; row < tileArray.length; row++) {
			for (int col = 0; col < tileArray.length; col++) {
				if ((tileArray[row][col].containsObjectOfType("Enemy")))
					enemiesPosition.add(ObjectPosition.of(col,row,this));
			}
		}
		return enemiesPosition;
	}

	public Room[][] getRoomArray() {
		return roomArray;
	}

	public Tile[][] getTileArray() {
		return tileArray;
	}

	//a depth first search method that determine the shortest distance between Player and Enemy
	public static int shortestPath(Tile[][] map, ObjectPosition startingPosition, ObjectPosition endPosition) {
		int startingRow = startingPosition.getRowPosition();
		int StartingColumn = startingPosition.getColumnPosition();
		int distance = 0;
		Queue<int[]> nextToVisit = new LinkedList<>();
		nextToVisit.offer(new int[] {startingRow, StartingColumn});
		Queue<int[]> temp = new LinkedList<>();

		while (!nextToVisit.isEmpty()) {
			//first read the starting position row and column
			int[] position = nextToVisit.poll();
			int row = position[0];
			int col = position[1];

			//check if the position is already the ending position
			if (row == endPosition.getRowPosition() && col == endPosition.getColumnPosition())
				return distance;
			//check if going up is a option
			if (row > 0 && !map[row - 1][col].containsObjectOfType("Wall"))
				temp.offer(new int[] {row - 1, col});
			//check if going down is a option
			if (row < map.length - 1 && !map[row + 1][col].containsObjectOfType("Wall"))
				temp.offer(new int[] {row + 1, col});
			//check if going left is a option
			if (col > 0 && !map[row][col - 1].containsObjectOfType("Wall"))
				temp.offer(new int[] {row, col - 1});
			//check if going right is a option
			if (col < map[0].length - 1 && !map[row][col + 1].containsObjectOfType("Wall"))
				temp.offer(new int[] {row, col + 1});

			//if there are no where to go next and the there are temp options, what to go next become temp options, refresh the temp and iterate distance
			if (nextToVisit.isEmpty() && !temp.isEmpty())
			{
				nextToVisit = temp;
				temp = new LinkedList<>();
				distance++;
			}

		}
		return distance;
	}
}
