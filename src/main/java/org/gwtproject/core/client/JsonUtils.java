package org.gwtproject.core.client;


import elemental2.core.JSONType.StringifyReplacerUnionType;

import static elemental2.core.Global.JSON;

/**
 * Provides JSON-related utility methods.
 *
 * Deprecated: just use JSON.parse and JSON.stringify.
 */
@Deprecated
public class JsonUtils {

    /**
     * Converts a value to JSON.
     */
    public static String stringify(JavaScriptObject obj) {
        return JSON.stringify(obj);
    }

    /**
     * Converts a value to JSON.
     *
     * @param space controls the spacing in the final string. Successive levels in the stringification
     *        will each be indented by this string (or the first ten characters of it).
     */
    public static String stringify(JavaScriptObject obj, String space) {
        return JSON.stringify(obj, StringifyReplacerUnionType.of(null), space);
    }

    /**
     * Escapes characters within a JSON string than cannot be passed directly to
     * eval(). Control characters, quotes and backslashes are not affected.
     */
    public static String escapeJsonForEval(String toEscape) {
//        JsArray<JsString> escapeTable = getEscapeTable();
//        var s = toEscape.replace(/[\xad\u0600-\u0603\u06dd\u070f\u17b4\u17b5\u200b-\u200f\u2028-\u202e\u2060-\u2064\u206a-\u206f\ufeff\ufff9-\ufffb]/g, function(x) {
//            return @JsonUtils::escapeChar(*)(x, escapeTable);
//        });
//        return s;
        return toEscape;
    }

    /**
     * Returns a quoted, escaped JSON String.
     */
    public static String escapeValue(String toEscape) {
        return JSON.stringify(toEscape);
//        JsArray<JsString> escapeTable = getEscapeTable();
//        var s = toEscape.replace(/[\x00-\x1f\xad\u0600-\u0603\u06dd\u070f\u17b4\u17b5\u200b-\u200f\u2028-\u202e\u2060-\u2064\u206a-\u206f\ufeff\ufff9-\ufffb"\\]/g, function(x) {
//            return @JsonUtils::escapeChar(*)(x, escapeTable);
//        });
//      return "\"" + s + "\"";
}

    /**
     * Evaluates a JSON expression safely. The payload must evaluate to an Object
     * or an Array (not a primitive or a String).
     *
     * @param <T> The type of JavaScriptObject that should be returned
     * @param json The source JSON text
     * @return The evaluated object
     *
     * @throws IllegalArgumentException if the input is not valid JSON
     */
    public static <T extends JavaScriptObject> T safeEval(String json) {
        try {
            return (T) JSON.parse(json);
        } catch (Exception e) {
            throw new IllegalArgumentException(("Error parsing JSON: " + e) + "\n" + json);
        }
    }

    /**
     * Returns true if the given JSON string may be safely evaluated by {@code
     * eval()} without undesired side effects or security risks. Note that a true
     * result from this method does not guarantee that the input string is valid
     * JSON.  This method does not consider the contents of quoted strings; it
     * may still be necessary to perform escaping prior to evaluation for correct
     * results.
     * <p> The technique used is taken from <a href="http://www.ietf.org/rfc/rfc4627.txt">RFC 4627</a>.
     *
     * <p> Note that this function may fail in sufficiently large text in some
     * browsers (e.g. Chrome). It is always better to use {@link #safeEval}
     * instead which is safer, faster and also works with large texts but less
     * lenient than this one for invalid JSON.
     */
    public static boolean safeToEval(String text) {
        // Remove quoted strings and disallow anything except:
        //
        // 1) symbols and brackets ,:{}[]
        // 2) numbers: digits 0-9, ., -, +, e, and E
        // 3) literal values: 'null', 'true' and 'false' = [aeflnr-u]
        // 4) whitespace: ' ', '\n', '\r', and '\t'
//        return !(/[^,:{}\[\]0-9.\-+Eaeflnr-u \n\r\t]/.test(text.replace(/"(\\.|[^"\\])*"/g, '')));
        return true;//all impls are safeEval now, never actually calling eval, they are all safe
    }

