package board;

import game.Player;
import pieces.*;

// Classe usada apenas para testes

public class BoardTeste {
    private Piece[][] board;

    public BoardTeste() {
        board = new Piece[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                board[i][j] = null;
            }
        }

        board[0][0] = new King('B', 0, 0);
        board[0][2] = new King('W', 0, 7);
        board[3][1] = new Queen('W', 3, 2);
        // board[4][5] = new Bishop('B', 4, 5);
    }

    public Piece[][] getBoard() {
        return board;
    }

    public void assignPiecesToPlayers(Player playerWhite, Player playerBlack) {
        playerBlack.addPiece(board[0][0]);
        playerWhite.addPiece(board[0][2]);
        playerWhite.addPiece(board[3][1]);
        // playerBlack.addPiece(board[4][5]);
    }

}
