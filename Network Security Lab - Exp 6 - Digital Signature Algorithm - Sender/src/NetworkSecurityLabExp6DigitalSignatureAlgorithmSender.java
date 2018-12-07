
import java.io.*;
import java.math.*;
import java.net.*;
import java.util.*;

//@author William Scott
public class NetworkSecurityLabExp6DigitalSignatureAlgorithmSender {
    //Network Security Lab - Exp 6 - Digital Signature Algorithm - Sender

    public static BufferedReader br;
    public static String input, hmt = "", opr;
    public static BigInteger p, q, h, g, x, y, k, r, s, w, v, u1, u2, p1q, hm;
    public static int ti = 0;
    public static Random rand = new Random();
    public static ArrayList al = new ArrayList();

    public static void main(String[] args) throws IOException {
        System.out.println("UR12CS135 - DSA Sender");

        ServerSocket ss = new ServerSocket(3000);
        System.out.println("\nWaiting for Receiver to Connect");
        Socket so = ss.accept();
        System.out.println("\nReceiver Connected!");

        br = new BufferedReader(new FileReader("..\\Exp 6 - Input File.doc"));
        input = br.readLine();

        OutputStream os = so.getOutputStream();
        PrintWriter pw = new PrintWriter(os, true);
        hm = BigInteger.valueOf(input.hashCode());

        System.out.println("Input String : " + input);
        System.out.println("Hashed Input : " + hm);

        System.out.println("\n----Global Public-Key Components----");
        getpq();
        p1q = p.subtract(BigInteger.ONE).divide(q);
        do {
            h = getrandom(1, p.subtract(BigInteger.ONE));
            g = h.modPow(p1q, p);
        } while (g.compareTo(BigInteger.ONE) != 1);
        System.out.println("h: " + h);
        System.out.println("g: " + g);

        opr = p + ":" + q + ":" + g;
        System.out.println("\nSending p:q:g:h(m): " + opr);
        pw.println(opr);

        System.out.println("\n---------User’s Private Key---------");
        x = getrandom(0, q);
        System.out.println("x: " + x);

        System.out.println("\n---------User’s Public Key----------");
        y = g.modPow(x, p);
        System.out.println("y: " + y);
        System.out.println("\nSending y: " + y);
        pw.println(y);

        System.out.println("\n--User's Per-Message Secret Number--");
        k = getrandom(0, q);
        System.out.println("k: " + k);

        System.out.println("\n--------------Signing---------------");
        r = g.modPow(k, p).mod(q);
        System.out.println("r: " + r);

        s = getinverse(q, k).multiply(hm.add(x.multiply(r))).mod(q);
        System.out.println("s: " + s);

        opr = hm + ":" + s + ":" + r;
        System.out.println("Sending h(m):s:r: " + opr);
        pw.println(opr);
    }

    public static BigInteger getrandom(int a, BigInteger b) {
        BigInteger c;
        int ca, cb;
        do {
            c = new BigInteger(b.bitLength(), rand);
            ca = c.compareTo(BigInteger.valueOf(a));
            cb = c.compareTo(b);
            System.out.println(" *f: " + c);
        } while (ca != 1 || cb != -1);
        return c;
    }

    public static BigInteger getinverse(BigInteger a, BigInteger b) throws IOException {
        long i = 0, count = 0;
        BigInteger t, ra;
        al.clear();
        if (b.intValue() == 0) {
            System.out.println("Problem Finding the invesrse, as the denominator is 0, Please run Again!");
            System.exit(0);
        }
        do {
            t = a.multiply(BigInteger.valueOf((int) i)).add(BigInteger.ONE);
            ra = t.mod(b);
            if (!al.contains(q)) {
                al.add(q);
                count++;
            }
            i++;
            System.out.println("--i: " + count + "  t : " + t + "  ra: " + ra);
        } while (ra.compareTo(BigInteger.ZERO) != 0);
        System.out.println(" inv: " + t.divide(b));
        return t.divide(b);
    }

    public static void getpq() {

        p = BigInteger.probablePrime(15, rand);
        System.out.println("p: " + p);
        System.out.println("p.long: " + p.longValue());

        al.clear();
        BigInteger bt;
        long t = 0, tt;
        do {
            do {
                tt = (long) (2 + (Math.random() * (p.longValue() - 2)));
                System.out.println("   *q: " + tt);
            } while (!checkprime(tt));
            q = BigInteger.valueOf(tt);
            bt = p.subtract(BigInteger.ONE).mod(q);
            if (!al.contains(q)) {
                al.add(q);
                t++;
            }
            System.out.println(" t : " + t + " q: " + q + " bt : " + bt);
        } while (bt != BigInteger.ZERO);
        System.out.println("\nq: " + q);

    }

    public static boolean checkprime(long a) {
        if (a <= 1) {
            return false;
        }
        for (long i = 2; i < a; i++) {
            if (a % i == 0) {
                return false;
            }
        }
        return true;
    }
}
