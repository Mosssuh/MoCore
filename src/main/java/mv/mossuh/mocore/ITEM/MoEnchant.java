package mv.mossuh.mocore.ITEM;

import org.bukkit.enchantments.Enchantment;

public class MoEnchant {

    private Enchantment enchant = null;
    private Integer level;
    public MoEnchant(Enchantment enchant, Integer level) {
        if (enchant != null) { this.enchant = enchant; }
        if (level < 0) {
            this.level = 0;
        } else {
            this.level = level;
        }
    }

    public Enchantment getEnchant() { return enchant; }
    public int getLevel() { return level; }

    public boolean isEnchant() {
        return enchant != null;
    }
}
