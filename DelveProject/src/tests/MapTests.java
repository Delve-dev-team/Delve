package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import classes.ObjectPosition;
import classes.Player;
import org.junit.jupiter.api.Test;
import classes.Map;
import classes.Tile;

import javax.swing.text.Position;
import java.util.ArrayList;

class MapTests {

	@Test
	void test() {
		
	}

	@Test
	void testIfMapContainsPlayer() {
		for (int i = 1; i < 10; i++) {
			Map map = new Map(i);
			Tile[][] mapTiles = map.getTileArray();
			boolean containsPlayer = false;
			for (int row = 0; row < mapTiles.length; row++) {
				for (int col = 0; col < mapTiles[0].length; col++) {
					if (mapTiles[row][col].isPlayerHere())
						containsPlayer = true;
				}
			}
			assertEquals(true, containsPlayer);
		}
	}
	
	@Test
	void testIfMapContainsExit() {
		for (int i = 1; i < 10; i++) {
			Map map = new Map(i);
			Tile[][] mapTiles = map.getTileArray();
			boolean containsExit = false;
			for (int row = 0; row < mapTiles.length; row++) {
				for (int col = 0; col < mapTiles[0].length; col++) {
					if (mapTiles[row][col].isExitHere())
						containsExit = true;
				}
			}
			assertEquals(true, containsExit);
		}	
	}
	
	@Test
	void testPrintMap() {
		System.out.println("Map 1:");
		Map map1 = new Map(1);
		map1.printRoomArray();
		map1.printTileGrid();
		
		System.out.println("\nMap 2:");
		Map map2 = new Map(15);
		map2.printRoomArray();
		map2.printTileGrid();
	}

	@Test
	void getPlayerPosition()
	{
		Map map = new Map(1);
		ObjectPosition playerPosition = null;
		for (int row = 0; row < map.getTileArray().length; row++) {
			for (int col = 0; col < map.getTileArray()[0].length; col++) {
				if ((map.getTileArray()[row][col].isPlayerHere()))
					playerPosition = ObjectPosition.of(row, col, map);
			}
		}
		assertEquals(playerPosition, map.getPlayerPosition());
	}

	@Test
	void getEnemiesPositions()
	{
		Map map = new Map(1);
		ArrayList<ObjectPosition> enemiesPosition = new ArrayList<>();
		for (int row = 0; row < map.getTileArray().length; row++) {
			for (int col = 0; col < map.getTileArray()[0].length; col++) {
				if ((map.getTileArray()[row][col].isEnemyHere()))
					enemiesPosition.add(ObjectPosition.of(row, col, map));
			}
		}

		assertEquals(map.getEnemiesPositions(),enemiesPosition);;
	}

	@Test
	void shortestPath()
	{
		Map map = new Map(1);
		for (ObjectPosition position: map.getEnemiesPositions())
		{
			assertEquals(map.shortestPath(map.getTileArray(),position,map.getPlayerPosition()),map.shortestPath(map.getTileArray(),position,map.getPlayerPosition()));
		}
	}
	
}
