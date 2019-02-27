package psrod.doublelinkedlist.views.main;

import psrod.doublelinkedlist._main.Runner;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PreviewCard {
    public JPanel MainPanel;
    public JLabel Criteria;
    public JLabel Image;
    public JLabel Theatre;
    public JButton Choose;

    private int theatreId;

    public PreviewCard() {
        Choose.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Runner.showElement(theatreId);
            }
        });
    }

    public void setTheatreId(int theatreId) {
        this.theatreId = theatreId;
    }
}
