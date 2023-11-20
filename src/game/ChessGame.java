package game;

import board.ChessBoard;
import board.ChessUI;
import draw.Draws;
import draw.DrawType.DrawTypes;
import draw.DrawType;
import logic.CheckValidation;
import logic.Exceptions;
import logic.MoveExecutor;
import pieces.Piece;
import specialmoves.Promotion;

// Classe principal do jogo.
public class ChessGame {
    private static Player playerWhite = new Player("");
    private static Player playerBlack = new Player("");

    private static Player currentPlayer = playerWhite;
    private static Player opponent = playerBlack;
    private static ChessLog log = new ChessLog();

    // Método main.
    public static void main(String[] args) {
        playChessGame();
    }

    // Inicia o jogo.
    private static void playChessGame() {
        // Cria o tabuleiro e o preenche com as peças.
        ChessBoard chessBoard = new ChessBoard();
        Piece[][] board = chessBoard.getBoard();
        chessBoard.assignPiecesToPlayers(playerWhite, playerBlack);

        // Flags para o fim do jogo.
        boolean checkMate = false;
        DrawType draw = new DrawType();

        UserInput.inputName(playerWhite, playerBlack);

        while (true) {
            ChessUI.printBoard(board);

            String position = ChessBoard.getKey(board);
            log.addPosition(position);

            handleCheck(board);

            if (CheckValidation.isCheckMate(board, currentPlayer, opponent)) {
                checkMate = true;
                log.addChar('#');
                break;
            }

            if (currentPlayer.isCheck()) {
                log.addChar('+');
                System.out.println("Xeque!");
            }

            if (Draws.isStalemate(board, currentPlayer, opponent)) {
                draw.setDrawType(DrawType.DrawTypes.STALEMATE);
                break;
            }

            if (Draws.insufficientMaterial(currentPlayer, opponent)) {
                draw.setDrawType(DrawType.DrawTypes.INSUFFICIENT_MATERIAL);
                break;
            }

            if (Draws.isFiftyMoveRule(log)) {
                draw.setDrawType(DrawType.DrawTypes.FIFTY_MOVES_RULE);
                break;
            }

            try {
                makeMove(board);
            } catch (Exceptions e) {
                String message = e.getMessage();
                if (message.equals("Draw!")) {
                    draw.setDrawType(DrawType.DrawTypes.AGREEMENT);
                    break;
                }
                if (message.equals("Draw TR!")) {
                    draw.setDrawType(DrawType.DrawTypes.THREEFOLD_REPETITION);
                    break;
                }
                System.out.println(e.getMessage());
                continue;
            }

            switchPlayers();
        }

        handleGameResult(draw, checkMate);
    }

    private static void makeMove(Piece[][] board) throws Exceptions {
        System.out.println(currentPlayer.getName() + ", é sua vez. Digite o movimento: ");

        int[] moveCoordinates;
        try {
            moveCoordinates = UserInput.inputCoordinates(board, currentPlayer, opponent, log);
        } catch (Exceptions e) {
            throw new Exceptions(e.getMessage());
        }

        try {
            MoveExecutor.movePiece(board, moveCoordinates, currentPlayer, opponent, log);
            int destinationRow = moveCoordinates[2];
            int destinationColumn = moveCoordinates[3];

            Promotion.promotion(board, board[destinationRow][destinationColumn], currentPlayer, log);
        } catch (Exceptions e) {
            throw new Exceptions(e.getMessage());
        }
    }

    private static void handleCheck(Piece[][] board) {
        if (CheckValidation.isCheck(board, currentPlayer, opponent)) {
            currentPlayer.setCheck(true);
        } else {
            currentPlayer.setCheck(false);
        }
    }

    private static void handleGameResult(DrawType draw, boolean checkMate) {
        if (draw.isDraw()) {
            draw.print();
        } else if (checkMate) {
            System.out.println("Xeque-mate! " + opponent.getName() + " venceu!");
        }
    }

    private static void switchPlayers() {
        Player aux = currentPlayer;
        currentPlayer = opponent;
        opponent = aux;
    }

    // Retorna o jogador branco.
    public static Player getPlayerWhite() {
        return playerWhite;
    }

    // Retorna o jogador preto.
    public static Player getPlayerBlack() {
        return playerBlack;
    }
}
