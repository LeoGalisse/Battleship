package gui;

import controller.Attack;

import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.BorderFactory;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.Point;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Container;
import java.util.ArrayList;

public class Enemy extends JLayeredPane implements MouseListener, MouseMotionListener, Refresh, Boat {
    private final JPanel battleBoard;
    private JLabel shipPiece1;
    private JLabel shipPiece2;
    private JLabel shipPiece3;
    private JLabel shipPiece4;
    private JLabel shipPiece5;
    private int xAdjustment;
    private int contEsquerda = 0;
    private int contDireita = 0;
    private int contCima = 0;
    private int contBaixo = 0;
    private int yAdjustment;
    private final JPanel row = new JPanel();
    private final JPanel column = new JPanel();
    private boolean status;
    ArrayList<ImageIcon> boat = new ArrayList<>();
    ArrayList<JLabel> pieces = new ArrayList<>();

    public void hideBoat() {
        hideBoat(battleBoard);
    }

    public void showBoat() {
        showBoat(battleBoard);
    }

    public boolean hideBoatAt(int x, int y) {
        return hideBoatAt(battleBoard, x, y);
    }

    public void setMouseListener(boolean status) {
        this.status = status;
    }

    public void setRefreshBounds(int widthDimension, int heightDimension) {
        if (widthDimension == 300) {
            battleBoard.setBounds(690, 30, widthDimension, heightDimension);
            row.setBounds(660, 0, 30 + widthDimension, 30);
            column.setBounds(660, 30, 30, 30 + heightDimension);
        } else {
            battleBoard.setBounds(720, 55, widthDimension, heightDimension);
            row.setBounds(660, 0, 60 + widthDimension, 55);
            column.setBounds(660, 55, 60, 60 + heightDimension);
        }
    }

