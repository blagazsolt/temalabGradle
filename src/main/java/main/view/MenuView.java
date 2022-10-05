package main.view;



import main.controller.InputHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

/**
 * A játék elindításakor egy menü jelenik meg, itt van lehetőség a pálya kiválasztására
 * A kezőmenü megvalósításáért felelős osztály
 *  kiválasztaóhatjuk belőle a megfelelő páláyt, illetve becsukhatjuk a programot
 */
public class MenuView extends JFrame implements ActionListener {

    /**
     * A pálya beolvasásáért felelős objektum amely egy txt fileból fogja inicializálni a megflelő pályát
     */
    private InputHandler inputHandler = new InputHandler();

    /**
     * Az elrendezéshez szükséges panelek
     */
    private JPanel bottomPanel = new JPanel();


    private JPanel leftPanel = new JPanel();

    /**
     * A megadott pályákat tároló modell lista
     */
    DefaultListModel<String> dataList = new DefaultListModel<>();

    /**
     * A pályanevek megjelenítéséért felelős jlist
     */
    JList<String> jlist = new JList<>(dataList);

    /**
     * A start és exit gomb illetve a pálya kiválasztására felszólító jlabel
     */
    private JLabel labelChooseMap = new JLabel("Pálya kiválasztása");
    private JButton btnStartGame = new JButton("Játék indítása");
    private JButton btnExit = new JButton("Kilépés");

    /**
     * A megjelenítendő ablak paramétereinek beállítása
     * illetve megjelenítése
     */
    public MenuView() {
        super("Demo Menu");
        this.getContentPane();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(450, 350);
        setResizable(false);

        initPanel();

        setVisible(true);

    }

    /**
     * a panelen lévő elemeket inicializáljuk
     * beállítjuk a megdflelő elhelyezkedést
     * illetve megjelenítést
     */
    private void initPanel() {
        labelChooseMap.setFont(new Font("Serif", Font.PLAIN, 25));
        add(labelChooseMap, BorderLayout.NORTH);

        loadList();
        JScrollPane scrollFrame = new JScrollPane(jlist);
        scrollFrame.setPreferredSize(new Dimension(350, 225));
        jlist.setAutoscrolls(true);

        leftPanel.add(scrollFrame);
        add(leftPanel, BorderLayout.WEST);

        btnStartGame.addActionListener(this);
        btnExit.addActionListener(ea -> System.exit(0));

        bottomPanel.add(btnStartGame);
        bottomPanel.add(btnExit);
        add(bottomPanel, BorderLayout.SOUTH);

    }

    /**
     * listázzuk a megfelelő mapek neveit, és ehhez szükséges ezeknek a betöltése egy fileból
     * ezt valósítjuk meg ebben a metódusban
     */
    private void loadList() {
        File mainDirectory = new File(System.getProperty("user.dir") + "\\Maps\\Main_config");
        File[] mapsFolder = mainDirectory.listFiles();
        for (File f : mapsFolder) {
            dataList.addElement(f.getName());
        }
    }

    /**
     * A listából az elem kiváélasztását kezelő metódus
     * a kiválasztott map névhez beolvassa a játékot
     * inicializálja a mappot
     * majd elindítja a játékot
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (jlist.getSelectedValue() != null) {
            try {
                File mainDirectory = new File(System.getProperty("user.dir") + "\\Maps");
                inputHandler.load(mainDirectory.getAbsolutePath() + "\\" + jlist.getSelectedValue());
                inputHandler.getGc().startGame();
                setVisible(false);
                dispose();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}

