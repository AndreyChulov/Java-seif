import DataModel.TurnResult;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Game {
    private static final Integer[] DIGITS = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};

    private Integer[] _secret = {null, null, null, null};

    Game(){
        Integer[] digits = DIGITS.clone();
        List<Integer> digitsList = Arrays.asList(digits);

        Collections.shuffle(digitsList);

        for (int counter = 0; counter < _secret.length; counter++){
            _secret[counter] = digitsList.get(counter);
        }
    }

    public String getSecret(){
        return Utilites.TurnArrayToString(_secret);
    }

    public TurnResult Turn(String number){
        char[] turn = new char[4];
        number.getChars(0, number.length(), turn, 0);
        Number[] digits = new Number[4];

        for (int counter = 0; counter < 4; counter++){
            digits[counter] = Integer.parseInt( String.valueOf(turn[counter]));
        }

        int right = Utilites.getRightCount(digits, _secret);
        int inPlace = Utilites.getInPlaceCount(digits, _secret);

        return new TurnResult(right, inPlace);
    }


}
