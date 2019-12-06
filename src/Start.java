import main.Main;
import processing.core.PApplet;

public class Start extends PApplet {
    public static void main(String[] args) {
        System.out.println("Space.trash: A programming puzzle game - http://pa.ul.ms/");
        String[] pArgs = { "main.Main" };
        Main sketch = new Main();
        PApplet.runSketch(pArgs, sketch);
    }
}
