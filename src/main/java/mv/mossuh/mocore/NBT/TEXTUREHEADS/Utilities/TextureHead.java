package mv.mossuh.mocore.NBT.TEXTUREHEADS.Utilities;

import java.util.UUID;

public class TextureHead {
    private UUID uuid;
    private String value = "";

    public TextureHead(UUID uuid, String value) {
        this.uuid = uuid;
        if (value != null) { this.value = value; }
    }

    public UUID getUUID() { return uuid; }
    public String getValue() { return value; }

    public boolean isValid() {
        return uuid != null && !value.isEmpty() && value.startsWith("ey");
    }
}
