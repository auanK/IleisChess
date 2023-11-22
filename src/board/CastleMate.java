package board;

import game.Player;
import pieces.*;

// Tabuleiro de xadrez a um lance do mate com roque.
public class CastleMate extends Board{
    public CastleMate() {
        super();

        board[0][0] = new Rook('W', 'B', 0, 0);

    }
}
