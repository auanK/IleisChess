package pieces;

import game.Player;

/**
 * Classe que representa a peça "Bispo" no jogo de xadrez.
 */
public class Bishop extends Piece {

    /**
     * Construtor da classe Bishop.
     *
     * @param cor            A cor da peça (W para branca ou B para preta).
     * @param positionRow    A linha em que a peça está posicionada.
     * @param positionColumn A coluna em que a peça está posicionada.
     */
    public Bishop(char cor, int positionRow, int positionColumn) {
        super('B', cor, positionRow, positionColumn); // Chama o construtor da superclasse Piece.
    }

    /**
     * Cria uma cópia da peça "Bispo".
     *
     * @return Uma nova instância do "Bispo" com as mesmas características.
     */
    public Bishop copy() {
        Bishop copyBishop = new Bishop(getColor(), getPositionRow(), getPositionColumn());
        return copyBishop;
    }

    /**
     * Valida o movimento do bispo de acordo com as regras do jogo de xadrez.
     *
     * O bispo se movimenta na diagonal, logo é necessário que a diferença entre as coordenadas
     * de origem e destino seja a mesma na vertical e horizontal, e a casa de destino esteja vazia
     * ou contenha uma peça adversária.
     *
     * @param board           O tabuleiro de jogo representado como uma matriz de peças.
     * @param sourceRow       A linha de origem da peça.
     * @param sourceColumn    A coluna de origem da peça.
     * @param destinationRow  A linha de destino do movimento.
     * @param destinationColumn A coluna de destino do movimento.
     * @param currentPlayer   O jogador atual que está realizando o movimento.
     * @return true se o movimento do bispo for válido, caso contrário, false.
     */
    public boolean validateMove(Piece[][] board, int sourceRow, int sourceColumn, int destinationRow,
            int destinationColumn, Player currentPlayer) {
        // Calcular a diferença entre as coordenadas de origem e destino
        int rowDiff = Math.abs(destinationRow - sourceRow);
        int colDiff = Math.abs(destinationColumn - sourceColumn);

        // Verificar se o movimento é na diagonal (mesma quantidade de casas na vertical e na horizontal)
        if (rowDiff == colDiff) {
            int rowStep;
            int colStep;

            // Determinar o sentido do movimento na diagonal
            rowStep = (destinationRow > sourceRow) ? 1 : -1;
            colStep = (destinationColumn > sourceColumn) ? 1 : -1;

            int currentRow = sourceRow + rowStep;
            int currentCol = sourceColumn + colStep;

            // Verificar se não há peças no caminho
            while (currentRow != destinationRow) {
                if (board[currentRow][currentCol] != null) {
                    return false;
                }
                currentRow += rowStep;
                currentCol += colStep;
            }

            // Verificar se a casa de destino está vazia ou contém uma peça adversária
            if (board[destinationRow][destinationColumn] == null
                    || !currentPlayer.getPieces().contains(board[destinationRow][destinationColumn])) {
                return true;
            }
        }

        // Se não for um movimento válido para o bispo, retorne falso
        return false;
    }
}
