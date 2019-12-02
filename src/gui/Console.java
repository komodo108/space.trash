package gui;

import g4p_controls.G4P;
import g4p_controls.GConstants;
import g4p_controls.GPanel;
import g4p_controls.GTextArea;
import main.AppletSingleton;
import processing.core.PApplet;

import java.awt.*;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import static main.Constants.*;

public class Console {
    private static class ColorString {
        enum Types {
            PRINT, ERROR
        }

        public String message;
        public Types type;

        ColorString(String message, Types type) {
            this.message = message;
            this.type = type;
        }
    }

    private GPanel panel;
    private GTextArea area;
    private PApplet applet = AppletSingleton.getInstance().getApplet();
    private Queue<ColorString> actions = new ConcurrentLinkedQueue<>();

    public Console() {
        panel = new GPanel(applet, 0, 0, CONSOLE_WIDTH, CONSOLE_HEIGHT);
        panel.setOpaque(true);
        panel.setLocalColorScheme(GConstants.PURPLE_SCHEME);
        panel.setText("Console");

        area = new GTextArea(applet, 10, 25, CONSOLE_WIDTH - 20, CONSOLE_HEIGHT - 35, G4P.SCROLLBARS_VERTICAL_ONLY | G4P.SCROLLBARS_AUTOHIDE);
        area.setOpaque(true);
        area.setLocalColorScheme(GConstants.PURPLE_SCHEME);
        area.setText("Python 2.7 for " + NAME + ".\n");
        area.setTextEditEnabled(false);
        panel.addControl(area);
    }

    public void update() {
        while(actions.peek() != null) {
            ColorString cs = actions.remove();
            area.appendText(cs.message);
            switch (cs.type) {
                case ERROR:
                    area.addStyle(G4P.FOREGROUND, Color.RED, area.getText().split("\n").length - 1, 0, 255);
                    break;
                default:
                    area.addStyle(G4P.FOREGROUND, Color.BLACK, area.getText().split("\n").length - 1, 0, 255);
                    break;
            }
        }
    }

    public void error(String message) {
        message = "ERROR > " + message + "\n";
        for(String line : message.split("\n")) {
            actions.add(new ColorString(line, ColorString.Types.ERROR));
        }
    }

    public void print(String message) {
        // FIXME: Possible Concurrent modification exceptions here and elsewhere
        message = "> " + message + "\n";
        for(String line : message.split("\n")) {
            actions.add(new ColorString(line, ColorString.Types.PRINT));
        }
    }

    public void help() {
        String message = "Help is available from http://help.net/" + "\n";
        for(String line : message.split("\n")) {
            actions.add(new ColorString(line, ColorString.Types.PRINT));
        }
    }
}
