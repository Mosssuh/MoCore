package mv.mossuh.mocore.UTILITIES.TIMEELAPSED;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class TimeStateManager {
    private static final Map<String, TimeState> states = new ConcurrentHashMap<>();

    public static TimeState getState(String id) {
        Set<String> ids = states.keySet();
        for (String i : ids) {
            if (i.equalsIgnoreCase(id)) {
                return states.get(i);
            }
        }
        states.computeIfAbsent(id, k -> new TimeState(null, null));
        return states.get(id);
    }

    public static void removeState(String id) {
        Set<String> ids = states.keySet();
        for (String i : ids) {
            if (i.equalsIgnoreCase(id)) {
                states.remove(i);
            }
        }
    }

    public static Collection<TimeState> getStates() {
        return states.values();
    }
}
