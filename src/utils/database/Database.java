package utils.database;

import data.Accounts;
import data.Items;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

public class Database {
    public static File usersDb = new File("./src/utils/database/usersdb.txt");
    public static File itemsDb = new File("./src/utils/database/itemsdb.txt");

    public static String hashPass(String password) {
        int temp = 0;
        for (int i = 0; i < password.length(); i++) {
            temp += password.charAt(i) * (i + 1) * (password.charAt(i) - '0') * ((int) Math.pow(10, (i % 2)));
        }
        return String.valueOf(temp);
    }

    public static void insertIntoDatabase() throws IOException {
        int counter = 1;
        new FileOutputStream("./src/utils/database/usersdb.txt").close();
        new FileOutputStream("./src/utils/database/itemsdb.txt").close();
        FileOutputStream fileOutputStream = new FileOutputStream(usersDb, true);
        PrintStream printStream = new PrintStream(fileOutputStream);
        printStream.println("ID | USER | PASSWORD | MAIL | PHONE_NUMBER | FIRST_NAME | LAST_NAME | ADDRESS | POSTAL_CODE | CARD_NUMBER | BALANCE |" +
                " IS_SELLER | NEED_VERIFY ");
        printStream.println("_____________________________________________________________________________________________________________________________________________");
        for (Accounts acc : Accounts.accounts) {
            printStream.println(counter + " " + acc.username + " " + acc.password + " " + acc.mail + " " + acc.phoneNumber + " " + acc.firstName + " " + acc.lastName + " " +
                    acc.address + " " + acc.postalCode + " " + acc.cardNumber + " " + acc.balance + " " + (acc.isSeller ? 1 : 0) + " " + (acc.needToBeVerified ? 1 : 0));
            counter++;
            printStream.println("_____________________________________________________________________________________________________________________________________________");
        }
        printStream.close();
        fileOutputStream.close();

        fileOutputStream = new FileOutputStream(itemsDb, true);
        printStream = new PrintStream(fileOutputStream);
        printStream.println("ID | ITEM_NAME | ITEM_SELLER | ITEM_PRICE | ITEM_TAG");
        printStream.println("_______________________________________________________");
        counter = 1;
        for (Items i : Items.items) {
            printStream.println(counter + " " + i.itemName + " " + i.seller + " " + i.price + " " + i.tag);
            counter++;
            printStream.println("_______________________________________________________");
        }
    }

    public static void getFromDatabase() throws IOException {
        if (!usersDb.exists()) {
            usersDb.createNewFile();
        } else {
            Scanner reader = new Scanner(usersDb);
            if (reader.hasNext()) {
                reader.nextLine();
                reader.nextLine();
            }
            while (reader.hasNext()) {
                String[] temp = reader.nextLine().split("\\s");
                Accounts.addAccount(new Accounts(temp[1], temp[2], temp[3], temp[4], temp[5], temp[6], temp[7], temp[8], temp[9], temp[10], temp[11], temp[12]));
                reader.nextLine();
            }
            reader.close();

            if (!itemsDb.exists()) {
                itemsDb.createNewFile();
            } else {
                reader = new Scanner(itemsDb);
                if (reader.hasNext()) {
                    reader.nextLine();
                    reader.nextLine();
                }
                while (reader.hasNext()) {
                    String[] temp = reader.nextLine().split("\\s");
                    Items.addItem(new Items(temp[2], temp[1], temp[3], temp[4]));
                    reader.nextLine();
                }
                reader.close();
            }
        }
    }
}
