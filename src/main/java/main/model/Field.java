package main.model;



import java.util.ArrayList;

/**
 * Az aszteroida és a portal közös őse.
 */
public abstract class Field {

    protected boolean closeToSun;

    public boolean isCloseToSun() {
        return closeToSun;
    }

    protected String ID;

    /**
     * A Field szomszédait tároló lista.
     */
    protected ArrayList<Field> neighbours;

    public Field(){
        neighbours = new ArrayList<>();
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) { this.ID = ID; }

    public ArrayList<Field> getNeighbours() {
        return neighbours;
    }

    public void setNeighbours(ArrayList<Field> neighbours) {
        this.neighbours = neighbours;
    }

    /**
     * A neigbours kollekcióból visszaad egy elemet
     * @param n az elem azonosítója
     * @return a kiválasztott elem
     */
    public Field getNeighbourById(int n){
        return neighbours.get(n);
    }

    public int getDirection(String id) {
        int n = 0;
        for(Field f: neighbours) {
            if(id.equals(f.getID())) {
                return n;
            }
            n++;
        }
        return -1;
    }

    /**
     *  Lekérdezi, hogy a párjához milyen aszteroida tartozik, majd átmozgatja a karaktert.
     * @param character a karakter amit mozgatni fog
     * @return sikerült-e a mozgatás
     */
    public abstract boolean accept(Character character);


    /**
     * Hozzáad egy Field-et a neighbours listához.
     * @param field az új Field, amit hozzá kell adni
     */
    public void addNeighbour(Field field){
        neighbours.add(field);
    }

    /**
     * Kivesz egy Field-et a neighbours listából.
     * @param field a Field, amit ki kell venni
     */
    public void removeNeighbour(Field field){
        neighbours.remove(field);
    }
    public abstract void SunStorm();

    public void printNeighbours(){
        System.out.println(ID);
        for(Field f : neighbours){
            System.out.println("Neighbour:" + f.getID());
        }
    }

    public void setCloseToSun(boolean b){
        closeToSun = b;
    }
}
