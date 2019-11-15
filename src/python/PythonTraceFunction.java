package python;

import org.python.core.PyException;
import org.python.core.PyFrame;
import org.python.core.PyObject;
import org.python.core.TraceFunction;

public class PythonTraceFunction extends TraceFunction {
    // TODO: Update the thread so we can be interrupted without breaking the JVM
    @Override
    public TraceFunction traceCall(PyFrame pyFrame) {
        System.out.println("test1");
        sleep(100);
        return this;
    }

    @Override
    public TraceFunction traceReturn(PyFrame pyFrame, PyObject pyObject) {
        System.out.println("test2");
        sleep(100);
        return this;
    }

    @Override
    public TraceFunction traceLine(PyFrame pyFrame, int i) {
        System.out.println("test3");
        sleep(100);
        return this;
    }

    @Override
    public TraceFunction traceException(PyFrame pyFrame, PyException e) {
        System.out.println("test4");
        sleep(100);
        return this;
    }

    /**
     * Sleep for a given time
     * @param millis the time, in milliseconds
     */
    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (Exception e) { /* Do nothing */ }
    }
}
