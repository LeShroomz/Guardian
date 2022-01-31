package dev.trixxie.felineapi.utility;

import dev.trixxie.guardian.utils.VersionUtility;
import net.md_5.bungee.api.ChatColor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MessageUtility {

    private static final Map<Character, Pattern> KNOWN_PATTERN_MAP = new ConcurrentHashMap<>();

    /**
     * @param colorChar The character that will be used for color codes (usually {@code '&'})
     * @param string The {@link String} that will have its values replaced.
     * @return A new {@link String} with the hex color codes being replaced.
     */
    public static String replaceHexColors(char colorChar, String string) {
        Pattern pattern = getReplaceAllRgbPattern(colorChar);
        Matcher matcher = pattern.matcher(string);

        StringBuffer buffer = new StringBuffer();
        while(matcher.find()) {
            if(matcher.group(1) != null) {
                matcher.appendReplacement(buffer, colorChar + "#$2");
                continue;
            }

            try {
                String hexCodeString = matcher.group(2);
                String hexCode = parseHexColor(hexCodeString);
                matcher.appendReplacement(buffer, hexCode);
            } catch(NumberFormatException ignored) {}
        }

        matcher.appendTail(buffer);
        return buffer.toString();
    }

    private static Pattern getReplaceAllRgbPattern(char colorChar) {
        if(KNOWN_PATTERN_MAP.containsKey(colorChar)) {
            return KNOWN_PATTERN_MAP.get(colorChar);
        }

        String colorCharString = Character.toString(colorChar);
        String colorCharPattern = Pattern.quote(colorCharString);

        String patternString = ("(" + colorCharPattern + ")?" + colorCharPattern + "#([0-9a-fA-F]{6})");
        Pattern pattern = Pattern.compile(patternString);
        KNOWN_PATTERN_MAP.put(colorChar, pattern);
        return pattern;
    }

    private static String parseHexColor(String string) throws NumberFormatException {
        if(string.startsWith("#")) {
            string = string.substring(1);
        }

        if(string.length() != 6) {
            throw new NumberFormatException("Invalid hex length!");
        }

        int colorInt = Integer.decode("#" + string);
        if(colorInt < 0x000000 || colorInt > 0xFFFFFF) {
            throw new NumberFormatException("Invalid hex color value!");
        }

        StringBuilder assembled = new StringBuilder();
        assembled.append(ChatColor.COLOR_CHAR);
        assembled.append("x");

        char[] charArray = string.toCharArray();
        for(char character : charArray) {
            assembled.append(ChatColor.COLOR_CHAR);
            assembled.append(character);
        }

        return assembled.toString();
    }

    /**
     * @param message The message that will be colored
     * @return A new string containing {@code message} but with the color codes replaced, or an empty string if message was {@code null}.
     * @see ChatColor#translateAlternateColorCodes(char, String)
     * @see HexColorUtility#replaceHexColors(char, String)
     */
    public static String color(String message) {
        if(message == null || message.isEmpty()) {
            return "";
        }

        int minorVersion = VersionUtility.getMinorVersion();
        if(minorVersion < 16) {
            return org.bukkit.ChatColor.translateAlternateColorCodes('&', message);
        }

        String messageReplaced = replaceHexColors('&', message);
        return net.md_5.bungee.api.ChatColor.translateAlternateColorCodes('&', messageReplaced);
    }

    /**
     * @param messageArray The array of messages that will be colored
     * @return A new array containing every message in the input array, but with color codes replaced.
     */
    public static String[] colorArray(String... messageArray) {
        int messageArrayLength = messageArray.length;
        String[] colorMessageArray = new String[messageArrayLength];

        for(int i = 0; i < messageArrayLength; i++) {
            String message = messageArray[i];
            colorMessageArray[i] = color(message);
        }

        return colorMessageArray;
    }


    /**
     * @param messageList The iterable of messages that will be colored
     * @return A {@code List<String>} containing every message in the input iterable, but with color codes replaced.
     * @see List
     * @see java.util.Collection
     */
    public static List<String> colorList(Iterable<String> messageList) {
        List<String> colorList = new ArrayList<>();
        for(String message : messageList) {
            String color = color(message);
            colorList.add(color);
        }
        return colorList;
    }

    /**
     * @param messageArray The array of messages that will be colored
     * @return A {@code List<String>} containing every message in the input array, but with color codes replaced.
     */
    public static List<String> colorList(String... messageArray) {
        List<String> messageList = Arrays.asList(messageArray);
        return colorList(messageList);
    }
}