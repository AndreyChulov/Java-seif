import DataModel.TurnResult;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Main implements Runnable{
    private static final int TARGET_GAMES_COUNT = 1000;
    private static int _gamesCount = 0;
    private static List<Number> _gamesStatistics = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        List<Thread> threads = new java.util.ArrayList<>();

        for (int counter = 0; counter < TARGET_GAMES_COUNT; counter++) {
            Thread thread = new Thread(new Main());
            threads.add(thread);
        }

        var startTime = System.currentTimeMillis();

        for (int counter = 0; counter < TARGET_GAMES_COUNT; counter++) {
            threads.get(counter).start();
        }

        try
        {
            for (int counter = 0; counter < TARGET_GAMES_COUNT; counter++) {
                //Thread.currentThread().join().wait();
                threads.get(counter).join();
            }
        }catch (Exception exception){
            System.out.println("Exception:" + exception.getMessage());
        }

        var endTime = System.currentTimeMillis();
        var elapsedTime = endTime - startTime;

        System.out.println("--------------------------------------------------------");
        System.out.println("--------------------Statistics--------------------------");
        System.out.println("--------------------------------------------------------");
        System.out.println("Threads count = 1000");
        System.out.println("Games count = 1000");
        System.out.println("Average turn win = " +
                (_gamesStatistics
                        .stream()
                        .reduce(0, (acc, el) -> acc.intValue() + el.intValue())
                        .intValue()
                            / (float)_gamesCount));
        System.out.println("Time takes to get statistics: " + elapsedTime/1000.0 + " seconds.");
        System.out.println("--------------------------------------------------------");

        try (SqLite db = new SqLite()){
            try {
                db.ExecuteWithoutResult("DROP TABLE IF EXISTS BotChampion;");
                db.ExecuteWithoutResult("CREATE TABLE IF NOT EXISTS BotChampion (ID INT, TotalWins INT, TotalTries INT);");
            } catch (Exception ex){
                System.out.println(ex.getMessage());
            }
        }
    }

    @Override
    public void run() {
        _gamesCount++;
        Game game = new Game();
        Bot bot = new Bot();
        bot.InitGameStart();
        String secret = game.getSecret();
        System.out.println("Secret = " + secret);

        TurnResult result = null;
        var tryIndex = 0;

        while (result == null || result.DigitsInPlace < 4){
            tryIndex++;
            System.out.println("Popitka: " + tryIndex);
            System.out.print("Vash hod:");
            String turn = bot.getTurn();
            System.out.println("Bot turn: " + turn);

            result = game.Turn(turn);
            bot.setTurnResult(result);
            System.out.println("Resultati: ugadano = "+ result.CorrectDigits + " na meste = " + result.DigitsInPlace);
        }

        _gamesStatistics.add(tryIndex);
    }
}