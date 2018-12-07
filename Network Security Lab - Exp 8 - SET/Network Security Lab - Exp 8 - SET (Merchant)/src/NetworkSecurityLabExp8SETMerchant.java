
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.MessageDigest;

// @author William Scott
public class NetworkSecurityLabExp8SETMerchant {
    //UR12CS135 - P.William Scott - Exp 8 - SET (Merchant)

    public static BigInteger ds, d, n, a, b;
    public static String pimd, st, oi;

    public static void main(String[] args) throws Exception {
        System.out.println("UR12CS135 - SET(Merchant)");
        ServerSocket ss = new ServerSocket(3000);
        Socket s = ss.accept();
        BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));

        ds = new BigInteger(br.readLine());
        oi = br.readLine();
        pimd = br.readLine();
        d = new BigInteger(br.readLine());
        n = new BigInteger(br.readLine());

        a = ds.modPow(d, n);
        st = sha512(pimd.concat(sha512(oi)));
        b = new BigInteger(st, 16);

        System.out.println("\n-------Received values------");
        System.out.println("Dual Signature      :" + ds);
        System.out.println("oi                  :" + oi);
        System.out.println("pimd                :" + pimd);
        System.out.println("Client's Public Key :" + d);

        System.out.println("\n------Calculated Values-------");
        System.out.println("H(pimd||H(oi))      :" + b);
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

}
