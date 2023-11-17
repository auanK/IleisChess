package board;

import game.Player;
import pieces.Bishop;
import pieces.King;
import pieces.Knight;
import pieces.Pawn;
import pieces.Piece;
import pieces.Queen;
import pieces.Rook;

// Classe que implementa o tabuleiro de xadrez padrão.
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

        // Coloca as torres.
        board[0][0] = new Rook('W', 'B', 0, 0);
        board[0][7] = new Rook('W', 'W', 0, 7);

        board[7][0] = new Rook('B', 'W', 7, 0);
        board[7][7] = new Rook('B', 'B', 7, 7);

        // Coloca os cavalos.
        board[0][1] = new Knight('W', 'W', 0, 1);
        board[0][6] = new Knight('W', 'B', 0, 6);

        board[7][1] = new Knight('B', 'B', 7, 1);
        board[7][6] = new Knight('B', 'W', 7, 6);

        // Coloca os bispos.
        board[0][2] = new Bishop('W', 'B', 0, 2);
        board[0][5] = new Bishop('W', 'W', 0, 5);

        board[7][2] = new Bishop('B', 'W', 7, 2);
        board[7][5] = new Bishop('B', 'B', 7, 5);

        // Coloca as rainhas.
        board[0][3] = new Queen('W', 'W', 0, 3);
        board[7][3] = new Queen('B', 'B', 7, 3);

        // Coloca os reis.
        board[0][4] = new King('W', 'B', 0, 4);
        board[7][4] = new King('B', 'W', 7, 4);

        // Coloca os peões.
        char colorWhiteSquare = 'W';
        char colorBlackSquare = 'B';
        for (int i = 0; i < 8; i++) {
            board[1][i] = new Pawn('W', colorWhiteSquare, 1, i);
            board[6][i] = new Pawn('B', colorBlackSquare, 6, i);
            colorWhiteSquare = (colorWhiteSquare == 'W') ? 'B' : 'W';
            colorBlackSquare = (colorBlackSquare == 'W') ? 'B' : 'W';
        }

    }

    // Retorna a matriz de peças.
    public Piece[][] getBoard() {
        return board;
    }

    // Distribui as peças para os jogadores.
    public void assignPiecesToPlayers(Player playerWhite, Player playerBlack) {
        for (int i = 0; i < 8; i++) {
            playerWhite.addPiece(board[0][i]);
            playerWhite.addPiece(board[1][i]);
            playerBlack.addPiece(board[7][i]);
            playerBlack.addPiece(board[6][i]);
        }
    }
}
