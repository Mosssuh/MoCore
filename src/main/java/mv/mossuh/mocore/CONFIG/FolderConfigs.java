package mv.mossuh.mocore.CONFIG;

import mv.mossuh.mocore.UTILITIES.UsefulMethods;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FolderConfigs {

    private List<MoConfig> configs;
    private JavaPlugin main;
    private String folderName;
    private boolean comments = false;

    public FolderConfigs(Boolean comments, JavaPlugin main, String folderName) {
        this.main = main;
        this.folderName = folderName;
        this.configs = new ArrayList<>();
        if (comments != null) { this.comments = comments; }
    }

    public void configure(String... examples) {
        createFolder(examples);
        reloadConfigs();
    }

    public void reloadConfigs(){
        this.configs = new ArrayList<>();
        registerConfigs();
    }

    public void createFolder(String... examples){
        File folder;
        try {
            folder = new File(main.getDataFolder() + File.separator + folderName);
            if(!folder.exists()){
                folder.mkdirs();
                createExample(examples);
            }
        } catch(SecurityException e) {
            folder = null;
        }
    }

    public void createExample(String... examples){
        for (String example : examples) {
            MoConfig config = new MoConfig(example, main, folderName);
            config.registerConfig(comments);
        }
    }

    public void saveConfigs() {
        for (MoConfig config : configs) {
            config.saveConfig();
        }
    }

    public void registerConfigs(){
        String path = main.getDataFolder() + File.separator + folderName;
        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();
        for (File listOfFile : listOfFiles) {
            if (listOfFile.isFile()) {
                String pathName = listOfFile.getName();
                String ext = UsefulMethods.getFileExtension(pathName);
                if (!ext.equals("yml")) {
                    continue;
                }
                MoConfig config = new MoConfig(pathName, main, folderName);
                config.registerConfig(comments);
                configs.add(config);
            }
        }
    }

    public List<MoConfig> getConfigs(){
        return this.configs;
    }

    public boolean fileAlreadyRegistered(String fileName) {
        for (MoConfig config : configs) {
            if (config.getFileName().equals(fileName)) {
                return true;
            }
        }
        return false;
    }

    public MoConfig getConfig(String fileName) {
        for (MoConfig config : configs) {
            if (config.getFileName().equals(fileName)) {
                return config;
            }
        }
        return null;
    }

    public boolean registerConfig(String pathName) {
        if(!fileAlreadyRegistered(pathName)) {
            MoConfig config = new MoConfig(pathName, main, folderName);
            config.registerConfig(comments);
            configs.add(config);
            return true;
        }else {
            return false;
        }
    }
}
