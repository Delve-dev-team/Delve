package classes;

public class Player {
    
    private int HP;

    private int MP;

    private int attackDamage;

    private int movementSpeed;

    private int attackRange;

    private int gold;

    private boolean headSlot;

    private boolean chestSlot;

    private boolean armSlot;

    private boolean legSlot;

    private boolean feetSlot;

    private boolean handSlot;

    public Player() {
        HP = 1000;
        MP = 1000;
        attackDamage = 100;
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
        enemy.setHP(enemy.getHP() - (this.attackDamage));
    }


    //public setters
    public void setHP(int hp) {
        this.HP = hp;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public void setAttackDamage(int attackDamage) {
        this.attackDamage = attackDamage;
    }

    public void setMovementSpeed(int movementSpeed) {
        this.movementSpeed = movementSpeed;
    }

    public void setAttackRange(int attackRange) {
        this.attackRange = attackRange;
    }

    public void setMP(int MP) {
        this.MP = MP;
    }


    //public getters
    public int getHP() {
        return this.HP;
    }

    public int getGold() {
        return this.gold;
    }

    public int getMP() {
        return MP;
    }

    public int getAttackDamage() {
        return attackDamage;
    }

    public int getAttackRange() {
        return attackRange;
    }

    public int getMovementSpeed() {
        return movementSpeed;
    }


}
