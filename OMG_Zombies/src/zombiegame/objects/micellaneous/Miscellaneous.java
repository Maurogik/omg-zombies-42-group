package zombiegame.objects.micellaneous;

import zombiegame.objects.Item;
import zombiegame.objects.Unusable;
import zombiegame.objects.weapons.Weapon;

/**
 * The enum enumerating the various Unusable objects available in the game
 * 
 * @author gaubert
 * 
 */
public abstract class Miscellaneous extends Item implements Unusable {

        private int numberOfUses;
        
        public Miscellaneous(int uses){
                this.numberOfUses=uses;
        }
        
        /**
         * return whether or not the current object is usable with the weapon
         */
        @Override
        public boolean isUsableWith(Weapon item) {
                return false;
        }

        public void loseUses(){
                this.numberOfUses--;
        }
        
        public int getUses(){
                return this.numberOfUses;
        }
}
