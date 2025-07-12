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

public class TC001_AccountRegistrationTest extends BaseClass{

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
}
