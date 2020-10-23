package classes;

public class Enemy {

	private int HP;
    private int MP;
    private int attackDamage;
    private int movementSpeed;
    private int attackRange;

    public Enemy(int level) {
        HP = 50 * level;
        MP = 50 * level;
        attackDamage = 5 * level;
        movementSpeed = 10;
        attackRange = 10;
    }

    public void attack(Player player) {
        player.setHP(player.getHP() - (this.attackDamage));
    }

    public void die(Player player) {
        this.HP = 0;
        player.setGold(player.getGold() + 5);
    }

    public void takeTurn(Map map) {

    }

    public void move(Map map) {
        
    }

    //public getters
    public int getHP() {
        return this.HP;
    }

    public int getMovementSpeed() {
        return movementSpeed;
    }

    public int getAttackRange() {
        return attackRange;
    }

    public int getAttackDamage() {
        return attackDamage;
    }

    public int getMP() {
        return MP;
    }

    //public setters
    public void setHP(int hp) {
        this.HP = hp;
    }

    public void setMP(int MP) {
        this.MP = MP;
    }

    public void setAttackRange(int attackRange) {
        this.attackRange = attackRange;
    }

    public void setMovementSpeed(int movementSpeed) {
        this.movementSpeed = movementSpeed;
    }

    public void setAttackDamage(int attackDamage) {
        this.attackDamage = attackDamage;
    }
}
