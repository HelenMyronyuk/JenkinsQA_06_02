package school.redrover.model.users;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.base.BaseMainHeaderPage;

public class CreateUserPage extends BaseMainHeaderPage<CreateUserPage> {

    @FindBy(id = "username")
    private WebElement userNameInputField;

    @FindBy(name = "password1")
    private WebElement passwordInputField;

    @FindBy(name ="password2")
    private WebElement confirmPasswordInputField;

    @FindBy(name = "fullname")
    private WebElement fullNameInputField;

    @FindBy(name = "email")
    private WebElement emailInputField;

    @FindBy(name = "Submit")
    private WebElement createUserButton;

    @FindBy(xpath = "//div[contains(@class, 'error') and contains(text(),'User name')]")
    private WebElement userExistsError;

    @FindBy(xpath = "//ol[@id='breadcrumbs']//li[@aria-current]")
    private WebElement actualIconName;

    @FindBy(xpath = "//div[contains(@class, 'error') and contains(text(),'e-mail')]")
    private WebElement invalidEmailError;

    public CreateUserPage(WebDriver driver) {
        super(driver);
    }

    @Step("Input '{name}' into 'Username' field")
    public CreateUserPage enterUsername(String name) {
        userNameInputField.sendKeys(name);

        return this;
    }

    @Step("Input '{name}' into 'Password' field")
    public CreateUserPage enterPassword(String name) {
        passwordInputField.sendKeys(name);

        return this;
    }

    @Step("Input '{name}' into 'ConfirmPassword' field")
    public CreateUserPage enterConfirmPassword(String name) {
        confirmPasswordInputField.sendKeys(name);

        return this;
    }

    @Step("Input '{name}' into 'Full Name' field")
    public CreateUserPage enterFullName(String name) {
        fullNameInputField.sendKeys(name);

        return this;
    }

    @Step("Input '{name}' into 'Email' field")
    public CreateUserPage enterEmail(String name) {
        emailInputField.sendKeys(name);

        return this;
    }

    @Step("Click on CreateUser button")
    public ManageUsersPage clickCreateUserButton() {
        createUserButton.click();

        return new ManageUsersPage(getDriver());
    }

    @Step("Create User with following data '{username, password, fullName, email}'")
    public CreateUserPage fillUserDetails(String username, String password, String fullName, String email) {
        return this.enterUsername(username)
                .enterPassword(password)
                .enterConfirmPassword(password)
                .enterFullName(fullName)
                .enterEmail(email);
    }

   @Step("Error in creating User with same name")
    public String getUserNameExistsError() {
        clickCreateUserButton();

        return getWait2().until(ExpectedConditions.visibilityOf(userExistsError)).getText();
    }

    @Step("Get logged in user's name")
    public String getActualIconName() {
        return actualIconName.getText().trim();
    }

   @Step("Get error message about incorrect email")
    public String getInvalidEmailError() {
        clickCreateUserButton();

        return getWait2().until(ExpectedConditions.visibilityOf(invalidEmailError)).getText();
    }
}

