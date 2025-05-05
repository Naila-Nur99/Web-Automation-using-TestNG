package testrunner;

import com.github.javafaker.Faker;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import page.UserRegistrationPage;
import setup.Setup;
import setup.UserModel;
import setup.Utils;

import java.io.IOException;

public class CongratulationsEmailTestRunner extends Setup {



    @Test(description = "Check if new user can register and receives Congratulations email.")
    public void userRegistration() throws IOException, ParseException, InterruptedException, org.apache.commons.configuration.ConfigurationException {
        UserRegistrationPage registerNewUser = new UserRegistrationPage(driver);
        Faker faker = new Faker();

        driver.findElement(By.partialLinkText("Register")).click();

        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String email = "tasnimislam2334+"+ Utils.generateRandomId(10, 99999)+"@gmail.com";
        String password = "1234";
        String phoneNumber = "0170" + Utils.generateRandomId(1000000, 9999999);
        String address = "Dhaka";

        UserModel userModel = new UserModel();
        userModel.setFirstname(firstName);
        userModel.setLastname(lastName);
        userModel.setEmail(email);
        userModel.setPassword(password);
        userModel.setPhonenumber(phoneNumber);
        userModel.setAddress(address);
        registerNewUser.doRegister(userModel);

        JSONObject userJson = new JSONObject();
        userJson.put("firstName", firstName);
        userJson.put("lastName", lastName);
        userJson.put("email", email);
        userJson.put("password", password);
        userJson.put("phoneNumber", phoneNumber);
        userJson.put("address", address);
        Utils.saveUserData("./src/test/resources/user.json", userJson);

        String mailBody = Utils.readMail();
        Assert.assertTrue(mailBody.contains("Welcome to our platform"));
    }



}

