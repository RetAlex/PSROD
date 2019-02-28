package psrod.doublelinkedlist.services;

import psrod.doublelinkedlist.entities.Theatre;
import psrod.doublelinkedlist.enums.Criteria;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SortingServiceIndex implements SortingService {
    private static Map<Integer, IndexedRecord<Theatre>> allTheatres = new HashMap<>();

    private int[][] sorted = new int[Criteria.values().length][];

    public void makeTask(List<Theatre> theatres){
        allTheatres = new HashMap<>();
        for(Criteria criteria: Criteria.values()) {
            sortByCriteria(criteria.getCriteriaName(), theatres);
            addSortingDimension(criteria, theatres);
        }
    }

    private void sortByCriteria(String criteria, List<Theatre> theatres){
        switch (criteria) {
            case "address":
                theatres.sort(Comparator.comparing(Theatre::getAddress));
                break;
            case "capacity":
                theatres.sort(Comparator.comparing(Theatre::getCapacity));
                break;
            case "rating":
                theatres.sort(Comparator.comparing(Theatre::getRating));
                break;
            case "distance":
                theatres.sort(Comparator.comparing(Theatre::getDistance));
                break;
            case "name":
                theatres.sort(Comparator.comparing(Theatre::getName));
        }
    }

    public Theatre getNext(int currentId, Criteria criteria){
        IndexedRecord<Theatre> currentPositions = getNodeById(currentId);
        return getNodeById(sorted[criteria.ordinal()][currentPositions.positions.get(criteria)+1]).element;
    }
    public Theatre getPrevious(int currentId, Criteria criteria){
        IndexedRecord<Theatre> currentPositions = getNodeById(currentId);
        return getNodeById(sorted[criteria.ordinal()][currentPositions.positions.get(criteria)-1]).element;
    }

    @Override
    public void addTheatre(Theatre theatre) {

    }

    @Override
    public void removeTheatre(int theatreId) {

    }

    public Map<Criteria, Theatre> getPrevNeighbours(Integer currentId){
        Map<Criteria, Theatre> result = new HashMap<>();
        IndexedRecord<Theatre> node = getNodeById(currentId);
        for(Criteria criteria: Criteria.values()){
            IndexedRecord<Theatre> element = getNodeById(sorted[criteria.ordinal()][node.positions.get(criteria)-1]);
            result.put(criteria, element==null?null:element.element);
        }
        return result;
    }

    public Map<Criteria, Theatre> getNextNeighbours(Integer currentId){
        Map<Criteria, Theatre> result = new HashMap<>();
        IndexedRecord<Theatre> node = getNodeById(currentId);
        for(Criteria criteria: Criteria.values()){
            IndexedRecord<Theatre> element = getNodeById(sorted[criteria.ordinal()][node.positions.get(criteria)+1]);
            result.put(criteria, element==null?null:element.element);
        }
        return result;
    }

    private void addSortingDimension(Criteria criteria, List<Theatre> theatres){
        sorted[criteria.ordinal()] = new int[theatres.size()];
        for(int i = 0; i<theatres.size(); i++){
            Theatre theatre = theatres.get(i);
            sorted[criteria.ordinal()][i]=theatre.getId();
            getNodeById(theatre.getId()).addCriteria(criteria, i);
        }
    }


    private IndexedRecord<Theatre> getNodeById(int id){
        IndexedRecord<Theatre> node = allTheatres.get(id);
        if(node==null) throw new TheatreNotExistsException();
        return node;
    }

    public static class IndexedRecord<T>{
        private T element;
        private Map<Criteria, Integer> positions;

        public IndexedRecord(T element){
            this.element=element;
        }

        public void addCriteria(Criteria criteria, Integer position){
            this.positions.put(criteria, position);
        }
    }
}
