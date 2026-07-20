package mv.mossuh.mocore.DATABASE;

import mv.mossuh.mocore.UTILITIES.UsefulString;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionMySQL {
    private Connection connection;

    private final String host;
    private final int port;
    private final String database;
    private final String user;
    private final String password;

    private String prefix = "&8[&bMoCore&8]";

    public ConnectionMySQL(String host, int port, String database, String user, String password) {
        this.host = host;
        this.port = port;
        this.database = database;
        this.user = user;
        this.password = password;

        connect();
    }

    public void setPrefix(String prefix) {
        if (prefix == null) return;
        this.prefix = prefix;
    }

    private void connect() {
        String url = "jdbc:mysql://" + host + ":" + port + "/" + database;
        try {
            connection = DriverManager.getConnection(url, user, password);
            UsefulString.get(prefix+" &aConnected to MySQL successfully.").hex().sendMessageInConsole();
        } catch (SQLException e) {
            UsefulString.get(prefix+" &cFailed to connect to MySQL: " + e.getMessage()).hex().sendMessageInConsole();
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connect();
            }
        } catch (SQLException e) {
            UsefulString.get(prefix+" &cMySQL connection check failed: " + e.getMessage()).hex().sendMessageInConsole();
            e.printStackTrace();
        }
        return connection;
    }

    public void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                UsefulString.get(prefix+" &eMySQL connection closed.").hex().sendMessageInConsole();
            }
        } catch (SQLException e) {
            UsefulString.get(prefix+" &cFailed to close MySQL connection: " + e.getMessage()).hex().sendMessageInConsole();
            e.printStackTrace();
        }
    }
}

