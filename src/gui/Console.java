package gui;

import g4p_controls.G4P;
import g4p_controls.GConstants;
import g4p_controls.GPanel;
import g4p_controls.GTextArea;
import main.AppletSingleton;
import processing.core.PApplet;

import static main.Constants.*;

public class Console {
    private GPanel panel;
    private GTextArea area;
    private PApplet applet = AppletSingleton.getInstance().getApplet();

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

    public void error(String message) {
        // TODO: Color this
        area.appendText("ERR> " + message + "\n");
    }

    public void print(String message) {
        area.appendText("> " + message + "\n");
    }

    public void help() {
        area.appendText("Help is available from http://help.net/" + "\n");
    }
}
