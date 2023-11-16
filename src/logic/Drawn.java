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

        // Verifica se alguma peça do jogador atual pode se mover para alguma posição
        return !MoveValidator.hasValidMove(board, currentPlayer, opponent);
    }
}
