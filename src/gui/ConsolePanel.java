package gui;

import g4p_controls.G4P;
import g4p_controls.GPanel;
import g4p_controls.GTextArea;
import processing.core.PApplet;
import python.middleware.ActionQueue;
import python.middleware.ActionString;
import python.middleware.PythonImplementation;
import python.middleware.PythonInteractable;

import java.awt.*;

import static main.Constants.*;

public class ConsolePanel extends GPanel implements PythonInteractable {
    private GTextArea console;
    private IConsole implementation;
    private int updated;
    private int time = 0;

    public ConsolePanel(PApplet applet, GPanel panel) {
        super(applet, ("Code Window").length() * 9, 0, panel.getWidth(), panel.getHeight() - 50);

        // Setup console
        implementation = new IConsole();
        this.setOpaque(true);
        this.setText("Console");
        this.setCollapsed(true);
        this.setDraggable(false);

        // Setup console text area
        console = new GTextArea(applet, 10, 25, EDITOR_WIDTH - 20, EDITOR_HEIGHT - 80, G4P.SCROLLBARS_VERTICAL_ONLY | G4P.SCROLLBARS_AUTOHIDE);
        console.setOpaque(true);
        console.setText("Python 2.7 for " + NAME + ".\n");
        console.setTextEditEnabled(false);
        setColorScheme(PURPLE_SCHEME);
        this.addControl(console);

        // Add to editor
        panel.addControl(this);
    }

    @Override
    public boolean update() {
        // Flash the panel if updated
        if(updated != 0) {
            time++;
            time %= CONSOLE_TIME;
            if(time < CONSOLE_TIME / 2) {
                setColorScheme(updated == 1 ? RED_SCHEME : GREEN_SCHEME);
            } else {
                setColorScheme(PURPLE_SCHEME);
            }
        }

        // Update the queue
        ActionQueue queue = implementation.getQueue(KEY);
        while(queue.peek() != null) {
            ActionString as = queue.remove();
            console.appendText(as.message);
            if(console.getText().split("\n").length > MAX_LENGTH) {
                String[] text = console.getTextAsArray();
                String[] newtext = new String[NEW_LENGTH];
                System.arraycopy(text, MAX_LENGTH - NEW_LENGTH, newtext, 0, NEW_LENGTH);
                console.setText(newtext);
            } switch (as.action) {
                case ERROR:
                    if(this.isCollapsed()) updated = 1;
                    console.addStyle(G4P.FOREGROUND, Color.RED, console.getText().split("\n").length - 1, 0, 255);
                    break;
                case PRINT: case HELP:
                    if(this.isCollapsed()) updated = 2;
                    console.addStyle(G4P.FOREGROUND, Color.BLACK, console.getText().split("\n").length - 1, 0, 255);
                    break;
            }
        } return false;
    }

    public void error(String error) {
        implementation.error(error);
    }

    private void setColorScheme(int scheme) {
        this.setLocalColorScheme(scheme);
        console.setLocalColorScheme(scheme);
    }

    public void updated() {
        updated = 0;
        time = 0;
        setColorScheme(PURPLE_SCHEME);
    }

    @Override
    public PythonImplementation getImplementation() {
        return implementation;
    }
}
