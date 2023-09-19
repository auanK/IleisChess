package pieces;

import game.Player;

/**
 * Classe que representa a peça "Rei" no jogo de xadrez.
 */
public class King extends Piece {

    /**
     * Construtor da classe King.
     *
     * @param cor            A cor da peça (W para branca ou B para preta).
     * @param positionRow    A linha em que a peça está posicionada.
     * @param positionColumn A coluna em que a peça está posicionada.
     */
    public King(char cor, int positionRow, int positionColumn) {
        super('K', cor, positionRow, positionColumn); // Chama o construtor da superclasse Piece.
    }

    /**
     * Cria uma cópia da peça "Rei".
     *
     * @return Uma nova instância do "Rei" com as mesmas características.
     */
    public King copy() {
        King copyKing = new King(getColor(), getPositionRow(), getPositionColumn());
        return copyKing;
    }

    /**
     * Valida o movimento do rei de acordo com as regras do jogo de xadrez.
     *
     * O rei se movimenta para qualquer casa adjacente à sua posição atual, logo
     * para o rei
     * se movimentar é necessário que a diferença entre as coordenadas de origem e
     * destino seja
     * de no máximo 1, e a casa de destino esteja vazia ou contenha uma peça
     * adversária.
     *
     * @param board             O tabuleiro de jogo representado como uma matriz de
     *                          peças.
     * @param sourceRow         A linha de origem da peça.
     * @param sourceColumn      A coluna de origem da peça.
     * @param destinationRow    A linha de destino do movimento.
     * @param destinationColumn A coluna de destino do movimento.
     * @param currentPlayer     O jogador atual que está realizando o movimento.
     * @return true se o movimento do rei for válido, caso contrário, false.
     */
    public boolean validateMove(Piece[][] board, int sourceRow, int sourceColumn, int destinationRow,
            int destinationColumn, Player currentPlayer) {
        // Calcular a diferença entre as coordenadas de origem e destino
        int rowDiff = Math.abs(destinationRow - sourceRow);
        int colDiff = Math.abs(destinationColumn - sourceColumn);

        // Verificar se o movimento é válido para o rei (uma casa em qualquer direção)
        if ((rowDiff <= 1 && colDiff <= 1)) {
            // Verificar se a casa de destino está vazia ou contém uma peça adversária
            if (board[destinationRow][destinationColumn] == null
                    || !currentPlayer.getPieces().contains(board[destinationRow][destinationColumn])) {
                return true;
            }
        }

        // Se não for um movimento válido para o rei, retorne falso
        return false;
    }

}
