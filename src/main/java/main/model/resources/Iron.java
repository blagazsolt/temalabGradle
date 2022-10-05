package main.model.resources;

/**
 * Egy nyersanyagot valósít meg az aszteroida magjában.
 */
public class Iron extends Resource {
    private static int autoIncrement = 1;

    private String ID;

    public Iron(){
        ID = "IR" + (autoIncrement++);
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

}
