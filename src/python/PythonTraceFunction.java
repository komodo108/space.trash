package python;

import org.python.core.PyException;
import org.python.core.PyFrame;
import org.python.core.PyObject;
import org.python.core.TraceFunction;

public class PythonTraceFunction extends TraceFunction {
    private Python python;

    /**
     * The trace function for multi-threaded execution
     * @param python a python manager
     */
    PythonTraceFunction(Python python) {
        this.python = python;
    }

    @Override
    public TraceFunction traceCall(PyFrame pyFrame) {
        sleep(10);
        if(python.abort) throw new PythonStopException();
        return this;
    }

    @Override
    public TraceFunction traceReturn(PyFrame pyFrame, PyObject pyObject) {
        sleep(10);
        if(python.abort) throw new PythonStopException();
        return this;
    }

    @Override
    public TraceFunction traceLine(PyFrame pyFrame, int i) {
        sleep(50);
        if(python.abort) throw new PythonStopException();
        return this;
    }

    @Override
    public TraceFunction traceException(PyFrame pyFrame, PyException e) {
        sleep(10);
        if(python.abort) throw new PythonStopException();
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
