package classes;

import java.util.ArrayList;

public class Shop {

	public ArrayList<Item> itemList;

	public Shop() {
		itemList.add("Leather Helmet", "headSlot", 10, "+ 100 HP", 0);
		itemList.add("Leather Armor", "chestSlot", 10, "+ 100 HP", 0);
		itemList.add("Leather Bracers", "armSlot", 10, "+ 100 HP", 0);
		itemList.add("Leather Pants", "legSlot", 10, "+ 100 HP", 0);
		itemList.add("Leather Boots", "feetSlot", 10, "+ 100 HP", 0);
		itemList.add("Leather Gloves", "handSlot", 10, "+ 100 HP", 0);
	}
	
}
