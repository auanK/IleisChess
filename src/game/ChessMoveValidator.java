package game;

import pieces.Piece;

public class ChessMoveValidator {

    /**
     * Este é um validador de movimento de xadrez que verifica se um movimento é
     * válido de acordo com as regras do jogo. Ele realiza várias verificações para
     * garantir se o movimento pode ser realizado.
     *
     * @param board         O tabuleiro de jogo representado como uma matriz de
     *                      peças.
     * 
     * @param coordinates   As coordenadas do movimento [linha de origem, coluna de
     *                      origem, linha de destino, coluna de destino].
     * 
     * @param currentPlayer O jogador atual que está fazendo o movimento.
     * 
     * @param opponent      O jogador adversário.
     * 
     * @return Uma String com a mensagem de erro, ou null se o movimento for válido.
     */

    public static String validateMove(Piece[][] board, int[] coordinates, Player currentPlayer, Player opponent) {
        // Obtém as coordenadas
        int sourceRow = coordinates[0];
        int sourceColumn = coordinates[1];
        int destinationRow = coordinates[2];
        int destinationColumn = coordinates[3];

        // Verifica se as coordenadas estão dentro do tabuleiro
        if (sourceRow < 0 || sourceRow > 7 || sourceColumn < 0 || sourceColumn > 7 || destinationRow < 0
                || destinationRow > 7 || destinationColumn < 0 || destinationColumn > 7) {
            return "Movimento inválido, posição fora do tabuleiro";
        }

        // Verifica se a posição de origem e destino são iguais
        if (sourceRow == destinationRow && sourceColumn == destinationColumn) {
            return "Movimento inválido, posição de origem e destino são iguais";
        }

        // Verifica se há uma peça na posição de origem
        if (board[sourceRow][sourceColumn] == null) {
            return "Movimento inválido, não há peça na posição de origem";
        }

        // Verifica se o jogador está tentando mover uma peça do adversário
        if (!currentPlayer.getPieces().contains(board[sourceRow][sourceColumn])) {
            return "Movimento inválido, peça selecionada não é sua";
        }

        // Verifica se o movimento é válido de acordo com as regras da peça
        if (!board[sourceRow][sourceColumn].validateMove(board, sourceRow, sourceColumn, destinationRow,
                destinationColumn, currentPlayer)) {
            return "Movimento inválido, peça não pode se mover para essa posição";
        }

        // Se todas as verificações passarem, o movimento é válido, então retorna null
        // (sem erro)
        return null;
    }

    /**
     * Este método move uma peça no tabuleiro de xadrez, desde que o movimento seja
     * válido.
     * Ele primeiro chama o método `validateMove` para verificar se o movimento é
     * válido e, se for, realiza o movimento no tabuleiro.
     *
     * @param board         O tabuleiro de jogo representado como uma matriz de
     *                      peças.
     * 
     * @param coordinates   As coordenadas do movimento [linha de origem, coluna de
     *                      origem, linha de destino, coluna de destino].
     * 
     * @param currentPlayer O jogador atual que está fazendo o movimento.
     * 
     * @param opponent      O jogador adversário.
     * 
     * @return true se o movimento for bem-sucedido, false se o movimento for
     *         inválido.
     */

    public static boolean movePiece(Piece[][] board, int[] coordinates, Player currentPlayer, Player opponent) {
        // Valida o movimento, se for inválido retorna o erro
        String errorMessage = validateMove(board, coordinates, currentPlayer, opponent);

        // Se houver erro, imprime o erro e retorna false
        if (errorMessage != null) {
            System.out.println(errorMessage);
            return false;
        }

        // Obtém as coordenadas
        int sourceRow = coordinates[0];
        int sourceColumn = coordinates[1];
        int destinationRow = coordinates[2];
        int destinationColumn = coordinates[3];

        // Atualiza a posição da peça no objeto Piece
        board[sourceRow][sourceColumn].setPosition(destinationRow, destinationColumn);

        // Verifica se é uma captura
        if (board[destinationRow][destinationColumn] != null) {
            // Se for uma captura, remove a peça do adversário e adiciona a peça capturada
            // na lista de peças capturadas do jogador atual
            opponent.removePiece(board[destinationRow][destinationColumn]);
            currentPlayer.addCapturedPiece(board[destinationRow][destinationColumn]);
            board[destinationRow][destinationColumn].setPosition(-1, -1); // Seta a posição da peça capturada para -1, -1
        }

        // Realiza o movimento no tabuleiro (atualiza a matriz)
        board[destinationRow][destinationColumn] = board[sourceRow][sourceColumn];
        board[sourceRow][sourceColumn] = null;

        // Retorna true para indicar que o movimento foi bem-sucedido
        return true;
    }

}