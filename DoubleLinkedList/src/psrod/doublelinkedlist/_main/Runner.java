package psrod.doublelinkedlist._main;

import psrod.doublelinkedlist.services.DatabaseSortingService;
import psrod.doublelinkedlist.services.SortingService;
import psrod.doublelinkedlist.services.SortingServiceLinked;
import psrod.doublelinkedlist.storage.TheatresDAO;
import psrod.doublelinkedlist.views.MainView;

import javax.swing.*;

public class Runner {
    private static MainView mainView;
    private static SortingService sortingService;

    public static void main(String[] args){
        sortingService = new DatabaseSortingService();
        sortingService.makeTask(TheatresDAO.getAllTheatres());
        showElement(0);
    }

    public static SortingService getSortingService(){
        return sortingService;
    }

    public static void showElement(int id){
        JFrame old = mainView;
        mainView = new MainView(SortingServiceLinked.getNodeById(id).getElement(), sortingService.getPrevNeighbours(id), sortingService.getNextNeighbours(id));
        if(old!=null) old.dispose();
    }
}
