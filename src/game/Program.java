package game;

import board.ChessBoard;
import pieces.Piece;
import board.ChessUI;
import logic.CheckValidation;
import logic.ChessMove;
import logic.InvalidMoveException;
import specialmoves.Promotion;

// Classe principal do jogo.
public class Program {
    private static Player playerWhite = new Player("White");
    private static Player playerBlack = new Player("Black");

    public static void main(String[] args) throws InvalidMoveException {
        // Cria o tabuleiro e distribui as peças.
        ChessBoard chessBoard = new ChessBoard();
        Piece[][] board = chessBoard.getBoard();
        chessBoard.assignPiecesToPlayers(playerWhite, playerBlack);

        // Inicia o jogo.
        Player currentPlayer = playerWhite;
        Player opponent = playerBlack;

        while (true) {
            // Imprime o tabuleiro.
            ChessUI.printBoard(board, playerWhite, playerBlack);

            // Verifica se o jogador atual está em xeque-mate.
            if (CheckValidation.isCheckMate(board, currentPlayer, opponent)) {
                System.out.println();
                System.out.println(currentPlayer.getName() + " em xeque-mate!");
                System.out.println();
                break;
            }

            // Verifica se o jogador atual está em xeque.
            if (CheckValidation.isCheck(board, currentPlayer, opponent)) {
                System.out.println(currentPlayer.getName() + " em xeque!");
            }

            System.out.println(currentPlayer.getName() + ", é sua vez. Digite o movimento: ");

            // Lê as coordenadas digitadas pelo usuário.
            int[] coordinates = UserInput.inputCoordinates(board, currentPlayer, opponent, playerWhite, playerBlack);
            if (coordinates == null) {
                continue;
            }

            // Tenta mover a peça, caso não seja possível, imprime a mensagem de erro.
            try {
                // Move a peça.
                ChessMove.movePiece(board, coordinates, currentPlayer, opponent);

                // Verifica se o movimento é uma promoção.
                int destinationRow = coordinates[2];
                int destinationColumn = coordinates[3];
                if (Promotion.isPromotionSquare(board[destinationRow][destinationColumn])) {
                    System.out.println("Escolha a peça para promoção (Q, R, B, N): ");
                    currentPlayer.removePiece(board[destinationRow][destinationColumn]);
                    String promotionPiece = Promotion.inputPromotion(board, board[destinationRow][destinationColumn]);
                    currentPlayer.addPiece(board[destinationRow][destinationColumn]);
                    System.out.println("Peça escolhida: " + promotionPiece);
                }

            } catch (InvalidMoveException e) {
                System.out.println(e.getMessage());
                continue;
            }

            // Troca os jogadores
            Player temp = currentPlayer;
            currentPlayer = opponent;
            opponent = temp;
        }

        // Fim do jogo
        System.out.println("O player " + opponent.getName() + " é o vencedor!");
    }
}
