package pieces;

// Classe que representa a peça Rei.
public class King extends Piece {
    // Construtor da classe.
    public King(char color, char colorSquare, int positionRow, int positionColumn) {
        super('K', color, colorSquare, positionRow, positionColumn);
    }

    /*
     * Método que valida o movimento do Rei. O Rei pode se mover uma casa em
     * qualquer direção.
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

        // Verifica se o rei está se movendo mais de uma casa por vez.
        if (rowDiff > 1 || colDiff > 1) {
            return false;
        }

        // Movimento válido.
        return true;
    }

}
