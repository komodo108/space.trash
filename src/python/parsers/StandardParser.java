package python.parsers;

public class StandardParser extends AParser implements IParser {
    @Override
    public String parse(String code) {
        String[] lines = code.split("\n");
        int linenum = 0;

        for(String line : lines) {
            line = line.toLowerCase();

            // Disable things from builtins
            if(line.contains("import")) return "Import on line " + linenum + " is not allowed.";
            if(line.contains("file")) return "Cannot use 'file' on line " + linenum + ".";
            if(line.contains("open")) return "Cannot use 'open' on line " + linenum + ".";
            if(line.contains("input")) return "Input is not allowed on line " + linenum + ".";
            if(line.contains("breakpoint")) return "Breakpoints are forbidden on line " + linenum + ".";
            if(line.contains("exec")) return "The use of 'exec' is forbidden on line " + linenum + ".";

            // Disable access to set variables
            if(line.contains("badconsole")) return "Use of 'console' is not allowed on line " + linenum + ".";
            if(line.contains("py.") || line.contains("py ")) return "Cannot refer to 'Py' on line " + linenum + ".";
            if(line.contains("python.PythonTraceFunction")) return "Use of 'python.PythonTraceFunction' is not allowed on line " + linenum + ".";
            if(line.contains("ptf")) return "Illegal use of 'ptf' on line " + linenum + ".";
            linenum++;
        } return null;
    }

    /**
     * Change the code to perform with our execution
     * For this instance, we need to change " to ', and replace all \n with \\n so pdb won't complain
     * Then we need to remove all instances of console, replace print and help with calls to console.whatever
     * @param code the code
     * @return changed code
     */
    @Override
    public String change(String code) {
        String newcode = code;
        newcode = newcode.replace("\"", "\'");

        newcode = newcode.replaceAll(genRegex("console"), "badconsole");
        newcode = newcode.replaceAll(genRegex("print("), "console.print(");
        newcode = newcode.replaceAll(genRegex("help()"), "console.help()");
        return newcode;
    }
}
