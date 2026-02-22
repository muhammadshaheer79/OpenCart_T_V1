package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;
import utilities.DataProviders;

public class TC003_LoginDDT extends BaseClass {

    // getting data provider from a class belonging to a different package hence need to use dataProviderCLass parameter
    @Test(dataProvider = "LoginData", dataProviderClass = DataProviders.class, groups = "Datadriven")
    public void verifyLoginDDT(String email, String pwd, String exp) {

        logger.info("****  Beginning TC003_LoginDDT execution  ****");
        try {
            // HomePage object:
            HomePage hp = new HomePage(driver);
            hp.clickMyAccount();
            logger.info("Clicked on MyAccount dropdown link element");
            hp.clickLogin();
            logger.info("Clicked on Login rolled out link item element");

            // LoginPage object:
            LoginPage lp = new LoginPage(driver);
            logger.info("Filling out credentials in the Returning Customer form...");
            lp.setEmail(email);
            lp.setPassword(pwd);
            lp.clickLogin();

            //MyAccountPage object:
            MyAccountPage maccp = new MyAccountPage(driver);

            logger.info("Validating expected My Account h2 heading");
            boolean targetPage = maccp.isThisMyAccountPage();

            if (exp.equalsIgnoreCase("Valid")) {
                if (targetPage == true) {
                    logger.info("Clicked on Logout link item element");
                    maccp.clickLogout();
                    logger.info("Test case passes for provided valid credentials as input");
                    Assert.assertTrue(true);
                } else {
                    logger.info("Test case fails for provided valid credentials as input");
                    Assert.assertTrue(false);
                }
            }

            if (exp.equalsIgnoreCase("Invalid")) {
                if (targetPage == true) {
                    logger.info("Clicked on Logout link item element");
                    maccp.clickLogout();
                    logger.info("Test case fails for provided invalid credentials as input");
                    Assert.assertTrue(false);
                } else {
                    logger.info("Test case passes for provided invalid credentials as input");
                    Assert.assertTrue(true);
                }
            }
        } catch (Exception e) {
            Assert.fail();
        } finally {
            logger.info("****  Finished TC003_LoginDDT execution  ****");
        }
    }
}
