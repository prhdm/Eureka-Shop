package Pages;

import data.Items;
import utils.ClearConsole;
import utils.Colors;
import utils.InputHandler;
import utils.log.Log;

import java.io.IOException;
import java.math.BigInteger;

public class SellerPage {
    public static void sellerPage(String username) throws IOException {
        while (true) {
            System.out.println(Colors.CYAN +
                    "List of available commands:\n"
                    + Colors.YELLOW + "1." + Colors.BLUE + "Account info\n"
                    + Colors.YELLOW + "2." + Colors.BLUE + "Add item [item_name] [price] [tag]\n"
                    + Colors.YELLOW + "3." + Colors.BLUE + "Remove item [item_name]\n"
                    + Colors.YELLOW + "4." + Colors.BLUE + "Log out" + Colors.RESET);
            System.out.print(Colors.YELLOW_BRIGHT + ">> " + Colors.RESET);
            String perform = InputHandler.inputHandler().nextLine();
            if(perform.equalsIgnoreCase("1")) {
                ClearConsole.clearConsole();
                System.out.println(Colors.CYAN + "Your account info:"+Colors.RESET);
                int counter = 1;
                for (Items i : Items.items) {
                    if (i.seller.equals(username)) {
                        System.out.println(Colors.YELLOW + counter + ". Item Name: "+ Colors.BLUE + i.itemName + " Price :" + i.price + " Tag: " + i.tag+ Colors.RESET);
                        counter++;
                    }
                }
            } else if(perform.equalsIgnoreCase("2")) {
                try {
                    String itemName="";
                    while (true) {
                        System.out.println(Colors.BLUE + "Enter Item's name:" + Colors.RESET);
                        System.out.print(Colors.YELLOW_BRIGHT + ">> " + Colors.RESET);
                        itemName = InputHandler.inputHandler().next();
                        if (Items.getItem(itemName) == null) {
                            System.out.println(Colors.RED + "This item is already in shop. Try another name!" + Colors.RESET);
                        } else break;
                    }
                    System.out.println(Colors.BLUE +"Enter the price:"+ Colors.RESET);
                    System.out.print(Colors.YELLOW_BRIGHT + ">> " + Colors.RESET);
                    String price = InputHandler.inputHandler().next();
                    new BigInteger(price);
                    System.out.println(Colors.BLUE +"Enter the tag:"+ Colors.RESET);
                    System.out.print(Colors.YELLOW_BRIGHT + ">> " + Colors.RESET);
                    String tag = InputHandler.inputHandler().next();
                    Items.addItem(new Items(username, itemName.toLowerCase(), price, tag.toLowerCase()));
                    Log.itemsLog(username,itemName,"add");
                } catch (Exception e) {
                    System.out.print(Colors.RED + "Operation failed. Enter the price in numbers!\n" + Colors.RESET);
                }

            } else if(perform.equalsIgnoreCase("3")) {
                int counter = 1;
                for (Items i : Items.items) {
                    if (i.seller.equals(username)) {
                        System.out.println(Colors.YELLOW + counter + ". Item Name: " + i.itemName + " Price :" + i.price + " Tag: " + i.tag+ Colors.RESET);
                        counter++;
                    }
                }
                System.out.println(Colors.CYAN + "Use remove [item_name] to remove item. Type back for exit." + Colors.RESET);
                String query="";
                String item="";
                do {
                    System.out.println(Colors.BLUE +"Enter item's name:"+ Colors.RESET);
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
                                    Log.itemsLog(username,o.itemName,"remove");
                                    item= items[1];
                                    break;
                                }
                            }
                        }
                    }
                } while (!query.equalsIgnoreCase("back"));
                ClearConsole.clearConsole();
                System.out.printf(Colors.GREEN +"You removed item %s.\n "+Colors.RESET, item);
            } else if(perform.equalsIgnoreCase("4")) {
                return;
            } else if (perform.toLowerCase().contains("remove")) {
                String query = "";
                String[] args = perform.split("\\s");
                if (args.length < 2) System.out.println("Invalid Query");
                else {
                    Items o = Items.getItem(args[1]);
                    if (o == null) {
                        System.out.println(Colors.RED + "Invalid item name.");
                    } else {
                        System.out.printf(Colors.CYAN +"You are removing item %s. Are you sure? [Y,N]\n "+Colors.RESET, args[1]);
                        query = InputHandler.inputHandler().next();
                        if (query.equalsIgnoreCase("y")) {
                            Items.removeItem(o);
                            Log.itemsLog(username,o.itemName,"remove");
                            ClearConsole.clearConsole();
                            System.out.printf(Colors.GREEN +"You removed item %s.\n "+Colors.RESET, args[2]);
                        }
                    }
                }
            } else if (perform.toLowerCase().contains("add item")) {
                String[] args = perform.split("\\s");
                try {
                    new BigInteger(args[3]);
                    Items.addItem(new Items(username ,args[2].toLowerCase(),args[3],args[4].toLowerCase()));
                    Log.itemsLog(username,args[2],"add");
                } catch (Exception e) {
                    System.out.print(Colors.RED + "Operation failed. Enter the price in numbers!\n" + Colors.RESET);
                }

            }
        }
    }
}
