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
import processing.core.PApplet;
import python.main.Python;
import python.parsers.Parsers;

import static main.Constants.*;

public class Main extends PApplet {
    private Parsers parsers;
    private Python py;

    private GUI gui;
    private Map map;
    private Level level;

    @Override
    public void settings() {
        size(WIDTH, HEIGHT);
    }

    @Override
    public void setup() {
        // Setup window options
        surface.setTitle(NAME);
        surface.setCursor(CROSS);

        // Setup the applet & gui
        AppletSingleton.getInstance().setApplet(this);
        gui = new GUI();

        // Setup the level
        level = new Level("level.json");
        map = level.getMap();
        Basebot bot = level.getBot();

        // Setup Python
        py = new Python(bot, gui.getConsolePanel());
        parsers = new Parsers();
    }

    @Override
    public void draw() {
        // Draw the map
        background(0);
        map.render();

        // Update GUI & Python
        gui.getConsolePanel().update();
        if(!py.isRunning() && !gui.isOn()) gui.setOff();

        // Update the level
        if(level.update(py.isRunning())) System.out.println("beep level over yay"); // next level
    }

    @SuppressWarnings("unused")
    public void handleButtonEvents(GButton button, GEvent event) {
        switch (button.getText()) {
            case "Go":
                gui.setOn();
                py.setup(gui.getText(), parsers.get("standard"));
                py.start();
                break;
            case "Stop":
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
