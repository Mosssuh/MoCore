package mv.mossuh.mocore.NBT.TEXTUREHEADS.Data;

import mv.mossuh.mocore.CONFIG.CoreConfigs;
import mv.mossuh.mocore.NBT.TEXTUREHEADS.Utilities.TextureHead;

import java.sql.*;
import java.util.Set;
import java.util.UUID;

public class TextureHeadsData {

    public static void registerDatabaseInMaps() {
        try {
            Connection conn = DriverManager.getConnection("jdbc:sqlite:" + CoreConfigs.getTextureHeadsData().getFile().getAbsolutePath());
            Statement stmt = conn.createStatement();
            stmt.execute("CREATE TABLE IF NOT EXISTS TextureHeads (uuid TEXT PRIMARY KEY, value TEXT)");

            ResultSet rsTimer = stmt.executeQuery("SELECT * FROM TextureHeads");
            while (rsTimer.next()) {
                UUID uuid = UUID.fromString(rsTimer.getString("uuid"));
                String value = rsTimer.getString("value");
                TextureHeadsCache.addTextureValue(new TextureHead(uuid, value));
            }

            rsTimer.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void registerMapsInDatabase() {
        try {
            Set<TextureHead> textureHeads = TextureHeadsCache.getTextureHeads();

            Connection conn = DriverManager.getConnection("jdbc:sqlite:" + CoreConfigs.getTextureHeadsData().getFile().getAbsolutePath());
            Statement stmt = conn.createStatement();
            stmt.execute("CREATE TABLE IF NOT EXISTS TextureHeads (uuid TEXT PRIMARY KEY, value TEXT)");

            for (TextureHead h : textureHeads) {
                UUID uuid = h.getUUID();
                String value = h.getValue();

                // Usamos INSERT IGNORE INTO para evitar reemplazar el valor si ya existe
                stmt.execute("INSERT OR IGNORE INTO TextureHeads (uuid, value) VALUES('" + uuid + "', '" + value + "')");
            }

            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addTextureHead(TextureHead head) {
        try {
            Connection conn = DriverManager.getConnection("jdbc:sqlite:" + CoreConfigs.getTextureHeadsData().getFile().getAbsolutePath());
            Statement stmt = conn.createStatement();
            stmt.execute("CREATE TABLE IF NOT EXISTS TextureHeads (uuid TEXT PRIMARY KEY, value TEXT)");

            UUID uuid = head.getUUID();
            String value = head.getValue();

            stmt.execute("INSERT OR IGNORE INTO TextureHeads (uuid, value) VALUES('" + uuid + "', '" + value + "')");

            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void vacuumDatabase() {
        try (Connection vacuumConn = DriverManager.getConnection("jdbc:sqlite:" + CoreConfigs.getTextureHeadsData().getFile().getAbsolutePath());
             Statement vacuumStmt = vacuumConn.createStatement()) {

            vacuumStmt.execute("VACUUM");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
