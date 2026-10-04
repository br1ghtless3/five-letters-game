package academy.fiveletters.mandatory.mr2;

import static academy.fiveletters.LetterStatus.CORRECT;
import static academy.fiveletters.LetterStatus.MISS;
import static academy.fiveletters.LetterStatus.PRESENT;
import static org.junit.jupiter.api.Assertions.assertEquals;

import academy.fiveletters.GuessResult;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/** Обязательные тесты: раскраска букв. */
@DisplayName("MR2. Проверка букв")
class LetterMatchingTest {

    @Test
    @DisplayName("Базовый случай: загадано \"озеро\", ввод \"арбуз\" -> ❌🟡❌❌🟡")
    void basicCase() {
        var result = GuessResult.draw("озеро", "арбуз");

        assertEquals(List.of(MISS, PRESENT, MISS, MISS, PRESENT), result.getLetterStatuses());
    }

    @Test
    @DisplayName("Полное совпадение: загадано \"озеро\", ввод \"озеро\" -> ✅✅✅✅✅")
    void exactMatch() {
        var result = GuessResult.draw("озеро", "озеро");

        assertEquals(List.of(CORRECT, CORRECT, CORRECT, CORRECT, CORRECT), result.getLetterStatuses());
    }

    @Test
    @DisplayName("Повторяющиеся буквы: загадано \"сорок\", ввод \"оооом\" -> ❌✅❌✅❌")
    void repeatedLettersAreNotDoubleCounted() {
        var result = GuessResult.draw("сорок", "оооом");

        assertEquals(List.of(MISS, CORRECT, MISS, CORRECT, MISS), result.getLetterStatuses());
    }

    @Test
    @DisplayName("Ни одна буква не подошла: все позиции ❌")
    void noMatchingLetters() {
        var result = GuessResult.draw("туман", "озеро");

        assertEquals(List.of(MISS, MISS, MISS, MISS, MISS), result.getLetterStatuses());
    }
}
