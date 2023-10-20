package pieces;

import game.Player;

public class Rook extends Piece {
    public Rook(char cor, int positionRow, int positionColumn) {
        super('R', cor, positionRow, positionColumn);
    }

    /**
     * @brief Método que valida o movimento da Torre. A Torre pode se mover em
     *        linha reta (horizontal ou vertical) quantas casas quiser, desde que
     *        não haja nenhuma peça entre a posição de origem e a posição de
     *        destino e a posição de destino esteja vazia ou contenha uma peça do
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
     * 
     */
    public boolean validateMove(Piece[][] board, int sourceRow, int sourceColumn, int destinationRow,
            int destinationColumn, Player currentPlayer) {
        int rowDiff = Math.abs(destinationRow - sourceRow);
        int colDiff = Math.abs(destinationColumn - sourceColumn);

        // Verifica se a torre não está se movendo em linha reta (horizontal ou vertical)
        if ((rowDiff != 0 || colDiff <= 0) && (rowDiff <= 0 || colDiff != 0)) {
            return false;
        }

        int start, end, current;
        int step = 1, row = 0, col = 0;

        /*
         * Define a direção do movimento da torre e a linha ou coluna que
         * será percorrida.
         */
        if (rowDiff == 0) {
            start = Math.min(sourceColumn, destinationColumn);
            end = Math.max(sourceColumn, destinationColumn);
            row = sourceRow;
        } else {
            start = Math.min(sourceRow, destinationRow);
            end = Math.max(sourceRow, destinationRow);
            col = sourceColumn;
        }

        // Verifica se há peças entre a posição de origem e a posição de destino
        current = start + step;
        while (current < end) {
            if (rowDiff == 0) {
                if (board[row][current] != null) {
                    return false;
                }
            } else {
                if (board[current][col] != null) {
                    return false;
                }
            }
            current += step;
        }

        /*
         * Verifica se a posição de destino está vazia ou se contém uma peça do
         * oponente e retorna true ou false, respectivamente.
         */
        Piece destinationPiece = board[destinationRow][destinationColumn];
        return destinationPiece == null || !currentPlayer.getPieces().contains(destinationPiece);
    }

}