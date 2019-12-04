package gui;

import common.AppletSingleton;
import g4p_controls.GButton;
import g4p_controls.GCScheme;
import g4p_controls.GConstants;
import processing.core.PApplet;

import static common.Constants.*;

/**
 * A Class for the Code Editor GUI Panel using G4P
 */
public class GUI {
    private EditorPanel editorPanel;
    private ConsolePanel consolePanel;
    private GButton go;
    private PApplet applet = AppletSingleton.getInstance().getApplet();

    public GUI() {
        // Setup new colour theme
        GCScheme.makeColorSchemes(applet);
        GCScheme.copyPalette(GConstants.ORANGE_SCHEME, 8);
        for(int i = 0; i < 7; i++) {
            GCScheme.changePaletteColor(8, i, applet.color((170 * i / 7)));
        } GCScheme.changePaletteColor(8, 11, applet.color(0));

        // Setup panel
        editorPanel = new EditorPanel(applet, WIDTH - EDITOR_WIDTH - 10, 10, EDITOR_WIDTH, EDITOR_HEIGHT);
        consolePanel = new ConsolePanel(applet, editorPanel);

        // Setup buttons
        go = new GButton(applet, 10, EDITOR_HEIGHT - 45, EDITOR_WIDTH - 20, 40);
        go.setText("Go");
        go.setLocalColorScheme(GConstants.GREEN_SCHEME);
        go.setOpaque(true);
        editorPanel.addControl(go);
    }

    public String getText() {
        return editorPanel.getEditor().getText();
    }

    public boolean isOn() {
        return go.getText().equals("Go");
    }

    public void setOff() {
        go.setText("Go");
        go.setLocalColorScheme(GConstants.GREEN_SCHEME);
    }

    public void setOn() {
        go.setText("Stop");
        go.setLocalColorScheme(GConstants.RED_SCHEME);
    }

    public ConsolePanel getConsolePanel() {
        return consolePanel;
    }
}
