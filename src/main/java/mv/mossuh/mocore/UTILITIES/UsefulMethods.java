package mv.mossuh.mocore.UTILITIES;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;
import java.util.function.BiPredicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UsefulMethods {

    private static final Map<String, BiPredicate<String, String>> OPERATORS = new HashMap<>();

    static {
        OPERATORS.put("<=", (a, b) -> Double.parseDouble(a) <= Double.parseDouble(b));
        OPERATORS.put("<", (a, b) -> Double.parseDouble(a) < Double.parseDouble(b));
        OPERATORS.put(">=", (a, b) -> Double.parseDouble(a) >= Double.parseDouble(b));
        OPERATORS.put(">", (a, b) -> Double.parseDouble(a) > Double.parseDouble(b));
        OPERATORS.put("==", (a, b) -> Double.parseDouble(a) == (Double.parseDouble(b)));
        OPERATORS.put("!=", (a, b) -> Double.parseDouble(a) != (Double.parseDouble(b)));
        OPERATORS.put("equalsIgnoreCase", String::equalsIgnoreCase);
        OPERATORS.put("!equalsIgnoreCase", (a, b) -> !a.equalsIgnoreCase(b));
        OPERATORS.put("equals", String::equals);
        OPERATORS.put("!equals", (a, b) -> !a.equals(b));
        OPERATORS.put("startsWith", String::startsWith);
        OPERATORS.put("!startsWith", (a, b) -> !a.startsWith(b));
        OPERATORS.put("contains", String::contains);
        OPERATORS.put("!contains", (a, b) -> !a.contains(b));
    }

    public static boolean evaluateString(String string) {
        if (string == null) return false;

        for (Map.Entry<String, BiPredicate<String, String>> entry : OPERATORS.entrySet()) {
            String operator = entry.getKey();
            int index = string.indexOf(operator);
            if (index != -1) {
                String val1 = string.substring(0, index).trim();
                String val2 = string.substring(index + operator.length()).trim();
                try {
                    return entry.getValue().test(val1, val2);
                } catch (NumberFormatException e) {
                    return false;
                }
            }
        }
        return false;
    }

    private static final Pattern RANDOM_PATTERN = Pattern.compile("\\{random_(\\d+)-(\\d+)}");

    public static String setRandomNumber(String string) {
        if (string == null) {
            return null;
        }

        StringBuilder result = new StringBuilder();
        int lastIndex = 0;

        Matcher matcher = RANDOM_PATTERN.matcher(string);
        while (matcher.find()) {
            result.append(string, lastIndex, matcher.start());
            int min = Integer.parseInt(matcher.group(1));
            int max = Integer.parseInt(matcher.group(2));
            result.append(UsefulMethods.random(min, max));
            lastIndex = matcher.end();
        }
        result.append(string.substring(lastIndex));
        return result.toString();
    }

    private static final Pattern TIME_FORMATTER_PATTERN = Pattern.compile("\\{time_formatter_(\\d+)}");

    public static String setTimeFormatter(String string, TimeFormat format) {
        if (string == null) return null;

        Matcher matcher = TIME_FORMATTER_PATTERN.matcher(string);
        StringBuffer buffer = new StringBuffer();

        while (matcher.find()) {
            try {
                long timeValue = Long.parseLong(matcher.group(1));
                String formattedTime = showCooldownFormatted(timeValue, System.currentTimeMillis(), format);
                matcher.appendReplacement(buffer, formattedTime);
            } catch (NumberFormatException e) {
                return buffer.toString();
            }
        }
        matcher.appendTail(buffer);
        return buffer.toString();
    }

    private static final Pattern CHANGE_OUTPUT_PATTERN = Pattern.compile(
            "\\{local_changeoutput_(.+?)_input:(.+?)_matcher:(.+?)_ifmatch:(.*?)_else:(.*?)}"
    );

    public static String setChangeOutputPlaceholder(String string) {
        if (string == null) return string;

        Matcher matcher = CHANGE_OUTPUT_PATTERN.matcher(string);
        StringBuffer buffer = new StringBuffer();

        while (matcher.find()) {
            String option = matcher.group(1);
            String input = setMathPlaceholder(matcher.group(2));
            String match = setMathPlaceholder(matcher.group(3));
            String ifMatch = setMathPlaceholder(matcher.group(4));
            String ifNotMatch = setMathPlaceholder(matcher.group(5));

            String evaluate = setMathPlaceholder(input + " " + option + " " + match);
            boolean condition = evaluateString(evaluate);
            matcher.appendReplacement(buffer, condition ? ifMatch : ifNotMatch);
        }
        matcher.appendTail(buffer);
        return buffer.toString();
    }

    private static final Pattern MATH_PATTERN = Pattern.compile(
            "\\{local_math_([^}]+)}"
    );

    public static String setMathPlaceholder(String string) {
        if (string == null) return null;

        Matcher matcher = MATH_PATTERN.matcher(string);
        StringBuffer result = new StringBuffer();
        while (matcher.find()) {
            String expression = matcher.group(1);
            double value = evaluateMathExpression(expression);
            matcher.appendReplacement(result, formatNumber(value, 5));
        }
        matcher.appendTail(result);
        return result.toString();
    }

    public static double evaluateMathExpression(String expression) {
        try {
            expression = expression.replaceAll("\\s+", "");

            Expression exp = new ExpressionBuilder(expression).build();
            return exp.evaluate();


        } catch (Exception e) {
            return 0;
        }
    }

    private static final NumberFormat NUMBER_FORMAT;
    static {
        NUMBER_FORMAT = NumberFormat.getInstance(Locale.US);
        NUMBER_FORMAT.setGroupingUsed(false);
    }

    public static String formatNumber(double number, int maxDecimal) {
        NUMBER_FORMAT.setMaximumFractionDigits(maxDecimal);
        return NUMBER_FORMAT.format(number).replace("$", "");
    }

    private static final Pattern NUMERIC_PATTERN = Pattern.compile("-?\\d+(\\.\\d+)?");

    public static boolean isNumeric(String string) {
        return string != null && NUMERIC_PATTERN.matcher(string).matches();
    }

    public static String getFileExtension(String filePath) {
        int lastIndex = filePath.lastIndexOf(".");
        if (lastIndex > 0 && lastIndex < filePath.length() - 1) {
            return filePath.substring(lastIndex+1);
        } else {
            return "invalid";
        }
    }

    public static double randomNumber() {
        double lowest = 1.000000000000000F;
        double highest = 10000.000000000000000F;
        return Math.floor(Math.random() * (highest - lowest + 1) + lowest);
    }

    public static double random(double min, double max) {
        try {
            Random rand = new Random();
            return (rand.nextInt((int) ((max - min) + 1)) + min);
        } catch (Exception ignored) {

        }
        return 0;
    }

    public static boolean hasPermission(CommandSender sender, String permission) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            return player.hasPermission(permission);
        } else {
            return true;
        }
    }

    public static boolean isOnline(Player player) {
        if (player != null) {
            return player.isOnline();
        }
        return false;
    }


    public static long showCooldown(long cooldownInSeconds, long startedTimeInMilliSeconds) {
        long currentTimeInMilliSeconds = System.currentTimeMillis();
        long wait = currentTimeInMilliSeconds - startedTimeInMilliSeconds;
        long waitDiv = wait / 1000;
        long seconds = cooldownInSeconds - waitDiv;

        long durationInMilliSeconds = cooldownInSeconds * 1000;

        if (((startedTimeInMilliSeconds + durationInMilliSeconds) > currentTimeInMilliSeconds) && (startedTimeInMilliSeconds != 0)) {
            return seconds;
        } else {
            return 0;
        }
    }


    public static String showCooldownFormatted(long cooldownInSeconds, long startedTimeInMilliSeconds, TimeFormat format) {
        String time = "";

        String year = format.getYear();
        String month = format.getMonth();
        String week = format.getWeek();
        String day = format.getDay();
        String hour = format.getHour();
        String minute = format.getMinute();
        String second = format.getSecond();

        long millis = System.currentTimeMillis();
        long cooldownmil = Math.round(cooldownInSeconds * 1000);

        long wait = millis - startedTimeInMilliSeconds;
        long waitDiv = wait / 1000;
        long waittotalsec = cooldownInSeconds - waitDiv;

        long waittotalmin = waittotalsec / 60;
        long waittotalhour = waittotalmin / 60;
        long waittotaldays = waittotalhour / 24;
        long waittotalweeks = waittotaldays / 7;
        long waittotalmonths = waittotalweeks / 4;
        long waittotalyears = waittotalmonths / 12;

        if (((startedTimeInMilliSeconds + cooldownmil) > millis) && (startedTimeInMilliSeconds != 0)) {
            if (waittotalsec > 59) {
                waittotalsec = waittotalsec - 60 * waittotalmin;
            }

            if (waittotalsec != 0) {
                time = waittotalsec + second;
            }

            if (waittotalmin > 59) {
                waittotalmin = waittotalmin - 60 * waittotalhour;
            }
            if (waittotalmin > 0) {
                time = waittotalmin + minute + " " + time;
            }

            if (waittotalhour > 23) {
                waittotalhour = waittotalhour - 24 * waittotaldays;
            }

            if (waittotalhour > 0) {
                time = waittotalhour + hour + " " + time;
            }

            if (waittotaldays > 6) {
                waittotaldays = waittotaldays - 7 * waittotalweeks;
            }

            if (waittotaldays > 0) {
                time = waittotaldays + day + " " + time;
            }

            if (waittotalweeks > 3) {
                waittotalweeks = waittotalweeks - 4 * waittotalmonths;
            }

            if (waittotalweeks > 0) {
                time = waittotalweeks + week + " " + time;
            }

            if (waittotalmonths > 11) {
                waittotalmonths = waittotalmonths - 12 * waittotalyears;
            }

            if (waittotalmonths > 0) {
                time = waittotalmonths + month + " " + time;
            }

            if (waittotalyears > 0) {
                time = waittotalyears + year + " " + time;
            }

            return time;
        } else {
            return "0" + second;
        }
    }

    public static boolean getBoolean(String string) {
        return string.equalsIgnoreCase("true");
    }

    public static void replacePlaceholder(StringBuilder sb, String placeholder, String replacement) {
        int index;
        while ((index = sb.indexOf(placeholder)) != -1) {
            sb.replace(index, index + placeholder.length(), replacement);
        }
    }


    public static String formatValue(double value, ValueFormat format) {

        String[] suffixes = {
                "",       // 0
                format.getThousand(), // 1 (10^3)
                format.getMillion(),  // 2 (10^6)
                format.getBillion(),  // 3 (10^9)
                format.getTrillion(), // 4 (10^12)
                format.getQuadrillion(),
                format.getQuintillion(),
                format.getSextillion(),
                format.getSeptillion(),
                format.getOctillion(),
                format.getNonillion()  // 10 (10^30)
        };

        if (value < 1000) {
            return new DecimalFormat("#.##").format(value);
        }

        int index = 0;
        while (value >= 1000 && index < suffixes.length - 1) {
            value /= 1000.0;
            index++;
        }

        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        return decimalFormat.format(value) + suffixes[index];
    }


    public static String showTimeElapsedFormatted(long timeInSeconds, TimeFormat format) {
        String time = "";

        String year = format.getYear();
        String month = format.getMonth();
        String week = format.getWeek();
        String day = format.getDay();
        String hour = format.getHour();
        String minute = format.getMinute();
        String second = format.getSecond();

        long elapsedTotalMin = timeInSeconds / 60;
        long elapsedTotalHour = elapsedTotalMin / 60;
        long elapsedTotalDays = elapsedTotalHour / 24;
        long elapsedTotalWeeks = elapsedTotalDays / 7;
        long elapsedTotalMonths = elapsedTotalWeeks / 4;
        long elapsedTotalYears = elapsedTotalMonths / 12;

        long secondsPart = timeInSeconds % 60;
        long minutesPart = elapsedTotalMin % 60;
        long hoursPart = elapsedTotalHour % 24;
        long daysPart = elapsedTotalDays % 7;
        long weeksPart = elapsedTotalWeeks % 4;
        long monthsPart = elapsedTotalMonths % 12;
        long yearsPart = elapsedTotalYears;

        if (timeInSeconds > 0) {
            if (secondsPart != 0) {
                time = secondsPart + second;
            }

            if (minutesPart > 0) {
                time = minutesPart + minute + " " + time;
            }

            if (hoursPart > 0) {
                time = hoursPart + hour + " " + time;
            }

            if (daysPart > 0) {
                time = daysPart + day + " " + time;
            }

            if (weeksPart > 0) {
                time = weeksPart + week + " " + time;
            }

            if (monthsPart > 0) {
                time = monthsPart + month + " " + time;
            }

            if (yearsPart > 0) {
                time = yearsPart + year + " " + time;
            }

            return time;
        } else {
            // No time has elapsed
            return "0";
        }
    }
}
