package main.model;



import main.interf.Steppable;

import java.util.Random;

/**
 * Egy kaput valósít meg amin keresztül eljuthatunk egyik aszteroidáról egy másikra.
 */
public class Portal extends Field implements Steppable {

    private static int autoIncrement = 1;

    /**
     * A portálnak a párja.
     */
    private Portal pair;



    private boolean isCrazy;

    public Portal(){
        ID = "G" + (autoIncrement++);
    }


    /**
     * A pair attribútum gettere
     * @return a pair attribútum értéke
     */
    public Portal getPair() {
        return pair;
    }

    /**
     * A pair attribútum settere
     * @param portal az pair új értéke
     */
    public void setPair(Portal portal){
        pair = portal;
    }

    /**
     * Lekérdezi, hogy a párjához milyen aszteroida tartozik, majd átmozgatja a karaktert.
     * @param character a karakter amit mozgatni fog
     * @return sikerült-e a mozgatás
     */
    @Override
    public boolean accept(Character character) {
        Asteroid neighbour = null;
        try {
            neighbour = (Asteroid) pair.getNeighbourById(0);
        }catch(Exception e){

        }
        if(neighbour == null)
            return false;
        neighbour.accept(character);
        return true;
    }

    public void SunStorm(){
        isCrazy = true;
    }

    /**
     * A Step hatására a megkergült portál egy random szomszédos aszteroidára vándorol át
     */
    @Override
    public void step(){
        if(isCrazy){
            Random random = new Random();
            int size = neighbours.get(0).getNeighbours().size();
            while(true) {
                int rng = random.nextInt(size);
                if (neighbours.get(0).getNeighbours().get(rng).getID().startsWith("A")) {
                    Asteroid onlyNeigh = (Asteroid) neighbours.get(0).getNeighbours().get(rng);
                    neighbours.clear();
                    neighbours.add(onlyNeigh);
                    break;
                }
            }
        }
    }

    @Override
    public Asteroid getAsteroid() {
        return (Asteroid)getNeighbours().get(0);
    }

    public boolean isCrazy() {
        return isCrazy;
    }

    public void setCrazy(boolean crazy) {
        isCrazy = crazy;
    }
}
