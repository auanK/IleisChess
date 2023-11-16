package draw;

import java.util.List;

import game.Player;
import logic.MoveValidator;
import pieces.Piece;

public class Draw {
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
        if (InsufficientMaterial.isKingVsKing(piecesCount, opponentPiecesCount)) {
            return true;
        }

        // Rei contra rei e bispo ou cavalo é empate.
        if (InsufficientMaterial.isKingVsKingAndBishopOrKnight(piecesCount, opponentPiecesCount, pieces,
                opponentPieces)) {
            return true;
        }

        // Rei e bispo contra rei e bispo com bispos da mesma cor é empate.
        if (InsufficientMaterial.isKingAndBishopVsKingAndBishop(piecesCount, opponentPiecesCount, pieces,
                opponentPieces)) {
            return true;
        }

        // Rei contra rei e dois cavalos é empate.
        if (InsufficientMaterial.isKingVsKingAndTwoKnights(piecesCount, opponentPiecesCount, pieces, opponentPieces)) {
            return true;
        }

        return false;
    }

    // Representa o tipo de empate.
    public static class DrawType {
        private DrawTypes drawType; // Tipo de empate.

        // Construtor da classe.
        public DrawType() {
            this.drawType = null;
        }

        // Define o tipo de empate.
        public void setDrawType(DrawTypes drawType) {
            this.drawType = drawType;
        }

        // Retorna o tipo de empate.
        public DrawTypes getDrawType() {
            return drawType;
        }

        public boolean isDraw() {
            return drawType != null;
        }

        // Imprime o tipo de empate.
        public void print() {
            switch (drawType) {
                case AGREEMENT:
                    System.out.println("Empate por acordo!");
                    break;
                case STALEMATE:
                    System.out.println("Empate por afogamento!");
                    break;
                case INSUFFICIENT_MATERIAL:
                    System.out.println("Empate por insuficiência de material!");
                    break;
            }
        }

    }

    // Enum que representa os tipos de empate.
    public enum DrawTypes {
        AGREEMENT,
        STALEMATE,
        INSUFFICIENT_MATERIAL,
    }
}
