package zombiegame.people;

import java.util.List;
import java.util.Random;

import javax.swing.JTextArea;
import zombiegame.engine.Field;
import zombiegame.engine.Location;
import zombiegame.objects.weapons.Shotgun;
import zombiegame.objects.weapons.WoodenStick;


/**
 * Class for a werewolf extends from EvilCharacter
 * 
 * @author gaubert
 * 
 */
public class Werewolf extends EvilCharacter {

        /**
         * Create a new werewolf just like a normal character
         * 
         * @param name
         * @param healthPoints
         */
        public Werewolf(String name, int healthPoints) {
                super(name, healthPoints);
        }

        /**
         * wether or not this character is a werewolf
         * 
         * @return
         */
        public boolean isWerewolf() {
                return true;
        }

        /**
         * Make the encounter with the character c Create a crew if he encounter
         * a werewolf or attack any other race
         */
        public boolean encounterCharacter(Character c, Field field) {
                if (c.isWerewolfCrew()) {
                        if (((WerewolfCrew) c).getCrewMembers() < 5) {
                                ((WerewolfCrew) c).addMember(field.getConsolePanel());
                                field.clear(this.location);
                        }
                } else if (c.isWerewolf()) {
                        WerewolfCrew meute = new WerewolfCrew("(Crew)" + this.name, 150, 2);
                        field.clear(c.location);
                        field.clear(this.location);
                        field.place(meute, c.location);
                        this.say("We are now a crew !", field.getConsolePanel());

                } else if(!c.defend(this, field)){
                        if (c.isHuman() && (c.getHealthPoints() <= 25)) {
                                this.bite((Human) c, field.getConsolePanel());
                        } else {
                                attack(c, field.getConsolePanel());
                                if (c.isHuman() && (c.getHealthPoints() <= 25)) {
                                        this.bite((Human) c, field.getConsolePanel());
                                }
                        }
                        
                }
                return false;
        }
        
        /**
         * The defense method, called in some occasion during encounters
         * 
         * @param c
         *                the character against the one you have to defend
         * @param field
         * @return true if the defend method is preventing the attack to happen
         */
        public boolean defend(Character c, Field field) {
                        if(c.isHuman() && ((Human)c).getWeapon()!=null && !((Human)c).getWeapon().getType().equals(new Shotgun().getType())){   
                                //A werewolf has a chance to bite his opponent during the defense
                                Random r=new Random();
                                if(r.nextInt(5)>3){
                                        super.defend(c, field);
                                        ((Human)c).setHasBeenBittenByLycan(true);
                                        return true;
                                }                                
                        }
                        this.say("I can't defend myself against "+c.getName(), field.getConsolePanel());
                        return false;
        }

        /**
         * attack a character
         * 
         * @param c
         */
        protected boolean attack(Character c, JTextArea cons) {
                super.attack(c, cons);
                c.reduceHealthPoints(15);
                return true;
        }

        /**
         * Method called when a vampire decides to bite a human
         * 
         * @param h
         *                Human who gets bitten by this vampire
         */
        public void bite(Human h, JTextArea cons) {
                // The human has no way to escape. He gets bitten.
                h.setHasBeenBittenByLycan(true);
                say("I have bitten you, " + h.getName() + "!", cons);
        }

        /**
         * Do the best move for the character
         * 
         * @param field
         * @return null if no best location have been found
         */
        public Location bestMove(Field field) {

                Location dest = null;
                List<Location> loc = field.adjacentLocations(this.location);
                Location vamp = null;
                Location human = null;
                Location lycan = null;

                for (Location l : loc) {
                        if (field.getObjectAt(l) != null
                                        && ((Character) field.getObjectAt(l)).isVampire()) {
                                vamp = l;
                        } else if (field.getObjectAt(l) != null
                                        && ((Character) field.getObjectAt(l)).isHuman()) {
                                human = l;
                        } else if (field.getObjectAt(l) != null
                                        && ((Character) field.getObjectAt(l)).isWerewolf()) {
                                lycan = l;
                        }
                }

                if (vamp != null) {
                        dest = vamp;
                } else if (human != null) {
                        dest = human;
                } else if (field.getFreeAdjacentLocations(location) != null
                                && field.getFreeAdjacentLocations(location).size() > 0) {
                        dest = field.getFreeAdjacentLocations(location).get(0);
                } else if (lycan != null) {
                        dest = lycan;
                }

                return dest;
        }

}
