package main.model.resources;


import main.model.Asteroid;

/**
 * Egy radioaktív nyersanyagot valósít meg az aszteroida magjában.
 */
public class Uranium extends Resource {

    private static int autoIncrement = 1;

    private String ID;

    int counter;

    public Uranium(){
        ID = "UR" + (autoIncrement++);
        counter = 0;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    /**
     * Az ősosztály beli metódust írja felül. Felrobbantja az aszteroidát.
     * @param asteroid az aszteroida, ami éppen napközelben van
     * @return Felrobban-e az aszteroida a hatására
     */
    public Boolean closeToSun(Asteroid asteroid){
        if (counter < 2 )
            counter++;
        else
            return true;
        return false;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }
}
