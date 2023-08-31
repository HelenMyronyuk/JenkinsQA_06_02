package school.redrover.model.base;

import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.*;
import school.redrover.model.manageJenkins.ManageNodesPage;
import school.redrover.runner.TestUtils;

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

    @Step("Click on 'New Item' on the side menu")
    public NewJobPage clickNewItemFromSideMenu() {
        newItem.click();

        return new NewJobPage(getDriver());
    }

    @Step("Click on 'People' on the side menu")
    public PeoplePage clickPeopleFromSideMenu() {
        people.click();

        return new PeoplePage(getDriver());
    }

    @Step("Click 'New Item', 'People', 'Build History', 'Manage Jenkins', 'My Views' on the side menu")
    public <ReturnedPage extends BaseMainHeaderPage<?>> ReturnedPage clickOptionFromSideMenu(ReturnedPage pageToReturn, String sideMenuLink) {
        getWait2().until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(@href, '"+sideMenuLink+"')]"))).click();

        return pageToReturn;
    }

    @Step("Click 'Changes', 'Console Output', 'Edit Build Information', 'Delete build', 'Replay', 'Pipeline Steps' and 'Workspaces' on build drop-down menu")
    public <SubmenuPage extends BaseMainHeaderPage<?>> SubmenuPage selectOptionFromDropDownList(SubmenuPage submenuPage) {
        WebElement option = getDriver().
                findElement(By.xpath("//div[@id='breadcrumb-menu-target']//span[contains(text(),'" + submenuPage.callByMenuItemName() + "')]"));

        TestUtils.scrollWithPauseByActions(this, option, 800);
        new Actions(getDriver())
                .moveToElement(option)
                .click()
                .perform();

        return submenuPage;
    }

    @Step("Click 'Build History' on the side menu")
    public BuildHistoryPage clickBuildsHistoryFromSideMenu() {
        getWait5().until(ExpectedConditions.elementToBeClickable(buildHistory)).click();

        return new BuildHistoryPage(getDriver());
    }

    @Step("Click 'Build Executor Status' under side menu")
    public ManageNodesPage clickBuildExecutorStatus() {
        getWait2().until(ExpectedConditions.elementToBeClickable(buildExecutorStatus)).click();

        return new ManageNodesPage(getDriver());
    }

    @Step("Click 'Small', 'Medium' or 'Large' Dashboard Table Size")
    public Self clickChangeJenkinsTableSize(String size) {
        getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@tooltip='" + size + "']"))).click();

        return (Self) this;
    }
}
