package testrunner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import page.LoginPage;
import page.UpdateGmailPage;
import setup.Setup;
import setup.Utils;

import java.io.FileReader;
import java.io.FileWriter;

public class UpdateGmailTestRunner extends Setup {


    @BeforeTest
    public void doLoginWithNewPassword() throws Exception {
        driver.get("https://dailyfinance.roadtocareer.net/login");


        JSONParser parser = new JSONParser();
        FileReader reader = new FileReader("./src/test/resources/user.json");
        JSONArray users = (JSONArray) parser.parse(reader);
        reader.close();
        JSONObject lastUser = (JSONObject) users.get(users.size() - 1);
        LoginPage login = new LoginPage(driver);
        String email = lastUser.get("email").toString();
        String password = lastUser.get("password").toString();
        login.doLogin(email,password);

    }

    @Test(priority = 1, description = "Update User Gmail")
    public void updateGmail() throws Exception {
        // Load user data
        JSONParser parser = new JSONParser();
        FileReader reader = new FileReader("./src/test/resources/user.json");
        JSONArray users = (JSONArray) parser.parse(reader);
        reader.close();

        // Get the last user and store old Gmail
        JSONObject lastUser = (JSONObject) users.get(users.size() - 1);
        String oldEmail = lastUser.get("email").toString();

        // Save old Gmail to localStorage.json
        JSONObject localStorage = new JSONObject();
        localStorage.put("oldEmail", oldEmail);

        FileWriter localWriter = new FileWriter("./src/test/resources/localStorage.json");
        localWriter.write(localStorage.toJSONString());
        localWriter.flush();
        localWriter.close();

        // Update on UI
        UpdateGmailPage updateGmailUser = new UpdateGmailPage(driver);
        String newGmail = "tasnimislam2334" + Utils.generateRandomId(100, 999) + "@gmail.com";

        updateGmailUser.updateUser(
                "",
                "",
                newGmail,
                "",
                "",
                ""
        );
        Thread.sleep(2000);
        driver.switchTo().alert().accept();

        // Update user.json with new Gmail
        for (Object obj : users) {
            JSONObject user = (JSONObject) obj;
            if (user.get("email").equals(oldEmail)) {
                user.put("email", newGmail);
                break;
            }
        }

        FileWriter writer = new FileWriter("./src/test/resources/user.json");
        writer.write(users.toJSONString());
        writer.flush();
        writer.close();
        Thread.sleep(2000);

        LoginPage logOut= new LoginPage(driver);
        logOut.doLogout();
    }



}
