package classes;

public class Tester {

	public static void main(String[] args) {
		ExitDir[] exits = {ExitDir.LEFT, ExitDir.RIGHT};
		Room room = new Room(exits, 2, true);
		room.printRoom();
		

		Map map = new Map(25);
		map.printTileGrid();
		
	}
	
}
