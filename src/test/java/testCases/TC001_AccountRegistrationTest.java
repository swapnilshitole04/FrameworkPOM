package testCases;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pageObjects.AccountRegistrationPage;
import pageObjects.HomePage;

import java.time.Duration;

public class TC001_AccountRegistrationTest {

    public WebDriver driver;

    @BeforeClass
    public void setup() throws InterruptedException {
        driver=new ChromeDriver();
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://tutorialsninja.com/demo/");
        driver.manage().window().maximize();
        Thread.sleep(2000);
    }

    @AfterClass
    public void tearDown(){
        driver.quit();
    }

    @Test
    public void verify_account_registration() throws InterruptedException {
        HomePage hp=new HomePage(driver);
        hp.clickMyAccount();
        hp.clickRegister();

        AccountRegistrationPage regpage=new AccountRegistrationPage(driver);
        regpage.setFirstname(randomString().toUpperCase());
        regpage.setLastname(randomString().toUpperCase());
        regpage.setEmail(randomString()+"@gmail.com");
        regpage.setTelephone(randomNumber());

        String password=randomAlphaNumberic();
        regpage.setPassword(password);
        regpage.setConfirmPassword(password);
        regpage.setPrivacyPolicy();
        regpage.clickContinue();

        Thread.sleep(2000);

        String confmsg=regpage.getConfirmationMsg();
        Assert.assertEquals(confmsg,"Your Account Has Been Created!");
    }

    public String randomString(){
        String generatedstring=RandomStringUtils.randomAlphabetic(5);
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

}
