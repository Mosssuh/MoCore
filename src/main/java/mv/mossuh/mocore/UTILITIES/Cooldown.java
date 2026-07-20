package mv.mossuh.mocore.UTILITIES;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Cooldown {

    private static final Map<String, Long> currentTimes = new ConcurrentHashMap<>();

    public static boolean startAndIsOnCooldown(String code, long cooldownInSeconds) {
        long cooldown = Long.parseLong(String.valueOf(cooldownInSeconds * 1000));
        long currentTime = System.currentTimeMillis();
        long lastTime = currentTimes.getOrDefault(code, 0L);
        if ((currentTime - lastTime) < cooldown) {
            return true;
        }

        currentTimes.put(code, currentTime);
        return false;
    }

    public static void cancelCooldown(String string) {
        if (currentTimes.containsKey(string)) {
            currentTimes.put(string, 0L);
        }
    }

    public static String showCooldownInSeconds(String code, long cooldownInSeconds) {

        long timecooldown = currentTimes.getOrDefault(code, 0L);
        long millis = System.currentTimeMillis();
        long cooldownmil = Math.round(cooldownInSeconds * 1000);

        long wait = millis - timecooldown;
        long waitDiv = wait / 1000;
        long waittotalsec = cooldownInSeconds - waitDiv;

        if (((timecooldown + cooldownmil) > millis) && (timecooldown != 0)) {
            return String.valueOf(waittotalsec);
        } else {
            return "0";
        }
    }
    public static String showCooldownFormatted(String code, long cooldownInSeconds, TimeFormat format) {
        return UsefulMethods.showCooldownFormatted(cooldownInSeconds, currentTimes.getOrDefault(code, 0L), format);
    }
}
