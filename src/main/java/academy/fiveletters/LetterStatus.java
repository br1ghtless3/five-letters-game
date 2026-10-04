package academy.fiveletters;

public enum LetterStatus {
    CORRECT("✅"),
    PRESENT("🟡"),
    MISS("❌");

    private final String symbol;

    LetterStatus(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }
}
