package python;

import org.python.core.PyException;
import org.python.core.PyFrame;
import org.python.core.PyObject;
import org.python.core.TraceFunction;

public class PythonTraceFunction extends TraceFunction {
    // TODO: Update the thread so we can be interrupted without breaking the JVM
    @Override
    public TraceFunction traceCall(PyFrame pyFrame) {
        sleep(100);
        return this;
    }

    @Override
    public TraceFunction traceReturn(PyFrame pyFrame, PyObject pyObject) {
        sleep(100);
        return this;
    }

    @Override
    public TraceFunction traceLine(PyFrame pyFrame, int i) {
        sleep(100);
        // Throw exception if abort flag is set
        return this;
    }

    @Override
    public TraceFunction traceException(PyFrame pyFrame, PyException e) {
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
