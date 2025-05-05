package page;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class ResetPasswrdPage {

    @FindBy(css="[type=email]")
    public WebElement txtEmail;

    @FindBy(css="[type=submit]")
    WebElement btnSumbit;

    @FindBy(tagName = "input")
    List<WebElement> txtInput;

    @FindBy(tagName = "button")
    WebElement btnReset;

    public ResetPasswrdPage(WebDriver driver){PageFactory.initElements(driver,this);}

    public void doResetPass(String email){
        txtEmail.sendKeys(email);
        btnSumbit.click();
    }

    public void doSetPass(String newPass,String confirmPass) throws InterruptedException {
        txtInput.get(0).sendKeys(newPass);
        txtInput.get(1).sendKeys(confirmPass);
        Thread.sleep(1500);
        btnReset.click();

    }
}
