package mv.mossuh.mocore.ITEM;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class MoSound {

    private static boolean versionCheck() {
        String v = Bukkit.getBukkitVersion();
        return v.contains("1.9") || v.contains("1.10") || v.contains("1.11") || v.contains("1.12") || v.contains("1.13") || v.contains("1.14") || v.contains("1.15") || v.contains("1.16") || v.contains("1.17") || v.contains("1.18") || v.contains("1.19") || v.contains("1.20") || v.contains("1.21");
    }

    public static final MoSound CLICK = new MoSound(versionCheck() ? Sound.valueOf("UI_BUTTON_CLICK") : Sound.valueOf("CLICK"));

    public static final MoSound FAIL = new MoSound(versionCheck() ? Sound.valueOf("ENTITY_ITEM_BREAK") : Sound.valueOf("ITEM_BREAK"));

    public static final MoSound WRONG = new MoSound(versionCheck() ? Sound.valueOf("ENTITY_CHICKEN_EGG") : Sound.valueOf("CHICKEN_EGG_POP"));

    public static final MoSound SUCCESS = new MoSound(versionCheck() ? Sound.valueOf("ENTITY_EXPERIENCE_ORB_PICKUP") : Sound.valueOf("ORB_PICKUP"));

    private Sound sound;
    private float pitch = 1.0F;
    private float volume = 1.0F;

    public MoSound(Sound sound) {
        if (sound != null) { this.sound = sound; }
    }

    public MoSound(Sound sound, Float pitch) {
        if (sound != null) { this.sound = sound; }
        if (pitch != null) { this.pitch = pitch; }
    }

    public MoSound(Sound sound, Float pitch, Float volume) {
        if (sound != null) { this.sound = sound; }
        if (pitch != null) { this.pitch = pitch; }
        if (volume != null) { this.volume = volume; }
    }

    public boolean isSound() {
        return sound != null;
    }

    public Sound getSound() { return sound; }
    public float getPitch() { return pitch; }
    public float getVolume() { return volume; }


    public void play(Location pos) {
        pos.getWorld().playSound(pos, this.sound, this.volume, this.pitch);
    }

    public void play(Player... players) {
        for (Player player : players)
            player.playSound(player.getLocation(), this.sound, this.volume, this.pitch);
    }
}
