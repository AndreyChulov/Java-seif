import DataModel.TurnResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Bot {
    private List<Number[]> _variants;
    private Number[] _currentTurn;

    public Bot(){
        _variants = new ArrayList<>();
        _currentTurn = null;
    }

    public void InitGameStart() {
        for (int digit = 0; digit < 10000; digit++) {

            int digit1 = digit / 1000;
            int digit2 = (digit - digit1*1000) / 100;
            int digit3 = (digit - digit1*1000 - digit2*100) / 10;;
            int digit4 = digit % 10;
            Number[] variant = new Number[]{digit1, digit2, digit3, digit4};
            int distinctElementsCount = (int) Arrays.stream(variant).distinct().count();

            if (distinctElementsCount == 4){
                _variants.add(variant);
            }
        }
    }

    public String getTurn() {
        Random generator = new Random();
        int turnIndex = generator.nextInt(_variants.size());
        _currentTurn = _variants.get(turnIndex);

        return Utilites.TurnArrayToString(_currentTurn);
    }

    public void setTurnResult(TurnResult result) {
        _variants = _variants.stream().filter(x->Utilites.getRightCount(_currentTurn, x) == result.CorrectDigits).toList();
        _variants = _variants.stream().filter(x->Utilites.getInPlaceCount(_currentTurn, x) == result.DigitsInPlace).toList();
    }
}
