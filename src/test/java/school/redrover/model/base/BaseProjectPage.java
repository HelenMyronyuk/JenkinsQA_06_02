package school.redrover.model.base;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.*;
import school.redrover.model.builds.*;
import school.redrover.model.interfaces.IAlert;
import school.redrover.model.jobsSidemenu.ChangesPage;
import school.redrover.model.jobsSidemenu.WorkspacePage;
import school.redrover.runner.TestUtils;

import java.util.List;

public abstract class BaseProjectPage<Self extends BaseProjectPage<?>> extends BaseJobPage<Self> implements IAlert<Self> {

    @FindBy(xpath = "//a[contains(@href, 'changes')]")
    private WebElement changesButton;

    @FindBy(xpath = "//span[contains(text(),'Delete')]")
    private WebElement deleteButton;

    @FindBy(xpath = "//form[@id='disable-project']/button")
    private WebElement disableButton;

    @FindBy(xpath = "//form[@id='enable-project']/*")
    private List<WebElement> disabledMessageList;

    @FindBy(xpath = "//form[@id='enable-project']/button")
    private WebElement enableButton;

    @FindBy(css = "form#enable-project")
    private WebElement disabledMessage;

    @FindBy(css = "[href*='build?']")
    private WebElement buildNowButton;

    @FindBy(xpath = "//td[@class='build-row-cell']")
    private WebElement buildRowCell;

    @FindBy(xpath = "//div[@id='main-panel']/h2")
    private WebElement permalinks;

    @FindBy(xpath = "//ul[@class='permalinks-list']//li")
    private List<WebElement> permalinksList;

    @FindBy(xpath = "//a[@href='lastBuild/']")
    private WebElement lastBuildLink;

    @FindBy(xpath = "//a[@href='lastBuild/']/button")
    private WebElement lastBuildDropDownMenu;

    @FindBy(xpath = "(//a[@update-parent-class='.build-row'])[1]")
    private WebElement lastBuildCompletedLink;

    @FindBy(xpath = "//a[text()='trend']")
    private WebElement trend;

    @FindBy(xpath = "//a[@class='model-link inside build-link display-name']//button")
    private WebElement buildsDropDownMenu;

    @FindBy(xpath = "//span[text() = 'Workspaces']")
    private WebElement workspacesFromBuildDropDownMenu;

    @FindBy(xpath = "//span[contains(text(),'Delete build ‘#1’')]")
    private WebElement deleteBuildButtonDropDownMenu;

    @FindBy(xpath = "//div[@id='breadcrumb-menu']//span[text()='Changes']/..")
    private WebElement changesButtonDropDownMenu;

    @FindBy(xpath = "//div[@id='no-builds']")
    private WebElement noBuildsMessage;

    @FindBy(xpath = "//span[text()='Workspace']/..")
    private WebElement workspaceButton;

    @FindBy(xpath = "//span[text()='Changes']/..")
    private WebElement changesFromLastBuild;

    @FindBy(xpath = "//span[text()='Status']/..")
    private WebElement statusButton;

    @FindBy(xpath = "//span[text()='Edit Build Information']/..")
    private WebElement editBuildInformFromDropDownOfBuild;

    @FindBy(xpath = "//span[text()='Console Output']/..")
    private WebElement consoleOutputType;

    @FindBy(xpath = "//a[@href='lastBuild/']/button")
    private WebElement permalinksLastBuildDropDown;

    @FindBy(css = ".pane.build-details a")
    private WebElement buildDate;

    @FindBy(xpath = "//div[@class='bd']/ul/li")
    private List<WebElement> buildDropdownMenuOptions;

    @FindBy(css = ".middle-align.build-badge img")
    private WebElement iconLock;

    @FindBy(xpath = "//a[not(contains(tooltip, 'In progress > Console Output'))]/ancestor::div/a[contains(@href,'/2/')]")
    private WebElement iconAddBranchBuild;

    @FindBy(xpath = "//a[@tooltip='Success > Console Output']")
    private WebElement buildIconStatus;

    @FindBy(xpath = "//div[@id='breadcrumb-menu-target']//span[text()='Changes']")
    private WebElement changesBuildButton;

    @FindBy(xpath = "//span[contains(text(), 'Replay')]")
    private WebElement replayButton;

    @FindBy(xpath = "//span[text()='Pipeline Steps']/..")
    private WebElement pipelineStepsDropDown;

    @FindBy(xpath = "//div[@id='breadcrumb-menu-target']//span[text()='Pipeline Steps']")
    private WebElement pipelineStepsDropDownFromSideMenu;


    public BaseProjectPage(WebDriver driver) {
        super(driver);
    }

