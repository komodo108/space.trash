package python;

import org.python.core.PyObject;
import org.python.util.PythonInterpreter;
import python.parsers.IParser;

/**
 * Safe access to a sandboxed python interpreter
 */
public class Python {
    private PythonInterpreter py;

    public Python() {
        try {
            py = new PythonInterpreter();
        } catch (Exception e) {
            // TODO: This should output to a console
            e.printStackTrace();
        }
    }

    public void run(String code, IParser parser) {
        try {
            String error = parser.parse(code);
            if(error == null) py.exec(code);
            else System.err.println(error);
        } catch (Exception e) {
            // TODO: This should output to a console
            e.printStackTrace();
        }
    }

    public PyObject get(String variable) {
        try {
            return py.get(variable);
        } catch (Exception e) {
            // TODO: This should output to a console
            e.printStackTrace();
        } return null;
    }

}
