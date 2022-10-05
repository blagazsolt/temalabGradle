package main.view.drawables;


import main.model.Asteroid;
import main.model.Settler;
import main.view.Drawable;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Random;

/**
 * A telepes megjelenítéséért felelős osztály
 */
public class SettlerView extends Drawable {

    /**
     * A hozzá tartózó aszteroidát tároljuk, hiszen ebből tudjuk elhelyezni a telepest
     */
    AsteroidView asteroidView;

    /**
     * Tároljuk a modellben szereplő telepest, itt jelenik meg a modell illetve a grafikus rész összekapcsolása
     */
    private Settler settler;

    /**
     * A settlerekhez tartozó view konstruktora
     * @param settler A modellben szereplő settler, amihez hozzárendelődik a view
     */
    public SettlerView(Settler settler){
        image = null;
        try {
            image = ImageIO.read(new File("images/amongus_transparent.png"));
        } catch (IOException e) {
            System.out.println("Failed to load image");
        }
        this.settler = settler;
        asteroidView = settler.getAsteroid().getAsteroidView();
    }

    /**
     * A drawable abstract osztály függvényének implementálása
     * Az aszterida kirajzolásra kerül a megfelelő helyre
     * @param panel A panel, amelyre a settler-t ki kell rajzolni
     */
    @Override
    public void draw(JPanel panel) {
        JLabel label = new JLabel(new ImageIcon(image));
        Random rng = new Random();
        Dimension size = label.getPreferredSize();
        asteroidView = settler.getAsteroid().getAsteroidView();
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
        return settler;
    }

    /**
     * visszadja hogy az objektum típusa settler
     * @return
     */
    @Override
    public String typeName() {
        return "settler";
    }

    /**
     * a modellben szereplő telepeshez tartozó aszteroidát adja vissza
     * @return
     */
    @Override
    public Asteroid getAsteroid() {
        return settler.getAsteroid();
    }

    /**
     * beállítja az asterodviewt amely tartozik a telepeshez, vagyis amin áll
     * @param asteroidView
     */
    public void setAsteroidView(AsteroidView asteroidView) {
        this.asteroidView = asteroidView;
    }
}
