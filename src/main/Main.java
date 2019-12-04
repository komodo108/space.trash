package main;

import bots.Basebot;
import common.AppletSingleton;
import g4p_controls.GButton;
import g4p_controls.GEvent;
import g4p_controls.GPanel;
import gui.ConsolePanel;
import gui.GUI;
import level.item.Item;
import level.item.TestItem;
import processing.core.PApplet;
import python.main.Python;
import python.parsers.Parsers;

import java.util.ArrayList;

import static common.Constants.*;

public class Main extends PApplet {
    private Parsers parsers;
    private Python py;
    private GUI gui;
    private Basebot bot;

    private Item item;

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
        item = new TestItem(500, 500);

        // Setup Python
        py = new Python(bot, gui.getConsolePanel());
        parsers = new Parsers();
    }

    @Override
    public void draw() {
        background(0);
        gui.getConsolePanel().update();

        bot.update();
        bot.render();

        // All for testing
        if(!item.update()) {
            item.render(); // Remove the item if update fails

            ArrayList test = new ArrayList();
            test.add(bot);
            item.interactOthers(test);

            if(!py.isRunning() && !gui.isOn()) gui.setOff();
        }
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
