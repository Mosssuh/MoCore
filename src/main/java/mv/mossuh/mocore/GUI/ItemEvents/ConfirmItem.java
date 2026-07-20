package mv.mossuh.mocore.GUI.ItemEvents;

import mv.mossuh.mocore.GUI.Items.GUIItem;
import org.bukkit.event.inventory.ClickType;

public class ConfirmItem {
    ClickType clickType;
    GUIItem item;

    public ConfirmItem(ClickType clickType, GUIItem item) {
        this.clickType = clickType;
        this.item = item;
    }

    public ClickType getClickType() { return clickType; }
    public GUIItem getItem() { return item; }
}
