package main.view.drawables;


import main.model.Asteroid;
import main.view.Drawable;
import main.model.Robot;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Random;

/**
 * A robot megjelenítéséért felelős osztály
 */
public class RobotView extends Drawable {

    /**
     * Tároljuk a modellben szereplő robotot, itt jelenik meg a modell illetve a grafikus rész összekapcsolása
     */
    private Robot robot;

    /**
     * A hozzá tartózó aszteroidát tároljuk, hiszen ebből tudjuk elhelyezni a robotot
     */
    private AsteroidView asteroidView;

    /**
     * A robothoz tartozó view konstruktora
     * @param r A modellbeli robot
     */
    public RobotView(Robot r){
        image = null;
        try {
            image = ImageIO.read(new File("images/portal_byme-removebg-previewblack.png"));

        } catch (IOException e) {
            System.out.println("Failed to load image");
        }
        robot = r;
        asteroidView = robot.getAsteroid().getAsteroidView();
    }

    /**
     * A drawable abstract osztály függvényének implementálása
     * Az robot kirajzolásra kerül a megfelelő helyre
     * @param panel A panel, amelyre a robotot ki kell rajzolni
     */
    @Override
    public void draw(JPanel panel) {
        JLabel label = new JLabel(new ImageIcon(image));
        Random rng = new Random();
        Dimension size = label.getPreferredSize();
        asteroidView = robot.getAsteroid().getAsteroidView();
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
        return robot;
    }

    /**
     * A robot típusát érhetjük el vele
     * @return
     */
    @Override
    public String typeName() {
        return "robot";
    }

    /**
     * a robothoz a modellben tartozó  aszteroidát éerhetjük el
     * @return
     */
    @Override
    public Asteroid getAsteroid() {
        return robot.getAsteroid();
    }
}
