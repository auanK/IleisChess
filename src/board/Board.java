package board;

import pieces.Piece;

// Interface que representa o tabuleiro.
public abstract class Board {
    protected Piece[][] board; // Matriz de peças.

    // Construtor da classe.
    public Board() {
        // Inicializa o tabuleiro.
        board = new Piece[8][8];

        // Preenche a matriz com peças nulas.
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                board[i][j] = null;
            }
        }
    }

    // Cria uma string que representa o tabuleiro. (Usada para empate por tripla repetição)
    public static String getKey(Piece[][] board) {
        StringBuilder key = new StringBuilder();

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j] == null) {
                    key.append("0");
                } else {
                    Piece piece = board[i][j];
                    key.append(piece.getLabel());
                    if (piece.getColor() == 'W') {
                        key.append("W");
                    } else {
                        key.append("B");
                    }
                }
            }
        }

        return key.toString();
    }

    // Retorna a matriz de peças.
    public Piece[][] getBoard() {
        return board;
    }

}
