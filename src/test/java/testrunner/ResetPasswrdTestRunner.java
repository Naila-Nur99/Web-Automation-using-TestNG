package testrunner;

import org.apache.commons.configuration.ConfigurationException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import page.ResetPasswrdPage;
import setup.Setup;
import setup.Utils;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;


public class ResetPasswrdTestRunner extends Setup {

        // Method to refresh or clear input fields
        public void clearFields() throws InterruptedException {
            driver.navigate().refresh();
            Thread.sleep(1000);

        }

        // Negative Test: Unregistered email
        @Test(priority = 1, description = "Negative Case: Click Reset Link with Unregistered Email")
        public void nonRegisteredEmail() throws InterruptedException {
            clearFields();
            driver.findElement(By.partialLinkText("Reset it here")).click();
            ResetPasswrdPage resetPage = new ResetPasswrdPage(driver);
            resetPage.doResetPass("bjijhbi@gmail.com");

            Thread.sleep(1000);
            String msgActual = driver.findElement(By.tagName("p")).getText();
            String msgExpected = "Your email is not registered";
            Assert.assertTrue(msgActual.contains(msgExpected));
            clearFields();
        }

        @Test(priority = 2, description = "Negative Case: Click Reset Link with Blank Fields")
        public void blankEmailField() throws InterruptedException {
            clearFields();

            ResetPasswrdPage resetPage = new ResetPasswrdPage(driver);
            resetPage.doResetPass("");

            String requiredActual = resetPage.txtEmail.getAttribute("validationMessage");
            String expectedTxt = "Please fill out this field";
            System.out.println(requiredActual);
            Assert.assertTrue(requiredActual.contains(expectedTxt));


        }



        @Test(priority = 3, description = "Click Reset Link with Valid Email")
        public void registeredEmail() throws Exception {
            clearFields();
            ResetPasswrdPage resetPage = new ResetPasswrdPage(driver);

            // Parse JSON to get last registered email
            JSONParser parser = new JSONParser();
            FileReader reader = new FileReader("./src/test/resources/user.json");
            Object obj = parser.parse(reader);
            JSONArray users = (JSONArray) obj;
            JSONObject lastUser = (JSONObject) users.get(users.size() - 1);
            String email = lastUser.get("email").toString();

            resetPage.doResetPass(email);
            Thread.sleep(1000);
            String msgActual = driver.findElement(By.tagName("p")).getText();
            String msgExpected = "Password reset link sent to your email";
            Assert.assertTrue(msgActual.contains(msgExpected));
        }


        public String extractResetLinkFromEmail() throws InterruptedException, ConfigurationException, IOException {
            Thread.sleep(5000); // wait for email delivery
            String mailBody = Utils.readMail();

            for (String extractedEmail : mailBody.split(" ")) {
                if (extractedEmail.startsWith("https://")) {
                    Utils.setEnvVar("reset_link", extractedEmail);  // store the reset link
                    return extractedEmail;
                }
            }

            throw new RuntimeException("Reset link not found in email body.");
        }

    @Test(priority = 4, description = "Set New Password by retriving password reset mail ")
    public void doSetPass() throws Exception {

        registeredEmail();
        String resetUrl = extractResetLinkFromEmail();
        System.out.println("Reset URL: " + resetUrl);

        driver.get(resetUrl);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));


        String password = "Test@12345";
        ResetPasswrdPage resetPage = new ResetPasswrdPage(driver);
        resetPage.doSetPass(password, password);

        WebElement messageElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("p")));
        String msgActual = messageElement.getText();
        String msgExpected = "Password reset successfully";

        Assert.assertTrue(msgActual.contains(msgExpected));
        System.out.println("Actual reset message: " + msgActual);

        JSONParser parser = new JSONParser();
        FileReader reader = new FileReader("./src/test/resources/user.json");
        JSONArray users = (JSONArray) parser.parse(reader);
        reader.close();

        JSONObject lastUser = (JSONObject) users.get(users.size() - 1);
        String email = lastUser.get("email").toString();

        for (Object obj : users) {
            JSONObject user = (JSONObject) obj;
            if (user.get("email").equals(email)) {
                user.put("password", password);
                break;
            }
        }

        FileWriter writer = new FileWriter("./src/test/resources/user.json");
        writer.write(users.toJSONString());
        writer.flush();
        writer.close();
    }



}


