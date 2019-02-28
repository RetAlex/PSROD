package psrod.doublelinkedlist.views.modals;

import psrod.doublelinkedlist._main.Runner;
import psrod.doublelinkedlist.entities.Theatre;
import psrod.doublelinkedlist.storage.TheatresDAO;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class EditTheatreForm {
    private int theatreId;
    private JFrame mainFrame;
    private JTextField Name;
    private JButton addButton;
    private JTextField Address;
    private JTextField Rating;
    private JTextField Distance;
    private JTextField Image;
    private JPanel MainForm;
    private JTextField Capacity;

    public EditTheatreForm(String name, String address, int rating, int capacity, int distance, String image, int id) {
        this.theatreId = id;
        Name.setText(name);
        Address.setText(address);
        Rating.setText(String.valueOf(rating));
        Capacity.setText(String.valueOf(capacity));
        Distance.setText(String.valueOf(distance));
        Image.setText(image);
        mainFrame = new JFrame();
        mainFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        mainFrame.setContentPane(MainForm);
        mainFrame.pack();
        mainFrame.setVisible(true);
        addButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Theatre theatre = new Theatre(TheatresDAO.getId(), Name.getText(), Address.getText(), Integer.parseInt(Rating.getText()), Integer.parseInt(Capacity.getText()), Integer.parseInt(Distance.getText()), Image.getText());
                TheatresDAO.editTheatre(theatreId, theatre);
                Runner.getSortingService().makeTask(TheatresDAO.getAllTheatres());
                Runner.showElement(theatre.getId());
                mainFrame.dispose();
            }
        });
    }
}
