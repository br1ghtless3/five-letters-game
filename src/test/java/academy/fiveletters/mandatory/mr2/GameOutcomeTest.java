package academy.fiveletters.mandatory.mr2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import academy.fiveletters.Dictionary;
import academy.fiveletters.GameSession;
import academy.fiveletters.GameStatus;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/** Обязательные тесты: завершение партии. */
@DisplayName("MR2. Победа и поражение")
class GameOutcomeTest {

    @Test
    @DisplayName("Угаданное слово переводит сессию в статус WIN")
    void correctGuessWinsTheGame() {
        var dictionary = new Dictionary(List.of("озеро", "туман"));
        var session = new GameSession("озеро", 6, dictionary);
        session.applyGuess("озеро");
        assertEquals(GameStatus.WIN, session.getStatus());
    }

    @Test
    @DisplayName("После 6 неудачных попыток сессия переходит в статус LOSE")
    void sixFailedAttemptsLoseTheGame() {
        var dictionary = new Dictionary(List.of("озеро", "дождь"));
        var session = new GameSession("озеро", 6, dictionary);
        for (int i = 0; i < 6; i++) {
            session.applyGuess("дождь");
        }

        assertEquals(GameStatus.LOSE, session.getStatus());
        assertEquals(6, session.getAttemptsUsed());
    }

    @Test
    @DisplayName("При поражении показывается загаданное слово")
    void answerIsRevealedOnLoss() {
        var dictionary = new Dictionary(List.of("озеро", "дождь"));
        var session = new GameSession("озеро", 6, dictionary);
        for (int i = 0; i < 6; i++) {
            session.applyGuess("дождь");
        }

        assertEquals(GameStatus.LOSE, session.getStatus());
        assertEquals("озеро", session.getAnswer());
    }

    @Test
    @DisplayName("Завершённая партия больше не принимает попытки")
    void finishedGameRejectsFurtherGuesses() {
        var dictionary = new Dictionary(List.of("озеро", "туман"));
        var session = new GameSession("озеро", 6, dictionary);
        session.applyGuess("озеро");

        assertThrows(IllegalStateException.class, () -> session.applyGuess("озеро"));
    }
}
