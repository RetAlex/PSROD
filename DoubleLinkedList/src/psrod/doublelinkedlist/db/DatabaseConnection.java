package psrod.doublelinkedlist.db;

import com.mysql.cj.jdbc.MysqlConnectionPoolDataSource;
import psrod.doublelinkedlist.entities.Theatre;
import psrod.doublelinkedlist.enums.Criteria;

import javax.sql.PooledConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatabaseConnection {
    private static MysqlConnectionPoolDataSource dataSource;
    private static Map<Integer, PooledConnection> connectionMap = new HashMap<>();
    private static int currentConnectionId;
    private static Map<Integer, Theatre> cache = new HashMap<>();

    public synchronized static IdentifiedConnection openConnection() throws SQLException {
        if(dataSource==null){
            dataSource = new MysqlConnectionPoolDataSource();
            dataSource.setUrl("jdbc:mysql://ukma:1234@68.183.216.120:3306/PSROD");
        }
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


    public static int getPlaceByCriteria(int id, Criteria criteria){
        try {
            return something(id, "select "+criteria.getCriteriaName()+" from indexes where theatre_id=?");
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public static int getIdByPlaceInCriteria(int place, Criteria criteria){
        try {
            return something(place, "select theatre_id from indexes where "+criteria.getCriteriaName()+"=?");
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    private static int something(int place, String sql) throws SQLException {
        DatabaseConnection.IdentifiedConnection identifiedConnection = DatabaseConnection.openConnection();
        Connection connection = identifiedConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, place);
        ResultSet resultSet = statement.executeQuery();
        int result = -1;
        if(resultSet.next()) result = resultSet.getInt(1);
        identifiedConnection.release();
        return result;
    }

    public static Theatre getTheatreById(int id){
        try {
            Theatre response = getFromCache(id);
            if(response != null) return response;
            DatabaseConnection.IdentifiedConnection identifiedConnection = DatabaseConnection.openConnection();
            Connection connection = identifiedConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement("select id, name, address, rating, capacity, distance, image from theatre where id=?");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next())
               response = new Theatre(id, resultSet.getString(2), resultSet.getString(3), resultSet.getInt(4), resultSet.getInt(5), resultSet.getInt(6), resultSet.getString(7));
            identifiedConnection.release();
            addToCache(response);
            return response;
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public static List<Theatre> getAllTheatres(){
        try {
            DatabaseConnection.IdentifiedConnection identifiedConnection = DatabaseConnection.openConnection();
            Connection connection = identifiedConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement("select id, name, address, rating, capacity, distance, image from theatre;");
            ResultSet resultSet = statement.executeQuery();
            List<Theatre> response = new ArrayList<>();
            while(resultSet.next()) response.add(new Theatre(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getInt(4), resultSet.getInt(5), resultSet.getInt(6), resultSet.getString(7)));
            identifiedConnection.release();
            response.forEach(DatabaseConnection::addToCache);
            return response;
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public static Theatre createTheatre(Theatre theatre){
        try {
            DatabaseConnection.IdentifiedConnection identifiedConnection = DatabaseConnection.openConnection();
            Connection connection = identifiedConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement("insert into theatre (name, address, rating, capacity, distance, image) value (?, ?, ?, ?, ?, ?);", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, theatre.getName());
            statement.setString(2, theatre.getAddress());
            statement.setInt(3, theatre.getRating());
            statement.setInt(4, theatre.getCapacity());
            statement.setInt(5, theatre.getDistance());
            statement.setString(6, theatre.getImageURL());
            statement.executeUpdate();
            ResultSet result = statement.getGeneratedKeys();
            result.next();
            theatre.setId(result.getInt(1));
            addToCache(theatre);
            return theatre;
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public static void removeTheatre(int id){
        try {
            DatabaseConnection.IdentifiedConnection identifiedConnection = DatabaseConnection.openConnection();
            Connection connection = identifiedConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement("delete from theatre where id=?");
            statement.setInt(1, id);
            statement.executeUpdate();
            removeFromCache(id);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    private static Theatre getFromCache(int id){
        return cache.get(id);
    }

    private static void addToCache(Theatre theatre){
        if(theatre!=null) cache.put(theatre.getId(), theatre);
    }

    private static void removeFromCache(int id){
        cache.remove(id);
    }
}
