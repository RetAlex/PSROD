package psrod.doublelinkedlist._main;

import psrod.doublelinkedlist.db.DatabaseConnection;
import psrod.doublelinkedlist.services.SortingService;
import psrod.doublelinkedlist.services.SortingServiceLinked;
import psrod.doublelinkedlist.storage.TheatresDAO;
import psrod.doublelinkedlist.views.MainView;

import javax.swing.*;

public class Runner {
    private static MainView mainView;
    private static SortingService sortingService;

    public static void main(String[] args){
        sortingService = new SortingServiceLinked();
        sortingService.makeTask(TheatresDAO.getAllTheatres());
        showElement(2);
    }

    public static SortingService getSortingService(){
        return sortingService;
    }

    public static void showElement(int id){
        JFrame old = mainView;
        mainView = new MainView(DatabaseConnection.getTheatreById(id), sortingService.getPrevNeighbours(id), sortingService.getNextNeighbours(id));
        if(old!=null) old.dispose();
    }
}
