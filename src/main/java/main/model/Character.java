package main.model;


/**
 * Absztrakt osztály, amely a játék karaktereivel elvégezhető műveletek metszetéért felelős.
 * Tárolja, hogy az adott karakter melyik aszteroidán helyezkedik el.
 */
public abstract class Character {

    /**
     * Az az objektum amelyen a karakter található
     */
    protected Asteroid asteroid;

    protected String ID;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    /**
     * Az asteroid attribútum gettere
     * @return az asteroid értéke
     */
    public Asteroid getAsteroid() {
        return asteroid;
    }

    /**
     * Az asteroid attribútum settere
     * @param asteroid az asteroid új értéke
     */
    public void setAsteroid(Asteroid asteroid) {
        this.asteroid = asteroid;
    }

    /**
     * A typecheck kikerülése végett, amikor az összes karakter inventoryját vizsgáljuk
     * akkor a robotnak null értéket ad vissza
     * @return - a robotnak nincs inventoryja
     */
    public Inventory getInventory(){
        return null;
    }

    /**
     * A karaktert a kiválasztott objektumra mozgatja át.
     * @param d a bolygó azonosítója
     */
    public void move(int d){
        Field neighbor = this.asteroid.getNeighbourById(d);
        boolean accepted = neighbor.accept(this);
        if(!accepted)
            return;

    }

    /**
     * Egy egységgel csökkenti annak az aszteroidának a köpenyét, amelyen a karakter található.
     */
    public void drill(){
        asteroid.decreaseNumberOfLayers();
    }

    /**
     * A karakter meghal, vagyis eltávolítódik, az aszteroidáról és nem helyezzük le új aszteroidára.
     */
    public void die(){
        asteroid.removeCharacter(this);
        this.asteroid = null;

    }

    /**
     * Absztrakt függvény, amely kezeli a karakterek viselkedését, ha az aszteroidán, amelyen tartózkodnak, felrobban.
     */
    public abstract Boolean exploded();

    @Override
    public String toString(){
        return "Character";
    }
}
