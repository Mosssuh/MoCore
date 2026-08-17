package mv.mossuh.mocore.GUI.Layouts;

import java.util.*;

public class GUILayout {

    private final List<Integer> slots;

    public GUILayout(Integer... slots) {
        this.slots = Collections.unmodifiableList(
                Arrays.asList(slots)
        );
    }

    public List<Integer> getSlots() {
        return slots;
    }

    public boolean contains(int slot) {
        return slots.contains(slot);
    }

    public int size() {
        return slots.size();
    }
}
