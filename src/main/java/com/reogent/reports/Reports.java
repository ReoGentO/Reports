package com.reogent.reports;

import com.reogent.reports.Commands.ReportCommand;
import com.reogent.reports.Commands.ReportTabCompleter;
import com.reogent.reports.Commands.ReportsCommand;
import com.reogent.reports.Commands.ReportsTabCompleter;
import com.reogent.reports.Config.GUI;
import com.reogent.reports.Config.Main;
import com.reogent.reports.DataBase.ReportsDatabase;
import com.reogent.reports.Listeners.PlayerListeners;
import com.reogent.reports.Utils.Configuration.Config;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.sql.*;
import java.util.Objects;

public class Reports extends JavaPlugin {
    private BukkitAudiences audiences;
    private static Reports instance;

    public File mainFile = new File(getDataFolder(), "main.yml");
    public Config mainConfig = new Config(mainFile, 10, this);
    public File guiFile = new File(getDataFolder(), "gui_settings.yml");
    public Config guiConfig = new Config(guiFile, 10, this);

    public BukkitAudiences adventure() {
        if(this.audiences == null) {
            throw new IllegalStateException("Tried to access Adventure when the plugin was disabled!");
        }
        return this.audiences;
    }

    @Override
    public void onEnable() {
        // Load configs
        if (!mainFile.exists()) {
            mainConfig.saveConfig();
            loadMainConfig();
        }
        if (!guiFile.exists()) {
            guiConfig.saveConfig();
            loadGUISettings();
        }
        instance = this;
        audiences = BukkitAudiences.create(this);
        File dataFolder = getDataFolder();
        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
        }
        // Load DB
        String dbPath = dataFolder.getAbsolutePath() + File.separator + "reports.db";

        // Connect to DB
        try {
            ReportsDatabase.connection = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
            ReportsDatabase.createTable();
        } catch (SQLException e) {
            getLogger().severe("Не удалось подключиться к базе данных! " + e.getMessage());
            setEnabled(false);
            return;
        }
        // Commands and listeners
        getCommand("report").setExecutor(new ReportCommand());
        getCommand("report").setTabCompleter(new ReportTabCompleter());
        getCommand("reports").setExecutor(new ReportsCommand());
        getCommand("reports").setTabCompleter(new ReportsTabCompleter());
        Bukkit.getPluginManager().registerEvents(new PlayerListeners(), this);
        getLogger().info("Плагин ReportPlugin включен!");
    }

    @Override
    public void onDisable() {
        try {
            if (ReportsDatabase.connection != null && !ReportsDatabase.connection.isClosed()) {
                ReportsDatabase.connection.close();
            }
        } catch (SQLException e) {
            getLogger().severe("Не удалось закрыть подключение к базе данных! " + e.getMessage());
        }
        getLogger().info("Плагин Reports выключен!");
        if(this.audiences != null) {
            this.audiences.close();
            this.audiences = null;
        }
    }


    public void loadMainConfig() {
        mainConfig.setHeader(new String[]{"Плагин на репорты от ReoGenT со слежкой!", "Telegram: https://t.me/uwuweweweonotwe", "Discord: TheBlackInfinity"});
        for (Main item : Main.values()) {
            if (mainConfig.getString(item.getPath()) == null) {
                if (Objects.equals(item.getPath(), "self_reports")) {
                    mainConfig.set(item.getPath(), item.getDefault(), "Если true, то игроки могут репортить сами себя");
                    continue;
                }
                if (Objects.equals(item.getPath(), "messages.enter_spy")) {
                    mainConfig.set(item.getPath(), item.getDefault(), "Сообщения плагина");
                    continue;
                }
                if (Objects.equals(item.getPath(), "cooldown")) {
                    mainConfig.set(item.getPath(), item.getDefault(), new String[]{"Кулдаун между репортами", "Введите -1 для отключения"});
                    continue;
                }
                mainConfig.set(item.getPath(), item.getDefault());
            }
        }
        mainConfig.saveConfig();
    }

    public void loadGUISettings() {
        guiConfig.setHeader(new String[]{"Конфигурация GUI и др.", "Внимание! Material устанавливаем в верхнем регистре!", "Telegram: https://t.me/uwuweweweonotwe", "Discord: TheBlackInfinity"});
        for (GUI item : GUI.values()) {
            if (guiConfig.getString(item.getPath()) == null) {
                if (Objects.equals(item.getPath(), "gui.name")) {
                    guiConfig.set(item.getPath(), item.getDefault(), "Текст");
                    continue;
                }
                if (Objects.equals(item.getPath(), "gui_border_material")) {
                    guiConfig.set(item.getPath(), item.getDefault(), "Настройки GUI");
                    continue;
                }
                guiConfig.set(item.getPath(), item.getDefault());
            }
        }
        guiConfig.saveConfig();
    }

    public BukkitAudiences getAudiences() {
        return audiences;
    }

    public static Reports getInstance() {
        return instance;
    }
}