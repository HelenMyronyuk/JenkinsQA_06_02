package school.redrover.model;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BaseModel;

public class LoginPage extends BaseModel {

    @FindBy(xpath = "//input[@name='j_username']")
    private WebElement inputUserNameField;

    @FindBy(xpath = "//input[@name='j_password']")
    private WebElement inputPasswordField;

    @FindBy(xpath = "//div[text()='Invalid username or password']")
    private WebElement incorrectUserNameOrPassword;

    @FindBy(xpath = "//button[@name='Submit']")
    private WebElement signInButton;

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    @Step("input user name '{userName}' in the input field on Login Page")
    public school.redrover.model.LoginPage enterUsername(String userName) {
        inputUserNameField.sendKeys(userName);

        return this;
    }

    @Step("input password '{password}' in the input field on Login Page")
    public school.redrover.model.LoginPage enterPassword(String password) {
        inputPasswordField.sendKeys(password);

        return this;
    }

    @Step("Click 'Sign in' button on Login Page")
    public <Page extends BaseModel> Page enterSignIn(Page page) {
        signInButton.click();

        return page;
    }

    @Step("Get message 'Incorrect username or password' on Login Page")
    public String getTextAlertIncorrectUsernameOrPassword() {
        return incorrectUserNameOrPassword.getText();
    }

    @Step("Check is there a 'Sign in' button on Login Page")
    public boolean isSignInButtonPresent() {
        return signInButton.isDisplayed();
    }
}
