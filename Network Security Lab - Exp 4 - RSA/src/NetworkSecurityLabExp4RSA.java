//@author William Scott

import java.math.BigInteger;
import java.util.Random;
import java.util.Scanner;

public class NetworkSecurityLabExp4RSA {
    
    public static Scanner in = new Scanner(System.in);
    public static BigInteger m, c, n, d, e = BigInteger.valueOf(5), pin;
    public static int p = 17, q = 11;
    
    public static void main(String[] args) {
        boolean r = true;
        int choice;
        System.out.println("RSA - UR12CS135");
        setkey();
        do {
            System.out.println("\n\n---------Main Menu---------\n1.Change Key \n2.Encode \n3.Decode \n4.Exit");
            choice = in.nextInt();
            in.nextLine();
            switch (choice) {
                case 1:
                    setkey();
                    break;
                case 2:
                    callencode();
                    break;
                case 3:
                    decode();
                    break;
                case 4:
                    r = false;
                    break;
                default:
                    System.out.println("Invalid Option!, Try Again...");
                    break;
            }
        } while (r == true);
    }
    
    public static void setkey() {
        System.out.println("\n--------Set Key--------");
        int y;
        Random r = new Random();
        BigInteger t = BigInteger.ONE;
        getprimes();
        n = BigInteger.valueOf(p * q);
        pin = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
        System.out.println("n   : " + n);
        System.out.println("p(n): " + pin);
        
        while (pin.gcd(e).doubleValue() > 1) {
            e = e.add(new BigInteger("2"));
        }
        d = e.modInverse(pin);
        System.out.println("d final: " + d);
        System.out.println("e final: " + e);
    }
    
    private static int gcd(int a, int b) {
        while (b > 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }
    
    public static void getprimes() {
        do {
            System.out.print("Enter Prime Number 1 (p):");
            p = in.nextInt();
            in.nextLine();
        } while (!checkprime(p));
        do {
            System.out.print("Enter Prime Number 2 (q):");
            q = in.nextInt();
            in.nextLine();
        } while (!checkprime(q));
    }
    
    public static boolean checkprime(int a) {
        int r = 0;
        if (a <= 1) {
            return false;
        }
        for (int i = 2; i < a; i++) {
            if (a % i == 0) {
                return false;
            }
        }
        return true;
    }
    
    public static void callencode() {
        int a;
        System.out.println("\n---------Encode--------\nEnter Plain Text");
        String b = in.next();
        for (int i = 0; i < b.length(); i++) {
            a = (int) b.charAt(i);
            encode(a);
        }
    }
    
    public static void encode(int a) {
        m = BigInteger.valueOf(a);
        System.out.println("\n*" + (char) a);
        System.out.println("m: " + m);
        System.out.println("e: " + e);
        System.out.println("n: " + n);
        c = m.modPow(e, n);
        int i = c.intValue();
        System.out.println("Cipher Text(ASCII): " + c);
        System.out.println("Cipher Text(Char) : " + (char) i);
    }
    
    public static void decode() {
        System.out.println("\n---------Decode--------\nEnter Cipher Text ASCII Value");
        c = in.nextBigInteger();
        System.out.println("\nc: " + c);
        System.out.println("d: " + d);
        System.out.println("n: " + n);
        m = c.modPow(d, n);
        int i = m.intValue();
        System.out.println("Plain Text(ASCII): " + m);
        System.out.println("Plain Text(Char) : " + (char) i);
    }
}
