package mv.mossuh.mocore.GUI.Holders;

import mv.mossuh.mocore.GUI.Interfaces.ClickListener;
import mv.mossuh.mocore.GUI.Interfaces.CloseListener;
import mv.mossuh.mocore.GUI.Items.GUIItem;
import mv.mossuh.mocore.GUI.Layouts.GUILayout;
import mv.mossuh.mocore.MoCore;
import mv.mossuh.mocore.UTILITIES.UsefulString;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SingleGUI implements InventoryHolder {
    private final MoCore instance = MoCore.getInstance();

    private long refreshInterval = -1;
    private BukkitTask refreshTask;
    private Runnable refreshAction;

    private CloseListener closeListener;
    private ClickListener clickListener;
    private InventoryType inventoryType;
    private String title;
    private Map<Integer, GUIItem> GUIItems = new ConcurrentHashMap<>();
    private Map<Integer, GUIItem> reservedItems = new ConcurrentHashMap<>();
    private Inventory inventory = null;
    private Location location = null;
    private GUILayout layout;

    public SingleGUI(InventoryType inventoryType, String title) {
        this.title = UsefulString.get(title).hex().apply();
        this.inventory = Bukkit.createInventory(this, inventoryType, this.title);
    }
    public SingleGUI(InventoryType inventoryType, String title, Location location) {
        this.title = UsefulString.get(title).hex().apply();
        this.inventory = Bukkit.createInventory(this, inventoryType, this.title);
        this.location = location;
    }

    public SingleGUI(int rows, String title) {
        this.title = UsefulString.get(title).hex().apply();
        this.inventory = Bukkit.createInventory(this, rows*9, this.title);
    }
    public SingleGUI(int rows, String title, Location location) {
        this.title = UsefulString.get(title).hex().apply();
        this.inventory = Bukkit.createInventory(this, rows*9, this.title);
        this.location = location;
    }


    public void setLayout(GUILayout layout) {
        this.layout = layout;
    }

    public GUILayout getLayout() {
        return layout;
    }


    public List<Integer> getContentSlots() {
        List<Integer> baseSlots;

        if (layout != null) {
            baseSlots = new ArrayList<>(layout.getSlots());
        } else {
            baseSlots = new ArrayList<>();
            for (int slot = 0; slot < 54; slot++) {
                baseSlots.add(slot);
            }
        }

        baseSlots.removeIf(reservedItems::containsKey);

        return baseSlots;
    }

    public SingleGUI setItem(GUIItem GUIItem, Integer... slot) {
        for (Integer integer : slot) {
            GUIItems.put(integer, GUIItem);
        }
        return this;
    }

    public SingleGUI addItem(GUIItem GUIItem) {
        for (int slot : getContentSlots()) {
            if (!GUIItems.containsKey(slot)) {
                GUIItems.put(slot, GUIItem);
                return this;
            }
        }
        return this;
    }

    public SingleGUI removeItem(int slot) {
        GUIItems.remove(slot);
        return this;
    }

    public GUIItem getItem(int slot) {
        if (slot >= 54) {
            return new GUIItem((ItemStack) null);
        }
        if (reservedItems.containsKey(slot)) {
            return reservedItems.get(slot);
        }
        return GUIItems.get(slot);
    }

    public Map<Integer, GUIItem> getItems() { return GUIItems; }
    public void setItems(Map<Integer, GUIItem> buttons) {
        if (buttons == null) {
            this.GUIItems = new ConcurrentHashMap<>();
            return;
        }
        this.GUIItems = buttons;
    }

    /*
     * ---------------------------------------------------------
     * RESERVED ITEMS
     * ---------------------------------------------------------
     */
    public SingleGUI setReservedItem(GUIItem item, Integer... slots) {
        if (item == null || slots == null) {
            return this;
        }
        for (Integer slot : slots) {
            if (slot == null || slot < 0 || slot >= 54) {
                continue;
            }
            reservedItems.put(slot, item);
            GUIItems.remove(slot);
        }
        return this;
    }

    public SingleGUI removeReservedItem(int slot) {
        reservedItems.remove(slot);
        return this;
    }

    public Map<Integer, GUIItem> getReservedItems() {
        return reservedItems;
    }


    public SingleGUI onGUIClose(CloseListener onGUIClose) {
        this.closeListener = onGUIClose;
        return this;
    }
    public SingleGUI onGUIClick(ClickListener onGUIClick) {
        this.clickListener = onGUIClick;
        return this;
    }

    public CloseListener getGUICloseListener() { return closeListener; }
    public ClickListener getGUIClickListener() { return clickListener; }

    public void refreshGUI(Player player) {
        InventoryView view = player.getOpenInventory();

        if (view.getTopInventory().getHolder() != this) return;

        Inventory top = view.getTopInventory();

        for (int slot = 0; slot < top.getSize(); slot++) {
            ItemStack current = top.getItem(slot);
            GUIItem guiItem = getItem(slot);
            ItemStack updated = guiItem != null ? guiItem.getItemStack() : null;

            if (current == null && updated == null) continue;
            if (current != null && updated != null && current.isSimilar(updated)) continue;

            top.setItem(slot, updated);
        }
        player.updateInventory();
    }


    public Location getLocation() { return location; }


    @NotNull
    @Override
    public Inventory getInventory() {
        inventory.setMaxStackSize(64);

        for (Map.Entry<Integer, GUIItem> entry : GUIItems.entrySet()) {
            inventory.setItem(entry.getKey(), entry.getValue().getItemStack());
        }

        for (Map.Entry<Integer, GUIItem> entry : reservedItems.entrySet()) {
            inventory.setItem(entry.getKey(), entry.getValue().getItemStack());
        }

        return inventory;
    }

    public void setRefreshAction(Runnable refreshAction) {
        this.refreshAction = refreshAction;
    }

    public void setRefreshInterval(long ticks) {
        this.refreshInterval = ticks;
    }

    public void stopAutoRefresh() {
        if (refreshTask != null) {
            refreshTask.cancel();
            refreshTask = null;
        }
    }

    public void startAutoRefresh(Player player, Runnable action) {
        this.refreshAction = action;
        stopAutoRefresh();
        if (refreshInterval <= 0) {
            return;
        }
        refreshTask = Bukkit.getScheduler().runTaskTimer(
                instance,
                () -> {

                    if (!player.isOnline()) {
                        stopAutoRefresh();
                        return;
                    }

                    InventoryView view = player.getOpenInventory();

                    if (view.getTopInventory().getHolder() != this) {
                        stopAutoRefresh();
                        return;
                    }

                    if (refreshAction != null) {
                        refreshAction.run();
                    }

                    refreshGUI(player);

                },
                refreshInterval,
                refreshInterval
        );
    }
}
