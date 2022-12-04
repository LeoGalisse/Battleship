package gui;

import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Dimension;
import java.awt.Font;

public class Menu extends JPanel {
    static boolean status;
    JPanel menuPanel;
    public Menu(JFrame frame){
        status = false;

        menuPanel = new JPanel();

        ImageIcon back = new ImageIcon("src/resources/background.png");
        JLabel image = new JLabel(back);
        Dimension imageSize = image.getPreferredSize();

        JButton play = new JButton("PLAY");
        Dimension playSize = play.getPreferredSize();

        frame.setPreferredSize(new Dimension(imageSize.width, imageSize.height));
        frame.setResizable(false);

        image.add(play);

        play.setBounds(800 - (playSize.width / 2) - 15, 700, 30 + playSize.width, playSize.height);
        play.setFont(new Font("Arial", Font.BOLD, 20));
        play.setVisible(true);

        menuPanel.add(image);

        frame.add(menuPanel);
        frame.pack();

        frame.setVisible(true);
        frame.getComponent(0).setVisible(true);

        play.addActionListener(e -> {
            status = true;
            play.setVisible(false);
            menuPanel.setVisible(false);
            new CreateUI();
        });
    }
}
