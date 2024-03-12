
// main approach is breaking down the number in multiple of three and solve particular case
import java.util.HashMap;

public class NumberToWords {

    // number Formatter
    public static String getInFormate(String str) throws Exception {
        if (str.isEmpty()) {
            throw new Exception("Please Enter Number");
        }
        return helperFormatter(str);
    }

    private static String helperFormatter(String str) {
        StringBuilder sb = new StringBuilder();

        // if entered number is negative
        if (str.charAt(0) == '-') {
            sb.append('-');
            str = str.substring(1);
        }

        int length = str.length();

        // if number is less than 1000, don't need to formated just return it
        if (length < 4) {
            sb.append(str);
            return sb.toString();
        }

        // if number is not in multiple of three then we need remainder of length
        int rem = length % 3;

        // it formates number in multiple of three and remaining numbers
        for (int i = 0; i < length; i++) {
            if (i != 0 && (i == rem || (i - rem) % 3 == 0)) {
                sb.append(", ");
            }

            sb.append(str.charAt(i));
        }

        return sb.toString();
    }

    // for Numbers
    public static String getInWords(int num) throws Exception {

        if (num < 0) {
            if (num == Integer.MIN_VALUE) {
                throw new Exception(
                        "please Enter Number between " + (Integer.MIN_VALUE + 1) + " to " + Integer.MAX_VALUE);
            }
            num *= -1;
            return "Negative " + helper(new StringBuilder(), new StringBuilder(String.valueOf(num)));
        }

        return helper(new StringBuilder(), new StringBuilder(String.valueOf(num)));
    }

    // for large numbers directly Enter String
    public static String getInWords(String str) throws Exception {

        if (str.isEmpty()) {
            throw new Exception("Please Enter Number between -10^24 to 10^24");
        }

        StringBuilder sb = new StringBuilder();
        StringBuilder given = new StringBuilder(str);

        if (given.charAt(0) == '-') {
            sb.append(" negative ");
            given.delete(0, 1);
        }

        if (given.length() > 24) {
            throw new Exception("Please Enter Number between -10^24 to 10^24");
        }

        for (int i = 0; i < given.length(); i++) {
            if (given.charAt(i) < '0' || given.charAt(i) > '9') {
                throw new Exception("Given is not valid number please Enter valid number");
            }
        }

        return helper(sb, given);
    }

    public static String helper(StringBuilder sb, StringBuilder str) {

        if (str.length() == 1 && str.charAt(0) == '0') {
            return "Zero";
        }

        // if some one only enter zeros in input then this condition applied
        if (str.charAt(0) == '0') {
            while (!str.isEmpty() && str.charAt(0) == '0') {
                str.delete(0, 1);
            }
            if (str.isEmpty()) {
                return "Zero";
            }
        }

        while (!str.isEmpty()) {

            int length = str.length();
            int rem = length % 3;
            char ch = str.charAt(0);

            // number is greater than three then break the number based on conditions
            if (length > 3) {
                // number size is multiple of 3 then threeDigits and twoDigits combination
                // applied
                // and based on their size tag will be given by tag method
                if (rem == 0 && ch != '0') {

                    sb.append(threeDigits(ch))
                            .append(twoDigits(str.charAt(1), str.charAt(2)))
                            .append(tag(length - 2));

                    // after using three digits delete them
                    str.delete(0, 3);
                    continue;
                }
                // if rem == 1 then one Extra digit means one digit method applied
                // tag will be given based on size of number
                if (rem == 1 && ch != '0') {
                    sb.append(oneDigit(ch - '0'))
                            .append(tag(length));

                    // after using digit delete it
                    str.delete(0, 1);
                    continue;
                }
                // this condition will true when extra digit size is 2
                // if first digit is zero then don't take it and all this handle by twoDigits
                // method
                if (ch != '0') {
                    sb.append(twoDigits(ch, str.charAt(1))).append(tag(length - 1));

                    // after using 2 digits delete them
                    str.delete(0, 2);
                    continue;
                }
            }

            // if digit is zero don't take it
            if (ch == '0') {
                str.delete(0, 1);
                continue;
            }
            // above all the condition applied when number is less than three
            if (rem == 0) {
                sb.append(threeDigits(ch));
            }

            else if (rem == 1) {
                sb.append(oneDigit(ch - '0'));
            }

            else {
                sb.append(twoDigits(ch, str.charAt(1)));
                str.delete(0, 1);
            }
            str.delete(0, 1);
        }
        sb.delete(0, 1);
        return sb.toString();
    }

    // Above all methods are helper methods and important for making number to
    // words------------------------------------------------

    // it handles tag of entire number (thousand,million ,etc)
    private static String tag(int len) {

        HashMap<Integer, String> map = new HashMap<>();

        map.put(4, " thousand");
        map.put(5, " thousand");
        map.put(7, " million");
        map.put(8, " million");
        map.put(10, " billion");
        map.put(11, " billion");
        map.put(13, " trillion");
        map.put(14, " trillion");
        map.put(16, " quadrillion");
        map.put(17, " quadrillion");
        map.put(19, " quintillion");
        map.put(20, " quintillion");
        map.put(22, " sextillion");
        map.put(23, " sextillion");

        return map.get(len);
    }

    // it handles only one digit (0 - 9)
    private static String oneDigit(int i) {
        String[] digits = { "", " one", " two", " three", " four", " five", " six", " seven", " eight", " nine" };
        return digits[i];
    }

    // it handles two digits numbers like (10 - 90)
    private static String twoDigits(char firstDigit, char secondDigit) {

        // if firstDigit is 1 then 10 - 19 is special answer
        if (firstDigit == '1') {
            return special(secondDigit - '0');
        }
        // else answer is combination of below two functions
        return helperTwoDigits(firstDigit - '0') + oneDigit(secondDigit - '0');
    }

    private static String helperTwoDigits(int i) {
        String[] arr = { "", "", " twenty", " thirty", " forty", " fifty", " sixty", " seventy", " eighty", " ninety" };
        return arr[i];
    }

    // it handles three digits numbers like (100 - 999) [internally call one digit
    // method]
    private static String threeDigits(char ch) {
        return oneDigit(ch - '0') + " hundred";
    }

    // these are 2 digit special numbers (10 - 19) bcz no combination happen for
    // these numbers
    private static String special(int i) {
        String[] special = { " ten", " eleven", " twelve", " thirteen", " fourteen", " fifteen", " sixteen",
                " seventeen", " eighteen", " nineteen" };
        return special[i];
    }
}
