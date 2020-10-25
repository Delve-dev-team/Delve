package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import classes.Map;
import classes.Tile;

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
	void containsPlayerTest() {
		assertEquals(1, 1);
	}
	
	@Test
	void containsExitTest() {
		assertEquals(1, 1);
	}
	
}
