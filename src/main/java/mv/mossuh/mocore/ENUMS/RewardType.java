package mv.mossuh.mocore.ENUMS;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RewardType {
    public static final RewardType CONSOLE_COMMAND = new RewardType("CONSOLE_COMMAND");
    public static final RewardType PLAYER_COMMAND = new RewardType("PLAYER_COMMAND");
    public static final RewardType PLAYER_COMMAND_AS_OP = new RewardType("PLAYER_COMMAND_AS_OP");
    public static final RewardType MESSAGE = new RewardType("MESSAGE");
    public static final RewardType TITLE = new RewardType("TITLE");
    public static final RewardType SOUND = new RewardType("SOUND");
    public static final RewardType BROADCAST_MESSAGE = new RewardType("BROADCAST_MESSAGE");
    public static final RewardType BROADCAST_TITLE = new RewardType("BROADCAST_TITLE");
    public static final RewardType JSON = new RewardType("JSON");
    public static final RewardType JSON_BROADCAST = new RewardType("JSON_BROADCAST");
    public static final RewardType EFFECT = new RewardType("EFFECT");
    public static final RewardType EXECUTE_ACTION = new RewardType("EXECUTE_ACTION");

    public static final RewardType WORLD_DROP = new RewardType("WORLD_DROP");
    public static final RewardType GIVE_ITEM = new RewardType("GIVE_ITEM");

    public static final RewardType CANCEL_BOOSTER = new RewardType("CANCEL_BOOSTER");
    public static final RewardType CANCEL_CLAIM = new RewardType("CANCEL_CLAIM");

    public static final RewardType CANCEL_MESSAGE = new RewardType("CANCEL_MESSAGE");

    public static final RewardType CANCEL_EVENT = new RewardType("CANCEL_EVENT");
    public static final RewardType CANCEL_DROPS = new RewardType("CANCEL_DROPS");

    public static final RewardType ADD_EXP = new RewardType("ADD_EXP");
    public static final RewardType SET_EXP = new RewardType("SET_EXP");
    public static final RewardType REMOVE_EXP = new RewardType("REMOVE_EXP");
    public static final RewardType ADD_LEVEL = new RewardType("ADD_LEVEL");
    public static final RewardType SET_LEVEL = new RewardType("SET_LEVEL");
    public static final RewardType REMOVE_LEVEL = new RewardType("REMOVE_LEVEL");
    public static final RewardType ACTIVATE_EXP_BOOST = new RewardType("ACTIVATE_EXP_BOOST");
    public static final RewardType CHANGE_EXP_BOOST = new RewardType("CHANGE_EXP_BOOST");
    public static final RewardType SET_VARIABLE = new RewardType("SET_VARIABLE");
    public static final RewardType REMOVE_VARIABLE = new RewardType("REMOVE_VARIABLE");

    public static final RewardType SET_ATTRIBUTE = new RewardType("SET_ATTRIBUTE");
    public static final RewardType REMOVE_ATTRIBUTE = new RewardType("REMOVE_ATTRIBUTE");

    public static final RewardType ADD_BOOST = new RewardType("ADD_BOOST");
    public static final RewardType REMOVE_BOOST = new RewardType("REMOVE_BOOST");
    public static final RewardType ADD_TIME = new RewardType("ADD_TIME");
    public static final RewardType REMOVE_TIME = new RewardType("REMOVE_TIME");
    public static final RewardType SET_BOOSTER = new RewardType("SET_BOOSTER");
    public static final RewardType REMOVE_BOOSTER = new RewardType("REMOVE_BOOSTER");
    public static final RewardType ACCUMULATE_BOOSTER = new RewardType("ACCUMULATE_BOOSTER");

    public static final RewardType NONE = new RewardType("NONE");

    private final String name;

    private RewardType(String name) {
        this.name = name.toUpperCase();
    }

    public String name() {
        return name;
    }

    public static RewardType convert(String rewardType) {
        if (rewardType == null) return NONE;
        return new RewardType(rewardType);
    }

    public static RewardType[] values() {
        List<RewardType> list = new ArrayList<>();

        for (Field field : RewardType.class.getDeclaredFields()) {
            if (!Modifier.isStatic(field.getModifiers())) continue;
            if (!Modifier.isFinal(field.getModifiers())) continue;

            try {
                if (field.getType() == RewardType.class) {
                    list.add((RewardType) field.get(null));
                }
            } catch (IllegalAccessException ignored) {
            }
        }
        return list.toArray(new RewardType[0]);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RewardType that = (RewardType) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return name;
    }
}
