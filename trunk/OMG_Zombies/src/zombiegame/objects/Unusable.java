package zombiegame.objects;

import zombiegame.objects.weapons.Weapon;


/**
 * Interface of a item witch is not usable
 * Still, you can combine this item with some weapon
 * @author gaubert
 *
 */
public interface Unusable {

        public boolean isUsableWith(Weapon item);
}
