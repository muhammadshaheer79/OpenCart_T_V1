package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;

public class TC002_LoginTest extends BaseClass {

    @Test(groups = {"Sanity", "Master"})
    public void verifyLogin() {

        logger.info("****  Beginning TC002_LoginTest execution  ****");

        try {
            HomePage hp = new HomePage(driver);
            hp.clickMyAccount();
            logger.info("Clicked on MyAccount dropdown link element");
            hp.clickLogin();
            logger.info("Clicked on Login rolled out link item element");

            LoginPage lp = new LoginPage(driver);
            logger.info("Filling out credentials in the Returning Customer form...");
            lp.setEmail(p.getProperty("email"));
            lp.setPassword(p.getProperty("password"));
            lp.clickLogin();

            //MyAccountPage object:
            MyAccountPage maccp = new MyAccountPage(driver);

            logger.info("Validating expected My Account h2 heading");
            boolean targetPage = maccp.isThisMyAccountPage();

            if (targetPage) {
                Assert.assertTrue(true);
            } else {
                logger.error("The test case TC002_LoginTest failed");
                logger.debug("Debug logs...");
                Assert.assertTrue(false);
            }

        } catch (Exception e) {
            Assert.fail();
        } finally {
            logger.info("****  Finished TC002_LoginTest execution  ****");
        }
    }
}
