package psrod.doublelinkedlist.storage;

import psrod.doublelinkedlist.entities.Theatre;
import psrod.doublelinkedlist.enums.Criteria;
import psrod.doublelinkedlist.utils.TimeUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TheatresDAO {
    private static List<Theatre> localStorage = List.of(
            new Theatre(0, "Theatre 1", "Street 2", 46, 150, 15*TimeUtils.minute, "theatres/1.jpg"),
            new Theatre(1, "Theatre 2", "Street 1", 38, 260, 20*TimeUtils.minute, "theatres/2.jpg"),
            new Theatre(2, "Theatre 3", "Street 12", 99, 15, 10*TimeUtils.minute, "theatres/3.jpg"),
            new Theatre(3, "Theatre 4", "AStreet 1", 12, 500, 11*TimeUtils.minute, "theatres/4.jpg"),
            new Theatre(4, "Theatre 5", "Street 1s", 62, 210, 13*TimeUtils.minute, "theatres/5.jpg")
    );

    private static int lastId;
    private static List<Theatre> runtimeStorage;

    public static List<Theatre> getAllTheatres(){
        if(runtimeStorage==null){
            runtimeStorage = new ArrayList<>(localStorage);
            for(Theatre theatre: runtimeStorage){
                if(theatre.getId()>lastId) lastId=theatre.getId();
            }
        }
        return runtimeStorage;
    }

    public static void addTheatre(Theatre theatre){
        runtimeStorage.add(theatre);
    }

    public static void removeTheatre(int id){
        runtimeStorage.removeIf(theatre -> theatre.getId() == id);
    }

    public static void editTheatre(int id, Theatre theatre){
        assert theatre.getId() == id;
        removeTheatre(id);
        addTheatre(theatre);
    }

    public static Theatre getByField(Criteria criteria, String data){
        try {
            switch (criteria) {
                case DISTANCE:
                    return runtimeStorage.stream().filter(t -> t.getDistance() == Integer.parseInt(data)).collect(Collectors.toList()).get(0);
                case ADDRESS:
                    return runtimeStorage.stream().filter(t -> t.getAddress().equals(data)).collect(Collectors.toList()).get(0);
                case CAPACITY:
                    return runtimeStorage.stream().filter(t -> t.getCapacity() == Integer.parseInt(data)).collect(Collectors.toList()).get(0);
                case NAME:
                    return runtimeStorage.stream().filter(t -> t.getName().equals(data)).collect(Collectors.toList()).get(0);
                case RATING:
                    return runtimeStorage.stream().filter(t -> t.getRating() == Integer.parseInt(data)).collect(Collectors.toList()).get(0);
            }
        }catch (Exception e) {
            throw new RuntimeException("Item not found exception", e);
        }
        throw new RuntimeException("Invalid criteria");
    }
    public static int getId(){
        return ++lastId;
    }
}
