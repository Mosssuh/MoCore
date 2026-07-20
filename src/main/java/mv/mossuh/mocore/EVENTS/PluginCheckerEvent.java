package mv.mossuh.mocore.EVENTS;

import mv.mossuh.mocore.ENUMS.PluginType;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class PluginCheckerEvent extends Event {
    private static final HandlerList handlers = new HandlerList();

    private PluginType pluginType = PluginType.None;
    public PluginCheckerEvent(PluginType pluginType) {
        if (pluginType != null) { this.pluginType = pluginType; }
    }

    public PluginType getPluginType() { return pluginType; }

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