    public Enemy() {
        Dimension boardSize = new Dimension(600, 600);
        Dimension rowSize = new Dimension(60, 55);
        Dimension columnSize = new Dimension(60, 60);

        row.setLayout(new GridLayout(1, 11));
        row.setBounds(660, 0, rowSize.width + boardSize.width, rowSize.height);
        add(row, BorderLayout.NORTH, JLayeredPane.DEFAULT_LAYER);

        JLabel indice;
        JPanel square;

        for (int j = 0; j < 11; j++) {
            square = new JPanel(new BorderLayout());
            square.setBorder(BorderFactory.createEtchedBorder());
            if (j != 0) {
                indice = new JLabel(String.valueOf(j));
                square.add(indice, BorderLayout.CENTER);
                square.setBackground(Color.WHITE);
            }

            row.add(square);
        }


        column.setLayout(new GridLayout(11, 1));
        column.setBounds(660, 55, columnSize.width, columnSize.height + boardSize.height);
        add(column, BorderLayout.WEST, JLayeredPane.DEFAULT_LAYER);

        for (int i = 0; i < 11; i++) {
            square = new JPanel(new BorderLayout());
            square.setBorder(BorderFactory.createEtchedBorder());
            if (i != 0) {
                String[] indiceLetras = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", " "};
                indice = new JLabel(indiceLetras[i - 1]);
                square.add(indice, BorderLayout.CENTER);
                square.setBackground(Color.WHITE);
                column.add(square);
            }
        }

        battleBoard = new JPanel();
        battleBoard.setLayout(new GridLayout(10, 10));
        battleBoard.setPreferredSize(boardSize);
        battleBoard.setBounds(720, 55, boardSize.width, boardSize.height);
        battleBoard.addMouseListener(this);
        battleBoard.addMouseMotionListener(this);

        add(battleBoard, JLayeredPane.DEFAULT_LAYER);

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                square = new JPanel(new BorderLayout());
                square.setBorder(BorderFactory.createEtchedBorder());
                square.setBackground(Color.BLUE);

                battleBoard.add(square);
            }
        }

        createBoat(battleBoard, boat, pieces);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (status) return;

        Object source = e.getSource();

        Attack attack = new Attack() {
            @Override
            public void setPositionOfAttack(int x, int y) {
                super.setPositionOfAttack(x, y);
            }

            @Override
            public int getPositionX() {
                return super.getPositionX();
            }

            @Override
            public int getPositionY() {
                return super.getPositionY();
            }
        };

        if (source instanceof JPanel panelPressed) {
            if (panelPressed.getComponentAt(e.getX(), e.getY()).getBackground() == Color.BLACK) {
                attack.setMarked(false);
                panelPressed.getComponentAt(e.getX(), e.getY()).setBackground(Color.BLUE);
                attack.setPositionOfAttack(0, 0);
            } else if (panelPressed.getComponentAt(e.getX(), e.getY()).getBackground() == Color.BLUE) {
                if (attack.getMarked() &&
                        (panelPressed.getComponentAt(attack.getPositionX(), attack.getPositionY()).getBackground() != Color.RED &&
                                panelPressed.getComponentAt(attack.getPositionX(), attack.getPositionY()).getBackground() != Color.WHITE))
                    panelPressed.getComponentAt(attack.getPositionX(), attack.getPositionY()).setBackground(Color.BLUE);
                attack.setMarked(true);
                attack.setPositionOfAttack(e.getX(), e.getY());
                panelPressed.getComponentAt(e.getX(), e.getY()).setBackground(Color.BLACK);
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (!status) return;

        shipPiece1 = null;
        shipPiece2 = null;
        shipPiece3 = null;
        shipPiece4 = null;
        shipPiece5 = null;

        contDireita = 0;
        contEsquerda = 0;
        contCima = 0;
        contBaixo = 0;

        Component component = battleBoard.findComponentAt(e.getX(), e.getY());

        if (component instanceof JPanel) return;

        Point parentsLocation = component.getParent().getLocation();

        xAdjustment = parentsLocation.x - e.getX();
        yAdjustment = parentsLocation.y - e.getY();

        shipPiece1 = (JLabel) component;
        shipPiece1.setLocation(e.getX() + xAdjustment + 720, e.getY() + yAdjustment + 55);

        for (int i = 60; i <= 240; i = i + 60) {
            if (battleBoard.findComponentAt(e.getX() + i, e.getY()) instanceof JLabel) {
                if (shipPiece2 == null) {
                    shipPiece2 = (JLabel) battleBoard.findComponentAt(e.getX() + i, e.getY());
                    shipPiece2.setLocation(e.getX() + xAdjustment + 720 + i, e.getY() + yAdjustment + 55);
                } else if (shipPiece3 == null) {
                    shipPiece3 = (JLabel) battleBoard.findComponentAt(e.getX() + i, e.getY());
                    shipPiece3.setLocation(e.getX() + xAdjustment + 720 + i, e.getY() + yAdjustment + 55);
                } else if (shipPiece4 == null) {
                    shipPiece4 = (JLabel) battleBoard.findComponentAt(e.getX() + i, e.getY());
                    shipPiece4.setLocation(e.getX() + xAdjustment + 720 - i, e.getY() + yAdjustment + 55);
                } else if (shipPiece5 == null) {
                    shipPiece5 = (JLabel) battleBoard.findComponentAt(e.getX() + i, e.getY());
                    shipPiece5.setLocation(e.getX() + xAdjustment + 720 - i, e.getY() + yAdjustment + 55);
                }
                contDireita++;
            } else break;
        }

        for (int i = 60; i <= 240; i = i + 60) {
            if (contDireita == 4) break;
            if (battleBoard.findComponentAt(e.getX() - i, e.getY()) instanceof JLabel) {
                if (shipPiece2 == null) {
                    shipPiece2 = (JLabel) battleBoard.findComponentAt(e.getX() - i, e.getY());
                    shipPiece2.setLocation(e.getX() + xAdjustment + 720 - i, e.getY() + yAdjustment + 55);
                } else if (shipPiece3 == null) {
                    shipPiece3 = (JLabel) battleBoard.findComponentAt(e.getX() - i, e.getY());
                    shipPiece3.setLocation(e.getX() + xAdjustment + 720 - i, e.getY() + yAdjustment + 55);
                } else if (shipPiece4 == null) {
                    shipPiece4 = (JLabel) battleBoard.findComponentAt(e.getX() - i, e.getY());
                    shipPiece4.setLocation(e.getX() + xAdjustment + 720 - i, e.getY() + yAdjustment + 55);
                } else if (shipPiece5 == null) {
                    shipPiece5 = (JLabel) battleBoard.findComponentAt(e.getX() - i, e.getY());
                    shipPiece5.setLocation(e.getX() + xAdjustment + 720 - i, e.getY() + yAdjustment + 55);
                }
                contEsquerda++;
            } else break;
        }

        if (contEsquerda + contDireita == 0) {
            for (int i = 60; i <= 240; i = i + 60) {
                if (battleBoard.findComponentAt(e.getX(), e.getY() + i) instanceof JLabel) {
                    if (shipPiece2 == null) {
                        shipPiece2 = (JLabel) battleBoard.findComponentAt(e.getX(), e.getY() + i);
                        shipPiece2.setLocation(e.getX() + xAdjustment + 720, e.getY() + yAdjustment + 55 + i);
                    } else if (shipPiece3 == null) {
                        shipPiece3 = (JLabel) battleBoard.findComponentAt(e.getX(), e.getY() + i);
                        shipPiece3.setLocation(e.getX() + xAdjustment + 720, e.getY() + yAdjustment + 55 + i);
                    } else if (shipPiece4 == null) {
                        shipPiece4 = (JLabel) battleBoard.findComponentAt(e.getX(), e.getY() + i);
                        shipPiece4.setLocation(e.getX() + xAdjustment + 720, e.getY() + yAdjustment + 55 + i);
                    } else if (shipPiece5 == null) {
                        shipPiece5 = (JLabel) battleBoard.findComponentAt(e.getX(), e.getY() + i);
                        shipPiece5.setLocation(e.getX() + xAdjustment + 720, e.getY() + yAdjustment + 55 + i);
                    }
                    contCima++;
                } else break;
            }

            for (int i = 60; i <= 240; i = i + 60) {
                if (contCima == 4) break;
                if (battleBoard.findComponentAt(e.getX(), e.getY() - i) instanceof JLabel) {
                    if (shipPiece2 == null) {
                        shipPiece2 = (JLabel) battleBoard.findComponentAt(e.getX(), e.getY() - i);
                        shipPiece2.setLocation(e.getX() + xAdjustment + 720, e.getY() + yAdjustment + 55 - i);
                    } else if (shipPiece3 == null) {
                        shipPiece3 = (JLabel) battleBoard.findComponentAt(e.getX(), e.getY() - i);
                        shipPiece3.setLocation(e.getX() + xAdjustment + 720, e.getY() + yAdjustment + 55 - i);
                    } else if (shipPiece4 == null) {
                        shipPiece4 = (JLabel) battleBoard.findComponentAt(e.getX(), e.getY() - i);
                        shipPiece4.setLocation(e.getX() + xAdjustment + 720, e.getY() + yAdjustment + 55 - i);
                    } else if (shipPiece5 == null) {
                        shipPiece5 = (JLabel) battleBoard.findComponentAt(e.getX(), e.getY() - i);
                        shipPiece5.setLocation(e.getX() + xAdjustment + 720, e.getY() + yAdjustment + 55 - i);
                    }
                    contBaixo++;
                } else break;
            }
        }

        add(shipPiece1, JLayeredPane.DRAG_LAYER);
        if (shipPiece2 != null) add(shipPiece2, JLayeredPane.DRAG_LAYER);
        if (shipPiece3 != null) add(shipPiece3, JLayeredPane.DRAG_LAYER);
        if (shipPiece4 != null) add(shipPiece4, JLayeredPane.DRAG_LAYER);
        if (shipPiece5 != null) add(shipPiece5, JLayeredPane.DRAG_LAYER);

        setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (!status) return;

        setCursor(null);

        if (shipPiece1 == null) return;

        shipPiece1.setVisible(false);
        remove(shipPiece1);
        shipPiece1.setVisible(true);

        if (shipPiece2 != null) {
            shipPiece2.setVisible(false);
            remove(shipPiece2);
            shipPiece2.setVisible(true);
        }
        if (shipPiece3 != null) {
            shipPiece3.setVisible(false);
            remove(shipPiece3);
            shipPiece3.setVisible(true);
        }
        if (shipPiece4 != null) {
            shipPiece4.setVisible(false);
            remove(shipPiece4);
            shipPiece4.setVisible(true);
        }
        if (shipPiece5 != null) {
            shipPiece5.setVisible(false);
            remove(shipPiece5);
            shipPiece5.setVisible(true);
        }

        //  The drop location should be within the bounds of the chess board

        int xMax = battleBoard.getWidth() - shipPiece1.getWidth();
        int x = Math.min(e.getX(), xMax);
        x = Math.max(x, 0);

        int yMax = battleBoard.getHeight() - shipPiece1.getHeight();
        int y = Math.min(e.getY(), yMax);
        y = Math.max(y, 0);

        Component c1 = battleBoard.findComponentAt(x, y);
        Component c2 = null;
        Component c3 = null;
        Component c4 = null;
        Component c5 = null;

        for (int i = 1, j = 60; i <= contDireita; i++, j = j + 60) {
            if (i == 1) c2 = battleBoard.findComponentAt(x + j, y);
            if (i == 2) c3 = battleBoard.findComponentAt(x + j, y);
            if (i == 3) c4 = battleBoard.findComponentAt(x + j, y);
            if (i == 4) c5 = battleBoard.findComponentAt(x + j, y);
        }

        for (int i = contDireita + 1, j = 60; i <= contDireita + contEsquerda; i++, j = j + 60) {
            if (i == 1) c2 = battleBoard.findComponentAt(x - j, y);
            if (i == 2) c3 = battleBoard.findComponentAt(x - j, y);
            if (i == 3) c4 = battleBoard.findComponentAt(x - j, y);
            if (i == 4) c5 = battleBoard.findComponentAt(x - j, y);
        }

        for (int i = 1, j = 60; i <= contCima; i++, j = j + 60) {
            if (i == 1) c2 = battleBoard.findComponentAt(x, y + j);
            if (i == 2) c3 = battleBoard.findComponentAt(x, y + j);
            if (i == 3) c4 = battleBoard.findComponentAt(x, y + j);
            if (i == 4) c5 = battleBoard.findComponentAt(x, y + j);
        }

        for (int i = contCima + 1, j = 60; i <= contCima + contBaixo; i++, j = j + 60) {
            if (i == 1) c2 = battleBoard.findComponentAt(x, y - j);
            if (i == 2) c3 = battleBoard.findComponentAt(x, y - j);
            if (i == 3) c4 = battleBoard.findComponentAt(x, y - j);
            if (i == 4) c5 = battleBoard.findComponentAt(x, y - j);
        }

        if (c1 instanceof JLabel) {
            createBoat(battleBoard, boat, pieces);
            battleBoard.repaint();
            JOptionPane.showConfirmDialog(this, "Não é possível sobrepor outro barco. Seu layout foi resetado!"
                    , "Atenção!!", JOptionPane.DEFAULT_OPTION);
        } else if (c2 instanceof JLabel) {
            createBoat(battleBoard, boat, pieces);
            battleBoard.repaint();
            JOptionPane.showConfirmDialog(this, "Não é possível sobrepor outro barco. Seu layout foi resetado!"
                    , "Atenção!!", JOptionPane.DEFAULT_OPTION);
        } else if (c3 instanceof JLabel) {
            createBoat(battleBoard, boat, pieces);
            battleBoard.repaint();
            JOptionPane.showConfirmDialog(this, "Não é possível sobrepor outro barco. Seu layout foi resetado!"
                    , "Atenção!!", JOptionPane.DEFAULT_OPTION);
        } else if (c4 instanceof JLabel) {
            createBoat(battleBoard, boat, pieces);
            battleBoard.repaint();
            JOptionPane.showConfirmDialog(this, "Não é possível sobrepor outro barco. Seu layout foi resetado!"
                    , "Atenção!!", JOptionPane.DEFAULT_OPTION);
        } else if (c5 instanceof JLabel) {
            createBoat(battleBoard, boat, pieces);
            battleBoard.repaint();
            JOptionPane.showConfirmDialog(this, "Não é possível sobrepor outro barco. Seu layout foi resetado!"
                    , "Atenção!!", JOptionPane.DEFAULT_OPTION);
        } else {
            Container parent1 = null;
            if (c1 != null) parent1 = (Container) c1;
            Container parent2 = null;
            if (c2 != null) parent2 = (Container) c2;
            Container parent3 = null;
            if (c3 != null) parent3 = (Container) c3;
            Container parent4 = null;
            if (c4 != null) parent4 = (Container) c4;
            Container parent5 = null;
            if (c5 != null) parent5 = (Container) c5;

            if (parent1 != null) parent1.add(shipPiece1);
            if (parent2 != null) parent2.add(shipPiece2);
            if (parent3 != null) parent3.add(shipPiece3);
            if (parent4 != null) parent4.add(shipPiece4);
            if (parent5 != null) parent5.add(shipPiece5);

            if (parent1 != null) parent1.revalidate();
            if (parent2 != null) parent2.revalidate();
            if (parent3 != null) parent3.revalidate();
            if (parent4 != null) parent4.revalidate();
            if (parent5 != null) parent5.revalidate();
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (!status) return;

        if (shipPiece1 == null) return;

        //  The drag location should be within the bounds of the chess board
        int x = e.getX() + xAdjustment + 720;
        int xMax = battleBoard.getWidth() - shipPiece1.getWidth() + 720;
        x = Math.min(x, xMax - 60 * contDireita);
        x = Math.max(x, 660 + 60 * (contEsquerda + 1));

        int y = e.getY() + yAdjustment + 55;
        int yMax = battleBoard.getHeight() - shipPiece1.getHeight() + 55;
        y = Math.min(y, yMax - 55 * (contCima));
        y = Math.max(y, 55 * (contBaixo + 1));

        shipPiece1.setLocation(x, y);

        for (int i = 1, j = 60; i <= contDireita; i++, j = j + 60) {
            if (i == 1 && shipPiece2 != null) {
                x = e.getX() + xAdjustment + 720;
                xMax = battleBoard.getWidth() - shipPiece2.getWidth() + 720;
                x = Math.min(x, xMax - 60 * (contDireita));
                x = Math.max(x, 660 + 60 * (contEsquerda + 1));

                y = e.getY() + yAdjustment + 55;
                yMax = battleBoard.getHeight() - shipPiece2.getHeight() + 55;
                y = Math.min(y, yMax);
                y = Math.max(y, 55);

                shipPiece2.setLocation(x + j, y);
            }
            if (i == 2 && shipPiece3 != null) shipPiece3.setLocation(x + j, y);
            if (i == 3 && shipPiece4 != null) shipPiece4.setLocation(x + j, y);
            if (i == 4 && shipPiece5 != null) shipPiece5.setLocation(x + j, y);
        }

        for (int i = contDireita + 1, j = 60; i <= contDireita + contEsquerda; i++, j = j + 60) {
            if (i == 1 && shipPiece2 != null) {
                x = e.getX() + xAdjustment + 720;
                xMax = battleBoard.getWidth() - shipPiece2.getWidth() + 720;
                x = Math.min(x, xMax);
                x = Math.max(x, 660 + 60 * (contEsquerda + 1));

                y = e.getY() + yAdjustment + 55;
                yMax = battleBoard.getHeight() - shipPiece2.getHeight() + 55;
                y = Math.min(y, yMax);
                y = Math.max(y, 55);

                shipPiece2.setLocation(x - j, y);
            }
            if (i == 2 && shipPiece3 != null) shipPiece3.setLocation(x - j, y);
            if (i == 3 && shipPiece4 != null) shipPiece4.setLocation(x - j, y);
            if (i == 4 && shipPiece5 != null) shipPiece5.setLocation(x - j, y);
        }

        for (int i = 1, j = 60; i <= contCima; i++, j = j + 60) {
            if (i == 1 && shipPiece2 != null) {
                x = e.getX() + xAdjustment + 720;
                xMax = battleBoard.getWidth() - shipPiece2.getWidth() + 720;
                x = Math.min(x, xMax - 60 * (contDireita));
                x = Math.max(x, 660 + 60 * (contEsquerda + 1));

                y = e.getY() + yAdjustment + 55;
                yMax = battleBoard.getHeight() - shipPiece2.getHeight() + 55;
                y = Math.min(y, yMax - 55 * (contCima));
                y = Math.max(y, 55 * (contBaixo + 1));

                shipPiece2.setLocation(x, y + j);
            }
            if (i == 2 && shipPiece3 != null) shipPiece3.setLocation(x, y + j);
            if (i == 3 && shipPiece4 != null) shipPiece4.setLocation(x, y + j);
            if (i == 4 && shipPiece5 != null) shipPiece5.setLocation(x, y + j);
        }

        for (int i = contCima + 1, j = 60; i <= contCima + contBaixo; i++, j = j + 60) {
            if (i == 1 && shipPiece2 != null) {
                x = e.getX() + xAdjustment + 720;
                xMax = battleBoard.getWidth() - shipPiece2.getWidth() + 720;
                x = Math.min(x, xMax);
                x = Math.max(x, 660 + 60 * (contEsquerda + 1));

                y = e.getY() + yAdjustment + 55;
                yMax = battleBoard.getHeight() - shipPiece2.getHeight() + 55;
                y = Math.min(y, yMax - 55 * (contCima));
                y = Math.max(y, 55 * (contBaixo + 1));

                shipPiece2.setLocation(x, y - j);
            }
            if (i == 2 && shipPiece3 != null) shipPiece3.setLocation(x, y - j);
            if (i == 3 && shipPiece4 != null) shipPiece4.setLocation(x, y - j);
            if (i == 4 && shipPiece5 != null) shipPiece5.setLocation(x, y - j);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}

