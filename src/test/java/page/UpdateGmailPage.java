package page;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class UpdateGmailPage {

    @FindBy(xpath = "//button[@aria-label='account of current user']")
    public WebElement btnProfile;

    @FindBy(tagName = "input")
    List<WebElement> txtInput;

    @FindBy(css="[type=button]")
    List<WebElement> btnProfileIcon;

    @FindBy(css = "[role=menuitem]")
    List<WebElement> menuItem;

    public UpdateGmailPage(WebDriver driver){
        PageFactory.initElements(driver,this);
    }

    public void updateUser(String firstName,String lastName,String email,String phoneNumber,String address,String gender) throws InterruptedException {
        btnProfile.click();  //profile icon
        menuItem.get(0).click();  //click profile
        btnProfileIcon.get(1).click();  //click edit
        Thread.sleep(1500);
        txtInput.get(1).sendKeys(firstName);
        txtInput.get(2).sendKeys(lastName);
        txtInput.get(3).sendKeys(Keys.CONTROL+"a", Keys.BACK_SPACE);
        txtInput.get(3).sendKeys(email);
        txtInput.get(4).sendKeys(phoneNumber);
        txtInput.get(5).sendKeys(address);
        txtInput.get(6).sendKeys(gender);
        Thread.sleep(2000);
        btnProfileIcon.get(2).click();



    }









}
