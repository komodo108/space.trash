package gui;

import java.util.ArrayList;
import java.util.List;

public class Tutorial {
    private List<String> text;
    private int selected;

    public Tutorial() {
        text = new ArrayList<>();
        selected = 0;
    }

    public void addText(String text) {
        this.text.add(text);
    }

    public String get() {
        selected %= text.size();
        return text.get(selected++);
    }
}
