package school.redrover.model.users;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.DeletePage;
import school.redrover.model.MainPage;
import school.redrover.model.base.BaseMainHeaderPage;
import school.redrover.model.base.BaseSubmenuPage;


import java.util.List;

public class ManageUsersPage extends BaseSubmenuPage<ManageUsersPage> {

    @FindBy(xpath = "//a[@href='addUser']")
    private WebElement createUser;

    @FindBy(xpath = "//span[contains(text(), 'Configure')]")
    private WebElement configureInDropDownMenu;

    @FindBy(xpath = "//span[contains(text(), 'Delete')]")
    private WebElement deleteInDropDownMenu;

    @FindBy(xpath = "//a[@class ='jenkins-table__link model-link inside']")
    private List<WebElement> users;

    @FindBy(id = "people")
    private List<WebElement> people;

    @FindBy(xpath = "//a[contains(@href,'/delete')]")
    private WebElement deleteButton;

    @FindBy(xpath = "//td//a[contains(@href, 'configure')]")
    private WebElement configureAdminButton;

    public ManageUsersPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public String callByMenuItemName() {
        return "Users";
    }

    @Step("Click the 'Create User' button")
    public CreateUserPage clickCreateUser() {
        createUser.click();

        return new CreateUserPage(getDriver());
    }

    @Step("Click '{userName}' name")
    public UserPage clickUserIDName(String userName) {
        WebElement userIDNameLink = getWait2()
                .until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//a[@href='user/" + userName + "/']")));
        userIDNameLink.click();

        return new UserPage(getDriver());
    }

    @Step("Open Dropdown menu for '{userName}' user")
    public ManageUsersPage openUserIDDropDownMenu(String userName) {
        getDriver()
                .findElement(By.xpath("//a[@href='user/" + userName + "/']/button[@class='jenkins-menu-dropdown-chevron']"))
                .sendKeys(Keys.ENTER);

        return this;
    }

    @Step("Select '{optionName}' menu item in User ID Dropdown menu")
    public <RedirectPage> RedirectPage selectItemInUserIDDropDownMenu(String optionName, RedirectPage redirectPage) {
        getWait5().until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(text(), '" + optionName +"')]"))).click();

        return redirectPage;
    }

    @Step("Verify if '{userName}' user is exist")
    public boolean isUserExist(String userName) {
        for (WebElement el : users) {
            if (el.getText().equals(userName)) {

                return true;
            }
        }

        return false;
    }

    @Step("Click 'Delete' button")
    public DeletePage<ManageUsersPage> clickDeleteUser() {
        deleteButton.click();

        return new DeletePage<>(this);
    }

    @Step("Click Gear 'Configure' button for Admin user")
    public UserConfigPage clickFirstUserEditButton() {
        configureAdminButton.click();

        return new UserConfigPage(new UserPage(getDriver()));
    }

    @Step("Click Gear 'Configure' button for '{userName}' User and redirect to UserConfigPage")
    public UserConfigPage clickConfigureButton(String userName) {
        getWait5().until(ExpectedConditions.elementToBeClickable(
                By.xpath("//td//a[contains(@href, '" + userName + "/configure')]"))).click();

        return new UserConfigPage(new UserPage(getDriver()));
    }

    @Step("Select Gear 'Configure' button for '{userName}' User and redirect to UserPage")
    public UserPage selectConfigureButton(String newUserName) {
        getWait5().until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[@href='user/" + newUserName + "/configure']"))).click();

        return new UserPage(getDriver());
    }
}

