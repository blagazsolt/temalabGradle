package main.interf;


import main.model.Asteroid;

/**
 * Interfész osztály. Azok az osztályok implementálják, melyek ciklikusan
 * (körönként vagy bizonyos időközönként) végrehajtanak bizonyos metódusokat
 */
public interface Steppable {
    /**
     * Ezt a függvényt hívja meg a Controller, amikor a ciklikusan végrehajtódó metódusoknak le kell futnia.
     */
    void step();

    public Asteroid getAsteroid();
}
