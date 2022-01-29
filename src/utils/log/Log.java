package utils.log;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Log {
    public static File users = new File("./src/utils/log/usersLog.txt");
    public static File admin = new File("./src/utils/log/adminLog.txt");
    public static File items = new File("./src/utils/log/itemsLog.txt");
    public static void generateLogfile() throws IOException {
        if(!users.exists()) users.createNewFile();
        if(!admin.exists()) admin.createNewFile();
        if(!items.exists()) items.createNewFile();
    }
    public static void log (String username , String action , String other) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(users,true);
        PrintStream printStream = new PrintStream(fileOutputStream);
        switch (action) {
            case "sl" -> {
                String log = String.format("[%s] REGISTER : User %s has made an account.", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()), username);
                printStream.println(log);
            }
            case "ll" -> {
                String log = String.format("[%s] LOGIN : User %s Logs in to his account." , new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()), username);
                printStream.println(log);
            }
            case "cp" -> {
                String log = String.format("[%s] CHANGE-PASS : User %s Changed his password." , new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()), username);
                printStream.println(log);
            }
            case "da" -> {
                String log = String.format("[%s] DELETE-ACCOUNT : User %s deletes his account." , new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()), username);
                printStream.println(log);
            }
            case "ub" -> printStream.printf("[%s] UPDATE-BALANCE : User %s update his balance to %s.\n" , new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()), username , other);
            case "cu" -> printStream.printf("[%s] USERNAME-CHANGE : User %s Changed his username to %s.\n" , new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()), username , other);
        }
        printStream.close();
        fileOutputStream.close();
    }
    public static void AdminLog(String username , String operation) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(admin, true);
        PrintStream printStream = new PrintStream(fileOutputStream);
        switch (operation) {

            case "login" -> printStream.printf("[%s] ADMIN-LOGIN : Admin logged into the panel. .\n", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            case "remove" -> printStream.printf("[%s] ADMIN-REMOVE : Admin removed user %s.\n", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()), username );
            case "verify" -> printStream.printf("[%s] ADMIN-VERIFY : Admin verified user %s.\n", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()), username );
            case "removeitem" -> printStream.printf("[%s] ADMIN-ITEM : Admin removed item %s.\n", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()), username );

        }
        printStream.close();
        fileOutputStream.close();
    }

    public static void itemsLog(String username, String itemname , String operation) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(items, true);
        PrintStream printStream = new PrintStream(fileOutputStream);
        switch (operation) {

            case "buy" -> printStream.printf("[%s] ITEM-BUY : User %s Has Bought %s .\n", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) , username ,itemname);
            case "remove" -> printStream.printf("[%s] ITEM-REMOVE : Item %s has been removed by %s.\n", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) ,itemname , username);
            case "add" -> printStream.printf("[%s] ADD-REMOVE : Item %s has been added by %s.\n", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) ,itemname , username);

        }
        printStream.close();
        fileOutputStream.close();
    }

}
