package psrod.doublelinkedlist.views.main;

import psrod.doublelinkedlist.enums.Criteria;

import javax.swing.*;

public class NeighboursPanel {
    public JPanel MainPanel;
    public PreviewCard Name;
    public PreviewCard Address;
    public PreviewCard Rating;
    public PreviewCard Distance;
    public PreviewCard Capacity;

    public NeighboursPanel() {
        Name.Criteria.setText("Name");
        Address.Criteria.setText("CurrentAddress");
        Rating.Criteria.setText("Rating");
        Distance.Criteria.setText("Distance");
        Capacity.Criteria.setText("Capacity");
    }

    public PreviewCard getCard(Criteria criteria){
        switch (criteria){
            case NAME: return Name;
            case RATING: return Rating;
            case ADDRESS: return Address;
            case CAPACITY: return Capacity;
            case DISTANCE: return Distance;
        }
        throw new RuntimeException();
    }
}
