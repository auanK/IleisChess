package pieces;

import game.Player;

public class Ery extends Piece {
    public Ery(char cor, int positionRow, int positionColumn) {
        super('E', cor, positionRow, positionColumn);
    }

    public boolean validateMove(Piece[][] board, int sourceRow, int sourceColumn, int destinationRow,
            int destinationColumn, Player currentPlayer) {
        return true;
    }
}
