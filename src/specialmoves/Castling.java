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

        Piece sourcePiece = board[sourceRow][sourceColumn];
        Piece destinationPiece = board[destinationRow][destinationColumn];

        // Verifica se a peça de destino pertence ao jogador atual.
        if (!currentPlayer.getPieces().contains(destinationPiece)) {
            return;
        }

        // Verifica se as duas peças são rei e torre.
        if (!((sourcePiece instanceof King && destinationPiece instanceof Rook)
                || sourcePiece instanceof Rook && destinationPiece instanceof King)) {
            return;
        }

        // Verifica se as duas peças não se moveram.
        if (sourcePiece.hasMoved() || destinationPiece.hasMoved()) {
            throw new InvalidMoveException("Movimento inválido, rei ou torre já se moveram!");
        }

        // Verifica se está em xeque.
        boolean isCheck = CheckValidation.isCheck(board, currentPlayer, opponent);
        if (isCheck) {
            throw new InvalidMoveException("Movimento inválido, roque não pode ser feito com o rei em xeque!");
        }

        // Verifica se existem peças entre o rei e a torre.
        validatePathClear(board, sourceRow, sourceColumn, destinationColumn, opponent);

        // Verifica se o rei ficaria em xeque ao se mover.
        boolean isCheckAfterMove = rockIsCheck(board, coordinates, currentPlayer, opponent);
        if (isCheckAfterMove) {
            throw new InvalidMoveException("Movimento inválido, o rei fica em xeque ao se mover!");
        }

        throw new InvalidMoveException("Rock!");
    }

    // Faz o roque.
    public static void castling(Piece[][] board, int[] coordinates, Player currentPlayer, Player opponent)
            throws InvalidMoveException {
        // Encontra as peças.
        Piece king = board[coordinates[0]][coordinates[1]];
        Piece rook = board[coordinates[2]][coordinates[3]];

        if (!(king instanceof King)) {
            Piece aux = king;
            king = rook;
            rook = aux;
        }

        // Salva as posições das peças e a diferença entre elas.
        int kingRow = king.getPositionRow();
        int kingColumn = king.getPositionColumn();
        int rookRow = rook.getPositionRow();
        int rookColumn = rook.getPositionColumn();

        int colDiff = Math.abs(kingColumn - rookColumn);

        // Verifica se o roque é grande ou pequeno e move as peças.
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

    // Verifica se o rei ficaria em xeque ao se mover.
    public static boolean rockIsCheck(Piece[][] board, int[] coordinates, Player currentPlayer, Player opponent) {
        // Encontra as peças.
        Piece king = board[coordinates[0]][coordinates[1]];
        Piece rook = board[coordinates[2]][coordinates[3]];

        if (!(king instanceof King)) {
            Piece aux = king;
            king = rook;
            rook = aux;
        }

        // Salva as posições das peças e a diferença entre elas.
        int kingRow = king.getPositionRow();
        int kingColumn = king.getPositionColumn();
        int rookColumn = rook.getPositionColumn();

        int colDiff = Math.abs(kingColumn - rookColumn);

        // Verifica se o roque é grande ou pequeno.
        boolean isCheck = false;
        if (colDiff == 4) {
            // Move o rei e verifica se está em xeque.
            board[kingRow][kingColumn] = null;
            board[kingRow][kingColumn - 2] = king;
            king.setPosition(kingRow, kingColumn - 2);

            if (CheckValidation.isCheck(board, currentPlayer, opponent)) {
                isCheck = true;

            }

            // Desfaz o movimento.
            board[kingRow][kingColumn] = king;
            board[kingRow][kingColumn - 2] = null;
            king.setPosition(kingRow, kingColumn);
        } else {
            // Move o rei e verifica se está em xeque.
            board[kingRow][kingColumn] = null;
            board[kingRow][kingColumn + 2] = king;
            king.setPosition(kingRow, kingColumn + 2);

            if (CheckValidation.isCheck(board, currentPlayer, opponent)) {
                isCheck = true;
            }

            // Desfaz o movimento.
            board[kingRow][kingColumn + 2] = null;
            board[kingRow][kingColumn] = king;
            king.setPosition(kingRow, kingColumn);
        }

        return isCheck;
    }

    // Verifica se o caminho do roque está livre.
    private static void validatePathClear(Piece[][] board, int row, int start, int end, Player opponent)
            throws InvalidMoveException {
        // Verifica se o movimento é para a esquerda ou para a direita.
        int step = (start < end) ? 1 : -1;
        int current = start + step;

        // Percorre o caminho entre o rei e a torre.
        while (current != end) {
            // Verifica se existe alguma peça entre o rei e a torre.
            if (board[row][current] != null) {
                throw new InvalidMoveException("Movimento inválido, existem peças entre o rei e a torre!");
            }

            // Verifica se a casa está sendo atacada por alguma peça do oponente.
            for (Piece piece : opponent.getPieces()) {
                int opponentRow = piece.getPositionRow();
                int opponentColumn = piece.getPositionColumn();

                if (piece.validateMove(board, opponentRow, opponentColumn, row, current)) {
                    throw new InvalidMoveException(
                            "Movimento inválido, existem casas sendo atacadas entre o rei e a torre!");
                }
            }

            current += step;
        }
    }

}
