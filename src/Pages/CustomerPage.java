package Pages;

import data.Accounts;
import data.Items;
import utils.ClearConsole;
import utils.Colors;
import utils.InputHandler;
import utils.log.Log;

import java.io.IOException;
import java.math.BigInteger;

public class CustomerPage {
    public static void customerPage(String username) throws IOException {
        while(true) {
            Accounts o = Accounts.getUser(username);
            System.out.println( Colors.CYAN +
                    "List of available commands:\n" +
                    Colors.YELLOW +"1. " + Colors.BLUE + "Account info\n" +
                    Colors.YELLOW +"2. " + Colors.BLUE + "Add balance\n" +
                    Colors.YELLOW +"3. " + Colors.BLUE + "List of items\n"+
                    Colors.YELLOW +"4. " + Colors.BLUE + "Search by tag [tag_name]\n"+
                    Colors.YELLOW +"5. " + Colors.BLUE + "Settings\n"+
                    Colors.YELLOW +"6. " + Colors.BLUE + "Log out"+ Colors.RESET );
            System.out.print(Colors.YELLOW_BRIGHT+">> " + Colors.RESET);
            String perform = InputHandler.inputHandler().nextLine();
            if (perform.equals("1") || perform.equalsIgnoreCase("info")) {
                ClearConsole.clearConsole();
                System.out.println(Colors.CYAN + "Your account info:"+Colors.RESET);
                System.out.println(Colors.CYAN + "First Name:" + Colors.BLUE + (o.firstName.equals("null") ? "" : o.firstName)
                        +Colors.CYAN + "\nLast Name :" + Colors.BLUE + (o.lastName.equals("null") ? "" : o.lastName)
                        +Colors.CYAN + "\nUsername :" + Colors.BLUE + (o.username.equals("null") ? "" : o.username)
                        +Colors.CYAN + "\nMail:" + Colors.BLUE + (o.mail.equals("null") ? "" : o.mail)
                        +Colors.CYAN + "\nPhone Number:" + Colors.BLUE + (o.phoneNumber.equals("null") ? "" : o.phoneNumber)
                        +Colors.CYAN + "\nBalance:" + Colors.BLUE + o.balance+Colors.RESET );
            } else if (perform.equals("2") || perform.equalsIgnoreCase("add balance")) {
                ClearConsole.clearConsole();
                if(o.cardNumber.equals("null")) {
                    System.out.println(Colors.BLUE + "Please enter your card number :" + Colors.RESET);
                    System.out.print(Colors.YELLOW_BRIGHT+">> " + Colors.RESET);
                    String cardNumber = InputHandler.inputHandler().next();
                    if (!cardNumber.equalsIgnoreCase("cancel")) {
                        System.out.println(Colors.BLUE + "How much do you want to update your balance?"+ Colors.RESET);
                        System.out.print(Colors.YELLOW_BRIGHT + ">> " + Colors.RESET);
                        String balance = InputHandler.inputHandler().next();
                        try {
                            o.balance = o.balance.add(new BigInteger(balance));
                            Log.log(username, "ub", o.balance.toString());
                            ClearConsole.clearConsole();
                            System.out.println(Colors.GREEN +"Updating balance has been done successfully."+ Colors.RESET);
                            System.out.println(Colors.BLUE +"Do you want to save your credit card  for later?"+ Colors.RESET);
                            System.out.print(Colors.YELLOW_BRIGHT + ">> " + Colors.RESET);
                            if (InputHandler.inputHandler().next().equals("y")) {
                                o.cardNumber = cardNumber;
                            }
                            System.out.println(Colors.GREEN +"Your total balance : " + o.balance+ Colors.RESET);
                        } catch (Exception e) {
                            System.out.println(Colors.RED +"Operation failed. Use numbers!\n"+ Colors.RESET);
                        }
                    } else { ClearConsole.clearConsole();}
                } else {
                    System.out.println(Colors.BLUE + "How much do you want to update your balance?"+ Colors.RESET);
                    System.out.print(Colors.YELLOW_BRIGHT + ">> " + Colors.RESET);
                    String balance = InputHandler.inputHandler().next();
                    try {
                        o.balance = o.balance.add(new BigInteger(balance));
                        Log.log(username, "ub", o.balance.toString());
                        ClearConsole.clearConsole();
                        System.out.println(Colors.GREEN +"Updating balance has been done successfully."+ Colors.RESET);
                        System.out.println(Colors.GREEN +"Your total balance : " + o.balance+ Colors.RESET);
                    } catch (Exception e) {
                        System.out.println(Colors.RED +"Operation failed. Use numbers!."+ Colors.RESET);
                    }
                }
            } else if (perform.equals("3") || perform.equalsIgnoreCase("list")) {
                ClearConsole.clearConsole();
                System.out.println(Colors.BLUE +"List of all items:"+ Colors.RESET);
                int counter = 1;
                for (Items i: Items.items) {
                    if (!i.seller.equals(username)) {
                        System.out.println(Colors.YELLOW + counter + ". " + Colors.GREEN + i.itemName + Colors.RESET);
                        counter++;
                    }
                }
                do {
                    System.out.println(Colors.BLUE + "Use buy [item_name] to buy. Type back for main page."+ Colors.RESET);
                    System.out.print(Colors.YELLOW_BRIGHT+">> " + Colors.RESET);
                    perform = InputHandler.inputHandler().nextLine();
                    if (perform.contains("buy")) {
                        buyItem(perform , username);
                        break;
                    }
                } while(!perform.equalsIgnoreCase("back"));
            } else if (perform.equals("4")) {
                ClearConsole.clearConsole();
                System.out.println(Colors.CYAN + "Enter tag to search:"+Colors.RESET);
                System.out.print(Colors.YELLOW_BRIGHT+">> " + Colors.RESET);
                String tag = InputHandler.inputHandler().next();
                int counter = 1;
                for (Items i : Items.items) {
                    if (i.tag.equals(tag)) {
                        System.out.println(Colors.YELLOW + counter + Colors.BLUE + ". Item Name: " + i.itemName + " Seller : " + i.seller + " Price :" + i.price + " Tag: " + i.tag + Colors.RESET);
                        counter++;
                    }
                }
                do {
                    System.out.println(Colors.BLUE + "Use buy [item_name] to buy. Type back for main page."+ Colors.RESET);
                    System.out.print(Colors.YELLOW_BRIGHT+">> " + Colors.RESET);
                    perform = InputHandler.inputHandler().nextLine();
                    if (perform.contains("buy")) {
                        buyItem(perform , username);
                        break;
                    }
                } while(!perform.equalsIgnoreCase("back"));
            } else if (perform.equals("5") || perform.equalsIgnoreCase("settings")) {
                if (CustomerSetting.customerSetting(username)) return;
            } else if(perform.equals("6") || perform.equalsIgnoreCase("log out")) {
                return;
            } else if (perform.contains("buy")) {
                buyItem(perform , username);
            }
        }
    }
    public static void buyItem(String perform , String username) throws IOException {
        Accounts o = Accounts.getUser(username);
        String[] args = perform.split("\\s");
        if (Accounts.isProfileComplete(o)) {
            for (Items i : Items.items) {
                if (i.itemName.equals(args[1])) {
                    System.out.printf(Colors.BLUE + "You are buying %s price %s. [Y,N]\n" + Colors.RESET, i.itemName, i.price);
                    System.out.print(Colors.YELLOW_BRIGHT + ">> " + Colors.RESET);
                    perform = InputHandler.inputHandler().next();
                    if (perform.equalsIgnoreCase("y")) {
                        if (o.balance.compareTo(new BigInteger(i.price)) != -1) {
                            o.balance = o.balance.subtract(new BigInteger(i.price));
                            Items.removeItem(i);
                            System.out.println(Colors.RED + "You have successfully bought " + i.itemName + " ." + Colors.RESET);
                            Log.itemsLog(username, i.itemName, "buy");
                        } else {
                            System.out.println(Colors.RED + "You dont have enough money." + Colors.RESET);
                        }
                    } else {
                        System.out.println(Colors.RED + " Operation canceled." + Colors.RESET);
                    }
                    return;
                }
            }
        } else System.out.println(Colors.RED + "Please complete your profile to buy items." + Colors.RESET);
    }
}