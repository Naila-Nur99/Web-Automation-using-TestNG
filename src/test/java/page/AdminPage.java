package page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class AdminPage {
    @FindBy(className = "search-box")
    public WebElement searchInput;

    public AdminPage(WebDriver driver){
        PageFactory.initElements(driver, this);
    }
}
