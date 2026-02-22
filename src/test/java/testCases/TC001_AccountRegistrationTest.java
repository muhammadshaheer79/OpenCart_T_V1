package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.AccountRegistrationPage;
import pageObjects.HomePage;
import testBase.BaseClass;

public class TC001_AccountRegistrationTest extends BaseClass {

    @Test(groups = {"Regression", "Master"})
    public void verifyAccountRegistration() {

        logger.info("****  Beginning TC001_AccountRegistrationTest execution  ****");

        try {
            HomePage hp = new HomePage(driver);
            hp.clickMyAccount();
            logger.info("Clicked on MyAccount dropdown link element");
            hp.clickRegister();
            logger.info("Clicked on Register rolled out link item element");

            AccountRegistrationPage regPage = new AccountRegistrationPage(driver);

            logger.info("Filling out customer details in Register Account form...");
            regPage.setFirstName(Character.toUpperCase(genRandomString().charAt(0)) + genRandomString().substring(1));
            regPage.setLastName(Character.toUpperCase(genRandomString().charAt(0)) + genRandomString().substring(1));

            regPage.setEmail(genRandomString() + "@outlook.com");  // dynamic generation of email
            regPage.setTelephone(genRandomNumber());    // dynamic generation of telephone no.

            String password = genRandomAlphaNumeric();
            regPage.setPassword(password);     // dynamic generation of password no.
            regPage.setConfirmPassword(password);

            regPage.setPrivacyPolicy();
            regPage.clickContinue();

            logger.info("Validating expected confirmation message");
            String confirmMsg = regPage.getConfirmationMessage();

            if (confirmMsg.equals("Your Account Has Been Created!")) {
                Assert.assertTrue(true);
            } else {
                logger.error("The test case TC001_AccountRegistrationTest failed");
                logger.debug("Debug logs...");
                Assert.assertTrue(false);
            }
//            Assert.assertEquals(confirmMsg, "Your Account Has Been Created!");
        } catch (Exception e) {
//            logger.error("The test case TC001_AccountRegistrationTest failed");
//            logger.debug("Debug logs...");
            Assert.fail();
        } finally {
            logger.info("****  Finished TC001_AccountRegistrationTest execution  ****");
        }


    }
}
