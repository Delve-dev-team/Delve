package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import classes.Item;
import classes.Player;

class Playertest {

    @Test
	void test() {
		
	}

    @Test
	void playerEquipItemTest() {
        Player a = new Player();
        a.equipItem(a, new Item("hat", "headSlot", 1, "ordinary hat", 1));
		assertEquals("hat", a.getHeadSlot());
	}
	
}
