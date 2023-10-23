package logic;

import game.Player;
import pieces.Piece;

public class ChessMove {
    public static void isValidSource(Piece[][] board, int[] coordinates, Player currentPlayer, Player opponent)
            throws InvalidMoveException {
        if (coordinates[0] < 0 || coordinates[0] > 7 || coordinates[1] < 0 || coordinates[1] > 7) {
            throw new InvalidMoveException("Movimento inválido, posição fora do tabuleiro!");
        }

        if (board[coordinates[0]][coordinates[1]] == null) {
            throw new InvalidMoveException("Movimento inválido, não há peça na posição de origem!");
        }

        if (!currentPlayer.getPieces().contains(board[coordinates[0]][coordinates[1]])) {
            throw new InvalidMoveException("Movimento inválido, a peça selecionada não é sua!");
        }
    }

    public static void validateMove(Piece[][] board, int[] coordinates, Player currentPlayer, Player opponent)
            throws InvalidMoveException {
                
        try {
            isValidSource(board, coordinates, currentPlayer, opponent);
        } catch (InvalidMoveException e) {
            throw e;
        }

        int sourceRow = coordinates[0];
        int sourceColumn = coordinates[1];
        int destinationRow = coordinates[2];
        int destinationColumn = coordinates[3];

        if (destinationRow < 0 || destinationRow > 7 || destinationColumn < 0 || destinationColumn > 7) {
            throw new InvalidMoveException("Movimento inválido, posição fora do tabuleiro!");
        }

        if (sourceRow == destinationRow && sourceColumn == destinationColumn) {
            throw new InvalidMoveException("Movimento inválido, posição de origem e destino são iguais!");
        }

        Piece sourcePiece = board[sourceRow][sourceColumn];

        if (currentPlayer.getPieces().contains(board[destinationRow][destinationColumn])) {
            throw new InvalidMoveException("Movimento inválido, posição de destino já possui uma peça sua!");
        }

        if (!sourcePiece.validateMove(board, sourceRow, sourceColumn, destinationRow, destinationColumn,
                currentPlayer)) {
            throw new InvalidMoveException("Movimento inválido, peça não pode se mover para essa posição!");
        }

        Piece destinationPiece = null;
        if (board[destinationRow][destinationColumn] != null) {
            destinationPiece = board[destinationRow][destinationColumn];
            opponent.removePiece(destinationPiece);
        }
        board[sourceRow][sourceColumn] = null;
        board[destinationRow][destinationColumn] = sourcePiece;
        sourcePiece.setPosition(destinationRow, destinationColumn);

        if (CheckValidation.isCheck(board, currentPlayer, opponent)) {
            board[sourceRow][sourceColumn] = sourcePiece;
            sourcePiece.setPosition(sourceRow, sourceColumn);
            board[destinationRow][destinationColumn] = destinationPiece;
            if (destinationPiece != null) {
                destinationPiece.setPosition(destinationRow, destinationColumn);
                opponent.addPiece(destinationPiece);
            }
            throw new InvalidMoveException("Movimento inválido, o rei está/ficaria em xeque!");
        }

        board[sourceRow][sourceColumn] = sourcePiece;
        sourcePiece.setPosition(sourceRow, sourceColumn);
        board[destinationRow][destinationColumn] = destinationPiece;
        if (destinationPiece != null) {
            destinationPiece.setPosition(destinationRow, destinationColumn);
            opponent.addPiece(destinationPiece);
        }
        
        

    }

    public static void movePiece(Piece[][] board, int[] coordinates, Player currentPlayer, Player opponent)
            throws InvalidMoveException {
        validateMove(board, coordinates, currentPlayer, opponent);
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
    }
}
