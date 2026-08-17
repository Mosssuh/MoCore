package mv.mossuh.mocore.GUI.Holders;

import mv.mossuh.mocore.GUI.Interfaces.ClickListener;
import mv.mossuh.mocore.GUI.Interfaces.CloseListener;
import mv.mossuh.mocore.GUI.Items.GUIItem;
import mv.mossuh.mocore.GUI.Layouts.GUILayout;
import mv.mossuh.mocore.GUI.PaginatedUtilities.PaginatedInventoryConfiguration;
import mv.mossuh.mocore.MoCore;
import mv.mossuh.mocore.UTILITIES.UsefulString;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class PaginatedGUI implements InventoryHolder {
    private final MoCore instance = MoCore.getInstance();

    private long refreshInterval = -1;
    private BukkitTask refreshTask;
    private Runnable refreshAction;

    private PaginatedInventoryConfiguration configuration = new PaginatedInventoryConfiguration();
    private CloseListener closeListener;
    private ClickListener clickListener;

    // Saves Inventory Slot, GUIItem
    private List<GUIItem> layoutItems = new CopyOnWriteArrayList<>();
    private Map<Integer, GUIItem> reservedItems = new ConcurrentHashMap<>();
    private int currentPage = 0;
    private String name;
    private GUILayout layout;

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

    public void setLayout(GUILayout layout) {
        this.layout = layout;

        if (currentPage > getFinalPage()) {
            currentPage = 0;
        }
    }

    public GUILayout getLayout() {
        return layout;
    }

    /*
     * ---------------------------------------------------------
     * DYNAMIC ITEMS
     * ---------------------------------------------------------
     */
    public void addItem(GUIItem item) {
        if (item == null) {
            return;
        }
        layoutItems.add(item);
    }

    public void addItems(Collection<GUIItem> items) {
        if (items == null) {
            return;
        }
        for (GUIItem item : items) {
            addItem(item);
        }
    }

    public void removeItem(int index) {
        if (index < 0 || index >= layoutItems.size()) {
            return;
        }
        layoutItems.remove(index);

        if (currentPage > getFinalPage()) {
            currentPage = getFinalPage();
        }
    }

    public void removeItem(GUIItem item) {
        if (item == null) {
            return;
        }
        layoutItems.remove(item);

        if (currentPage > getFinalPage()) {
            currentPage = getFinalPage();
        }
    }

    public void setItems(Collection<GUIItem> items) {
        if (items == null) {
            layoutItems = new CopyOnWriteArrayList<>();
            currentPage = 0;
            return;
        }
        layoutItems = new CopyOnWriteArrayList<>(items);

        if (currentPage > getFinalPage()) {
            currentPage = 0;
        }
    }

    public List<GUIItem> getItems() {
        return Collections.unmodifiableList(layoutItems);
    }

    public List<GUIItem> getCurrentPageItems() {
        int itemsPerPage = getContentSlots().size();

        if (itemsPerPage <= 0 || layoutItems.isEmpty()) {
            return Collections.emptyList();
        }

        int startIndex = currentPage * itemsPerPage;

        if (startIndex >= layoutItems.size()) {
            return Collections.emptyList();
        }

        int endIndex = Math.min(startIndex + itemsPerPage, layoutItems.size());

        return layoutItems.subList(startIndex, endIndex);
    }

    public GUIItem getItem(int slot) {
        if (reservedItems.containsKey(slot)) {
            return reservedItems.get(slot);
        }

        if (slot == configuration.getBackItemSlot()) {
            return backItem;
        }

        if (slot == configuration.getNextItemSlot()) {
            return nextItem;
        }

        List<Integer> contentSlots = getContentSlots();
        int indexInPage = contentSlots.indexOf(slot);

        if (indexInPage == -1) {
            return null;
        }

        int listIndex = currentPage * contentSlots.size() + indexInPage;

        if (listIndex < 0 || listIndex >= layoutItems.size()) {
            return null;
        }

        return layoutItems.get(listIndex);
    }

    public List<Integer> getContentSlots() {

        /*
         * -----------------------------------------------------
         * WITH LAYOUT
         * -----------------------------------------------------
         */
        if (layout != null) {
            List<Integer> slots = new ArrayList<>();

            for (int slot : layout.getSlots()) {
                if (reservedItems.containsKey(slot)) {
                    continue;
                }
                if (slot == configuration.getBackItemSlot()) {
                    continue;
                }
                if (slot == configuration.getNextItemSlot()) {
                    continue;
                }
                slots.add(slot);
            }

            return slots;
        }

        /*
         * -----------------------------------------------------
         * WITHOUT LAYOUT
         * -----------------------------------------------------
         */
        List<Integer> slots = new ArrayList<>();

        for (int slot = 0; slot < 54; slot++) {
            if (slot == configuration.getBackItemSlot()) {
                continue;
            }
            if (slot == configuration.getNextItemSlot()) {
                continue;
            }
            if (reservedItems.containsKey(slot)) {
                continue;
            }
            slots.add(slot);
        }

        return slots;
    }



    /*
     * ---------------------------------------------------------
     * STATIC ITEMS
     * ---------------------------------------------------------
     */
    public void setReservedItem(GUIItem item, Integer... slots) {
        if (item == null || slots == null) {
            return;
        }
        for (Integer slot : slots) {
            if (slot == null) {
                continue;
            }
            if (slot < 0 || slot >= 54) {
                continue;
            }
            reservedItems.put(slot, item);
        }
    }

    public void removeReservedItem(int slot) {
        reservedItems.remove(slot);
    }

    public Map<Integer, GUIItem> getReservedItems() {
        return reservedItems;
    }


    /*
     * ---------------------------------------------------------
     * PAGINATION
     * ---------------------------------------------------------
     */

    public boolean nextPage() {
        if (currentPage < getFinalPage()) {
            currentPage++;
            return true;
        }
        return false;
    }

    public boolean previousPage() {
        if (currentPage > 0) {
            currentPage--;
            return true;
        }
        return false;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int getFinalPage() {
        int itemsPerPage = getContentSlots().size();

        if (itemsPerPage <= 0 || layoutItems.isEmpty()) {
            return 0;
        }

        return (int) Math.ceil((double) layoutItems.size() / itemsPerPage) - 1;
    }

    /*
     * ---------------------------------------------------------
     * RENDERING
     * ---------------------------------------------------------
     */

    public void refreshGUI(Player player) {
        if (player == null || !player.isOnline()) {
            return;
        }

        InventoryView view = player.getOpenInventory();

        if (view == null) {
            return;
        }

        Inventory inventory = view.getTopInventory();

        if (inventory.getHolder() != this) {
            return;
        }

        /*
         * ---------------------------------------------------------
         * DYNAMIC ITEMS
         * ---------------------------------------------------------
         */
        List<Integer> contentSlots = getContentSlots();
        List<GUIItem> dynamicItems = getCurrentPageItems();

        for (int position = 0; position < contentSlots.size(); position++) {

            int slot = contentSlots.get(position);

            GUIItem guiItem = position < dynamicItems.size()
                    ? dynamicItems.get(position)
                    : null;

            ItemStack newItem = guiItem != null
                    ? guiItem.getItemStack()
                    : null;

            inventory.setItem(slot, newItem);
        }

        /*
         * ---------------------------------------------------------
         * RESERVED ITEMS
         * ---------------------------------------------------------
         */
        for (Map.Entry<Integer, GUIItem> entry : reservedItems.entrySet()) {

            GUIItem item = entry.getValue();

            if (item == null) {
                continue;
            }

            inventory.setItem(
                    entry.getKey(),
                    item.getItemStack()
            );
        }
    }

    private GUIItem backItem;
    private GUIItem nextItem;
    @NotNull
    @Override
    public Inventory getInventory() {
        Inventory inventory = Bukkit.createInventory(this, 54, this.name);

        /*
         * RESERVED ITEMS
         */
        for (Map.Entry<Integer, GUIItem> entry : reservedItems.entrySet()) {
            GUIItem item = entry.getValue();

            if (item == null) {
                continue;
            }

            inventory.setItem(entry.getKey(), item.getItemStack());
        }

        /*
         * BACK ITEM
         */
        GUIItem backItem = new GUIItem(configuration.getBackItem());

        backItem.onItemClick(event -> {
            event.setCancelled(true);

            Player player = event.getPlayer();

            if (!previousPage()) {
                UsefulString.get(configuration.getNoPreviousPages())
                        .hex()
                        .sendMessage(player);
                return;
            }

            refreshGUI(player);
        });

        inventory.setItem(configuration.getBackItemSlot(), backItem.getItemStack());
        this.backItem = backItem;

        /*
         * NEXT ITEM
         */
        GUIItem nextItem = new GUIItem(configuration.getNextItem());

        nextItem.onItemClick(event -> {
            event.setCancelled(true);

            Player player = event.getPlayer();

            if (!nextPage()) {
                UsefulString.get(configuration.getNoAdditionalPages())
                        .hex()
                        .sendMessage(player);
                return;
            }

            refreshGUI(player);
        });

        inventory.setItem(configuration.getNextItemSlot(), nextItem.getItemStack());
        this.nextItem = nextItem;

        /*
         * DYNAMIC ITEMS
         */
        List<Integer> contentSlots = getContentSlots();
        List<GUIItem> dynamicItems = getCurrentPageItems();

        for (int position = 0; position < dynamicItems.size(); position++) {
            GUIItem item = dynamicItems.get(position);

            if (item == null) {
                continue;
            }

            int inventorySlot = contentSlots.get(position);
            inventory.setItem(inventorySlot, item.getItemStack());
        }

        return inventory;
    }

    public PaginatedInventoryConfiguration getConfiguration() {
        return this.configuration;
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
                    if (!(player.getOpenInventory().getTopInventory().getHolder() instanceof PaginatedGUI)) {
                        stopAutoRefresh();
                        return;
                    }
                    PaginatedGUI opened = (PaginatedGUI) player.getOpenInventory().getTopInventory().getHolder();
                    if (opened != this) {
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

