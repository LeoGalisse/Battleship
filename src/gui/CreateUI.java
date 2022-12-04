package gui;

import controller.Attack;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Color;
import java.util.ArrayList;

public class CreateUI extends JFrame {
    private int contPlayer1 = 0;
    private int contPlayer2 = 0;
    ArrayList<Component> player1 = new ArrayList<>();
    ArrayList<Component> player2 = new ArrayList<>();
    boolean aGetVisible;
    boolean bGetVisible;
    boolean decisaoBarco;
    private static JFrame frame = null;

    public CreateUI() {
        Attack attack = new Attack() {
            @Override
            public int getPositionX() {
                return super.getPositionX();
            }

            @Override
            public int getPositionY() {
                return super.getPositionY();
            }

            @Override
            public boolean getMarked() {
                return super.getMarked();
            }
        };

        Enemy a = new Enemy();
        BattleBoard b = new BattleBoard();

        if (frame == null)
            frame = new JFrame("Battleship");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        if (!Menu.status) {
            new Menu(frame);
        }

        if (Menu.status) {
            frame.setResizable(true);
            frame.repaint();
            frame.setPreferredSize(new Dimension(1335, 693));
            frame.setMinimumSize(new Dimension(1335, 693));
            frame.add(b);
            frame.pack();
            frame.add(a);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

            a.setVisible(true);
            aGetVisible = true;
            b.setVisible(true);
            bGetVisible = false;

            b.setRefreshBounds(300, 300);
            b.setMouseListener(false);
            b.hideBoat();
            a.setRefreshBounds(600, 600);
            a.showBoat();
            a.setMouseListener(true);

            JButton btn1 = new JButton("Attack");
            JButton finalizar = new JButton("Finalizar layout");
            JButton decisao = new JButton("Mostrar barcos");

            JPanel panel = new JPanel();
            panel.setLayout(null);

            panel.add(btn1);
            panel.add(finalizar);
            panel.add(decisao);

            Insets insets = panel.getInsets();

            Dimension size = btn1.getPreferredSize();
            btn1.setBounds(480 + insets.left, 330 + insets.top, size.width, size.height);
            btn1.setVisible(false);

            Dimension sizeFinalizar = finalizar.getPreferredSize();
            finalizar.setBounds(430 + insets.left, 330 + insets.top,
                    sizeFinalizar.width, sizeFinalizar.height);

            Dimension sizeDecisao = decisao.getPreferredSize();
            decisao.setBounds(430 + insets.left - size.width, 330 + insets.top, sizeDecisao.width, sizeDecisao.height);
            decisao.setVisible(false);

            frame.add(panel);
            frame.pack();

            finalizar.addActionListener(e -> {
                if (!bGetVisible) {
                    a.hideBoat();
                    int action = JOptionPane.showConfirmDialog(CreateUI.this, "Você terminou de organizar seu jogo?"
                            , "Finalizar layout", JOptionPane.OK_CANCEL_OPTION);
                    if (action == JOptionPane.OK_OPTION) {

                        a.setRefreshBounds(300, 300);
                        a.setMouseListener(false);
                        a.hideBoat();

                        b.setRefreshBounds(600, 600);
                        b.setMouseListener(true);
                        b.showBoat();

                        bGetVisible = true;
                        aGetVisible = false;

                        finalizar.setBounds(760 + insets.left, 330 + insets.top, sizeFinalizar.width, sizeFinalizar.height);

                        frame.repaint();
                    } else a.showBoat();
                } else {
                    b.hideBoat();
                    int action = JOptionPane.showConfirmDialog(CreateUI.this, "Você terminou de organizar seu jogo?"
                            , "Finalizar layout", JOptionPane.OK_CANCEL_OPTION);
                    if (action == JOptionPane.OK_OPTION) {
                        finalizar.setVisible(false);
                        btn1.setVisible(true);
                        decisao.setVisible(true);

                        bGetVisible = false;
                        aGetVisible = true;

                        b.setRefreshBounds(300, 300);
                        b.setMouseListener(false);
                        b.hideBoat();

                        a.setRefreshBounds(600, 600);
                        a.showBoat();
                        a.hideBoat();

                        frame.repaint();
                    } else b.showBoat();
                }
            });

            btn1.addActionListener(e -> {
                if (!aGetVisible) {
                    b.hideBoat();
                    int action = JOptionPane.showConfirmDialog(CreateUI.this, "Você finalizou seu ataque?"
                            , "Finalizar ataque", JOptionPane.OK_CANCEL_OPTION);
                    if (action == JOptionPane.OK_OPTION) {
                        aGetVisible = true;
                        bGetVisible = false;

                        for (Component component : player2) {
                            component.setBackground(Color.BLUE);
                        }
                        for (Component component : player1) {
                            component.setBackground(Color.WHITE);
                        }

                        b.setRefreshBounds(300, 300);
                        b.setMouseListener(false);
                        b.hideBoat();

                        a.setRefreshBounds(600, 600);
                        a.showBoat();
                        a.hideBoat();

                        btn1.setBounds(480 + insets.left, 330 + insets.top, size.width, size.height);
                        decisao.setBounds(430 + insets.left - size.width, 330 + insets.top, sizeDecisao.width, sizeDecisao.height);

                        Component[] aux1 = a.getComponents();
                        for (Component component : aux1) {
                            if (component.getComponentAt(attack.getPositionX(), attack.getPositionY()) != null) {
                                if (component.getComponentAt(attack.getPositionX(), attack.getPositionY()).getBackground() == Color.BLACK) {
                                    component.getComponentAt(attack.getPositionX(), attack.getPositionY()).setBackground(Color.BLUE);
                                    if (a.hideBoatAt(attack.getPositionX(), attack.getPositionY())){
                                        contPlayer2++;
                                        component.getComponentAt(attack.getPositionX(), attack.getPositionY()).setBackground(Color.RED);
                                    }
                                    else {
                                        component.getComponentAt(attack.getPositionX(), attack.getPositionY()).setBackground(Color.WHITE);
                                        player2.add(component.getComponentAt(attack.getPositionX(), attack.getPositionY()));
                                    }
                                    break;
                                }
                            }
                        }

                        frame.repaint();
                    } else {
                        aGetVisible = false;
                        bGetVisible = true;
                    }
                } else if (!bGetVisible) {
                    a.hideBoat();
                    int action = JOptionPane.showConfirmDialog(CreateUI.this, "Você finalizou seu ataque?"
                            , "Finalizar ataque", JOptionPane.OK_CANCEL_OPTION);
                    if (action == JOptionPane.OK_OPTION) {
                        for (Component item : player2) {
                            item.setBackground(Color.WHITE);
                        }
                        for (Component value : player1) {
                            value.setBackground(Color.BLUE);
                        }

                        a.setRefreshBounds(300, 300);
                        a.setMouseListener(false);
                        a.hideBoat();

                        b.setRefreshBounds(600, 600);
                        b.showBoat();
                        b.hideBoat();

                        bGetVisible = true;
                        aGetVisible = false;

                        btn1.setBounds(810 + insets.left, 330 + insets.top, size.width, size.height);
                        decisao.setBounds(760 + insets.left - size.width, 330 + insets.top, sizeDecisao.width, sizeDecisao.height);

                        Component[] aux2 = b.getComponents();
                        for (Component component : aux2) {
                            if (component.getComponentAt(attack.getPositionX(), attack.getPositionY()) != null) {
                                if (component.getComponentAt(attack.getPositionX(), attack.getPositionY()).getBackground() == Color.BLACK) {
                                    component.getComponentAt(attack.getPositionX(), attack.getPositionY()).setBackground(Color.BLUE);
                                    if (b.hideBoatAt(attack.getPositionX(), attack.getPositionY())){
                                        contPlayer1++;
                                        component.getComponentAt(attack.getPositionX(), attack.getPositionY()).setBackground(Color.RED);
                                    }
                                    else {
                                        component.getComponentAt(attack.getPositionX(), attack.getPositionY()).setBackground(Color.WHITE);
                                        player1.add(component.getComponentAt(attack.getPositionX(), attack.getPositionY()));
                                    }
                                    break;
                                }
                            }
                        }

                        frame.repaint();
                    } else {
                        bGetVisible = false;
                        aGetVisible = true;
                    }
                }

                if(contPlayer1 == 22){
                    JOptionPane.showConfirmDialog(CreateUI.this, "Player1 venceu", "Vencedor", JOptionPane.DEFAULT_OPTION);
                }else if(contPlayer2 == 22){
                    JOptionPane.showConfirmDialog(CreateUI.this, "Player2 venceu", "Vencedor", JOptionPane.DEFAULT_OPTION);
                }
            });

            decisao.addActionListener(e -> {
                if (decisaoBarco) {
                    decisao.setText("Mostrar barcos");
                    if (!bGetVisible) a.hideBoat();
                    if (!aGetVisible) b.hideBoat();
                    decisaoBarco = false;
                } else {
                    decisaoBarco = true;
                    decisao.setText("Esconder");
                    if (!bGetVisible) a.showBoat();
                    if (!aGetVisible) b.showBoat();
                }
            });

        }
    }
}
