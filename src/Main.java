import g4p_controls.*;
import org.python.core.PyInteger;
import org.python.util.PythonInterpreter;
import processing.core.PApplet;

public class Main extends PApplet {
    private boolean test;
    private GPanel panel;
    private GTextArea area;
    private PythonInterpreter pi;
    private Basebot bot;

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

        // Setup panel
        panel = new GPanel(this, 800 - 305 - 10, 10, 330, 580);
        panel.setText("Code Window");
        panel.setLocalColorScheme(GCScheme.PURPLE_SCHEME);
        panel.setOpaque(true);

        // Setup text area
        area = new GTextArea(this, 10, 30, 300, 550, G4P.SCROLLBARS_VERTICAL_ONLY);
        area.setText("# Enter Code Here.");
        area.setLocalColorScheme(GConstants.PURPLE_SCHEME);
        area.setOpaque(true);
        panel.addControl(area);

        // Setup an AppletSingleton
        AppletSingleton.getInstance().setApplet(this);

        // Setup the python interpreter
        pi = new PythonInterpreter();
        pi.set("cool", new PyInteger(5));
        pi.exec("from java.lang import System");
        pi.exec("import Basebot");
        pi.exec("bot = Basebot(4)");
        pi.exec("print(bot.getValue())");
        bot = (Basebot) pi.get("bot").__tojava__(Basebot.class);
    }

    @Override
    public void draw() {
        background(0);
        fill(255);
        if(test) rect(0, 0, 100, 100);
        else try {
            pi.exec(area.getText());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void mouseReleased() {
        test = !test;
        try {
            pi.exec(area.getText());
        } catch (Exception e) {
            e.printStackTrace();
        } System.out.println(bot.getValue());
    }

    @Override
    public void keyReleased() {

    }
}
