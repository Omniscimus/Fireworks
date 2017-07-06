package net.omniscimus.fireworks.commands;

import java.text.ParseException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class contains methods to parse a time string.
 * 
 * Example of a time string: "3h24m3s" means 3 hours, 24 minutes and 3 seconds.
 * 
 * Allowed time characters:
 * <ul>
 * <li>y: years</li>
 * <li>w: weeks</li>
 * <li>d: days</li>
 * <li>h: hours</li>
 * <li>m: minutes</li>
 * <li>s: seconds</li>
 * </ul>
 *
 * @author Omniscimus
 */
public class TimeParser {
    
    private static final List<Character> DIGITS = Arrays.asList(
            new Character[] {'1', '2', '3', '4', '5', '6', '7', '8', '9', '0'});
    
    private static final Map<Character, Integer> TIME_UNITS = new HashMap<>();
    static {
        TIME_UNITS.put('s', 1);
        TIME_UNITS.put('m', 60 * TIME_UNITS.get('s'));
        TIME_UNITS.put('h', 60 * TIME_UNITS.get('m'));
        TIME_UNITS.put('d', 24 * TIME_UNITS.get('h'));
        TIME_UNITS.put('w', 7 * TIME_UNITS.get('d'));
        TIME_UNITS.put('y', (int) (365.25 * TIME_UNITS.get('d')));
    }
    
    /**
     * Converts a time string into an amount of seconds.
     *
     * @param time the time string
     * @return the amount of time time in the string, in seconds
     * @throws ParseException if the given string is not a valid time string
     */
    public static long parse(String time) throws ParseException {
        if (!time.matches("(\\d+y)?(\\d+w)?(\\d+d)?(\\d+h)?(\\d+m)?(\\d+s)?"))
            throw new ParseException("Invalid time string", 0);
        
        return parseNoCheck(time);
    }
    
    /**
     * Recursive method to parse the time in a string. Does not check if the
     * string is a valid time string for efficiency.
     * 
     * @param time the time string
     * @return the amount of time in the string, in seconds
     */
    private static long parseNoCheck(String time) {
        if (time.isEmpty())
            return 0;
        
        int i = 0;
        while (DIGITS.contains(time.charAt(i))) {
            i++;
        }
        
        long number = Long.parseLong(time.substring(0, i));
        int multiplier = TIME_UNITS.get(time.charAt(i));
        
        return number * multiplier
                + parseNoCheck(time.substring(i + 1, time.length()));
    }
    
}
