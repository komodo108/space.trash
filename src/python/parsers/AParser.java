package python.parsers;

public abstract class AParser implements IParser {
    @Override
    public String genRegex(String word) {
        StringBuilder sb = new StringBuilder();
        for(char c : word.toCharArray()) {
            sb.append("[" + Character.toLowerCase(c) + Character.toUpperCase(c) + "]");
        } return sb.toString();
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
