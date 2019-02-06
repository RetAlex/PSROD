package psrod.doublelinkedlist.views;

import psrod.doublelinkedlist.entities.Theatre;
import psrod.doublelinkedlist.enums.Commands;
import psrod.doublelinkedlist.enums.Criteria;
import psrod.doublelinkedlist.services.SortingService;

import javax.swing.*;
import java.awt.*;

public class MainView extends JFrame {
    private final int width = 1500, height = 1100;
    private final int offset = 100;

    public MainView() throws HeadlessException {
        super("Theatres");
        this.setSize(width, height);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setVisible(true);
    }

    public void renderView(SortingService.MultiDimensionalNode<Theatre> data){
        showLayout();
        showPreviousPart(data);
        showCurrentPart(data.getElement());
        showNextPart(data);
        this.repaint();
    }

    private void showLayout(){
        drawLine(width/3-50, 0, 5, height);
        drawLine(width/3*2+50, 0, 5, height);
    }

    private void showPreviousPart(SortingService.MultiDimensionalNode<Theatre> data){
        int index = -1, itemHeight = (height-offset)/Criteria.values().length;
        for(Criteria criteria: Criteria.values()){
            showLabel(0, offset/2+(++index)*itemHeight, width/3-50, 20, "<html><h3>"+criteria.prevName+"</h3></html>");
            if(index<Criteria.values().length-1) drawLine(0, offset/2+(index+1)*itemHeight, width/3-50, 5);
            SortingService.MultiDimensionalNode<Theatre> prev = data.getPrevious(criteria);
            if(prev==null){
                showEmpty(width/12, offset/2+index*itemHeight+20, width/6-50, itemHeight-30);
                continue;
            }
            TheatreView theatreView = showTheatre(width/12, offset/2+index*itemHeight+20, width/6-50, itemHeight-30, prev.getElement(), true);
            theatreView.addMouseListener(new TheatreMouseListener(criteria, Commands.PREVIOUS));
        }
    }

    private void showNextPart(SortingService.MultiDimensionalNode<Theatre> data){
        int index = -1, itemHeight = (height-offset)/Criteria.values().length;
        for(Criteria criteria: Criteria.values()){
            showLabel(width/3*2+50, offset/2+(++index)*itemHeight, width/3-50, 20, "<html><h3>"+criteria.nextName+"</h3></html>");
            if(index<Criteria.values().length-1) drawLine(width/3*2+50, offset/2+(index+1)*itemHeight, width/3-50, 5);
            SortingService.MultiDimensionalNode<Theatre> next = data.getNext(criteria);
            if(next==null){
                showEmpty(width/3*2+width/12+50, offset/2+index*itemHeight+20, width/6-50, itemHeight-30);
                continue;
            }
            TheatreView theatreView = showTheatre(width/3*2+width/12+50, offset/2+index*itemHeight+20, width/6-50, itemHeight-30, next.getElement(), true);
            theatreView.addMouseListener(new TheatreMouseListener(criteria, Commands.NEXT));
        }
    }

    private void showCurrentPart(Theatre mainTheatre){
        int mainFrameHeight = height/2;
        showLabel(width/3+10, height/2-mainFrameHeight/2-70, width/3-20, 60, "<html><h1>Current theatre:</h1></html>");
        showTheatre(width/3+10, height/2-height/4, width/3-20, height/2, mainTheatre, false);
    }

    private TheatreView showTheatre(int x, int y, int width, int height, Theatre theatre, boolean shortVersion){
        TheatreView theatreView = new TheatreView(x, y, width, height, theatre, shortVersion);
        this.add(theatreView);
        theatreView.setVisible(true);
        return theatreView;
    }

    private void showLabel(int x, int y, int width, int height, String text){
        JLabel label = new JLabel();
        label.setBounds(x, y, width, height);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setText(text);
        label.setVisible(true);
        this.add(label);
    }

    private void showEmpty(int x, int y, int width, int height){
        JLabel empty = new JLabel();
        empty.setText("This element is last.");
        empty.setBounds(x, y, width, height);
        empty.setVisible(true);
        empty.setHorizontalAlignment(SwingConstants.CENTER);
        empty.setVerticalAlignment(SwingConstants.CENTER);
        this.add(empty);
    }

    public void clearFrame(){
        this.getContentPane().removeAll();
        this.validate();
        this.repaint();
    }

    private void drawLine(int x, int y, int width, int height){
        JLabel line = new JLabel();
        line.setOpaque(true);
        line.setBounds(x, y, width, height);
        line.setBackground(Color.BLACK);
        line.setVisible(true);
        this.add(line);
    }
}
