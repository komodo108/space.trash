package python;

import org.python.core.PyException;
import org.python.core.PyFrame;
import org.python.core.PyObject;
import org.python.core.TraceFunction;

public class PythonTraceFunction extends TraceFunction {
    // FIXME: Not working
    @Override
    public TraceFunction traceCall(PyFrame pyFrame) {
        System.out.println("test1");
        return this;
    }

    @Override
    public TraceFunction traceReturn(PyFrame pyFrame, PyObject pyObject) {
        System.out.println("test2");
        return this;
    }

    @Override
    public TraceFunction traceLine(PyFrame pyFrame, int i) {
        System.out.println("test3");
        return this;
    }

    @Override
    public TraceFunction traceException(PyFrame pyFrame, PyException e) {
        System.out.println("test4");
        return this;
    }
}
