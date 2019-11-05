package python.parsers;

public class StandardParser implements IParser {
    @Override
    public String parse(String code) {
        String[] lines = code.split("\n");
        int linenum = 0;

        for(String line : lines) {
            if(line.contains("import")) return "Cannot use import statement on line " + linenum;
            linenum++;
        } return null;
    }
}
