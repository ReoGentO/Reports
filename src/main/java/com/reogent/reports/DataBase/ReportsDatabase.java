package com.reogent.reports.DataBase;

import com.reogent.reports.DataBase.Models.Report;
import com.reogent.reports.Reports;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

public class ReportsDatabase {
    public static String TABLE_NAME = "reports";
    public static Connection connection;

    public static void createTable() {
        try (Statement statement = connection.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "reporter_name TEXT," +
                    "reported_name TEXT," +
                    "reason TEXT," +
                    "timestamp INTEGER" +
                    ");";
            statement.execute(sql);
            Bukkit.getLogger().info("Таблица " + TABLE_NAME + " создана (если не существовала)!");
        } catch (SQLException e) {
            Bukkit.getLogger().severe("Не удалось создать таблицу! " + e.getMessage());
        }
    }

    public static void addReport(String reporterName, String reportedName, String reason) {
        long timestamp = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);
        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO " + TABLE_NAME + " (reporter_name, reported_name, reason, timestamp) VALUES (?, ?, ?, ?)"
        )) {
            statement.setString(1, reporterName);
            statement.setString(2, reportedName);
            statement.setString(3, reason);
            statement.setLong(4, timestamp);
            statement.executeUpdate();
            Bukkit.getLogger().info("Репорт добавлен в базу данных: " + reporterName + " на " + reportedName + " причина '" + reason + "'");
        } catch (SQLException e) {
            Bukkit.getLogger().severe("Не удалось добавить репорт в базу данных! " + e.getMessage());
        }
    }

    public static List<Report> getAllReports() {
        List<Report> reports = new ArrayList<>();
        String databasePath = JavaPlugin.getPlugin(Reports.class).getDataFolder().getAbsolutePath() + File.separator + "reports.db";
        String url = "jdbc:sqlite:" + databasePath;
        String query = "SELECT * FROM reports";

        try (Connection connection = DriverManager.getConnection(url);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String reporterName = resultSet.getString("reporter_name");
                String reportedName = resultSet.getString("reported_name");
                String reason = resultSet.getString("reason");
                long timestamp = resultSet.getLong("timestamp");

                reports.add(new Report(id, reporterName, reportedName, reason, timestamp));
            }

        } catch (SQLException e) {
            Bukkit.getLogger().severe("Не удалось загрузить репорты из базы данных reports.db: " + e.getMessage());
        }

        return reports;
    }

    public static void removeReport(int reportId) {
        try (PreparedStatement statement = connection.prepareStatement("DELETE FROM " + TABLE_NAME + " WHERE id = ?")) {
            statement.setInt(1, reportId);
            statement.executeUpdate();
            Bukkit.getLogger().info("Репорт с ID " + reportId + " удален из базы данных.");
        } catch (SQLException e) {
            Bukkit.getLogger().severe("Не удалось удалить репорт с ID " + reportId + " из базы данных: " + e.getMessage());
        }
    }
}
