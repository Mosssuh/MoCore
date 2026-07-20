package mv.mossuh.mocore.GUI.InventoryEvents;

import mv.mossuh.mocore.EVENTS.ItemClickEvent.ItemClickEvent;
import mv.mossuh.mocore.GUI.Holders.PaginatedGUI;
import mv.mossuh.mocore.GUI.Holders.SingleGUI;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class InventoryListenerGUI implements Listener {

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        if (event.getInventory().getHolder() != null) {
            InventoryHolder holder = event.getInventory().getHolder();
            if (holder instanceof PaginatedGUI) {
                PaginatedGUI gui = (PaginatedGUI) event.getInventory().getHolder();
                if (gui.getGUICloseListener() != null) {
                    gui.getGUICloseListener().onClose(event);
                }
            } else if (holder instanceof SingleGUI) {
                SingleGUI gui = (SingleGUI) event.getInventory().getHolder();
                if (gui.getGUICloseListener() != null) {
                    gui.getGUICloseListener().onClose(event);
                }
            }
        }
    }

    @EventHandler
    public void onClick(ItemClickEvent event) {
        Inventory inventory = event.getInventory();
        InventoryHolder holder = inventory.getHolder();
        if (holder instanceof PaginatedGUI) {
            PaginatedGUI gui = (PaginatedGUI) holder;
            if (gui.getGUIClickListener() != null) {
                gui.getGUIClickListener().onClick(event);
            }
        } else if (holder instanceof SingleGUI) {
            SingleGUI gui = (SingleGUI) holder;
            if (gui.getGUIClickListener() != null) {
                gui.getGUIClickListener().onClick(event);
            }
        }
    }
}
