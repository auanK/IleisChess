package pieces;

import game.Player;

// Classe abstrata que representa uma peça.
public abstract class Piece {
    private char label; // Rótulo da peça.
    private char color; // Cor da peça.
    private int positionRow; // Linha da posição da peça.
    private int positionColumn; // Coluna da posição da peça.
    private boolean moved; // Indica se a peça já se moveu.

    /**
     * @brief Construtor da classe, recebe os parâmetros necessários para a criação
     *        de uma peça e seta o atributo moved como false.
     * 
     * @param label          Rótulo da peça
     * @param color          Cor da peça
     * @param positionRow    Linha da posição da peça
     * @param positionColumn Coluna da posição da peça
     */
    public Piece(char label, char color, int positionRow, int positionColumn) {
        this.label = label;
        this.color = color;
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

    // Retorna a linha da posição da peça
    public int getPositionRow() {
        return positionRow;
    }

    // Retorna a coluna da posição da peça
    public int getPositionColumn() {
        return positionColumn;
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

    /**
     * @brief Método que valida o movimento da peça.
     * @param board             Tabuleiro atual
     * @param sourceRow         Linha da posição de origem
     * @param sourceColumn      Coluna da posição de origem
     * @param destinationRow    Linha da posição de destino
     * @param destinationColumn Coluna da posição de destino
     * @param currentPlayer     Jogador atual
     * @return true se o movimento for válido, false caso contrário
     */
    public abstract boolean validateMove(Piece[][] board, int sourceRow, int sourceColumn, int destinationRow,
            int destinationColumn, Player currentPlayer);

}
