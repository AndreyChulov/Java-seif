import DataModel.TurnResult;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;

public class Main {
    public static void main(String[] args) throws IOException {
        Game game = new Game();
        Bot bot = new Bot();
        bot.InitGameStart();
        String secret = game.getSecret();
        System.out.println("Secret = " + secret);

        TurnResult result = null;

        while (result == null || result.DigitsInPlace < 4){
            System.out.print("Vash hod:");
            //BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            //String turn = reader.readLine();
            String turn = bot.getTurn();
            System.out.println("Bot turn: " + turn);
            result = game.Turn(turn);
            bot.setTurnResult(result);
            System.out.println("Resultati: ugadano = "+ result.CorrectDigits + " na meste = " + result.DigitsInPlace);
        }
    }
}