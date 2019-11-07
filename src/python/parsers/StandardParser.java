package python.parsers;

public class StandardParser implements IParser {
    @Override
    public String parse(String code) {
        String[] lines = code.split("\n");
        int linenum = 0;

        for(String line : lines) {
            line = line.toLowerCase();
            if(line.contains("import")) return "Import statement on line " + linenum + " is not allowed.";
            else if(line.contains("pdb")) return "Use of 'pdb' on line " + linenum + " is forbidden.";
            else if(line.contains("open")) return "Cannot use 'open' on line " + linenum + ",";
            else if(line.contains("raw_input")) return "Input is not allowed on line " + linenum + ".";
            linenum++;
        } return null;
    }

    @Override
    public String change(String code) {
        String newcode = code;
        newcode = newcode.replace("\"", "\'");
        newcode = newcode.replace("\n", "\\n");
        return newcode;
    }
}
