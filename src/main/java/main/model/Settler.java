package main.model;



import main.model.resources.*;
import main.model.resources.*;

import main.model.resources.*;
import java.util.ArrayList;

/**
 * A játékosok által irányított telepeseket megvalósító osztály,
 * amely tárolja a telepesek által szállított portálokat és a nyersanyagokat tároló inventoryt.
 */
public class Settler extends Character {

    static int autoIncrement = 1;

    /**
     * A karakterhez tartozó inventory
     */
    private Inventory inventory;

    /**
     * Tárolja a szállított portálokat.
     */
    private ArrayList<Portal> portals;

    /**
     * olyan inventoryt tárol, amely tartalmazza portálhoz szükséges nyersanyagokat, ezt a játék elején állítjuk be.
     */
    private static Inventory inventoryPortal;

    /**
     * olyan inventoryt tárol, amely tartalmazza robothoz szükséges nyersanyagokat, ezt a játék elején állítjuk be.
     */
    private static Inventory inventoryRobot;


    public Settler(){
        ID = "S" + (autoIncrement++);
        portals = new ArrayList<Portal>();
        inventory = new Inventory();

        inventoryPortal = new Inventory();
        ArrayList<Resource> resourcesPortal = new ArrayList<>();
        resourcesPortal.add(new Iron());
        resourcesPortal.add(new Iron());
        resourcesPortal.add(new Uranium());
        resourcesPortal.add(new Ice());
        inventoryPortal.setResources(resourcesPortal);

        inventoryRobot = new Inventory();
        ArrayList<Resource> resourcesRobot = new ArrayList<>();
        resourcesRobot.add(new Coal());
        resourcesRobot.add(new Iron());
        resourcesRobot.add(new Uranium());
        inventoryRobot.setResources(resourcesRobot);
    }

    /**
     * Az inventory attribútum gettere
     * @return az inventory értéke
     */
    @Override
    public Inventory getInventory() {
        return inventory;
    }

    /**
     * Az inventory attribútum settere
     * @param inventory az inventory új értéke
     */
    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    /**
     * A portals attribútumhoz gettere
     * @return a portals attribútum értéke
     */
    public ArrayList<Portal> getPortals() {
        return portals;
    }

    /**
     * A portals attribútum settere
     * @param portals a portals attribútum új értéke
     */
    public void setPortals(ArrayList<Portal> portals) {
        this.portals = portals;
    }


    /**
     *  Hatására meghal a telepes, vagyis meghívódik a Die függvénye.
     */
    @Override
    public Boolean exploded(){
      //  this.die();
        return true;
    }

    /**
     * A aszteroida kéregvastagságának egy egységnyivel való csökkentése
     */
    void Drill(){
        asteroid.decreaseNumberOfLayers();
    }

    /**
     * Az aszteroida inventoryjából, eltávolítja az adott nyersanyagot, majd a nyersanyag tárolójába helyezi el.
     */
    public void Mine(){
        if(inventory.getSize() > 9)
            return;

        if(asteroid.getNumberOfLayers() == 0 && asteroid.getInventory() != null)
            asteroid.takeResource(inventory);
    }

    /**
     *  Elhelyezi az adott aszteroida szomszédságában a portált.
     */
    public Portal placePortal(String n){
        if(portals.size() == 0)
            return null;
        int size = portals.size();
        for(int i = 0; i<size ; i++) {
            Portal portal = portals.get(i);
            if(portal.getID().equals(n)) {
                ArrayList<Field> neighbours = new ArrayList<>();
                neighbours.add(asteroid);
                portal.setNeighbours(neighbours);
                asteroid.addNeighbour(portal);
                portals.remove(portal);
                return portal;
            }
        }
        return null;
    }

    /**
     * Az üreges aszteroida tárolóját, megtölti a kiválasztott nyersanyaggal.
     * @param inventoryToPut A kiválaszott nyersanyag, amelyet szeretne visszatenni
     */
    public void putResource(Inventory inventoryToPut){
        if(inventory.compareInventory(inventoryToPut)) {
            if(asteroid.putResource(inventoryToPut)) {
               inventory.remove(inventoryToPut);
            }
        }
    }

    /**
     * Létrehoz egy robotot, ha van elegendő nyersanyag.
     */
    public Robot createRobot() {
        if(inventory.compareInventory(inventoryRobot)) {
            Robot robot = new Robot();
            robot.setAsteroid(asteroid);
            asteroid.addCharacter(robot);
            inventory.remove(inventoryRobot);
            return robot;
        }else {
            return null;
        }
    }

    /**
     * Létrehoz egy portál párt, ha van elegendő nyersanyag és hely.
     */
    public void createPortal(){
        if(inventory.compareInventory(inventoryPortal) && portals.size() < 2) {
            Portal portal1 = new Portal();
            Portal portal2 = new Portal();
            portal1.setPair(portal1);
            portal2.setPair(portal2);
            portals.add(portal1);
            portals.add(portal2);
            inventory.remove(inventoryPortal);
        }
    }
    public Asteroid getAsteroid(){
        return asteroid;
    }


}
