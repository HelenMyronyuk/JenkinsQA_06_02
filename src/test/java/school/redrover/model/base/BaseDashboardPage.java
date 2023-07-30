package school.redrover.model.base;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.*;
import school.redrover.runner.TestUtils;

import java.util.List;

public abstract class BaseDashboardPage<Self extends BaseDashboardPage<?>> extends BaseSubmenuPage<Self> {

    @FindBy(css = ".task-link-wrapper>a[href$='newJob']")
    private WebElement newItem;

    @FindBy(xpath = "//span/a[@href='/asynchPeople/']")
    private WebElement people;

    @FindBy(xpath = "//span/a[@href='/view/all/builds']")
    private WebElement buildHistory;

    @FindBy(xpath = "//a[contains(@href,'/newView')]")
    private WebElement newView;

    @FindBy(xpath = "//a[@href='/computer/']")
    private WebElement buildExecutorStatus;

    @FindBy(xpath = "//div[@class = 'tab active']")
    private WebElement activeViewTab;

    public BaseDashboardPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public String callByMenuItemName() {
        return "Reload Configuration from Disk";
    }

    public NewJobPage selectNewItemInJobDropDownMenu(String folderName) {
        getWait5().until(ExpectedConditions.elementToBeClickable(By.xpath(String.format("//a[contains(@href,'/job/%s/newJob')]", folderName)))).click();

        return new NewJobPage(getDriver());
    }

    public NewJobPage clickNewItem() {
        newItem.click();

        return new NewJobPage(getDriver());
    }

    public PeoplePage clickPeopleOnLeftSideMenu() {
        people.click();

        return new PeoplePage(getDriver());
    }

    public BuildHistoryPage clickBuildsHistoryButton() {
        TestUtils.click(this, buildHistory);

        return new BuildHistoryPage(getDriver());
    }

    public NewViewPage createNewView() {
        getWait5().until(ExpectedConditions.elementToBeClickable(newView)).click();

        return new NewViewPage(getDriver());
    }

    public <ViewBasePage extends BaseDashboardPage<?>> ViewBasePage clickOnView(String viewName, ViewBasePage viewBasePage) {
        getWait2().until(ExpectedConditions.elementToBeClickable(
                By.xpath(String.format("//a[@href='/view/%s/']", viewName)))).click();

        return viewBasePage;
    }

    public boolean verifyViewIsPresent(String viewName) {
        boolean status = false;

        List<WebElement> views = getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='tabBar']")))
                .findElements(By.xpath("//div[@class='tabBar']/div"));
        for (WebElement view : views) {
            if (view.getText().equals(viewName)) {
                status = true;
                break;
            }
        }

        return status;
    }

    public ManageNodesPage clickBuildExecutorStatus() {
        getWait2().until(ExpectedConditions.elementToBeClickable(buildExecutorStatus)).click();

        return new ManageNodesPage(getDriver());
    }

    public String getActiveViewName() {
        return TestUtils.getText(this, activeViewTab);
    }

    public Self clickChangeJenkinsTableSize(String size) {
        getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@tooltip='" + size + "']"))).click();

        return (Self) this;
    }
}
