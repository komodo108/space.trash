package python.parsers;

import java.util.HashMap;

/**
 * Wrapper for holding reference to different parsers
 */
public class Parsers {
    private HashMap<String, IParser> parsers;

    public Parsers() {
        parsers = new HashMap<>();

        // Use reflection to load all parsers
        // https://stackoverflow.com/questions/347248/how-can-i-get-a-list-of-all-the-implementations-of-an-interface-programmatically
        // https://stackoverflow.com/questions/6094575/creating-an-instance-using-the-class-name-and-calling-constructor
        Package[] packages = Package.getPackages();
        for (Package p : packages) {
            ParserAnnotation annotation = p.getAnnotation(ParserAnnotation.class);
            if (annotation != null) {
                Class<?>[]  implementations = annotation.implementations();
                for (Class<?> impl : implementations) {
                    try {
                        parsers.put(impl.getSimpleName().toLowerCase().replace("parser", ""), (IParser) impl.getConstructor().newInstance());
                    } catch (Exception e) { e.printStackTrace(); }
                }
            }
        }
    }

    /**
     * Get a parser
     * @param parser the name of the parser, in lowercase (e.g. "none" for NoneParser)
     * @return null, or an instance of that parser
     */
    public IParser get(String parser) {
        return parsers.get(parser);
    }

}
