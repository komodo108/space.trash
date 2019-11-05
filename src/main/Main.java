package main;

import bots.Basebot;
import g4p_controls.*;
import gui.CodeEditor;
import org.python.core.PyInteger;
import org.python.util.PythonInterpreter;
import processing.core.PApplet;
import python.Python;
import python.parsers.IParser;
import python.parsers.Parsers;
import java.util.HashMap;

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
        py.run("from java.lang import System", parsers.get("none"));
        py.run("import bots.Basebot", parsers.get("none"));
        py.run("bot = bots.Basebot(4)", parsers.get("none"));
        py.run("print(bot.getValue())", parsers.get("none"));
        bot = (Basebot) py.get("bot").__tojava__(Basebot.class);
    }

    @Override
    public void draw() {
        background(0);
        fill(255);
        if(test) rect(0, 0, 100, 100);
        else py.run(editor.getText(), parsers.get("standard"));
    }

    @Override
    public void mouseReleased() {
        test = !test;
    }

    @Override
    public void keyReleased() {

    }
}
