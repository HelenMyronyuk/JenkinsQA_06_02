package school.redrover.model;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.base.*;
import school.redrover.model.interfaces.IDashboardTable;
import school.redrover.model.interfaces.IDescription;

public class MainPage extends BaseDashboardPage<MainPage> implements IDescription<MainPage>, IDashboardTable<MainPage> {

    @FindBy(css = "[href='/manage']")
    private WebElement manageJenkins;

    @FindBy(xpath = "//a[@href='/me/my-views']")
    private WebElement myViews;

    @FindBy(xpath = "//h1[text()='Welcome to Jenkins!']")
    private WebElement welcomeToJenkins;

    @FindBy(linkText = "Delete Agent")
    private WebElement deleteAgent;

    public MainPage(WebDriver driver) {
        super(driver);
    }

    public ManageJenkinsPage clickManageJenkinsPage() {
        manageJenkins.click();

        return new ManageJenkinsPage(getDriver());
    }

    public MyViewsPage clickMyViewsSideMenuLink() {
        getWait5().until(ExpectedConditions.elementToBeClickable(myViews)).click();

        return new MyViewsPage(getDriver());
    }

    public boolean isMainPageOpen() {
        return getWait5().until(ExpectedConditions.titleContains("Dashboard [Jenkins]"));
    }

    public String getTitle() {
        return getDriver().getTitle();
    }

    public boolean isWelcomeDisplayed() {
        return welcomeToJenkins.isDisplayed();
    }

    public String getWelcomeText() {
        return welcomeToJenkins.getText();
    }

    public MainPage clickNodeDropdownMenu(String nodeName) {
        getWait5().until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//tr/th/a[@href='/manage/computer/" + nodeName + "/']/button")))
                .sendKeys(Keys.RETURN);

        return this;
    }

    public DeletePage<ManageNodesPage> selectDeleteAgentInDropdown() {
        getWait5().until(ExpectedConditions.elementToBeClickable(deleteAgent)).click();

        return new DeletePage<>(new ManageNodesPage(getDriver()));
    }

    public MainPage refreshBrowser() {
        getDriver().navigate().refresh();

        return new MainPage(getDriver());
    }
}
