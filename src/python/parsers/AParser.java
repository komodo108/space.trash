package python.parsers;

public abstract class AParser implements IParser {
    @Override
    public String genRegex(String word) {
        StringBuilder sb = new StringBuilder();
        for(char c : word.toCharArray()) {
            sb.append("[" + Character.toLowerCase(c) + Character.toUpperCase(c) + "]");
        } return sb.toString();
    }
}
