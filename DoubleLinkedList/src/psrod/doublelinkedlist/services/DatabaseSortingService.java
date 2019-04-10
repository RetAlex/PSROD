package psrod.doublelinkedlist.services;

import psrod.doublelinkedlist.db.DatabaseConnection;
import psrod.doublelinkedlist.entities.Theatre;
import psrod.doublelinkedlist.enums.Criteria;

import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatabaseSortingService extends AbstractSortService {
    @Override
    public void makeTask(List<Theatre> theatres) {
        Map<Integer, Map<Criteria, Integer>> allTheatres = new HashMap<>();
        for(Criteria criteria: Criteria.values()) {
            sortByCriteria(criteria.getCriteriaName(), theatres);
            for(int i=0; i<theatres.size(); i++){
                allTheatres.getOrDefault(theatres.get(i).getId(), new HashMap<>()).put(criteria, i);
            }
        }
        saveIndexes(allTheatres);
    }

    private void saveIndexes(Map<Integer, Map<Criteria, Integer>> theatres) {
        try {
            DatabaseConnection.IdentifiedConnection identifiedConnection = DatabaseConnection.openConnection();
            Connection connection = identifiedConnection.getConnection();
            connection.setAutoCommit(false);
            connection.beginRequest();
            Statement statement = connection.createStatement();
            //noinspection SqlWithoutWhere
            statement.executeUpdate("delete * from indexes");
            PreparedStatement insert = connection.prepareStatement("insert into indexes (theatre_id, name, rating, address, capacity, distance) values (?,?,?,?,?,?);");
            for(Integer theatreId: theatres.keySet()){
                Map<Criteria, Integer> positions = theatres.get(theatreId);
                insert.setInt(1, theatreId);
                insert.setInt(2, positions.get(Criteria.NAME));
                insert.setInt(3, positions.get(Criteria.RATING));
                insert.setInt(4, positions.get(Criteria.ADDRESS));
                insert.setInt(5, positions.get(Criteria.CAPACITY));
                insert.setInt(6, positions.get(Criteria.DISTANCE));
                insert.addBatch();
            }
            insert.executeLargeUpdate();
            connection.commit();
            identifiedConnection.release();
        } catch (Exception e){
            throw new RuntimeException("Bad db config", e);
        }
    }

    @Override
    public Theatre getNext(int currentId, Criteria criteria) {
        int currentPlace = getPlaceByCriteria(currentId, criteria);
        return getTheatreById(getIdByPlaceInCriteria(currentPlace+1, criteria));
    }

    @Override
    public Theatre getPrevious(int currentId, Criteria criteria) {
        int currentPlace = getPlaceByCriteria(currentId, criteria);
        return getTheatreById(getIdByPlaceInCriteria(currentPlace-1, criteria));
    }

    @Override
    public void addTheatre(Theatre theatre) {

    }

    @Override
    public void removeTheatre(int theatreId) {

    }

    private int getPlaceByCriteria(int id, Criteria criteria){
        try {
            return something(id, criteria, "select ", " from indexes where theatre_id=?");
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    private int getIdByPlaceInCriteria(int place, Criteria criteria){
        try {
            return something(place, criteria, "select theatre_id from indexes where ", "=?");
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    private int something(int place, Criteria criteria, String s, String s2) throws SQLException {
        DatabaseConnection.IdentifiedConnection identifiedConnection = DatabaseConnection.openConnection();
        Connection connection = identifiedConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement(s +criteria.getCriteriaName()+ s2);
        statement.setInt(1, place);
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();
        int result = resultSet.getInt(1);
        identifiedConnection.release();
        return result;
    }

    private Theatre getTheatreById(int id){
        try {
            DatabaseConnection.IdentifiedConnection identifiedConnection = DatabaseConnection.openConnection();
            Connection connection = identifiedConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement("select id, name, address, rating, capacity, distance, image from theatre where id=?");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            identifiedConnection.release();
            if(!resultSet.next()) return null;
            return new Theatre(id, resultSet.getString(2), resultSet.getString(3), resultSet.getInt(4), resultSet.getInt(5), resultSet.getInt(6), resultSet.getString(7));
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
