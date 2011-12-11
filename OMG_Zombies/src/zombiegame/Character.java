package zombiegame;

/**
 * Parent Character class
 * 
 * @author pylaffon
 * 
 */
public abstract class Character {

        /** name of the character */
        protected String name;

        /**
         * represents the health (once down to 0, this character will be
         * destroyed)
         */
        protected int healthPoints;

        /**
         * Constructor of Character class.
         * 
         * @param name
         *                name of the character
         * @param healthPoints
         *                initial HP
         */
        public Character(String name, int healthPoints) {
                this.name = name;
                this.healthPoints = healthPoints;
        }

        // Accessors
        public String getName() {
                return name;
        }

        public int getHealthPoints() {
                return healthPoints;
        }

        /**
         * wether or not this character is a human
         * 
         * @return
         */
        public boolean isHuman() {
                return false;
        }

        /**
         * wether or not this character is a zombie
         * 
         * @return
         */
        public boolean isZombie() {
                return false;
        }

        /**
         * wether or not this character is a vampire
         * 
         * @return
         */
        public boolean isVampire() {
                return false;
        }
        
        /**
         * wether or not this character is a werewolf
         * 
         * @return
         */
        public boolean isWerewolf() {
                return false;
        }
        
        /**
         * wether or not the character is an evil character
         * @return
         */
        public boolean isEvilCharacter(){
                return false;
        }

        /**
         * Decrease the number of HP by a certain amount. HP cannot go below 0.
         * 
         * @param reduction
         *                number of HP to reduce
         */
        public void reduceHealthPoints(int reduction) {
                healthPoints = healthPoints - reduction;
                if (healthPoints < 0) {
                        healthPoints = 0;
                }
        }
        
        
        /**
         * Increase the number of HP by a certain amount.
         * 
         * @param increase
         *                number of HP to add
         */
        public void increaseHealthPoints(int increase) {
                if(increase >=0) {
                        this.healthPoints+=increase;   
                }
                else
                {
                        System.out.println("Wrong hp values in increaseHp method");
                }
        }
        

        /**
         * Output a character's saying to the screen
         * 
         * @param str
         *                what the character says
         */
        public void say(String str) {
                System.out.println(name + " says: " + str);
        }

        /**
         * Method triggered when the character described by the current object
         * meets another character, and does something to him (for example,
         * attack).
         * 
         * @param c
         *                the other character that this character meets
         */
        public void encounterCharacter(Character c) {
                // Default action: do nothing
                System.out.println(name + " meets " + c.name + " and does not attack!");
        }

        public void endOfTurn() {
                // do nothing
                System.out.println("this is not suposse to be print, check endOfTurn if Character");
        }

        /**
         * attack the character c
         * 
         * @param c
         */
        protected void attack(Character c) {
                this.say(c.getName() + ", I'm gonna kill you!");

        }
}
