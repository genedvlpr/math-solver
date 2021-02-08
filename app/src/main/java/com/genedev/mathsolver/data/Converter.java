package com.genedev.mathsolver.data;

/**
 * Created by raajesharunachalam on 3/25/17.
 */

public class Converter {
    public static String convertMathToWolfram(String math) {
        StringBuilder wolframString = new StringBuilder();
        for (int i = 0; i < math.length(); i++) {
            char currentChar = math.charAt(i);

            if (currentChar == '+') {
                wolframString.append("%2b");
            } else if (currentChar == '/') {
                wolframString.append("%2F");
            } else if (currentChar == '=') {
                wolframString.append("%3D");
            } else if (currentChar == ' ') {
                wolframString.append("+");
            } else if(currentChar == '^'){
                wolframString.append("%5E");
            }
            else {
                wolframString.append(currentChar);
            }
        }
        return wolframString.toString();
    }

}
