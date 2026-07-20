package mv.mossuh.mocore;

import mv.mossuh.mocore.CONFIG.CoreConfigs;
import mv.mossuh.mocore.EVENTS.ArmorEquipEvent.ArmorListener;
import mv.mossuh.mocore.EVENTS.ItemClickEvent.ItemClickListener;
import mv.mossuh.mocore.EVENTS.ItemSelectEvent.ItemSelectListener;
import mv.mossuh.mocore.GUI.InventoryEvents.InventoryListenerGUI;
import mv.mossuh.mocore.GUI.ItemEvents.ItemListenerGUI;
import mv.mossuh.mocore.NBT.TEXTUREHEADS.Data.TextureHeadsData;
import mv.mossuh.mocore.UTILITIES.PluginsChecker;
import mv.mossuh.mocore.UTILITIES.UsefulString;
import mv.mossuh.mocore.VERSION.ServerVersion;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public final class MoCore extends JavaPlugin {

    private static CoreConfigs configs;
    private static PluginsChecker checker;
    private static MoCore instance;
    @Override
    public void onEnable() {
        instance = this;
        ServerVersion.initialize();
        checker = new PluginsChecker();
        checker.startCheckingDependencies(this);

        configs = new CoreConfigs(this);
        configs.configure();

        TextureHeadsData.registerDatabaseInMaps();

        registerEvents();

        UsefulString.get("&8[&bMoCore&8] &aHas been enabled. Created by &bMossuh&a.").hex().sendMessageInConsole();
    }

    @Override
    public void onDisable() {
        TextureHeadsData.registerMapsInDatabase();
        TextureHeadsData.vacuumDatabase();
        UsefulString.get("&8[&bMoCore&8] &cHas been disabled. Created by &bMossuh&a.").hex().sendMessageInConsole();
    }

    public static MoCore getInstance() { return instance; }

    public static PluginsChecker getChecker() {
        return checker;
    }

    private void registerEvents() {
        getServer().getPluginManager().registerEvents(new ItemClickListener(), this);
        getServer().getPluginManager().registerEvents(new ItemSelectListener(), this);
        getServer().getPluginManager().registerEvents(new ItemListenerGUI(), this);
        getServer().getPluginManager().registerEvents(new InventoryListenerGUI(), this);
        getServer().getPluginManager().registerEvents(new ArmorListener(new ArrayList<>()), this);
    }
}
