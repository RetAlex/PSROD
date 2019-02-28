package psrod.doublelinkedlist.services;

import psrod.doublelinkedlist.entities.Theatre;
import psrod.doublelinkedlist.enums.Criteria;

import java.util.List;
import java.util.Map;

public interface SortingService {
    void makeTask(List<Theatre> theatres);
    Map<Criteria, Theatre> getPrevNeighbours(Integer currentId);
    Map<Criteria, Theatre> getNextNeighbours(Integer currentId);
    Theatre getNext(int currentId, Criteria criteria);
    Theatre getPrevious(int currentId, Criteria criteria);
    void addTheatre(Theatre theatre);
    void removeTheatre(int theatreId);
}