    @Step("Click on 'Changes' from left side menu")
    public ChangesPage<Self> clickChangeOnLeftSideMenu() {
        getWait10().until(ExpectedConditions.visibilityOf(changesButton)).click();

        return new ChangesPage<>((Self) this);
    }

    @Step("Click 'Delete' and 'Accept'")
    public MainPage clickDeleteAndAccept() {
        getWait2().until(ExpectedConditions.elementToBeClickable(deleteButton)).click();
        acceptAlert();

        return new MainPage(getDriver());
    }

    @Step("Click 'Delete' and 'Cancel'")
    public Self clickDeleteAndCancel() {
        getWait2().until(ExpectedConditions.elementToBeClickable(deleteButton)).click();
        dismissAlert();

        return (Self) this;
    }

    @Step("Click 'Disable'")
    public Self clickDisable() {
        disableButton.click();

        return (Self) this;
    }

    @Step("Click 'Enable'")
    public Self clickEnable() {
        getWait5().until(ExpectedConditions.elementToBeClickable(enableButton)).click();

        return (Self) this;
    }

    @Step("Get the 'Disable' button text")
    public String getDisableButtonText() {
        return disableButton.getText();
    }

    @Step("Get the 'Enable' button text")
    public String getEnableButtonText() {
        return enableButton.getText();
    }

    @Step("'Disabled' message is not displayed")
    public boolean isDisabledMessageNotDisplayed(){
        return disabledMessageList.size() == 0;
    }

    @Step("Get the 'Disabled' message text")
    public String getDisabledMessageText() {
        return getWait10().until(ExpectedConditions.visibilityOf(disabledMessage)).getText().trim().substring(0, 34);
    }

    @Step("Click 'Build Now' from side menu")
    public Self clickBuildNowFromSideMenu() {
        getWait10().until(ExpectedConditions.elementToBeClickable(buildNowButton)).click();
        getWait10().until(ExpectedConditions.visibilityOf(buildRowCell));

        return (Self) this;
    }

    @Step("Click on a build with parameters")
    public BuildWithParametersPage<Self> clickBuildWithParameters() {
        buildNowButton.click();

        return new BuildWithParametersPage<>((Self) this);
    }

    @Step("Click 'Console Output' from the build icon drop-down menu")
    public ConsoleOutputPage clickIconBuildOpenConsoleOutput(int buildNumber) {
        getWait5().until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[contains(@href,'/" + buildNumber + "/console')]"))).click();

        return new ConsoleOutputPage(getDriver());
    }

    @Step("Get size of 'Permalinks' list")
    public int getSizeOfPermalinksList() {
        getWait2().until(ExpectedConditions.visibilityOf(permalinks));

        return permalinksList.size();
    }

    @Step("Click 'Last Build' link")
    public BuildPage clickLastBuildLink() {
        getWait15().until(ExpectedConditions.visibilityOf(lastBuildCompletedLink));
        getDriver().navigate().refresh();
        getWait15().until(ExpectedConditions.visibilityOf(lastBuildLink)).click();

        return new BuildPage(getDriver());
    }

    @Step("Click 'Additional Branch Build'")
    public ConsoleOutputPage clickIconAdditionalBranchBuild() {
        getWait15().until(ExpectedConditions.visibilityOf(iconAddBranchBuild)).click();

        return new ConsoleOutputPage(getDriver());
    }

    @Step("Click on a build status")
    public ConsoleOutputPage clickBuildIconStatus() {
        getWait5().until(ExpectedConditions.visibilityOf(buildIconStatus));
        getWait5().until(ExpectedConditions.elementToBeClickable(buildIconStatus)).click();

        return new ConsoleOutputPage(getDriver());
    }

    @Step("Click 'trend' link")
    public TimelinePage clickTrend() {
        trend.click();

        return new TimelinePage(getDriver());
    }

    @Step("Open a build drop-down menu")
    public Self openBuildsDropDownMenu() {
        getWait10().until(ExpectedConditions.visibilityOf(buildsDropDownMenu)).sendKeys(Keys.RETURN);

        return (Self) this;
    }

    @Step("Click 'Delete build' from the build drop-down menu")
    public DeletePage<Self> clickDeleteBuildFromDropDownMenu() {
        openBuildsDropDownMenu();
        deleteBuildButtonDropDownMenu.click();

        return new DeletePage<>((Self) this);
    }

    @Step("'No Builds' message is present")
    public boolean isNoBuildsDisplayed() {
        return noBuildsMessage.isDisplayed();
    }

    @Step("Click 'Changes' from the build drop-down menu")
    public ChangesPage<Self> clickChangesFromDropDownMenu() {
        openBuildsDropDownMenu();
        changesButtonDropDownMenu.click();

        return new ChangesPage<>((Self) this);
    }

    @Step("Open a last build drop-down menu")
    private Self openLastBuildDropDownMenu() {
        statusButton.click();
        lastBuildDropDownMenu.sendKeys(Keys.RETURN);

        return (Self) this;
    }

