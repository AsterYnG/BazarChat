package com.arinc.util;

import com.arinc.exceptions.FailedToRegisterDriverException;
import lombok.experimental.UtilityClass;
import org.postgresql.Driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@UtilityClass
public final class ConnectionManager {
    private static final String URL_KEY = "db.url";
    private static final String USERNAME_KEY = "db.username";
    private static final String PASSWORD_KEY = "db.password";



    static {
        loadDriver();
    }

    private static void loadDriver() {
        try {
            DriverManager.registerDriver(new Driver());
        } catch (SQLException e) {
            throw new FailedToRegisterDriverException(e.getMessage());
        }
    }



    private static Connection open() {
        try {
            return DriverManager.getConnection(PropertiesUtil.get(URL_KEY), PropertiesUtil.get(USERNAME_KEY), PropertiesUtil.get(PASSWORD_KEY));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection get() {
        return open();
    }


}