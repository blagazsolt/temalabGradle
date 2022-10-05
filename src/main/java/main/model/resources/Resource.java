package main.model.resources;


import main.model.Asteroid;

/**
 * Egy abstract ősosztály amelyből származnak a nyersanyagok. Azért felel, hogy minden ős implementálja a CloseToSun() függvényt.
 */
public abstract class Resource {

    private String ID;

    public String getID() {
        return ID;
    }

    /**
     * Egy virtuális függvény amelyet felülírják a belőle származó nyersanyagok.
     * Alapértelmezetten nem történik semmi csak visszatér egy null értékkel.
     * @param asteroid az aszteroida, ami éppen napközelben van
     */
    public Boolean closeToSun(Asteroid asteroid){
        return false;
    }
}
