package mv.mossuh.mocore.UTILITIES;

import mv.mossuh.mocore.ENUMS.PluginType;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.util.EnumMap;
import java.util.Map;

public class InstanceManager {
    private static final Map<PluginType, Plugin> instances = new EnumMap<>(PluginType.class);

    public static void setupPlugin(PluginType type) {
        if (type == PluginType.None) return;

        Plugin plugin = Bukkit.getPluginManager().getPlugin(type.name());
        if (plugin != null && plugin.isEnabled()) {
            instances.put(type, plugin);
        }
    }

    public static Plugin getPlugin(PluginType type) {
        return instances.get(type);
    }

    @SuppressWarnings("unchecked")
    public static <T extends Plugin> T getPluginAs(PluginType type, Class<T> pluginClass) {
        Plugin plugin = instances.get(type);
        if (pluginClass.isInstance(plugin)) {
            return (T) plugin;
        }
        return null;
    }

    public static boolean isLoaded(PluginType type) {
        return instances.containsKey(type);
    }
}


