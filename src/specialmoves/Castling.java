package specialmoves;

import game.Player;
import logic.CheckValidation;
import logic.InvalidMoveException;
import pieces.King;
import pieces.Piece;
import pieces.Rook;

public class Castling {
    // Valida o roque.
    public static void validateCastling(Piece[][] board, int[] coordinates, Player currentPlayer, Player opponent)
            throws InvalidMoveException {
        int sourceRow = coordinates[0];
        int sourceColumn = coordinates[1];
        int destinationRow = coordinates[2];
        int destinationColumn = coordinates[3];

        Piece king = board[sourceRow][sourceColumn];
        Piece rook = board[destinationRow][destinationColumn];

        // Verifica se a peça de destino pertence ao jogador atual.
        if (!currentPlayer.getPieces().contains(rook)) {
            return;
        }

        // Verifica se as duas peças são rei e torre.
        if (!((king instanceof King && rook instanceof Rook) || (king instanceof Rook && rook instanceof King))) {
            return;
        }

        // Verifica se as duas peças não se moveram.
        if (king.hasMoved() || rook.hasMoved()) {
            throw new InvalidMoveException("Movimento inválido, rei ou torre já se moveram!");
        }

        // Verifica se está em xeque.
        boolean isCheck = CheckValidation.isCheck(board, currentPlayer, opponent);
        if (isCheck) {
            throw new InvalidMoveException("Movimento inválido, roque não pode ser feito com o rei em xeque!");
        }

        // Verifica se existem peças entre o rei e a torre.
        validatePathClear(board, sourceRow, sourceColumn, destinationColumn, opponent);

        // Faz o roque.
        castling(board, coordinates, currentPlayer);

        // Se o rei ficar em xeque, desfaz o roque.
        isCheck = CheckValidation.isCheck(board, currentPlayer, opponent);
        if (isCheck) {
            undoCastling(board, coordinates, currentPlayer);
            throw new InvalidMoveException("Movimento inválido, rei ficaria em xeque!");
        }

        throw new InvalidMoveException("R");
    }

    public static void castling(Piece[][] board, int[] coordinates, Player currentPlayer) {
        Piece king = board[coordinates[0]][coordinates[1]];
        Piece rook = board[coordinates[2]][coordinates[3]];

        if (!(king instanceof King)) {
            Piece aux = king;
            king = rook;
            rook = aux;
        }

        int kingRow = king.getPositionRow();
        int kingColumn = king.getPositionColumn();
        int rookRow = rook.getPositionRow();
        int rookColumn = rook.getPositionColumn();

        int colDiff = Math.abs(kingColumn - rookColumn);

        if (colDiff == 4) {
            board[kingRow][kingColumn] = null;
            board[kingRow][kingColumn - 2] = king;
            king.setPosition(kingRow, kingColumn - 2);

            board[rookRow][rookColumn] = null;
            board[rookRow][rookColumn + 3] = rook;
            rook.setPosition(rookRow, rookColumn + 3);
        } else {
            board[kingRow][kingColumn] = null;
            board[kingRow][kingColumn + 2] = king;
            king.setPosition(kingRow, kingColumn + 2);

            board[rookRow][rookColumn] = null;
            board[rookRow][rookColumn - 2] = rook;
            rook.setPosition(rookRow, rookColumn - 2);

        }

        king.setMoved(true);
        rook.setMoved(true);
    }

    public static void undoCastling(Piece[][] board, int[] coordinates, Player currentPlayer) {
        Piece king = board[coordinates[0]][coordinates[1]];
        Piece rook = board[coordinates[2]][coordinates[3]];

        if (!(king instanceof King)) {
            Piece aux = king;
            king = rook;
            rook = aux;
        }

        int kingRow = king.getPositionRow();
        int kingColumn = king.getPositionColumn();
        int rookRow = rook.getPositionRow();
        int rookColumn = rook.getPositionColumn();

        int colDiff = Math.abs(kingColumn - rookColumn);

        if (colDiff == 4) {
            board[kingRow][kingColumn] = null;
            board[kingRow][kingColumn + 2] = king;
            king.setPosition(kingRow, kingColumn + 2);

            board[rookRow][rookColumn] = null;
            board[rookRow][rookColumn - 3] = rook;
            rook.setPosition(rookRow, rookColumn - 3);
        } else {
            board[kingRow][kingColumn] = null;
            board[kingRow][kingColumn - 2] = king;
            king.setPosition(kingRow, kingColumn - 2);

            board[rookRow][rookColumn] = null;
            board[rookRow][rookColumn + 2] = rook;
            rook.setPosition(rookRow, rookColumn + 2);

        }

        king.setMoved(false);
        rook.setMoved(false);
    }

    private static void validatePathClear(Piece[][] board, int row, int start, int end, Player opponent)
            throws InvalidMoveException {
        int step = (start < end) ? 1 : -1;
        int current = start + step;

        while (current != end) {
            if (board[row][current] != null) {
                throw new InvalidMoveException("Movimento inválido, existem peças entre o rei e a torre!");
            }

            checkIfSquareIsUnderAttack(board, opponent, row, current);
            current += step;
        }
    }

    private static void checkIfSquareIsUnderAttack(Piece[][] board, Player opponent, int row, int column)
            throws InvalidMoveException {
        for (Piece piece : opponent.getPieces()) {
            int opponentRow = piece.getPositionRow();
            int opponentColumn = piece.getPositionColumn();

            if (piece.validateMove(board, opponentRow, opponentColumn, row, column)) {
                throw new InvalidMoveException(
                        "Movimento inválido, existem casas sendo atacadas entre o rei e a torre!");
            }
        }
    }

}
