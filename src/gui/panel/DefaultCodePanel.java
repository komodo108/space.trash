package gui.panel;

import g4p_controls.G4P;
import g4p_controls.GPanel;
import g4p_controls.GTextArea;
import gui.implementation.DefaultCode;
import processing.Assets;
import processing.core.PApplet;

import static main.Constants.EDITOR_WIDTH;
import static main.Constants.UNEDIT_HEIGHT;

public class DefaultCodePanel extends GPanel {
    private GTextArea area;
    private DefaultCode code;

    public DefaultCodePanel(PApplet applet, GPanel panel) {
        super(applet, ("Code Window").length() * 9, 0, panel.getWidth(), UNEDIT_HEIGHT + 35);

        // Setup unedit
        this.setOpaque(true);
        this.setText("Default");
        this.setCollapsed(true);
        this.setDraggable(false);

        // Setup console text area
        area = new GTextArea(applet, 10, 25, EDITOR_WIDTH - 20, UNEDIT_HEIGHT, G4P.SCROLLBARS_VERTICAL_ONLY | G4P.SCROLLBARS_AUTOHIDE);
        area.setFont(Assets.getInstance().getCodeFont());
        area.setOpaque(true);
        area.setTextEditEnabled(false);
        area.setLocalColorScheme(RED_SCHEME);
        this.setLocalColorScheme(RED_SCHEME);
        this.addControl(area);

        // Add to editor
        panel.addControl(this);
    }

    public void setCode(DefaultCode code) {
        this.code = code;
        area.setText(code.getCode());
    }

    public String getCode() {
        return code.getCode();
    }
}
