package gui;

import g4p_controls.*;
import main.AppletSingleton;
import processing.core.PApplet;

import static main.Constants.*;

/**
 * A Class for the Code Editor GUI Panel using G4P
 */
public class CodeEditor {
    private GPanel panel;
    private GTextArea area;
    private PApplet applet = AppletSingleton.getInstance().getApplet();

    public CodeEditor() {
        // Setup new colour theme
        GCScheme.makeColorSchemes(applet);
        GCScheme.copyPalette(GConstants.ORANGE_SCHEME, 8);
        for(int i = 0; i < 7; i++) {
            GCScheme.changePaletteColor(8, i, applet.color((255 * i / 7)));
        } GCScheme.changePaletteColor(8, 11, applet.color(0));

        // Setup panel
        panel = new GPanel(applet, WIDTH - EDITOR_WIDTH - 10, 10, EDITOR_WIDTH, EDITOR_HEIGHT);
        panel.setText("Code Window");
        panel.setLocalColorScheme(8);
        panel.setOpaque(true);

        // Setup text area
        area = new GTextArea(applet, 10, 25, EDITOR_WIDTH - 20, EDITOR_HEIGHT - 35, G4P.SCROLLBARS_VERTICAL_ONLY | G4P.SCROLLBARS_AUTOHIDE);
        area.setText("# Enter Code Here.");
        area.setLocalColorScheme(8);
        area.setOpaque(true);
        panel.addControl(area);
    }

    public String getText() {
        return area.getText();
    }
}