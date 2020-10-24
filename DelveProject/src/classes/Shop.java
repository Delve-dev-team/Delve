package classes;

import java.util.ArrayList;

public class Shop {

	public ArrayList<Item> itemList;

	public Shop() {
		itemList.add(new Item("Leather Helmet", "headSlot", 10, "+ 100 HP", 0));
		itemList.add(new Item("Leather Armor", "chestSlot", 10, "+ 100 HP", 0));
		itemList.add(new Item("Leather Bracers", "armSlot", 10, "+ 100 HP", 0));
		itemList.add(new Item("Leather Pants", "legSlot", 10, "+ 100 HP", 0));
		itemList.add(new Item("Leather Boots", "feetSlot", 10, "+ 100 HP", 0));
		itemList.add(new Item("Leather Gloves", "handSlot", 10, "+ 100 HP", 0));
	}
	
}
