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
}
