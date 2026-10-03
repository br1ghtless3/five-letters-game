package academy.fiveletters;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class Dictionary {
    private static final int WORD_LENGTH = 5;
    private static final int MIN_DICTIONARY_SIZE = 50;

    private final List<String> words;

    // конструктор
    public Dictionary(List<String> words) {
        if (words == null || words.isEmpty()) {
            throw new IllegalArgumentException("Переданный список пуст.");
        }

        List<String> normalizedWords = new ArrayList<>();

        for (String word : words) {
            if (word == null || word.length() != WORD_LENGTH) {
                throw new IllegalArgumentException("Длина слова должна составлять 5.");
            }
            normalizedWords.add(word.toLowerCase(Locale.ROOT));
        }
        this.words = List.copyOf(normalizedWords);
    }

    public List<String> getWords() {
        return words;
    }

    // static - тк пренадлежность конкретно этому классу
    public static Dictionary readerDictionary() {
        var inputStream = Dictionary.class.getClassLoader().getResourceAsStream("dictionary.txt");
        if (inputStream == null) {
            throw new IllegalStateException("Файл словаря не был найден.");
        }

        try (var reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            List<String> readerWords = new ArrayList<>();
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.isBlank()) {
                    continue;
                }
                line = line.trim();
                if (line.charAt(0) == '#') {
                    continue;
                }

                readerWords.add(line.toLowerCase(Locale.ROOT));
            }

            if (readerWords.size() < MIN_DICTIONARY_SIZE) {
                throw new IllegalArgumentException("Размер переданного словаря составляет меньше 50-ти слов.");
            }

            return new Dictionary(readerWords);
        } catch (IOException e) {
            throw new IllegalStateException("Ошибка чтения файла словаря", e);
        }
    }

    public int size() {
        return words.size();
    }

    public String getRandom(long seed) {
        var random = new Random(seed);
        var index = random.nextInt(words.size());
        return words.get(index);
    }

    // слово есть в словаре?
    public boolean contains(String word) {
        if (word == null) {
            return false;
        }
        return words.contains(word.toLowerCase(Locale.ROOT));
    }
}
