package pieces;

import game.Player;

/**
 * Classe abstrata que representa uma peça no jogo de xadrez.
 */
public abstract class Piece {

    // Atributos da classe Piece
    private char label; // Rótulo da peça (por exemplo, 'P' para peão)
    private char color; // Cor da peça (W para branca ou B para preta)
    private int positionRow; // Linha atual da posição da peça no tabuleiro
    private int positionColumn; // Coluna atual da posição da peça no tabuleiro

    /**
     * Construtor da classe Piece.
     *
     * @param label          O rótulo da peça (por exemplo, 'P' para peão).
     * @param color          A cor da peça (W para branca ou B para preta).
     * @param positionRow    A linha inicial da posição da peça no tabuleiro.
     * @param positionColumn A coluna inicial da posição da peça no tabuleiro.
     */
    public Piece(char label, char color, int positionRow, int positionColumn) {
        this.label = label;
        this.color = color;
        this.positionRow = positionRow;
        this.positionColumn = positionColumn;
    }

    /**
     * Obtém o rótulo da peça.
     *
     * @return O rótulo da peça (por exemplo, 'P' para peão).
     */
    public char getLabel() {
        return label;
    }

    /**
     * Obtém a cor da peça.
     *
     * @return A cor da peça (W para branca ou B para preta).
     */
    public char getColor() {
        return color;
    }

    /**
     * Obtém a linha atual da posição da peça no tabuleiro.
     *
     * @return A linha atual da posição da peça.
     */
    public int getPositionRow() {
        return positionRow;
    }

    /**
     * Obtém a coluna atual da posição da peça no tabuleiro.
     *
     * @return A coluna atual da posição da peça.
     */
    public int getPositionColumn() {
        return positionColumn;
    }

    /**
     * Atualiza a posição da peça no tabuleiro.
     *
     * @param positionRow    A nova linha da posição da peça.
     * @param positionColumn A nova coluna da posição da peça.
     */
    public void setPosition(int positionRow, int positionColumn) {
        this.positionRow = positionRow;
        this.positionColumn = positionColumn;
    }

    /**
     * Método abstrato que será implementado pelas subclasses para validar o
     * movimento de peças.
     *
     * @param board             O tabuleiro de jogo representado como uma matriz de
     *                          peças.
     * @param sourceRow         A linha de origem da peça.
     * @param sourceColumn      A coluna de origem da peça.
     * @param destinationRow    A linha de destino do movimento.
     * @param destinationColumn A coluna de destino do movimento.
     * @param currentPlayer     O jogador atual que está realizando o movimento.
     * @return true se o movimento da peça for válido, caso contrário, false.
     */
    public abstract boolean validateMove(Piece[][] board, int sourceRow, int sourceColumn, int destinationRow,
            int destinationColumn, Player currentPlayer);

    /**
     * Método abstrato que será implementado pelas subclasses para criar uma cópia
     * da peça.
     *
     * @return Uma nova instância da peça com as mesmas características.
     */
    public abstract Piece copy();
}
