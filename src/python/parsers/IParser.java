package python.parsers;

public interface IParser {

    /**
     * Gets a user-readable error string for why a piece of code couldn't be executed
     * @param code the code
     * @return null, or a user-readable error (e.g. "cannot use import on line 4")
     */
    String parse(String code);

    /**
     * Changes or removes lines from the input
     * @param code the code
     * @return the edited code
     */
    String change(String code);

    /**
     * Generate regex matching all variations of a given word (e.g. "con" -> "cOn", "CoN")
     * @param word a string
     * @return regex which matches all variations of it
     */
    String genRegex(String word);
}
