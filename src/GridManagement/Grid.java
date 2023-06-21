package GridManagement;

import GUI.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Grid extends Board {
    private Cell[][] grid;
    private int moves;
    private List<Position> validMoves;

    public Grid(Position post, int width, int height, int gridWidth, int gridHeight) {
        super(post, width, height);
        grid = new Cell[gridWidth][gridHeight];
        int cellWidth = (width - post.x) / gridWidth;
        int cellHeight = (height - post.y) / gridHeight;

        for (int x = 0; x < gridWidth; x++) {
            for (int y = 0; y < gridHeight; y++) {
                grid[x][y] = new Cell(new Position(post.x + cellWidth * x, post.y + cellHeight * y), cellWidth, cellHeight);
            }
        }
//        Inisiasi disc tengah
        int midX = gridWidth / 2 - 1;
        int midY = gridHeight / 2 - 1;
        grid[midX][midY].setDisc(1);
        grid[midX + 1][midY].setDisc(2);
        grid[midX][midY + 1].setDisc(2);
        grid[midX + 1][midY + 1].setDisc(1);

        moves = 0;
        validMoves = new ArrayList<>();
        updateValidMoves(1);
    }


    public void reset(){
        for(int x = 0; x < grid.length; x++) {
            for (int y = 0; y < grid[0].length; y++) {
                grid[x][y].reset();
            }
        }
        moves = 0;
        updateValidMoves(1);
    }

    public List<Position> getValidMoves() {
        return validMoves;
    }

    public void playMove(Position position, int player) {
        moves++;
        grid[position.x][position.y].setDisc(player);
        List<Position> changeCellPositions = getChangedPositions(position, player);
        for(Position swapPosition : changeCellPositions) {
            grid[swapPosition.x][swapPosition.y].setDisc(player);
        }
        updateValidMoves(player == 1 ? 2 : 1);
    }

    public Position mouseToGridPost(Position mousePosition) {
        int gridX = (mousePosition.x- post.x)/grid[0][0].width;
        int gridY = (mousePosition.y- post.y)/grid[0][0].height;
        if(gridX >= grid.length || gridX < 0 || gridY >= grid[0].length || gridY < 0) {
            return new Position(-1,-1);
        }
        return new Position(gridX,gridY);
    }

    public boolean isValidMove(Position position){
        return getValidMoves().contains(position);
    }

    public int getWinner(boolean stillValidMoves){
        int[] counts = new int[3];
        for(int y = 0; y < grid[0].length; y++){
            for(int x = 0; x < grid.length; x++){
                counts[grid[x][y].getDisc()]++;
            }
        }
        if(stillValidMoves && counts[0] > 0) return 0;
        else if (counts[1] == counts[2]) return 3;
        else return counts[1] > counts[2] ? 1 : 2;
    }

    public void paint(Graphics g){
        drawGrid(g);
        for(int x = 0; x < grid.length; x++) {
            for (int y = 0; y < grid[0].length; y++) {
                grid[x][y].mark(g);
            }
        }
    }

    private void drawGrid(Graphics g) {
        g.setColor(Color.BLACK);
//        Vertical Line
        int y2 = post.y+height;
        int y1 = post.y;
        for(int x = 0; x < grid.length+1; x++)
            g.drawLine(post.x+x * grid[0][0].width, y1, post.x+x * grid[0][0].width, y2);
//        Horizontal Line
        int x2 = post.x+width;
        int x1 = post.x;
        for(int y = 0; y < grid[0].length+1; y++)
            g.drawLine(x1, post.y+y * grid[0][0].height, x2, post.y+y * grid[0][0].height);
    }

    public void updateValidMoves(int playerID){
        for(Position validMove:validMoves) {
            grid[validMove.x][validMove.y].setHighlight(false);
        }
        validMoves.clear();

        for (int x = 0; x < grid.length; x++) {
            for (int y = 0; y < grid[0].length; y++) {
                if (grid[x][y].getDisc() == 0 && getChangedPositions(new Position(x, y), playerID).size() > 0) {
                    validMoves.add(new Position(x, y));
                }
            }
        }

        for (Position validMove:validMoves){
            grid[validMove.x][validMove.y].setHighlight(true);
        }
    }
    public List<Position> getChangedPositions(Position position, int playerID) {
        List<Position> result = new ArrayList<>();
        result.addAll(getChangedPositionsInDirect(position, playerID, Position.DOWN));
        result.addAll(getChangedPositionsInDirect(position, playerID, Position.LEFT));
        result.addAll(getChangedPositionsInDirect(position, playerID, Position.UP));
        result.addAll(getChangedPositionsInDirect(position, playerID, Position.RIGHT));
        result.addAll(getChangedPositionsInDirect(position, playerID, Position.DOWN_LEFT));
        result.addAll(getChangedPositionsInDirect(position, playerID, Position.DOWN_RIGHT));
        result.addAll(getChangedPositionsInDirect(position, playerID, Position.UP_LEFT));
        result.addAll(getChangedPositionsInDirect(position, playerID, Position.UP_RIGHT));
        return result;
    }
    private List<Position> getChangedPositionsInDirect(Position position, int playerID, Position direction){
        List<Position> result = new ArrayList<>();
        Position moving = new Position(position);
        int otherPlayer = playerID == 1 ? 2 : 1;
        moving.add(direction);

        while (inBounds(moving) && grid[moving.x][moving.y].getDisc() == otherPlayer){
            result.add(new Position(moving));
            moving.add(direction);
        }

        if (!inBounds(moving) || grid[moving.x][moving.y].getDisc() != playerID){
            result.clear();
        }
        return result;
    }
    private boolean inBounds(Position position){
        return !(position.x < 0 || position.y < 0 || position.x >= grid.length || position.y >= grid[0].length);
    }
}
