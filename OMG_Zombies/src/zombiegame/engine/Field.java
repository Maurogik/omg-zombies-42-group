package zombiegame.engine;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import javax.swing.JTextArea;

import zombiegame.people.*;
import zombiegame.people.Character;

/**
 * Represent a rectangular grid of field positions. Each position is able to
 * store a single animal.
 * 
 * @author David J. Barnes and Michael Kolling
 * @version 2008.03.30
 */
public class Field {
        // A random number generator for providing random locations.
        private static final Random rand = new Random();

        // The depth and width of the field.
        private int depth, width;
        // Storage for the animals.
        private Object[][] field;
        
        private JTextArea cons;

        /**
         * Represent a field of the given dimensions.
         * 
         * @param depth
         *                The depth of the field.
         * @param width
         *                The width of the field.
         */
        public Field(int depth, int width, JTextArea conso) {
                this.depth = depth;
                this.width = width;
                field = new Object[depth][width];
                cons=conso;
        }

        /**
         * Empty the field.
         */
        public void clear() {
                for (int row = 0; row < depth; row++) {
                        for (int col = 0; col < width; col++) {
                                field[row][col] = null;
                        }
                }
        }

        /**
         * Clear the given location.
         * 
         * @param location
         *                The location to clear.
         */
        public void clear(Location location) {
                field[location.getRow()][location.getCol()] = null;
        }

        /**
         * Place an object at the given location. If there is already an
         * object at the location it will be lost.
         * 
         * @param object
         *                The animal to be placed.
         * @param row
         *                Row coordinate of the location.
         * @param col
         *                Column coordinate of the location.
         */
        public void placeItem(Object object, int row, int col) {
                field[row][col] = object;
        }

        /**
         * Place an object at a random location on the field.
         */
        public void placeRandomly(Object character) {
                place(character, getFreeRandomLocation());
                
        }

        /**
         * Place an object at the given location. If there is already a character
         * at the location it will be lost.
         * 
         * @param character
         *                The animal to be placed.
         * @param location
         *                Where to place the animal.
         */
        public void place(Object character, Location location) {
                field[location.getRow()][location.getCol()] = character;
                ((Character)character).setLocation(location,this.getConsolePanel());
        }

        /**
         * Return the object at the given location, if any.
         * 
         * @param location
         *                Where in the field.
         * @return The object at the given location, or null if there is none.
         */
        public Object getObjectAt(Location location) {
                return getObjectAt(location.getRow(), location.getCol());
        }

        /**
         * Return the object at the given location, if any.
         * 
         * @param row
         *                The desired row.
         * @param col
         *                The desired column.
         * @return The object at the given location, or null if there is none.
         */
        public Object getObjectAt(int row, int col) {
                return field[row][col];
        }

        /**
         * Generate a random location that is adjacent to the given location, or
         * is the same location. The returned location will be within the valid
         * bounds of the field.
         * 
         * @param location
         *                The location from which to generate an adjacency.
         * @return A valid location within the grid area.
         */
        public Location randomAdjacentLocation(Location location) {
                List<Location> adjacent = adjacentLocations(location);
                return adjacent.get(0);
        }

        /**
         * Get a shuffled list of the free adjacent locations.
         * 
         * @param location
         *                Get locations adjacent to this.
         * @return A list of free adjacent locations.
         */
        public List<Location> getFreeAdjacentLocations(Location location) {
                List<Location> free = new LinkedList<Location>();
                List<Location> adjacent = adjacentLocations(location);
                for (Location next : adjacent) {
                        if (getObjectAt(next) == null) {
                                free.add(next);
                        }
                }
                return free;
        }

        /**
         * Try to find a free location that is adjacent to the given location.
         * If there is none, return null. The returned location will be within
         * the valid bounds of the field.
         * 
         * @param location
         *                The location from which to generate an adjacency.
         * @return A valid location within the grid area.
         */
        public Location freeAdjacentLocation(Location location) {
                // The available free ones.
                List<Location> free = getFreeAdjacentLocations(location);
                if (free.size() > 0) {
                        return free.get(0);
                } else {
                        return null;
                }
        }

        /**
         * Return a shuffled list of locations adjacent to the given one. The
         * list will not include the location itself. All locations will lie
         * within the grid.
         * 
         * @param location
         *                The location from which to generate adjacencies.
         * @return A list of locations adjacent to that given.
         */
        public List<Location> adjacentLocations(Location location) {
                assert location != null : "Null location passed to adjacentLocations";
                // The list of locations to be returned.
                List<Location> locations = new LinkedList<Location>();
                if (location != null) {
                        int row = location.getRow();
                        int col = location.getCol();
                        for (int roffset = -1; roffset <= 1; roffset++) {
                                int nextRow = row + roffset;
                                if (nextRow >= 0 && nextRow < depth) {
                                        for (int coffset = -1; coffset <= 1; coffset++) {
                                                int nextCol = col + coffset;
                                                // Exclude invalid locations and
                                                // the original location.
                                                if (nextCol >= 0 && nextCol < width
                                                                && (roffset != 0 || coffset != 0)) {
                                                        locations.add(new Location(nextRow,nextCol));
                                                }
                                        }
                                }
                        }

                        // Shuffle the list. Several other methods rely on the
                        // list
                        // being in a random order.
                        Collections.shuffle(locations, rand);
                }
                return locations;
        }

        /**
         * Return the depth of the field.
         * 
         * @return The depth of the field.
         */
        public int getDepth() {
                return depth;
        }

        /**
         * Return the width of the field.
         * 
         * @return The width of the field.
         */
        public int getWidth() {
                return width;
        }

        /**
         * Get a free random location in the map
         * 
         * @return a location object with nobody at that spot
         */
        public Location getFreeRandomLocation() {
                Random r = new Random();
                int row = r.nextInt(this.depth);
                int column = r.nextInt(this.width);
                while (getObjectAt(row, column) != null) {
                        row = r.nextInt(this.depth);
                        column = r.nextInt(this.width);
                }
                return new Location(row, column);
        }
        
        
        public int getNbWerewolf(){
                int x=0;
                Object c;
                Location l;
                for(int i=0;i<getDepth();i++){
                        for(int j=0;j<getWidth();j++){
                                l=new Location(i,j);
                                c=getObjectAt(l);
                                if(c!=null&& ((Character)c).isWerewolf()){
                                        x++;
                                }
                        }
                }
                
                return x;
        }
        
        public int getNbHuman(){
                int x=0;
                Object c;
                Location l;
                for(int i=0;i<getDepth();i++){
                        for(int j=0;j<getWidth();j++){
                                l=new Location(i,j);
                                c=getObjectAt(l);
                                if(c!=null && ((Character)c).isHuman()){
                                        x++;
                                }
                        }
                }
                
                return x;
        }
        
        public int getNbZombie(){
                int x=0;
                Object c;
                Location l;
                for(int i=0;i<getDepth();i++){
                        for(int j=0;j<getWidth();j++){
                                l=new Location(i,j);
                                c=getObjectAt(l);
                                if(c!=null && ((Character)c).isZombie()){
                                        x++;
                                }
                        }
                }
                
                return x;
        }
        
        public int getNbVampire(){
                int x=0;
                Object c;
                Location l;
                for(int i=0;i<getDepth();i++){
                        for(int j=0;j<getWidth();j++){
                                l=new Location(i,j);
                                c=getObjectAt(l);
                                if(c!=null && ((Character)c).isVampire()){
                                        x++;
                                }
                        }
                }
                
                return x;
        }
        
        /**
         * 
         * @return the panel where the console log are printed
         */
        public JTextArea getConsolePanel(){
                return cons;
        }
}
