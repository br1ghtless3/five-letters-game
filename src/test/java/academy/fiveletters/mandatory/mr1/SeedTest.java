package academy.fiveletters.mandatory.mr1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import academy.fiveletters.Dictionary;
import academy.fiveletters.GameSession;
import java.util.HashSet;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/** Обязательные тесты: одинаковый seed обязан давать одинаковое загаданное слово. */
@DisplayName("MR1. Воспроизводимость по seed")
class SeedTest {

    @Test
    @DisplayName("Одинаковый seed даёт одинаковое загаданное слово")
    void sameSeedProducesSameAnswer() {
        var dictionary = Dictionary.readerDictionary();
        long seed = 42L;

        var firstSession = GameSession.startGame(dictionary, 5, seed);
        var secondSession = GameSession.startGame(dictionary, 5, seed);

        assertEquals(
                firstSession.getAnswer(),
                secondSession.getAnswer(),
                "Одинаковый seed должен дать одно " + "и то же слово");
    }

    @Test
    @DisplayName("Разные seed'ы дают разные слова хотя бы иногда")
    void differentSeedsProduceDifferentAnswers() {
        var dictionary = Dictionary.readerDictionary();
        var uniqSet = new HashSet<String>();

        for (long seed = 1; seed <= 10; seed++) {
            var session = GameSession.startGame(dictionary, 5, seed);
            uniqSet.add(session.getAnswer());
        }

        assertTrue(uniqSet.size() > 1, "Разные seed'ы дают разные слова");
    }
}
