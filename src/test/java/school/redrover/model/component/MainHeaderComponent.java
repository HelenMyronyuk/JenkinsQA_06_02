package school.redrover.model.component;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.*;
import school.redrover.model.base.BaseComponent;
import school.redrover.model.base.BaseMainHeaderPage;
import school.redrover.model.base.BasePage;
import school.redrover.model.manageJenkins.ManageJenkinsPage;
import school.redrover.model.users.UserConfigPage;
import school.redrover.model.users.UserPage;
import school.redrover.model.views.MyViewsPage;
import school.redrover.runner.TestUtils;

import java.util.ArrayList;
import java.util.List;

public class MainHeaderComponent<Page extends BasePage<?, ?>> extends BaseComponent<Page> {

    @FindBy(id = "jenkins-head-icon")
    private WebElement logoIcon;

    @FindBy(id = "jenkins-name-icon")
    private WebElement logoText;

    @FindBy(xpath = "//a[contains(@href, '/user/')]/button")
    private WebElement userDropdown;

    @FindBy(id = "visible-am-button")
    private WebElement notificationIcon;

    @FindBy(xpath = "//div[@id='visible-am-list']//a[text()='Manage Jenkins']")
    private WebElement manageJenkinsFromNotificationPopUp;

    @FindBy(xpath = "//div[@id='visible-sec-am-list']//a[text()='Manage Jenkins']")
    private WebElement manageJenkinsFromSecurityPopUp;

    @FindBy(xpath = "//span[contains(text(),'Configure')]")
    private WebElement configureTabFromUserDropdownMenu;

    @FindBy(xpath = "//div[@class='bd']//span[.='My Views']")
    private WebElement myViewsTabFromUserDropdownMenu;

    @FindBy(xpath = "//a[@class='model-link']/span[contains(@class,'hidden-xs')]")
    private WebElement currentUserName;

    @FindBy(xpath = "//a[@rel='noopener noreferrer']")
    private WebElement jenkinsVersionLink;

    @FindBy(xpath = "//input[@id='search-box']")
    private WebElement searchBox;

    @FindBy(css = ".main-search__icon-trailing svg")
    private WebElement helpIcon;

    @FindBy(css = ".main-search__icon-leading svg")
    private WebElement searchBoxIcon;

    @FindBy(xpath = "//div[@id='search-box-completion']")
    private List<WebElement> searchResultList;

    @FindBy(xpath = "//div[@id='visible-sec-am-container']")
    private WebElement securityButtonIcon;

    @FindBy(xpath = "//a[@href='/logout']")
    private WebElement logOutButton;

    @FindBy(xpath = "//div[@class='login page-header__hyperlinks']//a[contains(@href,'/user/')]")
    private WebElement userButton;

    @FindBy(xpath = "//div[@class='login page-header__hyperlinks']//a[contains(@href,'/user/')]//button[@class='jenkins-menu-dropdown-chevron']")
    private WebElement userDropDownMenu;

    @FindBy(xpath = "//a[contains(@href,'api')]")
    private WebElement restApi;

    @FindBy(tagName = "footer")
    private WebElement footer;

    public MainHeaderComponent(Page page) {
        super(page);
    }

    @Step("Click 'Logo' button and move to main page")
    public MainPage clickLogo() {
        getWait2().until(ExpectedConditions.visibilityOf(logoIcon)).click();

        return new MainPage(getDriver());
    }

    public boolean isDisplayedLogoIcon() {
        return logoIcon.isDisplayed();
    }

    public boolean isDisplayedLogoText() {
        return logoText.isDisplayed();
    }

    public MainHeaderComponent<Page> clickNotificationIcon() {
        getWait5().until(ExpectedConditions.elementToBeClickable(notificationIcon)).click();

        return this;
    }

    public MainHeaderComponent<Page> clickUserDropdownMenu() {
        TestUtils.clickByJavaScript(this, userDropdown);

        return this;
    }

    public ManageJenkinsPage clickManageLinkFromNotificationPopUp() {
        new Actions(getDriver())
                .pause(1200)
                .click(manageJenkinsFromNotificationPopUp)
                .perform();

        return new ManageJenkinsPage(getDriver());
    }

