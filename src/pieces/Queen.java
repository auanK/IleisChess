package pieces;

import game.Player;

// Classe que representa a peça "Rainha"
public class Queen extends Piece {

    /**
     * Construtor da classe Queen.
     *
     * @param cor            A cor da peça (W para branco, B para preto).
     * @param positionRow    A linha da posição da peça no tabuleiro.
     * @param positionColumn A coluna da posição da peça no tabuleiro.
     */
    public Queen(char cor, int positionRow, int positionColumn) {
        super('Q', cor, positionRow, positionColumn); // Chama o construtor da superclasse Piece.
    }

    /**
     * Cria uma cópia da peça Rainha.
     *
     * @return Uma nova instância da peça Rainha com as mesmas características.
     */
    public Queen copy() {
        Queen copyQueen = new Queen(getColor(), getPositionRow(), getPositionColumn());
        return copyQueen;
    }

    /**
     * Valida o movimento da Rainha no tabuleiro.
     *
     * A Rainha se movimenta com uma combinação do movimento da Torre com o
     * movimento do Bispo. Para mais informações sobre o movimento da Torre e do
     * Bispo, consulte as classes correspondentes.
     *
     * @param board             O tabuleiro de xadrez.
     * @param sourceRow         A linha da posição de origem da Rainha.
     * @param sourceColumn      A coluna da posição de origem da Rainha.
     * @param destinationRow    A linha da posição de destino da Rainha.
     * @param destinationColumn A coluna da posição de destino da Rainha.
     * @param currentPlayer     O jogador atual.
     * @return true se o movimento for válido, caso contrário, false.
     */
    public boolean validateMove(Piece[][] board, int sourceRow, int sourceColumn, int destinationRow,
            int destinationColumn, Player currentPlayer) {
        // Calcular a diferença entre as coordenadas de origem e destino
        int rowDiff = Math.abs(destinationRow - sourceRow);
        int colDiff = Math.abs(destinationColumn - sourceColumn);

        // Movimento de torre
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

        // Movimento de bispo
        // Verificar se o movimento é na diagonal (mesma quantidade de casas na vertical
        // e na horizontal)
        if (rowDiff == colDiff) {
            int rowStep;
            int colStep;

            // Determinar o sentido do movimento na diagonal
            rowStep = (destinationRow > sourceRow) ? 1 : -1;
            colStep = (destinationColumn > sourceColumn) ? 1 : -1;

            int currentRow = sourceRow + rowStep;
            int currentCol = sourceColumn + colStep;

            // Verificar se não há peças no caminho
            while (currentRow != destinationRow) {
                if (board[currentRow][currentCol] != null) {
                    return false;
                }
                currentRow += rowStep;
                currentCol += colStep;
            }

            // Verificar se a casa de destino está vazia ou contém uma peça adversária
            if (board[destinationRow][destinationColumn] == null
                    || !currentPlayer.getPieces().contains(board[destinationRow][destinationColumn])) {
                return true;
            }
        }

        // Se não for um movimento válido para a rainha, retorne falso
        return false;
    }
}
