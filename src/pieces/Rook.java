package pieces;

public class Rook extends Piece {
    // Construtor da classe.
    public Rook(char color, char colorSquare, int positionRow, int positionColumn) {
        super('R', color, colorSquare, positionRow, positionColumn);
    }

    /**
     * Método que valida o movimento da Torre. A Torre pode se mover em linha reta
     * (horizontal ou vertical) quantas casas quiser, desde que não haja nenhuma
     * peça entre a posição de origem e a posição de destino.
     */
    public boolean validateMove(Piece[][] board, int sourceRow, int sourceColumn, int destinationRow,
            int destinationColumn) {

        // Verifica se a posição de origem é igual a posição de destino.
        if (sourceRow == destinationRow && sourceColumn == destinationColumn) {
            return false;
        }

        // Calcula a diferença entre as coordenadas de origem e destino.
        int rowDiff = Math.abs(destinationRow - sourceRow);
        int colDiff = Math.abs(destinationColumn - sourceColumn);

        // Verifica se a torre não está se movendo em linha reta (horizontal ou
        // vertical).
        if ((rowDiff != 0 || colDiff <= 0) && (rowDiff <= 0 || colDiff != 0)) {
            return false;
        }

        // Inicializa as variáveis que serão utilizadas para percorrer o tabuleiro.
        int start, end, current;
        int step = 1, row = 0, col = 0;

        // Define a direção do movimento da torre e a linha ou coluna que será
        // percorrida.
        if (rowDiff == 0) {
            start = Math.min(sourceColumn, destinationColumn);
            end = Math.max(sourceColumn, destinationColumn);
            row = sourceRow;
        } else {
            start = Math.min(sourceRow, destinationRow);
            end = Math.max(sourceRow, destinationRow);
            col = sourceColumn;
        }

        // Verifica se há peças entre a posição de origem e a posição de destino.
        current = start + step;
        while (current < end) {
            if (rowDiff == 0) {
                if (board[row][current] != null) {
                    return false;
                }
            } else {
                if (board[current][col] != null) {
                    return false;
                }
            }
            current += step;
        }

        // Movimento válido.
        return true;
    }

}