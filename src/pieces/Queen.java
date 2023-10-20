package pieces;

import game.Player;

public class Queen extends Piece {
    public Queen(char cor, int positionRow, int positionColumn) {
        super('Q', cor, positionRow, positionColumn);
    }

    public boolean validateMove(Piece[][] board, int sourceRow, int sourceColumn, int destinationRow,
            int destinationColumn, Player currentPlayer) {
        int rowDiff = Math.abs(destinationRow - sourceRow);
        int colDiff = Math.abs(destinationColumn - sourceColumn);

        // Verifica se a rainha está se movendo em diagonal
        if (rowDiff == colDiff) {
            // Define a direção do movimento da rainha
            int rowStep = (destinationRow > sourceRow) ? 1 : -1;
            int colStep = (destinationColumn > sourceColumn) ? 1 : -1;

            // Define a posição da primeira casa após a posição de origem
            int currentRow = sourceRow + rowStep;
            int currentCol = sourceColumn + colStep;

            // Verifica se há peças entre a posição de origem e a posição de destino.
            while (currentRow != destinationRow) {
                if (board[currentRow][currentCol] != null) {
                    return false;
                }
                currentRow += rowStep;
                currentCol += colStep;
            }
            // Senão, verifica se está se movendo em linha reta (horizontal ou vertical)
        } else if ((rowDiff == 0 && colDiff > 0) || (rowDiff > 0 && colDiff == 0)) {
            int start, end, current;
            int step, row = 0, col = 0;

            /*
             * Define a direção do movimento da rainha e a linha ou coluna que
             * será percorrida.
             */
            if (rowDiff == 0) {
                start = Math.min(sourceColumn, destinationColumn);
                end = Math.max(sourceColumn, destinationColumn);
                row = sourceRow;
                step = 1;
            } else {
                start = Math.min(sourceRow, destinationRow);
                end = Math.max(sourceRow, destinationRow);
                col = sourceColumn;
                step = 1;
            }

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
        } else {
            return false;
        }
        Piece destinationPiece = board[destinationRow][destinationColumn];
        return destinationPiece == null || !currentPlayer.getPieces().contains(destinationPiece);

    }

}
