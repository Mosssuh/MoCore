package mv.mossuh.mocore.GUI.Holders;

import mv.mossuh.mocore.GUI.Interfaces.ClickListener;
import mv.mossuh.mocore.GUI.Interfaces.CloseListener;
import mv.mossuh.mocore.GUI.Items.GUIItem;
import mv.mossuh.mocore.GUI.PaginatedUtilities.PaginatedInventoryConfiguration;
import mv.mossuh.mocore.UTILITIES.UsefulString;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PaginatedGUI implements InventoryHolder {
    private PaginatedInventoryConfiguration configuration = new PaginatedInventoryConfiguration();
    private CloseListener closeListener;
    private ClickListener clickListener;

    private Map<Integer, GUIItem> GUIItems = new ConcurrentHashMap<>();
    private int currentPage = 0;
    private String name;

    public PaginatedGUI(String name) {
        this.name = ChatColor.translateAlternateColorCodes('&', name);
    }

    public PaginatedGUI(String name, PaginatedInventoryConfiguration config) {
        this.name = UsefulString.get(name).hex().apply();
        this.configuration = config;
    }

    public void setDisplayName(String name) {
        this.name = UsefulString.get(name).hex().apply();
    }

    public void addItem(GUIItem button) {
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

        GUIItems.put(slot, button);
    }

    public void onGUIClose(CloseListener onGUIClose) {
        this.closeListener = onGUIClose;
    }

    public void onGUIClick(ClickListener onGUIClick) {
        this.clickListener = onGUIClick;
    }

    public CloseListener getGUICloseListener() {
        return closeListener;
    }

    public ClickListener getGUIClickListener() {
        return clickListener;
    }

    public void setItem(int slot, GUIItem button) {
        GUIItems.put(slot, button);
    }
    public void setItem(GUIItem GUIItem, Integer... slot) {
        for (Integer integer : slot) {
            GUIItems.put(integer, GUIItem);
        }
    }

    public void removeItem(int slot) {
        GUIItems.remove(slot);
    }

    public GUIItem getItem(int slot) {
        return this.currentPage > 0 ? GUIItems.get(slot + 45 * this.currentPage) : GUIItems.get(slot);
    }

    public boolean nextPage() {
        if (this.currentPage < this.getFinalPage()) {
            ++this.currentPage;
            return true;
        } else {
            return false;
        }
    }

    public boolean previousPage() {
        if (this.currentPage > 0) {
            --this.currentPage;
            return true;
        } else {
            return false;
        }
    }

    /** @deprecated */
    public int getMaxPage() {
        return this.getFinalPage();
    }

    public int getFinalPage() {
        int slot = 0;

        for (int nextSlot : GUIItems.keySet()) {
            if (nextSlot > slot) {
                slot = nextSlot;
            }
        }

        double highestSlot = slot + 1;
        return (int)Math.ceil(highestSlot / 45.0D) - 1;
    }

    public void refreshInventory(Player player) {
        Inventory inventory = getInventory();
        if (player.getOpenInventory() != null) {
            player.getOpenInventory().getTopInventory().setContents(inventory.getContents());
        } else {
            player.openInventory(inventory);
        }
    }

    @NotNull
    @Override
    public Inventory getInventory() {
        Inventory inventory = Bukkit.createInventory(this, 54, this.name);
        GUIItem backItem = new GUIItem(configuration.getBackItem());
        GUIItem nextItem = new GUIItem(configuration.getNextItem());

        backItem.onItemClick((event) -> {
            event.setCancelled(true);
            Player player = event.getPlayer();

            PaginatedGUI menu = (PaginatedGUI) event.getInventory().getHolder();
            if (menu != null) {
                if (!menu.previousPage()) {
                    UsefulString.get(configuration.getNoPreviousPages()).hex().sendMessage(player);
                } else {
                    refreshInventory(player);
                }
            }
        });

        nextItem.onItemClick((event) -> {
            event.setCancelled(true);
            Player player = event.getPlayer();

            PaginatedGUI menu = (PaginatedGUI) event.getInventory().getHolder();
            if (menu != null) {
                if (!menu.nextPage()) {
                    UsefulString.get(configuration.getNoAdditionalPages()).hex().sendMessage(player);
                } else {
                    refreshInventory(player);
                }
            }
        });

        int backSlot = configuration.getBackItemSlot();
        int nextSlot = configuration.getNextItemSlot();

        inventory.setItem(backSlot, backItem.getItemStack());
        inventory.setItem(nextSlot, nextItem.getItemStack());

        int counter = 0;

        for (int key = currentPage * 45; key <= Collections.max(GUIItems.keySet()) && counter < 54; ++key) {
            if (counter == backSlot || counter == nextSlot) {
                ++counter;
                continue;
            }

            if (GUIItems.containsKey(key)) {
                inventory.setItem(counter, GUIItems.get(key).getItemStack());
            }

            ++counter;
        }

        return inventory;
    }

    public PaginatedInventoryConfiguration getConfiguration() {
        return this.configuration;
    }
}

