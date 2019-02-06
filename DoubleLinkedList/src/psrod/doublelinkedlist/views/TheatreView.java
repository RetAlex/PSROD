package psrod.doublelinkedlist.views;

import psrod.doublelinkedlist.entities.Theatre;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class TheatreView extends JPanel {
    TheatreView(int x, int y, int width, int height, Theatre theatre, boolean shortVersion) {
        this.setLayout(null);
        this.setBounds(x, y, width, height);
        int imageEnding = (int) (height*0.8);
        try {

            JLabel image = new JLabel(new ImageIcon(ImageIO.read(theatre.loadImage()).getScaledInstance(width, imageEnding, Image.SCALE_DEFAULT)));
            image.setBounds(0, 0, width, imageEnding);
            image.setVisible(true);
            this.add(image);
        } catch (IOException e){
            System.out.println("Can't load file: "+e.getClass()+"["+e.getMessage()+"]");
            showException(0, 0, width, imageEnding, "Can't load image ("+e.getMessage()+")");
        }
        JLabel description = new JLabel();
        description.setBounds(0, imageEnding+10, width, height-imageEnding);
        description.setText(shortVersion?getSmallDescription(theatre):getDescription(theatre));
        description.setVisible(true);
        this.add(description);
        this.repaint();
    }

    private String getDescription(Theatre theatre){
        return "<html>" +
                getParagraph("Name", theatre.getName()) +
                getParagraph("Address", theatre.getAddress()) +
                "</html>";
    }
    private String getSmallDescription(Theatre theatre){
        return "<html>"
                +getParagraph("Name", theatre.getName())+
                "</html>";
    }

    private String getParagraph(String property, String value){
        return "<p>"+property+": <i>"+value+"</i>" +
                "</p>";
    }

    private void showException(int x, int y, int width, int height, String text){
        try {
            JLabel image = new JLabel(new ImageIcon(ImageIO.read(Objects.requireNonNull(TheatreView.class.getClassLoader().getResourceAsStream("exceptions/404.jpg"))).getScaledInstance(width, height-20, Image.SCALE_DEFAULT)));
            image.setBounds(x, y, width, height-20);
            image.setVisible(true);
            this.add(image);
        } catch (IOException e) {
            e.printStackTrace();
        }
        JLabel textLabel = new JLabel();
        textLabel.setBounds(x, height-15, width, 15);
        textLabel.setText(text);
        textLabel.setVisible(true);
        this.add(textLabel);
    }
}
