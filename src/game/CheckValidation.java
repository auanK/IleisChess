package game;

import pieces.King;
import pieces.Piece;

/**
 * Classe que verifica se um jogador está em check (xeque) no jogo de xadrez.
 */
public class CheckValidation {

    /**
     * Verifica se o jogador atual está em check (xeque).
     *
     * @param board         O tabuleiro de jogo representado como uma matriz de
     *                      peças.
     * @param currentPlayer O jogador atual que está sendo verificado.
     * @param opponent      O jogador adversário.
     * @return true se o jogador estiver em check, caso contrário, false.
     */

    public static boolean isCheck(Piece[][] board, Player currentPlayer, Player opponent) {
        // Percorre as peças do jogador atual até encontrar o rei, quando encontrar
        // armazena a posição do rei nas variáveis kingRow e kingColumn
        int kingRow = 0;
        int kingColumn = 0;
        for (Piece piece : currentPlayer.getPieces()) {
            if (piece instanceof King) {
                kingRow = piece.getPositionRow();
                kingColumn = piece.getPositionColumn();
                break;
            }
        }

        // Percorre as peças do jogador adversário e verifica se alguma delas pode capturar o rei
        for (Piece piece : opponent.getPieces()) {
            // Verifica se a peça do adversário pode fazer um movimento válido para a posição do rei
            if (piece.validateMove(board, piece.getPositionRow(), piece.getPositionColumn(), kingRow, kingColumn, opponent)) {
                // Se encontrarmos uma peça que pode capturar o rei, o jogador está em check
                return true;
            }
        }

        // Se nenhum movimento do adversário ameaça o rei do jogador atual, ele não está em check
        return false;
    }
}
