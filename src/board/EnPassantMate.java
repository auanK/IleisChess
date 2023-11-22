package board;

import game.Player;
import pieces.*;

// Tabuleiro de xadrez a um lance do mate com en passant.
public class EnPassantMate extends Board {

    // Construtor da classe.
    public EnPassantMate() {
        board[0][0] = new Rook('W', 'B', 0, 0);

        board[0][4] = new King('W', 'B', 0, 4);

        board[0][7] = new Rook('W', 'W', 0, 7);

        board[1][0] = new Pawn('W', 'W', 1, 0);
        board[1][1] = new Pawn('W', 'B', 1, 1);
        board[1][2] = new Pawn('W', 'W', 1, 2);

        board[1][5] = new Pawn('W', 'B', 1, 5);
        board[1][6] = new Pawn('W', 'W', 1, 6);

        board[2][2] = new Knight('W', 'B', 2, 2);

        board[2][6] = new Queen('W', 'B', 2, 6);

        board[3][5] = new Bishop('W', 'B', 3, 5);

        board[4][3] = new Pawn('B', 'W', 4, 3);
        board[4][4] = new Pawn('W', 'B', 4, 4);
        board[4][5] = new Pawn('B', 'W', 4, 5);

        // Peão que será capturado.
        board[6][6] = new Pawn('B', 'B', 6, 6);

        board[4][7] = new Pawn('W', 'W', 4, 7);

        board[5][2] = new Knight('B', 'W', 5, 2);
        board[5][4] = new Knight('W', 'W', 5, 4);
        board[5][7] = new King('B', 'B', 5, 7);

        board[6][0] = new Pawn('B', 'B', 6, 0);
        board[6][1] = new Pawn('B', 'W', 6, 1);
        board[6][3] = new Knight('B', 'B', 6, 3);
        board[6][4] = new Bishop('B', 'B', 6, 3);

        board[7][0] = new Rook('B', 'W', 7, 0);
        board[7][2] = new Bishop('B', 'W', 7, 2);
        board[7][4] = new Queen('B', 'W', 7, 3);
        board[7][5] = new Rook('B', 'B', 7, 5);
    }

    // Atribui as peças aos jogadores.
    public void assignPiecesToPlayers(Player playerWhite, Player playerBlack) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece piece = board[i][j];
                if (piece != null) {
                    if (piece.getColor() == 'W') {
                        playerWhite.addPiece(piece);
                    } else {
                        playerBlack.addPiece(piece);
                    }
                }
            }
        }
    }

}
