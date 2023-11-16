package board;

import game.Player;
import pieces.*;

// Classe usada para testar o empate por material insuficiente.
public class BoardDrawIM {
    private Piece[][] board; // Matriz de peças.

    public BoardDrawIM() {
        // Cria a matriz de peças.
        board = new Piece[8][8];

        // Preenche a matriz com peças nulas.
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                board[i][j] = null;
            }
        }
        
    }

    // Retorna a matriz de peças.
    public Piece[][] getBoard() {
        return board;
    }

    // Distribui as peças para os jogadores.
    public void assignPiecesToPlayers(Player playerWhite, Player playerBlack) {
      
    }
}
