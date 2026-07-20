package mv.mossuh.mocore.CONFIG;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class MoConfig {

    private String fileName;
    private JavaPlugin main;
    private String folderName;
    private File configFile = null;
    private YamlConfiguration configData = null;
    public MoConfig(String fileName, JavaPlugin main, String folderName) {
        this.fileName = fileName;
        this.main = main;
        this.folderName = folderName;
    }

    public void reloadConfig() {
        if (configData == null) {
            if (folderName == null) {
                configFile = new File(main.getDataFolder(), fileName);
            } else {
                configFile = new File(main.getDataFolder() + File.separator + folderName, fileName);
            }
        }
        configData = YamlConfiguration.loadConfiguration(configFile);
        Reader defConfigStream;

        if(folderName != null){
            defConfigStream = new InputStreamReader(Objects.requireNonNull(main.getResource(folderName + "/" + fileName)), StandardCharsets.UTF_8);
        }else{
            defConfigStream = new InputStreamReader(Objects.requireNonNull(main.getResource(fileName)), StandardCharsets.UTF_8);
        }

        if (defConfigStream != null) {
            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
            configData.setDefaults(defConfig);
        }

    }

    public File getFile() { return configFile; }
    public FileConfiguration getConfig() {
        if (configData == null) {
            reloadConfig();
        }
        return configData;
    }

    public void saveConfig() {
        try {
            configData.save(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void registerConfig(boolean comments) {
        if (folderName == null) {
            configFile = new File(main.getDataFolder(), fileName);
        } else {
            configFile = new File(main.getDataFolder() + File.separator + folderName, fileName);
        }

        if (!comments) {
            if (!configFile.exists()) {
                this.getConfig().options().copyDefaults(true);
                saveConfig();
            }
        } else {
            if (!configFile.exists()) {
                File file;
                main.saveResource(folderName == null ? fileName : folderName + "/" + fileName, false);
                if (folderName == null) {
                    file = new File(main.getDataFolder(), fileName);
                } else {
                    file = new File(main.getDataFolder() + File.separator + folderName, fileName);
                }
                configFile = file;
            } else {
                configData = YamlConfiguration.loadConfiguration(configFile);
            }
        }
    }

    public String getFileName() {
        return fileName;
    }
}
