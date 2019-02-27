package psrod.doublelinkedlist.views.modals;

import psrod.doublelinkedlist._main.Runner;
import psrod.doublelinkedlist.entities.Theatre;
import psrod.doublelinkedlist.enums.Criteria;
import psrod.doublelinkedlist.storage.TheatresDAO;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SearchForm {
    private JTextField Data;
    private JButton Search;
    private JComboBox<Criteria> CriteriaBox;
    private JPanel Content;

    public SearchForm() {
        CriteriaBox.setModel(new DefaultComboBoxModel<>(Criteria.values()));
        JFrame frame = new JFrame("Search");
        frame.setContentPane(Content);
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        Search.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                try {
                    Theatre theatre = TheatresDAO.getByField((Criteria) CriteriaBox.getSelectedItem(), Data.getText());
                    Runner.showElement(theatre.getId());
                }catch (Exception ex){
                    new NotFound();
                } finally {
                    frame.dispose();
                }
            }
        });
    }
}
