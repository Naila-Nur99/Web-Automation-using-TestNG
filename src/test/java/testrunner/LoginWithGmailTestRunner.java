package testrunner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import page.LoginPage;
import setup.Setup;

import java.io.FileReader;
import java.time.Duration;

public class LoginWithGmailTestRunner extends Setup {


    @Test(priority = 1, description = "Login with Old Gmail is Failed")
    public void oldGmailLogin() {
        try {
            JSONParser parser = new JSONParser();

            // Read old email from localStorage.json
            FileReader localReader = new FileReader("./src/test/resources/localStorage.json");
            JSONObject localStorage = (JSONObject) parser.parse(localReader);
            localReader.close();
            String oldEmail = localStorage.get("oldEmail").toString();

            // Read last user password from user.json
            FileReader userReader = new FileReader("./src/test/resources/user.json");
            JSONArray users = (JSONArray) parser.parse(userReader);
            userReader.close();
            JSONObject lastUser = (JSONObject) users.get(users.size() - 1);
            String password = lastUser.get("password").toString();

            // Perform login with old email and last password
            LoginPage oldGmail = new LoginPage(driver);
            oldGmail.doLogin(oldEmail, password);


            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            String msgActual = driver.findElement(By.tagName("p")).getText();
            String msgExpected = "Invalid email or password";
            Assert.assertTrue(msgActual.contains(msgExpected));


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test(priority=2,description = "Login with New Gmail is Successful")
    public void newGmailLogin() {
        try {
            // Parse the user.json file
            JSONParser parser = new JSONParser();
            FileReader reader = new FileReader("./src/test/resources/user.json");
            JSONArray users = (JSONArray) parser.parse(reader);
            reader.close();

            // Get last user
            JSONObject lastUser = (JSONObject) users.get(users.size() - 1);
            String email = lastUser.get("email").toString();
            String password = lastUser.get("password").toString();

            // Perform login with last user credentials
            LoginPage newGmail = new LoginPage(driver);
            newGmail.doLogin2(email, password);
            newGmail.doLogout();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
