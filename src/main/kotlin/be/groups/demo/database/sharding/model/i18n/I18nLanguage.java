package be.groups.demo.database.sharding.model.i18n;

import java.util.*;

/**
 * Enum representing the languages for {@link I18n} system
 *
 * @author michotte
 */
public enum I18nLanguage {

    /*
	 * Enum constants
	 */
    UNKNOWN(0, "UNKNOWN", "Unknown"),
    FR(1, "FR", "Français"),
    NL(2, "NL", "Nederlands"),
    DE(3, "DE", "Deutsch"),
    EN(4, "EN", "English"),
    X(5, "X", "Other");

    //region properties
    private int code;
    private String value;
    private String label;
    //endregion

    I18nLanguage(int code, String value, String label) {
        this.code = code;
        this.value = value;
        this.label = label;
    }

    //region Getters
    /**
     * Get the code
     *
     * @return the code
     */
    public int getCode() {
        return code;
    }

    /**
     * Get the value
     *
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * Get the label
     *
     * @return the label
     */
    public String getLabel() {
        return label;
    }
    //endregion

    //region Helper
    /**
     * {@link I18nLanguage} from an int value
     *
     * @param code {@link I18nLanguage} as an int value
     * @return The corresponding {@link I18nLanguage} if it exists
     */
    public static I18nLanguage codeFromInteger(int code) {

        for (I18nLanguage result : values()) {
            if (code == result.code) { return result; }
        }

        throw new NoSuchElementException(
            String.format("No language defined for the code %d", code));
    }

    /**
     * {@link I18nLanguage} from a String value
     *
     * @param value {@link I18nLanguage} as a String value
     * @return The corresponding {@link I18nLanguage} if it exists
     */
    public static I18nLanguage codeFromValue(String value) {

        for (I18nLanguage result : values()) {
            if (value.equals(result.value)) { return result; }
        }

        throw new NoSuchElementException(
            String.format("No language code defined for the value %s", value));
    }
    //endregion

    @Override public String toString() {
        return "I18nLanguage{" +
            "code=" + code +
            ", value='" + value + '\'' +
            ", label='" + label + '\'' +
            '}';
    }
}
