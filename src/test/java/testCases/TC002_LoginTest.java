package testCases;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;

public class TC002_LoginTest extends BaseClass{

    @Test(groups = {"Sanity"})
    public void verify_login(){
        HomePage hp=new HomePage(driver);
        hp.clickMyAccount();
        hp.clickLogin();

        //Login
        LoginPage lp=new LoginPage(driver);
        lp.setEmail(p.getProperty("email"));
        lp.setPassword(p.getProperty("password"));
        lp.clickLogin();

        //MyAccount
        MyAccountPage macc=new MyAccountPage(driver);
        boolean targetPage= macc.isMyAccountPageExists();

        Assert.assertTrue(targetPage);
    }
}
