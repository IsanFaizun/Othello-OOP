import javax.swing.*;

public class Game extends JFrame {
    public Game(){
        setTitle("Othello");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);

        GamePanel panel = new GamePanel();
        getContentPane().add(panel);

        setVisible(true);
    }
}
