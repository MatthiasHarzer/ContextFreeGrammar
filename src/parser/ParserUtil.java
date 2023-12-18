package parser;

/**
 * A utility class for parsers.
 *
 * @author Matthias Harzer
 */
public final class ParserUtil {
    /**
     * Splits a string with the given separator, without interpreting the separator as a regex.
     * @param string the string to split
     * @param separator the separator to split the string with
     * @return the split string
     */
    public static String[] split(String string, String separator) {
        return string.split("\\Q" + separator + "\\E");
    }
}
