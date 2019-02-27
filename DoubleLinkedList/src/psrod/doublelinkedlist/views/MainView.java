package psrod.doublelinkedlist.views;

import psrod.doublelinkedlist.entities.Theatre;
import psrod.doublelinkedlist.enums.Criteria;
import psrod.doublelinkedlist.views.main.MainForm;
import psrod.doublelinkedlist.views.main.PreviewCard;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;

public class MainView extends JFrame {
    private MainForm form;

    public MainView(Theatre theatre, Map<Criteria, Theatre> previous, Map<Criteria, Theatre> followings) throws HeadlessException {
        super("Theatres");
        form = new MainForm(theatre.getId());
        this.setContentPane(form.MainPanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
        showElement(theatre, previous, followings);
    }

    public void showElement(Theatre theatre, Map<Criteria, Theatre> previous, Map<Criteria, Theatre> followings){
        for(Criteria criteria: Criteria.values()){
            fillPreview(form.LeftPanel.getCard(criteria), previous.get(criteria));
            fillPreview(form.RightPanel.getCard(criteria), followings.get(criteria));
        }
        form.CurrentName.setText(theatre.getName());
        showImage(form.CurrentImage, theatre, 200, 140);
        form.CurrentCapacity.setText("Capacity: "+theatre.getCapacity());
        form.CurrentAddress.setText("CurrentAddress: "+theatre.getAddress());
        form.CurrentRating.setText("Rating: "+theatre.getRating());
        form.CurrentDistance.setText("Distance: "+theatre.getDistance());
    }

    private void fillPreview(PreviewCard card, Theatre theatre){
        if(theatre==null){
            card.Theatre.setText("No element for this criteria");
            showEmptyImage(card.Image, 100, 70);
            card.Choose.setVisible(false);
            return;
        }
        card.setTheatreId(theatre.getId());
        card.Theatre.setText(theatre.getName());
        showImage(card.Image, theatre, 100, 70);
    }

    private void showImage(JLabel element, Theatre theatre, int width, int height){
        try {
            element.setIcon(new ImageIcon(ImageIO.read(theatre.loadImage()).getScaledInstance(width,height, Image.SCALE_SMOOTH)));
        } catch (IOException | NullPointerException e){
            System.out.println("Can't load file: "+e.getClass()+"["+e.getMessage()+"]");
            showEmptyImage(element, width, height);
        }
    }

    private void showEmptyImage(JLabel element, int width, int height){
        try {
            element.setIcon(new ImageIcon(ImageIO.read(Objects.requireNonNull(MainView.class.getClassLoader().getResourceAsStream("exceptions/404.jpg"))).getScaledInstance(width, height, Image.SCALE_SMOOTH)));
        }catch (Exception ignored){}
    }
}
