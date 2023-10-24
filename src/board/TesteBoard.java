package board;

import game.Player;
import pieces.*;

public class TesteBoard {
    private Piece[][] board;

    public TesteBoard() {
        board = new Piece[8][8];

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                board[i][j] = null;
            }
        }

        board[0][0] = new King('W', 0, 0);
        board[1][0] = new Queen('W', 0, 1);
        board[7][7] = new King('B', 7, 7);
        board[7][0] = new Queen('B', 7, 0);
        board[7][1] = new Queen('B', 7, 1);
        board[1][7] = new Pawn('B', 1, 7);

    }

    public Piece[][] getBoard() {
        return board;
    }

    public void assignPiecesToPlayers(Player playerWhite, Player playerBlack) {
        playerWhite.addPiece(board[0][0]);
        playerWhite.addPiece(board[1][0]);
        playerBlack.addPiece(board[7][7]);
        playerBlack.addPiece(board[7][0]);
        playerBlack.addPiece(board[7][1]);
        playerBlack.addPiece(board[1][7]);
    }

}
