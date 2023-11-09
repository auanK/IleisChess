package logic;

import game.Player;
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
            if (piece.validateMove(board, piece.getPositionRow(), piece.getPositionColumn(), kingRow, kingColumn)) {
                return true;
            }
        }

        return false;
    }

    public static boolean isCheckMate(Piece[][] board, Player currentPlayer, Player opponent) {
        if (!isCheck(board, currentPlayer, opponent)) {
            return false;
        }

        for (Piece piece : currentPlayer.getPieces()) {
            int sourceRow = piece.getPositionRow();
            int sourceColumn = piece.getPositionColumn();

            for (int destinationRow = 0; destinationRow < 8; destinationRow++) {
                for (int destinationColumn = 0; destinationColumn < 8; destinationColumn++) {
                    int coordinates[] = { sourceRow, sourceColumn, destinationRow, destinationColumn };
                    try {
                        ChessMove.validateMove(board, coordinates, currentPlayer, opponent);
                    } catch (InvalidMoveException e) {
                        continue;
                    }
                }
            }
        }
        return true;
    }
}
