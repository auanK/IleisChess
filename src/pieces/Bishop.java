package pieces;

// Classe que representa a peça Bispo.
public class Bishop extends Piece {
    // Construtor da classe.
    public Bishop(char cor, int positionRow, int positionColumn) {
        super('B', cor, positionRow, positionColumn);
    }

    /**
     * Método que valida o movimento do Bispo. O Bispo pode se mover em diagonal
     * quantas casas quiser desde que não haja nenhuma peça entre a posição de
     * origem e a posição de destino.
     */
    public boolean validateMove(Piece[][] board, int sourceRow, int sourceColumn, int destinationRow,
            int destinationColumn) {
        // Calcula a diferença entre as coordenadas de origem e destino.
        int rowDiff = Math.abs(destinationRow - sourceRow);
        int colDiff = Math.abs(destinationColumn - sourceColumn);

        // Verifica se o bispo não está se movendo em diagonal.
        if (rowDiff != colDiff) {
            return false;
        }

        // Define a direção do movimento do bispo.
        int rowStep = (destinationRow > sourceRow) ? 1 : -1;
        int colStep = (destinationColumn > sourceColumn) ? 1 : -1;

        // Define a posição da primeira casa após a posição de origem.
        int currentRow = sourceRow + rowStep;
        int currentCol = sourceColumn + colStep;

        // Verifica se há peças entre a posição de origem e a posição de destino.
        while (currentRow != destinationRow) {
            if (board[currentRow][currentCol] != null) {
                return false;
            }
            currentRow += rowStep;
            currentCol += colStep;
        }

        // Movimento válido.
        return true;
    }

}
