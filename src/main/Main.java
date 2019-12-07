package main;

import bots.Basebot;
import g4p_controls.GButton;
import g4p_controls.GEditableTextControl;
import g4p_controls.GEvent;
import g4p_controls.GPanel;
import gui.ConsolePanel;
import gui.GUI;
import level.Level;
import level.map.Map;
import processing.Assets;
import processing.core.PApplet;
import processing.core.PFont;
import python.main.Python;
import python.parsers.Parsers;

import static main.Constants.*;

public class Main extends PApplet {
    private Assets assets;
    private Parsers parsers;
    private Python py;
    private GUI gui;
    private Map map;
    private Level level;
    private int id = 0;
    private int timer = 0;

    @Override
    public void settings() {
        size(WIDTH, HEIGHT);
    }

    @Override
    public void setup() {
        // Setup window options
        surface.setTitle(NAME);
        surface.setCursor(CROSS);

        // Setup the applet, asset manager, GUI & parsers
        AppletSingleton.getInstance().setApplet(this);
        assets = Assets.getInstance();
        gui = new GUI();
        parsers = new Parsers();

        // Load the initial level
        load(id);
    }

    @Override
    public void draw() {
        // Draw the map
        background(0);
        map.render();

        // Update GUI & Python
        gui.getConsolePanel().update();
        if (!py.isRunning() && !gui.isOn()) gui.setOff();
        // TODO: Maybe when the player fails to beat a level, show a message to try again

        // Update the level - this is true if the player has won
        if (level.update(py.isRunning())) timer++;

        // Once we have shown the win screen for a while, load the next level
        if(timer > 0) {
            // Display some cool text saying the player has beaten the level
            renderText("large", "Well Done!", CENTER, 0, 0, 255);
            renderText("small", "Loading next level...", CENTER, 0, (assets.getFontSize("large") / 8), 255);
            timer++;
        } if(timer > frameRate * 4) load(++id);
    }

    /**
     * Setup a new level for the player
     * @param id the id of the level to load
     */
    private void load(int id) {
        System.out.println("Loading level " + id + "...");
        level = new Level("level.json");
        map = level.getMap();
        Basebot bot = level.getBot();
        py = new Python(bot, gui.getConsolePanel());
        timer = 0;
    }

    /**
     * Render some text
     * @param text the text
     * @param mode text alignment mode
     * @param offsetX offset from center on X
     * @param offsetY offset from center on Y
     * @param colour the color to display with
     */
    private void renderText(String fontname, String text, int mode, int offsetX, int offsetY, int colour) {
        PFont font = Assets.getInstance().getFont(fontname);
        textFont(font);
        fill(colour);
        textAlign(mode);
        text(text, offsetX + width / 2f, offsetY + (height / 2f - font.getSize() / 2f));
    }

    @SuppressWarnings("unused")
    public void handleButtonEvents(GButton button, GEvent event) {
        switch (button.getText()) {
            case "Go":
                // Upon pressing Go, restart the level to normal
                load(id);

                // Then start Python execution
                gui.setOn();
                py.setup(gui.getText(), parsers.get("standard"));
                py.start();
                break;
            case "Stop":
                // When pressing Stop, stop execution
                gui.setOff();
                py.stop();
                break;
        }
    }

    @SuppressWarnings("unused")
    public void handlePanelEvents(GPanel panel, GEvent event) {
        if(panel.getText().equals("Console") && event == GEvent.EXPANDED) {
            ConsolePanel console = (ConsolePanel) panel;
            console.updated();
        }
    }

    @SuppressWarnings("unused")
    public void handleTextEvents(GEditableTextControl text, GEvent event) { /* no content - keep G4P quiet */ }
}
