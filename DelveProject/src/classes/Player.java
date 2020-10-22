package classes;

public class Player {
    
    int HP;

    int MP;

    int attackDamage;

    int attackSpeed;

    int movementSpeed;

    int attackRange;

    int gold;

    boolean headSlot;

    boolean chestSlot;

    boolean armSlot;

    boolean legSlot;

    boolean feetSlot;

    boolean handSlot;

    public Player() {
        HP = 1000;
        MP = 1000;
        attackDamage = 100;
        attackSpeed = 10;
        movementSpeed = 10;
        attackRange = 10;
        gold = 0;
        headSlot = false;
        chestSlot = false;
        armSlot = false;
        legSlot = false;
        feetSlot = false;
        handSlot = false;
    }

    // public String EquipItem (Item item) {
    //     if (this.(getSlot(item)) == false) {
    //         this.(getSlot(item)) = true;
    //         return item + " has been equipped.";
    //     } else {
    //         return "Sorry, slot " + item + " is full";
    //     }
    // }

    // public String UnequipItem (String slot) {
    //     if (this.slot == true) {
    //         this.slot = false;
    //         return item + " has been unequipped.";
    //     } else {
    //         return "There was no item in that slot";
    //     }
    // }

    public void Attack(Enemy enemy) {
        enemy.setHP(enemy.getHP() - (this.attackDamage * this.attackSpeed));
    }

    public int getHP() {
        return this.HP;
    }

    public void setHP(int hp) {
        this.HP = hp;
    }

    public int getGold() {
        return this.gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

}
