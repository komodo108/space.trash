package gui;

import g4p_controls.G4P;
import g4p_controls.GPanel;
import g4p_controls.GTextArea;
import processing.core.PApplet;

import static common.Constants.EDITOR_HEIGHT;
import static common.Constants.EDITOR_WIDTH;

public class EditorPanel extends GPanel {
    private GTextArea editor;

    public EditorPanel(PApplet applet, int x, int y, int width, int height) {
        super(applet, x, y, width, height);

        // Setup editor
        this.setText("Code Window");
        this.setLocalColorScheme(8);
        this.setOpaque(true);

        // Setup editor area
        editor = new GTextArea(applet, 10, 25, EDITOR_WIDTH - 20, EDITOR_HEIGHT - 80, G4P.SCROLLBARS_VERTICAL_ONLY | G4P.SCROLLBARS_AUTOHIDE);
        editor.setText("# Enter Code Here.");
        editor.setLocalColorScheme(8);
        editor.setOpaque(true);
        this.addControl(editor);
    }

    public GTextArea getEditor() {
        return editor;
    }
}
