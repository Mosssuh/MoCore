package mv.mossuh.mocore.GUI.Items;

import mv.mossuh.mocore.GUI.Interfaces.ConfirmListener;
import mv.mossuh.mocore.GUI.Interfaces.ClickListener;
import mv.mossuh.mocore.GUI.Interfaces.Utilities.ConfirmConfiguration;
import mv.mossuh.mocore.ENUMS.ResponseType;
import mv.mossuh.mocore.ITEM.MoItemStack;
import mv.mossuh.mocore.ITEM.MoSound;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class GUIItem {

    private ClickListener clickListener;

    private ConfirmListener<?> confirmListener;
    private ResponseType responseType;
    private ConfirmConfiguration confirmConfiguration = new ConfirmConfiguration();

    private ItemStack itemStack = new ItemStack(Material.AIR, 1);
    private MoSound MoSound = new MoSound(null, null, null);

    public GUIItem(MoItemStack MoItemStack) {
        if (MoItemStack != null) { this.itemStack = MoItemStack.obtain(); }
    }
    public GUIItem(ItemStack itemStack) {
        if (itemStack != null) { this.itemStack = itemStack; }
    }

    public ItemStack getItemStack() {
        return itemStack;
    }
    public MoItemStack getItemStackEditor() { return new MoItemStack(itemStack); }

    public GUIItem onItemClick(ClickListener clickListener) {
        this.clickListener = clickListener;
        return this;
    }

    public <T> GUIItem onConfirm(ConfirmListener<T> confirmListener, ResponseType responseType) {
        this.confirmListener = confirmListener;
        this.responseType = responseType;
        return this;
    }
    public void setConfirmConfiguration(ConfirmConfiguration configuration) {
        this.confirmConfiguration = configuration;
    }
    public ConfirmConfiguration getConfirmConfiguration() { return this.confirmConfiguration; }

    @SuppressWarnings("unchecked")
    public <T> ConfirmListener<T> getConfirmListener() {
        return (ConfirmListener<T>) this.confirmListener;
    }
    public ResponseType getResponseType() {
        return this.responseType;
    }

    public MoSound getClickSound() {
        return this.MoSound;
    }
    public GUIItem setClickSound(MoSound clickSound) { this.MoSound = clickSound; return this; }
    public ClickListener getItemListener() {
        return clickListener;
    }

    public void setItemStack(ItemStack itemStack) {
        if (itemStack != null) { this.itemStack = itemStack; }
    }
    public void setItemStack(MoItemStack itemStack) {
        if (itemStack != null) { this.itemStack = itemStack.obtain(); }
    }
}
