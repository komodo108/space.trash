import main.Main;
import processing.core.PApplet;

public class Start extends PApplet {
    public static void main(String[] args) {
        String[] pArgs = { "main.Main" };
        Main sketch = new Main();
        PApplet.runSketch(pArgs, sketch);
    }
}
