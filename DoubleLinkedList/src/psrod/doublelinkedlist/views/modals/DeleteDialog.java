package psrod.doublelinkedlist.views.modals;

import psrod.doublelinkedlist._main.Runner;
import psrod.doublelinkedlist.services.SortingService;
import psrod.doublelinkedlist.storage.TheatresDAO;

import javax.swing.*;
import java.awt.event.*;

public class DeleteDialog extends JDialog {
    private int theatreId;
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;

    public DeleteDialog(int theatreId) {
        this.theatreId = theatreId;
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(e -> onOK());

        buttonCancel.addActionListener(e -> onCancel());

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        TheatresDAO.removeTheatre(theatreId);
        SortingService.makeTask(TheatresDAO.getAllTheatres());
        Runner.showElement(TheatresDAO.getAllTheatres().get(0).getId());
        dispose();
    }

    private void onCancel() {
        dispose();
    }
}
