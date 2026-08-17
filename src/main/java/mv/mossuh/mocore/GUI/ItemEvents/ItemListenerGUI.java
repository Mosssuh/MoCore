package mv.mossuh.mocore.GUI.ItemEvents;

import com.google.common.collect.Maps;
import mv.mossuh.mocore.EVENTS.ItemClickEvent.ItemClickEvent;
import mv.mossuh.mocore.GUI.Holders.PaginatedGUI;
import mv.mossuh.mocore.GUI.Holders.SingleGUI;
import mv.mossuh.mocore.GUI.Interfaces.ClickListener;
import mv.mossuh.mocore.GUI.Interfaces.Utilities.ConfirmConfiguration;
import mv.mossuh.mocore.ENUMS.ResponseType;
import mv.mossuh.mocore.GUI.Items.GUIItem;
import mv.mossuh.mocore.ITEM.MoSound;
import mv.mossuh.mocore.UTILITIES.UsefulString;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ItemListenerGUI implements Listener {
    private final Map<UUID, ConfirmItem> needConfirm = Maps.newHashMap();

    @EventHandler
    public void itemClickEvent(ItemClickEvent event) {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        Inventory inventory = event.getInventory();
        InventoryHolder holder = inventory.getHolder();
        int clickedSlot = event.getClickedSlot();
        ClickType clickType = event.getClickType();

        GUIItem guiItem;
        if (holder instanceof PaginatedGUI) {
            PaginatedGUI paginatedGUI = (PaginatedGUI) holder;
            guiItem = paginatedGUI.getItem(clickedSlot);
            if (guiItem != null && guiItem.getItemListener() != null) {
                ClickListener itemListener = guiItem.getItemListener();
                MoSound MoSound = guiItem.getClickSound();

                itemListener.onClick(event);
                if (MoSound.isSound()) {
                    Location location = player.getEyeLocation();
                    Sound sound = MoSound.getSound();
                    float volume = MoSound.getVolume();
                    float pitch = MoSound.getPitch();
                    player.playSound(location, sound, volume, pitch);
                }
            } else if (guiItem != null && guiItem.getConfirmListener() != null) {
                event.setCancelled(true);
                event.getView().close();

                this.needConfirm.put(uuid, new ConfirmItem(clickType, guiItem));
                ConfirmConfiguration config = guiItem.getConfirmConfiguration();
                List<String> message = config.getRequestingMessage();
                if (message.isEmpty()) return;
                for (String l : message) {
                    UsefulString.get(l).hex().sendMessage(player);
                }
            }
        } else if (holder instanceof SingleGUI) {
            SingleGUI singleGUI = (SingleGUI) holder;
            guiItem = singleGUI.getItem(clickedSlot);

            if (guiItem != null && guiItem.getItemListener() != null) {
                ClickListener itemListener = guiItem.getItemListener();
                MoSound MoSound = guiItem.getClickSound();

                itemListener.onClick(event);
                if (MoSound.isSound()) {
                    Location location = player.getEyeLocation();
                    Sound sound = MoSound.getSound();
                    float volume = MoSound.getVolume();
                    float pitch = MoSound.getPitch();
                    player.playSound(location, sound, volume, pitch);
                }
            } else if (guiItem != null && guiItem.getConfirmListener() != null) {
                event.setCancelled(true);
                event.getView().close();

                this.needConfirm.put(uuid, new ConfirmItem(clickType, guiItem));
                ConfirmConfiguration config = guiItem.getConfirmConfiguration();
                List<String> message = config.getRequestingMessage();
                if (message.isEmpty()) return;
                for (String l : message) {
                    UsefulString.get(l).hex().sendMessage(player);
                }
            }
        }
    }


    @EventHandler(priority = EventPriority.LOW)
    public void onChat(AsyncPlayerChatEvent event) {

        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();

        if (!this.needConfirm.containsKey(uuid)) return;

        event.setCancelled(true);

        String message = event.getMessage().trim();

        ConfirmItem confirmItem = this.needConfirm.get(uuid);
        GUIItem guiItem = confirmItem.getItem();
        ClickType clickType = confirmItem.getClickType();

        ResponseType response = guiItem.getResponseType();
        ConfirmConfiguration config = guiItem.getConfirmConfiguration();

        // Cancelador
        if (config.getCanceller().equalsIgnoreCase(message)) {
            this.needConfirm.remove(uuid);
            UsefulString.get(config.getCancelledMessage()).hex().sendMessage(player);
            return;
        }

        String wrongMessage = config.getWrongAnswerMessage();
        MoSound wrongSound = config.getWrongAnswerSound();

        try {
            switch (response) {
                case YES: {
                    if (!message.equalsIgnoreCase("yes")) {
                        sendWrong(player, wrongMessage, wrongSound);
                        return;
                    }

                    this.needConfirm.remove(uuid);
                    guiItem.getConfirmListener().onConfirm(player, clickType, true);
                    break;
                }

                case TEXT: {
                    if (message.isEmpty()) {
                        sendWrong(player, wrongMessage, wrongSound);
                        return;
                    }

                    this.needConfirm.remove(uuid);
                    guiItem.getConfirmListener().onConfirm(player, clickType, message);
                    break;
                }

                case BOOLEAN: {
                    if (!message.equalsIgnoreCase("true") && !message.equalsIgnoreCase("false")) {
                        sendWrong(player, wrongMessage, wrongSound);
                        return;
                    }

                    boolean value = Boolean.parseBoolean(message);
                    this.needConfirm.remove(uuid);
                    guiItem.getConfirmListener().onConfirm(player, clickType, value);
                    break;
                }

                case DOUBLE: {
                    // Normalización decimal (1 , 1.0 , 1,0)
                    String normalized = message.replace(",", ".");

                    // Acepta enteros y decimales
                    if (!normalized.matches("-?\\d+(\\.\\d+)?")) {
                        sendWrong(player, wrongMessage, wrongSound);
                        return;
                    }

                    try {
                        double value = Double.parseDouble(normalized);

                        this.needConfirm.remove(uuid);
                        guiItem.getConfirmListener().onConfirm(player, clickType, value);
                        return;
                    } catch (NumberFormatException e) {
                        sendWrong(player, wrongMessage, wrongSound);
                        return;
                    }
                }
                case INTEGER: {
                    // Only valid integers
                    if (!message.matches("-?\\d+")) {
                        sendWrong(player, wrongMessage, wrongSound);
                        return;
                    }

                    try {
                        int value = Integer.parseInt(message);

                        this.needConfirm.remove(uuid);
                        guiItem.getConfirmListener().onConfirm(player, clickType, value);
                        return;
                    } catch (NumberFormatException e) {
                        sendWrong(player, wrongMessage, wrongSound);
                        return;
                    }
                }
                case LONG: {
                    if (!message.matches("-?\\d+")) {
                        sendWrong(player, wrongMessage, wrongSound);
                        return;
                    }

                    try {
                        long value = Long.parseLong(message);

                        this.needConfirm.remove(uuid);
                        guiItem.getConfirmListener().onConfirm(player, clickType, value);
                        return;
                    } catch (NumberFormatException e) {
                        sendWrong(player, wrongMessage, wrongSound);
                        return;
                    }
                }
            }

        } catch (Exception ex) {
            // Failed
            ex.printStackTrace();
        }
    }

    private void sendWrong(Player player, String msg, MoSound sound) {
        if (msg != null && !msg.isEmpty()) {
            UsefulString.get(msg).hex().sendMessage(player);
        }
        if (sound != null) {
            sound.play(player);
        }
    }



    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        if (this.needConfirm.containsKey(event.getPlayer().getUniqueId())) {
            this.needConfirm.remove(event.getPlayer().getUniqueId());
        }
    }
}
