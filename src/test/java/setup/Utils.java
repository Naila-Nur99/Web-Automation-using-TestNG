package setup;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

import static io.restassured.RestAssured.given;

public class Utils {

    private static final String CONFIG_PATH = "./src/test/resources/config.properties";

    public static int generateRandomId(int min, int max) {
        double randomId = Math.random() * (max - min) + min;
        return (int) randomId;
    }

    public static void saveUserData(String filePath, JSONObject jsonObject) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        JSONArray jsonArray = (JSONArray) parser.parse(new FileReader(filePath));
        jsonArray.add(jsonObject);
        FileWriter fileWriter = new FileWriter(filePath);
        fileWriter.write(jsonArray.toJSONString());
        fileWriter.flush();
        fileWriter.close();
    }

    public static void setEnvVar(String key, String value) throws ConfigurationException {
        PropertiesConfiguration config = new PropertiesConfiguration(CONFIG_PATH);
        config.setProperty(key, value);
        config.save();
    }

    public static String getEnvVar(String key) throws IOException {
        Properties prop = new Properties();
        FileReader reader = new FileReader(CONFIG_PATH);
        prop.load(reader);
        return prop.getProperty(key);
    }



    public static void getGmailList() throws IOException, ConfigurationException, InterruptedException {
        Thread.sleep(5000);
        String token = getEnvVar("gmail_token");

        RestAssured.baseURI = "https://gmail.googleapis.com";
        Response response = given()
                .contentType("application/json")
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/gmail/v1/users/me/messages");

        JsonPath jsonPath = response.jsonPath();
        String mailId = jsonPath.get("messages[0].id");
        System.out.println("Mail ID fetched: " + mailId);

        setEnvVar("mail_id", mailId);
    }

    public static String readMail() throws IOException, InterruptedException, ConfigurationException {
        getGmailList(); // fetch mail ID
        Thread.sleep(10000);

        String token = getEnvVar("gmail_token");
        String mailId = getEnvVar("mail_id");

        RestAssured.baseURI = "https://gmail.googleapis.com";
        Response response = given()
                .contentType("application/json")
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/gmail/v1/users/me/messages/" + mailId);

        JsonPath jsonPath = response.jsonPath();
        String mailBody = jsonPath.get("snippet");
        System.out.println("Email body: " + mailBody);

        return mailBody;
    }

    public static void scroll(WebDriver driver, int height) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,"+height+")");
    }
}
