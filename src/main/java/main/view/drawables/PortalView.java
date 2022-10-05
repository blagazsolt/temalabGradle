package main.view.drawables;

import main.model.Asteroid;
import main.model.Portal;
import main.view.Drawable;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Random;

/**
 * A portál megjelenítéséért felelős osztály
 */
public class PortalView extends Drawable {

    /**
     * A hozzá tartózó aszteroidát tároljuk, hiszen ebből tudjuk elhelyezni a portált
     */
    AsteroidView asteroidView;

    /**
     * Tároljuk a modellben szereplő portált, itt jelenik meg a modell illetve a grafikus rész összekapcsolása
     */
    private Portal portal;

    /**
     * A portál azonosításához szükséges label
     */
    private JLabel name;

    /**
     * A portálokhoz tartozó view konstruktora
     * @param p A modellbeli portál
     */
    public PortalView(Portal p){
        image = null;
        try {
            image = ImageIO.read(new File("images/portal_transparent.png"));
        } catch (IOException e) {
            System.out.println("Failed to load image");
        }
        portal = p;
        Asteroid asteroid = (Asteroid)portal.getNeighbours().get(0);

        asteroidView = asteroid.getAsteroidView();
        name = new JLabel(portal.getID());
        name.setForeground(Color.orange);
        name.setFont(new Font("Arial", Font.BOLD, 12));
    }

    /**
     * A drawable abstract osztály függvényének implementálása
     * Az portál kirajzolásra kerül a megfelelő helyre
     * @param panel Amelyre ki kell rajzolni
     */
    @Override
    public void draw(JPanel panel) {
        JLabel label = new JLabel(new ImageIcon(image));
        Random rng = new Random();
        Dimension size = label.getPreferredSize();
        Asteroid asteroid = (Asteroid) portal.getNeighbourById(0);
        asteroidView = asteroid.getAsteroidView();
        Point asteroidLocation = asteroidView.getLocation();
        for(int i = 0; i < asteroidView.getSlots().size(); i++){
            if(!asteroidView.getSlots().get(i)){
                location = new Point( asteroidLocation.x + (i % 4) * 32,asteroidLocation.y + (i / 4) * 32 );
                asteroidView.setSlotOccupied(i);
                break;
            }
        }

        label.setBounds(location.x,location.y , size.width, size.height);
        name.setBounds(location.x + (int)label.getPreferredSize().getWidth() -23,location.y + 8,(int)name.getPreferredSize().getWidth(),(int)name.getPreferredSize().getHeight());

        label.repaint();
        panel.add(name);
        panel.add(label);
    }

    @Override
    public Object getItem() {
        return portal;
    }

    /**
     * A portál típusát érhetjük el vele
     * @return
     */
    @Override
    public String typeName() {
        return "portal";
    }

    /**
     * modellben szereplő aszteroidát adja vissza
     * @return
     */
    @Override
    public Asteroid getAsteroid() {
        return (Asteroid) portal.getNeighbours().get(0);
    }
}
