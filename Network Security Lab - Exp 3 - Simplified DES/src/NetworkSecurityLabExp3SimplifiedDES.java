//@author William Scott

import java.util.Scanner;

public class NetworkSecurityLabExp3SimplifiedDES {

    public static String iptext = "00101000", key = "1100011110", k1 = "", k2 = "", mantext = "", fkval = "", optext = "";
    public static int s0[][] = {{1, 0, 3, 2}, {3, 2, 1, 0}, {0, 2, 1, 3}, {3, 1, 3, 2}}, s1[][] = {{0, 1, 2, 3}, {2, 0, 1, 3}, {3, 0, 1, 0}, {2, 1, 0, 3}};
    public static Scanner in = new Scanner(System.in);

    public static void main(String[] args) {
        boolean r = true;
        int choice;
        System.out.println("Simplified DES - UR12CS135");
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
                    encode();
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
        System.out.println("Enter Key (Note: Key should be 10 bit)");
        key = in.next();
        genkey();
    }

    public static void encode() {
        System.out.println("\n---------Encode--------\nEnter Plain Text");
        iptext = in.next();
        rollout(k1, k2);
        System.out.println("Cipher Text: " + optext);
    }

    public static void decode() {
        System.out.println("\n---------Decode--------\nEnter Cipher Text");
        iptext = in.next();
        rollout(k2, k1);
        System.out.println("Plain Text: " + optext);
    }

    public static void rollout(String a, String b) {
        System.out.println("\n---Manipulating----");
        mantext = ip(iptext);
        System.out.println(mantext);
        fk(mantext, a);
        sw();
        fk(fkval, b);
        optext = ipinverse(fkval);
    }

    public static void genkey() {
        System.out.println("\n--Generating Keys--");
        String p = "", l = "", r = "", t = "";
        p = p10(key);
        System.out.println(p);
        l = p.substring(0, 5);
        r = p.substring(5, 10);
        System.out.println(l + " " + r);
        l = leftshift(l);
        r = leftshift(r);
        t = l + r;
        System.out.println(l + " " + r);
        System.out.println(t);
        k1 = p8(t);
        System.out.println("Key 1: " + k1);
        l = leftshift(l);
        r = leftshift(r);
        l = leftshift(l);
        r = leftshift(r);
        t = l + r;
        k2 = p8(t);
        System.out.println("Key 2: " + k2);
    }

    public static void fk(String p, String a) {
        String l = "", r = "", la = "", ra = "", t = "", row = "", col = "";
        int lval, rval;
        la = p.substring(0, 4);
        ra = p.substring(4, 8);
        t = ep(ra);
        System.out.println(t);
        t = xor(t, a);
        System.out.println(t);
        l = t.substring(0, 4);
        r = t.substring(4, 8);
        row = l.charAt(0) + "" + l.charAt(3);
        col = l.charAt(1) + "" + l.charAt(2);
        lval = s0[getdecimalval(row)][getdecimalval(col)];
        l = getbinaryval(lval);
        row = r.charAt(0) + "" + r.charAt(3);
        col = r.charAt(1) + "" + r.charAt(2);
        rval = s1[getdecimalval(row)][getdecimalval(col)];
        r = getbinaryval(rval);
        t = l + "" + r;
        System.out.println(t);
        t = p4(t);
        System.out.println(t);
        la = xor(t, la);
        fkval = la + "" + ra;
        System.out.println(fkval);
    }

    public static void sw() {
        System.out.println("*Swap");
        fkval = fkval.substring(4, 8) + "" + fkval.substring(0, 4);
    }

    public static String ep(String a) {
        System.out.println("*Expansion Permutation");
        String b = "";
        b = a.charAt(3) + "" + a.charAt(0) + "" + a.charAt(1) + "" + a.charAt(2) + "" + a.charAt(1) + "" + a.charAt(2) + "" + a.charAt(3) + "" + a.charAt(0);
        return b;
    }

    public static String p10(String a) {
        System.out.println("*p10");
        String b = "";
        b = a.charAt(2) + "" + a.charAt(4) + "" + a.charAt(1) + "" + a.charAt(7) + "" + a.charAt(3) + "" + a.charAt(9) + "" + a.charAt(0) + "" + a.charAt(8) + "" + a.charAt(7) + "" + a.charAt(5);
        return b;
    }

    public static String p8(String a) {
        System.out.println("*p8");
        String b = "";
        b = a.charAt(5) + "" + a.charAt(2) + "" + a.charAt(6) + "" + a.charAt(3) + "" + a.charAt(7) + "" + a.charAt(4) + "" + a.charAt(9) + "" + a.charAt(8);
        return b;
    }

    public static String p4(String a) {
        String b = "";
        System.out.println("*p4");
        b = a.charAt(1) + "" + a.charAt(3) + "" + a.charAt(2) + "" + a.charAt(0);
        return b;
    }

    public static String ip(String a) {
        System.out.println("*Initial Permutation");
        String b = "";
        b = a.charAt(1) + "" + a.charAt(5) + "" + a.charAt(2) + "" + a.charAt(0) + "" + a.charAt(3) + "" + a.charAt(7) + "" + a.charAt(4) + "" + a.charAt(6);
        return b;
    }

    public static String ipinverse(String a) {
        System.out.println("*Inverse IP");
        String b = "";
        b = a.charAt(3) + "" + a.charAt(0) + "" + a.charAt(2) + "" + a.charAt(4) + "" + a.charAt(6) + "" + a.charAt(1) + "" + a.charAt(7) + "" + a.charAt(5);
        return b;
    }

    public static String leftshift(String a) {
        return a.substring(1, a.length()) + a.charAt(0);
    }

    public static String xor(String a, String b) {
        String val = "";
        for (int i = 0; i < a.length(); i++) {
            val += (a.charAt(i) ^ b.charAt(i)) + "";
        }
        return val;
    }

    public static int getdecimalval(String a) {
        int i = 0;
        if (a.charAt(0) == '0' && a.charAt(1) == '0') {
            return 0;
        } else if (a.charAt(0) == '0' && a.charAt(1) == '1') {
            return 1;
        } else if (a.charAt(0) == '1' && a.charAt(1) == '0') {
            return 2;
        } else {
            return 3;
        }
    }

    public static String getbinaryval(int a) {
        if (a == 0) {
            return "00";
        } else if (a == 1) {
            return "01";
        } else if (a == 2) {
            return "10";
        } else {
            return "11";
        }
    }
}
