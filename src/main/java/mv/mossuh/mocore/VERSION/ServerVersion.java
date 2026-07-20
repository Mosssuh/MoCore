package mv.mossuh.mocore.VERSION;

import mv.mossuh.mocore.UTILITIES.UsefulString;
import org.bukkit.Bukkit;

import java.util.HashMap;
import java.util.Map;

public enum ServerVersion {
    UNKNOWN(Integer.MAX_VALUE),
    MC1_8(180),
    MC1_9(190),
    MC1_10(1100),
    MC1_11(1110),
    MC1_12(1120),
    MC1_13(1130),
    MC1_14(1140),
    MC1_15(1150),
    MC1_16(1160),
    MC1_17(1170),
    MC1_18(1180),
    MC1_19(1190),
    MC1_20(1200),
    MC1_20_5(1205),
    MC1_21(1210),
    MC26(2600);

    private final int versionCode;

    private static final Map<String, ServerVersion> VERSIONS_MAP = new HashMap<>();
    private static ServerVersion currentVersion = UNKNOWN;

    static {
        for (ServerVersion version : values()) {
            String major = version.name().replace("MC", "").replace("_", ".");
            VERSIONS_MAP.put(major, version);
        }
        VERSIONS_MAP.put("1.20", MC1_20);
        VERSIONS_MAP.put("1.20.5", MC1_20_5);
        VERSIONS_MAP.put("1.21", MC1_21);
    }

    ServerVersion(int versionCode) {
        this.versionCode = versionCode;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public static ServerVersion getVersion(String bukkitVersion) {
        String version = bukkitVersion.split("-")[0];

        if (version.startsWith("1.8")) {
            return VERSIONS_MAP.getOrDefault("1.8", UNKNOWN);
        } else if (version.startsWith("1.9")) {
            return VERSIONS_MAP.getOrDefault("1.9", UNKNOWN);
        } else if (version.startsWith("1.10")) {
            return VERSIONS_MAP.getOrDefault("1.10", UNKNOWN);
        } else if (version.startsWith("1.11")) {
            return VERSIONS_MAP.getOrDefault("1.11", UNKNOWN);
        } else if (version.startsWith("1.12")) {
            return VERSIONS_MAP.getOrDefault("1.12", UNKNOWN);
        } else if (version.startsWith("1.13")) {
            return VERSIONS_MAP.getOrDefault("1.13", UNKNOWN);
        } else if (version.startsWith("1.14")) {
            return VERSIONS_MAP.getOrDefault("1.14", UNKNOWN);
        } else if (version.startsWith("1.15")) {
            return VERSIONS_MAP.getOrDefault("1.15", UNKNOWN);
        } else if (version.startsWith("1.16")) {
            return VERSIONS_MAP.getOrDefault("1.16", UNKNOWN);
        } else if (version.startsWith("1.17")) {
            return VERSIONS_MAP.getOrDefault("1.17", UNKNOWN);
        } else if (version.startsWith("1.18")) {
            return VERSIONS_MAP.getOrDefault("1.18", UNKNOWN);
        } else if (version.startsWith("1.19")) {
            return VERSIONS_MAP.getOrDefault("1.19", UNKNOWN);
        }

        if (version.equals("1.20.5")) {
            return VERSIONS_MAP.getOrDefault("1.20.5", UNKNOWN);
        } else if (version.startsWith("1.20")) {
            return VERSIONS_MAP.getOrDefault("1.20", UNKNOWN);
        }

        if (version.startsWith("1.21")) {
            return VERSIONS_MAP.getOrDefault("1.21", UNKNOWN);
        }

        if (version.startsWith("26")) {
            return VERSIONS_MAP.getOrDefault("26", UNKNOWN);
        }

        return UNKNOWN;
    }

    public static boolean isAtLeast(ServerVersion version) {
        return getCurrentVersion().versionCode >= version.versionCode;
    }

    public static ServerVersion getCurrentVersion() {
        return currentVersion;
    }

    public static void initialize() {
        String bukkitVersion = Bukkit.getServer().getBukkitVersion();
        currentVersion = getVersion(bukkitVersion);
        UsefulString.get("&8[&bMoCore&8] &aDetected server version: " + currentVersion.name()).hex().sendMessageInConsole();
    }
}



