package game;

import pieces.King;
import pieces.Piece;

public class CheckValidation {
    public static boolean isCheck(Piece[][] board, Player currentPlayer, Player opponent) {
        int kingRow = 0;
        int kingColumn = 0;
        for (Piece piece : currentPlayer.getPieces()) {
            if (piece instanceof King) {
                kingRow = piece.getPositionRow();
                kingColumn = piece.getPositionColumn();
                break;
            }
        }

        for (Piece piece : opponent.getPieces()) {
            if (piece.validateMove(board, piece.getPositionRow(), piece.getPositionColumn(), kingRow, kingColumn,
                    opponent)) {
                return true;
            }
        }

        return false;
    }
}
