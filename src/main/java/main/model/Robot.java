package main.model;



import main.interf.Steppable;

import java.util.ArrayList;
import java.util.Random;

/**
 * A robot karakterre vonatkozó műveletekért felelős.
 */
public class Robot extends Character implements Steppable {

    private static int autoIncrement = 1;

    public Robot(){
        ID = "R" + (autoIncrement++);

    }

    Random random = new Random(System.currentTimeMillis());


    /**
     * A metódus a robot viselkedését kezeli, ha az aszteroida felrobban,
     * ilyenkor a robot, az egyik szomszédos aszteroidára kerül át.
     */
    @Override
    public Boolean exploded(){
        ArrayList<Field> neighbours = asteroid.getNeighbours();
        move(random.nextInt() % neighbours.size());
        return false;
    }

    /**
     * A aszteroida kéregvastagságának egy egységnyivel való csökkentése
     */
    void Drill(){
        asteroid.decreaseNumberOfLayers();
    }

    /**
     * A robot, műveleteinek egyikét hívja meg  a step függvény.
     */
    @Override
    public void step(){

        switch (random.nextInt(2)){
            case 0: {
                ArrayList<Field> neighbours = asteroid.getNeighbours();
                if(neighbours.size() != 0)
                    move(Math.abs(random.nextInt(neighbours.size()) ));
            }
            case 1:{
                drill();
            }
        }
    }

    public Asteroid getAsteroid(){
        return asteroid;
    }


}


