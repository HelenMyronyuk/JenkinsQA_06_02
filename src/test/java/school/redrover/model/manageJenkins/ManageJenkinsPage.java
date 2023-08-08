package school.redrover.model.manageJenkins;

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
import school.redrover.model.jobsSidemenu.CredentialsPage;
import school.redrover.model.users.ManageUsersPage;
import school.redrover.runner.TestUtils;

import java.time.Duration;
import java.util.List;

public class ManageJenkinsPage extends BaseMainHeaderPage<ManageJenkinsPage> {

    @FindBy(tagName = "html")
    private WebElement page;

    @FindBy(tagName = "h1")
    private WebElement headline;

    @FindBy(linkText = "New Item")
    private WebElement newItemOnSideMenu;

    @FindBy(xpath = "//a[@href='/computer/']")
    private WebElement manageNodesOnSideMenu;

    @FindBy(xpath = "//a[text()='Manage Jenkins']")
    private WebElement manageJenkinsLink;

    @FindBy(id = "settings-search-bar")
    private WebElement searchBarOnManageJenkinsPage;

    @FindBy(xpath = "//div[@class = 'jenkins-search__results']")
    private WebElement searchResults;

    @FindBy(css = "div.jenkins-search__results-container--visible")
    private WebElement searchResultsIsVisible;

    @FindBy(css = ".jenkins-search__results-item--selected")
    private WebElement itemInDropdownSearchResults;

    @FindBy(css = ".jenkins-search__results-item--selected")
    private List<WebElement> listItemsInDropdownMenuSearchResults;

    @FindBy(xpath = "//div[@class = 'jenkins-search__results']/a")
    private List<WebElement> listItemsInDropdownMenuSearchResultsWithTagA;

    @FindBy(xpath = "//div[@class = 'jenkins-search__results']//a[contains(text(), 'Configure System')]")
    private WebElement configureSystemLinkInSearchResult;

    @FindBy(xpath = "//a[@href='configureSecurity']")
    private WebElement configureGlobalSecurityLink;

    @FindBy(xpath = "//a[@href='securityRealm/']")
    private WebElement manageUsersLink;

    @FindBy(xpath = "//a[@href='pluginManager']")
    private WebElement managePluginsLink;

    @FindBy(xpath = "//a[@href='configure']")
    private WebElement configureSystemLink;

    @FindBy(linkText = "Delete Agent")
    private WebElement deleteAgent;

    @FindBy(xpath = "//dl/dt[text()='Manage Credentials']")
    private WebElement credentialsLink;

    public ManageJenkinsPage(WebDriver driver) {
        super(driver);
    }

    @Step("Input '{text}' in the search field on ManageJenkins Page")
    public ManageJenkinsPage inputToSearchField(String text) {
        getWait2().until(ExpectedConditions.elementToBeClickable(searchBarOnManageJenkinsPage));
        searchBarOnManageJenkinsPage.sendKeys(text);

        return new ManageJenkinsPage(getDriver());
    }

    @Step("Input '{text}' in the search field using keyboard shortcut on ManageJenkins Page")
    public ManageJenkinsPage inputToSearchFieldUsingKeyboardShortcut(String text) {
        page.sendKeys(Keys.chord("/"));
        new Actions(getDriver()).sendKeys(text).perform();

        return this;
    }

    @Step("Get no result text in search field on ManageJenkins Page")
    public String getNoResultTextInSearchField() {
        Actions action = new Actions(getDriver());
        action.moveToElement(searchResultsIsVisible).perform();

        return searchResultsIsVisible.getText();
    }

    @Step("Click Manage Users on ManageJenkins Page")
    public ManageUsersPage clickManageUsers() {
        getWait2().until(ExpectedConditions.elementToBeClickable(manageUsersLink)).click();

        return new ManageUsersPage(getDriver());
    }

    @Step("Get header text from ManageJenkins Page")
    public String getActualHeader() {
        getWait5().until(ExpectedConditions.visibilityOf(headline));

        return headline.getText();
    }

    @Step("Get dropdown results in search field on ManageJenkins Page")
    public String getDropdownResultsInSearchField() {
        return getWait10().until(ExpectedConditions.elementToBeClickable(searchResults)).getText();
    }

    @Step("Click +New item on ManageJenkins Page")
    public NewJobPage clickNewItem() {
        newItemOnSideMenu.click();

        return new NewJobPage(getDriver());
    }

