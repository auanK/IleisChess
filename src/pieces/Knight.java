package pieces;

import game.Player;

// Classe que representa a peça Cavalo.
public class Knight extends Piece {
    // Construtor da classe.
    public Knight(char cor, int positionRow, int positionColumn) {
        super('N', cor, positionRow, positionColumn);
    }

    /**
     * Método que valida o movimento do Cavalo. O Cavalo pode se mover em L
     * (duas casas em uma direção e uma casa em uma direção perpendicular).
     */
    public boolean validateMove(Piece[][] board, int sourceRow, int sourceColumn, int destinationRow,
            int destinationColumn, Player currentPlayer) {
        // Calcula a diferença entre as linhas e colunas da posição de origem e da
        // posição de destino.
        int rowDiff = Math.abs(destinationRow - sourceRow);
        int colDiff = Math.abs(destinationColumn - sourceColumn);

        // Verifica se o cavalo está se movendo em L.
        return (rowDiff == 2 && colDiff == 1) || (rowDiff == 1 && colDiff == 2);
    }

}
