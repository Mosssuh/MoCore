package mv.mossuh.mocore.NBT.TEXTUREHEADS.Data;

import mv.mossuh.mocore.NBT.TEXTUREHEADS.Utilities.TextureHead;

import java.util.*;

public class TextureHeadsCache {

    private static final Set<TextureHead> textureHeads = new HashSet<>();

    public static void addTextureValue(TextureHead head) {
        textureHeads.add(head);
    }

    public static Set<TextureHead> getTextureHeads() {
        return textureHeads;
    }

    public static TextureHead getByValue(String value) {
        for (TextureHead h : textureHeads) {
            if (h.getValue().equals(value)) {
                return h;
            }
        }
        return new TextureHead(null, null);
    }

    public static TextureHead getByUUID(UUID uuid) {
        for (TextureHead h : textureHeads) {
            if (h.getUUID() == uuid) {
                return h;
            }
        }
        return new TextureHead(null, null);
    }


    public static boolean hasTextureHead(UUID uuid) {
        for (TextureHead h : textureHeads) {
            if (h.getUUID() == uuid) {
                return true;
            }
        }
        return false;
    }

    public static boolean hasTextureHead(String value) {
        for (TextureHead h : textureHeads) {
            if (h.getValue().equals(value)) {
                return true;
            }
        }
        return false;
    }
}
