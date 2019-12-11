package main;

import bots.RealBasebot;
import g4p_controls.*;
import gui.GUI;
import gui.cutscene.Cutscene;
import gui.cutscene.End;
import gui.cutscene.Start;
import gui.panel.ConsolePanel;
import gui.panel.EditorPanel;
import level.Level;
import level.map.Map;
import processing.Assets;
import processing.core.PApplet;
import processing.core.PFont;
import python.main.Python;
import python.parsers.Parsers;

import java.awt.event.KeyEvent;

import static main.Constants.*;

public class Main extends PApplet {
    private Assets assets;
    private Parsers parsers;
    private Python py;
    private GUI gui;
    private Cutscene start, end;
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
        parsers = new Parsers();

        // Make the cut-scenes which we set on at different points
        start = new Start();
    }

    // TODO: Fix level 4

    @Override
    public void draw() {
        // Clear the frame
        background(0);

        // Render cut-scenes
        if(start != null && start.isRender()) start.render();
        else if(start != null && !start.isRender()) {
            // Once we are done, make the GUI and load levels
            gui = new GUI();
            load(id, true);

            // Don't allow this to be called again
            start = null;
        } else if(end != null && end.isRender()) end.render();

        // Render the level
        else {
            // Render the map
            map.render();

            // Update GUI & Python
            gui.getConsolePanel().update();
            if (!py.isRunning() && !gui.isOn()) gui.setOff();
            // TODO: Maybe when the player fails to beat a level, show a message to try again

            // Update the level - this is true if the player has won
            if (level.update(py.isRunning())) timer++;

            // Once we have shown the win screen for a while, load the next level
            // If we have won, load the cut-scene
            if(timer > 0 && id == LEVELS - 1) {
                gui.stop();
                end = new End();
            } else if (timer > 0) {
                // Display some cool text saying the player has beaten the level
                renderText("large", "Well Done!", CENTER, 0, 0, 255);
                renderText("small", "Loading next level...", CENTER, 0, (assets.getFontSize("large") / 8), 255);
                timer++;
            } if (timer > frameRate * 4) load(++id, true);
        }
    }

    /**
     * Setup a new level for the player
     * @param id the id of the level to load
     * @param newlevel if we are to reset the code
     */
    private void load(int id, boolean newlevel) {
        System.out.println("Loading level " + id + "...");
        level = new Level("level" + id + ".json");
        gui.setTutorial(level.getTutorial());
        gui.setCode(level.getCode());
        if(newlevel) gui.setText("# Enter code here");

        map = level.getMap();
        RealBasebot bot = level.getBot();
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

    @Override
    public void mouseClicked() {
        if(start != null) start.advance();
        else if(end != null) end.advance();
    }

    @Override
    public void keyPressed() {
        if(key == CODED && keyCode == KeyEvent.VK_HOME) load(++id, true);
        if(key == CODED && keyCode == KeyEvent.VK_END) load(--id, true);
    }

    @SuppressWarnings("unused")
    public void handleButtonEvents(GButton button, GEvent event) {
        switch (button.getText()) {
            case "Go":
                // Upon pressing Go, restart the level to normal
                load(id, false);

                // Then start Python execution
                gui.setOn();
                py.setup(gui.getText(), gui.getDefaultCode(), parsers.get("standard"), parsers.get("level"));
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
    public void handleButtonEvents(GImageButton button, GEvent event) {
        if(button.tag.equals("TUT-0")) {
            EditorPanel panel = (EditorPanel) button.getParent();
            panel.buttonClick();
        }
    }

    @SuppressWarnings("unused")
    public void handleTextEvents(GEditableTextControl text, GEvent event) { /* no content - keep G4P quiet */ }
}
