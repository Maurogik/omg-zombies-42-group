package zombiegame.people;

import zombiegame.engine.Field;
import zombiegame.engine.Simulator;

/**
 * Zombie class, derives from EvilCharacter.
 * 
 * @author pylaffon
 * 
 */
public class Zombie extends EvilCharacter {
        /**
         * Constructor of Zombie class.
         * 
         * @param name
         *                name of the character
         * @param healthPoints
         *                initial HP
         * @param field 
         */
        public Zombie(String name, int healthPoints, Field field) {
                super(name, healthPoints, field);
        }

        /**
         * Output a character's saying to the screen
         * 
         * @param str
         *                what the character says
         */
        public void say(String str) {
                System.out.println(name + " says: BRAIIIIIINS!");
        }

        /**
         * the encounter between this character and c attack c
         */
        public void encounterCharacter(Character c) {
                this.say("Go away " + c.getName()
                                + " before i start to...humm...beg you to leave me alive ???");
        }

        /**
         * wether or not this character is a zombie
         * 
         * @return
         */
        public boolean isZombie() {
                return true;
        }

        /**
         * wether or not this character is a MadZombie
         * 
         * @return
         */
        public boolean isMadZombie() {
                return false;
        }

        /**
         * attack the character c
         * 
         * @param c
         */
        protected void attack(Character c) {
                if (c.isHuman() || c.isVampire() && Simulator.GenerateRandomBoolean()) {
                        super.attack(c);
                        c.reduceHealthPoints(5);
                }
        }

        /**
         * Method triggered on each character at the end of each turn.
         */
        public void endOfTurn() {
                // Do nothing. Zombies are useless anyway...
        }
}
