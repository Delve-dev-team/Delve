package tests;

import classes.GameController;
import classes.ObjectPosition;

public class Tester {

	public static void main(String[] args) {
		/*ExitDir[] exits = {ExitDir.LEFT, ExitDir.RIGHT};
		Room room = new Room(exits, 2, true);
		room.printRoom();
		

		Map map = new Map(1);
		map.printTileGrid();
		map.printRoomArray();*/

		GameController gameController = new GameController();

		gameController.enterNextLevel();
	}
	
}
