package mv.mossuh.mocore.EVENTS.ItemSelectEvent;


import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ItemSelectListener implements Listener {
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        HumanEntity humanEntity = event.getWhoClicked();
        Inventory inventory = event.getClickedInventory();
        if (humanEntity instanceof Player) {
            if (inventory != null) {
                Player player = (Player) humanEntity;

                ItemStack selectedItem = event.getCurrentItem();
                ItemStack unSelectedItem = event.getCursor();

                if (selectedItem != null && unSelectedItem != null) {

                    ItemSelectEvent itemSelectEvent = new ItemSelectEvent(player, selectedItem, unSelectedItem, inventory, event);
                    Bukkit.getPluginManager().callEvent(itemSelectEvent);

                    if (itemSelectEvent.isCancelled()) {
                        event.setCancelled(true);
                    }
                }
            }
        }
    }
}