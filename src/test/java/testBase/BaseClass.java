package testBase;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Parameters;

public class BaseClass {
    public static WebDriver driver;     // driver variable declared as static as well to make it common or be shared
                                        // among multiple object instances of the Base Class
    public Logger logger;   // log4j defined
    public Properties p;

    @BeforeClass(groups = {"Sanity", "Regression", "Master"})
    @Parameters({"os", "browser"})
    public void setup(String os, String br) throws IOException {

        // Loading config.properties file:
        FileReader file = new FileReader("./src//test//resources//config.properties");
        p = new Properties();
        p.load(file);

        // below statement fetches log4j2 xml file and loads or stores it into the logger variable
        // (log configuration done)
        logger = LogManager.getLogger(this.getClass());

        // for test framework execution on a grid environment retrieved from config.properties file:
        if (p.getProperty("execution_env").equalsIgnoreCase("remote")) {
            DesiredCapabilities capabilities = new DesiredCapabilities();

            // choosing O.S based on passed parameter from xml file:
            if (os.equalsIgnoreCase("windows")) {
                capabilities.setPlatform(Platform.WIN10);
            } else if (os.equalsIgnoreCase("mac")) {
                capabilities.setPlatform(Platform.MAC);
            } else if (os.equalsIgnoreCase("linux")) {
                capabilities.setPlatform(Platform.LINUX);
            } else {
                System.out.println("Oops!! No matching O.S found!");
                return;
            }

            // choosing web browser based on passed parameter from xml file:
            switch (br.toLowerCase()) {
                case "chrome":
                    capabilities.setBrowserName("chrome");
                    break;

                case "edge":
                    capabilities.setBrowserName("MicrosoftEdge");
                    break;

                case "firefox":
                    capabilities.setBrowserName("firefox");
                    break;
                default:
                    System.out.println("Sorry! No matching browser found!");
                    return;
            }

            // Initializing the driver according to the specific OS and browser configured for selenium hubs and nodes
            driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capabilities);
        }

        // To launch test on the passed browser parameter in local environment from xml file:
        if (p.getProperty("execution_env").equalsIgnoreCase("local")) {
            switch (br.toLowerCase()) {
                case "chrome":
                    driver = new ChromeDriver();
                    break;
                case "edge":
                    driver = new EdgeDriver();
                    break;
                case "firefox":
                    driver = new FirefoxDriver();
                    break;
                default:
                    System.out.println("Invalid browser name...");
                    return;
            }
        }

        driver.manage().deleteAllCookies();     // deletes all cookies info from the launched web page
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        driver.get(p.getProperty("clientAppURL1"));     // reading web app URL from properties file
        // driver.get(p.getProperty("clientAppURL3"));     // reading web app URL for docker-grid setup from properties file
        driver.manage().window().maximize();
    }

    @AfterClass(groups = {"Sanity", "Regression", "Master"})
    public void tearDown() {
        driver.quit();
    }

    public String genRandomString() {
        String generatedString = RandomStringUtils.randomAlphabetic(6);      // RandomStringUtils is a predefined class provided by
        // apache commons lang3 library that provides randomAlphabetic method which takes length of the string to be randomly
        // generated as input

        return generatedString;     // 6 character long random string value returned from method
    }

    public String genRandomNumber() {
        String generatedNumber = RandomStringUtils.randomNumeric(10);      // RandomStringUtils is a predefined class provided by
        // apache commons lang3 library that provides randomNumeric method which takes length of the number value in string format
        // to be randomly generated as input

        return generatedNumber;     // 6 character long random string value returned from method
    }

    public String genRandomAlphaNumeric() {
        String generatedAlphaNumStr = RandomStringUtils.randomAlphanumeric(8);      // RandomStringUtils is a predefined class provided by
        // apache commons lang3 library that provides randomAlphaNumeric method which takes length of the alphanumeric value in string format
        // to be randomly generated as input

        return generatedAlphaNumStr;     // 6 character long random string value returned from method
    }

    public String captureScreen(String tName) throws IOException {
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());

        TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
        File sourceFile = takesScreenshot.getScreenshotAs(OutputType.FILE);

        String targetFilePath = System.getProperty("user.dir") + ".\\screenshots\\" + tName + "_" + timeStamp + ".png";
        File targetFile = new File(targetFilePath);

        sourceFile.renameTo(targetFile);

        return targetFilePath;
    }
}
