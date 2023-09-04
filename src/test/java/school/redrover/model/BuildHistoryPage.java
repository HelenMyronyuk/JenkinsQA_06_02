package school.redrover.model;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.base.BaseMainHeaderPage;
import school.redrover.model.base.BaseSubmenuPage;
import school.redrover.model.builds.BuildPage;
import school.redrover.model.builds.ConsoleOutputPage;
import school.redrover.runner.TestUtils;

import java.time.Duration;
import java.util.List;

public class BuildHistoryPage extends BaseSubmenuPage<BuildHistoryPage> {

    @FindBy(xpath = "//table[@id='projectStatus']/tbody/tr/td[4]")
    private WebElement statusMessage;

    @FindBy(xpath = "//a[@class='jenkins-table__link jenkins-table__badge model-link inside']")
    private WebElement nameOfBuildLink;

    @FindBy(xpath = "//div[@class='timeline-event-bubble-title']/a")
    private WebElement bubbleTitle;

    @FindBy(xpath = "//h1")
    private WebElement pageHeader;

    @FindBy(xpath = "//table[@id='projectStatus']/tbody/tr")
    private List<WebElement> buildHistoryTable;

    @FindBy(xpath = "//table[@id='projectStatus']")
    private WebElement projectStatusTable;

    @FindBy(css = ".task-link-wrapper>a[href$='newJob']")
    private WebElement newItem;

    @FindBy(xpath = "//div[@id='label-tl-0-1-e1']")
    private WebElement lastBuildLinkFromTimeline;

    @FindBy(xpath = "(//div[contains(text(), 'default')])[1]")
    private WebElement lastDefaultBuildBubbleLinkFromTimeline;

    @FindBy(xpath = "//div[contains(@class, 'simileAjax-bubble-contentContainer-pngTranslucent')]")
    private WebElement buildBubblePopUp;

    @FindBy(xpath = "(//a[@class='jenkins-table__link jenkins-table__badge model-link inside' and not (contains(@href, 'default'))])[1]")
    private WebElement lastNotDefaultBuild;

    @FindBy(xpath = "//div[contains(@class, 'simileAjax-bubble-close-pngTranslucent')]")
    private WebElement closePopUpButtonInTimeline;

    @FindBy(xpath = "//div[@class = 'timeline-event-bubble-title']/a[contains(@href, '/default/1')]")
    private WebElement defaultBuildBubbleLink;

    @FindBy(xpath = "//div[@class='label-event-blue  event-blue  timeline-event-label' and not (contains(text(), 'default'))]")
    private WebElement lastNotDefaultBuildFromTimeline;

    @FindBy(xpath = "//div[@class='simileAjax-bubble-contentContainer simileAjax-bubble-contentContainer-pngTranslucent']//a")
    private WebElement notDefaultBuildLinkFromBubblePopUp;

    @FindBy(xpath = "//td/a[contains(@href, 'default')]/span")
    private WebElement defaultProjectLink;

    @FindBy(xpath = "//td/a[contains(@href, 'default')]/button")
    private WebElement defaultProjectDropdown;

    @FindBy(xpath = "//span[contains(text(),'Build Now')]")
    private WebElement buildNowButton;

    @FindBy(xpath = "//a[@class='jenkins-table__link jenkins-table__badge model-link inside' and contains(@href, '2')]")
    private WebElement newBuildLink;

    @FindBy(xpath = "//a[@class='jenkins-table__link jenkins-table__badge model-link inside' and contains(@href, 'default/2')]")
    private WebElement newDefaultBuildLink;

    public BuildHistoryPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public String callByMenuItemName() {
        return "Build History";
    }

    @Step("Click build console output on the Jenkins table")
    public ConsoleOutputPage clickProjectBuildConsole(String projectBuildName) {
        getDriver().findElement(By.xpath("//a[contains(@href, '" + projectBuildName + "')  and contains(@href, 'console') and not(contains(@href, 'default'))]")).click();

        return new ConsoleOutputPage(getDriver());
    }

