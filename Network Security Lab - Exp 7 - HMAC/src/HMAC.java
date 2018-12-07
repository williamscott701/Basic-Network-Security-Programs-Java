
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Scanner;

public class HMAC {

    public static String p, k, s, messageip, haship, ipad = "00110110", opad = "01011100";
    public static Scanner in = new Scanner(System.in);

    public static void main(String[] args) throws Exception {
        getvalues();
        p = calculate(ipad, messageip);
        System.out.println("First Hash Output         : " + p);
        p = calculate(opad, p);
        System.out.println("Output of HMAC (SHA-512)  : " + p);
    }

    public static void getvalues() {
        System.out.print("\nEnter 8 bit Key(k)  : ");
        k = in.nextLine();
        System.out.print("Enter Input Message : ");
        messageip = in.nextLine();
    }

    public static String calculate(String pad, String message) throws Exception {
        s = xor(k, pad);
        haship = Integer.toHexString(Integer.parseInt(s, 2)) + message;
        System.out.println("\nAppended Input to SHA-512 : " + haship);
        return sha512(haship);
    }

    public static String xor(String a, String b) {
        String c = "";
        for (int i = 0; i < a.length(); i++) {
            c += a.charAt(i) ^ b.charAt(i);
        }
        return c;
    }

    public static String sha512(String a) throws Exception {
        MessageDigest sha = MessageDigest.getInstance("SHA-512");
        sha.update(a.getBytes());
        return new BigInteger(1, sha.digest()).toString(16);
    }
}
