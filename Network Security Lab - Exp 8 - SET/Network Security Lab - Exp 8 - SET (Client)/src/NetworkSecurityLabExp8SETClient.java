
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.Socket;
import java.security.MessageDigest;
import java.util.Random;
import java.util.Scanner;

// @author William Scott
public class NetworkSecurityLabExp8SETClient {
    //UR12CS135 - P.William Scott - Exp 8 - SET (Client)

    private static Scanner in = new Scanner(System.in);
    private static String oi, pi, oimd, pimd, pomd;
    private static BigInteger message, ds;

    public static void main(String[] args) throws Exception {
        System.out.println("UR12CS135 - SET(Client)");
        RSA r = new RSA(1024);
        System.out.println("Enter Payment Information");
        pi = in.nextLine();
        System.out.println("Enter Order Information");
        oi = in.nextLine();

        pimd = sha512(pi);
        oimd = sha512(oi);
        pomd = sha512(pimd.concat(oimd));
        message = new BigInteger(pomd, 16);
        ds = r.encrypt(message);

        System.out.println("\npimd           :" + pimd);
        System.out.println("oimd           :" + oimd);
        System.out.println("pomd           :" + pomd);
        System.out.println("Message        :" + message);
        System.out.println("Dual Signature :" + ds);

        Socket smerchant = new Socket("localhost", 3000);
        Socket sbank = new Socket("localhost", 4000);
        PrintWriter pwmerchant = new PrintWriter(smerchant.getOutputStream(), true);
        PrintWriter pwbank = new PrintWriter(sbank.getOutputStream(), true);

        pwmerchant.println(ds);
        pwmerchant.println(oi);
        pwmerchant.println(pimd);
        pwmerchant.println(r.getD());
        pwmerchant.println(r.getN());

        pwbank.println(ds);
        pwbank.println(pi);
        pwbank.println(oimd);
        pwbank.println(r.getD());
        pwbank.println(r.getN());

        pwmerchant.close();
        pwbank.close();
        smerchant.close();
        sbank.close();
    }

    private static String sha512(String a) throws Exception {
        MessageDigest sha = MessageDigest.getInstance("SHA-512");
        sha.update(a.getBytes());
        return new BigInteger(1, sha.digest()).toString(16);
    }
}

class RSA {

    private BigInteger p, q, n, pin, d, e = new BigInteger("5");
    private Random r = new Random();

    RSA(int i) {
        this.p = BigInteger.probablePrime(i, this.r);
        this.q = BigInteger.probablePrime(i, this.r);
        this.n = this.p.multiply(this.q);
        this.pin = this.p.subtract(BigInteger.ONE).multiply(this.q.subtract(BigInteger.ONE));
        while (this.pin.gcd(this.e).doubleValue() > 1) {
            this.e = this.e.add(new BigInteger("2"));
        }
        this.d = this.e.modInverse(this.pin);
    }

    public BigInteger getD() {
        return this.d;
    }

    public BigInteger getN() {
        return this.n;
    }

    public BigInteger encrypt(BigInteger m) {
        return m.modPow(this.e, this.n);
    }

    public BigInteger decrypt(BigInteger c) {
        return c.modPow(this.d, this.n);
    }
}
