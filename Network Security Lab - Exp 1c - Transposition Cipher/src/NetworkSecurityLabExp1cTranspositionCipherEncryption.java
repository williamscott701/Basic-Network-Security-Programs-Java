import java.util.Scanner;

//@author William Scott
public class NetworkSecurityLabExp1cTranspositionCipherEncryption{

    public static String input, inputcpy, output, alph;
    public static int key, a, b;
    public static char m[][];
    public static Scanner in = new Scanner(System.in);

    public static void main(String[] args) {
        boolean r = true;
        int choice;
        System.out.println("\nTransposition Cipher - UR12CS135");
        setkey();
        do {
            System.out.println("\n\n---------Main Menu---------\n1.Change Key\n2.Encode\n3.Decode \n4.Exit");
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

    public static void encode() {
        System.out.println("\n---------Encode--------");
        settext();
        for (int i = 0; i < b; i++) {
            m[0][i] = Integer.toString(key).charAt(i);
        }
        int t = 0;
        for (int i = 1; i < a; i++) {
            for (int j = 0; j < b; j++) {
                m[i][j] = input.charAt(t);
                t++;
            }
        }
        for (int i = 0; i < a; i++) {
            System.out.println("");
            for (int j = 0; j < b; j++) {
                System.out.print(" " + m[i][j]);
            }
        }
        System.out.println("");
        for (int i = 0; i < b - 1; i++) {
            for (int j = 0; j < b - 1; j++) {
                if (m[0][j] > m[0][j + 1]) {
                    swap(j);
                }
            }
        }
        for (int i = 0; i < a; i++) {
            System.out.println("");
            for (int j = 0; j < b; j++) {
                System.out.print(" " + m[i][j]);
            }
        }
        output();
        System.out.println("\n\nEncoded Text: " + output);
    }

    public static void output() {
        output = "";
        for (int i = 0; i < b; i++) {
            for (int j = 1; j < a; j++) {
                output += m[j][i];
            }
        }
    }

    public static void swap(int j) {
        char d;
        for (int i = 0; i < a; i++) {
            d = m[i][j];
            m[i][j] = m[i][j + 1];
            m[i][j + 1] = d;
        }
    }

    public static void decode() {
        System.out.println("\n---------Decode--------");
        System.out.print("\nEnter Text: ");
        System.out.println("Decoded Text: " + output);
    }

    public static void settext() {
        alph = "abcdefghiklmnopqrstuvwxyz";
        String t = "";
        System.out.print("Enter Text: ");
        input = in.nextLine();
        inputcpy = input;
        input = input.replaceAll(" ", "");
        a = (input.length() / b) + 1;
        int r = input.length() % b;
        if (r != 0) {
            a++;
            for (int i = 0; i < b - r; i++) {
                t = t + "x";
            }
            input += t;
        }
        System.out.println(input);
        char temp[][] = new char[a][b];
        m = temp;
        for (int i = 0; i < input.length(); i++) {
            System.out.print(input.charAt(i));
        }
    }

    public static void setkey() {
        System.out.println("\n--------Set Key--------");
        System.out.println("Note: Key Should be in Numbers");
        System.out.print("Enter the Key: ");
        key = in.nextInt();
        in.nextLine();
        b = Integer.toString(key).length();
    }
}
