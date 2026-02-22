package utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.ImageHtmlEmail;
import org.apache.commons.mail.resolver.DataSourceUrlResolver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import testBase.BaseClass;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ExtentReportManager implements ITestListener {
    public ExtentSparkReporter sparkReporter;   // UI of the report
    public ExtentReports extent;    // populate common info on the report
    public ExtentTest test;     // creating test case entries in the report and update status of the test methods

    String repName;

    public void onStart(ITestContext testContext) {

        /*
            SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
            Date dt = new Date();
            String currentDateTimeStamp = df.format(dt);
        */

        // generating timestamp of relevant date format and attaching it to the test report name:
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        repName = "Test-Report-" + timeStamp + ".html";
        // Directory specified while instantiating ExtentSparkReporter object corresponding to a test report file:
        sparkReporter = new ExtentSparkReporter(".\\reports\\" + repName);

        sparkReporter.config().setDocumentTitle("OpenCart Automation Report");   // Title of the newly created report
        sparkReporter.config().setReportName("OpenCart Functional Testing");     // name of the newly created report
        sparkReporter.config().setTheme(Theme.DARK);    // Sets the theme of the report to be of black color
//        sparkReporter.config().setTheme(Theme.STANDARD);    // Sets the theme of the report to be of white color

        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);     // attaches common info with configurations and already
        // created report file specified by sparkReporter
        // It is responsible to combine the UI and common info outputs in one file

        // Populates common info as hash map (key-value pairs):
        extent.setSystemInfo("Application", "OpenCart");
        extent.setSystemInfo("Module", "Admin");
        extent.setSystemInfo("Sub-module", "Customers");
        extent.setSystemInfo("Tester name", System.getProperty("user.name"));
        extent.setSystemInfo("Environment", "QA");

        String os = testContext.getCurrentXmlTest().getParameter("os");
        extent.setSystemInfo("Operating System", os);

        String browser = testContext.getCurrentXmlTest().getParameter("browser");
        extent.setSystemInfo("Browser name", browser);

        List<String> includedGroups = testContext.getCurrentXmlTest().getIncludedGroups();

        if (!includedGroups.isEmpty()) {
            extent.setSystemInfo("Groups", includedGroups.toString());
        }

    }

    public void onTestSuccess(ITestResult result) {
        test = extent.createTest(result.getTestClass().getName());     // creates a new entry of test case method class that passed in the report dynamically
        test.assignCategory(result.getMethod().getGroups());    // to display group(s) assigned to test case method class in the report
        test.log(Status.PASS, "The test case which PASSED is: " + result.getName());    // updates status as pass
    }

    public void onTestFailure(ITestResult result) {
        test = extent.createTest(result.getTestClass().getName());     // creates a new entry of test case method class that failed in the report dynamically
        test.assignCategory(result.getMethod().getGroups());    // to display group(s) assigned to test case method class in the report
        test.log(Status.FAIL, "The test case which FAILED is: " + result.getName());    // updates status as fail
        test.log(Status.INFO, "The test case FAILED because: " + result.getThrowable().getMessage());    // writes reason for failure (exceptions or error) of test method

       // take screenshot on test case failure and attach it into the report:
       try {
           String imgPath = new BaseClass().captureScreen(result.getName());
           test.addScreenCaptureFromPath(imgPath);

       } catch(IOException e1) {
           e1.printStackTrace();
       }
    }

    public void onTestSkipped(ITestResult result) {
        test = extent.createTest(result.getTestClass().getName());     // creates a new entry of test case method class that was skipped in the report dynamically
        test.assignCategory(result.getMethod().getGroups());
        test.log(Status.SKIP, "The test case which was SKIPPED is: " + result.getName());    // updates status as skipped or ignored
        test.log(Status.INFO, "The test case was SKIPPED because: " + result.getThrowable().getMessage());
    }

    public void onFinish(ITestContext context) {
        extent.flush();     // this is important and mandatory as it is responsible to make everything we've
        // written above in the script actualize as report output
        String pathOfExtentReport = System.getProperty("user.dir") + "\\reports\\" + repName;
        File extentReport = new File(pathOfExtentReport);

        try {
            Desktop.getDesktop().browse(extentReport.toURI());      // opens the report on a browser automatically
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*
        try {
            URL url = new URL("file:///" + System.getProperty("user.dir") + "\\reports\\" + repName);
            // Creates the automated email message and sends it to whoever is specified: -
            ImageHtmlEmail email = new ImageHtmlEmail();
            email.setDataSourceResolver (new DataSourceUrlResolver(url));
            email.setHostName("smtp.googlemail.com");
            email.setSmtpPort(465);
            email.setAuthenticator(new DefaultAuthenticator("pavanoltraining@gmail.com","password"));
            email.setSSLOnConnect(true);
            email.setFrom("pavanoltraining@gmail.com"); // Sender
            email.setSubject("Test Results");
            email.setMsg("Please find Attached Report....");
            email.addTo("pavankumar.busyqa@gmail.com"); // Receiver
            email.attach(url, "extent report", "please check report...");
            email.send(); // sends the email
        }
        catch(Exception e) {
            e.printStackTrace();
        }
         */
    }
}