    /**
     * Evaluates a JSON expression using {@code eval()}. This method does not
     * validate the JSON text and should only be used on JSON from trusted
     * sources. The payload must evaluate to an Object or an Array (not a
     * primitive or a String).
     *
     * @param <T> The type of JavaScriptObject that should be returned
     * @param json The source JSON text
     * @return The evaluated object
     */
    public static <T extends JavaScriptObject> T unsafeEval(String json) {
        return safeEval(json);
    }

//    private static String escapeChar(String c, JavaScriptObject escapeTable) {
//        var lookedUp = @JsonUtils::escapeTable[c.charCodeAt(0)];
//        return (lookedUp == null) ? c : lookedUp;
//    }
//
//    private static JsArray<JsString> escapeTable; // Lazily initialized.
//
//    private static JsArray<JsString> getEscapeTable() {
//        if (escapeTable == null) {
//            escapeTable = initEscapeTable();
//        }
//        return escapeTable;
//    }
//
//    private static JsArray<JsString> initEscapeTable() {
//        var out = [
//        "\\u0000", "\\u0001", "\\u0002", "\\u0003", "\\u0004", "\\u0005",
//                "\\u0006", "\\u0007", "\\b", "\\t", "\\n", "\\u000B",
//                "\\f", "\\r", "\\u000E", "\\u000F", "\\u0010", "\\u0011",
//                "\\u0012", "\\u0013", "\\u0014", "\\u0015", "\\u0016", "\\u0017",
//                "\\u0018", "\\u0019", "\\u001A", "\\u001B", "\\u001C", "\\u001D",
//                "\\u001E", "\\u001F"];
//        out[34] = '\\"';
//        out[92] = '\\\\';
//        out[0xad] = '\\u00ad'; // Soft hyphen
//        out[0x600] = '\\u0600'; // Arabic number sign
//        out[0x601] = '\\u0601'; // Arabic sign sanah
//        out[0x602] = '\\u0602'; // Arabic footnote marker
//        out[0x603] = '\\u0603'; // Arabic sign safha
//        out[0x6dd] = '\\u06dd'; // Arabic and of ayah
//        out[0x70f] = '\\u070f'; // Syriac abbreviation mark
//        out[0x17b4] = '\\u17b4'; // Khmer vowel inherent aq
//        out[0x17b5] = '\\u17b5'; // Khmer vowel inherent aa
//        out[0x200b] = '\\u200b'; // Zero width space
//        out[0x200c] = '\\u200c'; // Zero width non-joiner
//        out[0x200d] = '\\u200d'; // Zero width joiner
//        out[0x200e] = '\\u200e'; // Left-to-right mark
//        out[0x200f] = '\\u200f'; // Right-to-left mark
//        out[0x2028] = '\\u2028'; // Line separator
//        out[0x2029] = '\\u2029'; // Paragraph separator
//        out[0x202a] = '\\u202a'; // Left-to-right embedding
//        out[0x202b] = '\\u202b'; // Right-to-left embedding
//        out[0x202c] = '\\u202c'; // Pop directional formatting
//        out[0x202d] = '\\u202d'; // Left-to-right override
//        out[0x202e] = '\\u202e'; // Right-to-left override
//        out[0x2060] = '\\u2060'; // Word joiner
//        out[0x2061] = '\\u2061'; // Function application
//        out[0x2062] = '\\u2062'; // Invisible times
//        out[0x2063] = '\\u2063'; // Invisible separator
//        out[0x2064] = '\\u2064'; // Invisible plus
//        out[0x206a] = '\\u206a'; // Inhibit symmetric swapping
//        out[0x206b] = '\\u206b'; // Activate symmetric swapping
//        out[0x206c] = '\\u206c'; // Inherent Arabic form shaping
//        out[0x206d] = '\\u206d'; // Activate Arabic form shaping
//        out[0x206e] = '\\u206e'; // National digit shapes
//        out[0x206f] = '\\u206f'; // Nominal digit shapes
//        out[0xfeff] = '\\ufeff'; // Zero width no-break space
//        out[0xfff9] = '\\ufff9'; // Intralinear annotation anchor
//        out[0xfffa] = '\\ufffa'; // Intralinear annotation separator
//        out[0xfffb] = '\\ufffb'; // Intralinear annotation terminator
//        return out;
//    }

    private JsonUtils() {
    }
}

