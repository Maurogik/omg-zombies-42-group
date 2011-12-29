package zombiegame.people;

import zombiegame.engine.Field;
import zombiegame.engine.Location;

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

        protected Location location;

        protected boolean asPlayed;

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

        public boolean canPlay() {
                return !asPlayed;
        }

        public void setPlay() {
                asPlayed = false;
        }

        public void justPlayed() {
                asPlayed = true;
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
         * 
         * @return
         */
        public boolean isEvilCharacter() {
                return false;
        }

        /**
         * wether or not the character is a WerewolfCrew
         * 
         * @return
         */
        public boolean isWerewolfCrew() {
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
                if (increase >= 0) {
                        this.healthPoints += increase;
                } else {
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
        public void encounterCharacter(Character c, Field field) {
                // Default action: do nothing
                System.out.println(name + " meets " + c.name + " and does not attack!");
        }

        /**
         * Method triggered when the new turn start.
         * Clear the encounter character if dead, turn it into zombie if human
         * After the action, if human, pick up the object on the ground(if there is any)
         * 
         */
        public void action(Field field, Field fieldObj) {
                Location loc = field.randomAdjacentLocation(location);
                this.say("I'm now acting");

                if (field.getObjectAt(loc) == null) {
                        Location a = this.location;
                        field.place(this, loc);
                        field.clear(a);
                } else {
                        try {
                                Character c=(Character) field.getObjectAt(loc);
                                encounterCharacter(c,field);
                                if (c.getHealthPoints() == 0) {
                                        field.clear(loc);
                                        if(c.isHuman()){
                                                field.place(((Human)c).turnIntoZombie(), loc);
                                                System.out.println(c.getName()+" has been turend into a zombie");
                                        }

                                }
                        } catch (Exception e) {
                                e.printStackTrace();
                        }
                }
                if(this.isHuman()){
                        ((Human)this).pickUpObject(fieldObj,loc);
                }
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

        /**
         * Return the rabbit's location.
         * 
         * @return The rabbit's location.
         */
        public Location getLocation() {
                return location;
        }

        /**
         * Place the rabbit at the new location in the given field.
         * 
         * @param newLocation
         *                The rabbit's new location.
         */
        public void setLocation(Location newLocation) {
                if (location != null) {
                        System.out.println(this.name + " go from " + location.getRow() + " "
                                        + location.getCol());
                } else {
                        System.out.print("start at location ");
                }

                this.location = newLocation;
                System.out.println(" to : " + location.getRow() + " " + location.getCol());
        }
}
