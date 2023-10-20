package pieces;

import game.Player;

public class Gui extends Piece {
    public Gui(char cor, int positionRow, int positionColumn) {
        super('G', cor, positionRow, positionColumn);
    }

    public boolean validateMove(Piece[][] board, int sourceRow, int sourceColumn, int destinationRow,
            int destinationColumn, Player currentPlayer) {

        if (board[destinationRow][destinationColumn] != null) {
            return false;
        }
        return true;
    }
}
