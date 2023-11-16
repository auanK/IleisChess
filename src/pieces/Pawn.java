package pieces;

// Classe que representa a peça Peão.
public class Pawn extends Piece {
    // Construtor da classe.
    public Pawn(char color, char colorSquare, int positionRow, int positionColumn) {
        super('P', color, colorSquare, positionRow, positionColumn);
    }

    /**
     * Método que valida o movimento do Peão. O Peão pode se mover uma casa para
     * frente, duas casas para frente (apenas na primeira jogada e cas) ou uma casa
     * na diagonal (apenas para capturar uma peça do oponente).
     */
    public boolean validateMove(Piece[][] board, int sourceRow, int sourceColumn, int destinationRow,
            int destinationColumn) {

        // Verifica se a posição de origem é igual a posição de destino.
        if (sourceRow == destinationRow && sourceColumn == destinationColumn) {
            return false;
        }

        // Calcula a diferença entre as coordenadas de origem e destino
        int rowDifference = destinationRow - sourceRow;
        int colDifference = Math.abs(destinationColumn - sourceColumn);

        // Verifica a cor do peão e define a direção do movimento
        char color = getColor();
        int moveDirection = (color == 'W') ? 1 : -1;

        // Verifica se o peão está se movendo na vertical
        if (sourceColumn == destinationColumn) {
            // Movimento de uma casa para frente
            if (rowDifference == moveDirection) {
                return true;
            }
            // Movimento de duas casas para frente (apenas na primeira jogada)
            else if (rowDifference == 2 * moveDirection && sourceRow == (color == 'W' ? 1 : 6)
                    && board[sourceRow + moveDirection][sourceColumn] == null
                    && board[destinationRow][destinationColumn] == null) {
                return true;
            }
        }
        // Verifica o movimento diagonal para capturar uma peça do oponente
        else if (colDifference == 1 && rowDifference == moveDirection
                && board[destinationRow][destinationColumn] != null
                && board[destinationRow][destinationColumn].getColor() != color) {
            return true;
        }

        return false;
    }
}
