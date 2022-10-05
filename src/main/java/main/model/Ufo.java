package main.model;



import main.interf.Steppable;

import java.util.ArrayList;
import java.util.Random;

public class Ufo extends Character implements Steppable {

    private static int autoIncrement = 1;

    private Inventory inventory;

    public Ufo(){
        ID = "U" + (autoIncrement++);
        inventory =  new Inventory();
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


    @Override
    public Boolean exploded() {
        return true;
    }

    /**
     * Az aszteroida inventoryjából, eltávolítja az adott nyersanyagot, majd a nyersanyag tárolójába helyezi el.
     */
    public void Mine(){
        if(inventory.getSize() == 10)
            return;

        if(asteroid.getNumberOfLayers() == 0)
            asteroid.takeResource(inventory);
    }

    /**
     * A step hatására az Ufo vagy bányászni fog az adott aszteroidáján, vagy mozogni
     * egy szomszédos aszteroidára, vagy a portálal egy távolibbra
     */
    @Override
    public void step() {
        Random random = new Random();
        switch (random.nextInt(2)){
            case 0: {
                ArrayList<Field> neighbours = asteroid.getNeighbours();
                if(neighbours.size() != 0)
                    move(Math.abs(random.nextInt(neighbours.size()) ));
            }
            case 1:{
                Mine();
            }
        }
    }

    public Asteroid getAsteroid(){
        return asteroid;
    }
}
