package main;

import bots.Basebot;
import g4p_controls.GButton;
import g4p_controls.GEvent;
import gui.CodeEditor;
import gui.Console;
import processing.core.PApplet;
import python.main.Python;
import python.parsers.Parsers;

import static main.Constants.*;

public class Main extends PApplet {
    private boolean test;
    private Parsers parsers;
    private Python py;
    private CodeEditor editor;
    private Console console;
    private Basebot bot;

    @Override
    public void settings() {
        // Set the size
        size(WIDTH, HEIGHT);
    }

    @Override
    public void setup() {
        // Setup window options
        surface.setTitle(NAME);
        surface.setCursor(CROSS);

        // Setup an main.AppletSingleton
        AppletSingleton.getInstance().setApplet(this);

        // Setup components
        editor = new CodeEditor();
        console = new Console();
        bot = new Basebot(4);
        py = new Python(bot, console);
        parsers = new Parsers();
    }

    @Override
    public void draw() {
        background(0);
        fill(255);
        if(test) rect(0, 0, 100, 100);
        else rect(100, 100, 100, 100);

        console.update();
        bot.update();

        if(!py.isRunning() && !editor.isOn()) editor.setOff();
    }

    @SuppressWarnings("unused")
    public void handleButtonEvents(GButton button, GEvent event) {
        switch (button.getText()) {
            case "Go":
                editor.setOn();
                py.setup(editor.getText(), parsers.get("standard"));
                py.start();
                break;
            case "Stop":
                editor.setOff();
                py.stop();
                break;
        }
    }

    @Override
    public void mouseReleased() {
        test = !test;
        if(mouseButton == RIGHT) System.out.println(bot.getValue());
    }

    @Override
    public void keyReleased() { }
}
