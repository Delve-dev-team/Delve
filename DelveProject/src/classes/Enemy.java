package classes;

public class Enemy {

	int HP;

    int MP;

    int attackDamage;

    int attackSpeed;

    int movementSpeed;

    int attackRange;

    public Enemy(int level) {
        HP = 50 * level;
        MP = 50 * level;
        attackDamage = 5 * level;
        attackSpeed = 10;
        movementSpeed = 10;
        attackRange = 10;
    }

    public void attack(Player player) {
        player.setHP(player.getHP() - (this.attackDamage * this.attackSpeed));
    }

    public void die(Player player) {
        this.HP = 0;
        player.setGold(player.getGold() + 5);
    }

    public void takeTurn(Map map) {

    }

    public void move(Map map) {
        
    }

    public int getHP() {
        return this.HP;
    }

    public void setHP(int hp) {
        this.HP = hp;
    }
	
}
