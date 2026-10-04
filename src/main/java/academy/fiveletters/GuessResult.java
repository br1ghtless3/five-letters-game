package academy.fiveletters;

import java.util.List;

public class GuessResult {
    private static final int WORD_LENGTH = 5;
    private final List<LetterStatus> letterStatuses;

    public GuessResult(List<LetterStatus> letterStatuses) {
        if (letterStatuses == null || letterStatuses.size() != WORD_LENGTH) {
            throw new IllegalArgumentException("Результат должен содержать ровно 5 статусов.");
        }
        this.letterStatuses = List.copyOf(letterStatuses);
    }

    public List<LetterStatus> getLetterStatuses() {
        return letterStatuses;
    }

    public static GuessResult draw(String answer, String guess) {
        LetterStatus[] result = new LetterStatus[WORD_LENGTH];
        boolean[] answerUsed = new boolean[WORD_LENGTH];

        for (int i = 0; i < WORD_LENGTH; i++) {
            if (guess.charAt(i) == answer.charAt(i)) {
                result[i] = LetterStatus.CORRECT;
                answerUsed[i] = true;
            }
        }

        for (int i = 0; i < WORD_LENGTH; i++) {
            if (result[i] != null) {
                continue;
            }
            char current = guess.charAt(i);
            for (int j = 0; j < WORD_LENGTH; j++) {
                if (!answerUsed[j] && answer.charAt(j) == current) {
                    result[i] = LetterStatus.PRESENT;
                    answerUsed[j] = true;
                    break;
                }
            }
            if (result[i] == null) {
                result[i] = LetterStatus.MISS;
            }
        }
        return new GuessResult(List.of(result));
    }
}
