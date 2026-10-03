package academy.fiveletters;

import java.util.ArrayList;
import java.util.List;

public class GameSession {
    private static final int WORD_LENGTH = 5;

    private final String answer;
    private final int maxAttempts;
    private int attemptsUsed;
    private final List<String> attemptsHistory = new ArrayList<>();
    private GameStatus status;

    private final WordValidator validator;

    // конструктор самой сессии старый, ктр вызывает новый для тестов
    public GameSession(String answer, int maxAttempts) {
        this(answer, maxAttempts, null);
    }

    // новый, ура
    public GameSession(String answer, int maxAttempts, Dictionary dictionary) {
        if (answer == null || answer.length() != WORD_LENGTH) {
            throw new IllegalArgumentException("Передано некорректное слово. Слово должно состоять из 5-ти букв.");
        }

        if (maxAttempts <= 0) {
            throw new IllegalArgumentException(
                    "Передано некорректное количество попыток. Количество попыток должно быть больше 0.");
        }

        this.attemptsUsed = 0;
        this.status = GameStatus.IN_PROGRESS;
        this.answer = answer.toLowerCase();
        this.maxAttempts = maxAttempts;
        this.validator = new WordValidator(dictionary);

    }

    public GameStatus getStatus() {
        return status;
    }

    public int getAttemptsUsed() {
        return attemptsUsed;
    }

    public int getMaxAttempts() {
        return maxAttempts;
    }

    public List<String> getAttemptsHistory() {
        return List.copyOf(attemptsHistory); // нет изменениям!
    }

    public String getAnswer() {
        return answer;
    }

    public static GameSession startGame(Dictionary dictionary, int maxAttempts, long seed) {
        if (dictionary == null) {
            throw new IllegalArgumentException("Словарь не может быть равен null.");
        }
        String word = dictionary.getRandom(seed);
        return new GameSession(word, maxAttempts, dictionary);
    }

    public void applyGuess(String guess) {
        if (status != GameStatus.IN_PROGRESS) {
            throw new IllegalStateException("Игра была завершена, новые попытки не принимаются.");
        }

        String cleanGuess = validator.validate(guess);

        attemptsUsed++;
        attemptsHistory.add(cleanGuess.toLowerCase());

        if (cleanGuess.equalsIgnoreCase(answer)) {
            status = GameStatus.WIN;
        } else if (attemptsUsed >= maxAttempts) {
            status = GameStatus.LOSE;
        }
    }
}
