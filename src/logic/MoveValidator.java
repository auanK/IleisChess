package logic;

import game.Player;
import pieces.Piece;
import pieces.King;
import specialmoves.Castling;
import specialmoves.EnPassant;
import game.PlayChess;
import game.ChessLog;

// Classe que implementa a validação de movimentos.
public class MoveValidator {
    // Verifica se a posição de origem é válida.
    public static void isValidSource(Piece[][] board, int[] coordinates, Player currentPlayer, Player opponent)
            throws Exceptions {
        int sourceRow = coordinates[0];
        int sourceColumn = coordinates[1];

        // Verifica se a posição está dentro do tabuleiro.
        if (sourceRow < 0 || sourceRow > 7 || sourceColumn < 0 || sourceColumn > 7) {
            throw new Exceptions("Movimento inválido, posição fora do tabuleiro!");
        }

        Piece sourcePiece = board[sourceRow][sourceColumn];

        // Verifica se há uma peça na posição de origem.
        if (sourcePiece == null) {
            throw new Exceptions("Movimento inválido, não há peça na posição de origem!");
        }

        // Verifica se a peça de origem pertence ao jogador atual.
        if (!currentPlayer.getPieces().contains(sourcePiece)) {
            throw new Exceptions("Movimento inválido, a peça selecionada não é sua!");
        }
    }

    // Verifica se o movimento é válido.
    public static void validateMove(Piece[][] board, int[] coordinates, Player currentPlayer, Player opponent, ChessLog log)
            throws Exceptions {

        // Verifica se a posição de origem é válida.
        try {
            isValidSource(board, coordinates, currentPlayer, opponent);
        } catch (Exceptions e) {
            throw e;
        }

        int sourceRow = coordinates[0];
        int sourceColumn = coordinates[1];
        int destinationRow = coordinates[2];
        int destinationColumn = coordinates[3];

        // Verifica se a posição de destino está dentro do tabuleiro.
        if (destinationRow < 0 || destinationRow > 7 || destinationColumn < 0 || destinationColumn > 7) {
            throw new Exceptions("Movimento inválido, posição fora do tabuleiro!");
        }

        // Verifica se a posição de destino é igual a de origem.
        if (sourceRow == destinationRow && sourceColumn == destinationColumn) {
            throw new Exceptions("Movimento inválido, posição de origem e destino são iguais!");
        }

        Piece sourcePiece = board[sourceRow][sourceColumn];
        Piece destinationPiece = board[destinationRow][destinationColumn];

        Castling.validateCastling(board, coordinates, currentPlayer, opponent);
        EnPassant.validateEnPassant(board, coordinates, currentPlayer, opponent);

        // Verifica se a posição de destino já possui uma peça do jogador atual.
        if (currentPlayer.getPieces().contains(board[destinationRow][destinationColumn])) {
            throw new Exceptions("Movimento inválido, posição de destino já possui uma peça sua!");
        }

        // Verifica se a peça de origem pode se mover para a posição de destino.
        if (!sourcePiece.validateMove(board, sourceRow, sourceColumn, destinationRow, destinationColumn)) {
            throw new Exceptions("Movimento inválido, peça não pode se mover para essa posição!");
        }

        // Simula o movimento e verifica se o rei do jogador atual ficaria em xeque, em
        // seguida desfaz o movimento.
        MoveExecutor.simulateMove(board, coordinates, currentPlayer, opponent, sourcePiece, destinationPiece);
        boolean isCheck = CheckValidation.isCheck(board, currentPlayer, opponent);
        MoveExecutor.undoMove(board, coordinates, currentPlayer, opponent, sourcePiece, destinationPiece);

        if (isCheck) {
            throw new Exceptions("Movimento inválido, o rei está/ficaria em xeque!");
        }
    }

    // Verifica se existe algum movimento válido para o jogador atual.
    public static boolean hasValidMove(Piece[][] board, Player currentPlayer, Player opponent) {
        ChessLog log = PlayChess.getLog();
        for (Piece piece : currentPlayer.getPieces()) {
            int sourceRow = piece.getPositionRow();
            int sourceColumn = piece.getPositionColumn();

            for (int destinationRow = 0; destinationRow < 8; destinationRow++) {
                for (int destinationColumn = 0; destinationColumn < 8; destinationColumn++) {
                    int coordinates[] = { sourceRow, sourceColumn, destinationRow, destinationColumn };
                    try {
                        validateMove(board, coordinates, currentPlayer, opponent, log);
                        return true;
                    } catch (Exceptions e) {
                        continue;
                    }
                }
            }
        }
        return false;
    }
    
}
