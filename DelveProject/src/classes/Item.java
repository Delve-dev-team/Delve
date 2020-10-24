package classes;

public class Item {
    
    private String name;

    private String slot;

    private String effect;

    private int cooldown;

    public Item(String name, String slot, int price, String effect, int cooldown) {
        this.name = name;
        this.slot = slot;
        this.effect = effect;
        this.cooldown = cooldown;
    }

    public String getName() {
        return this.name;
    }

    public String getSlot() {
        return this.slot;
    }

    public String getEffect() {
        return this.effect;
    }

    public int getCooldown() {
        return this.cooldown;
    }

}
