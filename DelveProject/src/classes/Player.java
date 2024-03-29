package classes;

public class Player {
    
    private int HP;

    private int MP;

    private int attackDamage;

    private int movementSpeed;

    private int attackRange;

    private int gold;

    private int ap;

    private Item headSlot;

    private Item chestSlot;

    private Item armSlot;

    private Item legSlot;

    private Item feetSlot;

    private Item handSlot;

    private int rowPosition;

    private int colPosition;

    public Player(int row, int col) {
        HP = 1000;
        MP = 1000;
        attackDamage = 100;
        movementSpeed = 10;
        attackRange = 10;
        gold = 0;
        ap = movementSpeed;
        headSlot = null;
        chestSlot = null;
        armSlot = null;
        legSlot = null;
        feetSlot = null;
        handSlot = null;
    }

 public String equipItem(Player player, Item item) {

       if (item.getSlot().equals("headSlot")) {
           if (player.headSlot == null) {
               player.headSlot = item;
               return item.getName() + " has been equipped.";
           }
       } else if (item.getSlot().equals("chestSlot")) {
           if (player.chestSlot == null) {
               player.chestSlot = item;
               return item.getName() + " has been equipped.";
           }
       } else if (item.getSlot().equals("armSlot")) {
           if (player.armSlot == null) {
               player.armSlot = item;
               return item.getName() + " has been equipped.";
           }
       } else if (item.getSlot().equals("legSlot")) {
           if (player.legSlot == null) {
               player.legSlot = item;
               return item.getName() + " has been equipped.";
           }
       } else if (item.getSlot().equals("feetSlot")) {
           if (player.feetSlot == null) {
               player.feetSlot = item;
               return item.getName() + " has been equipped.";
           }
       } else if (item.getSlot().equals("handSlot")) {
           if (player.handSlot == null) {
               player.handSlot = item;
               return item.getName() + " has been equipped.";
           }
       } else {
           return "Sorry, " + item.getSlot() + " is full";
       }
       return "error";
   }

   public void abilityOne(Enemy enemy){ //Fix this ability
        if (enemy  != null) {
            if (this.getMP() >= 100) {
                System.out.println("Enemy:I'm on fire!");
                this.setMP(this.getMP() - 100);
                enemy.die(this);
            } else
                System.out.println("Out of Mana!");
        }

        System.out.println("No enemey selected");
   }

    public void abilityTwo(Enemy enemy) { //Fix this ability
        if (enemy != null) {
            if (this.getMP() >= 100) {
                System.out.println("Enemy: I'm frozen!");
                this.setMP(this.getMP() - 100);
                enemy.setDisarmed(true);
            } else
                System.out.println("Out of Mana!");
        }
    }

    public void abilityThree(){
        if (this.getMP() >= 100) {
            this.setHP(this.getHP() + 100);
            this.setMP(this.getMP() - 100);
            System.out.println("Healed!");
        }
        else
           System.out.println("Out of Mana!");
    }

    public void abilityFour(){
        if(this.getMP() >= 100) {
            this.setMP(this.getMP() + 100);
            this.setHP(this.getHP() - 100);
            System.out.println("Mana Points restored");
        }
        else
            System.out.println("Out of Mana!");

    }

//   public String unequipItem (String slot) {
//
//       if (slot == "headSlot") {
//           if (this.headSlot != null) {
//        	   String message = this.headSlot.getName() + " has been unequipped.";
//               this.headSlot = null;
//               return this.headSlot.getName() + " has been unequipped.";
//           }
//       } else if (slot == "chestSlot") {
//           if (this.chestSlot != null) {
//               this.chestSlot = null;
//               return this.chestSlot.getName() + " has been unequipped.";
//           }
//       } else if (slot.equals("armSlot")) {
//           if (this.armSlot != null) {
//               this.armSlot = null;
//               return this.armSlot.getName() + " has been unequipped.";
//           }
//       } else if (slot.equals("legSlot")) {
//           if (this.legSlot != null) {
//               this.legSlot = null;
//               return this.legSlot.getName() + " has been unequipped.";
//           }
//       } else if (slot.equals("feetSlot")) {
//           if (this.feetSlot != null) {
//               this.feetSlot = null;
//               return this.feetSlot.getName() + " has been unequipped.";
//           }
//       } else if (slot.equals("handSlot")) {
//           if (this.handSlot != null) {
//               this.handSlot = null;
//               return this.handSlot.getName() + " has been unequipped.";
//           }
//       } else {
//           return "There was no item in that slot";
//       }
//       return "error";
//   }

    public void consumeAP(int ap)
    {
        if (this.ap >= ap)
            this.ap -= ap;
    }

    public void refreshAp() {
        this.ap = this.movementSpeed;
    }

   public void attack(Enemy enemy) {
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

   public String getHeadSlot() {

        if (headSlot == null)
            return "None";
       return headSlot.getName();
   }

   public String getChestlot() {
        if(chestSlot == null)
            return "None";
        return chestSlot.getName();
   }

   public String getArmSlot() {
        if (armSlot == null)
            return "None";
        return armSlot.getName();
   }

   public String getLegSlot() {
        if(legSlot == null)
           return "None";
        return legSlot.getName();
   }

   public String getFeetSlot() {
       if(chestSlot == null)
           return "None";
       return feetSlot.getName();
   }

   public String getHandSlot() {
        if(chestSlot == null)
           return "None";
        return handSlot.getName();
   }

    public int getAp() {
        return ap;
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
}
