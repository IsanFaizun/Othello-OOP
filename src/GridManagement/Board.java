package GridManagement;

import GUI.*;
public abstract class Board {
    protected Position post;
    protected int width;
    protected int height;

    public Board(Position post, int width, int height){
        this.post = post;
        this.width = width;
        this.height = height;
    }

    public abstract void reset();
}
