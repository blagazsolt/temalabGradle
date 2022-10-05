package main.view.drawables;


import main.model.Asteroid;
import main.view.Drawable;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * a nap objektum megjelenítéséért felelő osztály
 */
public class SunView extends Drawable {

    /**
     * A naphoz tartozó view, a nap mindig a képernyő közepén helyezkedik el
     */
    public SunView(){
        location = new Point(100,100);
        image = null;
        try {
            image = ImageIO.read(new File("images/Sun_optional_transparent.png"));
        } catch (IOException e) {
            System.out.println("Failed to load image");
        }
    }

    /**
     * A drawable abstract osztály függvényének implementálása
     * Az nap kirajzolásra kerül a megfelelő helyre
     * @param panel Amelyre ki kell rajzolni a napot
     */
    @Override
    public void draw(JPanel panel) {
        JLabel label = new JLabel(new ImageIcon(image));
        Dimension size = label.getPreferredSize();
        label.setBounds(panel.getWidth()/2 - size.width/2, panel.getHeight()/2 - size.height/2, size.width, size.height);

        label.repaint();
        panel.add(label);
    }

    @Override
    public Object getItem() {
        return null;
    }

    /**
     * visszadja hogy az objektum típusa sun
     * @return
     */
    @Override
    public String typeName() {
        return "sun";
    }

    /**
     * visszaadja a modellben létrehozott aszteroidát
     * @return
     */
    @Override
    public Asteroid getAsteroid() {
        return new Asteroid();
    }
}
