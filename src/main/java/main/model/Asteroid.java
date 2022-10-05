package main.model;



import main.view.drawables.AsteroidView;

import java.util.ArrayList;
import java.util.ListIterator;

/**
 * Az aszteroida osztály a Field osztály leszármazottja. Alapvetően ezeken mozognak a karakterek.
 * O felelős a karakterek tárolásáért, egy az aszteroida által tárolt nyersanyag nyílvántartásáért
 * és ő kezeli le a játék által generált nehezítő körülményeket.
 */
public class Asteroid extends Field{

    private static int autoIncrement = 1;

    public Asteroid(){
        ID = "A" + (autoIncrement++);
        inventory = new Inventory();
        characters = new ArrayList<Character>();
    }

    /**
     *
     * @param character a karakter amit mozgatni fog
     * @return
     * Beleteszi a characters tömbjébe a paraméterűl kapott charactert.
     */
    @Override
    public boolean accept(Character character) {
        characters.add(character);
        Asteroid oldAst = character.getAsteroid();
        character.setAsteroid(this);
        oldAst.removeCharacter(character);
        return true;
    }

    /**
     * Az aszteroida tudja, hogy melyik mapon helyezkedik el.
     */
    private Map map;

    /**
     * Tárolja az aszteroida által tárolt nyersanyagot. Ebben a tárolóban egyszerre maximum 1 nyersanyag lehet.
     */
    private Inventory inventory;

    /**
     * Tárolja, hogy milyen karakterek tartózkodnak rajta.
     */
    private ArrayList<Character> characters;

    /**
     * A kéregvastagságot tartalmazó attribútum
     */
    private int numberOfLayers;

    private AsteroidView asteroidView;

    public AsteroidView getAsteroidView() {
        return asteroidView;
    }

    public void setAsteroidView(AsteroidView asteroidView) {
        this.asteroidView = asteroidView;
    }

    /**
     * A map attribútum gettere.
     * @return visszaadja a map attribútum értékét
     */
    public Map getMap(){return map;}


    /**
     * id alapjan keressuk a telepest
     * @param id
     * @return
     */
    public Character getCharacterByid(String id) {
        for(Character c: characters) {
            if(id.equals( c.getID())) {
                return c;
            }
        }
        return null;
    }

    /**
     * A m attribútum settere.
     * @param map a map új értéke
     */
    public void setMap(Map map) {
        this.map = map;
    }

    /**
     * Az inventory attribútum gettere.
     * @return visszaadja a inventory attribútum értékét
     */
    public Inventory getInventory(){ return inventory; }

    /**
     * Az inventory attribútum settere.
     * @param inventory az inventory új értéke
     */
    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    /**
     * A characters attribútum gettere.
     * @return visszaadja a characters attribútum értékét
     */
    public ArrayList<Character> getCharacters() {
        return characters;
    }

    /**
     * A characters attribútum settere.
     * @param characters a characters kollekció új értéke
     */
    public void setCharacters(ArrayList<Character> characters){this.characters =characters;}

    /**
     * A numberOfLayers gettere
     * @return a numberOfLayers attribútum értéke
     */
    public int getNumberOfLayers() {
        return numberOfLayers;
    }

    /**
     * A numberOfLayers settere
     * @param numberOfLayers a numberOfLayers attribútum új értéke
     */
    public void setNumberOfLayers(int numberOfLayers) {
        this.numberOfLayers = numberOfLayers;
    }

    /**
     * Megmondja, hogy aktuálisan hányan tartózkodnak rajta.
     * @return a karakterek száma
     */
    public int numberOfSettlers(){
        int numberOfSettlers = 0;
        for(int i = 0; i < characters.size();i++) {
              if(characters.get(i).getID().contains("S")) {
                  numberOfSettlers++;
              }
        }
        return numberOfSettlers;
    }

    /**
     * Felrobbantja az aszteroidát, minden rajta található karakternek szól, hogy felrobbant.
     */
    public void explode(){
        ListIterator<Character> iter = characters.listIterator();
        while(iter.hasNext()){
                iter.next().exploded();
            }

        map.removeAsteroid(this);
    }

    /**
     * Eltávolítja a magból a nyersanyagot.
     * @param inv ehhez az inventoryhoz adja hozzá a saját inventory-ját
     */
    public void takeResource(Inventory inv){
        inventory.addToGiven(inv);
        this.inventory = new Inventory();

    }

    /**
     * Ha nyersanyagot akarnak visszarakni a teljesen kifúrt üreges aszteroida belsejébe, akkor ez a függvény hívódik meg.
     * @param inventory Az inventoryban található anyagot teszi vissza a telepes az aszteroidába
     * @return sikerült-e a visszahelyezés
     */
    public boolean putResource(Inventory inventory){
        if(this.inventory.getResources().size() == 0 && this.numberOfLayers == 0) {
            this.inventory = inventory;
            return true;
        }
        return false;
    }

    /**
     * A fúrás következtében a kéregvastagság egy egységgel csökken.
     */
    public void decreaseNumberOfLayers(){
        if(numberOfLayers > 0)
            numberOfLayers--;
    }

    /**
     * Hozzáad egy paraméterként kapott character-t a characters attribútumához.
     * @param character az új karakter
     */
    public void addCharacter(Character character){
        characters.add(character);

    }

    /**
     * Eltávolítja a karaktert a characters kollekcióból.
     * @param character Az eltávolítandó karakter
     */
    public void removeCharacter(Character character){

        characters.remove(character);
    }

    /**
     * Ha a pályán napvihar tör ki, akkor ha üreges és üres az inventoryja akkor nem történik semmi egyébként az összes rajta lévő
     * karakternek meghívódik a die függvénye.
     */
    public void SunStorm(){

        if(!(numberOfLayers == 0 && (inventory == null || inventory.getSize() == 0))){
            int i = -1;
            int size = characters.size() - 1;
            while ( i < size) {
                i++;
                characters.get(0).die();
            }
        }
        return;

    }

    /**
     * A Map hívja meg, hogy megnézze, hogy az aszteroida anyaga napközeli
     * állapotban elvégezze az inventory-jában lévő anyagokon a megfelelő CloseToSun műveletet.
     * @return az aszteroida napközelben van-e
     */
    public boolean closeToSun(){

        if(closeToSun == true && numberOfLayers == 0 && ( inventory.getSize() != 0))
        {
            return inventory.getResources().get(0).closeToSun(this);
        }
        return false;
    }


}
