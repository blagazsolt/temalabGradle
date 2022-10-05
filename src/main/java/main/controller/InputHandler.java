package main.controller;



import main.model.*;

import main.model.*;

import main.model.resources.*;
import main.model.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import main.model.Character;
import main.model.resources.*;
import java.util.ArrayList;


public class InputHandler {
    private Map map;
    private GameController gc;

    public Map getMap() {
        return map;
    }

    public GameController getGc() {
        return gc;
    }

    /**
     * A pálya betöltéséért felelős
     * A 4 config file segítségével feltölti a játékhoz szükséges adatszerkezeteket
     * @param folder A pálya könyvtára, amelyben a config file-ok elérhetőek
     * @throws IOException Ha nem sikerül az olvasás
     */
    public void load(String folder) throws IOException {

        /**
         * Asteroid config file beolvasása
         */
        BufferedReader br = new BufferedReader(new FileReader(folder + "\\" + "asteroidconfig.txt"));
        String line = br.readLine();

        String[] asteroidIDs = line.split(",");
        map = new Map();
        gc = new GameController();
        for (int i = 0; i < asteroidIDs.length; i++) {
            Asteroid asteroid = new Asteroid();
            asteroid.setID(asteroidIDs[i].trim());
            map.addAsteroid(asteroid);
            map.addField(asteroid);
            asteroid.setMap(map);
        }
        int cntr = 0;
        while ((line = br.readLine()) != null && !line.equals("")) {
            String[] values = line.split(",");
            for (int i = 1; i < map.getAsteorids().size() + 1; i++) {
                ArrayList<Asteroid> asteroids = map.getAsteorids();
                if (values[i].contains("x")) {
                    asteroids.get(i - 1).addNeighbour(asteroids.get(cntr));
                }
            }
            cntr++;
        }

        int context = -1;
        while ((line = br.readLine()) != null && !line.equals("")) {
            String parsed[] = line.split(",");
            switch (parsed[0].charAt(0)) {
                case 'A':
                    context++;
                    break;
                case 'c':
                    for (int i = 1; i < parsed.length; i++) {
                        switch (parsed[i].charAt(0)) {
                            case 'S':
                                Settler settler = new Settler();
                                settler.setID(parsed[i]);
                                settler.setAsteroid(map.getAsteorids().get(context));
                                map.getAsteorids().get(context).addCharacter(settler);
                                break;
                            case 'U':
                                Ufo ufo = new Ufo();
                                ufo.setID(parsed[i]);
                                ufo.setAsteroid(map.getAsteorids().get(context));
                                map.getAsteorids().get(context).addCharacter(ufo);
                                gc.addStep(ufo);
                                break;
                            case 'R':
                                Robot robot = new Robot();
                                robot.setID(parsed[i]);
                                robot.setAsteroid(map.getAsteorids().get(context));
                                map.getAsteorids().get(context).addCharacter(robot);
                                gc.addStep(robot);
                                break;
                        }
                    }
                    break;
                case 'l':
                    map.getAsteorids().get(context).setNumberOfLayers(Integer.parseInt(parsed[1].trim()));
                    break;
                case 'i':
                    if (parsed.length < 2)
                        break;
                    Asteroid asteroid = map.getAsteorids().get(context);
                    Inventory inventory = new Inventory();
                    ArrayList<Resource> res = new ArrayList<Resource>();
                    switch (parsed[1].substring(0, 2)) {
                        case "UR":
                            Uranium ur = new Uranium();
                            ur.setID(parsed[1]);
                            res.add(ur);
                            break;
                        case "IC":
                            Ice ice = new Ice();
                            ice.setID(parsed[1]);
                            res.add(ice);
                            break;
                        case "CO":
                            Coal coal = new Coal();
                            coal.setID(parsed[1]);
                            res.add(coal);
                            break;
                        case "IR":
                            Iron iron = new Iron();
                            iron.setID(parsed[1]);
                            res.add(iron);
                            break;
                    }
                    inventory.setResources(res);
                    asteroid.setInventory(inventory);
                    break;
                case 't':
                    if (parsed[1].equals("true"))
                        map.getAsteorids().get(context).setCloseToSun(true);
                    else
                        map.getAsteorids().get(context).setCloseToSun(false);
                    break;
                case 'p':
                    for (int i = 1; i < parsed.length; i++) {
                        Portal portal = new Portal();
                        portal.setID(parsed[i]);
                        portal.addNeighbour(map.getAsteorids().get(context));
                        map.getAsteorids().get(context).addNeighbour(portal);
                        gc.addStep(portal);
                        map.addField(portal);
                        portal.addNeighbour(map.getAsteorids().get(context));
                    }
            }
        }

        /**
         * Character config file beolvasása
         */
        br = new BufferedReader(new FileReader(folder + "\\" + "characterconfig.txt"));
        while ((line = br.readLine()) != null && !line.equals("")) {
            String parsed[] = line.split(",");
            switch (parsed[0].charAt(0)) {
                case 'S':
                    for (Asteroid a : map.getAsteorids()) {
                        for (Character c : a.getCharacters()) {
                            if (c.getID().equals(parsed[0])) {
                                Settler s = (Settler) c;
                                line = br.readLine();
                                String parsedLine[] = line.split(",");
                                if (parsedLine.length > 1) {

                                    Inventory inventory = new Inventory();
                                    ArrayList<Resource> res = new ArrayList<Resource>();
                                    for (int i = 1; i < parsedLine.length; i++) {
                                        switch (parsedLine[i].substring(0, 2)) {
                                            case "UR":
                                                Uranium ur = new Uranium();
                                                ur.setID(parsedLine[i]);
                                                res.add(ur);
                                                break;
                                            case "IC":
                                                Ice ice = new Ice();
                                                ice.setID(parsedLine[i]);
                                                res.add(ice);
                                                break;
                                            case "CO":
                                                Coal coal = new Coal();
                                                coal.setID(parsedLine[i]);
                                                res.add(coal);
                                                break;
                                            case "IR":
                                                Iron iron = new Iron();
                                                iron.setID(parsedLine[i]);
                                                res.add(iron);
                                                break;
                                        }
                                    }
                                    inventory.setResources(res);
                                    s.setInventory(inventory);
                                }
                                line = br.readLine();
                                String parsedLinePortal[] = line.split(",");
                                ArrayList<Portal> portals = new ArrayList<Portal>();
                                for (int i = 1; i < parsedLinePortal.length; i++) {
                                    Portal p = new Portal();
                                    p.setID(parsedLinePortal[i]);
                                    portals.add(p);
                                }
                                s.setPortals(portals);
                            }
                        }
                    }
                    break;
                case 'U':
                    for (Asteroid a : map.getAsteorids()) {
                        for (Character c : a.getCharacters()) {
                            if (c.getID().equals(parsed[0])) {
                                Ufo ufo = (Ufo) c;
                                line = br.readLine();
                                String parsedLine[] = line.split(",");
                                if (parsedLine.length > 1) {

                                    Inventory inventory = new Inventory();
                                    ArrayList<Resource> res = new ArrayList<Resource>();
                                    for (int i = 1; i < parsedLine.length; i++) {
                                        switch (parsedLine[i].substring(0, 2)) {
                                            case "UR":
                                                Uranium ur = new Uranium();
                                                ur.setID(parsedLine[i]);
                                                res.add(ur);
                                                break;
                                            case "IC":
                                                Ice ice = new Ice();
                                                ice.setID(parsedLine[i]);
                                                res.add(ice);
                                                break;
                                            case "CO":
                                                Coal coal = new Coal();
                                                coal.setID(parsedLine[i]);
                                                res.add(coal);
                                                break;
                                            case "IR":
                                                Iron iron = new Iron();
                                                iron.setID(parsedLine[i]);
                                                res.add(iron);
                                                break;
                                        }
                                    }
                                    inventory.setResources(res);
                                    ufo.setInventory(inventory);
                                }
                            }
                        }
                    }
                    break;
            }
        }

        /**
         * Portal config file beolvasása
         */
        br = new BufferedReader(new FileReader(folder + "\\" + "portalconfig.txt"));
        while ((line = br.readLine()) != null && !line.equals("")) {
            String parsed[] = line.split(":");
            Portal p1 = findPortal(parsed[0]);
            if (p1 == null)
                continue;
            line = br.readLine();
            parsed = line.split(":");
            p1.setPair(findPortal(parsed[1]));
            line = br.readLine();
            parsed = line.split(":");
            p1.setCrazy(Boolean.parseBoolean(parsed[1]));
            line = br.readLine();
            parsed = line.split(":");
            p1.setCloseToSun(Boolean.parseBoolean(parsed[1]));
        }

        /**
         * Resource config file beolvasása
         */
        br = new BufferedReader(new FileReader(folder + "\\" + "resourceconfig.txt"));
        while ((line = br.readLine()) != null && !line.equals("")) {
            String parsed[] = line.split(":");
            Uranium u = findUranium(parsed[0]);
            parsed = br.readLine().split(":");
            u.setCounter(Integer.parseInt(parsed[1]));
        }

        br.close();
        gc.setMap(map);
    }

