package main;


import main.controller.InputHandler;
import main.model.*;

import main.model.*;

import main.model.resources.Resource;
import main.view.MenuView;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import main.model.Character;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Scanner;

import static java.lang.System.getProperty;

public class Main {
    /**
     * A program menüje ahonnan lehet indítani az egyes USE-CASE-eket.
     * @param args
     */
    public static void main(String[] args) throws IOException {

        MenuView menuView = new MenuView();

        // View view = new View();
        // view.drawAll();


        File myObj = new File("Tesztek.txt");

        myObj.createNewFile();

        InputHandler ih = new InputHandler();

        Scanner scanner = new Scanner(System.in);


        while(true) {
            System.out.print(">>>");
            String[] newline;
            try {
                newline = scanner.nextLine().split(" ");
            }catch(Exception e){
                break;
            }
            ControlGame(newline,ih);
            System.out.println(newline[0]);
        }

    }
    //segedfuggvenyeknel sokszor volt hogy nullal terunk vissza ezt majd vizsgalni kene
    public static void ControlGame(String[] args,InputHandler ih) throws IOException {
        Character c;
        String characterID;
        Settler s;
        switch(args[0]) {
            case "Move":
                try {
                    characterID = args[1];
                    String fieldId = args[2];
                    Field f1 = ih.getMap().getAsteroidById(fieldId);
                    c = ih.getMap().getCharacterByid(characterID);
                    Field f2 = c.getAsteroid();
                    int direction = f2.getDirection(fieldId);
                    if (direction != -1)
                        c.move(direction);
                    break;

                } catch (NullPointerException e) {
                    break;
                }


            case "Drill":
                try {
                    characterID = args[1];
                    c = ih.getMap().getCharacterByid(characterID);
                    if (args[1].contains("R")) {
                        Robot r = (Robot) c;
                        r.drill();
                    }
                    if (args[1].contains("S")) {
                        s = (Settler) c;
                        s.drill();
                    }
                    break;
                } catch (NullPointerException e) {
                    break;
                }
            case "Mine":
                try {
                    characterID = args[1];
                    c = ih.getMap().getCharacterByid(characterID);
                    if (args[1].contains("U")) {
                        Ufo u = (Ufo) c;
                        u.Mine();
                    }
                    if (args[1].contains("S")) {
                        s = (Settler) c;
                        s.Mine();
                    }
                    break;
                } catch (NullPointerException e) {
                    break;
                }

            case "Create":
                try {
                    characterID = args[1];
                    c = ih.getMap().getCharacterByid(characterID);
                    s = (Settler) c;
                    if (args[2].contains("robot")) {
                        s.createRobot();
                    }
                    if (args[2].contains("portal")) {
                        s.createPortal();
                    }
                    break;
                } catch (NullPointerException e) {
                    break;
                }

            case "PutResourceBack":
                try {
                    characterID = args[1];
                    c = ih.getMap().getCharacterByid(characterID);
                    s = (Settler) c;
                    Inventory inv = new Inventory();
                    ArrayList<Resource> res = new ArrayList<>();

                    for(int i = 0; i< s.getInventory().getSize(); i++){
                        if (s.getInventory().getResources().get(i).getID().equals(args[2])) {
                            res.add(s.getInventory().getResources().get(i));
                            inv.setResources(res);
                            s.putResource(inv);
                        }
                    }

                    break;
                } catch (NullPointerException e) {
                    break;
                }
            case "NextRound":
                if(args.length == 2 && args[1].equals("SunStorm"))
                    for(int i = 0; i < ih.getMap().getAsteorids().size();i++){
                        if(ih.getMap().getAsteorids().get(i).isCloseToSun())
                            ih.getMap().getAsteorids().get(i).SunStorm();
                    }

                for(int i = 0; i < ih.getMap().getAsteorids().size();i++){
                    ih.getMap().getAsteorids().get(i).closeToSun();
                }

                //  ih.getGc().step();


                break;
            case "Save":
                //sok melomajd megcsinaljukxdddddd
                break;
            case "Load":
                try {
                    Path root = Path.of(getProperty("user.dir"));
                    ih.load(root.getParent() + "\\" +args[1]);
                } catch (IOException e) {
                    Path root = Path.of(getProperty("user.dir"));
                    ih.load(root + "\\" +args[1]);
                }
                break;
            case "isCloseToSun":
                try {
                    Field f3 = ih.getMap().getFieldById(args[1]);
                    boolean b = false;
                    if (args[2].equals("true"))
                        b = true;
                    if (args[2].equals("false"))
                        b = false;
                    f3.setCloseToSun(b);
                    break;
                } catch (NullPointerException e) {
                    break;
                }

            case "PlacePortal":
                s = (Settler) ih.getGc().getMap().getCharacterByid(args[1]);
                s.placePortal(args[2]);
                break;


            case "ListAsteroid":
                if (args.length > 1) {
                    Asteroid a = ih.getMap().getAsteroidById(args[1]);
                    if(a == null)
                        break;
                    WriteOutAsteroid(a);
                    break;
                } else {
                    ArrayList<Asteroid> asteroids = ih.getMap().getAsteorids();
                    for (Asteroid a : asteroids
                    ) {
                        WriteOutAsteroid(a);
                    }
                    break;
                }

            case "ListCharacter":
                if (args.length > 1) {
                    c = ih.getMap().getCharacterByid(args[1]);
                    WriteOutCharacter(c);
                } else {
                    for (Asteroid asteroid : ih.getMap().getAsteorids()
                    ) {
                        if (!asteroid.getCharacters().isEmpty()) {
                            for (Character character : asteroid.getCharacters()
                            ) {

                                WriteOutCharacter(character);
                            }
                        }
                    }
                }
                break;
            case "Status":
                // Boolean isGameEnded =ih.getGc().isGameEnded();
                String text;
                Boolean isAnyoneAlive = ih.getMap().isAnyoneAlive();
                Boolean win = ih.getMap().hasGameWon();
                Boolean hasEnoungToContinue = ih.getMap().isEnoughResource();
                if (!isAnyoneAlive || !hasEnoungToContinue)
                    text="<Lost>\n";
                else if (win) {
                    text="<Win>\n";
                } else
                    text="<In Progress>\n";
                Path path = Paths.get("Tesztek.txt");
                Files.write(path,text.getBytes(StandardCharsets.UTF_8),StandardOpenOption.APPEND);

                break;


            case "DeleteTest":
                PrintWriter pw = new PrintWriter("Tesztek.txt");
                pw.close();
                break;
        }
    }
    public static void WriteOutCharacter(Character c) throws IOException {
        StringBuilder resource= new StringBuilder();
        //Nem fut végig mert a robotnak nincs inventoryja

        if(!c.getID().contains("R")){

            if(!c.getInventory().getResources().isEmpty()){
                for (Resource r :c.getInventory().getResources()) {
                    resource.append( r.getID());
                    resource.append(",");
                }

            }else{
                resource.append("-");
            }

        }else{
            resource.append("-");
        }
        StringBuilder portal = new StringBuilder();

        if(c.getID().contains("S")){
            Settler settler = (Settler)c;
            for (Portal p:settler.getPortals()
            ) {
                portal.append(p.getID());
                portal.append(",");
            }

        }

        String appendChar = "<"+c.getID()+">"+"<"+resource+">"+"<"+portal+">"+"\n";
        Path path = Paths.get("Tesztek.txt");

        Files.write(path, appendChar.getBytes(StandardCharsets.UTF_8), StandardOpenOption.APPEND);
    }
    public static void WriteOutAsteroid(Asteroid a) throws IOException {

        //ebben tároljuk az összefűzött fileba kiirandó karaktereket.
        StringBuilder fileChar = new StringBuilder();
        //ebben tároljuk az összefűzött fileba kiirandó portálokat.
        StringBuilder filePortal = new StringBuilder();

        for (Character c1 : a.getCharacters()) {
            fileChar.append(c1.getID());

            fileChar.append(",");

        }
        for (Field f : a.getNeighbours()) {
            if (f.getID().contains("G")) {
                filePortal.append(f.getID());
                filePortal.append(",");
            }
        }


        String resource;
        if(!a.getInventory().getResources().isEmpty())
            resource=a.getInventory().getResources().get(0).getID();
        else
            resource="-";
        String append = "<" + a.getID() + ">" + "<" + fileChar + ">" + "<" + a.getNumberOfLayers() + ">" + "<" + resource + ">" + "<" + a.isCloseToSun() + ">" + "<" + filePortal + ">" + "\n";
        Path path = Paths.get("Tesztek.txt");

        Files.write(path, append.getBytes(StandardCharsets.UTF_8), StandardOpenOption.APPEND);

    }


}

