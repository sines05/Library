package LibraryManagementSystem.util;

import java.util.regex.Pattern;

public class RegExPatterns {

    public static boolean namePattern(String name) {
        return !Pattern.matches("[A-Za-z\\s]{2,}", name);
    }

    public static boolean contactNoPattern(String contactNo) {
        return !Pattern.matches("0\\d{9}", contactNo);
    }

    public static boolean emailPattern(String email) {
        return !Pattern.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", email);
    }

    public static boolean idPattern(String value) {
        return !Pattern.matches("(\\d+)", value);
    }

    public static boolean otpPattern(String otp) {
        return !Pattern.matches("[0-9]{6}", otp);
    }

    public static boolean passwordPattern(String password) {
        return !Pattern.matches(".{5,25}", password);
    }

}