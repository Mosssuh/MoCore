package mv.mossuh.mocore.UTILITIES.ARGS.RewardArgs;

import mv.mossuh.mocore.UTILITIES.ARGS.ArgType;
import mv.mossuh.mocore.UTILITIES.ARGS.Args;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class RewardArgs implements Args {


    private RewardArgsType rewardArgsType = RewardArgsType.NONE;

    private Entity entity = null;
    private String string = null;
    private LivingEntity livingEntity = null;
    private Player player = null;
    private UUID uuid = null;
    private Item item = null;
    private ItemStack itemStack = null;
    private Block block = null;

    private Location location = null;


    public RewardArgs(RewardArgsType rewardArgsType, String string) {
        if (rewardArgsType !=  null) { this.rewardArgsType = rewardArgsType; }
        this.string = string;
    }
    public RewardArgs(RewardArgsType rewardArgsType, LivingEntity livingEntity) {
        if (rewardArgsType !=  null) { this.rewardArgsType = rewardArgsType; }
        this.livingEntity = livingEntity;
        if (livingEntity != null) { this.location = livingEntity.getLocation(); }
    }
    public RewardArgs(RewardArgsType rewardArgsType, Entity entity) {
        if (rewardArgsType !=  null) { this.rewardArgsType = rewardArgsType; }
        this.entity = entity;
        if (entity != null) { this.location = entity.getLocation(); }
    }
    public RewardArgs(RewardArgsType rewardArgsType, Player player) {
        if (rewardArgsType !=  null) { this.rewardArgsType = rewardArgsType; }
        this.player = player;
        if (player != null) { this.location = player.getLocation(); }
    }
    public RewardArgs(RewardArgsType rewardArgsType, UUID uuid) {
        if (rewardArgsType !=  null) { this.rewardArgsType = rewardArgsType; }
        this.uuid = uuid;
    }
    public RewardArgs(RewardArgsType rewardArgsType, Item item) {
        if (rewardArgsType !=  null) { this.rewardArgsType = rewardArgsType; }
        this.item = item;
        if (item != null) { this.location = item.getLocation(); }
    }
    public RewardArgs(RewardArgsType rewardArgsType, ItemStack itemStack) {
        if (rewardArgsType !=  null) { this.rewardArgsType = rewardArgsType; }
        this.itemStack = itemStack;
    }
    public RewardArgs(RewardArgsType rewardArgsType, Block block) {
        if (rewardArgsType !=  null) { this.rewardArgsType = rewardArgsType; }
        this.block = block;
        if (block != null) { this.location = block.getLocation(); }
    }
    public RewardArgs(RewardArgsType rewardArgsType) {
        if (rewardArgsType !=  null) { this.rewardArgsType = rewardArgsType; }
    }

    public RewardArgsType getArgumentType() { return rewardArgsType; }
    public String getString() { return string; }
    public LivingEntity getLivingEntity() { return livingEntity; }
    public Entity getEntity() { return entity; }
    public Player getPlayer() { return player; }
    public UUID getUUID() { return uuid; }
    public Item getItem() { return item; }
    public ItemStack getItemStack() { return itemStack; }
    public Block getBlock() { return block; }


    public ArgType getArgType() { return ArgType.REWARD; }
    public boolean hasArgs() {
        return !rewardArgsType.equals(RewardArgsType.NONE);
    }
    public boolean hasLocation() { return location != null; }
    public Location getLocation() { return location; }
}
