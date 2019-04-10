package psrod.doublelinkedlist.db;

import com.mysql.cj.jdbc.MysqlConnectionPoolDataSource;

import javax.sql.PooledConnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class DatabaseConnection {
    private static MysqlConnectionPoolDataSource dataSource;
    private static Map<Integer, PooledConnection> connectionMap = new HashMap<>();
    private static int currentConnectionId;

    {
        dataSource = new MysqlConnectionPoolDataSource();
        dataSource.setUrl("jdbc:mysql://ukma:1234@68.183.216.120:3306/PSROD");
    }

    public synchronized static IdentifiedConnection openConnection() throws SQLException {
        PooledConnection connection = dataSource.getPooledConnection(); int id = currentConnectionId++;
        connectionMap.put(id, connection);
        return new IdentifiedConnection(id, connection.getConnection());
    }

    public static void closeConnection(int id) throws SQLException {
        connectionMap.get(id).close();
    }

    public static class IdentifiedConnection{
        private int id;
        private Connection connection;

        public IdentifiedConnection(int id, Connection connection) {
            this.id = id;
            this.connection = connection;
        }

        public Connection getConnection() {
            return connection;
        }

        public void release() throws SQLException {
            DatabaseConnection.closeConnection(this.id);
        }
    }
}
