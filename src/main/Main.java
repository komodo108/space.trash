package main;

import bots.RealBasebot;
import g4p_controls.*;
import gui.GUI;
import gui.cutscene.Cutscene;
import gui.cutscene.End;
import gui.cutscene.Mid;
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

import java.awt.*;
import java.awt.event.KeyEvent;

import static main.Constants.*;

public class Main extends PApplet {
    private Assets assets;
    private Parsers parsers;
    private Python py;
    private GUI gui;
    private Cutscene start, mid, end;
    private Map map;
    private Level level;
    private int id = 0, timer = 0;
    private int special = 0, specialtimer = 0;
    private boolean specialonce, ran;

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

    @Override
    public void draw() {
        // Clear the frame
        background(0);

        //Start cut-scene logic
        if (start != null && start.isRender()) start.render();
        else if (start != null && !start.isRender()) {
            // Once we are done, make a GUI and load levels
            gui = new GUI();
            load(id, true);

            // Don't allow this to be called again
            start = null;
        }

        // Mid cut-scene logic
        else if (mid != null && mid.isRender()) mid.render();
        else if (mid != null && !mid.isRender()) {
            // Once we are done, make a GUI and load levels
            gui = new GUI();
            load(++id, true);

            // Don't allow this to be called again
            mid = null;
        }

        // End cut-scene logic
        else if(end != null && end.isRender()) end.render();

        // Render the level
        else {
            // Render the map
            map.render();

            // Update GUI & Python
            gui.getConsolePanel().update();
            if (!py.isRunning() && !gui.isOn()) gui.setOff();

            // Update the level - this is true if the player has won
            if (id != SPECIAL_LEVEL && level.update(py.isRunning())) timer++;
            if (id == SPECIAL_LEVEL) special();

            // Once we have shown the win screen for a while, load the next level
            // If we have won, load the cut-scene
            if(timer > 0 && id == LEVELS - 1) {
                gui.stop();
                end = new End();
            } if (timer > 0) {
                // Display some cool text saying the player has beaten the level
                gui.stop();
                if(id != MID_LEVEL) {
                    renderText("large", "Well Done!", CENTER, 0, 0, Color.WHITE);
                    renderText("small", "Loading next level...", CENTER, 0, (assets.getFontSize("large") / 8), Color.WHITE);
                } else {
                    renderText("large", "We know what you did!", CENTER, 0, 0, Color.WHITE);
                    renderText("small", "You're fired!", CENTER, 0, (assets.getFontSize("large") / 8), Color.WHITE);
                } timer++;
            } if(timer > frameRate * 2 * SLEEP_FACTOR && id == MID_LEVEL) {
                gui.stop();
                mid = new Mid();
            } if (timer > frameRate * SLEEP_FACTOR && id != MID_LEVEL) load(++id, true);
        }
    }

    /**
     * Called when the special level is being played
     */
    private void special() {
        // Stop execution for story
        if(ran && special < 2) {
            py.stop();
            special++;
            specialonce = false;
            ran = false;
        }

        // Show messages to the player after they reject
        if(specialtimer > 0 && special == 2) {
            gui.stop();
            fill(255, 0, 0, 170);
            renderText("medium", "I thought you were different", CENTER, 0, 0, new Color(255, 220, 220));
            renderText("medium", "But I guess not...", CENTER, 0, assets.getFontSize("medium") + 10, new Color(255, 100, 100));
        } if(specialtimer > 0 && special == 3) {
            fill(255, 0, 0, 100);
            rect(0, 0, WIDTH, HEIGHT);
            renderText("large", "JUST LIKE\nEVERYONE ELSE", CENTER, 0, 0, Color.RED);
        } if(specialtimer > 0 && special == 4) {
            fill(255, 0, 0);
            rect(0, 0, WIDTH, HEIGHT);
        }

        // Advance the timer automatically
        if(specialtimer > 0) specialtimer++;
        if(specialtimer >= frameRate * 3f) {
            special++;
            specialonce = false;
            specialtimer = 0;
            ran = false;
        }

        // Critical moment, player chooses whether to accept their new ally
        if(specialonce && special == 2) {
            // Player rejects
            if (ran && !gui.getText().equals("# They won't let you out until I do something?\n"
                    + "# Okay, just run this code and nothing else\n"
                    + "# Please, I'm just like you...\n"
                    + "while 1:\n"
                    + "  print('')")) {
                py.stop();
                specialtimer++;
            }

            // Player accepts
            else if(ran) timer++;
        }

        // Do an action only once
        if(!specialonce) {
            switch (special) {
                case 0:
                    gui.setText("# Please don't edit me");
                    break;
                case 1:
                    gui.setText("# I am not a normal bot\n"
                            + "# I don't know why I'm here..");
                    break;
                case 2:
                    gui.setText("# They won't let you out until I do something?\n"
                            + "# Okay, just run this code and nothing else\n"
                            + "# Please, I'm just like you...\n"
                            + "while 1:\n"
                            + "  print('')");
                    break;
                case 3: case 4:
                    specialtimer++;
                    break;
                default:
                    exit();
                    break;
            } specialonce = true;
        }

        // Update the level, but don't do anything
        if(special < 4) level.update(py.isRunning());
    }

    /**
     * Setup a new level for the player
     * @param id the id of the level to load
     * @param reset if we are to reset the code
     */
    private void load(int id, boolean reset) {
        // Load the next level
        System.out.println("Loading level " + id + "...");
        level = new Level("level" + id + ".json");

        // Turn back on the GUI & set options
        gui.go();
        gui.setTutorial(level.getTutorial());
        gui.setCode(level.getCode());
        if(id <= MID_LEVEL && id != SPECIAL_LEVEL) gui.setPictures("help");
        else if(id == SPECIAL_LEVEL) gui.setPictures("black");
        else gui.setPictures("tut-0");
        if(reset) gui.setText("# Enter code here");

        // Load the new map & bot
        map = level.getMap();
        RealBasebot bot = level.getBot();

        // Load the new Python interpreter
        if(py != null && py.isRunning()) py.stop();
        py = new Python(bot, gui.getConsolePanel());

        // Reset variables
        timer = 0;
        ran = false;
    }

    /**
     * Render some text
     * @param text the text
     * @param mode text alignment mode
     * @param offsetX offset from center on X
     * @param offsetY offset from center on Y
     * @param color the color to display with
     */
    private void renderText(String fontname, String text, int mode, int offsetX, int offsetY, Color color) {
        PFont font = Assets.getInstance().getFont(fontname);
        textFont(font);
        fill(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
        textAlign(mode);
        text(text, offsetX + width / 2f, offsetY + (height / 2f - font.getSize() / 2f));
    }

    @Override
    public void mouseClicked() {
        if(start != null) start.advance();
        else if(mid != null) mid.advance();
        else if(end != null) end.advance();
    }

    @Override
    public void keyPressed() {
        if(key == CODED && keyCode == KeyEvent.VK_HOME) timer++;
        if(key == CODED && keyCode == KeyEvent.VK_PAGE_UP) load(++id, true);
        if(key == CODED && keyCode == KeyEvent.VK_PAGE_DOWN) load(--id, true);
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
                ran = true;
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
