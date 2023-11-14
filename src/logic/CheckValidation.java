package logic;

import game.Player;
import pieces.King;
import pieces.Piece;

// Classe que verifica se o rei está em xeque ou xeque-mate.
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

    // Verifica se o rei está em xeque-mate
    public static boolean isCheckMate(Piece[][] board, Player currentPlayer, Player opponent) {
        // Se o rei não está em xeque, não há xeque-mate.
        if (!isCheck(board, currentPlayer, opponent)) {
            return false;
        }

        // Se o rei está em xeque, verifica se há algum movimento possível para o rei,
        // ou para qualquer outra peça, que tire o rei do xeque.
        for (Piece piece : currentPlayer.getPieces()) {
            int sourceRow = piece.getPositionRow();
            int sourceColumn = piece.getPositionColumn();

            for (int destinationRow = 0; destinationRow < 8; destinationRow++) {
                for (int destinationColumn = 0; destinationColumn < 8; destinationColumn++) {
                    int coordinates[] = { sourceRow, sourceColumn, destinationRow, destinationColumn };
                    try {
                        ChessMove.validateMove(board, coordinates, currentPlayer, opponent);
                        return false;
                    } catch (InvalidMoveException e) {
                        continue;
                    }
                }
            }
        }
        return true;
    }
}
