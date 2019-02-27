package psrod.doublelinkedlist.views.main;

import psrod.doublelinkedlist.views.modals.DeleteDialog;
import psrod.doublelinkedlist.views.modals.SearchForm;
import psrod.doublelinkedlist.views.modals.TheatreForm;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainForm {
    public JPanel MainPanel;
    public JPanel MiddlePanel;
    public JPanel Content;
    public JPanel ActionsPanel;
    public JButton Add;
    public JButton Delete;
    public JButton Search;
    public JButton Edit;
    public NeighboursPanel LeftPanel;
    public NeighboursPanel RightPanel;
    public JLabel CurrentName;
    public JLabel CurrentImage;
    public JLabel CurrentDistance;
    public JLabel CurrentRating;
    public JLabel CurrentCapacity;
    public JLabel CurrentAddress;

    public MainForm(int theatreId) {
        Add.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                new TheatreForm();
            }
        });
        Delete.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                DeleteDialog dialog = new DeleteDialog(theatreId);
                dialog.pack();
                dialog.setVisible(true);
            }
        });
        Search.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new SearchForm();
            }
        });
    }
}
