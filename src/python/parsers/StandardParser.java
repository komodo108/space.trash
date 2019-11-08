package python.parsers;

public class StandardParser implements IParser {
    @Override
    public String parse(String code) {
        code = code.replace("\\n", "\n");
        String[] lines = code.split("\n");
        int linenum = 0;

        for(String line : lines) {
            line = line.toLowerCase();
            if(line.contains("import")) return "Import on line " + linenum + " is not allowed.";
            if(line.contains("pdb")) return "Use of 'pdb' on line " + linenum + " is forbidden.";
            if(line.contains("badconsole")) return "Use of 'console' is not allowed on line " + linenum + ".";
            if(line.contains("file")) return "Cannot use 'file' on line " + linenum + ",";
            if(line.contains("open")) return "Cannot use 'open' on line " + linenum + ",";
            if(line.contains("input")) return "Input is not allowed on line " + linenum + ".";
            if(line.contains("breakpoint")) return "Breakpoints are forbidden on line " + linenum + ".";
            if(line.contains("exec")) return "The use of 'exec' is forbidden on line " + linenum + ".";
            linenum++;
        } return null;
    }

    @Override
    public String change(String code) {
        String newcode = code;
        newcode = newcode.replace("\"", "\'");
        newcode = newcode.replace("\n", "\\n");

        newcode = newcode.toLowerCase().replace("console", "badconsole");
        newcode = newcode.toLowerCase().replace("print(", "console.print(");
        newcode = newcode.toLowerCase().replace("help()", "console.help()");
        return newcode;
    }
}
