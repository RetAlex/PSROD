package psrod.doublelinkedlist.services;

import psrod.doublelinkedlist.entities.Theatre;
import psrod.doublelinkedlist.enums.Criteria;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SortingServiceLinked extends AbstractSortService {
    private static Map<Integer, MultiDimensionalNode<Theatre>> allTheatres = new HashMap<>();

    public void makeTask(List<Theatre> theatres){
        allTheatres = new HashMap<>();
        for(Criteria criteria: Criteria.values()) {
            sortByCriteria(criteria.getCriteriaName(), theatres);
            addSortingDimension(criteria.getCriteriaName(), theatres);
        }
    }

    public Theatre getNext(int currentId, Criteria criteria){
        return getNodeById(currentId).getNext(criteria).getElement();
    }
    public Theatre getPrevious(int currentId, Criteria criteria){
        return getNodeById(currentId).getPrevious(criteria).getElement();
    }

    @Override
    public void addTheatre(Theatre theatre) {

    }

    @Override
    public void removeTheatre(int theatreId) {

    }

    public Map<Criteria, Theatre> getPrevNeighbours(Integer currentId){
        Map<Criteria, Theatre> result = new HashMap<>();
        MultiDimensionalNode<Theatre> node = getNodeById(currentId);
        for(Criteria criteria: Criteria.values()){
            MultiDimensionalNode<Theatre> element = node.previousElements.get(criteria.getCriteriaName());
            result.put(criteria, element==null?null:element.getElement());
        }
        return result;
    }

    public Map<Criteria, Theatre> getNextNeighbours(Integer currentId){
        Map<Criteria, Theatre> result = new HashMap<>();
        MultiDimensionalNode<Theatre> node = getNodeById(currentId);
        for(Criteria criteria: Criteria.values()){
            MultiDimensionalNode<Theatre> element = node.nextElements.get(criteria.getCriteriaName());
            result.put(criteria, element==null?null:element.getElement());
        }
        return result;
    }
    private static void addSortingDimension(String criteria, List<Theatre> theatres){
        MultiDimensionalNode<Theatre> previous = null;
        for(Theatre theatre: theatres){
            MultiDimensionalNode<Theatre> newNode = getNodeById(theatre);
            allTheatres.put(theatre.getId(), newNode);
            if(previous!=null){
                previous.addNext(criteria, newNode);
                newNode.addPrevious(criteria, previous);
            }
            previous = newNode;
        }
    }


    private static MultiDimensionalNode<Theatre> getNodeById(Theatre theatre){
        return allTheatres.getOrDefault(theatre.getId(), new MultiDimensionalNode<>(theatre));
    }

    public static MultiDimensionalNode<Theatre> getNodeById(int id){
        MultiDimensionalNode<Theatre> node = allTheatres.get(id);
        if(node==null) throw new TheatreNotExistsException();
        return node;
    }

    public static class MultiDimensionalNode<T>{
        private T element;
        private Map<String, MultiDimensionalNode<T>> previousElements;
        private Map<String, MultiDimensionalNode<T>> nextElements;

        private MultiDimensionalNode(T element) {
            this.element = element;
            this.previousElements = new HashMap<>();
            this.nextElements = new HashMap<>();
        }

        private void addNext(String criteria, MultiDimensionalNode<T> node){
            nextElements.put(criteria, node);
        }

        private void addPrevious(String criteria, MultiDimensionalNode<T> node){
            previousElements.put(criteria, node);
        }

        public MultiDimensionalNode<T> getNext(String criteria){
            return nextElements.getOrDefault(criteria, null);
        }

        public MultiDimensionalNode<T> getNext(Criteria criteria){
            return nextElements.getOrDefault(criteria.getCriteriaName(), null);
        }

        public MultiDimensionalNode<T> getPrevious(String criteria){
            return previousElements.getOrDefault(criteria, null);
        }

        public MultiDimensionalNode<T> getPrevious(Criteria criteria){
            return previousElements.getOrDefault(criteria.getCriteriaName(), null);
        }

        public T getElement(){
            return element;
        }
    }
}
