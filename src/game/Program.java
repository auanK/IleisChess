package game;

import board.ChessBoard;
import board.TesteBoard;
import board.ChessUI;
import pieces.Piece;
import logic.CheckValidation;
import logic.ChessMove;
import logic.InvalidMoveException;

public class Program {
    private static Player playerWhite = new Player("White");
    private static Player playerBlack = new Player("Black");

    public static void main(String[] args) throws InvalidMoveException {
        //ChessBoard chessBoard = new ChessBoard();
        TesteBoard chessBoard = new TesteBoard();
        Piece[][] board = chessBoard.getBoard();
        chessBoard.assignPiecesToPlayers(playerWhite, playerBlack);

        Player currentPlayer = playerWhite;
        Player opponent = playerBlack;

        while (true) {
            ChessUI.printBoard(board, playerWhite, playerBlack);

            if (CheckValidation.isCheckMate(board, currentPlayer, opponent)) {
                System.out.println(currentPlayer.getName() + " em xeque-mate!");
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
            } catch (InvalidMoveException e) {
                System.out.println(e.getMessage());
                continue;
            }

            Player temp = currentPlayer;
            currentPlayer = opponent;
            opponent = temp;

        }
    }
}
