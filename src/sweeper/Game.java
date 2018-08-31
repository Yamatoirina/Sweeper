package sweeper;

import javax.sound.sampled.*;
import java.io.IOException;
import java.lang.ref.PhantomReference;
import java.net.URL;

public class Game {
    private Bomb bomb;
    private Flag flag;



    private GameState state;

    public Game(int cols, int rows, int bombs){

        Ranges.setSize(new Coord (cols, rows));
        bomb = new Bomb (bombs);
        flag = new Flag();
    }

    public void start (){

        bomb.start();
        flag.start();
        state = GameState.PLAYED;
        audioStart();



    }
    public Box getBox (Coord coord){

        if (Box.OPENED == flag.get(coord))
        return bomb.get (coord);
        else
            return flag.get(coord);

    }


    public void pressLeftButton(Coord coord){
        audioClick();
        if (isGameOver())
            return;
            openBox(coord);
            checkWinner();

    }


    public void pressRightButton(Coord coord){
        audioFlagged();
        if (isGameOver())
            return;
            flag.toggleFlagedToBox(coord);


    }

    public GameState getState() {
        return state;
    }


    public int getTotalBombs(){
        return bomb.getTotalBombs();
    }


    public int getTotalFlagged(){
        return flag.getTotalFlagged();
    }

    private boolean isGameOver(){
        if (GameState.PLAYED != state){
            start();
            return true;
        }
        return false;
    }

    private void checkWinner (){
        if(GameState.PLAYED == state )
            if (flag.getTotalClosed() == bomb.getTotalBombs()){
                audioWinner();
                state = GameState.WINNER;
                flag.setFlaggedToLastClosedBoxes();
            }
    }



    private void openBox (Coord coord){
        switch (flag.get(coord)){
            case OPENED: setOpenedAroundNumber(coord); break;
            case FLAGED: break;
            case CLOSED:
                switch (bomb.get (coord)){
                    case ZERO: openBoxesAroundZero (coord); break;
                    case BOMB: openBombs (coord); break;
                    default: flag.setOpenedToBox(coord); break;
                }
        }
    }

    private void setOpenedAroundNumber(Coord coord) {
        if(Box.BOMB != bomb.get (coord))
            if (bomb.get(coord).getNumber() == flag.getCountOfFlaggedAround(coord))
                for (Coord around : Ranges.getCoordsAround(coord))
                    if (flag.get (around) == Box.CLOSED)
                        openBox(around);
    }


    private void openBombs (Coord bombedCoord){
        audioBombed();
        flag.setBombedToBox (bombedCoord);

        for (Coord coord : Ranges.getAllCoords())
            if (bomb.get (coord) == Box.BOMB)
                flag.setOpenedToClosedBox(coord);
            else
                flag.setNoBombToFlaggedBox(coord);
        state = GameState.BOMBED;
    }


    private void openBoxesAroundZero (Coord coord){
        flag.setOpenedToBox(coord);
        for(Coord around : Ranges.getCoordsAround(coord))
            openBox(around);
    }



    public void audioStart() {

        try {
            // Open an audio input stream.
            URL url = this.getClass().getClassLoader().getResource("breath.wav");
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
            // Get a sound clip resource.
            Clip clip = AudioSystem.getClip();
            // Open audio clip and load samples from the audio input stream.
            clip.open(audioIn);
            clip.start();
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }



    public void audioClick() {

        try {
            // Open an audio input stream.
            URL url = this.getClass().getClassLoader().getResource("clack.wav");
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
            // Get a sound clip resource.
            Clip clip = AudioSystem.getClip();
            // Open audio clip and load samples from the audio input stream.
            clip.open(audioIn);
            clip.start();
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }



    public void audioFlagged() {

        try {
            // Open an audio input stream.
            URL url = this.getClass().getClassLoader().getResource("click.wav");
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
            // Get a sound clip resource.
            Clip clip = AudioSystem.getClip();
            // Open audio clip and load samples from the audio input stream.
            clip.open(audioIn);
            clip.start();
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }





    public void audioBombed() {

        try {
            // Open an audio input stream.
            URL url = this.getClass().getClassLoader().getResource("cat.wav");
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
            // Get a sound clip resource.
            Clip clip = AudioSystem.getClip();
            // Open audio clip and load samples from the audio input stream.
            clip.open(audioIn);
            clip.start();
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }



    public void audioWinner() {

        try {
            // Open an audio input stream.
            URL url = this.getClass().getClassLoader().getResource("winner.wav");
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
            // Get a sound clip resource.
            Clip clip = AudioSystem.getClip();
            // Open audio clip and load samples from the audio input stream.
            clip.open(audioIn);
            clip.start();
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }


}


