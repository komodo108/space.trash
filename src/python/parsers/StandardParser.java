package python.parsers;

public class StandardParser extends AParser implements IParser {
    @Override
    public String parse(String code) {
        String[] lines = code.split("\n");
        int lineno = 0;

        for(String line : lines) {
            line = line.toLowerCase();

            // Disable things from builtins (KEY should be one of these)
            if(line.contains("import")) return "Import on line " + lineno + " is not allowed.";
            if(line.contains("file")) return "Cannot use 'file' on line " + lineno + ".";
            if(line.contains("open")) return "Cannot use 'open' on line " + lineno + ".";
            if(line.contains("input")) return "Input is not allowed on line " + lineno + ".";
            if(line.contains("breakpoint")) return "Breakpoints are forbidden on line " + lineno + ".";
            if(line.contains("exec")) return "The use of 'exec' is forbidden on line " + lineno + ".";

            // Disable access to set variables
            if(line.contains("badconsole")) return "Use of 'console' is not allowed on line " + lineno + ".";
            if(line.contains("py.") || line.contains("py ")) return "Cannot refer to 'Py' on line " + lineno + ".";
            if(line.contains("python.main.PythonTraceFunction")) return "Use of 'python.main.PythonTraceFunction' is not allowed on line " + lineno + ".";
            if(line.contains("ptf")) return "Illegal use of 'ptf' on line " + lineno + ".";
            if(line.contains("__ibot")) return "Illegal use of '__ibot' on line " + lineno + ".";
            if(line.contains("__impl")) return "Cannot refer to '__impl' on line " + lineno + ".";
            lineno++;
        } return null;
    }
}
