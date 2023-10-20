package board;

import game.Player;
import pieces.*;

public class ChessBoard {
    private Piece[][] board;

    public ChessBoard() {
        board = new Piece[8][8];

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                board[i][j] = null;
            }
        }

        board[0][0] = new Rook('W', 0, 0);
        board[0][7] = new Hugo('W', 0, 7);

        board[7][0] = new Ery('B', 7, 0);
        board[7][7] = new Gui('B', 7, 7);

        board[0][1] = new Knight('W', 0, 1);
        board[0][6] = new Knight('W', 0, 6);

        board[7][1] = new Knight('B', 7, 1);
        board[7][6] = new Knight('B', 7, 6);

        board[0][2] = new Bishop('W', 0, 2);
        board[0][5] = new Bishop('W', 0, 5);

        board[7][2] = new Bishop('B', 7, 2);
        board[7][5] = new Bishop('B', 7, 5);

        board[0][3] = new Queen('W', 0, 3);
        board[7][3] = new Queen('B', 7, 3);

        board[0][4] = new King('W', 0, 4);
        board[7][4] = new King('B', 7, 4);

        for (int i = 0; i < 8; i++) {
            board[1][i] = new Pawn('W', 1, i);
            board[6][i] = new Pawn('B', 6, i);
        }
    }

    public Piece[][] getBoard() {
        return board;
    }

    public void assignPiecesToPlayers(Player playerWhite, Player playerBlack) {
        for (int i = 0; i < 8; i++) {
            if (board[0][i] != null)
                playerWhite.addPiece(board[0][i]);

            if (board[1][i] != null)
                playerWhite.addPiece(board[1][i]);

            if (board[7][i] != null)
                playerBlack.addPiece(board[7][i]);

            if (board[6][i] != null)
                playerBlack.addPiece(board[6][i]);

        }
    }

}
