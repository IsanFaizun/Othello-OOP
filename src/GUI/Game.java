package GUI;

import GUI.*;
import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Game extends JFrame {

    private GamePanel gamePanel;
    public Game(){
        setTitle("Othello");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        GamePanel panel = new GamePanel();
        getContentPane().add(panel);

        pack();
        setVisible(true);
    }
}
