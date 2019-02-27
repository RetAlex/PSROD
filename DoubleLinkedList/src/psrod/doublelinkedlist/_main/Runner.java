package psrod.doublelinkedlist._main;

import psrod.doublelinkedlist.services.SortingService;
import psrod.doublelinkedlist.storage.TheatresDAO;
import psrod.doublelinkedlist.views.MainView;

import javax.swing.*;

public class Runner {
    private static MainView mainView;

    public static void main(String[] args){
        SortingService.makeTask(TheatresDAO.getAllTheatres());
        showElement(0);
    }

    public static void showElement(int id){
        JFrame old = mainView;
        mainView = new MainView(SortingService.getNodeById(id).getElement(), SortingService.getPrevNeighbours(id), SortingService.getNextNeighbours(id));
        if(old!=null) old.dispose();
    }
}
