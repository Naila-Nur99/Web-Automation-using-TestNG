package testrunner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import page.ItemCreatePage;
import page.LoginPage;
import setup.ItemModel;
import setup.Setup;
import setup.Utils;

import java.io.FileReader;
import java.io.IOException;

public class ItemCreateTestRunner extends Setup {

    @BeforeTest
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
    @Test(priority = 1, description = "Add first item with all the fields")
    public void addFirstItem() throws InterruptedException {
        ItemCreatePage addNewItem=new ItemCreatePage(driver);
        String itemName="Air Freshener";
        String amount="1000";
        String purchaseDate="05/05/2025";
        String month="May";
        String remarks="Essential";
        addNewItem.addingItem(itemName,amount,purchaseDate,month,remarks);

        Thread.sleep(2000);
        driver.switchTo().alert().accept();

        String headerActual= driver.findElement(By.tagName("h2")).getText();
        String headerExpected="User Daily Costs";
        Assert.assertTrue(headerActual.contains(headerExpected),"Item Added");
    }
    @Test(priority = 2, description = "Add Second item with only mandatory fields")
    public void addSecondItem() throws InterruptedException {
        ItemCreatePage addNewItem=new ItemCreatePage(driver);
        String itemName="Book";
        String amount="200";
        String purchaseDate="05/05/2025";
        String month="";
        String remarks="";
        addNewItem.addingItem(itemName,amount,purchaseDate,month,remarks);

        Thread.sleep(2000);
        driver.switchTo().alert().accept();

        String headerActual= driver.findElement(By.tagName("h2")).getText();
        String headerExpected="User Daily Costs";
        Assert.assertTrue(headerActual.contains(headerExpected),"Item Added");
    }

    @Test(priority = 3, description = "Assert 2 items are showing on the item list")
    public void showItem() throws InterruptedException {
        ItemCreatePage addNewItem=new ItemCreatePage(driver);

        addNewItem.showItem();

    }




}
