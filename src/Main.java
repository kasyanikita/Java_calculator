import java.io.IOException;
import java.util.*;

public class Main {
    private final static Set<String> operations = new HashSet<>(Arrays.asList("+", "-", "/", "*"));
    private final static Set<String> arabic_nums = new HashSet<>(Arrays.asList("1", "2", "3", "4", "5",
                                                                        "6", "7", "8", "9", "10"));
    private final static Set<String> roman_nums = new HashSet<>(Arrays.asList("I", "II", "III", "IV", "V",
                                                                        "VI", "VII", "VIII", "IX", "X"));

    public static boolean isCorrectInput(String[] input) {
        return input.length == 3 && operations.contains(input[1]);
    }

    public static String getTypeOfNums(String lval, String rval) throws IOException {
        if (arabic_nums.contains(lval) && arabic_nums.contains(rval)) {
            return "arabic";
        }
        if (roman_nums.contains(lval) && roman_nums.contains(rval)) {
            return "roman";
        }
        throw new IOException();
    }

    public static String romanToArabic(String val) {
        Map<String, String> table = new HashMap<>();
        table.put("I", "1");
        table.put("II", "2");
        table.put("III", "3");
        table.put("IV", "4");
        table.put("V", "5");
        table.put("VI", "6");
        table.put("VII", "7");
        table.put("VIII", "8");
        table.put("IX", "9");
        table.put("X", "10");
        return table.get(val);
    }

    public static String arabicToRoman(String val) {
        String[] roman = {"C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
        int[] arabic = {100, 90, 50, 40, 10, 9, 5, 4, 1};
        StringBuilder res = new StringBuilder();
        int v = Integer.parseInt(val);
        while (v != 0) {
            for (int i = 0; i < roman.length; ++i) {
                if (v / arabic[i] != 0) {
                    int amount = v / arabic[i];
                    for (int j = 0; j < amount; ++j) {
                        res.append(roman[i]);
                    }
                    v -= amount * arabic[i];
                }
            }
        }
        return res.toString();
    }

    public static String calcRoman(String lval, String rval, String op) throws IOException {
        String arabic_lval = romanToArabic(lval), arabic_rval = romanToArabic(rval);
        String arabic_res = calcArabic(arabic_lval, arabic_rval, op);
        if (Integer.parseInt(arabic_res) < 1) {
            throw new IOException();
        }
        return arabicToRoman(arabic_res);
    }

    public static String calcArabic(String lval, String rval, String op) {
        int a = Integer.parseInt(lval);
        int b = Integer.parseInt(rval);
        return switch (op) {
            case "+" -> String.valueOf(a + b);
            case "-" -> String.valueOf(a - b);
            case "*" -> String.valueOf(a * b);
            case "/" -> String.valueOf(a / b);
            default -> "Error";
        };
    }

    public static String calc(String input) throws IOException {
        String[] split_input = input.split(" ");
        if (!isCorrectInput(split_input)) {
            throw new IOException();
        }
        String a = split_input[0], b = split_input[2], op = split_input[1];
        return (getTypeOfNums(a, b).equals("arabic")) ? calcArabic(a, b, op) : calcRoman(a, b, op);
    }

    public static void main(String[] args) throws IOException {
        Scanner scan = new Scanner(System.in);
        String input = scan.nextLine();
        System.out.println(calc(input));
    }
}
