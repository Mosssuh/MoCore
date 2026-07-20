package mv.mossuh.mocore.GUI.Holders;

import mv.mossuh.mocore.GUI.Interfaces.ClickListener;
import mv.mossuh.mocore.GUI.Interfaces.CloseListener;
import mv.mossuh.mocore.GUI.Items.GUIItem;
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
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SingleGUI implements InventoryHolder {
    private static final MoCore instance = MoCore.getInstance();
    private CloseListener closeListener;
    private ClickListener clickListener;
    private InventoryType inventoryType;
    private String title;
    private Map<Integer, GUIItem> GUIItems = new ConcurrentHashMap<>();
    private Inventory inventory = null;
    private Location location = null;

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

    public SingleGUI setItem(int slot, GUIItem GUIItem) {
        GUIItems.put(slot, GUIItem);
        return this;
    }

    public SingleGUI setItem(GUIItem GUIItem, Integer... slot) {
        for (Integer integer : slot) {
            GUIItems.put(integer, GUIItem);
        }
        return this;
    }

    public SingleGUI addItem(GUIItem GUIItem) {
        int slot = 0;
        if (!GUIItems.isEmpty()) {
            int highestSlot = -1;

            for (int itemSlot : GUIItems.keySet()) {
                if (itemSlot > highestSlot) {
                    highestSlot = itemSlot;
                }
            }

            slot = highestSlot + 1;
        }
        GUIItems.put(slot, GUIItem);
        return this;
    }

    public SingleGUI removeItem(int slot) {
        GUIItems.remove(slot);
        return this;
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

    public GUIItem getButton(int slot) {
        return slot < 54 ? GUIItems.get(slot) : new GUIItem((ItemStack) null);
    }

    public void refreshGUI(Player player) {
        InventoryView view = player.getOpenInventory();

        if (view.getTopInventory().getHolder() != this) return;

        Inventory top = view.getTopInventory();
        for (int slot : GUIItems.keySet()) {
            GUIItem guiItem = GUIItems.get(slot);
            top.setItem(slot, guiItem != null ? guiItem.getItemStack() : null);
        }
        player.updateInventory();
    }
    public Location getLocation() { return location; }


    @NotNull
    @Override
    public Inventory getInventory() {
        inventory.setMaxStackSize(64);

        for (int slot : GUIItems.keySet()) {
            GUIItem GUIItem = GUIItems.get(slot);
            inventory.setItem(slot, GUIItem.getItemStack());
        }

        return inventory;
    }
}
