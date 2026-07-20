package mv.mossuh.mocore.ENUMS;

public enum EventType {
    // Con entidad, sin argumento
    BLOCK_BREAK(true, false),
    BLOCK_PLACE(true, false),
    PLAYER_CAUGHT_FISH(true, false),
    PLAYER_CAUGHT_ENTITY(true, false),
    PLAYER_KILLS(true, false),
    PLAYER_DIE(true, false),
    PLAYER_ATTACK(true, false),
    PLAYER_ATTACKED(true, false),
    ITEM_INTERACT(true, false),
    BLOCK_INTERACT(true, false),
    ENTITY_INTERACT(true, false),
    ITEM_CONSUME(true, false),
    ITEM_BREAK(true, false),
    ITEM_PICKUP(true, false),
    ITEM_DROP(true, false),
    ITEM_HELD(true, false),
    ITEM_UNHELD(true, false),
    PLAYER_EQUIP_PIECE(true, false),
    PLAYER_UNEQUIP_PIECE(true, false),
    ITEM_SELECT(true, false),
    ITEM_UNSELECT(true, false),
    ITEM_ENCHANT(true, false),
    ITEM_CRAFT(true, false),

    // Con argumentos
    PLAYER_CHANGE_WORLD(false, true),
    PLAYER_COMMAND(false, true),
    PLAYER_CHAT(false, true),

    // Sin argumentos ni entidad (simples)
    BLOCKIFY_BLOCK_BREAK(false, false),
    EDPRISON_BLOCK_BREAK(false, false),
    PLAYER_BED_ENTER(false, false),
    PLAYER_BED_LEAVE(false, false),
    PLAYER_JOIN(false, false),
    PLAYER_LEAVE(false, false),
    PLAYER_RESPAWN(false, false),
    PLAYER_FLY(false, false),
    PLAYER_UNFLY(false, false),
    PLAYER_SNEAK(false, false),
    PLAYER_UNSNEAK(false, false),
    PLAYER_SPRINT(false, false),
    PLAYER_UNSPRINT(false, false),
    PLAYER_LEVELUP(false, false),
    DRAG_AND_DROP_CLAIM(true, false),
    RIGHT_CLICK_CLAIM(true, false),

    // Por defecto
    NONE(false, false),

    // Inválido
    INVALID(false, false);

    private final boolean hasEntity;
    private final boolean hasArgument;

    EventType(boolean hasEntity, boolean hasArgument) {
        this.hasEntity = hasEntity;
        this.hasArgument = hasArgument;
    }

    public boolean hasEntity() {
        return hasEntity;
    }

    public boolean hasArgument() {
        return hasArgument;
    }

    public boolean isSimple() {
        return !hasEntity && !hasArgument;
    }
}
