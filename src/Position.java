public class Position {
    public int x;
    public int y;
    public static final Position DOWN = new Position(0, 1);
    public static final Position UP = new Position(0, -1);
    public static final Position LEFT = new Position(-1, 0);
    public static final Position RIGHT = new Position(1, 0);

    public Position(int x, int y){
        this.x = x;
        this.y = y;
    }

    public Position(Position positionToCopy) {
        this.x = positionToCopy.x;
        this.y = positionToCopy.y;
    }

    public void add(Position otherPost){
        this.x += otherPost.x;
        this.y += otherPost.y;
    }
}
