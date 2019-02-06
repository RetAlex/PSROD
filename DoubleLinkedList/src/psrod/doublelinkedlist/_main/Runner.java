package psrod.doublelinkedlist._main;

import psrod.doublelinkedlist.entities.Theatre;
import psrod.doublelinkedlist.enums.Commands;
import psrod.doublelinkedlist.enums.Criteria;
import psrod.doublelinkedlist.services.SortingService;
import psrod.doublelinkedlist.storage.TheatresDAO;
import psrod.doublelinkedlist.views.MainView;

public class Runner {
    private static SortingService.MultiDimensionalNode<Theatre> element = null;
    private static MainView mainView;

    public static void main(String[] args){
        element = SortingService.makeTask(TheatresDAO.getAllTheatres());
        mainView = new MainView();
        mainView.renderView(element);
    }

    public static void processCommand(Commands command, Criteria criteria) {
        switch (command){
            case NEXT:
                element = element.getNext(criteria.getCriteriaName());
                mainView.clearFrame();
                mainView.renderView(element);
                break;
            case PREVIOUS:
                element = element.getPrevious(criteria.getCriteriaName());
                mainView.clearFrame();
                mainView.renderView(element);
                break;
        }
    }
}
