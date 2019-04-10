package psrod.doublelinkedlist.services;

import psrod.doublelinkedlist.entities.Theatre;
import psrod.doublelinkedlist.enums.Criteria;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

abstract class AbstractSortService implements SortingService {

    @Override
    public Map<Criteria, Theatre> getPrevNeighbours(Integer currentId) {
        Map<Criteria, Theatre> result = new HashMap<>();
        for(Criteria criteria: Criteria.values()){
            result.put(criteria, getPrevious(currentId, criteria));
        }
        return result;
    }

    @Override
    public Map<Criteria, Theatre> getNextNeighbours(Integer currentId) {
        Map<Criteria, Theatre> result = new HashMap<>();
        for(Criteria criteria: Criteria.values()){
            result.put(criteria, getNext(currentId, criteria));
        }
        return result;
    }

    void sortByCriteria(String criteria, List<Theatre> theatres){
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
}
