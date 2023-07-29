package school.redrover.model;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.base.BaseMainHeaderPage;
import school.redrover.model.base.BasePage;
import school.redrover.model.base.BaseSubmenuPage;

import java.time.Duration;

public class BuildPage extends BaseMainHeaderPage<BuildPage> implements IDescription<BuildPage>{

    @FindBy(xpath = "//span[@Class='build-status-icon__outer']/*[@title ='Success']")
    private WebElement greenIconV;

    @FindBy(xpath = "//h1")
    private WebElement buildHeader;

    @FindBy(xpath = "//span[contains(text(),'Started by upstream')] ")
    private WebElement buildInfo;

    @FindBy(xpath = "//span[contains(text(), 'Delete build')]/..")
    private WebElement deleteBuildButton;

    @FindBy(xpath = "//span[contains(text(), 'Console Output')]/..")
    private WebElement consoleOutputButton;

    @FindBy(xpath = "//span[text()='Edit Build Information']/..")
    private WebElement editBuildInformation;

    @FindBy(xpath = "//button[@formnovalidate]")
    private WebElement keepBuildForeverButton;

    @FindBy (css = "a[href = 'aggregatedTestReport/']")
    private WebElement aggregatedTestResultLink;

    @FindBy(css = "#main-panel table tr:last-child td:last-child")
    private WebElement aggregatedTestResultNodeValue;

    @FindBy(css = ".task:last-of-type span a")
    private WebElement aggregatedTestResultSideMenuOption;

    @FindBy(xpath = "//body/div[@id='breadcrumbBar']/ol[@id='breadcrumbs']/li[5]/a[1]/button[1]")
    private WebElement buildDropDownMenu;

    @FindBy(xpath = "//body/div[@id='breadcrumbBar']/ol[@id='breadcrumbs']/li[5]/a[1]")
    private WebElement buildNumber;

    @FindBy(xpath = "//span[contains(text(), 'Replay')]/..")
    private WebElement replayButton;

    @FindBy(xpath = "//a[contains(@href,'flowGraphTable')]")
    private WebElement pipelineSteps;

    @FindBy(xpath = "//a[contains(@href,'/lastBuild/changes')]")
    private WebElement changesPageButton;

    public BuildPage(WebDriver driver) {
        super(driver);
    }

    public boolean isDisplayedGreenIconV() {
        return getWait5().until(ExpectedConditions.visibilityOf(greenIconV)).isDisplayed();
    }

    public boolean isDisplayedBuildTitle() {
        return buildHeader.getText().contains("Build #1");
    }

    public String getBuildInfo() {
        return buildInfo.getText().substring(0, buildInfo.getText().length() - 38);
    }

    public <JobTypePage extends BasePage<?, ?>> DeletePage<JobTypePage> clickDeleteBuild(JobTypePage jobTypePage) {
        getWait5().until(ExpectedConditions.elementToBeClickable(deleteBuildButton)).click();

        return new DeletePage<>(jobTypePage);
    }

    public ChangesBuildPage clickChangesBuildFromSideMenu(){
        changesPageButton.click();

        return new ChangesBuildPage(getDriver());
    }

    public ConsoleOutputPage clickConsoleOutput() {
        consoleOutputButton.click();

        return new ConsoleOutputPage(getDriver());
    }

    public PipelineStepsPage clickPipelineStepsFromSideMenu(){
        pipelineSteps.click();

        return new PipelineStepsPage(getDriver());
    }

    public EditBuildInformationPage clickEditBuildInformation() {
        getWait5().until(ExpectedConditions.elementToBeClickable(editBuildInformation)).click();

        return new EditBuildInformationPage(getDriver());
    }

    public String getBuildHeaderText() {
        return getWait5().until(ExpectedConditions.visibilityOf(buildHeader)).getText();
    }

    public String getBuildNameFromTitle() {
        String buildName = getWait5().until(ExpectedConditions.visibilityOf(buildHeader)).getText();

        return buildName.substring(buildName.indexOf(" ") + 1, buildName.indexOf(" ("));
    }

    public BuildPage clickKeepBuildForever() {
        keepBuildForeverButton.click();

        return this;
    }

    public boolean isDisplayedAggregatedTestResultLink() {
        return aggregatedTestResultLink.isDisplayed();
    }

    public String getTestResultsNodeText() {
        return aggregatedTestResultNodeValue.getText();
    }

    public String getAggregateTestResultSideMenuLinkText() {
        return aggregatedTestResultSideMenuOption.getAttribute("href");
    }

    public BuildPage getBuildDropdownMenu() {
        new Actions(getDriver())
                .moveToElement(buildNumber)
                .pause(Duration.ofMillis(300))
                .perform();
        getWait2().until(ExpectedConditions.visibilityOf(buildDropDownMenu)).sendKeys(Keys.RETURN);

        return this;
    }

    public <SubmenuPage extends BaseSubmenuPage<?>> SubmenuPage selectOptionFromBuildDropDownList(SubmenuPage submenuPage) {
        new Actions(getDriver())
                .moveToElement(getDriver().findElement(By.xpath("//a[contains(@href, '" + submenuPage.callByMenuItemName() + "')]")))
                .click()
                .perform();

        return submenuPage;
    }

    public <JobTypePage extends BasePage<?, ?>> DeletePage<JobTypePage> selectDeleteOptionFromBuildDropDownList(JobTypePage jobTypePage) {
        new Actions(getDriver())
                .moveToElement(getDriver().findElement(By.xpath("//a[contains(@href, 'confirmDelete')]")))
                .click()
                .perform();

        return new DeletePage<>(jobTypePage);
    }

    public <JobTypePage extends BasePage<?, ?>> ReplayPage<JobTypePage> clickReplay(JobTypePage jobTypePage){
        replayButton.click();

        return new ReplayPage<>(jobTypePage);
    }
}
