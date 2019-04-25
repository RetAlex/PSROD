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
                int id = theatres.get(i).getId();
                var value = allTheatres.getOrDefault(id, new HashMap<>());
                value.put(criteria, i);
                allTheatres.put(id, value);
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
            statement.executeUpdate("delete from indexes");
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
            insert.executeBatch();
            connection.commit();
            identifiedConnection.release();
        } catch (Exception e){
            throw new RuntimeException("Bad db config", e);
        }
    }

    @Override
    public Theatre getNext(int currentId, Criteria criteria) {
        int currentPlace = DatabaseConnection.getPlaceByCriteria(currentId, criteria);
        return currentPlace==-1?null:DatabaseConnection.getTheatreById(DatabaseConnection.getIdByPlaceInCriteria(currentPlace+1, criteria));
    }

    @Override
    public Theatre getPrevious(int currentId, Criteria criteria) {
        int currentPlace = DatabaseConnection.getPlaceByCriteria(currentId, criteria);
        return currentPlace==-1?null:DatabaseConnection.getTheatreById(DatabaseConnection.getIdByPlaceInCriteria(currentPlace-1, criteria));
    }

    @Override
    public void addTheatre(Theatre theatre) {
        makeTask(DatabaseConnection.getAllTheatres());
    }

    @Override
    public void removeTheatre(int theatreId) {
        makeTask(DatabaseConnection.getAllTheatres());
    }
}
