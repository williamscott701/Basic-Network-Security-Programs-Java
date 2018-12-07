
import java.util.Scanner;

//@author William Scott
public class NetworkSecurityLabExp1aCeaserCipher {

    public static String text, output = "";
    public static int op, sno, j;
    public static Scanner in = new Scanner(System.in);

    public static void main(String[] args) {
        boolean r = true;
        int choice;
        System.out.println("\nCaesar Cipher - UR12CS135");
        setkey();
        do {
            System.out.println("\n\n---------Main Menu---------\n1.Change Key\n2.Encode \n3.Decode \n4.Exit");
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
        System.out.print("\nEnter Text: ");
        text = in.nextLine();
        output = "";
        if (op == 1) {
            left();
        } else if (op == 2) {
            right();
        }
        System.out.println("Encoded Text: " + output);
    }

    public static void decode() {
        System.out.println("\n---------Decode--------");
        System.out.print("\nEnter Text: ");
        text = in.nextLine();
        output = "";
        if (op == 2) {
            left();
        } else if (op == 1) {
            right();
        }
        System.out.println("Decoded Text: " + output);
    }

    public static void setkey() {
        int i = 0;
        do {
            if (i != 0) {
                System.out.println("Invalid Option!, Try Again");
            }
            System.out.println("\nSelect Shift Operation: \n1.Left    2.Right");
            op = in.nextInt();
            in.nextLine();
            i++;
        } while (op != 1 && op != 2);
        System.out.print("\nEnter Shift Number: ");
        sno = in.nextInt();
        in.nextLine();
    }

    public static void left() {
        for (char i : text.toCharArray()) {
            j = i;
            if (Character.isLowerCase(i)) {
                j = i - sno;
                if (j < 'a') {
                    j += 26;
                }
            }

            if (Character.isUpperCase(i)) {
                j = i - sno;
                if (j < 'A') {
                    j += 26;
                }
            }
            output = output + (char) j;
        }
    }

    public static void right() {
        for (char i : text.toCharArray()) {
            if (Character.isLowerCase(i)) {
                output = output + (char) ((i - 'a' + sno) % 26 + 'a');
            } else if (Character.isUpperCase(i)) {
                output = output + (char) ((i - 'A' + sno) % 26 + 'A');
            } else {
                output = output + i;
            }
        }
    }
}
