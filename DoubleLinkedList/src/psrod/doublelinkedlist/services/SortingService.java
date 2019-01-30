package psrod.doublelinkedlist.services;

import psrod.doublelinkedlist.entities.Theatre;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SortingService {
    private static Map<Integer, MultiDimensionalNode<Theatre>> allTheatres = new HashMap<>();

    public static MultiDimensionalNode<Theatre> makeTask(List<Theatre> theatres){
        for(String criteria: List.of("address", "capacity", "rating", "distance", "name")) {
            sortByCriteria(criteria, theatres);
            addSortingDimension(criteria, theatres);
        }
        MultiDimensionalNode<Theatre> nodeToReturn = allTheatres.get(theatres.get(0).getId());
        allTheatres=null;
        return nodeToReturn;
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

        public MultiDimensionalNode<T> getPrevious(String criteria){
            return previousElements.getOrDefault(criteria, null);
        }

        public T getElement(){
            return element;
        }
    }
}
