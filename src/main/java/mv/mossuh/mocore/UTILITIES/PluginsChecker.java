package mv.mossuh.mocore.UTILITIES;

import mv.mossuh.mocore.ENUMS.PluginType;
import mv.mossuh.mocore.EVENTS.PluginCheckerEvent;
import mv.mossuh.mocore.MoCore;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;

public class PluginsChecker {
    private static final EnumSet<PluginType> status = EnumSet.noneOf(PluginType.class);
    private static final Map<PluginType, Integer> taskIDs = new EnumMap<>(PluginType.class);
    private static final Map<PluginType, String[]> dependencies = new EnumMap<>(PluginType.class);

    static {
        dependencies.put(PluginType.PlaceholderAPI, new String[]{"PlaceholderAPI"});
        dependencies.put(PluginType.SuperiorSkyblock2, new String[]{"SuperiorSkyblock2"});
        dependencies.put(PluginType.MoBoosters, new String[]{"vBoosters"});
        dependencies.put(PluginType.MoMomentum, new String[]{"vMomentum"});
        dependencies.put(PluginType.ExcellentEconomy, new String[]{"ExcellentEconomy"});
        dependencies.put(PluginType.FancyHolograms, new String[]{"FancyHolograms"});
        dependencies.put(PluginType.ProtocolLib, new String[]{"ProtocolLib"});
        dependencies.put(PluginType.WorldEdit, new String[]{"WorldGuard"});
        dependencies.put(PluginType.WorldGuard, new String[]{"WorldEdit"});
        dependencies.put(PluginType.FastAsyncWorldEdit, new String[]{"FastAsyncWorldEdit"});
        dependencies.put(PluginType.Vault, new String[]{"Vault"});
        dependencies.put(PluginType.DecentHolograms, new String[]{"DecentHolograms"});
        dependencies.put(PluginType.MoPets, new String[]{"MoPets"});
        dependencies.put(PluginType.MoArmors, new String[]{"MoArmors"});
        dependencies.put(PluginType.MoChestShop, new String[]{"MoChestShop"});
    }

    public void startCheckingDependencies(MoCore main) {
        for (PluginType type : dependencies.keySet()) {
            checkPlugin(main, type, dependencies.get(type));
        }
    }

    private void checkPlugin(MoCore main, PluginType type, String[] dependencies) {
        int taskId = main.getServer().getScheduler().scheduleSyncRepeatingTask(main, () -> {
            PluginManager pm = Bukkit.getPluginManager();
            boolean allEnabled = true;
            for (String plugin : dependencies) {
                if (!pm.isPluginEnabled(plugin)) {
                    allEnabled = false;
                    break;
                }
            }

            if (allEnabled) {
                status.add(type);
                InstanceManager.setupPlugin(type);
                Bukkit.getPluginManager().callEvent(new PluginCheckerEvent(type));
                main.getServer().getScheduler().cancelTask(taskIDs.get(type));
            } else {
                status.remove(type);
            }
        }, 0L, 20L);

        status.remove(type); // default: disabled
        taskIDs.put(type, taskId);
    }

    public static boolean isPluginEnabled(PluginType type) {
        return status.contains(type);
    }
}
