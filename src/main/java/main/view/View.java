package main.view;


import main.controller.GameController;
import main.model.*;

import main.model.*;

import main.view.drawables.*;
import main.model.*;

import main.model.Robot;
import main.model.resources.Resource;
import main.view.drawables.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import main.model.Character;
import main.view.drawables.*;
import java.util.ArrayList;
import java.util.Comparator;

public class View extends JFrame{

    /**
     *A kirajzolható objektumokat tároljuk a listában
     */
    private ArrayList<Drawable> drawables;

    /**
     * A játék irányításához tárolunk egy gamecontroller objektumot
     */
    private GameController gameController;

    /**
     * A panel amivel dolgozunk amin megjelenítjük az objtektumokat
     */
    private JPanel panel;

    /**
     * A menü amelyből kiválasztatjuk az adott telepesre eső funkciót
     */
    private JPopupMenu popupmenu = new JPopupMenu("Edit");

    /**
     * Ezen a labelen jelenítjük meg az adott akciót és kimenetelét
     */
    JLabel label;

    /**
     * klikkelés pozícióját tároljuk le, hogy megállapítsuk hogy melyik objektumon történt a kattintás
     */
    private Point mousePosition;

    /**
     * az aktuálisan kiválasztot telepest tároljuk hogy rajta műveleteket végezhessünk
     */
    private Drawable selectedSettler;

    /**
     * Az osztály konstruktora, létrehozza a nézetet, illetve egy felugrómenüt is, mely a telepesek irányítását teszi lehetővé.
     */
    public View() {
        /**
         * beállítjuk az ablak kinézetét és paramétereit
         */
        super("Pupák telepesek");
        panel = new JPanel();
        getContentPane();
        panel.setLayout(null);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        add(panel);
        setSize(1100, 1000);
        panel.setBackground(new Color(0, 0, 0));
        setVisible(true);
        drawables = new ArrayList<Drawable>();
        drawables.add(new SunView());
        int numberOfAsteroids;
        label = new JLabel("");


        /**
         * létrehozzuk az egyes funkciókat megjelenítő menüpontokat
         */

        label.setHorizontalAlignment(JLabel.CENTER);
        label.setBounds(20,20,1000,20);
        label.setForeground(Color.WHITE);
        JMenuItem Move = new JMenuItem("Move");
        JMenuItem Drill = new JMenuItem("Drill");
        JMenuItem Mine = new JMenuItem("Mine");
        JMenuItem RobotCreate = new JMenuItem("RobotCreate");
        JMenuItem PortalCreate = new JMenuItem("PortalCreate");
        JMenuItem PutResourceBack = new JMenuItem("PutResourceBack");
        JMenuItem Save = new JMenuItem("Save");
        JMenuItem PlacePortal = new JMenuItem("PlacePortal");

        /**
         * hozzáadjuk az egyes menüelemeket a felugró menühöz
         */
        popupmenu.add(Move);
        popupmenu.add(Drill);
        popupmenu.add(Mine);
        popupmenu.add(RobotCreate);
        popupmenu.add(PortalCreate);
        popupmenu.add(PutResourceBack);
        //popupmenu.add(Save);
        popupmenu.add(PlacePortal);

        /**
         * beállítjuk a menüelemekre az action listenereket vagyis hozzájuk rendelünk bizonyos akciókat
         */
        this.addMouseListener(new MyMouseAdapter());
        Move.addActionListener(new MoveActionPerformed());
        Drill.addActionListener(new DrillActionPerformed());
        Mine.addActionListener(new MineActionPerformed());
        RobotCreate.addActionListener(new RobotCreateActionPerformed());
        PortalCreate.addActionListener(new PortalCreateActionPerformed());
        PutResourceBack.addActionListener(new PutResourceBackActionPerformed());
        //Save.addActionListener(new SaveActionPerformed());
        PlacePortal.addActionListener(new PlacePortalActionPerformed());

        /**
         * panelhez adjuk a megfelelő elemeket
         */
        panel.add(label);
        panel.add(popupmenu);
        panel.setLayout(null);
        panel.setVisible(true);

    }

