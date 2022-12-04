package gui;

import controller.Sound;
import database.Arquivos;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.io.IOException;
import java.util.ArrayList;

public interface Boat {
    Arquivos arq = new Arquivos("posicao.txt");
    int[] vetor = new int[]{1, 2, 12, 13, 14, 15, 16, 9, 19, 60, 61,
            62, 73, 83, 93, 69, 79, 89, 99, 46, 56, 66};
    default void createBoat(JPanel battleBoard, ArrayList<ImageIcon> boat, ArrayList<JLabel> pieces){
        boat.add(new ImageIcon("src/resources/patrolboat1.png"));
        boat.add(new ImageIcon("src/resources/patrolboat2.png"));
        boat.add(new ImageIcon("src/resources/carrier1.png"));
        boat.add(new ImageIcon("src/resources/carrier2.png"));
        boat.add(new ImageIcon("src/resources/carrier3.png"));
        boat.add(new ImageIcon("src/resources/carrier4.png"));
        boat.add(new ImageIcon("src/resources/carrier5.png"));
        boat.add(new ImageIcon("src/resources/patrolboatvertical1.png"));
        boat.add(new ImageIcon("src/resources/patrolboatvertical2.png"));
        boat.add(new ImageIcon("src/resources/destroyerhorizontal1.png"));
        boat.add(new ImageIcon("src/resources/destroyerhorizontal2.png"));
        boat.add(new ImageIcon("src/resources/destroyerhorizontal3.png"));
        boat.add(new ImageIcon("src/resources/destroyer1.png"));
        boat.add(new ImageIcon("src/resources/destroyer2.png"));
        boat.add(new ImageIcon("src/resources/destroyer3.png"));
        boat.add(new ImageIcon("src/resources/battleship1.png"));
        boat.add(new ImageIcon("src/resources/battleship2.png"));
        boat.add(new ImageIcon("src/resources/battleship3.png"));
        boat.add(new ImageIcon("src/resources/battleship4.png"));
        boat.add(new ImageIcon("src/resources/destroyer1.png"));
        boat.add(new ImageIcon("src/resources/destroyer2.png"));
        boat.add(new ImageIcon("src/resources/destroyer3.png"));

        for (int i = 0; i < 22; i++) {
            pieces.add(new JLabel(boat.get(i)));
            JPanel panel = (JPanel) battleBoard.getComponent(vetor[i]);
            panel.add(pieces.get(i));
            arq.escrever(battleBoard.getComponent(vetor[i]).getX(), battleBoard.getComponent(vetor[i]).getY());
        }
    }

    default void hideBoat(JPanel battleBoard){
        for (int i = 0; i < 100; i++) {
            JPanel panel = (JPanel) battleBoard.getComponent(i);
            try{
                panel.getComponent(0).setVisible(false);
            }catch (ArrayIndexOutOfBoundsException ignored){}
        }
    }

    default void showBoat(JPanel battleBoard){
        for (int i = 0; i < 100; i++) {
            JPanel panel = (JPanel) battleBoard.getComponent(i);
            try{
                panel.getComponent(0).setVisible(true);
            }catch (ArrayIndexOutOfBoundsException ignored){}
        }
        arq.ler();
    }

    default boolean hideBoatAt(JPanel battleBoard, int x, int y){
        ImageIcon explosion = new ImageIcon("src/resources/explosion1.png");
        JLabel piece = new JLabel(explosion);

        Sound audio;
        Sound.filePath = "src/resources/explosion.wav";

        try{
            audio = new Sound();
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            throw new RuntimeException(e);
        }

        try{
            audio.restart();
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            throw new RuntimeException(e);
        }

        try{
            JPanel panel = (JPanel) battleBoard.getComponentAt(x, y);
            panel.remove(panel.getComponent(0));
            panel.add(piece);
            audio.play();
            return true;
        }catch (ArrayIndexOutOfBoundsException e){
            try{
                audio.stop();
            } catch (UnsupportedAudioFileException | LineUnavailableException | IOException ex) {
                throw new RuntimeException(ex);
            }
            return false;
        }
    }

}
