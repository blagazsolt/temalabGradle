package main.controller;



import main.interf.Steppable;
import main.model.*;

import main.model.*;

import main.view.drawables.*;
import main.model.*;

import main.view.View;

import javax.swing.*;
import main.model.Character;
import main.view.drawables.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Ez az osztály felel azért, hogy elindítsa a játékot, és ellenőrizze Step()-enként,
 * hogy vége lett-e a játéknak, és hogy mi a kimenetele (nyertek vagy vesztettek a telepesek)
 */
public class GameController  {
    /**
     * Ebben tárolja az összes aszteroidákat tároló Map objektumot.
     */
    private Map map;
    private ArrayList<Steppable> steppables = new ArrayList<Steppable>();
    private View view;


    public View getView() {
        return view;
    }
    /**
     * A map attribútum gettere
     * @return map attribútum
     */
    public Map getMap() {
        return map;
    }

    /**
     * A map attribútum settere
     * @param map a map új értéke
     */
    public void setMap(Map map) {
        this.map = map;
    }

    /**
     *  Lefuttatja hatására az ellenőrzést, vagyis hogy véget ért-e a játék.
     */
    public void step(JLabel label) {
        for (Steppable s:steppables) {
            s.step();
        }
        Random random = new Random();
        if(random.nextInt(8) % 8 == 0)
            map.sunstormOnAll();

        map.checkAsteroid();

        removeDeadObjects();
        isGameEnded(label);
    }

    /**
     * Eltávolítja a steppable tömbből azokat az elemeket, amelyek meghaltak
     */
    private void removeDeadObjects() {
        int size = steppables.size();
        ArrayList<Steppable> tempList = new ArrayList<>();
        for(int i = 0; i < size; i++){
            if(steppables.get(i).getAsteroid() == null){
                tempList.add(steppables.get(i));
            }
        }
        steppables.removeAll(tempList);
    }

    /**
    *   Hozzáadja a paraméterűl kapott steppable objektumot a steppables listához.
    */
    public void addStep(Steppable steppable){
        steppables.add(steppable);
    }

    /**
     * Elindítja a játékot, létrehozza a Map-ot
     */
    public void startGame(){
        view = new View();
        view.setGameController(this);
        int numberOfAsteroids = map.getAsteorids().size();
        for(int i = 0; i < numberOfAsteroids;i++){
            Asteroid asteroid = map.getAsteorids().get(i);

            AsteroidView asteroidView = new AsteroidView(180 / numberOfAsteroids * i, asteroid);

            asteroid.setAsteroidView(asteroidView);

            for(Character c : asteroid.getCharacters()){
                switch(c.getID().charAt(0)){
                    case 'S':
                        SettlerView sw = new SettlerView((Settler)c);
                        view.addDrawable(sw);
                        break;
                    case 'R':
                        RobotView rw = new RobotView((Robot)c);
                        view.addDrawable(rw);
                        break;
                    case 'U':
                        UfoView uw = new UfoView((Ufo)c);
                        view.addDrawable(uw);
                        break;
                }
            }
            for(Field f : asteroid.getNeighbours()){
                if(f.getID().startsWith("G")){
                    PortalView pw = new PortalView((Portal)f);
                    view.addDrawable(pw);
                }
            }
            view.addDrawable(asteroidView);

        }


        view.drawAll();
    }

    /**
     * Ellenőrzi, hogy bármely feltétele teljesül-e ami lezárná a játékot.
     */
    public void isGameEnded(JLabel label){
            if(!map.isAnyoneAlive() || !map.isEnoughResource() ) {
                label.setText("Game Lost");
            }
            if(map.hasGameWon()) {
                label.setText("Game Won");
            }
    }

}
