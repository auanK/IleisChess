package game;

import board.ChessBoard;
import pieces.Piece;
import board.ChessUI;
import board.TesteBoard;
import logic.CheckValidation;
import logic.ChessMove;
import logic.InvalidMoveException;

public class Program {
    private static Player playerWhite = new Player("White");
    private static Player playerBlack = new Player("Black");

    public static void main(String[] args) throws InvalidMoveException {
        // ChessBoard chessBoard = new ChessBoard();
        TesteBoard chessBoard = new TesteBoard();
        Piece[][] board = chessBoard.getBoard();
        chessBoard.assignPiecesToPlayers(playerWhite, playerBlack);

        Player currentPlayer = playerWhite;
        Player opponent = playerBlack;

        while (true) {
            ChessUI.printBoard(board, playerWhite, playerBlack);

            if (CheckValidation.isCheckMate(board, currentPlayer, opponent)) {
                System.out.println();
                System.out.println(currentPlayer.getName() + " em xeque-mate!");
                System.out.println();
                break;
            }

            if (CheckValidation.isCheck(board, currentPlayer, opponent)) {
                System.out.println(currentPlayer.getName() + " em xeque!");
            }

            System.out.println(currentPlayer.getName() + ", é sua vez. Digite o movimento: ");

            int[] coordinates = UserInput.inputCoordinates(board, currentPlayer, opponent, playerWhite, playerBlack);
            if (coordinates == null) {
                continue;
            }

            try {
                ChessMove.movePiece(board, coordinates, currentPlayer, opponent);

                int destinationRow = coordinates[2];
                int destinationColumn = coordinates[3];
                if (ChessBoard.isPromotionSquare(board[destinationRow][destinationColumn])) {
                    System.out.println("Escolha a peça para promoção (Q, R, B, N): ");
                    currentPlayer.removePiece(board[destinationRow][destinationColumn]);
                    String promotionPiece = UserInput.inputPromotion(board, board[destinationRow][destinationColumn]);
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
        System.out.println("O player " + currentPlayer.getName() + " é o vencedor!");
    }
}
