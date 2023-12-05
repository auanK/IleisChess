package game;

import java.io.IOException;

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
    // Parâmetros do jogo.
    private static Piece[][] board;
    private static Player playerWhite;
    private static Player playerBlack;
    private static Player currentPlayer;
    private static Player opponent;
    private static ChessLog log;
    private static DrawType draw;
    private static boolean resign;

    private static String cyan = "\u001B[36m";
    private static String red = "\u001B[31m";
    private static String reset = "\u001B[0m";

    // Inicia o jogo.
    public static void playChessGame(Piece[][] boardLoad, Player playerWhiteLoad, Player playerBlackLoad, int initial,
            String filename, ChessLog logLoad) {
        // Verifica se o jogo está sendo carregado ou não.
        if (filename == null) {
            board = boardLoad;
            playerWhite = playerWhiteLoad;
            playerBlack = playerBlackLoad;

            log = new ChessLog();
            draw = new DrawType();

            // Adiciona o movimento inicial ao log.
            if (initial == 0) {
                currentPlayer = playerWhite;
                opponent = playerBlack;
            } else {
                currentPlayer = playerBlack;
                opponent = playerWhite;
            }

            if (logLoad != null) {
                log = logLoad;
            }

            // Lê o nome dos jogadores.
            Input.inputName(playerWhite, playerBlack);

        } else {
            // Carrega o jogo.
            try {
                SaveGame.loadGame(filename);
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Erro ao carregar o jogo: " + e.getMessage());
            }
        }

        // Flag de xeque-mate.
        boolean checkMate = false;

        // Flag de recuperação.
        int recovery = 0;

        // Loop principal do jogo.
        while (true) {
            // Salva o jogo a cada 2 jogadas.
            if (recovery == 2) {
                SaveGame.saveGame(board, currentPlayer, opponent, log, draw, resign, "recovery");
                recovery = 0;
            } else {
                recovery++;
            }

            // Imprime o tabuleiro.
            BoardUI.printBoard(board, playerWhite, playerBlack);

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
                System.out.println(cyan + currentPlayer.getName() + reset + ", você está em " + red + "xeque!" + reset);
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

            if (resign) {
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

                if (message.equals("Save!")) {
                    Input.inputSaveGame(board, currentPlayer, opponent, log, draw, resign);
                    continue;
                }

                if (message.equals("Withdraw!")) {
                    Input.inputResign();
                    continue;
                }

                if (message.equals("Exit!")) {
                    try {
                        Input.inputExit();
                    } catch (Exceptions e1) {
                        return;
                    }
                }

                // Imprime a mensagem de erro e pula para a próxima iteração.
                System.out.println(red + message + reset);

                try {
                    Thread.sleep(500);
                } catch (InterruptedException s) {
                    System.out.println("Erro ao tentat dormir o programa.");
                }
                continue;
            }

            // Troca os jogadores.
            switchPlayers();
        }

        // Lida com o resultado do jogo.
        handleGameResult(draw, checkMate);

        // Lida com o salvamento do log.
        Input.inputSaveLog(playerWhite.getName(), playerBlack.getName(), log, draw, resign, currentPlayer);

        // Lida com o salvamento do jogo.
        Input.inputSaveGame(board, currentPlayer, opponent, log, draw, resign);
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
        if (resign) {
            System.out.println("O(A) jogador(a) " + cyan + currentPlayer.getName() + cyan + " desistiu!");
            if (opponent.getColor() == 'W') {
                log.addMove("0-1");
            } else {
                log.addMove("1-0");
            }
        } else if (draw.isDraw()) {
            draw.print();
            log.addMove("1/2-1/2");
        } else if (checkMate) {
            System.out.println("Xeque-mate! " + cyan + opponent.getName() + reset + " venceu!");
            if (opponent.getColor() == 'W') {
                log.addMove("1-0");
            } else {
                log.addMove("0-1");
            }
        }
        System.out.println();
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

    // Seta os atributos da classe. (Usado para carregar o jogo)
    public static void setAttributes(Piece[][] boardLoad, Player playerWhiteLoad, Player playerBlackLoad,
            Player currentPlayerLoad, Player opponentLoad, ChessLog logLoad, DrawType drawLoad, boolean resignLoad) {
        board = boardLoad;
        playerWhite = playerWhiteLoad;
        playerBlack = playerBlackLoad;
        currentPlayer = currentPlayerLoad;
        opponent = opponentLoad;
        log = logLoad;
        draw = drawLoad;
        resign = resignLoad;
    }

    // Seta a desistencia
    public static void setResign(boolean resignLoad) {
        resign = resignLoad;
    }

}
