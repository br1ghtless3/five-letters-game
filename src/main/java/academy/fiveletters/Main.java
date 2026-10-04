package academy.fiveletters;

import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.Scanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Main {
    private static final int MAX_ATTEMPTS = 6;
    private static final Logger LOG = LoggerFactory.getLogger(Main.class);

    private Main() {}

    public static void main(String[] args) {
        LOG.debug("Аргументы запуска: {}", String.join(" ", args));

        Dictionary dictionary = Dictionary.readerDictionary();

        boolean isCheck = false;
        String answer = "";
        String guess = "";
        long seed = 0L;
        boolean hasSeed = false;

        int i = 0;
        while (i < args.length) {
            String arg = args[i];
            if ("check".equalsIgnoreCase(arg)) {
                isCheck = true;
                i++;
            } else if ("--answer".equalsIgnoreCase(arg) && i + 1 < args.length) {
                answer = args[i + 1];
                i += 2;
            } else if ("--guess".equalsIgnoreCase(arg) && i + 1 < args.length) {
                guess = args[i + 1];
                i += 2;
            } else if ("--seed".equalsIgnoreCase(arg) && i + 1 < args.length) {
                seed = parseSeed(args[i + 1]);
                hasSeed = true;
                i += 2;
            } else {
                i++;
            }
        }

        if (isCheck || !guess.isEmpty()) {
            runCheck(dictionary, answer, guess);
            return;
        }

        runGame(dictionary, answer, hasSeed, seed);
    }

    private static long parseSeed(String seedStr) {
        try {
            return Long.parseLong(seedStr);
        } catch (NumberFormatException e) {
            LOG.warn("Некорректный формат seed: {}", seedStr);
            return 0L;
        }
    }

    private static void runCheck(Dictionary dictionary, String answer, String guess) {
        if (answer.isEmpty() || guess.isEmpty()) {
            System.out.println("Ошибка: для режима check укажите --answer <слово> и --guess <догадка>");
            return;
        }
        GameSession session = new GameSession(answer, MAX_ATTEMPTS, dictionary);
        try {
            GuessResult result = session.applyGuess(guess);
            String icons = "";
            for (LetterStatus status : result.getLetterStatuses()) {
                icons += status.getSymbol();
            }
            System.out.println(icons + " " + guess.trim().toLowerCase(Locale.ROOT));
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void runGame(Dictionary dictionary, String answer, boolean hasSeed, long seed) {
        Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);
        GameRunner runner;
        if (!answer.isEmpty()) {
            runner = new GameRunner(scanner, System.out, dictionary, answer);
        } else if (hasSeed) {
            runner = new GameRunner(scanner, System.out, dictionary, seed);
        } else {
            runner = new GameRunner(scanner, System.out, dictionary);
        }
        runner.run();
    }
}
