package pieces;

import game.Player;

// Classe que representa a peça Cavalo
public class Knight extends Piece {
    // Construtor da classe
    public Knight(char cor, int positionRow, int positionColumn) {
        super('N', cor, positionRow, positionColumn);
    }

    /**
     * @brief Método que valida o movimento do Cavalo. O Cavalo pode se mover em L
     *        (duas casas em uma direção e uma casa em uma direção perpendicular),
     *        desde que a casa de destino esteja vazia ou contenha uma peça do
     *        oponente.
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
        int rowDiff = Math.abs(destinationRow - sourceRow);
        int colDiff = Math.abs(destinationColumn - sourceColumn);

        /*
         * Verifica se o cavalo está se movendo em L (duas casas em uma direção e uma
         * casa em uma direção perpendicular) e se a casa de destino está vazia ou
         * contém uma peça do oponente e retorna true ou false respectivamente.
         */
        return (rowDiff == 2 && colDiff == 1) || (rowDiff == 1 && colDiff == 2) &&
                (board[destinationRow][destinationColumn] == null
                        || !currentPlayer.getPieces().contains(board[destinationRow][destinationColumn]));
    }

}
