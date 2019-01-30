package psrod.doublelinkedlist.enums;

import psrod.doublelinkedlist.exceptions.NotImplementedException;

public enum Criteria {
    ADDRESS, CAPACITY, RATING, DISTANCE, NAME;

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
