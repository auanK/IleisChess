package pieces;

import game.Player;

public abstract class Piece {

    private char label;
    private char color;
    private int positionRow;
    private int positionColumn;
    private boolean moved;

    public Piece(char label, char color, int positionRow, int positionColumn) {
        this.label = label;
        this.color = color;
        this.positionRow = positionRow;
        this.positionColumn = positionColumn;
        this.moved = false;
    }

    public char getLabel() {
        return label;
    }

    public char getColor() {
        return color;
    }

    public int getPositionRow() {
        return positionRow;
    }

    public int getPositionColumn() {
        return positionColumn;
    }

     public boolean hasMoved() {
        return moved;
     }

    public void setPosition(int positionRow, int positionColumn) {
        this.positionRow = positionRow;
        this.positionColumn = positionColumn;
    }

    public abstract boolean validateMove(Piece[][] board, int sourceRow, int sourceColumn, int destinationRow,
            int destinationColumn, Player currentPlayer);

}
