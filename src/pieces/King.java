package pieces;

import game.Player;

public class King extends Piece {
    public King(char cor, int positionRow, int positionColumn) {
        super('K', cor, positionRow, positionColumn);
    }

    public boolean validateMove(Piece[][] board, int sourceRow, int sourceColumn, int destinationRow,
            int destinationColumn, Player currentPlayer) {
        int rowDiff = Math.abs(destinationRow - sourceRow);
        int colDiff = Math.abs(destinationColumn - sourceColumn);

        if (rowDiff <= 1 && colDiff <= 1) {
            Piece destinationPiece = board[destinationRow][destinationColumn];
            return destinationPiece == null || !currentPlayer.getPieces().contains(destinationPiece);
        }

        return false;
    }

}
