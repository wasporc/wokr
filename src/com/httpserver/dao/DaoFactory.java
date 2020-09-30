package com.httpserver.dao;

import com.httpserver.settings.Settings;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public final class DaoFactory {

    private static DataSource dataSource;

    private DaoFactory() {
    }

    public static DataSource getDataSource(){
        if (dataSource == null){
            PGSimpleDataSource ds = new PGSimpleDataSource();
            //ds.setServerName("localhost");
            ds.setServerName(Settings.data.get("HOST"));
            ds.setPortNumber(Integer.parseInt(Settings.data.get("PORTSQL")));
            ds.setDatabaseName(Settings.data.get("TABLE"));
            ds.setUser(Settings.data.get("USER"));
            ds.setPassword(Settings.data.get("PASS"));

            dataSource = ds;
        }
        return dataSource;
    }

    public static Connection getConnection() throws SQLException {
        return getDataSource().getConnection();
    }
}