    /**
     * Minden objektura meghívja a kirajzolást.
     * A kirajzolandó elemek(aszteroidák,telepesek,stb) megjelenítéséért felelős függvény amely
     * végig iterál a kirajzolandó objektumokat tárolandó listán és meghívja a draw függvényüket
     * az elején letörli az egész panelt vagyis frissítjük a tartalmat
     *  ezután történik az újrarajzolásd
     */
    public void drawAll() {

        /**
         * Slot-tömbök alaphelyzetbe állítása
         */

        for (Drawable drawable : drawables) {
            if(drawable.typeName().equals("asteroid"))
                ((AsteroidView)drawable).initSlots();
        }

        removeDeadObjects();

        /**
         * Rendezés a megfelelő kirajzoláshoz
         */

        drawables.sort(compareByTypeName);

        panel.removeAll();
        for (Drawable drawable : drawables) {
            drawable.draw(panel);
        }
        panel.add(label);
        panel.repaint();
        repaint();
        setVisible(true);

        if(label.getText().equals("Game Lost") || label.getText().equals("Game Won")){
            int resp = JOptionPane.showConfirmDialog(this, "Wanna play again?",
                    "Game Ended", JOptionPane.YES_NO_OPTION);

            if (resp == JOptionPane.YES_OPTION) {
                setVisible(false);
                dispose();
                MenuView menuView = new MenuView();
            } else {
                setVisible(false);
                dispose();
                System.exit(0);
            }
        }

    }


    /**
     * Frissíti a nézetet, az aktuális állapotot rajzoltatja újra.
     */
    private void removeDeadObjects() {
        drawables.clear();
        drawables.add(new SunView());
        int numberOfAsteroids = gameController.getMap().getAsteorids().size();
        for(int i = 0; i < numberOfAsteroids;i++){
            Asteroid asteroid = gameController.getMap().getAsteorids().get(i);

            AsteroidView asteroidView = new AsteroidView(360 / numberOfAsteroids * i, asteroid);

            asteroid.setAsteroidView(asteroidView);


            for(Character c : asteroid.getCharacters()){
                switch(c.getID().charAt(0)){
                    case 'S':
                        SettlerView sw = new SettlerView((Settler)c);
                        this.addDrawable(sw);
                        break;
                    case 'R':
                        RobotView rw = new RobotView((Robot)c);
                        this.addDrawable(rw);
                        break;
                    case 'U':
                        UfoView uw = new UfoView((Ufo)c);
                        this.addDrawable(uw);
                        break;
                }
            }
            for(Field f : asteroid.getNeighbours()){
                if((f).getID().startsWith("G")){
                    if(((Portal)f).getAsteroid() != null ) {
                        PortalView pw = new PortalView((Portal) f);
                        this.addDrawable(pw);
                    }
                }
            }

            if(gameController.getMap().getAsteorids().contains(asteroid))
                this.addDrawable(asteroidView);

        }
    }


    /**
     *  Comparator, hogy az aszteroidák hátul legyenek a drawable tömbben
     */

    private final Comparator<Drawable> compareByTypeName = (Drawable o1, Drawable o2) ->
            o2.typeName().compareTo( o1.typeName() );

