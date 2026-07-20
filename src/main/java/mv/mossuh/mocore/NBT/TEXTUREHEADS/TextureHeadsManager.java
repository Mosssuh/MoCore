package mv.mossuh.mocore.NBT.TEXTUREHEADS;

import mv.mossuh.mocore.NBT.TEXTUREHEADS.Data.TextureHeadsCache;
import mv.mossuh.mocore.NBT.TEXTUREHEADS.Data.TextureHeadsData;
import mv.mossuh.mocore.NBT.TEXTUREHEADS.Utilities.TextureHead;

import java.util.UUID;

public class TextureHeadsManager {

    public static void addTextureValue(TextureHead head) {
        if (!TextureHeadsCache.hasTextureHead(head.getUUID())) {
            TextureHeadsData.addTextureHead(head);
            TextureHeadsCache.addTextureValue(head);
        }
    }

    public static TextureHead getByValue(String value) {
        return TextureHeadsCache.getByValue(value);
    }

    public static TextureHead getByUUID(UUID uuid) {
        return TextureHeadsCache.getByUUID(uuid);
    }

    public static boolean hasTextureHead(UUID uuid) {
        return TextureHeadsCache.hasTextureHead(uuid);
    }
    public static boolean hasTextureHead(String value) {
        return TextureHeadsCache.hasTextureHead(value);
    }
}
