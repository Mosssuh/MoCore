package mv.mossuh.mocore.EVENTS.ItemClickEvent;


import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ItemClickListener implements Listener {
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        HumanEntity humanEntity = event.getWhoClicked();
        Inventory inventory = event.getClickedInventory();
        ClickType clickType = event.getClick();
        if (humanEntity instanceof Player) {
            if (inventory != null) {
                Player player = (Player) humanEntity;

                ItemStack selectedItem = event.getCurrentItem();
                ItemStack unSelectedItem = event.getCursor();

                ItemClickEvent itemClickEvent = new ItemClickEvent(player, selectedItem, unSelectedItem, inventory, clickType, event);
                Bukkit.getPluginManager().callEvent(itemClickEvent);

                if (itemClickEvent.isCancelled()) {
                    event.setCancelled(true);
                }
            }
        }
    }
}