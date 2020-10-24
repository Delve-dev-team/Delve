package classes;

import java.util.ArrayList;

public class Tile {

	public ArrayList<Object> elements;
	
	public Tile() {
		this.elements = new ArrayList<Object>();
	}
	
	public Tile(Object input) {
		this.elements = new ArrayList<Object>();
		
		elements.add(input);
	}
	
	public Tile(Object[] input) {
		this.elements = new ArrayList<Object>();
		
		for (int i = 0; i < input.length; i++) {
			elements.add(input[i]);
		}
	}
	
	
	
	public void add(Object obj) {
		elements.add(obj);
	}
	
	public void remove(Object obj) {
		elements.remove(obj);
	}
	
	public void removeAll() {
		elements.removeAll(elements);
	}
	
	public boolean containsObjectOfType(String type) {
		for (int i = 0; i < elements.size(); i++) {
			if(elements.get(i).getClass().getName().contains(type)) {
				return true; 
			}
		}
		return false;
	}
	
	//This assumes there's only 1 object of the desired type on the tile.
	public Object getObjectOfType(String type) {
		for (int i = 0; i < elements.size(); i++) {
			if(elements.get(i).getClass().getName().contains(type)) {
				return elements.get(i);
			}
		}
		return null;
	}

	public Player getPlayer() {
		for (int i = 0; i < elements.size(); i++) {
			if(elements.get(i).getClass().getName().contains("Player")) {
				return (Player) elements.get(i);
			}
		}
		return null;
	}
	
	
	
}
