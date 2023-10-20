package pieces;

import game.Player;

public class Bishop extends Piece {
    public Bishop(char cor, int positionRow, int positionColumn) {
        super('B', cor, positionRow, positionColumn);
    }

    /**
     * @brief Método que valida o movimento do Bispo. O Bispo pode se mover em
     *        diagonal quantas casas quiser, desde que não haja nenhuma peça entre a
     *        posição de origem e a posição de destino e a posição de destino esteja
     *        vazia ou contenha uma peça do oponente.
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

        // Verifica se o bispo está se movendo em diagonal
        if (rowDiff != colDiff) {
            return false;
        }

        // Define a direção do movimento do bispo
        int rowStep = (destinationRow > sourceRow) ? 1 : -1;
        int colStep = (destinationColumn > sourceColumn) ? 1 : -1;

        // Define a posição da primeira casa após a posição de origem
        int currentRow = sourceRow + rowStep;
        int currentCol = sourceColumn + colStep;

        // Verifica se há peças entre a posição de origem e a posição de destino
        while (currentRow != destinationRow) {
            if (board[currentRow][currentCol] != null) {
                return false;
            }
            currentRow += rowStep;
            currentCol += colStep;
        }

        /*
         * Verifica se a posição de destino está vazia ou se contém uma peça do
         * oponente e retorna true ou false, respectivamente.
         */
        Piece destinationPiece = board[destinationRow][destinationColumn];
        return destinationPiece == null || !currentPlayer.getPieces().contains(destinationPiece);
    }

}
