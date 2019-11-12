package python.parsers;

public class NoneParser extends AParser implements IParser {
    @Override
    public String parse(String code) {
        return null;
    }

    @Override
    public String change(String code) {
        return code;
    }
}
