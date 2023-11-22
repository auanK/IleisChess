package pieces;

import java.io.Serializable;

// Classe abstrata que representa uma peça.
public abstract class Piece implements Serializable {
    private char label;         // Rótulo da peça.
    private char color;         // Cor da peça.
    private char colorSquare;  // Cor do quadrado de origem da peça.
    private int positionRow;    // Linha da posição da peça.
    private int positionColumn; // Coluna da posição da peça.
    private int count_moves;    // Contador de movimentos da peça.

    // Construtor da classe.
    public Piece(char label, char color, char colorSquare, int positionRow, int positionColumn) {
        this.label = label;
        this.color = color;
        this.colorSquare = colorSquare;
        this.positionRow = positionRow;
        this.positionColumn = positionColumn;
        this.count_moves = 0;
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

    // Retorna o quantas vezes a peça se moveu.
    public int getCountMoves() {
        return count_moves;
    }

    // Retorna se a peça já se moveu
    public boolean hasMoved() {
        return count_moves > 0;
    }

    // Incrementa o contador de movimentos da peça.
    public void addMove() {
        count_moves++;
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
