package logic;

import game.Player;
import pieces.Piece;

// Classe que verifica se o jogo está empatado.
public class Drawn {
    // Verifica se o jogo ocorreu um empasse.
    public static boolean isStalemate(Piece[][] board, Player currentPlayer, Player opponent) {
        // Verifica se o jogador atual está em check.
        if (currentPlayer.isCheck()) {
            return false;
        }

        // Verifica se o jogador atual tem algum movimento válido.
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
