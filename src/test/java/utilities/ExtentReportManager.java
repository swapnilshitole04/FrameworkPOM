package utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ExtentReportManager implements ITestListener {

    public ExtentSparkReporter sparkReporter; //UI of report
    public ExtentReports extent; //populate common info on the report
    public ExtentTest test; //creating testcase entries in the report & update status of hte test methods

    public void onStart(ITestContext testContext){

        /*SimpleDateFormat df=new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
        Date dt=new Date();
        String currentdatetimestamp=df.format(dt);*/

        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()); //time stamp
        String repName = "Test-Report-" + timeStamp + ".html";
        sparkReporter=new ExtentSparkReporter(".\\reports\\" + repName); //specify report location

        sparkReporter.config().setDocumentTitle("opencart Automation Report"); //Title of report
        sparkReporter.config().setReportName("opencart Functional Testing");  //name of report
        sparkReporter.config().setTheme(Theme.DARK); //theme

        extent=new ExtentReports();
        extent.attachReporter(sparkReporter);

        extent.setSystemInfo("Application","opencart");
        extent.setSystemInfo("Environment","QA");
        extent.setSystemInfo("Tester Name","Swapnil");
        extent.setSystemInfo("os","Windows10");
        extent.setSystemInfo("Browser Name","Chrome");

        String os=testContext.getCurrentXmlTest().getParameter("os");
        extent.setSystemInfo("Operating System", os);

        String browser=testContext.getCurrentXmlTest().getParameter("browser");
        extent.setSystemInfo("Browser", browser);
    }
    public void onTestSuccess(ITestResult result){
        test=extent.createTest(result.getTestClass().getName()); //create a new entry in report
        test.assignCategory(result.getMethod().getGroups());
        test.log(Status.PASS,"Test case passed is:"+ result.getName()+"got successfully executed"); //update status p/f/s
    }
    public void onTestFailure(ITestResult result){
        test=extent.createTest(result.getTestClass().getName());
        test.assignCategory(result.getMethod().getGroups());

        test.log(Status.FAIL, result.getName()+"got failed");
        test.log(Status.INFO, result.getThrowable().getMessage());
    }
    public void onTestSkipped(ITestResult result){
        test=extent.createTest(result.getTestClass().getName());
        test.assignCategory(result.getMethod().getGroups());
        test.log(Status.SKIP, result.getName()+ "got skipped");
        test.log(Status.INFO, result.getThrowable().getMessage());
    }
    public void onFinish(ITestContext context){
        extent.flush();
    }
}
