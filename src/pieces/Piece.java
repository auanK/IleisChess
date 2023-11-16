package pieces;

// Classe abstrata que representa uma peça.
public abstract class Piece {
    private char label;         // Rótulo da peça.
    private char color;         // Cor da peça.
    private char colorSquare;  // Cor do quadrado de origem da peça.
    private int positionRow;    // Linha da posição da peça.
    private int positionColumn; // Coluna da posição da peça.
    private boolean moved;      // Indica se a peça já se moveu.

    // Construtor da classe.
    public Piece(char label, char color, char colorSquare, int positionRow, int positionColumn) {
        this.label = label;
        this.color = color;
        this.colorSquare = colorSquare;
        this.positionRow = positionRow;
        this.positionColumn = positionColumn;
        this.moved = false;
    }

    // Retorna o rótulo da peça
    public char getLabel() {
        return label;
    }

    // Retorna a cor da peça
    public char getColor() {
        return color;
    }

    // Retorna a cor do quadrado de origem da peça.
    public char getColorSquare() {
        return colorSquare;
    }

    // Retorna a linha da posição da peça
    public int getPositionRow() {
        return positionRow;
    }

    // Retorna a coluna da posição da peça
    public int getPositionColumn() {
        return positionColumn;
    }

    // Seta se a peça já se moveu
    public void setMoved(boolean moved) {
        this.moved = moved;
    }

    // Retorna se a peça já se moveu
    public boolean hasMoved() {
        return moved;
    }

    // Seta a linha e a coluna da posição da peça.
    public void setPosition(int positionRow, int positionColumn) {
        this.positionRow = positionRow;
        this.positionColumn = positionColumn;
    }

    // Método abstrato que valida o movimento da peça.
    public abstract boolean validateMove(Piece[][] board, int sourceRow, int sourceColumn, int destinationRow,
            int destinationColumn);

}
