package psrod.doublelinkedlist._main;

import psrod.doublelinkedlist.entities.Theatre;
import psrod.doublelinkedlist.enums.Commands;
import psrod.doublelinkedlist.enums.Criteria;
import psrod.doublelinkedlist.services.SortingService;
import psrod.doublelinkedlist.storage.TheatresDAO;

import java.io.IOException;
import java.util.Arrays;

public class Runner {
    private static SortingService.MultiDimensionalNode<Theatre> element = null;
    private static Criteria currentCriteria = Criteria.NAME;

    public static void main(String[] args){
        element = SortingService.makeTask(TheatresDAO.getAllTheatres());
        Commands command = Commands.NEXT;
        while(!command.equals(Commands.FINISH)) {
            System.out.println("Current criteria is: " + currentCriteria.getCriteriaName());
            System.out.println("Current element is: "+element.getElement().toString());
            System.out.println("Type in next command to do "+ Arrays.toString(Commands.values()) +": ");
            try {
                command = Commands.valueOf(readFromConsole().toUpperCase());
                processCommand(command);
            } catch (IOException | IllegalArgumentException e) {
                System.out.println("Can't process input ["+e.getClass()+": "+e.getMessage()+"], please try again.");
            }
        }
    }

    private static void processCommand(Commands command) throws IOException {
        switch (command){
            case NEXT:
                element = element.getNext(currentCriteria.getCriteriaName());
                break;
            case PREVIOUS:
                element = element.getPrevious(currentCriteria.getCriteriaName());
                break;
            case SWITCH:
                System.out.println("Choose new category "+ Arrays.toString(Criteria.values())+"");
                currentCriteria = Criteria.valueOf(readFromConsole());
        }
    }

    public static String readFromConsole() throws IOException {
        StringBuilder ret = new StringBuilder();
        char character;
        while(true){
            character = (char) System.in.read();
            if(character=='\n') break;
            ret.append(character);
        }
        return ret.toString();
    }
}
