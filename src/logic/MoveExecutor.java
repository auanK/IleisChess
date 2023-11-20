package logic;

import game.ChessLog;
import game.Player;
import pieces.Piece;
import specialmoves.Castling;

// Classe que executa o movimento das peças no tabuleiro.
public class MoveExecutor {
    // Move a peça no tabuleiro.
    public static void movePiece(Piece[][] board, int[] coordinates, Player currentPlayer, Player opponent, ChessLog log)
            throws Exceptions {
        // Verifica se o movimento é válido
        try {
            MoveValidator.validateMove(board, coordinates, currentPlayer, opponent);
        } catch (Exceptions e) {
            // Se o movimento for um roque, o tratamento é diferente.
            if (e.getMessage().equals("Castling!")) {
                Castling.castling(board, coordinates, currentPlayer, opponent, log);
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

        String notation = sourcePiece.getLabel() + log.parseChessNotation(sourceRow, sourceColumn);

        // Se existir uma peça na posição de destino, remove a peça do jogador
        // adversário e adiciona as peças capturadas do jogador atual.
        Piece destinationPiece = board[destinationRow][destinationColumn];
        if (destinationPiece != null) {
            opponent.removePiece(destinationPiece);
            currentPlayer.addCapturedPiece(destinationPiece);
            destinationPiece.setPosition(-1, -1); // Posição inválida

            // Adiciona a peça capturada à notação.
            notation += "x" + destinationPiece.getLabel();
        }

        // Move a peça no tabuleiro
        board[destinationRow][destinationColumn] = sourcePiece;
        board[sourceRow][sourceColumn] = null;
        sourcePiece.setMoved(true);

        // Criar a notação do movimento e adiciona ao log.
        notation += log.parseChessNotation(destinationRow, destinationColumn);
        log.addMove(notation);

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
