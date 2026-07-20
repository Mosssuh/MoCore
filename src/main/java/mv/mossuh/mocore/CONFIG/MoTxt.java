package mv.mossuh.mocore.CONFIG;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class MoTxt {

    private final JavaPlugin main;
    private final String fileName;
    private final String folderName;
    private File file;

    public MoTxt(String fileName, JavaPlugin main, String folderName) {
        this.fileName = fileName;
        this.main = main;
        this.folderName = folderName;
        register();
    }

    private void register() {
        File folder = (folderName == null)
                ? main.getDataFolder()
                : new File(main.getDataFolder(), folderName);

        if (!folder.exists()) {
            folder.mkdirs();
        }

        file = new File(folder, fileName);

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void writeLine(String line) {
        writeLines(line);
    }

    public void writeLines(List<String> lines) {
        try {
            Files.write(
                    file.toPath(),
                    lines,
                    StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeLines(String... lines) {
        writeLines(Arrays.asList(lines));
    }

    /* =========================
       LECTURA
       ========================= */

    /** Read the entire file (No recomended for big files)*/
    public List<String> readLines() {
        try {
            return Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    /**
     * Read only the lines that contains the filters
     */
    public List<String> readLines(String... contains) {
        List<String> result = new ArrayList<>();

        try (Stream<String> stream = Files.lines(file.toPath(), StandardCharsets.UTF_8)) {
            stream.forEach(line -> {
                for (String filter : contains) {
                    if (!line.contains(filter)) {
                        return;
                    }
                }
                result.add(line);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    /* =========================
       EDITION
       ========================= */

    public void editLine(int index, String newLine) {
        List<String> lines = readLines();
        if (index < 0 || index >= lines.size()) return;

        lines.set(index, newLine);
        saveAll(lines);
    }

    public void editLine(String newLine, String... contains) {
        List<String> result = new ArrayList<>();
        boolean edited = false;

        try (Stream<String> stream = Files.lines(file.toPath(), StandardCharsets.UTF_8)) {
            for (String line : (Iterable<String>) stream::iterator) {

                if (!edited && containsAll(line, contains)) {
                    result.add(newLine);
                    edited = true;
                } else {
                    result.add(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        if (edited) {
            saveAll(result);
        }
    }


    public void replaceLine(String contains, String newLine) {
        List<String> lines = readLines();

        for (int i = 0; i < lines.size(); i++) {
            if (lines.get(i).contains(contains)) {
                lines.set(i, newLine);
                break;
            }
        }

        saveAll(lines);
    }

    public void replaceLine(String newLine, String... contains) {
        List<String> result = new ArrayList<>();

        try (Stream<String> stream = Files.lines(file.toPath(), StandardCharsets.UTF_8)) {
            for (String line : (Iterable<String>) stream::iterator) {
                if (containsAll(line, contains)) {
                    result.add(newLine);
                } else {
                    result.add(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        saveAll(result);
    }


    public void removeLines(String... contains) {
        List<String> remaining = new ArrayList<>();

        try (Stream<String> stream = Files.lines(file.toPath(), StandardCharsets.UTF_8)) {
            stream.forEach(line -> {
                for (String filter : contains) {
                    if (!line.contains(filter)) {
                        remaining.add(line);
                        return;
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        saveAll(remaining);
    }


    public void removeLine(int index) {
        List<String> lines = readLines();
        if (index < 0 || index >= lines.size()) return;

        lines.remove(index);
        saveAll(lines);
    }

    public void clear() {
        saveAll(new ArrayList<>());
    }

    /* =========================
       INTERNAL UTILITIES
       ========================= */

    private void saveAll(List<String> lines) {
        try {
            Files.write(
                    file.toPath(),
                    lines,
                    StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public File getFile() {
        return file;
    }

    private boolean containsAll(String line, String... contains) {
        for (String filter : contains) {
            if (!line.contains(filter)) {
                return false;
            }
        }
        return true;
    }

}
