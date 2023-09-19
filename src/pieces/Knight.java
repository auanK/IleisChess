package pieces;

import game.Player;

/**
 * Classe que representa a peça "Cavalo" no jogo de xadrez.
 */
public class Knight extends Piece {

    /**
     * Construtor da classe Knight.
     *
     * @param cor            A cor da peça (W para branca ou B para preta).
     * @param positionRow    A linha em que a peça está posicionada.
     * @param positionColumn A coluna em que a peça está posicionada.
     */
    public Knight(char cor, int positionRow, int positionColumn) {
        super('N', cor, positionRow, positionColumn); // Chama o construtor da superclasse Piece.
    }

    /**
     * Cria uma cópia da peça "Cavalo".
     *
     * @return Uma nova instância do "Cavalo" com as mesmas características.
     */
    public Knight copy() {
        Knight copyKnight = new Knight(getColor(), getPositionRow(), getPositionColumn());
        return copyKnight;
    }

    /**
     * Valida o movimento do cavalo de acordo com as regras do jogo de xadrez.
     *
     * O cavalo se movimenta em forma de "L", logo é necessário que a diferença
     * entre as
     * coordenadas de origem e destino seja de 2 em uma direção e 1 na outra, e a
     * casa de destino esteja vazia ou contenha uma peça adversária.
     *
     * @param board             O tabuleiro de jogo representado como uma matriz de
     *                          peças.
     * @param sourceRow         A linha de origem da peça.
     * @param sourceColumn      A coluna de origem da peça.
     * @param destinationRow    A linha de destino do movimento.
     * @param destinationColumn A coluna de destino do movimento.
     * @param currentPlayer     O jogador atual que está realizando o movimento.
     * @return true se o movimento do cavalo for válido, caso contrário, false.
     */
    public boolean validateMove(Piece[][] board, int sourceRow, int sourceColumn, int destinationRow,
            int destinationColumn, Player currentPlayer) {
        // Calcular a diferença entre as coordenadas de origem e destino
        int rowDiff = Math.abs(destinationRow - sourceRow);
        int colDiff = Math.abs(destinationColumn - sourceColumn);

        // Verificar se o movimento é válido para o cavalo
        if ((rowDiff == 2 && colDiff == 1) || (rowDiff == 1 && colDiff == 2)) {
            // O movimento é válido se for uma das duas opções:
            // 1. Duas casas na vertical e uma casa na horizontal.
            // 2. Duas casas na horizontal e uma casa na vertical.

            // Verificar se a casa de destino está vazia ou contém uma peça adversária
            if (board[destinationRow][destinationColumn] == null
                    || !currentPlayer.getPieces().contains(board[destinationRow][destinationColumn])) {
                return true;
            }
        }

        // Se não for um movimento válido para o cavalo, retorne falso
        return false;
    }
}
