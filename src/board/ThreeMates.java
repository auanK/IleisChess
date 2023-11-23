package board;

import game.Player;
import pieces.*;

// Tabuleiro com três opções de mate.
public class ThreeMates extends Board {
    public ThreeMates() {
        super();

        board[0][2] = new King('B', 'B', 0, 2);
        board[0][4] = new King('W', 'B', 0, 4);
        board[0][7] = new Rook('W', 'W', 0, 7);

        board[1][0] = new Queen('W', 'W', 1, 0);
        board[1][4] = new Pawn('W', 'W', 1, 4);
        board[1][5] = new Pawn('W', 'B', 1, 5);

        board[4][5] = new Pawn('B', 'W', 4, 5);
        board[4][5].addMove();
        board[4][6] = new Pawn('W', 'B', 4, 6);

        board[5][7] = new Bishop('W', 'B', 5, 7);

        board[6][2] = new Pawn('W', 'B', 6, 2); 

        board[6][7] = new Bishop('W', 'W', 7, 6);
    }

    // Atribui as peças aos jogadores.
    public void assignPiecesToPlayers(Player playerWhite, Player playerBlack) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j] == null) {
                    continue;
                }
                if (board[i][j].getColor() == 'W') {
                    playerWhite.addPiece(board[i][j]);
                } else {
                    playerBlack.addPiece(board[i][j]);
                }
            }
        }
    }
}
