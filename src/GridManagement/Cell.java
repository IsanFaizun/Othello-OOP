package GridManagement;

import GUI.*;
import java.awt.*;

public class Cell extends Board {
    private int disc;
    private boolean highlight;

    public Cell(Position post, int width, int height){
        super(post, width, height);
        reset();
    }

    public void reset(){
        disc = 0;
        highlight = false;
    }

    public int getDisc() {
        return disc;
    }

    public void setDisc(int disc) {
        this.disc = disc;
    }

    public void setHighlight(boolean highlight) {
        this.highlight = highlight;
    }

    public void mark(Graphics g) {
        if(highlight) {
            g.setColor(new Color(255,0,0,100));
            g.fillRect(post.x, post.y, width, height);
        }

        if(disc == 0) return;
        g.setColor(disc == 1 ? Color.BLACK : Color.WHITE);
        g.fillOval(post.x, post.y, width, height);
    }
}