    @Step("Click 'Changes' from the last build drop-down menu")
    public ChangesPage<Self> clickChangesViaLastBuildDropDownMenu() {
        openLastBuildDropDownMenu();
        changesFromLastBuild.click();

        return new ChangesPage<>((Self) this);
    }

    @Step("Click 'Workspace' from the side menu")
    public WorkspacePage clickWorkspaceFromSideMenu() {
        workspaceButton.click();

        return new WorkspacePage(getDriver());
    }

    @Step("Click 'Edit Build Information' from the build drop-down menu")
    public EditBuildInformationPage clickEditBuildInformFromProjectPage() {
        openBuildsDropDownMenu();
        editBuildInformFromDropDownOfBuild.click();

        return new EditBuildInformationPage(getDriver());
    }

    @Step("Click 'Changes' from the build drop-down menu")
    public ChangesBuildPage clickChangesBuildFromProjectPage() {
        openBuildsDropDownMenu();
        changesBuildButton.click();

        return new ChangesBuildPage(getDriver());
    }

    @Step("Click 'Console Output' from the build drop-down")
    public ConsoleOutputPage clickConsoleOutputType() {
        consoleOutputType.click();

        return new ConsoleOutputPage(getDriver());
    }

    @Step("Click on a build icon from the side menu")
    public BuildPage clickBuildFromSideMenu(String jobName, int buildName) {
        getDriver().findElement(By.xpath("//a[@href='/job/" + jobName + "/" + buildName + "/']")).click();

        return new BuildPage(getDriver());
    }

    @Step("Get the last build number")
    public String getLastBuildNumber() {
        return getWait10().until(ExpectedConditions.elementToBeClickable(lastBuildCompletedLink)).getText();
    }

    @Step("Open the last build drop-down menu under 'Permalinks'" )
    public Self openPermalinksLastBuildsDropDownMenu() {
        getWait10().until(ExpectedConditions.visibilityOf(permalinksLastBuildDropDown)).sendKeys(Keys.RETURN);

        return (Self) this;
    }

    @Step("Click 'Edit Build Information' from the last build drop-down menu")
    public EditBuildInformationPage editBuildInfoPermalinksLastBuildDropDown() {
        openPermalinksLastBuildsDropDownMenu();
        editBuildInformFromDropDownOfBuild.click();

        return new EditBuildInformationPage(getDriver());
    }

    @Step("Click 'Delete Build' from the last build drop-down menu")
    public DeletePage<Self> deleteBuildPermalinksLastBuildDropDown() {
        openPermalinksLastBuildsDropDownMenu();
        deleteBuildButtonDropDownMenu.click();

        return new DeletePage<>((Self) this);
    }

    @Step("Click on a build date on the left pane")
    public BuildPage clickBuildDateFromBuildRow() {
        buildDate.click();

        return new BuildPage(getDriver());
    }

    @Step("Get text of all the oprions from the build drop-down menu")
    public List<String> getTextBuildDropDownMenuOptions() {
        return TestUtils.getTexts(buildDropdownMenuOptions);
    }

    @Step("The lock icon is displayed in the build drop-down menu")
    public boolean isIconLockIsDispalyed() {
        return iconLock.isDisplayed();
    }

    @Step("Click on a build date on the left pane")
    public ReplayPage<Self> clickReplayFromDropDownMenu() {
        getWait2().until(ExpectedConditions.elementToBeClickable(replayButton)).click();

        return new ReplayPage<>((Self) this);
    }

    @Step("Click 'Workspaces' option from the build drop-down menu'")
    public WorkspacesBuildPage clickWorkspaceButtonFromBuildDropDown() {
        getWait2().until(ExpectedConditions.elementToBeClickable(workspacesFromBuildDropDownMenu)).click();

        return new WorkspacesBuildPage(getDriver());
    }

    @Step("Click 'Pipeline Steps' option from the last build drop-down menu'")
    public PipelineStepsPage clickPipelineStepsViaLastBuildDropDownMenu() {
        openLastBuildDropDownMenu();
        pipelineStepsDropDown.click();

        return new PipelineStepsPage(getDriver());
    }

    @Step("Refresh the page")
    public Self refreshPage() {
        getDriver().navigate().refresh();

        return (Self) this;
    }

    @Step("Click 'Pipeline Steps' option from the build drop-down menu on the left pane")
    public PipelineStepsPage clickPipelineStepsFromBuildDropDownFromSideMenu() {
        openBuildsDropDownMenu();
        Actions actions = new Actions(getDriver());
        actions.moveToElement(pipelineStepsDropDownFromSideMenu);
        actions.click().perform();

        return new PipelineStepsPage(getDriver());
    }
}
