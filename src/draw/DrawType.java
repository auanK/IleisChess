package draw;

// Classe que implementa a flag de empate.
public class DrawType {
    private DrawTypes drawType; // Tipo de empate. (null = não há empate)

    // Construtor da classe.
    public DrawType() {
        this.drawType = null;
    }

    // Retorna o tipo de empate.
    public DrawTypes getDrawType() {
        return drawType;
    }

    // Retorna o tipo de empate em String.
    public String getDrawTypeString() {
        if (drawType != null) {
            switch (drawType) {
                case AGREEMENT:
                    return "empate por acordo";
                case STALEMATE:
                    return "empate por afogamento";
                case INSUFFICIENT_MATERIAL:
                    return "empate por insuficiência de material";
                case THREEFOLD_REPETITION:
                    return "empate por tripla repetição";
                case FIFTY_MOVES_RULE:
                    return "empate por regra dos 50 movimentos";
            }
        }
        return null;
    }

    // Define o tipo de empate.
    public void setDrawType(DrawTypes draw) {
        this.drawType = draw;
    }

    public boolean isDraw() {
        return drawType != null;
    }

    // Imprime o tipo de empate.
    public void print() {
        if (drawType != null) {
            switch (drawType) {
                case AGREEMENT:
                    System.out.println("Empate por acordo!");
                    break;
                case STALEMATE:
                    System.out.println("Empate por afogamento!");
                    break;
                case INSUFFICIENT_MATERIAL:
                    System.out.println("Empate por insuficiência de material!");
                    break;
                case THREEFOLD_REPETITION:
                    System.out.println("Empate por tripla repetição!");
                    break;
                case FIFTY_MOVES_RULE:
                    System.out.println("Empate por regra dos 50 movimentos!");
                    break;
            }
        }
    }

    // Enum que contém os tipos de empate.
    public enum DrawTypes {
        AGREEMENT,
        STALEMATE,
        INSUFFICIENT_MATERIAL,
        THREEFOLD_REPETITION,
        FIFTY_MOVES_RULE
    }
}
