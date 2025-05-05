package testrunner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.testng.annotations.Test;
import page.LoginPage;
import setup.Setup;

import java.io.FileReader;

public class UserLoginNewPassTestRunner extends Setup {


    @Test(priority = 1, description = "Login with New Password")
    public void doLoginWithNewPassword() throws Exception {

        JSONParser parser = new JSONParser();
        FileReader reader = new FileReader("./src/test/resources/user.json");
        JSONArray users = (JSONArray) parser.parse(reader);
        reader.close();
        JSONObject lastUser = (JSONObject) users.get(users.size() - 1);
        LoginPage login = new LoginPage(driver);
        String email = lastUser.get("email").toString();
        String password = lastUser.get("password").toString();
        login.doLogin(email,password);
        driver.get("https://dailyfinance.roadtocareer.net/login");

        LoginPage loginPage = new LoginPage(driver);
        loginPage.doLogin(email, password);

    }

}
