package logic;

import game.Player;
import pieces.Piece;
import specialmoves.Castling;

public class ChessMove {
    // Verifica se a posição de origem é válida.
    public static void isValidSource(Piece[][] board, int[] coordinates, Player currentPlayer, Player opponent)
            throws InvalidMoveException {
        int sourceRow = coordinates[0];
        int sourceColumn = coordinates[1];

        // Verifica se a posição está dentro do tabuleiro.
        if (sourceRow < 0 || sourceRow > 7 || sourceColumn < 0 || sourceColumn > 7) {
            throw new InvalidMoveException("Movimento inválido, posição fora do tabuleiro!");
        }

        Piece sourcePiece = board[sourceRow][sourceColumn];

        // Verifica se há uma peça na posição de origem.
        if (sourcePiece == null) {
            throw new InvalidMoveException("Movimento inválido, não há peça na posição de origem!");
        }

        // Verifica se a peça de origem pertence ao jogador atual.
        if (!currentPlayer.getPieces().contains(sourcePiece)) {
            throw new InvalidMoveException("Movimento inválido, a peça selecionada não é sua!");
        }
    }

    // Verifica se o movimento é válido.
    public static void validateMove(Piece[][] board, int[] coordinates, Player currentPlayer, Player opponent)
            throws InvalidMoveException {

        // Verifica se a posição de origem é válida.
        try {
            isValidSource(board, coordinates, currentPlayer, opponent);
        } catch (InvalidMoveException e) {
            throw e;
        }

        int sourceRow = coordinates[0];
        int sourceColumn = coordinates[1];
        int destinationRow = coordinates[2];
        int destinationColumn = coordinates[3];

        // Verifica se a posição de destino está dentro do tabuleiro.
        if (destinationRow < 0 || destinationRow > 7 || destinationColumn < 0 || destinationColumn > 7) {
            throw new InvalidMoveException("Movimento inválido, posição fora do tabuleiro!");
        }

        // Verifica se a posição de destino é igual a de origem.
        if (sourceRow == destinationRow && sourceColumn == destinationColumn) {
            throw new InvalidMoveException("Movimento inválido, posição de origem e destino são iguais!");
        }

        Piece sourcePiece = board[sourceRow][sourceColumn];
        Piece destinationPiece = board[destinationRow][destinationColumn];

        Castling.validateCastling(board, coordinates, currentPlayer, opponent);

        // Verifica se a posição de destino já possui uma peça do jogador atual.
        if (currentPlayer.getPieces().contains(board[destinationRow][destinationColumn])) {
            throw new InvalidMoveException("Movimento inválido, posição de destino já possui uma peça sua!");
        }

        // Verifica se a peça de origem pode se mover para a posição de destino.
        if (!sourcePiece.validateMove(board, sourceRow, sourceColumn, destinationRow, destinationColumn)) {
            throw new InvalidMoveException("Movimento inválido, peça não pode se mover para essa posição!");
        }

        // Simula o movimento e verifica se o rei do jogador atual ficaria em xeque, em
        // seguida desfaz o movimento.
        simulateMove(board, coordinates, currentPlayer, opponent, sourcePiece, destinationPiece);
        boolean isCheck = CheckValidation.isCheck(board, currentPlayer, opponent);
        undoMove(board, coordinates, currentPlayer, opponent, sourcePiece, destinationPiece);

        if (isCheck) {
            throw new InvalidMoveException("Movimento inválido, o rei está/ficaria em xeque!");
        }
    }

    // Move a peça no tabuleiro.
    public static void movePiece(Piece[][] board, int[] coordinates, Player currentPlayer, Player opponent)
            throws InvalidMoveException {
        // Verifica se o movimento é válido
        try {
            validateMove(board, coordinates, currentPlayer, opponent);
        } catch (InvalidMoveException e) {
            if (e.getMessage().equals("R")) {
                return;
            }
            throw e;
        }

        int sourceRow = coordinates[0];
        int sourceColumn = coordinates[1];
        int destinationRow = coordinates[2];
        int destinationColumn = coordinates[3];

        // Salva a peça de origem e seta sua nova posição
        Piece sourcePiece = board[sourceRow][sourceColumn];
        sourcePiece.setPosition(destinationRow, destinationColumn);

        // Se existir uma peça na posição de destino, remove a peça do jogador
        // adversário e adiciona as peças capturadas do jogador atual.
        Piece destinationPiece = board[destinationRow][destinationColumn];
        if (destinationPiece != null) {
            opponent.removePiece(destinationPiece);
            currentPlayer.addCapturedPiece(destinationPiece);
            destinationPiece.setPosition(-1, -1); // Posição inválida
        }

        // Move a peça no tabuleiro
        board[destinationRow][destinationColumn] = sourcePiece;
        board[sourceRow][sourceColumn] = null;
        sourcePiece.setMoved(true);

    }

    // Simula o movimento da peça no tabuleiro.
    public static void simulateMove(Piece[][] board, int[] coordinates, Player currentPlayer, Player opponent,
            Piece sourcePiece, Piece destinationPiece) {
        int sourceRow = coordinates[0];
        int sourceColumn = coordinates[1];
        int destinationRow = coordinates[2];
        int destinationColumn = coordinates[3];

        // Remove a peça de destino do jogador adversário se existir.
        if (destinationPiece != null) {
            opponent.removePiece(destinationPiece);
        }

        // Move a peça no tabuleiro e seta sua nova posição.
        board[sourceRow][sourceColumn] = null;
        board[destinationRow][destinationColumn] = sourcePiece;
        sourcePiece.setPosition(destinationRow, destinationColumn);
    }

    // Desfaz um movimento simulado.
    public static void undoMove(Piece[][] board, int[] coordinates, Player currentPlayer, Player opponent,
            Piece sourcePiece, Piece destinationPiece) {
        int sourceRow = coordinates[0];
        int sourceColumn = coordinates[1];
        int destinationRow = coordinates[2];
        int destinationColumn = coordinates[3];

        // Desfaz o movimento no tabuleiro
        board[sourceRow][sourceColumn] = sourcePiece;
        sourcePiece.setPosition(sourceRow, sourceColumn);
        board[destinationRow][destinationColumn] = destinationPiece;

        // Se existir, devolve a peça capturada ao jogador adversário.
        if (destinationPiece != null) {
            destinationPiece.setPosition(destinationRow, destinationColumn);
            opponent.addPiece(destinationPiece);
        }

    }
}