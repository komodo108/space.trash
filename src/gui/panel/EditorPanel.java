package gui.panel;

import g4p_controls.*;
import gui.implementation.Tutorial;
import processing.core.PApplet;

import java.awt.*;

import static main.Constants.EDITOR_HEIGHT;
import static main.Constants.EDITOR_WIDTH;

public class EditorPanel extends GPanel {
    private GTextArea editor;
    private GImageButton button;
    private GLabel message;
    private Tutorial tutorial;
    private PApplet applet;

    public EditorPanel(PApplet applet, int x, int y, int width, int height) {
        super(applet, x, y, width, height);
        this.applet = applet;

        // Setup editor
        this.setText("Code Window");
        this.setLocalColorScheme(8);
        this.setOpaque(true);

        // Setup the button
        String[] files = { "images/tut-0.png", "images/tut-0.png", "images/tut-0.png" };
        button = new GImageButton(applet, 10, 25, files);
        button.tag = "TUT-0";
        this.addControl(button);

        // Setup the label
        message = new GLabel(applet, 79, 25, EDITOR_WIDTH - 20 - 69, 64);
        message.setText("");
        message.setFont(new Font("Arial", Font.PLAIN, 14));
        message.setLocalColorScheme(15);
        this.addControl(message);

        // Setup editor area
        editor = new GTextArea(applet, 10, 25 + 74, EDITOR_WIDTH - 20, EDITOR_HEIGHT - 154, G4P.SCROLLBARS_VERTICAL_ONLY | G4P.SCROLLBARS_AUTOHIDE);
        editor.setText("# Enter Code here");
        editor.setLocalColorScheme(8);
        editor.setOpaque(true);
        this.addControl(editor);
    }

    public void setTutorial(Tutorial tutorial) {
        this.tutorial = tutorial;
        message.setText(tutorial.get());
    }

    public GTextArea getEditor() {
        return editor;
    }

    public void setPictures(String file_loc) {
        String[] files = { file_loc, file_loc, file_loc };
        button = new GImageButton(applet, 10, 25, files);
        button.tag = "TUT-0";
        this.addControl(button);
    }

    public void buttonClick() {
        message.setText(tutorial.get());
    }
}
