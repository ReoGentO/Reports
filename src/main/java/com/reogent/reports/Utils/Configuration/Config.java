package com.reogent.reports.Utils.Configuration;

import com.reogent.reports.DataBase.Models.Report;
import com.reogent.reports.Reports;
import com.reogent.reports.Utils.Utils;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.xenondevs.inventoryaccess.component.AdventureComponentWrapper;
import xyz.xenondevs.inventoryaccess.component.ComponentWrapper;
import xyz.xenondevs.invui.gui.PagedGui;
import xyz.xenondevs.invui.item.builder.ItemBuilder;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class Config {

    private int comments;
    private final JavaPlugin pl;
    private File file;
    private FileConfiguration config;

    public Config(File configFile, int comments, JavaPlugin pl) {
        this.comments = comments;
        this.pl = pl;
        this.file = configFile;
        this.loadConfig();
    }

    public YamlConfiguration getConfig() {
        return YamlConfiguration.loadConfiguration(this.file);
    }
    public Object get(String path) {
        return this.config.get(path);
    }
    public Material getMaterial(String path) {
        try {
            return Material.valueOf(this.config.getString(path));
        } catch (IllegalArgumentException e) {
            Logger.getLogger("Reports").warning("Error in config " + this.file.getName());
            Logger.getLogger("Reports").warning("Please check the value of material: " + this.config.getString(path));
            return Material.BARRIER;
        }
    }
    public Object get(String path, Object def) {
        return this.config.get(path, def);
    }
    public String getString(String path) {
        return this.config.getString(path);
    }
    public String getString(String path, String def) {
        return this.config.getString(path, def);
    }
    public int getInt(String path) {
        return this.config.getInt(path);
    }
    public int getInt(String path, int def) {
        return this.config.getInt(path, def);
    }

    public boolean getBoolean(String path) {
        return this.config.getBoolean(path);
    }
    public boolean getBoolean(String path, boolean def) {
        return this.config.getBoolean(path, def);
    }

    public void createSection(String path) {
        this.config.createSection(path);
    }

    public ConfigurationSection getConfigurationSection(String path) {
        return this.config.getConfigurationSection(path);
    }

    public double getDouble(String path) {
        return this.config.getDouble(path);
    }
    public double getDouble(String path, double def) {
        return this.config.getDouble(path, def);
    }
    public List<?> getList(String path) {
        return this.config.getList(path);
    }

    public List<?> getList(String path, List<?> def) {
        return this.config.getList(path, def);
    }
    public boolean contains(String path) {
        return this.config.contains(path);
    }
    public void removeKey(String path) {
        this.config.set(path, null);
    }

    public void set(String path, Object value) {
        this.config.set(path, value);
    }

    public void set(String path, Object value, String comment) {
        if(!this.config.contains(path)) {
            this.config.set(getPluginName() + "_COMMENT_" + comments, " " + comment);
            comments++;
        }
        this.config.set(path, value);
    }

    public void set(String path, Object value, String[] comment) {
        for(String comm : comment) {
            if(!this.config.contains(path)) {
                this.config.set(getPluginName() + "_COMMENT_" + comments, " " + comm);
                comments++;
            }

        }
        this.config.set(path, value);
    }

    public List<ComponentWrapper> getListToComponentReport(List<String> path, Report report) {
        return path.stream()
                .map(line -> new AdventureComponentWrapper(MiniMessage.miniMessage().deserialize(
                        line.replace("{reporter_player}", report.getReporterName())
                                .replace("{reported_player}", report.getReportedName())
                                .replace("{reason}", report.getReason())
                                .replace("{date}", report.getFormattedTimestamp())
                                .replace("{id}", String.valueOf(report.getId()))
                )))
                .collect(Collectors.toList());
    }

    public List<ComponentWrapper> getLTCNextPagedGui(List<String> path, PagedGui<?> gui) {
        return path.stream()
                .map(line -> new AdventureComponentWrapper(MiniMessage.miniMessage().deserialize(
                        line.replace("{next_page}", String.valueOf(gui.getCurrentPage() + 2)).replace("{all_pages}", String.valueOf(gui.getPageAmount()))))
                )
                .collect(Collectors.toList());
    }

    public List<ComponentWrapper> getLTCPrevPagedGui(List<String> path, PagedGui<?> gui) {
        return path.stream()
                .map(line -> new AdventureComponentWrapper(MiniMessage.miniMessage().deserialize(
                        line.replace("{prev_page}", String.valueOf(gui.getCurrentPage())).replace("{all_pages}", String.valueOf(gui.getPageAmount()))))
                )
                .collect(Collectors.toList());
    }

    public List<ComponentWrapper> getLTC(String path) {
        List<String> loreList = this.config.getStringList(path);
        return loreList.stream()
                .map(line -> new AdventureComponentWrapper(MiniMessage.miniMessage().deserialize(line)))
                .collect(Collectors.toList());
    }

    public void setHeader(String[] header) {
        setHeader(this.file, header);
        this.comments = header.length + 2;
        this.reloadConfig();
    }
    public void reloadConfig() {
        this.config = YamlConfiguration.loadConfiguration(file);
    }

    public void saveConfig() {
        String config = this.config.saveToString();
        saveConfig(config, this.file);
    }

    public Set<String> getKeys() {
        return this.config.getKeys(false);
    }
    public void loadConfig() {
        this.config = YamlConfiguration.loadConfiguration(file);
        try (InputStreamReader reader = new InputStreamReader(pl.getResource(file.getName()), StandardCharsets.UTF_8)){
            if (reader!=null){
                YamlConfiguration defaults = YamlConfiguration.loadConfiguration(reader);
                this.config.setDefaults(defaults);
            }

        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    /*
     * Adds header block to config
     * @param file - Config file
     * @param header - Header lines
     */
    public void setHeader(File file, String[] header) {
        if(!file.exists()) {
            return;
        }

        try {
            String currentLine;
            StringBuilder config = new StringBuilder();
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));
            while((currentLine = reader.readLine()) != null) {
                config.append(currentLine + "\n");
            }
            reader.close();
            config.append("# +----------------------------------------------------+ #\n");

            for(String line : header) {

                if(line.length() > 50) {
                    continue;
                }
                int lenght = (50 - line.length()) / 2;
                StringBuilder finalLine = new StringBuilder(line);

                for(int i = 0; i < lenght; i++) {
                    finalLine.append(" ");
                    finalLine.reverse();
                    finalLine.append(" ");
                    finalLine.reverse();
                }

                if(line.length() % 2 != 0) {
                    finalLine.append(" ");
                }

                config.append("# < " + finalLine.toString() + " > #\n");

            }

            config.append("# +----------------------------------------------------+ #");

            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8));
            writer.write(prepareConfigString(config.toString()));
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
     * Read file and make comments SnakeYAML friendly
     * @param filePath - Path to file
     * @return - File as Input Stream
     */
    public InputStream getConfigContent(File file) {

        if(!file.exists()) {
            return null;
        }
        try {
            int commentNum = 0;

            String addLine;
            String currentLine;
            String pluginName = this.getPluginName();

            StringBuilder whole = new StringBuilder("");
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));
            while((currentLine = reader.readLine()) != null) {

                if(currentLine.startsWith("#")) {
                    addLine = currentLine.replaceFirst("#", pluginName + "_COMMENT_" + commentNum + ":");
                    whole.append(addLine + "\n");
                    commentNum++;

                } else {
                    whole.append(currentLine + "\n");
                }

            }
            String config = whole.toString();
            InputStream configStream = new ByteArrayInputStream(config.getBytes(Charset.forName("UTF-8")));

            reader.close();
            return configStream;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    /*
     * Get comments from file
     * @param file - File
     * @return - Comments number
     */
    private int getCommentsNum(File file) {
        if(!file.exists()) {
            return 0;
        }
        try {
            int comments = 0;
            String currentLine;
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));

            while((currentLine = reader.readLine()) != null) {
                if(currentLine.startsWith("#")) {
                    comments++;
                }
            }
            reader.close();
            return comments;
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }
    private String prepareConfigString(String configString) {

        int lastLine = 0;
        int headerLine = 0;

        String[] lines = configString.split("\n");
        StringBuilder config = new StringBuilder("");
        for(String line : lines) {
            if(line.startsWith(this.getPluginName() + "_COMMENT")) {
                String comment = "#" + line.trim().substring(line.indexOf(":") + 1);

                if(comment.startsWith("# +-")) {
                    /*
                     * If header line = 0 then it is
                     * header start, if it's equal
                     * to 1 it's the end of header
                     */
                    if(headerLine == 0) {
                        config.append(comment + "\n");
                        lastLine = 0;
                        headerLine = 1;

                    } else if(headerLine == 1) {
                        config.append(comment + "\n\n");
                        lastLine = 0;
                        headerLine = 0;

                    }
                } else {
                    /*
                     * Last line = 0 - Comment
                     * Last line = 1 - Normal path
                     */
                    String normalComment;
                    if(comment.startsWith("# ' ")) {
                        normalComment = comment.substring(0, comment.length() - 1).replaceFirst("# ' ", "# ");
                    } else {
                        normalComment = comment;
                    }
                    if(lastLine == 0) {
                        config.append(normalComment + "\n");
                    } else if(lastLine == 1) {
                        config.append("\n" + normalComment + "\n");
                    }
                    lastLine = 0;
                }
            } else {
                config.append(line + "\n");
                lastLine = 1;
            }
        }
        return config.toString();
    }
    /*
     * Saves configuration to file
     * @param configString - Config string
     * @param file - Config file
     */
    public void saveConfig(String configString, File file) {
        String configuration = this.prepareConfigString(configString);
        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8))) {
            writer.write(configuration);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public String getPluginName() {
        return pl.getDescription().getName();
    }
}