package testrunner;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.testng.annotations.Test;
import page.LoginPage;
import page.RegDataSet;
import page.UserRegistrationPage;
import setup.Setup;
import setup.Utils;

import java.io.IOException;

public class UserRegCsvTestRunner extends Setup {
    @Test(dataProvider = "RegCSVData", dataProviderClass = RegDataSet.class)
    public void doCSVregistration(String firstName, String lastname, String email, String password,String phoneNumber, String address) throws IOException, ParseException {
        driver.findElement(By.linkText("Register")).click();
        UserRegistrationPage doReg = new UserRegistrationPage(driver);
        doReg.doRegistercsv(firstName,lastname,email,password,phoneNumber,address);

    }

}
