package Landing;

import data.Accounts;
import utils.ClearConsole;
import utils.Colors;
import utils.InputHandler;
import utils.database.Database;
import utils.log.Log;

import java.io.IOException;

public class SignUp {
    public static void signUp() throws IOException {
        ClearConsole.clearConsole();
        String username;
        while (true) {
            System.out.println(Colors.BLUE + "Enter a username:" + Colors.RESET);
            System.out.print(Colors.YELLOW_BRIGHT + ">> " + Colors.RESET);
            username = InputHandler.inputHandler().next().toLowerCase();
            if (Accounts.getUser(username) != null)
                System.out.println(Colors.RED + "This username has been taken already!" + Colors.RESET);
            else if (username.length() < 3)
                System.out.println(Colors.RED + "Username must have at least 3 characters!" + Colors.RESET);
            else if (username.equals("admin"))
                System.out.println(Colors.RED + "You can't sign up as admin!" + Colors.RESET);
            else break;
        }
        if (username.equalsIgnoreCase("cancel")) { ClearConsole.clearConsole(); return;}
        String password;
        while (true) {
            System.out.println(Colors.BLUE + "Enter a password:" + Colors.RESET);
            System.out.print(Colors.YELLOW_BRIGHT + ">> " + Colors.RESET);
            password = InputHandler.inputHandler().next();
            if (password.length() < 4) {
                System.out.println(Colors.RED + "You're password must have at least 4 characters." + Colors.RESET);
            } else if (password.length() > 12) {
                System.out.println(Colors.RED + "You're password can't be longer than 12 characters." + Colors.RESET);
            } else {
                break;
            }
        }
        if (username.equalsIgnoreCase("cancel")) { ClearConsole.clearConsole(); return;}
        String mail;
        while (true) {
            System.out.println(Colors.BLUE + "Enter an Email:" + Colors.RESET);
            System.out.print(Colors.YELLOW_BRIGHT + ">> " + Colors.RESET);
            mail = InputHandler.inputHandler().next();
            if (username.equalsIgnoreCase("cancel")) { ClearConsole.clearConsole(); return;}
            if (!mail.matches("^(.+)@(\\S+)$")) {
                System.out.println(Colors.RED + "Invalid Email. example : example@example.com" + Colors.RESET);
            } else {
                break;
            }
        }
        System.out.println(Colors.BLUE + "To continue please solve the captcha:" + Colors.RESET);
            while (true) {
                int a = (int) (Math.random() * 10), b = (int) (Math.random() * 10);
                System.out.printf(Colors.CYAN +"%d + %d \n", a, b);
                System.out.print(Colors.YELLOW_BRIGHT + ">> " + Colors.RESET);
                String result = InputHandler.inputHandler().next();
                if (username.equalsIgnoreCase("cancel")) { ClearConsole.clearConsole(); return;}
                if (a + b == Integer.parseInt(result)) {
                    System.out.println(Colors.GREEN + "You're account has been registered successfully. Please log in to your account." + Colors.RESET);
                    break;
                } else {
                    System.out.println(Colors.RED + "Wrong answer! Try Again." + Colors.RESET);
                }
            }
            Accounts.addAccount(new Accounts(username.toLowerCase(), Database.hashPass(password), mail));
            Log.log(username.toLowerCase() , "sl","");
    }
    public static void signUpCommand(String perform) throws IOException {
        String[] args = perform.split("\\s");
        String username, password, mail;
            if (args.length < 5) {
                System.out.println(Colors.RED + "Invalid type. Use sign up [username] [password] [mail]" + Colors.RESET);
                return;
            } else {
                username = args[2];
                password = args[3];
                mail = args[4];
            }
            if (Accounts.getUser(username) != null) {
                System.out.println(Colors.RED + "This username has been taken already!" + Colors.RESET);
                return;
            } else if (username.length() < 3) {
                System.out.println(Colors.RED +"Username must have at least 3 characters!" + Colors.RESET);
                return;
            } else if (username.equals("admin")) {
                System.out.println(Colors.RED +"You can't sign up as admin" + Colors.RESET);
                return;
            }
            if (password.length() < 4) {
                System.out.println(Colors.RED + "You're password must have at least 4 characters." + Colors.RESET);
                return;
            } else if (password.length() > 12) {
                System.out.println(Colors.RED + "You're password can't be longer than 12 characters." + Colors.RESET);
                return;
            }
            if (!mail.matches("^(.+)@(\\S+)$")) {
                System.out.println(Colors.RED +"Invalid Email. example : example@example.com" + Colors.RESET);
                return;
            }
            System.out.println(Colors.BLUE + "To continue please solve the captcha:" + Colors.RESET);
            while (true) {
                int a = (int) (Math.random() * 10), b = (int) (Math.random() * 10);
                System.out.printf(Colors.CYAN +"%d + %d \n", a, b);
                System.out.print(Colors.YELLOW_BRIGHT + ">> " + Colors.RESET);
                String result = InputHandler.inputHandler().next();
                if (username.equalsIgnoreCase("cancel")) { ClearConsole.clearConsole(); return;}
                if (a + b == Integer.parseInt(result)) {
                    System.out.println(Colors.GREEN + "You're account has been registered successfully. Please log in to your account." + Colors.RESET);
                    break;
                } else {
                    System.out.println(Colors.RED + "Wrong answer! Try Again." + Colors.RESET);
                }
            }
            Accounts.addAccount(new Accounts(username.toLowerCase(), Database.hashPass(password), mail));
            Log.log(username.toLowerCase() , "sl" , "");
        }
}
