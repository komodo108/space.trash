package main;

import bots.Basebot;
import g4p_controls.GButton;
import g4p_controls.GEvent;
import g4p_controls.GPanel;
import gui.ConsolePanel;
import gui.GUI;
import processing.core.PApplet;
import python.main.Python;
import python.parsers.Parsers;

import static main.Constants.*;

public class Main extends PApplet {
    private Parsers parsers;
    private Python py;
    private GUI gui;
    private Basebot bot;

    @Override
    public void settings() {
        size(WIDTH, HEIGHT);
    }

    @Override
    public void setup() {
        // Setup window options
        surface.setTitle(NAME);
        surface.setCursor(CROSS);

        // Setup an applet
        AppletSingleton.getInstance().setApplet(this);

        // Setup components
        gui = new GUI();
        bot = new Basebot();
        py = new Python(bot, gui.getConsolePanel());
        parsers = new Parsers();
    }

    @Override
    public void draw() {
        background(0);

        gui.getConsolePanel().update();

        bot.update();
        bot.render();

        if(!py.isRunning() && !gui.isOn()) gui.setOff();
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
}
