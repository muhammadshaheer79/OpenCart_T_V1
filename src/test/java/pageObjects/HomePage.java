package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends BasePage {

    public HomePage(WebDriver driver) {
        super(driver);  // passing driver argument to parent class constructor defined in BasePage using super keyword
        // the parent class will then initiate the driver w/ PageFactory object class features using the constructor it has
    }

    @FindBy(xpath = "//span[normalize-space()='My Account']") WebElement pLinkMyAccount;
    @FindBy(xpath = "//a[normalize-space()='Register']") WebElement liLinkRegister;
    @FindBy(xpath = "//a[normalize-space()='Login']") WebElement liLinkLogin;

    public void clickMyAccount() {
        pLinkMyAccount.click();
    }

    public void clickRegister() {
        liLinkRegister.click();
    }

    public void clickLogin() {
        liLinkLogin.click();
    }
}
