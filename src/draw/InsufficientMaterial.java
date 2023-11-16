package draw;

import java.util.List;

import pieces.Bishop;
import pieces.Knight;
import pieces.Piece;

// Classe que verifica se há material suficiente para um xeque-mate.
public class InsufficientMaterial {
    // Rei contra rei é empate.
    public static boolean isKingVsKing(int piecesCount, int opponentPiecesCount) {
        return piecesCount == 1 && opponentPiecesCount == 1;
    }

    // Rei contra rei e bispo ou cavalo é empate.
    public static boolean isKingVsKingAndBishopOrKnight(int piecesCount, int opponentPiecesCount,
            List<Piece> pieces, List<Piece> opponentPieces) {
        if (piecesCount == 1 && opponentPiecesCount == 2) {
            return containsKnightOrBishop(opponentPieces);
        }

        if (piecesCount == 2 && opponentPiecesCount == 1) {
            return containsKnightOrBishop(pieces);
        }

        return false;
    }

    // Rei e bispo contra rei e bispo com bispos da mesma cor é empate.
    public static boolean isKingAndBishopVsKingAndBishop(int piecesCount, int opponentPiecesCount,
            List<Piece> pieces, List<Piece> opponentPieces) {
        if (piecesCount == 2 && opponentPiecesCount == 2) {
            Piece bishop = getBishop(pieces);
            Piece opponentBishop = getBishop(opponentPieces);

            if (bishop != null && opponentBishop != null) {
                return bishop.getColor() == opponentBishop.getColor();
            }
        }

        return false;
    }

    // Rei contra rei e dois cavalos é empate.
    public static boolean isKingVsKingAndTwoKnights(int piecesCount, int opponentPiecesCount, List<Piece> pieces,
            List<Piece> opponentPieces) {
        if (piecesCount == 1 && opponentPiecesCount == 3) {
            int knightsCount = getPieceCount(opponentPieces, Knight.class);

            return knightsCount == 2;
        } 

        if (piecesCount == 3 && opponentPiecesCount == 1) {
            int knightsCount = getPieceCount(pieces, Knight.class);

            return knightsCount == 2;
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

    // Retorna um bispo da lista de peças.
    private static Bishop getBishop(List<Piece> pieces) {
        for (Piece piece : pieces) {
            if (piece instanceof Bishop) {
                return (Bishop) piece;
            }
        }
        return null;
    }

    // Retorna a quantidade de um tipo de peça na lista de peças.
    private static int getPieceCount(List<Piece> pieces, Class<?> pieceClass) {
        int count = 0;

        for (Piece piece : pieces) {
            if (piece.getClass() == pieceClass) {
                count++;
            }
        }

        return count;
    }
}
