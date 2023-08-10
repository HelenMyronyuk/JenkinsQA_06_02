package school.redrover.model;

import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.base.*;
import school.redrover.model.interfaces.IAlert;
import school.redrover.model.interfaces.IDashboardTable;
import school.redrover.model.interfaces.IDescription;
import school.redrover.model.interfaces.IViewBar;
import school.redrover.model.manageJenkins.ManageJenkinsPage;
import school.redrover.model.manageJenkins.ManageNodesPage;
import school.redrover.model.views.MyViewsPage;

public class MainPage extends BaseDashboardPage<MainPage> implements IDescription<MainPage>, IDashboardTable<MainPage>, IViewBar, IAlert<MainPage> {

    @FindBy(css = "[href='/manage']")
    private WebElement manageJenkins;

    @FindBy(xpath = "//a[@href='/me/my-views']")
    private WebElement myViews;

    @FindBy(xpath = "//h1[text()='Welcome to Jenkins!']")
    private WebElement welcomeToJenkins;

    @FindBy(linkText = "Delete Agent")
    private WebElement deleteAgent;

    @FindBy(xpath = "//a[text()='Icon legend']")
    private WebElement iconLegendButton;

    public MainPage(WebDriver driver) {
        super(driver);
    }

    @Step("click 'Manage Jenkins' link from Main page")
    public ManageJenkinsPage clickManageJenkinsPage() {
        manageJenkins.click();

        return new ManageJenkinsPage(getDriver());
    }

    @Step("click 'My Views' link from side menu")
    public MyViewsPage clickMyViewsSideMenuLink() {
        getWait5().until(ExpectedConditions.elementToBeClickable(myViews)).click();

        return new MyViewsPage(getDriver());
    }

    @Step("Check if Main page is open")
    public boolean isMainPageOpen() {
        return getWait5().until(ExpectedConditions.titleContains("Dashboard [Jenkins]"));
    }

    @Step("Get Heading text on Main page")
    public String getTitle() {
        return getDriver().getTitle();
    }

    @Step("Check if 'Welcome' is displayed on Main page")
    public boolean isWelcomeDisplayed() {
        return welcomeToJenkins.isDisplayed();
    }

    @Step("Get text 'Welcome' on Main page")
    public String getWelcomeText() {
        return welcomeToJenkins.getText();
    }

    @Step("click '{nodeName}' link from Dropdown menu")
    public MainPage clickNodeDropdownMenu(String nodeName) {
        getWait5().until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//tr/th/a[@href='/manage/computer/" + nodeName + "/']/button")))
                .sendKeys(Keys.RETURN);

        return this;
    }

    @Step("Select 'DeleteAgent' link from Dropdown menu")
    public DeletePage<ManageNodesPage> selectDeleteAgentInDropdown() {
        getWait5().until(ExpectedConditions.elementToBeClickable(deleteAgent)).click();

        return new DeletePage<>(new ManageNodesPage(getDriver()));
    }

    public IconLegendPage clickIconLegend() {
        iconLegendButton.click();

        return new IconLegendPage(getDriver());
    }
}
