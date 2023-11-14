package board;

import game.Player;
import pieces.*;

// Classe que cria um tabuleiro de xadrez e distribui as peças.
public class ChessBoard {
    private Piece[][] board; // Matriz de peças.

    // Construtor da classe.
    public ChessBoard() {
        // Cria a matriz de peças.
        board = new Piece[8][8];

        // Preenche a matriz com peças nulas.
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                board[i][j] = null;
            }
        }

    }

    public Piece[][] getBoard() {
        return board;
    }

    public void assignPiecesToPlayers(Player playerWhite, Player playerBlack) {

    }
}