    @Step("Get build status on the Jenkins table")
    public String getStatusMessageText() {
        new Actions(getDriver())
                .pause(3000)
                .moveToElement(statusMessage)
                .perform();

        getDriver().navigate().refresh();

        return statusMessage.getText();
    }

    @Step("Click build name on timeline")
    public BuildHistoryPage clickBuildNameOnTimeline(String projectBuildName) {
        getWait10().until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[contains(text(), '" + projectBuildName + "')]"))).click();

        return this;
    }

    @Step("Get bubble title on timeline")
    public boolean getBubbleTitleOnTimeline() {
        return getWait10().until(ExpectedConditions.visibilityOf(lastBuildLinkFromTimeline)).isDisplayed();
    }

    @Step("Get number of items on the Jenkins table")
    public int getNumberOfLinesInBuildHistoryTable() {
        getWait5().until(ExpectedConditions.visibilityOf(projectStatusTable));

        return buildHistoryTable.size();
    }

    @Step("Click build link badge on the Jenkins table")
    public BuildPage clickNameOfBuildLink() {
        getWait10().until(ExpectedConditions.elementToBeClickable(nameOfBuildLink)).click();

        return new BuildPage(getDriver());
    }

    @Step("Click New Item button on the Build history page")
    public NewJobPage clickNewItem() {
        newItem.click();

        return new NewJobPage(getDriver());
    }

    @Step("Get Page header")
    public String getHeaderText() {
        return pageHeader.getText();
    }

    @Step("Click last default build bubble on the Timeline")
    public BuildHistoryPage clickDefaultBuildBubbleFromTimeline() {
        new Actions(getDriver())
                .pause(3500)
                .perform();
        getDriver().navigate().refresh();
        getWait10().until(ExpectedConditions.elementToBeClickable(lastDefaultBuildBubbleLinkFromTimeline)).click();

        return new BuildHistoryPage(getDriver());
    }

    @Step("Close Pop Up in Timeline")
    public BuildHistoryPage closeProjectWindowButtonInTimeline() {
        getWait15().until(ExpectedConditions.visibilityOf(closePopUpButtonInTimeline)).click();
        return new BuildHistoryPage(getDriver());
    }

    @Step("Verify that default build bubble pop up has default from header text")
    public boolean isDefaultBuildPopUpHeaderTextDisplayed() {
        return getWait10().until(ExpectedConditions.visibilityOf(buildBubblePopUp)).getText().contains("default");
    }

    @Step("Verify that build bubble pop up has job name from header text")
    public boolean isBuildPopUpHeaderTextDisplayed(String jobName) {
        return getWait10().until(ExpectedConditions.visibilityOf(buildBubblePopUp)).getText().contains(jobName);
    }

    @Step("Verify that build bubble pop up is displayed from timeline")
    public boolean isBuildPopUpInvisible() {
        return getWait5().until(ExpectedConditions.invisibilityOf(buildBubblePopUp));
    }

    @Step("Click last not default build link badge on the Jenkins table")
    public BuildPage clickLastNotDefaultBuild() {
        new Actions(getDriver())
                .pause(2500)
                .perform();
        getDriver().navigate().refresh();
        getWait5().until(ExpectedConditions.elementToBeClickable(lastNotDefaultBuild)).sendKeys(Keys.RETURN);

        return new BuildPage(getDriver());
    }

    @Step("Click default build link from timeline")
    public BuildPage clickDefaultBuildLinkFromTimeline() {
        defaultBuildBubbleLink.click();

        return new BuildPage(getDriver());
    }

    @Step("Click last not default build link badge on the Jenkins table")
    public BuildHistoryPage clickLastNotDefaultBuildFromTimeline() {
        new Actions(getDriver())
                .pause(3000)
                .perform();

        getDriver().navigate().refresh();
        getWait10().until(ExpectedConditions.elementToBeClickable(lastNotDefaultBuildFromTimeline)).click();

        return new BuildHistoryPage(getDriver());
    }

