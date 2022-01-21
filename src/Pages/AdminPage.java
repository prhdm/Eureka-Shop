package Pages;

import data.Accounts;
import data.Items;
import utils.ClearConsole;
import utils.Colors;
import utils.InputHandler;
import utils.log.Log;

import java.io.IOException;

public class AdminPage {
    public static void adminPage() throws IOException {
        while (true) {
            String query;
            System.out.println(Colors.CYAN + "List of available commands:\n" +
                               Colors.YELLOW +"1. " + Colors.BLUE +"List of unverified users\n"+
                               Colors.YELLOW +"2. " + Colors.BLUE +"List of all users \n"+
                               Colors.YELLOW +"3. " + Colors.BLUE +"Remove user\n" +
                                Colors.YELLOW +"4. " + Colors.BLUE +"List of all items\n"+
                                Colors.YELLOW +"5. " + Colors.BLUE +"Remove item\n"+
                               Colors.YELLOW +"6. " + Colors.BLUE +"Log out" + Colors.RESET);
            System.out.print(Colors.YELLOW_BRIGHT+">> " + Colors.RESET);
            String perform = InputHandler.inputHandler().next();
            switch (perform) {
                case "1" -> {
                    ClearConsole.clearConsole();
                    System.out.println(Colors.BLUE +"List of unverified users:"+ Colors.RESET);
                    int counter = 1;
                    for (Accounts acc : Accounts.accounts) {
                        if (acc.needToBeVerified) {
                            System.out.println(Colors.YELLOW +counter + ". " + Colors.GREEN + acc.username + Colors.RESET);
                            counter++;
                        }
                    }
                    if (counter != 1) {
                        System.out.println(Colors.BLUE + "Use verify [username] for verify users. Type back for main page." + Colors.RESET);
                        verifyUser();
                        ClearConsole.clearConsole();
                    } else {
                        ClearConsole.clearConsole();
                        System.out.println(Colors.RED + "There is no record to show."+ Colors.RESET);
                    }
                }
                case "2" -> {
                    ClearConsole.clearConsole();
                    System.out.println(Colors.BLUE +"List of all users:"+ Colors.RESET);
                    Accounts.allUsers();
                    System.out.println(Colors.CYAN + "Use remove [Username] to remove user. Type back for main page." + Colors.RESET);
                    deleteUser();
                }
                case "3" -> {
                    ClearConsole.clearConsole();
                    System.out.println(Colors.BLUE +"List of all users:"+ Colors.RESET);
                    Accounts.allUsers();
                    System.out.println(Colors.CYAN + "Type user's name to remove. Type back for exit." + Colors.RESET);
                    String username;
                    while (true) {
                        System.out.print(Colors.YELLOW_BRIGHT + ">> " + Colors.RESET);
                        username = InputHandler.inputHandler().next();
                        if (username.equalsIgnoreCase("back")) break;
                        Accounts o = Accounts.getUser(username);
                        if (o != null) {
                            System.out.printf(Colors.CYAN +"You are removing user %s. Are you sure? [Y,N]\n "+Colors.RESET, username);
                            query = InputHandler.inputHandler().next();
                            if (query.equalsIgnoreCase("y")) {
                                for (Items i : Items.items) {
                                    if (i.seller.equals(username)) {
                                        Items.removeItem(i);
                                    }
                                }
                                Accounts.removeAccount(o);
                                break;
                            }
                        } else {
                            System.out.println(Colors.RED + "Invalid Username." + Colors.RESET);
                        }
                    }
                    System.out.printf(Colors.GREEN +"You removed user %s.\n "+Colors.RESET, username);
                    Log.AdminLog(username,"remove");
                }
                case "4" -> {
                    ClearConsole.clearConsole();
                    System.out.println(Colors.BLUE +"List of all items:"+ Colors.RESET);
                    int counter = 1;
                    for (Items i: Items.items) {
                        System.out.println(Colors.YELLOW +counter +". "+ Colors.GREEN + i.itemName + Colors.RESET);
                        counter++;
                    }
                    System.out.println(Colors.CYAN + "Use remove [item_name] to remove item. Type back for exit." + Colors.RESET);
                    String item="";
                    do {
                        System.out.print(Colors.YELLOW_BRIGHT + ">> " + Colors.RESET);
                        query = InputHandler.inputHandler().nextLine().toLowerCase();
                        if (query.contains("remove")) {
                            String[] items = query.split("\\s");
                            if (items.length < 2) System.out.println("Invalid Query");
                            else {
                                Items o = Items.getItem(items[1]);
                                if (o == null) {
                                    System.out.println(Colors.RED + "Invalid item name.");
                                } else {
                                    System.out.printf(Colors.CYAN +"You are removing item %s. Are you sure? [Y,N]\n "+Colors.RESET, items[1]);
                                    query = InputHandler.inputHandler().next();
                                    if (query.equalsIgnoreCase("y")) {
                                        Items.removeItem(o);
                                        Log.itemsLog("Admin",o.itemName,"remove");
                                        item= items[1];
                                        break;
                                    }
                                }
                            }
                        }
                    } while (!query.equalsIgnoreCase("back"));
                    ClearConsole.clearConsole();
                    System.out.printf(Colors.GREEN +"You removed item %s.\n "+Colors.RESET, item);
                    Log.AdminLog(item,"removeitem");
                }
                case "5" -> {
                    int counter = 1;
                    ClearConsole.clearConsole();
                    System.out.println(Colors.BLUE +"List of all items:"+ Colors.RESET);
                    for (Items i : Items.items) {
                        System.out.println(Colors.YELLOW +counter +". "+ Colors.GREEN + i.itemName + Colors.RESET);
                        counter++;
                    }
                    System.out.println(Colors.CYAN + "Type item's name to remove. Type back for exit." + Colors.RESET);
                    String itemname;
                    while (true) {
                        System.out.print(Colors.YELLOW_BRIGHT + ">> " + Colors.RESET);
                        itemname = InputHandler.inputHandler().next();
                        if (itemname.equalsIgnoreCase("back")) break;
                        Items o = Items.getItem(itemname);
                        if (o != null) {
                            System.out.printf(Colors.CYAN + "You are removing item %s. Are you sure? [Y,N]\n " + Colors.RESET, itemname);
                            query = InputHandler.inputHandler().next();
                            if (query.equalsIgnoreCase("y")) {
                                Items.removeItem(o);
                                Log.itemsLog("Admin",o.itemName,"remove");
                                break;
                            }
                        } else {
                            System.out.println(Colors.RED + "Invalid item name." + Colors.RESET);
                        }
                        System.out.printf(Colors.GREEN + "You removed item %s.\n " + Colors.RESET, itemname);
                        Log.AdminLog(itemname, "removeitem");
                    }
                }
                case "6" -> {return;}
            }
        }
    }

