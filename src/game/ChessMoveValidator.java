package game;

import pieces.Piece;

public class ChessMoveValidator {
    public static String isValidSource(Piece[][] board, int[] coordinates, Player currentPlayer,
            Player opponent) {
        if (coordinates[0] < 0 || coordinates[0] > 7 || coordinates[1] < 0 || coordinates[1] > 7) {
            return "Movimento inválido, posição fora do tabuleiro";
        }

        if (board[coordinates[0]][coordinates[1]] == null) {
            return "Movimento inválido, não há peça na posição de origem";
        }

        if (!currentPlayer.getPieces().contains(board[coordinates[0]][coordinates[1]])) {
            return "Movimento inválido, a peça selecionada não é sua";
        }

        return null;
    }

    public static String validateMove(Piece[][] board, int[] coordinates, Player currentPlayer, Player opponent) {
        String isValidSource = isValidSource(board, coordinates, currentPlayer, opponent);
        if (isValidSource != null) {
            return isValidSource;
        }
        int sourceRow = coordinates[0];
        int sourceColumn = coordinates[1];
        int destinationRow = coordinates[2];
        int destinationColumn = coordinates[3];

        if (destinationRow < 0 || destinationRow > 7 || destinationColumn < 0 || destinationColumn > 7) {
            return "Movimento inválido, posição fora do tabuleiro";
        }

        if (sourceRow == destinationRow && sourceColumn == destinationColumn) {
            return "Movimento inválido, posição de origem e destino são iguais";
        }

        if (!board[sourceRow][sourceColumn].validateMove(board, sourceRow, sourceColumn, destinationRow,
                destinationColumn, currentPlayer)) {
            return "Movimento inválido, peça não pode se mover para essa posição";
        }

        return null;
    }

    public static boolean movePiece(Piece[][] board, int[] coordinates, Player currentPlayer, Player opponent) {
        String errorMessage = validateMove(board, coordinates, currentPlayer, opponent);

        if (errorMessage != null) {
            System.out.println(errorMessage);
            return false;
        }

        int sourceRow = coordinates[0];
        int sourceColumn = coordinates[1];
        int destinationRow = coordinates[2];
        int destinationColumn = coordinates[3];

        board[sourceRow][sourceColumn].setPosition(destinationRow, destinationColumn);
        if (board[destinationRow][destinationColumn] != null) {
            opponent.removePiece(board[destinationRow][destinationColumn]);
            currentPlayer.addCapturedPiece(board[destinationRow][destinationColumn]);
            board[destinationRow][destinationColumn].setPosition(-1, -1);
        }

        board[destinationRow][destinationColumn] = board[sourceRow][sourceColumn];
        board[sourceRow][sourceColumn] = null;

        return true;
    }
}