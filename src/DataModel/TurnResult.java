package DataModel;

public class TurnResult {
    public final int CorrectDigits;
    public final int DigitsInPlace;

    public TurnResult(int correctDigits, int digitsInPlace){

        CorrectDigits = correctDigits;
        DigitsInPlace = digitsInPlace;
    }
}
