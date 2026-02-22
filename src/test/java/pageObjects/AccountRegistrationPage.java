package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AccountRegistrationPage extends BasePage {
    public AccountRegistrationPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//input[@id='input-firstname']") WebElement txtFirstname;

    @FindBy(xpath = "//input[@id='input-lastname']") WebElement txtLastname;

    @FindBy(xpath = "//input[@id='input-email']") WebElement txtEmail;

    @FindBy(xpath = "//input[@id='input-telephone']") WebElement txtTelephone;

    @FindBy(xpath = "//input[@id='input-password']") WebElement txtPassword;

    @FindBy(xpath = "//input[@id='input-confirm']") WebElement txtConfirmPassword;

    @FindBy(xpath = "//input[@name='agree']") WebElement chkPrivPolicy;

    @FindBy(xpath = "//input[@value='Continue']") WebElement btnContinue;

    @FindBy(xpath = "//h1[normalize-space()='Your Account Has Been Created!']") WebElement msgConfirmation;

    public void setFirstName(String fName) {
        txtFirstname.sendKeys(fName);
    }

    public void setLastName(String lName) {
        txtLastname.sendKeys(lName);
    }

    public void setEmail(String email) {
        txtEmail.sendKeys(email);
    }

    public void setTelephone(String tel) {
        txtTelephone.sendKeys(tel);
    }

    public void setPassword(String pwd) {
        txtPassword.sendKeys(pwd);
    }

    public void setConfirmPassword(String pwd) {
        txtConfirmPassword.sendKeys(pwd);
    }

    public void setPrivacyPolicy() {
        chkPrivPolicy.click();
    }

    public void clickContinue() {
        // Solution 1:
        btnContinue.click();
    }

    public String getConfirmationMessage() {
        try {
            return (msgConfirmation.getText());
        } catch (Exception e) {
            return (e.getMessage());
        }
    }
}
