package mv.mossuh.mocore.NBT;

import de.tr7zw.nbtapi.NBT;
import de.tr7zw.nbtapi.iface.ReadWriteNBT;
import de.tr7zw.nbtapi.iface.ReadWriteNBTList;
import de.tr7zw.nbtapi.iface.ReadableNBTList;
import mv.mossuh.mocore.NBT.TEXTUREHEADS.TextureHeadsManager;
import mv.mossuh.mocore.NBT.TEXTUREHEADS.Utilities.TextureHead;
import mv.mossuh.mocore.VERSION.ServerVersion;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.UUID;
import java.util.zip.GZIPInputStream;

public class NBTMethods {

    public static String decodeBase64AndDecompress(String base64) throws IOException {
        byte[] compressedData = Base64.getDecoder().decode(base64);
        try (GZIPInputStream gzipInputStream = new GZIPInputStream(new ByteArrayInputStream(compressedData))) {
            byte[] buffer = new byte[1024];
            StringBuilder output = new StringBuilder();
            int length;
            while ((length = gzipInputStream.read(buffer)) != -1) {
                output.append(new String(buffer, 0, length));
            }
            return output.toString();
        }
    }

    public static void setHeadTexture(ItemStack item, String value) {
        if (ServerVersion.isAtLeast(ServerVersion.MC1_20_5)) {
            setHeadTextureNewVersion(item, value);
        } else {
            setHeadTextureOldVersion(item, value);
        }
    }

    private static void setHeadTextureNewVersion(ItemStack item, String value) {
        NBT.modifyComponents(item, nbt -> {
            TextureHead head = TextureHeadsManager.getByValue(value);
            UUID uuid;
            if (head.isValid()) {
                uuid = head.getUUID();
            } else {
                uuid = UUID.randomUUID();
                TextureHeadsManager.addTextureValue(new TextureHead(uuid, value));
            }

            ReadWriteNBT profileNbt = nbt.getOrCreateCompound("minecraft:profile");
            profileNbt.setUUID("id", uuid);
            ReadWriteNBT propertiesNbt = profileNbt.getCompoundList("properties").addCompound();
            propertiesNbt.setString("name", "textures");
            propertiesNbt.setString("value", value);

        });
    }

    private static void setHeadTextureOldVersion(ItemStack item, String value) {
        if (item != null && item.getType() != Material.AIR && item.getAmount() > 0) {
            NBT.modify(item, nbt -> {
                TextureHead head = TextureHeadsManager.getByValue(value);
                UUID uuid;
                if (head.isValid()) {
                    uuid = head.getUUID();
                } else {
                    uuid = UUID.randomUUID();
                    TextureHeadsManager.addTextureValue(new TextureHead(uuid, value));
                }

                ReadWriteNBT compund = nbt.getOrCreateCompound("SkullOwner");

                compund.setUUID("Id", uuid);
                compund.getOrCreateCompound("Properties")
                        .getCompoundList("textures")
                        .addCompound()
                        .setString("Value", value);
            });
        }
    }

    public static void setString(ItemStack item, String name, String valor) {
        if (item != null && item.getType() != Material.AIR && item.getAmount() > 0) {
            NBT.modify(item, tags -> {
                tags.setString(name, valor);
            });
        }
    }

    public static String getString(ItemStack item, String key) {
        if (item != null && item.getType() != Material.AIR && item.getAmount() > 0) {
            String string = NBT.readNbt(item).getString(key);
            if (!string.isEmpty()) {
                return string;
            }
        }
        return null;
    }

    public static void setBoolean(ItemStack item, String name, Boolean valor) {
        if (item != null && item.getType() != Material.AIR && item.getAmount() > 0) {
            NBT.modify(item, tags -> {
                tags.setBoolean(name, valor);
            });
        }
    }

    public static boolean getBoolean(ItemStack item, String key) {
        if (item != null && item.getType() != Material.AIR && item.getAmount() > 0) {
            Boolean valor = NBT.readNbt(item).getBoolean(key);
            if (valor != null) {
                return valor;
            }
        }
        return false;
    }

    public static void setInteger(ItemStack item, String name, int number) {
        if (item != null && item.getType() != Material.AIR && item.getAmount() > 0) {
            NBT.modify(item, tags -> {
                tags.setInteger(name, number);
            });
        }
    }

    public static void setLong(ItemStack item, String name, long number) {
        if (item != null && item.getType() != Material.AIR && item.getAmount() > 0) {
            NBT.modify(item, tags -> {
                tags.setLong(name, number);
            });
        }
    }

    public static void setDouble(ItemStack item, String name, double number) {
        if (item != null && item.getType() != Material.AIR && item.getAmount() > 0) {
            NBT.modify(item, tags -> {
                tags.setDouble(name, number);
            });
        }
    }

    public static void setStringList(ItemStack item, String key, List<String> values) {
        NBT.modify(item, nbt -> {
            ReadWriteNBTList<String> list = nbt.getStringList(key);
            list.clear();
            list.addAll(values);
        });
    }


    public static Integer getInteger(ItemStack item, String key) {
        if (item != null && item.getType() != Material.AIR && item.getAmount() > 0) {
            Integer integer = NBT.readNbt(item).getInteger(key);
            if (integer != null && !integer.toString().isEmpty()) {
                return integer;
            }
        }
        return null;
    }

    public static Long getLong(ItemStack item, String key) {
        if (item != null && item.getType() != Material.AIR && item.getAmount() > 0) {
            Long longNumber = NBT.readNbt(item).getLong(key);
            if (longNumber != null && !longNumber.toString().isEmpty()) {
                return longNumber;
            }
        }
        return null;
    }

    public static Double getDouble(ItemStack item, String key) {
        if (item != null && item.getType() != Material.AIR && item.getAmount() > 0) {
            Double aDouble = NBT.readNbt(item).getDouble(key);
            if (aDouble != null && !aDouble.toString().isEmpty()) {
                return aDouble;
            }
        }
        return null;
    }

    public static List<String> getStringList(ItemStack item, String key) {
        if (item != null && item.getType() != Material.AIR && item.getAmount() > 0) {
            ReadableNBTList<String> aList = NBT.readNbt(item).getStringList(key);
            if (aList != null) {
                return aList.toListCopy();
            }
        }
        return null;
    }

    public static void removeKey(ItemStack item, String key) {
        if (item != null && item.getType() != Material.AIR && item.getAmount() > 0) {
            NBT.modify(item, tags -> {
                tags.removeKey(key);
            });
        }
    }
}
