package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import classes.Enemy;
import classes.Item;
import classes.Player;

class Playertests {

    @Test
	void playerAttackTest() {
		Player a = new Player();
		Enemy b = new Enemy(1);
		a.attack(b);
		assertEquals(40, b.getHP());
	}

    @Test
	void playerGoldTest() {
		Player a = new Player();
		Enemy b = new Enemy(1);
		b.die(a);
		assertEquals(5, a.getGold());
	}

    @Test
	void playerItemTest() {
        Player a = new Player();
        a.equipItem(a, new Item("hat", "headSlot", 1, "ordinary hat", 1));
        assertEquals("hat", a.getHeadSlot());
        a.unequipItem("headSlot");
		assertEquals(null, a.getHeadSlot());
	}
	
}
