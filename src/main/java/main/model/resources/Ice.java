package main.model.resources;



import main.model.Asteroid;
import main.model.Inventory;

import java.util.ArrayList;

/**
 * Egy nyersanyagot valósít meg az aszteroida magjában.
 */
public class Ice extends Resource {

    private static int autoIncrement = 1;

    private String ID;

    public Ice(){
        ID = "IC" + (autoIncrement++);
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
        iceInventory = new Inventory();
        ArrayList<Resource> res = new ArrayList<>();
        res.add(new Ice());
        iceInventory.setResources(res);
    }

    /**
     * Statikus inventory. Egy darab ice resource-ot tárol a resources tömbjében.
     */
    private static Inventory iceInventory ;

    public static Inventory getIceInventory() {
        return iceInventory;
    }

    public static void setIceInventory(Inventory iceInventory) {
        Ice.iceInventory = iceInventory;
    }

    /**
     *  Az ősosztály beli metódust írja felül. Elpárologtatja a vizet az aszteroida magjából.
     * @param asteroid az aszteroida, ami éppen napközelben van
     */
    public Boolean closeToSun(Asteroid asteroid){
        asteroid.setInventory(new Inventory());
        return false;
    }
}