    @Step("Click last not default build link badge on bubble pop up from timeline")
    public BuildPage clickLastNotDefaultBuildLinkFromBubblePopUp() {
        getWait10().until(ExpectedConditions.elementToBeClickable(notDefaultBuildLinkFromBubblePopUp)).click();

        return new BuildPage(getDriver());
    }

    @Step("Open default build drop-down menu")
    public BuildHistoryPage openDefaultProjectDropdown() {
        new Actions(getDriver())
                .moveToElement(defaultProjectLink)
                .pause(Duration.ofMillis(300))
                .perform();
        getWait2().until(ExpectedConditions.visibilityOf(defaultProjectDropdown)).sendKeys(Keys.RETURN);

        return this;
    }

    @Step("Get a page form the 'Default' project drop-down menu")
    public <ReturnedPage extends BaseMainHeaderPage<?>> ReturnedPage getPageFromDefaultProjectDropdownMenu(ReturnedPage pageToReturn) {
        getWait2().until(ExpectedConditions.elementToBeClickable(By.xpath("//li/a/span[contains(text(), '" + pageToReturn.callByMenuItemName() + "')]"))).click();

        return pageToReturn;
    }

    @Step("Open a default build drop-down menu")
    public BuildHistoryPage openDefaultBuildDropDownMenu(String projectName) {
        getDriver().
                findElement(By.xpath("(//a[contains(@href, '/job/" + projectName + "/default/')]//button)[2]"))
                .sendKeys(Keys.ENTER);

        return this;
    }

    @Step("Open a project drop-down menu")
    public BuildHistoryPage openProjectDropDownMenu(String projectName) {
        getDriver().
                findElement(By.xpath("//a[@href='/job/" + projectName + "/']/button[@class='jenkins-menu-dropdown-chevron']"))
                .sendKeys(Keys.ENTER);

        return this;
    }

    @Step("Select an option from the project menu")
    public <SubmenuPage extends BaseMainHeaderPage<?>> SubmenuPage clickOptionsFromMenu(SubmenuPage submenuPage) {
        WebElement option = getDriver().
                findElement(By.xpath("//div[@id='breadcrumb-menu-target']//span[contains(text(),'" + submenuPage.callByMenuItemName() + "')]"));

        TestUtils.scrollWithPauseByActions(this, option, 800);
        getWait2().until(ExpectedConditions.elementToBeClickable(option)).click();

        return submenuPage;
    }

    @Step("Open a project from Build drop-down menu")
    public BuildHistoryPage openProjectBuildDropDownMenu() {
        getDriver().
                findElement(By.xpath("//a[@class='jenkins-table__link jenkins-table__badge model-link inside']//button[@class='jenkins-menu-dropdown-chevron']"))
                .sendKeys(Keys.ENTER);

        return this;
    }

    @Step("Select an option from the Buildproject menu")
    public <SubmenuPage extends BaseMainHeaderPage<?>> SubmenuPage clickOptionsFromBuildMenu(SubmenuPage submenuPage) {
        WebElement option = getDriver().
                findElement(By.xpath("//span[contains(text(),'" + submenuPage.callByMenuItemName() + "')]"));

        TestUtils.scrollWithPauseByActions(this, option, 800);
        getWait2().until(ExpectedConditions.elementToBeClickable(option)).click();

        return submenuPage;
    }

    @Step("Select an Build now option from the project drop down menu")
    public BuildHistoryPage clickBuildNowFromMenu() {
        getWait2().until(ExpectedConditions.elementToBeClickable(buildNowButton)).click();

        return this;
    }

    public boolean isNewBuildDisplayed(TestUtils.JobType jobType) {
        getDriver().navigate().refresh();

        boolean newBuild;
        newBuild = getWait5().until(ExpectedConditions.visibilityOf(newBuildLink)).isDisplayed();

        if (jobType == TestUtils.JobType.MultiConfigurationProject && newBuild) {
            new Actions(getDriver())
                    .pause(1500)
                    .perform();
            getDriver().navigate().refresh();
            newBuild = getWait5().until(ExpectedConditions.visibilityOf(newDefaultBuildLink)).isDisplayed();
        }

        return newBuild;
    }
}
