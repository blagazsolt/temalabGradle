package main.model;



import main.model.resources.*;
import main.model.resources.*;

import main.model.resources.*;
import java.util.ArrayList;

/**
 * Tárolja az aszteroidákat, ő menedzseli a létezésüket, és ezáltal ő tudja hogy melyik objektumból mennyi van.
 */
public class Map {

    /**
     * Lista mely tárolja az aszteroidákat
     */
    private ArrayList<Asteroid> asteorids;


    /**
     * Lista mely tárolja a feieldeket
      */
    private ArrayList<Field> fields;

    /**
     * Inventory osztálypéldány, tárolja, hogy mennyi nyersanyag kell a játék megnyeréséhez.
     */
    private static Inventory StaticInventoryWin;

    /**
     * A Map konstrukotra, létrehozza a tagváltozókat, és a StaticInventoryWinnek megadja az alapértékeit, ami szükséges ahhoz, hogy megnyerjük a játékot.
     */
    public Map(){
        asteorids = new ArrayList<>();
        fields=new ArrayList<>();
        StaticInventoryWin = new Inventory();
        ArrayList<Resource> resources = new ArrayList<>();
        for(int i =0;i<3;i++) {
            resources.add(new Coal());
            resources.add(new Iron());
            resources.add(new Uranium());
            resources.add(new Ice());
        }
        StaticInventoryWin.setResources(resources);
    }

    /**
     * A asteroids attribútum gettere
     * @return az asteroids attr. értéke
     */
    public ArrayList<Asteroid> getAsteorids() {
        return asteorids;
    }

    /**
     * Az asteroids attr. settere
     * @param asteorids az asteroids attr. új értéke
     */
    public void setAsteorids(ArrayList<Asteroid> asteorids) {
        this.asteorids = asteorids;
    }


    /**
     * Új aszteroidát ad az asteroids kollekcióhoz
     * @param ast az új aszterida
     */
    public void addAsteroid(Asteroid ast){
        asteorids.add(ast);
    }

    /**
     *  Eltávolít egy Asteroidát az asteroidList-ből
     * @param a az eltávolítandó aszteroida
     */
    public void removeAsteroid(Asteroid a){
        asteorids.remove(a);
    }

    /**
     * Az összes fielden meghívja a SunStorm metódust.
     */
    public void sunstormOnAll(){
        for (Field f: fields) {
            f.SunStorm();
        }
    }

    /**
     * Megnézi van-e még elő telepes bármely aszteroidán.
     * @return él-e még valaki
     */
    public boolean isAnyoneAlive() {
        for (Asteroid a : asteorids) {
            for (Character character : a.getCharacters()) {
                if (character.getID().startsWith("S"))
                    return true;
            }
        }
        return false;
    }

    /**
     * Megnézi van-e elég nyersanyag valamennyi aszteroidán és telepesnél, hogy a játék tudjon folytatódni
     * (ha nincs elég és már nem lehet nyerni false-al tér vissza)
     * @return van-e még elég nyersanyag
     */
    public boolean isEnoughResource(){
        Inventory localInv = new Inventory();
        ArrayList<Character> charInv = new ArrayList<>();
        for (Asteroid a:asteorids) {
            localInv.add(a.getInventory());
            charInv=a.getCharacters();

            for (Character c : charInv) {
                try {
                    localInv.add(c.getInventory());
                }
                catch (NullPointerException e){
                }
            }
        }

        if(localInv.compareInventory(StaticInventoryWin)){

            return true;
        }
        return false;

    }

    /**
     * Az Asteroid CloseToSun metódusát hívja meg.
     */
    public void checkAsteroid(){
        ArrayList<Asteroid> tempList = new ArrayList<>();
        ArrayList<Field> tempListField = new ArrayList<>();

        for(int i = 0 ; i < asteorids.size(); i++)
            if(asteorids.get(i).closeToSun()) {
                tempList.add(asteorids.get(i));
                for(Field f : asteorids.get(i).getNeighbours()){
                    if( f.getID().startsWith("G") )
                        tempListField.add(f);
                }
            }

        this.fields.removeAll(tempListField);
        this.asteorids.removeAll(tempList);

        for(Asteroid asteroid : tempList){
            for(Field field : asteroid.getNeighbours()){
                field.removeNeighbour(asteroid);
            }
        }
   }

    public Character getCharacterByid(String id) {
        Character c = null;
        for(Asteroid a: asteorids) {
            if(a.getCharacterByid(id) != null) {
                c = a.getCharacterByid(id);
                break;
            }
        }
        return c;
    }

    public Asteroid getAsteroidById(String id) {
        for(Asteroid a: asteorids) {
            if(id.equals( a.getID()))
                return a;
        }
        return null;
    }

    public Field getFieldById(String id) {
        for(Field f: fields) {
            if(id.equals( f.getID()))
                return f;
        }
        return null;
    }


    /**
     * Megnézi, hogy bármely aszteroidán össze lett-e gyűjtve a játék megnyeréséhez
     * elegendő nyersanyag. Ha igen, igazzal tér vissza.
     * @return megnyerték-e a játékot
     */
    public boolean hasGameWon(){
        Inventory localInv = new Inventory();
        ArrayList<Character> characters = new ArrayList<>();
        try{
        for (Asteroid a:asteorids) {
                characters.addAll(a.getCharacters());
                for (Character c : characters) {
                    localInv.add(c.getInventory());
                }
                if (localInv.compareInventory(StaticInventoryWin)) {
                    return true;
                }
                localInv = new Inventory();
                characters.clear();
            }
        }
        catch (NullPointerException e){
            //Nem tudta, hozzáadni, mert nincs karakter az aszteroidán.
        }
        return false;
    }
    public void addField(Field f ){fields.add(f);}
    public ArrayList<Field> getFields() {
        return fields;
    }
}
