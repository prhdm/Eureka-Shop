package Landing;

import Pages.*;
import data.Accounts;
import utils.*;
import utils.log.Log;
import utils.database.Database;

import java.io.IOException;

public class LogIn {
    public static void logIn() throws IOException {
        while (true) {
            ClearConsole.clearConsole();
            System.out.println(Colors.BLUE + "Enter your username:" + Colors.RESET);
            System.out.print(Colors.YELLOW_BRIGHT+">> " + Colors.RESET);
            String username = InputHandler.inputHandler().next();
            if (username.equalsIgnoreCase("cancel")) { ClearConsole.clearConsole(); return;}
            System.out.println(Colors.BLUE +"Enter your password:");
            System.out.print(Colors.YELLOW_BRIGHT+">> " + Colors.RESET + Colors.RESET);
            String password = InputHandler.inputHandler().next();
            if (username.equalsIgnoreCase("cancel")) { ClearConsole.clearConsole(); return;}
            if(username.equalsIgnoreCase("admin") && password.equals("admin")) {
                ClearConsole.clearConsole();
                System.out.println(Colors.GREEN + "You have logged in as admin." + Colors.RESET);
                AdminPage.adminPage();
                return;
            }
            else if(Accounts.getUserName(username.toLowerCase(), Database.hashPass(password)) != null) {
                if (username.equalsIgnoreCase("cancel")) { ClearConsole.clearConsole(); return;}
                if (!Accounts.getUser(username.toLowerCase()).isSeller) {
                    ClearConsole.clearConsole();
                    Log.log(username , "ll", "");
                    CustomerPage.customerPage(username.toLowerCase());
                } else {
                    ClearConsole.clearConsole();
                    System.out.println(Colors.CYAN + "In which role do you want to log in?" + Colors.RESET);
                    System.out.println(Colors.YELLOW + "1." + Colors.BLUE + "Customer \n" + Colors.YELLOW + "2." + Colors.BLUE+"Seller");
                    System.out.print(Colors.YELLOW_BRIGHT+">> " + Colors.RESET);
                    String perform = InputHandler.inputHandler().next();
                    if (perform.equalsIgnoreCase("cancel")) { ClearConsole.clearConsole(); return;}
                    if(perform.equals("1") || perform.equalsIgnoreCase("customer")) {
                        ClearConsole.clearConsole();
                        Log.log(username , "ll" , "");
                        CustomerPage.customerPage(username.toLowerCase());
                    } else if(perform.equals("2") || perform.equalsIgnoreCase("seller")) {
                        ClearConsole.clearConsole();
                        Log.log(username , "ll" , "");
                        SellerPage.sellerPage(username.toLowerCase());
                    }
                }
                return;
            } else {
                System.out.println(Colors.RED + "Invalid username or password!" + Colors.RESET);
            }
        }
    }

    public static void logInCommand(String perform) throws IOException {
            String[] args = perform.split("\\s");
            String username, password;
            if (args.length < 4) {
                System.out.println(Colors.RED + "Invalid command. Use log in [username] [password]" + Colors.RESET);
                return;
            } else {
                username = args[2];
                password = args[3];
            }
            if(username.equalsIgnoreCase("admin") && password.equals("admin")) {
                ClearConsole.clearConsole();
                System.out.println(Colors.GREEN + "You have logged in as admin." + Colors.RESET);
                Log.AdminLog("","login");
                AdminPage.adminPage();
            }
            else if(Accounts.getUserName(username.toLowerCase(), Database.hashPass(password)) != null) {
                if (!Accounts.getUser(username.toLowerCase()).isSeller) {
                    ClearConsole.clearConsole();
                    Log.log(username , "ll" , "");
                    CustomerPage.customerPage(username.toLowerCase());
                } else {
                    System.out.println(Colors.CYAN + "In which role do you want to log in?" + Colors.RESET);
                    System.out.println(Colors.YELLOW + "1." + Colors.BLUE + "Customer \n" + Colors.YELLOW + "2." + Colors.BLUE+"Seller");
                    System.out.print(Colors.YELLOW_BRIGHT+">> " + Colors.RESET);
                    perform = InputHandler.inputHandler().next();
                    if (perform.equalsIgnoreCase("cancel")) { ClearConsole.clearConsole(); return;}
                    if(perform.equals("1") || perform.equalsIgnoreCase("customer")) {
                        ClearConsole.clearConsole();
                        Log.log(username , "ll" , "");
                        CustomerPage.customerPage(username.toLowerCase());
                    } else if(perform.equals("2") || perform.equalsIgnoreCase("seller")) {
                        ClearConsole.clearConsole();
                        Log.log(username , "ll" , "");
                        SellerPage.sellerPage(username.toLowerCase());
                    }
                }
            } else {
                System.out.println(Colors.RED + "Invalid username or password" + Colors.RESET);
            }
    }
}