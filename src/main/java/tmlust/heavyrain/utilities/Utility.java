package tmlust.heavyrain.utilities;

public class Utility {

    public static boolean isOnlyNumbers(char[] s) {
        for (char c : s) {
            if (!(c >= 48 && c <= 57)) {
                return false;
            }
        }
        return true;
    }

}
