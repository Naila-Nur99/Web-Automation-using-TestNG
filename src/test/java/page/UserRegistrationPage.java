package page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import setup.UserModel;

import java.util.List;

public class UserRegistrationPage {
    @FindBy(tagName = "input")
    List<WebElement> txtInput;
    @FindBy(id="register")
    WebElement btnRegister;
    public UserRegistrationPage(WebDriver driver){
        PageFactory.initElements(driver,this);
    }

    public void doRegister(UserModel userModel){
            txtInput.get(0).sendKeys(userModel.getFirstname()); //firstname
            txtInput.get(1).sendKeys(userModel.getLastname()); //lastname
            txtInput.get(2).sendKeys(userModel.getEmail()); //email
            txtInput.get(3).sendKeys(userModel.getPassword()); //password
            txtInput.get(4).sendKeys(userModel.getPhonenumber()); //phonenumber'
            txtInput.get(5).sendKeys(userModel.getAddress()); //address
            txtInput.get(6).click(); //select male gender
            txtInput.get(8).click(); //accept terms
            btnRegister.click();
        }
    public void doRegistercsv(String firstName, String lastname, String email, String password,String phoneNumber, String address){
        txtInput.get(0).sendKeys(firstName); //firstname
        txtInput.get(1).sendKeys(lastname); //lastname
        txtInput.get(2).sendKeys(email); //email
        txtInput.get(3).sendKeys(password); //password
        txtInput.get(4).sendKeys(phoneNumber); //phonenumber'
        txtInput.get(5).sendKeys(address); //address
        txtInput.get(6).click(); //select male gender
        txtInput.get(8).click(); //accept terms
        btnRegister.click();
    }
}