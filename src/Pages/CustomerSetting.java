package Pages;

import data.Accounts;
import data.Items;
import utils.ClearConsole;
import utils.Colors;
import utils.InputHandler;
import utils.database.Database;
import utils.log.Log;

import java.io.IOException;


public class CustomerSetting {
    public static boolean customerSetting(String username) throws IOException {
        while (true) {
            System.out.println(Colors.CYAN +
                    "List of available commands:\n"
                    + Colors.YELLOW + "1. " + Colors.BLUE + "Change username\n"
                    + Colors.YELLOW + "2. " + Colors.BLUE + "Change password\n"
                    + Colors.YELLOW + "3. " + Colors.BLUE + "complete profile\n"
                    + Colors.YELLOW + "4. " + Colors.BLUE + "request seller status\n"
                    + Colors.YELLOW + "5. " + Colors.BLUE + "delete account\n"
                    + Colors.YELLOW + "6. " + Colors.BLUE + "back" + Colors.RESET);
            System.out.print(Colors.YELLOW_BRIGHT + ">> " + Colors.RESET);
            String perform = InputHandler.inputHandler().next();
            switch (perform) {
                case "1":
                    System.out.print(Colors.YELLOW_BRIGHT + ">> " + Colors.RESET);
                    perform = InputHandler.inputHandler().next();
                    for (Accounts acc : Accounts.accounts) {
                        if (acc.username.equals(username)) {
                            if (Accounts.getUser(perform) != null)
                                System.out.println(Colors.RED + "This username has already been taken" + Colors.RESET);
                            else if (perform.length() < 3)
                                System.out.println(Colors.RED + "Username must have at least 3 characters" + Colors.RESET);
                            else if (perform.equals("admin"))
                                System.out.println(Colors.RED + "You can't change your username to admin" + Colors.RESET);
                            else {
                                ClearConsole.clearConsole();
                                acc.username = perform.toLowerCase();
                                System.out.printf(Colors.GREEN+"Your username has been changed to %s\n"+Colors.RESET, perform);
                                Log.log(username,"cu",perform.toLowerCase());
                                return true;
                            }
                            break;
                        }
                    }
                    break;
                case "2":
                    System.out.print(Colors.YELLOW_BRIGHT + ">> " + Colors.RESET);
                    perform = InputHandler.inputHandler().next();
                    for (Accounts acc : Accounts.accounts) {
                        if (acc.username.equals(username)) {
                            if (perform.length() < 4) {
                                System.out.println(Colors.RED + "You're password must have at least 4 characters." + Colors.RESET);
                            } else if (perform.length() > 12) {
                                System.out.println(Colors.RED + "You're password can't be longer than 12 characters." + Colors.RESET);
                            } else {
                                acc.password = Database.hashPass(perform);
                                ClearConsole.clearConsole();
                                System.out.println(Colors.GREEN+"You have changed your password "+Colors.RESET);
                                Log.log(username,"cp" , "");
                                return true;
                            }
                        }
                    }
                    break;
                case "3":
                    Accounts acc = Accounts.getUser(username);
                    if (!Accounts.isProfileComplete(acc)) {
                        try {
                            System.out.println(Colors.BLUE + "Enter your phone number:");
                            System.out.print(Colors.YELLOW_BRIGHT + ">> " + Colors.RESET);
                            acc.phoneNumber = InputHandler.inputHandler().next();
                            System.out.println(Colors.BLUE + "Enter your address:" + Colors.RESET);
                            System.out.print(Colors.YELLOW_BRIGHT + ">> " + Colors.RESET);
                            acc.address = InputHandler.inputHandler().nextLine().replaceAll("\\s", "-");
                            System.out.println(Colors.BLUE + "Enter your postal code:" + Colors.RESET);
                            System.out.print(Colors.YELLOW_BRIGHT + ">> " + Colors.RESET);
                            acc.postalCode = InputHandler.inputHandler().next();
                            System.out.println(Colors.BLUE + "Enter your firstName:" + Colors.RESET);
                            System.out.print(Colors.YELLOW_BRIGHT + ">> " + Colors.RESET);
                            acc.firstName = InputHandler.inputHandler().next();
                            System.out.println(Colors.BLUE + "Enter your lastname:" + Colors.RESET);
                            System.out.print(Colors.YELLOW_BRIGHT + ">> " + Colors.RESET);
                            acc.lastName = InputHandler.inputHandler().next();
                            System.out.println(Colors.GREEN + "Profile updated!" + Colors.RESET);
                        } catch (Exception e) {
                            System.out.println(Colors.RED + "An error occurred! Please try again later." + Colors.RESET);
                        }
                    } else {
                        ClearConsole.clearConsole();
                        System.out.println(Colors.RED + "Your profile is already completed!" + Colors.RESET);
                    }
                    break;
                case "4":
                    Accounts account = Accounts.getUser(username);
                    if (account.needToBeVerified) {
                        ClearConsole.clearConsole();
                        System.out.println(Colors.GREEN +"You are on waiting list"+ Colors.RESET);
                    } else if (account.isSeller) {
                        ClearConsole.clearConsole();
                        System.out.println(Colors.GREEN +"You're Seller."+ Colors.RESET);
                    } else {
                        account.needToBeVerified = true;
                        System.out.println(Colors.GREEN +"Your request has been sent to admin."+ Colors.RESET);
                    }
                    break;
                case "5":
                    System.out.println(Colors.CYAN +"Your account will be deleted. Are you sure? [Y,N]"+Colors.RESET);
                    System.out.print(Colors.YELLOW_BRIGHT + ">> " + Colors.RESET);
                    perform = InputHandler.inputHandler().next();
                    if (perform.equalsIgnoreCase("y")) {
                        Accounts.removeAccount(Accounts.getUser(username));
                        for (Items i : Items.items) {
                            if (i.seller.equals(username)) {
                                Items.removeItem(i);
                            }
                        }
                        Log.log(username,"da","");
                        ClearConsole.clearConsole();
                        System.out.println(Colors.GREEN +"Your account has been deleted. Come back to our shop soon."+ Colors.RESET);
                        return true;
                    }
                case "6":
                    return false;
                case "back":
                    return false;
            }
        }
    }
}
