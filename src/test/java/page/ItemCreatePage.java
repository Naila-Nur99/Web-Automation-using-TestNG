package page;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import setup.ItemModel;
import setup.Utils;

import java.time.Duration;
import java.util.List;

public class ItemCreatePage {
    WebDriver driver;
    @FindBy(className="add-cost-button")
    WebElement btnAddCost;

    @FindBy(id="itemName")
    WebElement txtItemName;

    @FindBy(css="[type=button]")
    List<WebElement> btnValue;
    @FindBy(id="amount")
    WebElement txtAmount;

    @FindBy(id="purchaseDate")
    WebElement txtDate;

    @FindBy(id = "month")
    WebElement monthInput;

    @FindBy(id="remarks")
    WebElement txtRemarks;

    @FindBy(css="[type=submit]")
    WebElement btnSubmit;

    public ItemCreatePage(WebDriver driver){
        PageFactory.initElements(driver,this);
        this.driver = driver;
    }

    public void addingItem(String itemName,String amount,
                           String purchaseDate,String month,String remarks) throws InterruptedException {
        btnAddCost.click();
        txtItemName.sendKeys(itemName);
        btnValue.get(2).click();
        txtAmount.sendKeys(amount);

        //txtDate.sendKeys(Keys.CONTROL,"a");
        //txtDate.sendKeys(Keys.BACK_SPACE);
        txtDate.sendKeys(purchaseDate);

        monthInput.sendKeys(month);


        txtRemarks.sendKeys(remarks);
        btnSubmit.click();

    }
    public void showItem() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tbody/tr[1]/td")));

        List<WebElement> firstRowData = driver.findElements(By.xpath("//tbody/tr[1]/td"));
        String firstItem = firstRowData.get(0).getText();
        System.out.println("First item text: " + firstItem);
        Assert.assertTrue(firstItem.contains("Air Freshener"));

        List<WebElement> secondRowData = driver.findElements(By.xpath("//tbody/tr[2]/td"));
        if (secondRowData.isEmpty()) {
            Assert.fail("Second row is missing. Possibly only one item present.");
        }
        String secondItem = secondRowData.get(0).getText();
        System.out.println("Second item text: " + secondItem);
        Assert.assertTrue(secondItem.contains("Book"));
    }


}
