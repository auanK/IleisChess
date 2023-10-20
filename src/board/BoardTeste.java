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
        // board[7][1] = new Queen('W', 7, 1);
        // board[1][5] = new Queen('W', 1, 5);
        board[1][0] = new Hugo('B', 1, 0);
        board[7][7] = new King('W', 7, 7);
    }

    public Piece[][] getBoard() {
        return board;
    }

    public void assignPiecesToPlayers(Player playerWhite, Player playerBlack) {
        // playerWhite.addPiece(board[7][1]);
        playerWhite.addPiece(board[7][7]);
        playerBlack.addPiece(board[0][0]);
        playerBlack.addPiece(board[1][0]);
        // playerWhite.addPiece(board[1][5]);
    }

    // clona o tabuleiro
    public static Piece[][] clone(Piece board[][]) {
        Piece[][] tempBoard = new Piece[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j] != null) {
                    tempBoard[i][j] = board[i][j];
                }
            }
        }

        return tempBoard;
    }

}
