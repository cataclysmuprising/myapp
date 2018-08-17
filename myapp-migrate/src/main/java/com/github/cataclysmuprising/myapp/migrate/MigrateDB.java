/*******************************************************************************
 * Copyright (C) 2018 Than Htike Aung
 * myapp-migrate - MigrateDB.java
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 ******************************************************************************/
package com.github.cataclysmuprising.myapp.migrate;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.flywaydb.core.Flyway;

public class MigrateDB {
    private static final Logger logger = LogManager.getLogger(MigrateDB.class);
    private static String jdbcSettingPath, migrationPath;
    private static String url, userName, password, database, schema;

    public static void main(String[] args) throws IOException, SQLException {
        loadMigrationProperties();
        loadJdbcProperties();
        dropDatabase();
        createDatabase();
        createSchema();
        migrateScripts();
    }

    private static void loadMigrationProperties() throws IOException {
        logger.info(">>> Loading Migration Properties.....");
        Properties migrateProperties = new Properties();
        try (InputStream migrationStream = MigrateDB.class.getResourceAsStream("/migrate.properties")) {
            migrateProperties.load(migrationStream);
            migrationPath = migrateProperties.get("migrationPath").toString();
            jdbcSettingPath = migrateProperties.get("jdbcSettingAbsolutePath").toString();
        }
    }

    private static void loadJdbcProperties() throws IOException {
        logger.info(">>> Loading JDBC Properties.....");
        Properties jdbcProperties = new Properties();
        try (InputStream jdbcSettingStream = MigrateDB.class.getResourceAsStream(jdbcSettingPath)) {
            jdbcProperties.load(jdbcSettingStream);
            url = jdbcProperties.get("jdbc.url").toString();
            userName = jdbcProperties.get("jdbc.username").toString();
            password = jdbcProperties.get("jdbc.password").toString();
            database = jdbcProperties.get("jdbc.database").toString();
            schema = jdbcProperties.get("jdbc.schema").toString();
        }
    }

    private static void dropDatabase() throws SQLException {
        logger.info(">>> Prepare to drop database from PostgresDB with name = '" + database + "'");
        Connection con = DriverManager.getConnection(url, userName, password);
        Statement stmt = con.createStatement();
        ResultSet result = stmt.executeQuery("SELECT 1 from pg_database WHERE datname='" + database + "';");
        if (result.next()) {
            stmt.executeQuery("SELECT pg_terminate_backend (pg_stat_activity.pid) FROM pg_stat_activity WHERE pg_stat_activity.datname = '" + database + "';");
            stmt.executeUpdate("DROP database " + database + ";");
            logger.info("--- Old database with name = '" + database + "' has been dropped.");
        } else {
            logger.info("--- Old database with name = '" + database + "' does not exist. Skipping drop database process.....");
        }
    }

    private static void createDatabase() throws SQLException {
        logger.info(">>> Create new database to PostgresDB with name = '" + database + "'");
        Connection con = DriverManager.getConnection(url, userName, password);
        Statement stmt = con.createStatement();
        stmt.executeUpdate("CREATE DATABASE " + database + " WITH OWNER = postgres ENCODING = 'UTF8' CONNECTION LIMIT = -1;");
    }

    private static void createSchema() throws SQLException {
        logger.info(">>> Creating new schema to PostgresDB with name = '" + schema + "' at database ='" + database + "'");
        Connection con = DriverManager.getConnection(url + database, userName, password);
        Statement stmt = con.createStatement();
        stmt.executeUpdate("CREATE SCHEMA " + schema + " AUTHORIZATION postgres;");
        stmt.executeUpdate("GRANT ALL ON SCHEMA  " + schema + " TO postgres;");
    }

    private static void migrateScripts() {
        Flyway flyway = new Flyway();
        flyway.setSqlMigrationPrefix("");
        flyway.setLocations(migrationPath);
        String migrateUrl = url + database + "?currentSchema=" + schema;
        flyway.setDataSource(migrateUrl, userName, password);
        flyway.clean();
        logger.info(">>> Migrating sql scripts to PostgresDB with connection-url = '" + migrateUrl + "'");
        flyway.migrate();
        logger.info("<<< xxxxx Migration Proccess has been successfully finished. xxxxx >>>");
    }
}
