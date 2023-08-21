package school.redrover.model.builds;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.*;
import school.redrover.model.base.BaseMainHeaderPage;
import school.redrover.model.base.BasePage;
import school.redrover.model.base.BaseSubmenuPage;
import school.redrover.model.interfaces.IDescription;

import java.time.Duration;

public class BuildPage extends BaseMainHeaderPage<BuildPage> implements IDescription<BuildPage> {

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

    @Step("Green check mark symbol is present")
    public boolean isDisplayedGreenIconV() {
        return getWait10().until(ExpectedConditions.visibilityOf(greenIconV)).isDisplayed();
    }

    @Step("Build title is displayed")
    public boolean isDisplayedBuildPageHeaderText() {
        return buildHeader.getText().contains("Build #1");
    }

    @Step("Get the build information")
    public String getBuildInfo() {
        return buildInfo.getText().substring(0, buildInfo.getText().length() - 38);
    }

    @Step("Click Delete the build on the side bar menu")
    public <JobTypePage extends BasePage<?, ?>> DeletePage<JobTypePage> clickDeleteBuild(JobTypePage jobTypePage) {
        getWait10().until(ExpectedConditions.elementToBeClickable(deleteBuildButton)).click();

        return new DeletePage<>(jobTypePage);
    }

    @Step("Click Changes on the side bar menu")
    public ChangesBuildPage clickChangesBuildFromSideMenu(){
        changesPageButton.click();

        return new ChangesBuildPage(getDriver());
    }

    @Step("Click Console Output on the side bar menu")
    public ConsoleOutputPage clickConsoleOutput() {
        consoleOutputButton.click();

        return new ConsoleOutputPage(getDriver());
    }

    @Step("Click Pipeline Steps on the side bar menu")
    public PipelineStepsPage clickPipelineStepsFromSideMenu(){
        pipelineSteps.click();

        return new PipelineStepsPage(getDriver());
    }

    @Step("Click Edit Buld Information on the side bar menu")
    public EditBuildInformationPage clickEditBuildInformation() {
        getWait5().until(ExpectedConditions.elementToBeClickable(editBuildInformation)).click();

        return new EditBuildInformationPage(getDriver());
    }

    @Step("Get the build header")
    public String getBuildHeaderText() {
        return getWait5().until(ExpectedConditions.visibilityOf(buildHeader)).getText();
    }

    @Step("Get the build name from the build title")
    public String getBuildNameFromTitle() {
        String buildName = getWait5().until(ExpectedConditions.visibilityOf(buildHeader)).getText();

        return buildName.substring(buildName.indexOf(" ") + 1, buildName.indexOf(" ("));
    }

    @Step("Click Keep This Build Forever button")
    public BuildPage clickKeepBuildForever() {
        keepBuildForeverButton.click();

        return this;
    }

    @Step("Aggregate Downstream Test Results link is displayed")
    public boolean isDisplayedAggregatedTestResultLink() {
        return aggregatedTestResultLink.isDisplayed();
    }

    @Step("Get Aggregate Downstream Test Results text in Post-build Actions")
    public String getTestResultsNodeText() {
        return aggregatedTestResultNodeValue.getText();
    }

    @Step("Get Aggregate Downstream Test Results link text")
    public String getAggregateTestResultSideMenuLinkText() {
        return aggregatedTestResultSideMenuOption.getAttribute("href");
    }

    @Step("Get the build drop-down menu from the breadcrumb")
    public BuildPage getBuildDropdownMenu() {
        new Actions(getDriver())
                .moveToElement(buildNumber)
                .pause(Duration.ofMillis(300))
                .perform();
        getWait2().until(ExpectedConditions.visibilityOf(buildDropDownMenu)).sendKeys(Keys.RETURN);

        return this;
    }

    @Step("Select an option from the build drop-down menu")
    public <SubmenuPage extends BaseSubmenuPage<?>> SubmenuPage selectOptionFromBuildDropDownList(SubmenuPage submenuPage) {
        new Actions(getDriver())
                .moveToElement(getDriver().findElement(By.xpath("//a[contains(@href, '" + submenuPage.callByMenuItemName() + "')]")))
                .click()
                .perform();

        return submenuPage;
    }

    @Step("Select Delete option from the build drop-down menu")
    public <JobTypePage extends BasePage<?, ?>> DeletePage<JobTypePage> selectDeleteOptionFromBuildDropDownList(JobTypePage jobTypePage) {
        new Actions(getDriver())
                .moveToElement(getDriver().findElement(By.xpath("//a[contains(@href, 'confirmDelete')]")))
                .click()
                .perform();

        return new DeletePage<>(jobTypePage);
    }

    @Step("Click Replay option from the build drop-down menu")
    public <JobTypePage extends BasePage<?, ?>> ReplayPage<JobTypePage> clickReplay(JobTypePage jobTypePage){
        replayButton.click();

        return new ReplayPage<>(jobTypePage);
    }
}
