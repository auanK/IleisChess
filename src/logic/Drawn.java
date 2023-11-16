package logic;

import game.Player;
import pieces.Piece;

// Classe que verifica se o jogo está empatado.
public class Drawn {
    // Verifica se ocorreu um empasse.
    public static boolean isStalemate(Piece[][] board, Player currentPlayer, Player opponent) {
        // Se o rei está em xeque, não há empasse.
        if (currentPlayer.isCheck()) {
            return false;
        }

        // Verifica se alguma peça do jogador atual pode se mover para alguma posição
        return !MoveValidator.hasValidMove(board, currentPlayer, opponent);
    }
}