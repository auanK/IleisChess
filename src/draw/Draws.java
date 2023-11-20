package draw;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import game.ChessLog;
import game.Player;
import logic.MoveValidator;
import pieces.Piece;

// Classe que implementa os empates.
public class Draws {
    // Verifica se ocorreu empate por afogamento.
    public static boolean isStalemate(Piece[][] board, Player currentPlayer, Player opponent) {
        // Se o rei está em xeque, não há empate.
        if (currentPlayer.isCheck()) {
            return false;
        }

        // Verifica se alguma peça do jogador atual pode se mover para alguma posição
        return !MoveValidator.hasValidMove(board, currentPlayer, opponent);
    }

    // Empate por insuficiência de material.
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

    // Empate por tripla repetição de posições.
    public static boolean isThreefoldRepetition(ArrayList<String> positions) {
        // Se não houver pelo menos 6 posições, não há empate.
        if (positions.size() < 6) {
            return false;
        }

        // Percorre a lista de posições.
        for (int i = 0; i < positions.size(); i++) {
            // Posição atual.
            String currentPosition = positions.get(i);
            int repetitions = 1;

            // Conta quantas vezes a posição atual se repete.
            for (int j = i + 1; j < positions.size(); j++) {
                if (currentPosition.equals(positions.get(j))) {
                    repetitions++;
                }
            }

            // Se a posição atual se repetiu 3 vezes, há empate.
            if (repetitions >= 3) {
                return true;
            }

        }

        // Se nenhuma posição se repetiu 3 vezes, não há empate.
        return false;
    }

    // Empate por 50 movimentos sem captura ou movimento de peão.
    public static boolean isFiftyMoveRule(ChessLog log) {
        // Se não houver pelo menos 100 movimentos, não há empate.
        if (log.getLog().size() < 100) {
            return false;
        }

        // Percorre os últimos 100 movimentos.
        for (int i = log.getLog().size() - 1; i >= log.getLog().size() - 100; i--) {
            // Movimento atual.
            String currentMove = log.getLog().get(i);

            // Se o movimento atual for uma captura ou movimento de peão, não há empate.
            if (currentMove.contains("x") || currentMove.contains("P")) {
                return false;
            }
        }

        // Se nenhum movimento for uma captura ou movimento de peão, há empate.
        return true;
    }
}
