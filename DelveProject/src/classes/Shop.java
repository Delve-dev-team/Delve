package classes;

import java.util.ArrayList;
import java.util.List;

public class Shop {

	public List<Item> itemList = new ArrayList<>();

	public Shop() {
		itemList.add(new Item("Leather Helmet", "headSlot", 10, "+ 100 HP", 0));
		itemList.add(new Item("Leather Armor", "chestSlot", 10, "+ 100 HP", 0));
		itemList.add(new Item("Leather Bracers", "armSlot", 10, "+ 100 HP", 0));
		itemList.add(new Item("Leather Pants", "legSlot", 10, "+ 100 HP", 0));
		itemList.add(new Item("Leather Boots", "feetSlot", 10, "+ 100 HP", 0));
		itemList.add(new Item("Leather Gloves", "handSlot", 10, "+ 100 HP", 0));
	}
	
}
