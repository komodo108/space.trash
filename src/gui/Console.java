package gui;

import g4p_controls.G4P;
import g4p_controls.GConstants;
import g4p_controls.GPanel;
import g4p_controls.GTextArea;
import main.AppletSingleton;
import processing.core.PApplet;
import python.middleware.ActionQueue;
import python.middleware.ActionString;
import python.middleware.PythonImplementation;
import python.middleware.PythonInteractable;

import java.awt.*;

import static main.Constants.*;

public class Console implements PythonInteractable {
    private GPanel panel;
    private GTextArea area;
    private PApplet applet = AppletSingleton.getInstance().getApplet();
    private IConsole implementation;

    public Console() {
        implementation = new IConsole();
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

    @Override
    public void update() {
        ActionQueue queue = implementation.getQueue(KEY);
        while(queue.peek() != null) {
            ActionString as = queue.remove();
            area.appendText(as.message);
            if(area.getText().split("\n").length > MAX_LENGTH) {
                String[] text = area.getTextAsArray();
                String[] newtext = new String[NEW_LENGTH];
                System.arraycopy(text, MAX_LENGTH - NEW_LENGTH, newtext, 0, NEW_LENGTH);
                area.setText(newtext);

            } switch (as.action) {
                case ERROR:
                    area.addStyle(G4P.FOREGROUND, Color.RED, area.getText().split("\n").length - 1, 0, 255);
                    break;
                case PRINT: case HELP:
                    area.addStyle(G4P.FOREGROUND, Color.BLACK, area.getText().split("\n").length - 1, 0, 255);
                    break;
            }
        }
    }

    public void error(String error) {
        implementation.error(error);
    }

    @Override
    public PythonImplementation getImplementation() {
        return implementation;
    }
}
