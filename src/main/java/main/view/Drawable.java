package main.view;



import main.model.Asteroid;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Minden kirajzolni kívánt objektumnak ebből az osztályból kell leszármaznia
 */
public abstract class Drawable {

    /**
     * A kirajzolás helye
     */
    protected Point location;

    /**
     * Az objektum képe
     */
    protected BufferedImage image;

    /**
     * A kirajzoló függvény
     * @param panel Amelyre a kirajzolást végezzük
     */
    public abstract void draw(JPanel panel);

    /**
     * Visszakaphajtuk az adott objektum helyét
     * @return
     */
    public Point getLocation() {
        return location;
    }

    public abstract Object getItem();
    /**
     * Visszakapjuk az adott objektum típusát(settler, aszteroida, stb)
     * @return
     */
    public abstract String typeName();
    public abstract Asteroid getAsteroid();

}