    /**
     * GameController beállítása a paraméterben kapott értékre.
     * @param gameController
     */
    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }

    /**
     * A kirajzolandó elemeklistájához hozzáadása a parméterben kapott objektumot.
     * @param d
     */
    public void addDrawable(Drawable d) {
        drawables.add(d);
    }

    /**
     * A move funkcióra való kattintáskor történő működésért felelős osztály
     */
    public class MoveActionPerformed implements ActionListener{
        /**
         * A settler mozgását iráyító eseménykezelő.
         * @param e
         */
        public void actionPerformed(ActionEvent e) {
            try {
                Settler settler =  (Settler)selectedSettler.getItem();
                JOptionPane jOptionPane = new JOptionPane();
                StringBuilder line= new StringBuilder();
                for (Field field:settler.getAsteroid().getNeighbours()
                ) {
                    line.append(field.getID());
                    line.append(",");
                }
                String message = jOptionPane.showInputDialog("Válassz szomszédot ezek közül: "+line);

                int direction = settler.getAsteroid().getDirection(message);
                //azt nem ellenörzi, hogy melyik egy valid aszteroidára akar-e lépni vagy elírta-e vagy ilyesmi vagy hogy szomszédja-e egyáltalán.
                if(direction >= 0)
                    settler.move(direction);
                gameController.step(label);
                drawAll();
                label.setText("Move MenuItem clicked.");
            }catch (NullPointerException nullPointerException) {
                nullPointerException.printStackTrace();
                label.setText("Move failed");
            }
        }
    }

    /**
     * A drill funkcióra való kattintáskor történő működésért felelős osztály
     */
    public class DrillActionPerformed implements ActionListener{
        /**
         * Aszteroidának köpenyét csökkentőeseménykezelő
         * @param e Esemény.
         */
        public void actionPerformed(ActionEvent e) {
            try {
                Settler settler = (Settler)selectedSettler.getItem();
                settler.drill();
                label.setText("Drill MenuItem clicked.");
                gameController.step(label);
                drawAll();
            }catch (NullPointerException nullPointerException){
                label.setText("Drill failed");
            }

        }
    }

    /**
     * A mine funkcióra való kattintáskor történő működésért felelős osztály
     */
    public class MineActionPerformed implements ActionListener{
        /**
         * A bányszás esemény kiinduló pontja.
         * @param e
         */
        public void actionPerformed(ActionEvent e) {
            try {
                Settler settler = (Settler)selectedSettler.getItem();
                settler.Mine();
                gameController.step(label);
                drawAll();
                label.setText("Mine MenuItem clicked.");
            }catch (NullPointerException nullPointerException){
                label.setText("Mine failed");
            }

        }
    }

    /**
     * A robotcreate funkcióra való kattintáskor történő működésért felelős osztály
     */
    public class RobotCreateActionPerformed implements ActionListener{
        /**
         * Robot készítést végző eseménykezelő.
         * @param e
         */
        public void actionPerformed(ActionEvent e) {
            try {
                Settler settler = (Settler)selectedSettler.getItem();
                Robot r = settler.createRobot();
                //Robotelkészültét ellenőrizzük, ha sikeres, akkor újrarajzolás.
                if(r!=null) {
                    RobotView rw = new RobotView(r);
                    addDrawable(rw);
                    repaint();
                    drawAll();
                }
                gameController.step(label);
                drawAll();
                label.setText("RobotCreate MenuItem clicked.");
            }catch (NullPointerException nullPointerException){
                label.setText("RobotCreate failed");
            }

        }
    }

    /**
     * A portalcreate funkcióra való kattintáskor történő működésért felelős osztály
     */
    public class PortalCreateActionPerformed implements ActionListener{
        /**
         * Portálkészítést kiváltó eseménykezelő.
         * @param e
         */
        public void actionPerformed(ActionEvent e) {
            try {
                Settler settler = (Settler)selectedSettler.getItem();
                settler.createPortal();
                gameController.step(label);
                drawAll();
                label.setText("PortalCreate MenuItem clicked.");
            }catch (NullPointerException nullPointerException){
                label.setText("PutBackResource failed");
            }

        }
    }

    /**
     * A putresourceback funkcióra való kattintáskor történő működésért felelős osztály
     */
    public class PutResourceBackActionPerformed implements ActionListener{
        /**
         * Nyersanyagvisszatételét kiváltó esemény kezelője.
         * @param e
         */
        public void actionPerformed(ActionEvent e) {
            Settler settler = (Settler)selectedSettler.getItem();
            JOptionPane jOptionPane = new JOptionPane();
            StringBuilder line= new StringBuilder();
            //Összesítés, milyen nyersanyagvan nála.
            for (Resource r: settler.getInventory().getResources()
            ) {
                line.append(r.getID());
                line.append(",");
            }
            //Bekérjük, a nála lévő nyersanyagk közül melyiket szeretnévisszarakni.
            String message = jOptionPane.showInputDialog("Válassz a nyersanyagok közül: "+line);
            ArrayList<Resource> res = new ArrayList<>();
            Inventory inv = new Inventory();
            //Nyersanyag visszatétele.
            for(int i = 0; i< settler.getInventory().getSize(); i++){
                if (settler.getInventory().getResources().get(i).getID().equals(message)) {
                    res.add(settler.getInventory().getResources().get(i));
                    inv.setResources(res);
                    settler.putResource(inv);
                }
                label.setText("PutResourceBack MenuItem clicked.");
            }
            gameController.step(label);
            drawAll();
        }
    }


    /**
     * A placeportal funkcióra való kattintáskor történő működésért felelős osztály
     */
    public class PlacePortalActionPerformed implements ActionListener{
        /**
         * Portál letevő eseménykezelő.
         * @param e
         */
        public void actionPerformed(ActionEvent e) {
            Settler settler =(Settler)selectedSettler.getItem();
            ArrayList<Portal> portals = settler.getPortals();
            //ezzel valamiért valami bajvan
            if(!(portals.isEmpty())) {
                Portal portal = settler.placePortal(portals.get(0).getID());
                PortalView pw = new PortalView(portal);
                drawables.add(pw);
                drawAll();
            }
            gameController.step(label);
            drawAll();
            label.setText("PlacePortal MenuItem clicked.");


        }
    }

    /**
     * Segédfüggvény amely megállapítja hogy egy megadott téglalapon belül történ-e a kattintás vagy sem
     * @param mousePosition a kattintás pozíciója
     * @param rectVert a megadott téglalap baé felső koordinátája
     * @param rectSize a megadott téglalap mérete
     * @return  bool amely értéke false ha nem a téglalapőon belül történt a kattintás true ha pedig igen
     */
    private boolean isMousePositionInRect(Point mousePosition, Point rectVert, int rectSize){
        return rectVert.x < mousePosition.x && rectVert.x + rectSize > mousePosition.x && rectVert.y  < mousePosition.y && rectVert.y + rectSize > mousePosition.y;
    }

    /**
     * az egérkattintás hatására bekövetkező esemény kezeléséért felelős
     * megállapítja a kattintás helyét
     * ellenőrzi az összes megjelenített objektumra, hogy rá történt e a kattintás
     * majd ellenőrizzük hogy egy telepesen történ-e ez a kattintás és a telepest letároljuk
     *
     */
    public class MyMouseAdapter implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {
            if (SwingUtilities.isLeftMouseButton(e)) {
                mousePosition = new Point(e.getX(), e.getY());
                for (Drawable d : drawables) {
                    if (d.getLocation() != null && d.typeName() != null) {
                        if (isMousePositionInRect(mousePosition, d.getLocation(), 50) && d.typeName().equals("settler")) {
                            popupmenu.show(panel, e.getX(), e.getY());
                            selectedSettler = d;
                        }
                    }
                }
                System.out.println("left click");
            }
            else if(SwingUtilities.isRightMouseButton(e)) {
                mousePosition = new Point(e.getX(), e.getY());
                for (Drawable d : drawables) {
                    if (d.getLocation() != null && d.typeName() != null) {
                        if (isMousePositionInRect(mousePosition, d.getLocation(), 50) && d.typeName().equals("settler")) {
                            selectedSettler = d;
                            SettlerView selectedSettlerView = (SettlerView)selectedSettler;
                            Settler settler = (Settler) selectedSettlerView.getItem();

                            JPopupMenu popupmenuDetails = new JPopupMenu("Details");
                            popupmenuDetails.add(new JMenuItem("Resources:"));

                            for(Resource res : settler.getInventory().getResources())
                            {
                                popupmenuDetails.add(new JMenuItem(res.getID()));
                            }

                            popupmenuDetails.add(new JMenuItem("Portals:"));

                            for(Portal portal : settler.getPortals())
                            {
                                popupmenuDetails.add(new JMenuItem(portal.getID()));
                            }

                            popupmenuDetails.show(panel,e.getX(),e.getY());
                        }
                    }
                }
                System.out.println("righ click");
            }
        }

        /**
         * az egérgomb lenyomását kezelő metódus
         * @param e
         */
        @Override
        public void mousePressed(MouseEvent e) {

        }

        /**
         * az egérgomb elengedését kezelő metódus
         * @param e
         */
        @Override
        public void mouseReleased(MouseEvent e) {

        }

        /**
         * az egérgomb mozgatása a képernyőre eseményt kezelő metódus
         * @param e
         */
        @Override
        public void mouseEntered(MouseEvent e) {

        }

        /**
         * az egérgomb elmozgatása a képernyőről eseményt kezelő metódus
         * @param e
         */
        @Override
        public void mouseExited(MouseEvent e) {


        }


    }
}