    @Step("Click Configure Global Security on ManageJenkins Page")
    public ConfigureGlobalSecurityPage clickConfigureGlobalSecurity() {
        getWait2().until(ExpectedConditions.elementToBeClickable(configureGlobalSecurityLink)).click();

        return new ConfigureGlobalSecurityPage(getDriver());
    }

    @Step("Click Manage Nodes on ManageJenkins Page")
    public ManageNodesPage clickManageNodes() {
        getWait2().until(ExpectedConditions.elementToBeClickable(manageNodesOnSideMenu));
        manageNodesOnSideMenu.click();

        return new ManageNodesPage(getDriver());
    }

    @Step("Click Manage Jenkins Link on ManageJenkins Page")
    public ManageJenkinsPage clickManageJenkinsLink() {
        new Actions(getDriver())
                .pause(Duration.ofMillis(300))
                .click(getWait5().until(ExpectedConditions.elementToBeClickable(manageJenkinsLink))).perform();

        return new ManageJenkinsPage(getDriver());
    }

    @Step("Click Manage Plugins on ManageJenkins Page")
    public ManagePluginsPage clickManagePlugins() {
        getWait2().until(ExpectedConditions.elementToBeClickable(managePluginsLink)).click();

        return new ManagePluginsPage(getDriver());
    }

    @Step("Get result of search '{text}' from drop down on ManageJenkins Page")
    public ConfigureSystemPage selectOnTheFirstLineInDropdown(String text) {
        getWait5().until(ExpectedConditions.visibilityOfAllElements(itemInDropdownSearchResults));
        for (WebElement option : listItemsInDropdownMenuSearchResults) {
            if (option.getText().equals(text)) {
                option.click();
                break;
            }
        }

        return new ConfigureSystemPage(getDriver());
    }

    @Step("Select all drop down results from search field on ManageJenkins Page")
    public ManageJenkinsPage selectAllDropdownResultsFromSearchField() {
        Actions action = new Actions(getDriver());
        action.moveToElement(searchResultsIsVisible).perform();

        return this;
    }

    @Step("If result of search from drop down contains '{text}' on ManageJenkins Page")
    public boolean isDropdownResultsFromSearchFieldContainsTextToSearch(String text) {
        for (WebElement option : listItemsInDropdownMenuSearchResultsWithTagA) {
            if (!option.getText().toLowerCase().contains(text)) {

                return false;
            }
        }

        return true;
    }

    @Step("If drop down results from search field links on ManageJenkins Page")
    public boolean isDropdownResultsFromSearchFieldLinks() {
        for (WebElement option : listItemsInDropdownMenuSearchResultsWithTagA) {
            if (!"a".equals(option.getTagName())) {

                return false;
            }
        }

        return true;
    }

    @Step("Click Configure System from search dropdown on ManageJenkins Page")
    public ConfigureSystemPage clickConfigureSystemFromSearchDropdown() {
        getWait5().until(ExpectedConditions.visibilityOfAllElements(searchResults));
        configureSystemLinkInSearchResult.click();

        return new ConfigureSystemPage(getDriver());
    }

    @Step("Click Configure System link on ManageJenkins Page")
    public ConfigureSystemPage clickConfigureSystemLink() {
        getWait2().until(ExpectedConditions.elementToBeClickable(configureSystemLink)).click();

        return new ConfigureSystemPage(getDriver());
    }

    @Step("Click '{nodeName}' from drop down on ManageJenkins Page")
    public ManageJenkinsPage clickNodeDropdownMenu(String nodeName) {
        getWait5().until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//a[@href='/manage/computer/" + nodeName + "/']/button")))
                .sendKeys(Keys.RETURN);

        return this;
    }

    @Step("Click Delete Agent from drop down on ManageJenkins Page")
    public DeletePage<ManageNodesPage> selectDeleteAgentInDropdown() {
        getWait5().until(ExpectedConditions.elementToBeClickable(deleteAgent)).click();

        return new DeletePage<>(new ManageNodesPage(getDriver()));
    }

    @Step("Click 'Credentials' link on 'ManageJenkins' Page")
    public CredentialsPage clickCredentialsLink() {
        TestUtils.scrollWithPauseByActions(this, manageUsersLink, 300);
        TestUtils.clickByJavaScript(this, credentialsLink);
        return new CredentialsPage(getDriver());
    }
}
