package pieces;

import game.Player;

public class Hugo extends Piece {
    public Hugo(char cor, int positionRow, int positionColumn) {
        super('H', cor, positionRow, positionColumn);
    }

    public boolean validateMove(Piece[][] board, int sourceRow, int sourceColumn, int destinationRow,
            int destinationColumn, Player currentPlayer) {
        int rowDiff = Math.abs(destinationRow - sourceRow);
        int colDiff = Math.abs(destinationColumn - sourceColumn);

        if ((rowDiff == 2 && colDiff == 1) || (rowDiff == 1 && colDiff == 2)) {
            if (board[destinationRow][destinationColumn] == null
                    || !currentPlayer.getPieces().contains(board[destinationRow][destinationColumn])) {
                return true;
            }
        }

        if ((rowDiff == 0 && colDiff > 0) || (rowDiff > 0 && colDiff == 0)) {
            int start;
            int end;

            if (rowDiff == 0) {
                start = Math.min(sourceColumn, destinationColumn);
                end = Math.max(sourceColumn, destinationColumn);

                for (int i = start + 1; i < end; i++) {
                    if (board[sourceRow][i] != null) {
                        return false;
                    }
                }
            } else {
                start = Math.min(sourceRow, destinationRow);
                end = Math.max(sourceRow, destinationRow);

                for (int i = start + 1; i < end; i++) {
                    if (board[i][sourceColumn] != null) {
                        return false;
                    }
                }
            }

            if (board[destinationRow][destinationColumn] == null
                    || !currentPlayer.getPieces().contains(board[destinationRow][destinationColumn])) {
                return true;
            }
        }

        if (rowDiff == colDiff) {
            int rowStep;
            int colStep;

            rowStep = (destinationRow > sourceRow) ? 1 : -1;
            colStep = (destinationColumn > sourceColumn) ? 1 : -1;

            int currentRow = sourceRow + rowStep;
            int currentCol = sourceColumn + colStep;

            while (currentRow != destinationRow) {
                if (board[currentRow][currentCol] != null) {
                    return false;
                }
                currentRow += rowStep;
                currentCol += colStep;
            }

            if (board[destinationRow][destinationColumn] == null
                    || !currentPlayer.getPieces().contains(board[destinationRow][destinationColumn])) {
                return true;
            }
        }

        return false;
    }
}
