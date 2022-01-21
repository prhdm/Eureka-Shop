package data;

import utils.Colors;

import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class Accounts {
    public String firstName = "null";
    public String lastName = "null";
    public String username;
    public String password;
    public String mail;
    public String phoneNumber = "null";
    public String address = "null";
    public String postalCode = "null";
    public String cardNumber = "null";
    public BigInteger balance = BigInteger.valueOf(0);
    public static final List<Accounts> accounts = new LinkedList<>();
    public boolean isSeller = false;
    public boolean needToBeVerified = false;
    public static void addAccount(Accounts acc) {
        accounts.add(acc);
    }
    public static void removeAccount(Accounts acc) {
        accounts.remove(acc);
    }
    public static boolean isProfileComplete(Accounts acc) {
        return !acc.phoneNumber.equals("null") && !acc.address.equals("null") && !acc.postalCode.equals("null");
    }
    public Accounts(String username , String password , String mail) {
        this.username = username;
        this.password = password;
        this.mail = mail;
    }

    public Accounts(String username , String password , String mail , String phoneNumber , String firstName , String lastName , String address , String postalCode , String cardNumber , String balance , String isSeller , String needToBeVerified) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.mail = mail;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.postalCode = postalCode;
        this.cardNumber = cardNumber;
        this.balance = new BigInteger(balance);
        this.isSeller = isSeller.equals("1");
        this.needToBeVerified = needToBeVerified.equals("1");
    }

    public static Accounts getUserName(String username, String password){
        Accounts account = null;
        for (Accounts acc : accounts) {
            if (acc.username.equals(username) && acc.password.equals(password)) {
                account = acc;
            }
        }
        return account;
    }
    public static Accounts getUser(String username) {
        Accounts account = null;
        for (Accounts acc : accounts) {
            if (acc.username.equals(username)) {
                account = acc;
            }
        }
        return account;
    }
    
    public static void allUsers() {
        int counter = 1;
        for (Accounts acc : accounts) {
            System.out.println(Colors.YELLOW + counter +". " + Colors.GREEN + acc.username + Colors.RESET);
            counter++;
        }
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Accounts accounts = (Accounts) o;
        return Objects.equals(username, accounts.username) && Objects.equals(password, accounts.password) && Objects.equals(mail, accounts.mail);
    }
}