    public static void deleteUser() throws IOException {
        String query;
        do {
            System.out.print(Colors.YELLOW_BRIGHT + ">> " + Colors.RESET);
            query = InputHandler.inputHandler().nextLine().toLowerCase();
            if (query.contains("remove")) {
                String[] username = query.split("\\s");
                if (username.length < 2) System.out.println("Invalid Query . Use remove [Username]");
                else {
                    Accounts o = Accounts.getUser(username[1]);
                    if (o == null) {
                        System.out.println(Colors.RED + "Invalid Username.");
                    } else {
                        System.out.printf(Colors.CYAN +"You are removing user %s. Are you sure? [Y,N]\n "+Colors.RESET, username[1]);
                        query = InputHandler.inputHandler().next();
                        if (query.equalsIgnoreCase("y")) {
                            Accounts.removeAccount(o);
                            for (Items i : Items.items) {
                                if (i.seller.equals(username[1])) {
                                    Items.removeItem(i);
                                }
                            }
                            ClearConsole.clearConsole();
                            Log.AdminLog(username[1],"remove");
                            System.out.printf(Colors.GREEN +"You removed user %s.\n "+Colors.RESET, username[1]);
                            return;
                        }
                    }
                }
            }
        } while (!query.equalsIgnoreCase("back"));
        ClearConsole.clearConsole();
    }

    public static void verifyUser() throws IOException {
        String query;
        do {
            System.out.print(Colors.YELLOW_BRIGHT + ">> " + Colors.RESET);
            query = InputHandler.inputHandler().nextLine().toLowerCase();
            if (query.contains("verify")) {
                String[] args = query.split("\\s");
                if (args.length < 2) System.out.println(Colors.RED + "Use verify [username]." + Colors.RESET);
                else {
                    Accounts o = Accounts.getUser(args[1]);
                    if (o == null) {
                        System.out.println(Colors.RED + "Invalid Username." + Colors.RESET);
                    } else {
                        if (o.needToBeVerified) {
                            System.out.printf(Colors.CYAN +"You are giving user %s Seller status. Are you sure? [Y, N]\n "+Colors.RESET, args[1]);
                            System.out.print(Colors.YELLOW_BRIGHT+">> " + Colors.RESET);
                            query = InputHandler.inputHandler().next();
                            if (query.equalsIgnoreCase("y")) {
                                o.isSeller = true;
                                o.needToBeVerified = false;
                                Log.AdminLog(args[1], "verify");
                                ClearConsole.clearConsole();
                                System.out.printf(Colors.GREEN +"You verified user %s.\n "+Colors.RESET, args[1]);
                                return;
                            }
                        }
                    }
                }
            }
        } while (!query.equalsIgnoreCase("back"));
        ClearConsole.clearConsole();
    }
}



