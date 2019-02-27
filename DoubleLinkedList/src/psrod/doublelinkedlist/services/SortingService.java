package psrod.doublelinkedlist.services;

import psrod.doublelinkedlist.entities.Theatre;
import psrod.doublelinkedlist.enums.Criteria;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SortingService {
    private static Map<Integer, MultiDimensionalNode<Theatre>> allTheatres = new HashMap<>();

    public static void makeTask(List<Theatre> theatres){
        allTheatres = new HashMap<>();
        for(Criteria criteria: Criteria.values()) {
            sortByCriteria(criteria.getCriteriaName(), theatres);
            addSortingDimension(criteria.getCriteriaName(), theatres);
        }
    }

    private static void sortByCriteria(String criteria, List<Theatre> theatres){
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

    public static Theatre getNext(int currentId, Criteria criteria){
        return getNodeById(currentId).getNext(criteria).getElement();
    }
    public static Theatre getPrevious(int currentId, Criteria criteria){
        return getNodeById(currentId).getPrevious(criteria).getElement();
    }
    public static Map<Criteria, Theatre> getPrevNeighbours(Integer currentId){
        Map<Criteria, Theatre> result = new HashMap<>();
        MultiDimensionalNode<Theatre> node = getNodeById(currentId);
        for(Criteria criteria: Criteria.values()){
            MultiDimensionalNode<Theatre> element = node.previousElements.get(criteria.getCriteriaName());
            result.put(criteria, element==null?null:element.getElement());
        }
        return result;
    }

    public static Map<Criteria, Theatre> getNextNeighbours(Integer currentId){
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
