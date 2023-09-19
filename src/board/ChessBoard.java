package board;

import pieces.Piece;
import pieces.Queen;
import game.Player;
import pieces.Bishop;
import pieces.King;
import pieces.Knight;
import pieces.Pawn;
import pieces.Rook;

/**
 * Classe que representa um tabuleiro de xadrez padrão e suas peças.
 */
public class ChessBoard {
    // Atributo que representa o tabuleiro de xadrez
    private Piece[][] board;

    /**
     * Construtor que inicializa o tabuleiro de xadrez e configura as peças.
     */
    public ChessBoard() {
        // Cria uma matriz de objetos do tipo Piece para representar o tabuleiro
        board = new Piece[8][8];

        // Preenche as posições sem peça com null para indicar a ausência de peça
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                board[i][j] = null;
            }
        }

        // Configura as peças no tabuleiro

        // Configura as torres
        board[0][0] = new Rook('W', 0, 0);
        board[0][7] = new Rook('W', 0, 7);

        board[7][0] = new Rook('B', 7, 0);
        board[7][7] = new Rook('B', 7, 7);

        // Configura os cavalos
        board[0][1] = new Knight('W', 0, 1);
        board[0][6] = new Knight('W', 0, 6);

        board[7][1] = new Knight('B', 7, 1);
        board[7][6] = new Knight('B', 7, 6);

        // Configura os bispos
        board[0][2] = new Bishop('W', 0, 2);
        board[0][5] = new Bishop('W', 0, 5);

        board[7][2] = new Bishop('B', 7, 2);
        board[7][5] = new Bishop('B', 7, 5);

        // Configura a rainha
        board[0][3] = new Queen('W', 0, 3);
        board[7][3] = new Queen('B', 7, 3);

        // Configura o rei
        board[0][4] = new King('W', 0, 4);
        board[7][4] = new King('B', 7, 4);

        // Configura os peões
        for (int i = 0; i < 8; i++) {
            board[1][i] = new Pawn('W', 1, i);
            board[6][i] = new Pawn('B', 6, i);
        }
    }

    /**
     * Método para obter o estado atual do tabuleiro.
     * 
     * @return Uma matriz representando o tabuleiro de xadrez.
     */
    public Piece[][] getBoard() {
        return board;
    }

    /**
     * Método para associar as peças aos jogadores.
     * 
     * @param playerWhite Jogador branco.
     * @param playerBlack Jogador preto.
     */
    public void assignPiecesToPlayers(Player playerWhite, Player playerBlack) {
        for (int i = 0; i < 8; i++) {
            playerWhite.addPiece(board[0][i]);
            playerWhite.addPiece(board[1][i]);
            playerBlack.addPiece(board[7][i]);
            playerBlack.addPiece(board[6][i]);
        }
    }
}
