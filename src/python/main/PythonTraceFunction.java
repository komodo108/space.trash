package python.main;

import org.python.core.PyException;
import org.python.core.PyFrame;
import org.python.core.PyObject;
import org.python.core.TraceFunction;

import static main.Constants.LONG_SLEEP_TIME;
import static main.Constants.SHORT_SLEEP_TIME;

public class PythonTraceFunction extends TraceFunction {
    private PythonAbortSingleton abort = PythonAbortSingleton.getInstance();

    /**
     * The trace function for multi-threaded execution
     */
    PythonTraceFunction() { }

    /*
        Thank you @tkohn for explaining how to kill the thread without killing Python executions!
        https://bugs.jython.org/issue2530 <3

        As they explain, instead of using Thread.interrupt(), we use this Trace Function to see if
        an abort flag has been set. If the flag is set, then we throw an Exception to kill Python,
        catch the Exception within the thread and then the thread will stop itself.

        Also note, that working on complex interactions with Jython is non-trivial.
        Hope you enjoy reading old documentation :^).
     */

    @Override
    public TraceFunction traceCall(PyFrame pyFrame) {
        sleep(SHORT_SLEEP_TIME);
        if(abort.isAbort()) throw new PythonStopException();
        return this;
    }

    @Override
    public TraceFunction traceReturn(PyFrame pyFrame, PyObject pyObject) {
        sleep(SHORT_SLEEP_TIME);
        if(abort.isAbort()) throw new PythonStopException();
        return this;
    }

    @Override
    public TraceFunction traceLine(PyFrame pyFrame, int i) {
        sleep(LONG_SLEEP_TIME);
        if(abort.isAbort()) throw new PythonStopException();
        return this;
    }

    @Override
    public TraceFunction traceException(PyFrame pyFrame, PyException e) {
        sleep(SHORT_SLEEP_TIME);
        if(abort.isAbort()) throw new PythonStopException();
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
