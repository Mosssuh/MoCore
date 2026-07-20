package mv.mossuh.mocore.EVENTS.ItemSelectEvent;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ItemSelectEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    private final Player player;
    private ItemStack selectedItem;
    private ItemStack unselectedItem;
    private Inventory inventory;
    private InventoryClickEvent event;
    private boolean isCancelled;

    public ItemSelectEvent(Player player, ItemStack selectedItem, ItemStack unselectedItem, Inventory inventory, InventoryClickEvent event) {
        this.player = player;
        this.selectedItem = selectedItem;
        this.unselectedItem = unselectedItem;
        this.event = event;
        this.inventory = inventory;
        this.isCancelled = false;
    }

    public Player getPlayer() {
        return player;
    }

    public void setSelectedItem(ItemStack itemStack) {
        event.setCurrentItem(itemStack);
    }

    public void setUnSelectedItem(ItemStack itemStack) {
        event.setCursor(itemStack);
    }

    public Inventory getInventory() {
        return inventory;
    }
    public ItemStack getSelectedItem() {
        return selectedItem;
    }

    public ItemStack getUnSelectedItem() {
        return unselectedItem;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public boolean isCancelled() {
        return isCancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.isCancelled = cancel;
    }
}



