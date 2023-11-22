package game;

import java.io.*;

import board.ChessBoard;
import draw.Draws;
import draw.DrawType;
import logic.CheckValidation;
import logic.Exceptions;
import logic.MoveExecutor;
import pieces.Piece;
import specialmoves.Promotion;
import ui.BoardUI;

// Classe onde o jogo é executado.
public class PlayChess {
    // Declara o log da partida e os jogadores.
    private static ChessLog log = new ChessLog();
    private static Player currentPlayer;
    private static Player opponent;

    // Inicia o jogo.
    public static void playChessGame(Piece[][] board, Player playerWhite, Player playerBlack, int initial) {
        // Usa a flag initial para definir o jogador inicial.
        if (initial == 0) {
            currentPlayer = playerWhite;
            opponent = playerBlack;
        } else {
            currentPlayer = playerBlack;
            opponent = playerWhite;
        }

        // Flags para o fim do jogo.
        boolean checkMate = false;
        DrawType draw = new DrawType();

        // Adiciona o movimento inicial ao log.
        log.addMove("Initial" + currentPlayer.getColor());

        // Lê o nome dos jogadores.
        Input.inputName(playerWhite, playerBlack);

        // Loop principal do jogo.
        while (true) {
            // Imprime o tabuleiro.
            BoardUI.printBoard(board, currentPlayer, opponent);

            // Salva a posição atual do tabuleiro.
            String position = ChessBoard.getKey(board);
            log.addPosition(position);

            // Verifica se o jogador atual está em xeque.
            if (CheckValidation.isCheck(board, currentPlayer, opponent)) {
                currentPlayer.setCheck(true);
            } else {
                currentPlayer.setCheck(false);
            }

            // Verifica se o jogador atual está em xeque-mate.
            if (CheckValidation.isCheckMate(board, currentPlayer, opponent)) {
                checkMate = true;
                log.addChar('#');
                break;
            }

            // Se estiver em xeque, adiciona ao último movimento o caractere '+'.
            if (currentPlayer.isCheck()) {
                System.out.println(currentPlayer.getName() + ", você está em xeque!");
                log.addChar('+');
            }

            // Verifica se o jogo terminou em empate por afogamento.
            if (Draws.isStalemate(board, currentPlayer, opponent)) {
                draw.setDrawType(DrawType.DrawTypes.STALEMATE);
                break;
            }

            // Verifica se o jogo terminou em empate por material insuficiente.
            if (Draws.insufficientMaterial(currentPlayer, opponent)) {
                draw.setDrawType(DrawType.DrawTypes.INSUFFICIENT_MATERIAL);
                break;
            }

            // Verifica se o jogo terminou em empate pela regra das 50 jogadas.
            if (Draws.isFiftyMoveRule(log)) {
                draw.setDrawType(DrawType.DrawTypes.FIFTY_MOVES_RULE);
                break;
            }

            // Tenta fazer um movimento.
            try {
                makeMove(board);
            } catch (Exceptions e) {
                String message = e.getMessage();

                // Verifica se o jogo terminou em empate por acordo.
                if (message.equals("Draw!")) {
                    draw.setDrawType(DrawType.DrawTypes.AGREEMENT);
                    break;
                }

                // Verifica se o jogo terminou em empate por afogamento.
                if (message.equals("Draw Threefold Repetition!")) {
                    draw.setDrawType(DrawType.DrawTypes.THREEFOLD_REPETITION);
                    break;
                }

                // Imprime a mensagem de erro e pula para a próxima iteração.
                System.out.println(e.getMessage());
                continue;
            }

            // Troca os jogadores.
            switchPlayers();
        }

        // Lida com o resultado do jogo.
        handleGameResult(draw, checkMate);

        // Lida com o salvamento do log.
        Input.inputSaveLog(playerWhite.getName(), playerBlack.getName(), log, draw, playerBlack);
    }

    // Lida com a tentativa de movimento.
    private static void makeMove(Piece[][] board) throws Exceptions {
        System.out.println(currentPlayer.getName() + ", é sua vez. Digite o movimento: ");

        // Criar um array com as coordenadas de origem e destino.
        int[] moveCoordinates;

        // Tenta ler as coordenadas digitadas pelo usuário.
        try {
            moveCoordinates = Input.inputCoordinates(board, currentPlayer, opponent, log);
        } catch (Exceptions e) {
            throw new Exceptions(e.getMessage());
        }

        // Tenta fazer o movimento.
        try {
            MoveExecutor.movePiece(board, moveCoordinates, currentPlayer, opponent, log);

            // Se for uma promoção, lida com a promoção.
            int destinationRow = moveCoordinates[2];
            int destinationColumn = moveCoordinates[3];

            Promotion.promotion(board, board[destinationRow][destinationColumn], currentPlayer, log);
        } catch (Exceptions e) {
            throw new Exceptions(e.getMessage());
        }
    }

    // Lida com o resultado do jogo.
    private static void handleGameResult(DrawType draw, boolean checkMate) {
        if (draw.isDraw()) {
            draw.print();
            log.addMove("1/2-1/2");
        } else if (checkMate) {
            System.out.println("Xeque-mate! " + opponent.getName() + " venceu!");
            if (opponent.getColor() == 'W') {
                log.addMove("1-0");
            } else {
                log.addMove("0-1");
            }
        }
    }

    // Troca os jogadores.
    private static void switchPlayers() {
        Player aux = currentPlayer;
        currentPlayer = opponent;
        opponent = aux;
    }

    // Retorna o log da partida.
    public static ChessLog getLog() {
        return log;
    }
}
