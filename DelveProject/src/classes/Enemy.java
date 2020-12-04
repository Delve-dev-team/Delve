package classes;

public class Enemy {

    private int HP;
    private int MP;
    private int attackDamage;
    //private int movementSpeed;
    private int attackRange;
    private int level;
    private String status;
    private int statusTimer;
    //private int ap;

    private int rowPosition;
    private int colPosition;

    private boolean isDisarmed = false;
    public Enemy(int level, int row, int col) {
        this.level = level;
        HP = 50 * level;
        MP = 50 * level;
        attackDamage = 50 * level;
        //movementSpeed = 10;
        attackRange = 5;
        //ap = movementSpeed;
        status = "None";
        statusTimer = 0;

    }

    public void attack(Map map) {
        {
            if (!isDisarmed)
                map.getPlayer().setHP(map.getPlayer().getHP()-(this.getAttackDamage()));
        }
    }

    public void die(Player player) {
        this.HP = 0;
        player.setGold(player.getGold() + 5 * level);
    }

    /*public void consumeAP(int ap)
    {
        if (this.ap >= ap)
            this.ap -= ap;
    }*/

    /*private void refreshAp() {
        this.ap = this.movementSpeed;
    }*/

    //public getters
    public int getHP() {
        return this.HP;
    }

    public String getStatus(){
        return this.status;
    }

    public int getStatusTimer(){
        return this.statusTimer;
    }

    /*public int getMovementSpeed() {
        return movementSpeed;
    }*/

    public int getAttackRange() {
        return attackRange;
    }

    public int getAttackDamage() {
        return attackDamage;
    }

    public int getMP() {
        return MP;
    }

    /*public int getAp() {
        return ap;
    }*/

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

    public void setStatus(String status){
        this.status = status;
    }

    public void setStatusTimer(int timer){
        this.statusTimer = statusTimer + timer;
    }

    /*public void setMovementSpeed(int movementSpeed) {
        this.movementSpeed = movementSpeed;
    }*/

    public void setAttackDamage(int attackDamage) {
        this.attackDamage = attackDamage;
    }

    public int getColPosition() {
        return colPosition;
    }

    public int getRowPosition() {
        return rowPosition;
    }

    public void setRowPosition(int rowPosition) {
        this.rowPosition = rowPosition;
    }

    public void setColPosition(int colPosition) {
        this.colPosition = colPosition;
    }

    public boolean isDisarmed(){
        return this.isDisarmed;
    }

    public void setDisarmed(boolean disarmed) {
        isDisarmed = disarmed;
    }
}
