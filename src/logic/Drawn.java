package logic;

import game.Player;
import pieces.*;
import java.util.List;

// Classe que verifica se o jogo está empatado.
public class Drawn {
    // Verifica se ocorreu empate por afogamento.
    public static boolean isStalemate(Piece[][] board, Player currentPlayer, Player opponent) {
        // Se o rei está em xeque, não há empate.
        if (currentPlayer.isCheck()) {
            return false;
        }

        // Verifica se alguma peça do jogador atual pode se mover para alguma posição
        return !MoveValidator.hasValidMove(board, currentPlayer, opponent);
    }

    // Verifica se ocorreu empate por insuficiência de material.
    public static boolean insufficientMaterial(Player currentPlayer, Player opponent) {
        List<Piece> pieces = currentPlayer.getPieces();
        List<Piece> opponentPieces = opponent.getPieces();

        int piecesCount = pieces.size();
        int opponentPiecesCount = opponentPieces.size();

        // Rei contra rei é empate.
        if (piecesCount == 1 && opponentPiecesCount == 1) {
            return true;
        }

        // Rei contra rei e (bispo ou cavalo) é empate.
        if ((piecesCount == 1 && opponentPiecesCount == 2) || (piecesCount == 2 && opponentPiecesCount == 1)) {
            if ((containsKnightOrBishop(opponentPieces) || containsKnightOrBishop(pieces))) {
                return true;
            }
        }

        // Rei e bispo contra rei e bispo é empate se os bispos estiverem na mesma cor.
        if (piecesCount == 2 && opponentPiecesCount == 2) {
            Piece bishop = getBishop(pieces);
            Piece opponentBishop = getBishop(opponentPieces);

            if (bishop == null || opponentBishop == null) {
                return false;
            }

            if (bishop.getColorSquare() == opponentBishop.getColorSquare()) {
                return true;
            }
        }

        return false;
    }

    // Verifica se a lista de peças contém um bispo ou um cavalo.
    private static boolean containsKnightOrBishop(List<Piece> pieces) {
        for (Piece piece : pieces) {
            if (piece instanceof Knight || piece instanceof Bishop) {
                return true;
            }
        }
        return false;
    }

    // Retorna o bispo da lista de peças.
    private static Bishop getBishop(List<Piece> pieces) {
        for (Piece piece : pieces) {
            if (piece instanceof Bishop) {
                return (Bishop) piece;
            }
        }
        return null;
    }

}