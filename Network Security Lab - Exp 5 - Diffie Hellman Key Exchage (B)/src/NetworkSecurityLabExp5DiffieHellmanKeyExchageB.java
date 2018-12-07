//@author William Scott

import java.io.*;
import java.math.BigInteger;
import java.net.*;
import java.util.Scanner;

public class NetworkSecurityLabExp5DiffieHellmanKeyExchageB {

    public static Scanner in = new Scanner(System.in);
    public static BigInteger q, alpha, ya, yb, xb, key;
    public static long tl;

    public static void main(String[] args) throws IOException {
        System.out.println("------User B------");
        Socket s = new Socket("127.0.0.1", 3000);
        System.out.println("\nConnected to User A\n");

        InputStream is = s.getInputStream();
        OutputStream os = s.getOutputStream();
        PrintWriter pw = new PrintWriter(os, true);
        BufferedReader brs = new BufferedReader(new InputStreamReader(is));

        System.out.println("\nWaiting for User A to Send q");
        q = BigInteger.valueOf(Integer.valueOf(brs.readLine()));
        System.out.println("q Received: " + q);

        System.out.println("\nWaiting for User A to Send alpha");
        alpha = BigInteger.valueOf(Integer.valueOf(brs.readLine()));
        System.out.println("alpha Received: " + alpha);

        System.out.println("\nWaiting for User A to Send ya");
        ya = BigInteger.valueOf(Integer.valueOf(brs.readLine()));
        System.out.println("ya Received: " + ya);

        tl = 2 + (int) (Math.random() * (q.longValue() - 2));
        xb = BigInteger.valueOf(tl);
        System.out.println("\nxb: " + xb);
        yb = alpha.modPow(xb, q);
        System.out.println("yb: " + yb);
        pw.println(yb);
        System.out.println("Sent yb to User A");

        key = ya.modPow(xb, q);
        System.out.println("Key: " + key);
    }
}
