package GUI;

import GridManagement.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import VSComAuto.*;
public class GamePanel extends JPanel implements MouseListener{
    public enum GameState {WTurn, BTurn, Draw, WWins, BWins}
    private GameState gameState;
    private String gameStateStr;
    private VSCom vsCom;
    private static final int PANEL_HEIGHT = 600;
    private static final int PANEL_WIDTH = 500;
    private Grid gameArea;
    public GamePanel(){
        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        setBackground(new Color(0x00AB5B));

        gameArea = new Grid(new Position(0,0), PANEL_WIDTH, PANEL_HEIGHT-100, 8, 8);
        setGameState(GameState.BTurn);
        chooseType();
        addMouseListener(this);
    }

    public void paint(Graphics g){
        super.paint(g);
        gameArea.paint(g);
        drawGameState(g);
    }
    public void restart() {
        gameArea.reset();
        setGameState(GameState.BTurn);
    }

    public void handleInput(int keyCode) {
        if(keyCode == KeyEvent.VK_ESCAPE) {
            System.exit(0);
        } else if(keyCode == KeyEvent.VK_R) {
            restart();
            repaint();
        }
    }
    private void playTurn(Position gridPosition) {
        if(!gameArea.isValidMove(gridPosition)) {
            return;
        } else if(gameState == GameState.BTurn) {
            gameArea.playMove(gridPosition, 1);
            setGameState(GameState.WTurn);
        } else if(gameState == GameState.WTurn) {
            gameArea.playMove(gridPosition, 2);
            setGameState(GameState.BTurn);
        }
    }
    private void setGameState(GameState newState) {
        gameState = newState;
        switch (gameState) {
            case WTurn:
                if(gameArea.getValidMoves().size() > 0){
                    gameStateStr = "White's Turn";
                } else {
                    gameArea.updateValidMoves(1);
                    if (gameArea.getValidMoves().size() > 0){
                        setGameState(GameState.BTurn);
                    } else {
                        endGame(false);
                    }
                }
                break;
            case BTurn:
                if (gameArea.getValidMoves().size() > 0){
                    gameStateStr = "Black's Turn";
                } else {
                    gameArea.updateValidMoves(2);
                    if (gameArea.getValidMoves().size() > 0){
                        setGameState(GameState.WTurn);
                    } else {
                        endGame(false);
                    }
                }
                break;
            case WWins: gameStateStr = "White Wins"; break;
            case BWins: gameStateStr = "Black Wins"; break;
            case Draw: gameStateStr = "The Game is Draw"; break;
        }
    }
    private void endGame(boolean isEnd){
        int result = gameArea.getWinner(isEnd);
        if(result == 1){
            setGameState(GameState.BWins);
        } else if (result == 2) {
            setGameState(GameState.WWins);
        } else if (result == 3) {
            setGameState(GameState.Draw);
        }
    }
    public void mousePressed(MouseEvent e) {
        if(gameState == GameState.WTurn || gameState == GameState.BTurn) {
            Position gridPosition = gameArea.mouseToGridPost(new Position(e.getX(), e.getY()));
            playTurn(gridPosition);
            endGame(true);

            while(gameState == GameState.WTurn && vsCom != null) {
                playTurn(vsCom.chooseMove());
                endGame(true);
            }
        }

        repaint();
    }
    private void drawGameState(Graphics g) {
        g.setColor(Color.BLACK);
        g.setFont(new Font("Helvetica", Font.BOLD, 35));
        int strWidth = g.getFontMetrics().stringWidth(gameStateStr);
        g.drawString(gameStateStr, PANEL_WIDTH/2-strWidth/2, PANEL_HEIGHT-40);
    }

    private void chooseType() {
        String[] options = new String[] {"vs Player", "vs COM"};
        String message = "Select the game mode you would like to use.";
        int difficultyChoice = JOptionPane.showOptionDialog(null, message,
                "Choose how to play.",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options, options[0]);
        switch(difficultyChoice) {
            case 0: // Remove the AI so it becomes PvP
                vsCom = null;
                break;
            case 1:
                vsCom = new VSCom(gameArea);
                break;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
