package main.view.drawables;


import main.model.Asteroid;
import main.model.Ufo;
import main.view.Drawable;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Random;

/**
 * az ufo megjelenítéséért felelős osztály
 */
public class UfoView extends Drawable {

    /**
     * A hozzá tartózó aszteroidát tároljuk, hiszen ebből tudjuk elhelyezni az ufót
     */
    private AsteroidView asteroidView;


    /**
     * Tároljuk a modellben szereplő ufót, itt jelenik meg a modell illetve a grafikus rész összekapcsolása
     */
    private Ufo ufo;

    /**
     * tároljuk az eltolást az aszteroidán
     */
    private static final Point SHIFT_ON_ASTEROID = new Point(10,30);

    /**
     * Au ufokhoz tartozó view kosntruktora
     * @param ufo A modellbeli ufo, amelyhez hozzárendelődik a view
     */
    public UfoView(Ufo ufo){
        image = null;
        try {
            image = ImageIO.read(new File("images/ufo_colorfull_transparent.png"));
        } catch (IOException e) {
            System.out.println("Failed to load image");
        }
        this.ufo = ufo;
    }

    /**
     * A drawable abstract osztály függvényének implementálása
     * Az ufo kirajzolásra kerül a megfelelő helyre
     * @param panel A panel, amelyre ki kell rajzolnunk az ufo-t
     */
    @Override
    public void draw(JPanel panel) {
        JLabel label = new JLabel(new ImageIcon(image));
        Random rng = new Random();
        Dimension size = label.getPreferredSize();
        asteroidView = ufo.getAsteroid().getAsteroidView();
        Point asteroidLocation = asteroidView.getLocation();
        for(int i = 0; i < asteroidView.getSlots().size(); i++){
            if(!asteroidView.getSlots().get(i)) {
                location = new Point(asteroidLocation.x + (i % 4) * 32, asteroidLocation.y + (i / 4) * 32);
                asteroidView.setSlotOccupied(i);
                break;
            }
        }
        label.setBounds(location.x,location.y , size.width, size.height);
        label.repaint();
        panel.add(label);
    }

    @Override
    public Object getItem() {
        return ufo;
    }

    /**
     * visszadja hogy az objektum típusa ufo
     * @return
     */
    @Override
    public String typeName() {
        return "ufo";
    }

    /**
     * visszadja az ufohoz tartozó aszteroidát
     * @return
     */
    @Override
    public Asteroid getAsteroid() {
        return ufo.getAsteroid();
    }


}
