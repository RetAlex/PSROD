package psrod.doublelinkedlist.entities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

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
    // Image url for this theatre
    private String imageURL;

    public Theatre(int id, String name, String address, int rating, int capacity, int distance, String imageURL) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.rating = rating;
        this.capacity = capacity;
        this.distance = distance;
        this.imageURL = imageURL;
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

    public String getImageURL() {
        return imageURL;
    }

    public InputStream loadImage() throws FileNotFoundException {
        InputStream is = Theatre.class.getClassLoader().getResourceAsStream(imageURL);
        if(is == null){
            File file = new File(imageURL);
            if(!file.exists()) throw new FileNotFoundException();
            is = new FileInputStream(file);
        }
        return is;
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
