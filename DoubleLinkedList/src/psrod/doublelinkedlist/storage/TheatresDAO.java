package psrod.doublelinkedlist.storage;

import psrod.doublelinkedlist.entities.Theatre;
import psrod.doublelinkedlist.utils.TimeUtils;

import java.util.ArrayList;
import java.util.List;

public class TheatresDAO {
    private static List<Theatre> localStorage = List.of(
            new Theatre(0, "Theatre 1", "Street 2", 46, 150, 15*TimeUtils.minute, "theatres/1.jpg"),
            new Theatre(1, "Theatre 2", "Street 1", 38, 260, 20*TimeUtils.minute, "theatres/2.jpg"),
            new Theatre(2, "Theatre 3", "Street 12", 99, 15, 10*TimeUtils.minute, "theatres/3.jpg"),
            new Theatre(3, "Theatre 4", "AStreet 1", 12, 500, 11*TimeUtils.minute, "theatres/4.jpg"),
            new Theatre(4, "Theatre 5", "Street 1s", 62, 210, 13*TimeUtils.minute, "theatres/5.jpg")
    );

    public static List<Theatre> getAllTheatres(){
        return new ArrayList<>(localStorage);
    }
}
