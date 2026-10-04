package academy.fiveletters;

import java.util.Locale;

public class WordValidator {
    private static final int WORD_LENGTH = 5;
    private final Dictionary dictionary;

    public WordValidator(Dictionary dictionary) {
        if (dictionary == null) {
            throw new IllegalArgumentException("передан пустой словарь.");
        }
        this.dictionary = dictionary;
    }

    public String validate(String guess) {
        if (guess == null || guess.isBlank()) {
            throw new IllegalArgumentException("Было передано пустое значение.");
        }
        String normalWord = guess.trim().toLowerCase(Locale.ROOT);

        if (normalWord.length() != WORD_LENGTH) {
            throw new IllegalArgumentException("Передано некорректное слово. Слово должно состоять из 5-ти букв.");
        }

        for (int i = 0; i < normalWord.length(); i++) {
            char c = normalWord.charAt(i);
            if (!Character.isLetter(c)) {
                throw new IllegalArgumentException("Слово должно состоять только из букв");
            }
        }

        if (!dictionary.contains(normalWord)) {
            throw new IllegalArgumentException("Введеного слова нет в словаре.");
        }
        return normalWord;
    }
}
