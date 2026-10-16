package mv.mossuh.mocore.ITEM;

import de.tr7zw.nbtapi.iface.ReadWriteItemNBT;
import mv.mossuh.mocore.NBT.NBTMethods;
import mv.mossuh.mocore.UTILITIES.UsefulString;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class MoItemStack {

    private ItemStack itemStack = new ItemStack(Material.AIR, 1, (byte) 0);
    public MoItemStack(Material material, Byte data, Integer amount) {
        if (material == null || data == null || amount == null) {
            return;
        }
        if (amount > 64) { amount = 64; }
        this.itemStack = new ItemStack(material, amount, (short) 0, data);
    }

    public MoItemStack(Material material, Integer amount) {
        if (material == null || amount == null) {
            return;
        }
        if (amount > 64) { amount = 64; }
        this.itemStack = new ItemStack(material, amount);
    }

    public MoItemStack(ItemStack itemStack) {
        if (itemStack != null) { this.itemStack = itemStack; }
    }

    public MoItemStack setName(String name) {
        if (name == null) return this;
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta != null) {
            itemMeta.setDisplayName(UsefulString.get(name).hex().apply());
            itemStack.setItemMeta(itemMeta);
        }
        return this;
    }

    public MoItemStack setLore(String... lore) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta != null) {
            List<String> newLore = new ArrayList<>();
            for (String line : lore) {
                if (line == null) continue;
                newLore.add(UsefulString.get(line).hex().apply());
            }
            itemMeta.setLore(newLore);
            itemStack.setItemMeta(itemMeta);
        }
        return this;
    }

    public MoItemStack setLore(ArrayList<String> lore) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta != null) {
            if (lore == null || lore.isEmpty()) return this;
            List<String> newLore = new ArrayList<>();
            for (String line : lore) {
                newLore.add(UsefulString.get(line).hex().apply());
            }
            itemMeta.setLore(newLore);
            itemStack.setItemMeta(itemMeta);
        }
        return this;
    }

    public MoItemStack addLoreLine(String line) {
        if (line == null) return this;
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta != null) {
            List<String> lore = itemMeta.getLore();
            List<String> newLore;
            if (lore != null) {
                newLore = new ArrayList<>(lore);
            } else {
                newLore = new ArrayList<>();
            }
            newLore.add(UsefulString.get(line).hex().apply());
            itemMeta.setLore(newLore);
            itemStack.setItemMeta(itemMeta);
        }
        return this;
    }

    public MoItemStack setEnchantment(MoEnchant... enchantments) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta != null) {
            for (MoEnchant enchantment : enchantments) {
                if (enchantment.isEnchant()) {
                    itemMeta.addEnchant(enchantment.getEnchant(), enchantment.getLevel(), true);
                }
            }
            itemStack.setItemMeta(itemMeta);
        }
        return this;
    }

    public MoItemStack setEnchantment(List<MoEnchant> enchantments) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta != null) {
            for (MoEnchant enchantment : enchantments) {
                if (enchantment.isEnchant()) {
                    itemMeta.addEnchant(enchantment.getEnchant(), enchantment.getLevel(), true);
                }
            }
            itemStack.setItemMeta(itemMeta);
        }
        return this;
    }

    public MoItemStack addEnchantment(MoEnchant enchantment) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta != null) {
            if (enchantment.isEnchant()) {
                itemMeta.addEnchant(enchantment.getEnchant(), enchantment.getLevel(), true);
                itemStack.setItemMeta(itemMeta);
            }
        }
        return this;
    }

    public MoItemStack addFlag(ItemFlag... itemFlags) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta != null) {
            itemMeta.addItemFlags(itemFlags);
            itemStack.setItemMeta(itemMeta);
        }
        return this;
    }

    public MoItemStack setTags(Consumer<ReadWriteItemNBT> consumer) {
        NBTMethods.modify(itemStack, consumer);
        return this;
    }


    public boolean isItemStack() {
        return !itemStack.getType().equals(Material.AIR);
    }

    public ItemStack obtain() {
        return itemStack;
    }
}
