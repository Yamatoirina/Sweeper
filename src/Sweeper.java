
import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;

import sweeper.*;
import sweeper.Box;


public class Sweeper extends JFrame {

    private Game game;
    private final int IMAGE_SIZE = 100;
    private final int COLS = 9;
    private final int ROWS = 9;
    private final int BOMBS = 10;
    private JPanel panel;
    private JLabel label;

    public static void main(String[] args) {
        new Sweeper();





    }

    private Sweeper(){

        game = new Game (COLS, ROWS, BOMBS);
        game.start();
        setImages();
        initPanel();
        initLabel();
        initFrame();



    }

    private void initLabel() {
        label = new JLabel(getMessage());
        Font font = new Font("Times New Roman", Font.BOLD, 25 );
        label.setFont(font);
        add (label, BorderLayout.SOUTH);






    }

    private void initPanel() {
        panel = new JPanel()
        {
            @Override
            public void paintComponent(Graphics g){
                super.paintComponent(g);
                for(Coord coord : Ranges.getAllCoords())
                    g.drawImage((Image) game.getBox(coord).image, coord.x * IMAGE_SIZE,
                            coord.y * IMAGE_SIZE, this);




            }

        };

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int x = e.getX() / IMAGE_SIZE;
                int y = e.getY() / IMAGE_SIZE;
                Coord coord = new Coord(x,y);
                switch (e.getButton()){
                    case MouseEvent.BUTTON1 : game.pressLeftButton(coord);
                    break;
                    case MouseEvent.BUTTON3 : game.pressRightButton(coord);
                    break;
                    case MouseEvent.BUTTON2 : game.start();
                    break;
                }
                label.setText(getMessage());
                panel.repaint();

            }
        });


        panel.setPreferredSize(new Dimension(Ranges.getSize().x * IMAGE_SIZE,
                                            Ranges.getSize().y * IMAGE_SIZE));
        add(panel);
    }

    private void initFrame() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Find the Cat");
        setResizable(false);
        pack();
        setVisible(true);
        setLocationRelativeTo(null);

    }

    private void setImages(){
        for (Box box : Box.values())
            box.image = getImage(box.name().toLowerCase());
        setIconImage(getImage("icon"));
    }

    private Image getImage(String name){

        String filename = "img/" + name + ".png";
        ImageIcon icon = new ImageIcon(getClass().getResource(filename));
        return icon.getImage();
    }

    private String getMessage (){
        switch (game.getState()){

            case BOMBED: return "Ha-Ha-Ha! You Lose!";
            case WINNER: return "Congratulations! All Cats caught!";
            case PLAYED:
                default:
                    if (game.getTotalFlagged() == 0){
                        return "HellCome!";
                    }
                    return "Marked " + game.getTotalFlagged() + " of " + game.getTotalBombs() + "Cats";
        }
    }


//    public void audio() {
//
//        try {
//            // Open an audio input stream.
//            URL url = this.getClass().getClassLoader().getResource("breath.wav");
//            AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
//            // Get a sound clip resource.
//            Clip clip = AudioSystem.getClip();
//            // Open audio clip and load samples from the audio input stream.
//            clip.open(audioIn);
//            clip.start();
//        } catch (UnsupportedAudioFileException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (LineUnavailableException e) {
//            e.printStackTrace();
//        }
//    }

}
