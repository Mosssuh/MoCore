package mv.mossuh.mocore.GUI.Interfaces;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public interface ConfirmListener<T> {
    void onConfirm(Player player, ClickType clickType, T value);
    // void onConfirm(AsyncPlayerChatEvent paramAsyncPlayerChatEvent);
}
