package ui;

public class UtilTools {
    public static void sleep(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            System.out.println("Erro ao dormir o programa.");
        }
    }

    public static void clearConsole() {
        System.out.print("\033[H\033[2J");
    }
}
