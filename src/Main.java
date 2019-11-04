import org.python.core.PyInteger;
import org.python.util.PythonInterpreter;
import processing.core.PApplet;

public class Main extends PApplet {
    private boolean test;
    private PythonInterpreter pi;

    @Override
    public void settings() {
        // Set the size
        size(800, 600);
    }

    @Override
    public void setup() {
        // Setup window options
        surface.setTitle("Hi");
        surface.setCursor(CROSS);

        // Setup the python interpreter
        pi = new PythonInterpreter();
        pi.set("cool", new PyInteger(5));
        pi.exec("from java.lang import System");
        pi.exec("import Basebot");
        pi.exec("bot = Basebot(4)");
        pi.exec("print(bot.getValue())");
    }

    @Override
    public void draw() {
        background(0);
        fill(255);
        if(test) rect(0, 0, 100, 100);
        else rect(100, 100, 100, 100);
    }

    @Override
    public void mouseReleased() {
        test = !test;
        pi.exec("bot.setValue(bot.getValue() + 1)");
        pi.exec("print(bot.getValue())");
        Basebot bot = (Basebot) pi.get("bot").__tojava__(Basebot.class);
        bot.setValue(bot.getValue() + 1);
        System.out.println(bot.getValue());
    }

    @Override
    public void keyReleased() {

    }
}
