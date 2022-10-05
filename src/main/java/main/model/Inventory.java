package main.model;

import main.model.resources.Resource;

import java.util.ArrayList;


/**
 * A nyersanyagok tároló osztálya. Közvetlenül csak ő ismeri a nyersanyagokat.
 */
public class Inventory {

    /**
     *  Ebben a listában tárolja a resource-okat.
     */
    private ArrayList<Resource> resources;

    public Inventory() {
        resources = new ArrayList<Resource>();
    }

    /**
     * A resources attribútumhoz tartozó getter
     * @return a resources attr. értéke
     */
    public ArrayList<Resource> getResources() {
        return resources;
    }

    /**
     * A resources attribútumhoz tartozó setter
     * @param resources a resources új értéke
     */
    public void setResources(ArrayList<Resource> resources){
        this.resources = resources;
    }

    /**
     * A paraméterként kapott inventory tartalmát hozzáadja a sajátjához.
     * @param inventory a hozzáadni kívánt inventory
     */
    public void add(Inventory inventory){
        for(Resource helpres : inventory.resources)
            resources.add(helpres);
    }

    public void addToGiven(Inventory inventory){
        for(Resource res : resources){
            inventory.resources.add(res);
        }
    }

    /**
     * A paraméterként kapott inventory tartalmát hasonlítja össze a sajátjával.
     * Amennyiben minden erőforrásból van legalább annyi a saját inventory-jában,
     * mint amennyi a paraméterként átvett inventoryban, akkor igazzal tér vissza,
     * egyébként hamissal.
     * @param inventory az összehasonlításhoz használt inventory
     * @return megegyeznek-e
     */
    public boolean compareInventory(Inventory inventory){
        int icseged = 0;
        int urseged = 0;
        int irseged = 0;
        int coseged = 0;
        int ichelp = 0;
        int urhelp = 0;
        int irhelp = 0;
        int cohelp = 0;
        for(Resource helpres : inventory.resources)
        {
            if (helpres.getID().contains("CO"))
                coseged++;
            if (helpres.getID().contains("IC"))
                icseged++;
            if (helpres.getID().contains("IR"))
                irseged++;
            if (helpres.getID().contains("UR"))
                urseged++;
        }
        for(Resource helpres : resources)
        {
            if (helpres.getID().contains("CO"))
                cohelp++;
            if (helpres.getID().contains("IC"))
                ichelp++;
            if (helpres.getID().contains("IR"))
                irhelp++;
            if (helpres.getID().contains("UR"))
                urhelp++;
        }

        return icseged <= ichelp && irseged <= irhelp && coseged <= cohelp && urseged <= urhelp;

    }

    /**
     * A saját inventory-jából kivonja a paraméterként kapott inventory tartalmát.
     * @param inventoryToRemove A kivonni kívánt inventory
     */
    public void remove(Inventory inventoryToRemove){
        for(int i = 0; i < inventoryToRemove.resources.size(); i++) {
            Resource resInRemove = inventoryToRemove.resources.get(i);
            switch (resInRemove.getID().substring(0,2)){
                case "UR" :
                    for(Resource resInOwn : resources){
                        if(resInOwn.getID().startsWith("UR")) {
                            resources.remove(resInOwn);
                            break;
                        }
                    }
                    break;
                case "IC" :
                    for(Resource resInOwn : resources){
                        if(resInOwn.getID().startsWith("IC")) {
                            resources.remove(resInOwn);
                            break;
                        }
                    }
                    break;
                case "CO" :
                    for(Resource resInOwn : resources){
                        if(resInOwn.getID().startsWith("CO")) {
                            resources.remove(resInOwn);
                            break;
                        }
                    }
                    break;
                case "IR" :
                    for(Resource resInOwn : resources){
                        if(resInOwn.getID().startsWith("IR")) {
                            resources.remove(resInOwn);
                            break;
                        }
                    }
                    break;
            }
        }

    }

    /**
     * Ez a metódus kezeli le az esetleges robbanásokat és szublimációkat.
     * Minden resources tömbben tárolt elemen meghívja a CloseToSun függvényt.
     * @param asteroid az aszteroida, amelyen az ellenőrzés van
     */
    public void checkResources(Asteroid asteroid){
        for(Resource helpres : asteroid.getInventory().resources)
            helpres.closeToSun(asteroid);
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        for(Resource r: resources){
            sb.append(r.getID());
        }
        return sb.toString();
    }

    public int getSize(){return resources.size();}
}
