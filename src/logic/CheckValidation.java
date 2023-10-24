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
            if (piece.validateMove(board, piece.getPositionRow(), piece.getPositionColumn(), kingRow, kingColumn,
                    opponent)) {
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
                    if (piece.validateMove(board, sourceRow, sourceColumn, destinationRow, destinationColumn,
                            currentPlayer)) {
                        Piece destinationPiece = board[destinationRow][destinationColumn];
                        ChessMove.simulateMove(board,
                                new int[] { sourceRow, sourceColumn, destinationRow, destinationColumn },
                                currentPlayer, opponent, piece, destinationPiece);
                        boolean isCheck = CheckValidation.isCheck(board, currentPlayer, opponent);
                        ChessMove.undoMove(board,
                                new int[] { sourceRow, sourceColumn, destinationRow, destinationColumn },
                                currentPlayer, opponent, piece, destinationPiece);

                        if (!isCheck) {
                            return false;
                        }

                    }
                }
            }
        }

        return true;
    }
}
