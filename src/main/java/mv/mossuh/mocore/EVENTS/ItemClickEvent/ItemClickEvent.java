package mv.mossuh.mocore.EVENTS.ItemClickEvent;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

public class ItemClickEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    private final Player player;
    private ItemStack selectedItem;
    private ItemStack unselectedItem;
    private Inventory inventory;
    private InventoryView view;
    private ClickType clickType;
    private int  clickedSlot = -1;
    private InventoryClickEvent event;
    private boolean isCancelled;

    public ItemClickEvent(Player player, ItemStack selectedItem, ItemStack unselectedItem, Inventory inventory, ClickType clickType, InventoryClickEvent event) {
        this.player = player;
        this.selectedItem = selectedItem;
        this.unselectedItem = unselectedItem;
        this.clickType = clickType;
        this.inventory = inventory;
        this.isCancelled = false;
        if (event != null) {
            this.event = event;
            this.clickedSlot = event.getRawSlot();
            this.view = event.getView();
        }
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
    public int getClickedSlot() { return clickedSlot; }

    public Inventory getInventory() {
        return inventory;
    }
    public InventoryView getView() { return view; }
    public ClickType getClickType() { return  clickType; }
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



