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
import school.redrover.model.builds.BuildPage;
import school.redrover.model.builds.ConsoleOutputPage;

import java.util.List;

public class BuildHistoryPage extends BaseMainHeaderPage<BuildHistoryPage> {

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

    @FindBy(xpath = "//div[@class='label-event-blue  event-blue  timeline-event-label']")
    private WebElement lastBuildLinkFromTimeline;

    @FindBy(xpath = "(//div[contains(text(), 'default')])[1]")
    private WebElement lastDefaultBuildBubbleLinkFromTimeline;

    @FindBy(xpath = "//div[@class='simileAjax-bubble-contentContainer simileAjax-bubble-contentContainer-pngTranslucent']")
    private WebElement buildBubblePopUp;


    @FindBy(xpath = "(//a[@class='jenkins-table__link jenkins-table__badge model-link inside' and not (contains(@href, 'default'))])[1]")
    private WebElement lastNotDefaultBuild;

    public BuildHistoryPage(WebDriver driver) {
        super(driver);
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
        getWait10().until(ExpectedConditions.elementToBeClickable(getDriver().findElement(
                By.xpath("//div[contains(text(), '" + projectBuildName + "')]"))));

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
    public String getHeaderText(){
        return pageHeader.getText();
    }

    @Step("Click last default build bubble on the Timeline")
    public BuildHistoryPage clickDefaultBuildBubbleFromTimeline() {
        new Actions(getDriver())
                .pause(2000)
                .perform();
        getDriver().navigate().refresh();
        getWait10().until(ExpectedConditions.elementToBeClickable(lastDefaultBuildBubbleLinkFromTimeline)).click();

        return new BuildHistoryPage(getDriver());
    }

    @Step("Verify that default build bubble pop up has default from header text")
    public boolean isDefaultBuildPopUpHeaderTextDisplayed() {
        return getWait10().until(ExpectedConditions.visibilityOf(buildBubblePopUp)).getText().contains("default");
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
}
