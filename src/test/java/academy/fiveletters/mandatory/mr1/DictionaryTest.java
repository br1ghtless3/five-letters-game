package academy.fiveletters.mandatory.mr1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import academy.fiveletters.Dictionary;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/** Обязательные тесты: словарь. */
@DisplayName("MR1. Словарь")
class DictionaryTest {

    @Test
    @DisplayName("Словарь содержит не меньше 50 слов")
    void dictionaryContainsAtLeastFiftyWords() {
        var dictionary = Dictionary.readerDictionary();
        assertTrue(dictionary.size() >= 50, "Словарь должен содержать не менее 50-ти слов.");
    }

    @Test
    @DisplayName("Все слова словаря состоят ровно из 5 букв")
    void allWordsAreExactlyFiveLettersLong() {
        var dictionary = Dictionary.readerDictionary();
        for (String word : dictionary.getWords()) {
            assertEquals(5, word.length(), "Слово '" + word + "' должно состоять ровно из 5-ти букв.");
        }
    }

    @Test
    @DisplayName("Пустой словарь приводит к ошибке, а не к запуску игры без слова")
    void emptyDictionaryIsRejected() {
        assertThrows(IllegalArgumentException.class, () -> new Dictionary(List.of()));
    }
}
