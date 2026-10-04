package academy.fiveletters.mandatory.mr3;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import academy.fiveletters.Dictionary;
import academy.fiveletters.GameRunner;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Scanner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/** Обязательные тесты: меню и режим автопроверки. */
@DisplayName("MR3. Меню и детерминированный режим")
class MenuTest {

    @Test
    @DisplayName("Некорректный пункт меню не роняет программу")
    void invalidMenuChoiceDoesNotCrash() {
        String input = "привет\n9\n  \n2\n";
        var outBytes = new ByteArrayOutputStream();
        var printStream = new PrintStream(outBytes, true, StandardCharsets.UTF_8);
        var dictionary = new Dictionary(List.of("озеро", "арбуз"));

        var runner = new GameRunner(new Scanner(input), printStream, dictionary);

        assertDoesNotThrow(runner::run);
        String output = outBytes.toString(StandardCharsets.UTF_8);
        assertTrue(output.contains("Некорректный ввод. Выберите 1 или 2."));
    }

    @Test
    @DisplayName("Можно сыграть несколько партий подряд без перезапуска")
    void severalGamesInARow() {
        String input = "1\nозеро\n1\nозеро\n2\n";
        var outBytes = new ByteArrayOutputStream();
        var printStream = new PrintStream(outBytes, true, StandardCharsets.UTF_8);
        var dictionary = new Dictionary(List.of("озеро", "арбуз"));

        var runner = new GameRunner(new Scanner(input), printStream, dictionary, "озеро");
        runner.run();

        String output = outBytes.toString(StandardCharsets.UTF_8);

        int winsCount = output.split("Победа!", -1).length - 1;
        assertEquals(2, winsCount);
    }

    @Test
    @DisplayName("Детерминированный режим даёт предсказуемый вывод для автопроверки")
    void deterministicModeProducesPredictableOutput() {
        String input = "1\nарбуз\nозеро\n2\n";
        var outBytes = new ByteArrayOutputStream();
        var printStream = new PrintStream(outBytes, true, StandardCharsets.UTF_8);
        var dictionary = new Dictionary(List.of("озеро", "арбуз"));

        var runner = new GameRunner(new Scanner(input), printStream, dictionary, "озеро");
        runner.run();

        String output = outBytes.toString(StandardCharsets.UTF_8);

        assertTrue(output.contains("❌🟡❌❌🟡 арбуз"));
        assertTrue(output.contains("Осталось попыток: 5"));
        assertTrue(output.contains("Победа! Слово угадано за 2 попытки"));
    }
}
