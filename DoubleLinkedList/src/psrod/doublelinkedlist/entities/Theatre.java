package psrod.doublelinkedlist.entities;

public class Theatre {
    // Unique identifier of theatre in DB
    private int id;
    // Name of the theatre
    private String name;
    // Full address of theatre
    private String address;
    // Average rating of theatre up to 50 (0.0-5.0)
    private int rating;
    // Sum of every hall capacity
    private int capacity;
    // Counted in milliseconds from nearest subway station
    private int distance;

    public Theatre(int id, String name, String address, int rating, int capacity, int distance) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.rating = rating;
        this.capacity = capacity;
        this.distance = distance;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public int getRating() {
        return rating;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getDistance() {
        return distance;
    }

    @Override
    public String toString() {
        return "Theatre{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", rating=" + rating +
                ", capacity=" + capacity +
                ", distance=" + distance +
                '}';
    }
}
