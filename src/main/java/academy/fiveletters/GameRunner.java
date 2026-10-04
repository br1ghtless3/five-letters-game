package academy.fiveletters;

import java.io.PrintStream;
import java.util.Locale;
import java.util.Objects;
import java.util.Scanner;

public class GameRunner {
    private static final int MAX_ATTEMPTS = 6;

    private final Scanner scanner;
    private final PrintStream out;
    private final Dictionary dictionary;
    private final long fixedSeed;
    private final boolean hasFixedSeed;
    private final String fixedAnswer;

    // для обычной игры
    public GameRunner(Scanner scanner, PrintStream out, Dictionary dictionary) {
        this.scanner = Objects.requireNonNull(scanner, "Scanner не может быть null");
        this.out = Objects.requireNonNull(out, "PrintStream не может быть null");
        this.dictionary = Objects.requireNonNull(dictionary, "Dictionary не может быть null");
        this.fixedSeed = 0L;
        this.hasFixedSeed = false;
        this.fixedAnswer = "";
    }

    // для тестов по seed
    public GameRunner(Scanner scanner, PrintStream out, Dictionary dictionary, long fixedSeed) {
        this.scanner = Objects.requireNonNull(scanner, "Scanner не может быть null");
        this.out = Objects.requireNonNull(out, "PrintStream не может быть null");
        this.dictionary = Objects.requireNonNull(dictionary, "Dictionary не может быть null");
        this.fixedSeed = fixedSeed;
        this.hasFixedSeed = true;
        this.fixedAnswer = "";
    }

    // для тестов по ответу
    public GameRunner(Scanner scanner, PrintStream out, Dictionary dictionary, String fixedAnswer) {
        this.scanner = Objects.requireNonNull(scanner, "Scanner не может быть null");
        this.out = Objects.requireNonNull(out, "PrintStream не может быть null");
        this.dictionary = Objects.requireNonNull(dictionary, "Dictionary не может быть null");
        this.fixedSeed = 0L;
        this.hasFixedSeed = false;
        this.fixedAnswer = Objects.requireNonNull(fixedAnswer, "fixedAnswer не может быть null");
    }

    public void run() {
        printMenu();
        while (scanner.hasNextLine()) {
            String choice = scanner.nextLine().trim();

            if ("1".equals(choice)) {
                playGame();
                printMenu();
            } else if ("2".equals(choice)) {
                break;
            } else {
                out.println("Некорректный ввод. Выберите 1 или 2.");
                printMenu();
            }
        }
    }

    private void printMenu() {
        out.println("1. Новая игра");
        out.println("2. Выход");
    }

    public void playGame() {
        GameSession session;
        if (!fixedAnswer.isEmpty()) {
            session = new GameSession(fixedAnswer, MAX_ATTEMPTS, dictionary);
        } else if (hasFixedSeed) {
            session = GameSession.startGame(dictionary, MAX_ATTEMPTS, fixedSeed);
        } else {
            session = GameSession.startGame(dictionary, MAX_ATTEMPTS, System.currentTimeMillis());
        }

        while (session.getStatus() == GameStatus.IN_PROGRESS && scanner.hasNextLine()) {
            String guess = scanner.nextLine();

            try {
                GuessResult result = session.applyGuess(guess);
                String icons = "";
                for (LetterStatus status : result.getLetterStatuses()) {
                    icons += status.getSymbol();
                }
                out.println(icons + " " + guess.trim().toLowerCase(Locale.ROOT));
                out.println("Осталось попыток: " + (session.getMaxAttempts() - session.getAttemptsUsed()));
            } catch (IllegalArgumentException e) {
                out.println(e.getMessage());
            }
        }

        if (session.getStatus() == GameStatus.WIN) {
            out.println("Победа! Слово угадано за " + session.getAttemptsUsed() + " попытки");
        } else if (session.getStatus() == GameStatus.LOSE) {
            out.println("Неудача. Загаданное слово: " + session.getAnswer());
        }
    }
}
