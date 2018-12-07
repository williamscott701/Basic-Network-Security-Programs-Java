
import java.io.*;
import java.math.BigInteger;
import java.net.*;
import java.util.ArrayList;

//@author William Scott
public class NetworkSecurityLabExp6DigitalSignatureAlgorithmReceiver {
    //Network Security Lab - Exp 6 - Digital Signature Algorithm - Receiver

    public static String t1, t2[];
    public static BigInteger p, q, g, k, r, y, s, w, v, u1, u2, p1q, hm;
    public static ArrayList al=new ArrayList();

    public static void main(String[] args) throws IOException {
        System.out.println("UR12CS135 - DSA Receiver");
        Socket so = new Socket("127.0.0.1", 3000);
        System.out.println("\nConnected to Sender\n");

        InputStream is = so.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        System.out.println("----------Receiving Values----------");
        t1 = br.readLine();
        t2 = t1.split(":");
        p = new BigInteger(t2[0]);
        q = new BigInteger(t2[1]);
        g = new BigInteger(t2[2]);

        System.out.println("Received p: " + p);
        System.out.println("Received q: " + q);
        System.out.println("Received g: " + g);

        y = new BigInteger(br.readLine());
        System.out.println("\nReceived y: " + y);

        t1 = br.readLine();
        t2 = t1.split(":");
        hm = new BigInteger(t2[0]);
        s = new BigInteger(t2[1]);
        r = new BigInteger(t2[2]);

        System.out.println("\nReceived h(m): " + hm);
        System.out.println("Received s: " + s);
        System.out.println("Received r: " + r);

        System.out.println("\n--------------Verifying-------------");
        w = getinverse(q, s).mod(q);
        //w=s.modInverse(q);
        System.out.println("w: " + w);

        u1 = (hm.multiply(w)).mod(q);
        u2 = (r.multiply(w)).mod(q);
        System.out.println("u1: " + u1);
        System.out.println("u2: " + u2);
        v = (((g.pow(u1.intValue())).multiply(y.pow(u2.intValue()))).mod(p)).mod(q);
        System.out.println("v: " + v);

        System.out.println("\n------------Testing v & r-----------");
        if (v.equals(r)) {
            System.out.println("v and r are equal, hence Authenticated");
        } else {
            System.out.println("v and r are not equal, hence not Authenticated");
        }
    }

    public static BigInteger getinverse(BigInteger a, BigInteger b) {
        long i = 0, count=0;
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

}
