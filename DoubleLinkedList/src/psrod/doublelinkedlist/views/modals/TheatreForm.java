package psrod.doublelinkedlist.views.modals;

import psrod.doublelinkedlist._main.Runner;
import psrod.doublelinkedlist.entities.Theatre;
import psrod.doublelinkedlist.services.SortingService;
import psrod.doublelinkedlist.storage.TheatresDAO;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TheatreForm {
    private JFrame mainFrame;
    private JTextField Name;
    private JButton addButton;
    private JTextField Address;
    private JTextField Rating;
    private JTextField Distance;
    private JTextField Image;
    private JPanel MainForm;
    private JTextField Capacity;

    public TheatreForm() {
        mainFrame = new JFrame();
        mainFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        mainFrame.setContentPane(MainForm);
        mainFrame.pack();
        mainFrame.setVisible(true);
        addButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Theatre theatre = new Theatre(TheatresDAO.getId(), Name.getText(), Address.getText(), Integer.parseInt(Rating.getText()), Integer.parseInt(Capacity.getText()), Integer.parseInt(Distance.getText()), Image.getText());
                TheatresDAO.addTheatre(theatre);
                SortingService.makeTask(TheatresDAO.getAllTheatres());
                Runner.showElement(theatre.getId());
                mainFrame.dispose();
            }
        });
    }
}
