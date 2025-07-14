package testCases;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

public class BaseClass {

    public WebDriver driver;
    public Properties p;

    @BeforeClass(groups = {"Sanity","Regression","Master"})
    @Parameters({"os","browser"})
    public void setup(String os, String br) throws InterruptedException, IOException {

        //loading config.properties
        FileReader file=new FileReader("src/test/resources/config.properties");
        p=new Properties();
        p.load(file);

        switch (br.toLowerCase())
        {
            case "chrome":driver=new ChromeDriver();break;
            case "edge":driver=new EdgeDriver();break;
            default: System.out.println("Invalid browser"); return;
        }
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get(p.getProperty("appURL")); //reading URL from properties file
        driver.manage().window().maximize();
        Thread.sleep(2000);
    }

    @AfterClass
    public void tearDown(){
        driver.quit();
    }

    public String randomString(){
        String generatedstring= RandomStringUtils.randomAlphabetic(5);
        return generatedstring;
    }

    public String randomNumber(){
        String generatednumber=RandomStringUtils.randomNumeric(10);
        return generatednumber;
    }

    public String randomAlphaNumberic(){
        String generatedstring=RandomStringUtils.randomAlphabetic(10);
        String generatednumber=RandomStringUtils.randomNumeric(10);
        return (generatedstring+"@"+generatednumber);
    }

    public String captureScreen(String tname) throws IOException{
        String timeStamp = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());

        TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
        File sourceFile = takesScreenshot.getScreenshotAs(OutputType.FILE);

        String targetFilePath = System.getProperty("user.dir")+"\\screenshots\\" + tname + "_" + timeStamp + ".png";
        File targetFile = new File(targetFilePath);

        sourceFile.renameTo(targetFile);
        return targetFilePath;
    }
}
