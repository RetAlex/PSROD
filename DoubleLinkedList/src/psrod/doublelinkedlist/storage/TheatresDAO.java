package psrod.doublelinkedlist.storage;

import psrod.doublelinkedlist.entities.Theatre;
import psrod.doublelinkedlist.utils.TimeUtils;

import java.util.ArrayList;
import java.util.List;

public class TheatresDAO {
    private static List<Theatre> localStorage = List.of(
            new Theatre(0, "Theatre 1", "Street 2", 46, 150, 15*TimeUtils.minute),
            new Theatre(1, "Theatre 2", "Street 1", 38, 260, 20*TimeUtils.minute)
    );

    public static List<Theatre> getAllTheatres(){
        return new ArrayList<>(localStorage);
    }
}
