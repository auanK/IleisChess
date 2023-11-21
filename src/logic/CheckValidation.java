package logic;

import game.Player;
import game.ChessLog;
import pieces.King;
import pieces.Piece;

// Classe que implementa o xeque e o xeque-mate.
public class CheckValidation {
    // Verifica se o rei está em xeque
    public static boolean isCheck(Piece[][] board, Player currentPlayer, Player opponent) {
        // Encontra a posição do rei.
        int kingRow = 0;
        int kingColumn = 0;
        for (Piece piece : currentPlayer.getPieces()) {
            if (piece instanceof King) {
                kingRow = piece.getPositionRow();
                kingColumn = piece.getPositionColumn();
                break;
            }
        }

        // Verifica se alguma peça do oponente pode capturar o rei.
        for (Piece piece : opponent.getPieces()) {
            if (piece.validateMove(board, piece.getPositionRow(), piece.getPositionColumn(), kingRow, kingColumn)) {
                return true;
            }
        }

        return false;
    }

    // Verifica se ocorreu um xeque-mate.
    public static boolean isCheckMate(Piece[][] board, Player currentPlayer, Player opponent) {
        // Se o rei não está em xeque, não há xeque-mate.
        if (!currentPlayer.isCheck()) {
            return false;
        }

        // Verifica se alguma peça do jogador atual pode se mover para alguma posição
        return !MoveValidator.hasValidMove(board, currentPlayer, opponent);
    }
}
