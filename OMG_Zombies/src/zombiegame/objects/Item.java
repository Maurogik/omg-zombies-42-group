package zombiegame.objects;

public abstract class Item {
    
    public Item()
    {
        
    }

        public boolean isWeapon() {
                return false;
        }

        public boolean isEdible() {
                return false;
        }

        public boolean isMiscellaneous() {
                return false;
        }

}
