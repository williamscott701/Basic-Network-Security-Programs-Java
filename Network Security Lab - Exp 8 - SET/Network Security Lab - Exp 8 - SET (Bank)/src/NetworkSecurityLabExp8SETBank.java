
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.MessageDigest;

// @author William Scott
public class NetworkSecurityLabExp8SETBank {
    //UR12CS135 - P.William Scott - Exp 8 - SET (Bank)

    public static BigInteger ds, d, n, a, b;
    public static String pi, oimd, st;
    public static BufferedReader br;

    public static void main(String[] args) throws Exception {
        System.out.println("UR12CS135 - SET(Bank)");
        ServerSocket ss = new ServerSocket(4000);
        Socket s = ss.accept();
        br = new BufferedReader(new InputStreamReader(s.getInputStream()));

        ds = new BigInteger(br.readLine());
        pi = br.readLine();
        oimd = br.readLine();
        d = new BigInteger(br.readLine());
        n = new BigInteger(br.readLine());

        a = ds.modPow(d, n);
        st = sha512(sha512(pi).concat(oimd));
        b = new BigInteger(st, 16);

        System.out.println("\n-----Received Values------");
        System.out.println("Dual Signature      :" + ds);
        System.out.println("pi                  :" + pi);
        System.out.println("oimd                :" + oimd);
        System.out.println("Client's Public Key :" + d);

        System.out.println("\n------Calculated Values-------");
        System.out.println("H(H(pi)||oimd)      :" + b);
        System.out.println("D(k,ds)             :" + a);

        if (a.toString().compareTo(b.toString()) == 0) {
            System.out.println("Client's signature is verified");
        }

        br.close();
        ss.close();
        s.close();

    }

    private static String sha512(String a) throws Exception {
        MessageDigest sha = MessageDigest.getInstance("SHA-512");
        sha.update(a.getBytes());
        return new BigInteger(1, sha.digest()).toString(16);
    }

    public static BigInteger decrypt(BigInteger c, BigInteger e, BigInteger n) {
        return c.modPow(e, n);
    }
}
