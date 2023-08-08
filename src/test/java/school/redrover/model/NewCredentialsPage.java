package school.redrover.model;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.base.BaseMainHeaderPage;

public class NewCredentialsPage extends BaseMainHeaderPage<NewCredentialsPage> {

    @FindBy(xpath = "//input[@name='_.username']")
    private WebElement usernameInputField;

    @FindBy(xpath = "//input[@name='_.password']")
    private WebElement passwordInputField;

    @FindBy(name = "Submit")
    private WebElement createButton;

    public NewCredentialsPage(WebDriver driver) {
        super(driver);
    }

    @Step("Input '{username}' into 'Username' input field")
    public NewCredentialsPage inputUsernameIntoUsernameInputField(String username) {
        getWait2().until(ExpectedConditions.elementToBeClickable(usernameInputField)).sendKeys(username);
        return this;
    }

    @Step("Input '{password}' into 'Password' input field")
    public NewCredentialsPage inputPasswordIntoPasswordInputField(String password) {
        getWait2().until(ExpectedConditions.elementToBeClickable(passwordInputField)).sendKeys(password);
        return this;
    }

    @Step("Click 'Create' button")
    public GlobalCredentialsPage clickCreateButton() {
        createButton.click();
        return new GlobalCredentialsPage(getDriver());
    }
}
