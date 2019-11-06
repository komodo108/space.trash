package main;

import bots.Basebot;
import g4p_controls.GButton;
import g4p_controls.GEvent;
import gui.CodeEditor;
import processing.core.PApplet;
import python.Python;
import python.parsers.Parsers;

import static main.Constants.*;

public class Main extends PApplet {
    private boolean test;
    private Parsers parsers;
    private Python py;
    private CodeEditor editor;
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
        py = new Python();
        parsers = new Parsers();

        // Setup the python interpreter
        py.runLine("from java.lang import System", parsers.get("none"));
        py.runLine("import pdb", parsers.get("none"));
        py.runLine("import bots.Basebot", parsers.get("none"));
        py.runLine("bot = bots.Basebot(4)", parsers.get("none"));
        py.runLine("print(bot.getValue())", parsers.get("none"));
        bot = (Basebot) py.get("bot").__tojava__(Basebot.class);
    }

    @Override
    public void draw() {
        background(0);
        fill(255);
        if(test) rect(0, 0, 100, 100);
        else rect(100, 100, 100, 100);

        if(py.isRunning()) py.step();
        if(!py.isRunning() && !editor.isOn()) editor.setOff();
    }

    public void handleButtonEvents(GButton button, GEvent event) {
        py.setup(editor.getText(), parsers.get("standard"));
        switch (button.getText()) {
            case "Go":
                editor.setOn();
                py.start();
                break;
            case "Stop":
                editor.setOff();
                py.stop();
                break;
            case "Step":
                py.step();
                break;
        }
    }

    @Override
    public void mouseReleased() {
        test = !test;
        if(mouseButton == RIGHT) System.out.println(bot.getValue());
    }

    @Override
    public void keyReleased() {

    }
}
