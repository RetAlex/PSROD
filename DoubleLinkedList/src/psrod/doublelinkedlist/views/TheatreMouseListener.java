package psrod.doublelinkedlist.views;

import psrod.doublelinkedlist._main.Runner;
import psrod.doublelinkedlist.enums.Commands;
import psrod.doublelinkedlist.enums.Criteria;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class TheatreMouseListener implements MouseListener {
    private final Criteria criteria;
    private final Commands command;

    public TheatreMouseListener(Criteria criteria, Commands command) {
        this.criteria = criteria;
        this.command = command;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Runner.processCommand(command, criteria);
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