    /**
     * Id alapján talál meg egy portal objektumot a pályán
     * @param id A portál ID-je
     * @return A portál
     */
    private Portal findPortal(String id) {
        for (Field f : map.getFields()) {
            if (f.getID().equals(id)) {
                Portal p = (Portal) f;
                return p;
            }
        }
        for (Asteroid a : map.getAsteorids()) {
            for (Character c : a.getCharacters()) {
                if (c.getID().startsWith("S")) {
                    Settler s = (Settler) c;
                    for (Portal p : s.getPortals()) {
                        if (p.getID().equals(id)) {
                            return p;
                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     * Id alapján talál meg egy Urán objektumot a pályán
     * @param id A elem id-ja
     * @return Maga az urán objektum
     */
    private Uranium findUranium(String id) {
        for (Asteroid a : map.getAsteorids()) {
            for (Resource r : a.getInventory().getResources()) {
                if (r.getID().equals(id)) {
                    return (Uranium) r;
                }
            }
            for (Character c : a.getCharacters()) {
                if (c.getID().startsWith("S")) {
                    Settler s = (Settler) c;
                    for (Resource r : s.getInventory().getResources()) {
                        if (r.getID().equals(id)) {
                            return (Uranium) r;
                        }
                    }
                } else if (c.getID().startsWith("U")) {
                    Ufo ufo = (Ufo) c;
                    for (Resource r : ufo.getInventory().getResources()) {
                        if (r.getID().equals(id)) {
                            return (Uranium) r;
                        }
                    }
                }
            }
        }
        System.out.println("Cannot find uranium: " + id);
        return null;
    }

}
