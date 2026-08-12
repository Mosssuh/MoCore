package mv.mossuh.mocore.ENUMS;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EventType {
    // === WITH ENTITY, NO ARGS ===
    public static final EventType BLOCK_BREAK = new EventType("BLOCK_BREAK", true, false);
    public static final EventType BLOCK_PLACE = new EventType("BLOCK_PLACE", true, false);
    public static final EventType PLAYER_CAUGHT_FISH = new EventType("PLAYER_CAUGHT_FISH", true, false);
    public static final EventType PLAYER_CAUGHT_ENTITY = new EventType("PLAYER_CAUGHT_ENTITY", true, false);
    public static final EventType PLAYER_KILLS = new EventType("PLAYER_KILLS", true, false);
    public static final EventType PLAYER_DIE = new EventType("PLAYER_DIE", true, false);
    public static final EventType PLAYER_ATTACK = new EventType("PLAYER_ATTACK", true, false);
    public static final EventType PLAYER_ATTACKED = new EventType("PLAYER_ATTACKED", true, false);
    public static final EventType ITEM_INTERACT = new EventType("ITEM_INTERACT", true, false);
    public static final EventType BLOCK_INTERACT = new EventType("BLOCK_INTERACT", true, false);
    public static final EventType ENTITY_INTERACT = new EventType("ENTITY_INTERACT", true, false);
    public static final EventType ITEM_CONSUME = new EventType("ITEM_CONSUME", true, false);
    public static final EventType ITEM_BREAK = new EventType("ITEM_BREAK", true, false);
    public static final EventType ITEM_PICKUP = new EventType("ITEM_PICKUP", true, false);
    public static final EventType ITEM_DROP = new EventType("ITEM_DROP", true, false);
    public static final EventType ITEM_HELD = new EventType("ITEM_HELD", true, false);
    public static final EventType ITEM_UNHELD = new EventType("ITEM_UNHELD", true, false);
    public static final EventType PLAYER_EQUIP_PIECE = new EventType("PLAYER_EQUIP_PIECE", true, false);
    public static final EventType PLAYER_UNEQUIP_PIECE = new EventType("PLAYER_UNEQUIP_PIECE", true, false);
    public static final EventType ITEM_SELECT = new EventType("ITEM_SELECT", true, false);
    public static final EventType ITEM_UNSELECT = new EventType("ITEM_UNSELECT", true, false);
    public static final EventType ITEM_ENCHANT = new EventType("ITEM_ENCHANT", true, false);
    public static final EventType ITEM_CRAFT = new EventType("ITEM_CRAFT", true, false);

    // === WITH ARGS ===
    public static final EventType PLAYER_CHANGE_WORLD = new EventType("PLAYER_CHANGE_WORLD", false, true);
    public static final EventType PLAYER_COMMAND = new EventType("PLAYER_COMMAND", false, true);
    public static final EventType PLAYER_CHAT = new EventType("PLAYER_CHAT", false, true);

    // === WITHOUT ARGS AND ENTITIES (SIMPLES) ===
    public static final EventType PLAYER_BED_ENTER = new EventType("PLAYER_BED_ENTER", false, false);
    public static final EventType PLAYER_BED_LEAVE = new EventType("PLAYER_BED_LEAVE", false, false);
    public static final EventType PLAYER_JOIN = new EventType("PLAYER_JOIN", false, false);
    public static final EventType PLAYER_LEAVE = new EventType("PLAYER_LEAVE", false, false);
    public static final EventType PLAYER_RESPAWN = new EventType("PLAYER_RESPAWN", false, false);
    public static final EventType PLAYER_FLY = new EventType("PLAYER_FLY", false, false);
    public static final EventType PLAYER_UNFLY = new EventType("PLAYER_UNFLY", false, false);
    public static final EventType PLAYER_SNEAK = new EventType("PLAYER_SNEAK", false, false);
    public static final EventType PLAYER_UNSNEAK = new EventType("PLAYER_UNSNEAK", false, false);
    public static final EventType PLAYER_SPRINT = new EventType("PLAYER_SPRINT", false, false);
    public static final EventType PLAYER_UNSPRINT = new EventType("PLAYER_UNSPRINT", false, false);
    public static final EventType PLAYER_LEVELUP = new EventType("PLAYER_LEVELUP", false, false);
    public static final EventType DRAG_AND_DROP_CLAIM = new EventType("DRAG_AND_DROP_CLAIM", true, false);
    public static final EventType RIGHT_CLICK_CLAIM = new EventType("RIGHT_CLICK_CLAIM", true, false);

    // === BY DEFAULT AND INVALID ===
    public static final EventType NONE = new EventType("NONE", false, false);
    public static final EventType INVALID = new EventType("INVALID", false, false);

    private final String name;
    private final boolean hasEntity;
    private final boolean hasArgument;

    public EventType(String name, boolean hasEntity, boolean hasArgument) {
        this.name = name.toUpperCase();
        this.hasEntity = hasEntity;
        this.hasArgument = hasArgument;
    }

    public boolean hasEntity() { return hasEntity; }
    public boolean hasArgument() { return hasArgument; }
    public boolean isSimple() { return !hasEntity && !hasArgument;  }


    public String name() {
        return name;
    }

    public static EventType convert(String applicator, boolean hasEntity, boolean hasArgument) {
        if (applicator == null) return INVALID;
        return new EventType(applicator, hasEntity, hasArgument);
    }

    public static EventType[] values() {
        List<EventType> list = new ArrayList<>();

        for (Field field : EventType.class.getDeclaredFields()) {
            if (!Modifier.isStatic(field.getModifiers())) continue;
            if (!Modifier.isFinal(field.getModifiers())) continue;

            try {
                if (field.getType() == EventType.class) {
                    list.add((EventType) field.get(null));
                }
            } catch (IllegalAccessException ignored) {

            }
        }
        return list.toArray(new EventType[0]);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventType that = (EventType) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name.toUpperCase());
    }

    @Override
    public String toString() {
        return name;
    }
}
