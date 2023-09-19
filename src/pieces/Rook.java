package pieces;

import game.Player;

// Classe que representa a peça "Torre"
public class Rook extends Piece {

    // Construtor da classe Rook
    public Rook(char cor, int positionRow, int positionColumn) {
        super('R', cor, positionRow, positionColumn);
    }

    // Método para criar uma cópia da peça
    public Rook copy() {
        Rook rookCopy = new Rook(getColor(), getPositionRow(), getPositionColumn());
        return rookCopy;
    }

    /**
     * Valida o movimento da Torre no tabuleiro.
     *
     * A Torre se movimenta na horizontal e vertical. Para que o movimento seja
     * válido, a diferença entre as coordenadas de origem e destino deve ser de
     * 1 na vertical e 0 na horizontal, ou 0 na vertical e 1 na horizontal. A
     * casa destino deve estar vazia ou conter uma peça adversária.
     *
     * @param board             O tabuleiro de xadrez.
     * @param sourceRow         A linha da posição de origem da Torre.
     * @param sourceColumn      A coluna da posição de origem da Torre.
     * @param destinationRow    A linha da posição de destino da Torre.
     * @param destinationColumn A coluna da posição de destino da Torre.
     * @param currentPlayer     O jogador atual.
     * @return true se o movimento for válido, caso contrário, false.
     */
    public boolean validateMove(Piece[][] board, int sourceRow, int sourceColumn, int destinationRow,
            int destinationColumn, Player currentPlayer) {
        // Calcular a diferença entre as coordenadas de origem e destino
        int rowDiff = Math.abs(destinationRow - sourceRow);
        int colDiff = Math.abs(destinationColumn - sourceColumn);

        // Verificar se o movimento é na horizontal ou vertical
        if ((rowDiff == 0 && colDiff > 0) || (rowDiff > 0 && colDiff == 0)) {
            int start;
            int end;

            // Se for um movimento horizontal
            if (rowDiff == 0) {
                // Determinar o sentido do movimento horizontal
                start = Math.min(sourceColumn, destinationColumn);
                end = Math.max(sourceColumn, destinationColumn);

                // Verificar se não há peças no caminho
                for (int i = start + 1; i < end; i++) {
                    if (board[sourceRow][i] != null) {
                        return false;
                    }
                }
                // Se for um movimento vertical
            } else {
                // Determinar o sentido do movimento vertical
                start = Math.min(sourceRow, destinationRow);
                end = Math.max(sourceRow, destinationRow);

                // Verificar se não há peças no caminho
                for (int i = start + 1; i < end; i++) {
                    if (board[i][sourceColumn] != null) {
                        return false;
                    }
                }
            }

            // Verificar se a casa de destino está vazia ou contém uma peça adversária
            if (board[destinationRow][destinationColumn] == null
                    || !currentPlayer.getPieces().contains(board[destinationRow][destinationColumn])) {
                return true;
            }
        }

        // Se não for um movimento válido para a Torre, retorne falso
        return false;
    }
}
