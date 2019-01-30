package psrod.doublelinkedlist._main;

import psrod.doublelinkedlist.entities.Theatre;
import psrod.doublelinkedlist.services.SortingService;
import psrod.doublelinkedlist.storage.TheatresDAO;

public class Runner {
    public static void main(String[] args){
        SortingService.MultiDimensionalNode<Theatre> firstElement = SortingService.makeTask(TheatresDAO.getAllTheatres());
        String currentCriteria = "name";
        System.out.println("Current criteria is:");
    }
}
