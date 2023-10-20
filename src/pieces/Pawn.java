package pieces;

import game.Player;

// Classe que representa a peça Peão
public class Pawn extends Piece {
    // Construtor da classe
    public Pawn(char cor, int positionRow, int positionColumn) {
        super('P', cor, positionRow, positionColumn);
    }

    /**
     * @brief Método que valida o movimento do Peão. O Peão pode se mover uma casa
     *        para frente, duas casas para frente (apenas na primeira jogada) ou uma
     *        casa na diagonal (apenas para capturar uma peça do oponente).
     * 
     * @param board             Tabuleiro atual
     * @param sourceRow         Linha da posição de origem
     * @param sourceColumn      Coluna da posição de origem
     * @param destinationRow    Linha da posição de destino
     * @param destinationColumn Coluna da posição de destino
     * @param currentPlayer     Jogador atual
     * 
     * @return true se o movimento for válido, false caso contrário
     */
    public boolean validateMove(Piece[][] board, int sourceRow, int sourceColumn, int destinationRow,
            int destinationColumn, Player currentPlayer) {
        // Calcula a diferença entre as coordenadas de origem e destino
        int rowDifference = destinationRow - sourceRow;
        int colDifference = Math.abs(destinationColumn - sourceColumn);

        // Verifica a cor do peão e define a direção do movimento
        char color = getColor();
        int moveDirection = (color == 'W') ? 1 : -1;

        // Verifica se o peão está se movendo na vertical
        if (sourceColumn == destinationColumn) {
            // Movimento de uma casa para frente
            if (rowDifference == moveDirection && board[destinationRow][destinationColumn] == null) {
                return true;
            }
            // Movimento de duas casas para frente (apenas na primeira jogada)
            else if (rowDifference == 2 * moveDirection && sourceRow == (color == 'W' ? 1 : 6)
                    && board[sourceRow + moveDirection][sourceColumn] == null
                    && board[destinationRow][destinationColumn] == null) {
                return true;
            }
        }
        // Verifica o movimento diagonal para capturar uma peça do oponente
        else if (colDifference == 1 && rowDifference == moveDirection) {
            /**
             * Verifica se a posição de destino está vazia ou se contém uma peça do
             * oponente e retorna true ou false, respectivamente.
             */
            Piece destinationPiece = board[destinationRow][destinationColumn];
            return destinationPiece != null && !currentPlayer.getPieces().contains(destinationPiece);
        }

        return false;
    }
}
