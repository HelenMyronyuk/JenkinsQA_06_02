package school.redrover.runner;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;
import school.redrover.runner.order.OrderForTests;
import school.redrover.runner.order.OrderUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

@Listeners({FilterForTests.class, OrderForTests.class})
public abstract class BaseTest {

    private WebDriver driver;

    private OrderUtils.MethodsOrder<Method> methodsOrder;

    Set<String> jobNames = new HashSet<>();
    Set<String> viewNames = new HashSet<>();
    Set<String> myViewNames = new HashSet<>();
    Set<String> userNames = new HashSet<>();
    Set<String> nodeNames = new HashSet<>();

    @BeforeClass
    protected void beforeClass() {
        methodsOrder = OrderUtils.createMethodsOrder(
                Arrays.stream(this.getClass().getMethods())
                        .filter(m -> m.getAnnotation(Test.class) != null && m.getAnnotation(Ignore.class) == null)
                        .collect(Collectors.toList()),
                m -> m.getName(),
                m -> m.getAnnotation(Test.class).dependsOnMethods());
    }

    @BeforeMethod
    protected void beforeMethod(Method method) {
        ProjectUtils.logf("Run %s.%s", this.getClass().getName(), method.getName());
        try {
            if (!methodsOrder.isGroupStarted(method) || methodsOrder.isGroupFinished(method)) {

                startDriver();
                getWeb();
                loginWeb();
            } else {
                getWeb();
            }
        } catch (Exception e) {
            closeDriver();
            throw new RuntimeException(e);
        } finally {
            methodsOrder.markAsInvoked(method);
        }
    }

//    protected void clearData() {
//        ProjectUtils.log("Clear data");
//        JenkinsUtils.clearData();
//    }

    protected void loginWeb() {
        ProjectUtils.log("Login");
        JenkinsUtils.login(driver);
    }

    protected void getWeb() {
        ProjectUtils.log("Get web page");
        ProjectUtils.get(driver);
    }

    protected void startDriver() {
        ProjectUtils.log("Browser open");

        int count = 0;
        do {
            try {
                Thread.sleep(500);
                driver = ProjectUtils.createDriver();
            } catch (Exception e) {
                if (++count >= 3) {
                    throw new RuntimeException(e);
                }
            }
        } while (driver == null);

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    protected void stopDriver() {
        try {
            JenkinsUtils.logout(driver);
        } catch (Exception ignore) {
        }

        closeDriver();
    }

    protected void closeDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
            ProjectUtils.log("Browser closed");
        }
    }

    @AfterMethod(alwaysRun = true)
    protected void afterMethod(Method method, ITestResult testResult) {
        if (!testResult.isSuccess() && ProjectUtils.isServerRun()) {
           File file = ProjectUtils.takeScreenshot(driver, method.getName(), this.getClass().getName());
            try {
                Allure.addAttachment("Page state: ", FileUtils.openInputStream(file));
            } catch (IOException e) {
                ProjectUtils.log("Couldn't make a screenshot because of exception: " + e.getMessage());
            }
            ProjectUtils.captureDOM(driver, method.getName(), this.getClass().getName());
        }

        if (!testResult.isSuccess() || methodsOrder.isGroupFinished(method)) {
            stopDriver();
        }
        clearData();
        ProjectUtils.logf("Execution time is %o sec\n\n", (testResult.getEndMillis() - testResult.getStartMillis()) / 1000);
    }

    protected WebDriver getDriver() {
        return driver;
    }

//    protected WebDriverWait getWait5() {
//        if (wait5 == null) {
//            wait5 = new WebDriverWait(getDriver(), Duration.ofSeconds(5));
//        }
//        return wait5;
//    }
//
//    protected WebDriverWait getWait2() {
//        if (wait2 == null) {
//            wait2 = new WebDriverWait(getDriver(), Duration.ofSeconds(2));
//        }
//        return wait2;
//    }
//
//    protected WebDriverWait getWait10() {
//        if (wait10 == null) {
//            wait10 = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
//        }
//        return wait10;
//    }

    @Step("Init Job Name")
    public String initName(){
        String jobName = RandomStringUtils.randomAlphanumeric(7);
        jobNames.add(jobName);
        Allure.addAttachment("Job name for class %s".formatted(this.getClass().getName()), jobName);
        return jobName;
    }

    @Step("Init View Name")
    public String initViewName(){
        String viewName = RandomStringUtils.randomAlphanumeric(7);
        viewNames.add(viewName);
        Allure.addAttachment("View name for class %s".formatted(this.getClass().getName()), viewName);
        return viewName;
    }

    @Step("Init User Name")
    public String initUserName(){
        String userName = RandomStringUtils.randomAlphanumeric(7);
        userNames.add(userName);
        Allure.addAttachment("View name for class %s".formatted(this.getClass().getName()), userName);
        return userName;
    }

    @Step("Clear Data")
    public void clearData(){
        JenkinsUtils.deleteViews(viewNames);
        JenkinsUtils.deleteMyViews(myViewNames);
        JenkinsUtils.deleteJobs(jobNames);
        JenkinsUtils.deleteUsers(userNames);
        JenkinsUtils.deleteNodes(nodeNames);
        JenkinsUtils.deleteDescription();
    }

}
