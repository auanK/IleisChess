package pieces;

import game.Player;

/**
 * Classe que representa a peça "Peão" no jogo de xadrez.
 */
public class Pawn extends Piece {

    /**
     * Construtor da classe Pawn.
     *
     * @param cor            A cor da peça (W para branca ou B para preta).
     * @param positionRow    A linha em que a peça está posicionada.
     * @param positionColumn A coluna em que a peça está posicionada.
     */
    public Pawn(char cor, int positionRow, int positionColumn) {
        super('P', cor, positionRow, positionColumn); // Chama o construtor da superclasse Piece.
    }

    /**
     * Cria uma cópia da peça "Peão".
     *
     * @return Uma nova instância do "Peão" com as mesmas características.
     */
    public Pawn copy() {
        Pawn copyPawn = new Pawn(getColor(), getPositionRow(), getPositionColumn());
        return copyPawn;
    }

    /**
     * Valida o movimento do peão de acordo com as regras do jogo de xadrez.
     *
     * O peão se movimenta avançando para frente, para que o peão se mova, é
     * necessário que a diferença entre as coordenadas de origem e destino seja de
     * 1 na vertical (avançando para frente) e 0 na horizontal, e a casa de destino
     * deve estar vazia. Alternativamente, o peão pode mover-se duas casas para
     * frente a partir de sua posição inicial. Além disso, para capturar uma peça
     * adversária, o peão deve se mover exatamente 1 casa diagonalmente para frente.
     * (En passant e promoção ainda não implementados)
     *
     * @param board           O tabuleiro de jogo representado como uma matriz de peças.
     * @param sourceRow       A linha de origem da peça.
     * @param sourceColumn    A coluna de origem da peça.
     * @param destinationRow  A linha de destino do movimento.
     * @param destinationColumn A coluna de destino do movimento.
     * @param currentPlayer   O jogador atual que está realizando o movimento.
     * @return true se o movimento do peão for válido, caso contrário, false.
     */
    public boolean validateMove(Piece[][] board, int sourceRow, int sourceColumn, int destinationRow,
            int destinationColumn, Player currentPlayer) {

        // Calcula a diferença entre as coordenadas de origem e destino
        int rowDifference = destinationRow - sourceRow;
        int colDifference = Math.abs(destinationColumn - sourceColumn);
        char color = getColor();

        // Determina a direção de movimento com base na cor da peça
        int moveDirection = (color == 'W') ? 1 : -1;

        // Verifica se o movimento é na vertical e se a casa de destino está vazia
        if (sourceColumn == destinationColumn) {
            if (rowDifference == moveDirection) {
                return board[destinationRow][destinationColumn] == null;
                // Verifica se o movimento é de duas casas para frente e se a casa de destino
                // está vazia
            } else if (rowDifference == 2 * moveDirection && sourceRow == (color == 'W' ? 1 : 6)) {
                return board[sourceRow + moveDirection][sourceColumn] == null
                        && board[destinationRow][destinationColumn] == null;
            }
            // Verifica se o movimento é diagonal e se a casa de destino está ocupada por
            // uma peça adversária
        } else if (colDifference == 1 && rowDifference == moveDirection) {
            return board[destinationRow][destinationColumn] != null &&
                    !currentPlayer.getPieces().contains(board[destinationRow][destinationColumn]);
        }

        // Se não for um movimento válido para o peão, retorne falso
        return false;
    }

}
