package main.view.drawables;



import main.model.Asteroid;
import main.view.Drawable;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Az aszteroida megjelenítéséért felelős osztály
 */
public class AsteroidView extends Drawable {

    /**
     * Az aszetoida megjelenítéséhez szükséges szög
     * ez alapján tudjuk egyenletesen elosztani az aszteroidákat
     *
     */
    private double phi;

    /**
     * Tároljuk a modellben szereplő aszteroidát, itt jelenik meg a modell illetve a grafikus rész összekapcsolása
     */
    private Asteroid asteroid;

    /**
     * Tároljuk a naptól való távolságot
     */
    private double distance;

    /**
     * Az aszteroida azonosítóját is megjelenítjük
     * ehhez szükséges egy label
     */
    private JLabel name;


    private ArrayList<Boolean> slots;

    public ArrayList<Boolean> getSlots() {
        return slots;
    }

    public void setSlots(ArrayList<Boolean> slots) {
        this.slots = slots;
    }

    /**
     * Az szteroidákhoz tartozó view konstruktora
     * @param phi Amilyen szögben elhelyezkedik az aszteroida
     * @param a A modellben szereplő aszteroida, amihez hozzácsatolódik a view
     */
    public AsteroidView(double phi, Asteroid a){
        Random rng = new Random();
        this.phi = Math.toRadians(phi);
        image = null;
        try {
            image = ImageIO.read(new File("images/asteroid_transparent.png"));
        } catch (IOException e) {
            System.out.println("Failed to load image");
        }

        JLabel label = new JLabel(new ImageIcon(image));
        asteroid = a;
        Dimension size = label.getPreferredSize();
        if(asteroid.isCloseToSun())
            distance = 200;
        else
            distance = 400;
        location = new Point((int)(Math.sin(phi) * distance  +  1000/2  - size.width/2),(int)(Math.cos(phi)  * distance+  1000/2  - size.height/2));
        name = new JLabel(asteroid.getID() + "  layers: " + asteroid.getNumberOfLayers());
        name.setBackground(new Color(0,0,0));
        name.setForeground(Color.orange);

        slots =  new ArrayList<>(Collections.nCopies(16, false));
    }

    /**
     * A drawable abstract osztály függvényének implementálása
     * Az aszterida kirajzolásra kerül a megfelelő helyre
     * @param panel Amelyre ki kell rajzolni
     */
    @Override
    public void draw(JPanel panel) {
        JLabel label = new JLabel(new ImageIcon(image));

        Dimension size = label.getPreferredSize();
        label.setBounds(location.x,location.y, size.width, size.height);
        name.setBounds(location.x + (int)label.getPreferredSize().getWidth() ,location.y + 50,(int)name.getPreferredSize().getWidth(),(int)name.getPreferredSize().getHeight());
        label.repaint();
        name.setText(asteroid.getID() + "  layers: " + asteroid.getNumberOfLayers());
        panel.add(name);

        panel.add(label);
    }
    /**
     * visszadja az aszteroida helyét
     * @return
     */
    public Point getLocation() {
        return location;
    }

    @Override
    public Object getItem() {
        return asteroid;
    }

    /**
     * Visszadja az aszteroida típusát
     * @return
     */
    @Override
    public String typeName() {
        return "asteroid";
    }

    /**
     * Visszaadja a modellben lévő aszteroidát
     * @return
     */
    public Asteroid getAsteroid() {
        return asteroid;
    }

    /**
     * A modellben lévő aszteroidát beállítja
     * @param asteroid
     */
    public void setAsteroid(Asteroid asteroid) {
        this.asteroid = asteroid;
    }

    /**
     * Egy aszteroidához 16 hely tartozik, ahová a rajta szereplő elemeke bekerülnek
     * Ezt minden rajzolás előtt ki kell üríteni, majd feltölteni
     */
    public void initSlots(){
        for (int i = 0; i < 16; i++ ){
            slots.set(i,false);
        }
    }

    public void setSlotOccupied(int i){
        slots.set(i,true);
    }
}
