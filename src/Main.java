import Pages.LandingPage;
import utils.database.Database;
import utils.log.Log;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Database.getFromDatabase();
        Log.generateLogfile();
        LandingPage.landingPage();
        Database.insertIntoDatabase();
    }
}