    public ManageJenkinsPage clickManageLinkFromSecurityPopUp() {
        new Actions(getDriver())
                .pause(1200)
                .click(manageJenkinsFromSecurityPopUp)
                .perform();

        return new ManageJenkinsPage(getDriver());
    }

    public UserConfigPage openConfigureTabFromUserDropdownMenu() {
        getWait5().until(ExpectedConditions.elementToBeClickable(configureTabFromUserDropdownMenu)).click();

        return new UserConfigPage(new UserPage(getDriver()));
    }

    public String getCurrentUserName() {
        return currentUserName.getAttribute("innerText");
    }

    public String getBackgroundColorNotificationIcon() {
        return notificationIcon.getCssValue("background-color");
    }

    public String getLinkVersion() {
        return jenkinsVersionLink.getText();
    }

    public LoginPage clickLogoutButton() {
        logOutButton.click();

        return new LoginPage(getDriver());
    }

    public MainHeaderComponent<Page> typeToSearch(String search) {
        getWait5().until(ExpectedConditions.visibilityOf(searchBox)).sendKeys(search);

        return this;
    }

    public BuiltInNodePage sendSearchBox() {
        searchBox.sendKeys(Keys.RETURN);

        return new BuiltInNodePage(getDriver());
    }

    public <Page extends BaseMainHeaderPage<?>> Page sendKeysSearchBox(String name, Page page) {
        searchBox.sendKeys(name);
        searchBox.sendKeys(Keys.RETURN);

        return page;
    }

    public String getAttributeFromSearchBox() {
        return searchBox.getAttribute("placeholder");
    }

    public boolean isDisplayedHelpIcon() {
        return helpIcon.isDisplayed();
    }

    public boolean isDisplayedSearchBoxIcon() {
        return searchBoxIcon.isDisplayed();
    }

    public List<String> getListOfSearchResult() {
        List<String> searchResult = new ArrayList<>();
        for (WebElement webElement : searchResultList) {
            if (!webElement.getText().equals("")) {
                searchResult.add(webElement.getText());
            }
        }

        return searchResult;
    }

    public boolean isSearchResultContainsText(String text) {
        List<String> searchResult = getListOfSearchResult();
        for (String str : searchResult) {
            if (!str.toLowerCase().contains(text.toLowerCase())) {

                return false;
            }
        }

        return true;
    }

    public UserPage clickOnAdminButton() {
        getWait2().until(ExpectedConditions.visibilityOf(userButton)).click();

        return new UserPage(getDriver());
    }

    public MainHeaderComponent<Page> clickOnUserDropDownMenu() {
        getWait2().until(ExpectedConditions.visibilityOf(userDropDownMenu)).sendKeys(Keys.RETURN);

        return this;
    }

    public <ReturnedPage extends BaseMainHeaderPage<?>> ReturnedPage getPageFromUserDropdownMenu(String listMenuName, ReturnedPage pageToReturn) {
        getWait2().until(ExpectedConditions.elementToBeClickable(By.xpath("//li/a/span[contains(text(), '" + listMenuName + "')]"))).click();

        return pageToReturn;
    }

    public RestApiPage clickOnRestApiLink() {
        restApi.click();

        return new RestApiPage(getDriver());
    }

    public MainPage clickLogoWithPause() {
        new Actions(getDriver())
                .moveToElement(logoIcon)
                .pause(3000)
                .click()
                .perform();

        return new MainPage(getDriver());
    }

    public MyViewsPage clickMyViewsTabFromUserDropdownMenu() {
        getWait5().until(ExpectedConditions.elementToBeClickable(myViewsTabFromUserDropdownMenu)).click();

        return new MyViewsPage(getDriver());
    }

    public void scrollToFooter() {
        TestUtils.scrollWithPauseByActions(this, footer, 100);
    }

    public SearchBoxPage clickHelpIcon() {
        helpIcon.click();

        return new SearchBoxPage(getDriver());
    }

    public MainHeaderComponent<Page> clickSecurityIcon() {
        securityButtonIcon.click();

        return this;
    }
}
