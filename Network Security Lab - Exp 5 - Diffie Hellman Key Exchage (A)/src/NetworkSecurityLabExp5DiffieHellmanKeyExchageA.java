//@author William Scott

import java.io.*;
import java.math.*;
import java.net.*;
import java.util.*;

public class NetworkSecurityLabExp5DiffieHellmanKeyExchageA {

    public static Scanner in = new Scanner(System.in);
    public static BigInteger q, alpha, xa, xb, ya, yb, key;
    public static long tl;
    public static ArrayList al = new ArrayList();

    public static void main(String[] args) throws IOException {
        System.out.println("------User A------");
        ServerSocket ss = new ServerSocket(3000);
        System.out.println("Waiting for User B to Connect");
        Socket s = ss.accept();
        System.out.println("\nUser B Connected!");

        InputStream is = s.getInputStream();
        OutputStream os = s.getOutputStream();
        PrintWriter pw = new PrintWriter(os, true);
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        do {
            System.out.println("\nEnter Prime q");
            q = in.nextBigInteger();
        } while (!prime(q));

        pw.println(q);
        System.out.println("Sent q to User B");

        primitiveroot(q);
        pw.println(alpha);
        System.out.println("Sent alpha to User B");
        tl = 2 + (int) (Math.random() * (q.longValue() - 2));
        xa = BigInteger.valueOf(tl);

        System.out.println("\nxa: " + xa);
        ya = alpha.modPow(xa, q);
        System.out.println("ya: " + ya);
        pw.println(ya);
        System.out.println("Sent ya to User B");
        pw.println(q);

        System.out.println("Waiting for User B to send yb");
        yb = BigInteger.valueOf(Integer.valueOf(br.readLine()));

        key = yb.modPow(xa, q);
        System.out.println("Key: " + key);
    }

    public static void primitiveroot(BigInteger q) {
        Boolean k = false;
        int i = 2;
        alpha = BigInteger.valueOf(i);
        BigInteger bj, bk;
        do {
            k = false;
            alpha = BigInteger.valueOf(i);
            System.out.println("\n*i: " + alpha);
            for (int j = 1; j < q.intValue(); j++) {
                bj = BigInteger.valueOf(j);
                bk = alpha.modPow(bj, q);
                System.out.println(bk);
                if (al.contains(bk)) {
                    k = true;
                    break;
                } else {
                    al.add(bk);
                }
            }
            if (k == true) {
                i++;
                al.clear();
            }
        } while (k);
        System.out.println("Alpha Selected: " + alpha);
    }

    public static boolean prime(BigInteger t) {
        int r = 0;
        int a =t.intValue();
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

}
