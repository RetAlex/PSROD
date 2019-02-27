package psrod.doublelinkedlist.views.modals;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class NotFound {
    private JPanel MainFrame;
    private JButton okButton;

    public NotFound() {
        JFrame frame = new JFrame("Not found");
        frame.setContentPane(MainFrame);
        frame.pack();
        frame.setVisible(true);
        okButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                frame.dispose();
            }
        });
    }
}
