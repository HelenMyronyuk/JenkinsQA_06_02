package school.redrover.model.base;

import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.*;
import school.redrover.model.manageJenkins.ManageNodesPage;

public abstract class BaseDashboardPage<Self extends BaseDashboardPage<?>> extends BaseSubmenuPage<Self> {

    @FindBy(css = ".task-link-wrapper>a[href$='newJob']")
    private WebElement newItem;

    @FindBy(xpath = "//a[@href='/asynchPeople/']")
    private WebElement people;

    @FindBy(xpath = "//a[@href='/view/all/builds']")
    private WebElement buildHistory;

    @FindBy(xpath = "//a[@href='/computer/']")
    private WebElement buildExecutorStatus;

    public BaseDashboardPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public String callByMenuItemName() {
        return "Reload Configuration from Disk";
    }

    @Step("Click on 'New Item' in the side menu")
    public NewJobPage clickNewItemFromSideMenu() {
        newItem.click();

        return new NewJobPage(getDriver());
    }

    public PeoplePage clickPeopleFromSideMenu() {
        people.click();

        return new PeoplePage(getDriver());
    }

    public <ReturnedPage extends BaseMainHeaderPage<?>> ReturnedPage clickOptionFromSideMenu(ReturnedPage pageToReturn, String sideMenuLink) {
        getWait2().until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@href='" + sideMenuLink + "']"))).click();

        return pageToReturn;
    }

    public <ReturnedPage extends BaseMainHeaderPage<?>> ReturnedPage clickBuildOptionFromDropDownMenu(ReturnedPage pageToReturn, String dropDownMenuLink) {
        getWait2().until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(text(),'" + dropDownMenuLink + "')]"))).click();

        return pageToReturn;
    }

    public BuildHistoryPage clickBuildsHistoryFromSideMenu() {
        getWait5().until(ExpectedConditions.elementToBeClickable(buildHistory)).click();

        return new BuildHistoryPage(getDriver());
    }

    public ManageNodesPage clickBuildExecutorStatus() {
        getWait2().until(ExpectedConditions.elementToBeClickable(buildExecutorStatus)).click();

        return new ManageNodesPage(getDriver());
    }

    public Self clickChangeJenkinsTableSize(String size) {
        getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@tooltip='" + size + "']"))).click();

        return (Self) this;
    }
}
