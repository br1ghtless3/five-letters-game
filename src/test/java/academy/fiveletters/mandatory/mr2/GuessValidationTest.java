package academy.fiveletters.mandatory.mr2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import academy.fiveletters.Dictionary;
import academy.fiveletters.GameSession;
import academy.fiveletters.GameStatus;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

/** Обязательные тесты: валидация ввода. */
@DisplayName("MR2. Валидация ввода")
class GuessValidationTest {

    @ParameterizedTest
    @ValueSource(strings = {"дом", "домики", ""})
    @DisplayName("Слово не из 5 букв отклоняется: \"{0}\"")
    void wordOfWrongLengthIsRejected(String guess) {
        var dictionary = new Dictionary(List.of("туман"));
        var session = new GameSession("туман", 6, dictionary);

        assertThrows(IllegalArgumentException.class, () -> session.applyGuess(guess));
    }

    @ParameterizedTest
    @ValueSource(strings = {"дом12", "дом!!", "до ма"})
    @DisplayName("Ввод с не-буквами отклоняется: \"{0}\"")
    void nonLetterInputIsRejected(String guess) {
        var dictionary = new Dictionary(List.of("туман"));
        var session = new GameSession("туман", 6, dictionary);

        assertThrows(IllegalArgumentException.class, () -> session.applyGuess(guess));
    }

    @Test
    @DisplayName("Слово, которого нет в словаре, отклоняется")
    void wordOutsideDictionaryIsRejected() {
        var dictionary = new Dictionary(List.of("туман"));
        var session = new GameSession("туман", 6, dictionary);

        assertThrows(IllegalArgumentException.class, () -> session.applyGuess("озеро"));
    }

    @Test
    @DisplayName("Некорректный ввод не тратит попытку")
    void invalidInputDoesNotConsumeAttempt() {
        var dictionary = new Dictionary(List.of("туман"));
        var session = new GameSession("туман", 6, dictionary);

        assertThrows(IllegalArgumentException.class, () -> session.applyGuess("река"));

        assertEquals(0, session.getAttemptsUsed());
        assertTrue(session.getAttemptsHistory().isEmpty());
    }

    @Test
    @DisplayName("Ввод не зависит от регистра: \"ОЗЕРО\" и \"озеро\" обрабатываются одинаково")
    void inputIsCaseInsensitive() {
        var dictionary = new Dictionary(List.of("озеро"));
        var sessionUpper = new GameSession("озеро", 6, dictionary);
        var sessionLower = new GameSession("озеро", 6, dictionary);

        sessionUpper.applyGuess("ОЗЕРО");
        sessionLower.applyGuess("озеро");

        assertEquals(GameStatus.WIN, sessionUpper.getStatus());
        assertEquals(GameStatus.WIN, sessionLower.getStatus());

        assertEquals(1, sessionUpper.getAttemptsUsed());
        assertEquals(1, sessionLower.getAttemptsUsed());

        assertEquals(sessionLower.getAttemptsHistory(), sessionUpper.getAttemptsHistory());
    }
}
