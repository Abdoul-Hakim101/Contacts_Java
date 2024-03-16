//package contacts;
//
//import java.util.Scanner;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//public class Test {
//    public static void main(String[] args) {
//        Scanner scanner = new Scanner(System.in);
//        System.out.println(isValidPhoneNumber(scanner.nextLine().trim()));
//    }
//    private static boolean isValidPhoneNumber(String phoneNumber) {
//        String regex = "^\\+?\\d* ?(?:\\(\\w{2,}\\) ?)?(?:\\w{2,}[- ])*\\w{2,}$";
//        Pattern pattern = Pattern.compile(regex);
//        Matcher matcher = pattern.matcher(phoneNumber);
//        return matcher.matches();
//    }
//
//
//}
