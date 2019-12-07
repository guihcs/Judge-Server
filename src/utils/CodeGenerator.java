package utils;

public class CodeGenerator {



    public static String generate(int length){
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            double value = Math.random() * 52;
            char c = (char) (value > 26 ? 'a' + value - 26 : 'A' + value);
            builder.append(c);
        }

        return builder.toString();
    }
}
