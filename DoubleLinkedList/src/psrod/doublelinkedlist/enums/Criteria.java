package psrod.doublelinkedlist.enums;

import psrod.doublelinkedlist.exceptions.NotImplementedException;

public enum Criteria {
    ADDRESS("Address asc alphabetical", "Address desc alphabetical"), CAPACITY("Capacity higher", "Capacity lower"), RATING("Rating higher", "Rating lower"), DISTANCE("Distance bigger", "Distance smaller"), NAME("Name asc alphabetical", "Name desc alphabetical");

    public String nextName, prevName;

    Criteria(String nextName, String prevName) {
        this.nextName = nextName;
        this.prevName = prevName;
    }

    public String getCriteriaName(){
        switch (this){
            case NAME:
                return "name";
            case RATING:
                return "rating";
            case ADDRESS:
                return "address";
            case CAPACITY:
                return "capacity";
            case DISTANCE:
                return "distance";
        }
        throw new NotImplementedException();
    }
}
