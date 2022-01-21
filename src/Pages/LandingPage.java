package Pages;

import Landing.*;
import utils.*;

import java.io.IOException;

public class LandingPage {

    public static void landingPage() throws IOException {
        ClearConsole.clearConsole(); // Like a Real CLI!

        // Welcome Screen
        System.out.println(Colors.BLUE_BACKGROUND + "╔════════════Eureka═════════════╗" +Colors.RESET + "\n" +
                           Colors.BLUE_BACKGROUND + "╟═"+ Colors.PURPLE_BACKGROUND +"Welcome To Eureka Online Shop"+ Colors.BLUE_BACKGROUND +"═╣" +Colors.RESET + "\n" +
                           Colors.BLUE_BACKGROUND + "╚════════════Eureka═════════════╝" +Colors.RESET +"\n"); // What's Better Than a Banner With a Warm Welcome.

        while (true) {
            System.out.println(Colors.CYAN + "Sign In/Log In:\n"
                             + Colors.YELLOW + "1. " +Colors.BLUE + "Log in [username] [password] \n"
                             + Colors.YELLOW + "2. " +Colors.BLUE +"Sign up [username] [password] [mail]\n"
                             + Colors.YELLOW + "3. " +Colors.BLUE + "Close" + Colors.RESET );

            System.out.print(Colors.YELLOW_BRIGHT+">> " + Colors.RESET);
            String perform = InputHandler.inputHandler().nextLine();

            if(perform.equals("1")) {
                LogIn.logIn();
            } else if(perform.toLowerCase().contains("log in")) {
                LogIn.logInCommand(perform);
            } else if(perform.equals("2")) {
                SignUp.signUp();
            } else if(perform.toLowerCase().contains("sign up")) {
                SignUp.signUpCommand(perform);
            } else if (perform.equals("3") || perform.equalsIgnoreCase("close")) {
                System.out.println(Colors.GREEN + "Thanks for coming! Visit our shop again." + Colors.RESET);
                return;
            } else {
                System.out.println(Colors.RED + "Invalid operation." + Colors.RESET);
            }
        }
    }
}