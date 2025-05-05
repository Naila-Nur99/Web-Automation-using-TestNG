package testrunner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import page.AdminPage;
import page.LoginPage;
import setup.Setup;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class AdminLoginTestRunner extends Setup {

    @Test(priority =1,description = "admin login")

    public void adminLogin() {

        driver.get("https://dailyfinance.roadtocareer.net/login");

        LoginPage loginPage = new LoginPage(driver);
        if (System.getProperty("email") != null && System.getProperty("password") != null) {
            loginPage.doLogin(System.getProperty("email"), System.getProperty("password"));
        } else {
            loginPage.doLogin("admin@test.com", "admin123");
        }
    }

    @Test(priority = 2,description = "Search by updated gmail on admin dashboard")
    public void searchUpdatedGmail() throws IOException, ParseException, FileNotFoundException {


        AdminPage adminPage = new AdminPage(driver);
        JSONParser jsonParser = new JSONParser();
        JSONArray jsonArray = (JSONArray) jsonParser.parse(new FileReader("./src/test/resources/user.json"));
        JSONObject userObj = (JSONObject) jsonArray.get(jsonArray.size()-1);
        String email =(String) userObj.get("email");
        adminPage.searchInput.sendKeys(email);
        List<WebElement> allData = driver.findElements(By.xpath("//tbody/tr[1]/td"));
        String actualEmail = allData.get(2).getText();
        Assert.assertTrue(actualEmail.contains(email));

        LoginPage logOut = new LoginPage(driver);
        logOut.doLogout();


    }





}
