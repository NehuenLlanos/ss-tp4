package ar.edu.itba.ss.tp4.utils;

public class Utils {
    public static int factorial(int n) {
        if (n == 0) {
            return 1;
        }
        return n * factorial(n - 1);
    }
}
