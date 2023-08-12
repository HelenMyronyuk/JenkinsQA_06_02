package school.redrover.model.users;

import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.DeletePage;
import school.redrover.model.MainPage;
import school.redrover.model.base.baseConfig.BaseConfigPage;
import school.redrover.runner.TestUtils;

public class UserConfigPage extends BaseConfigPage<UserConfigPage, UserPage> {

    @FindBy(xpath = "//input[@name='email.address']")
    private WebElement inputEmail;

    @FindBy(xpath = "//input[@name='insensitiveSearch']")
    private WebElement insensitiveSearchCheckbox;

    @FindBy(xpath = "//a[contains(@href, 'delete')]")
    private WebElement deleteButton;

    @FindBy(xpath = "//div[contains(text(), 'Session Termination')]")
    private WebElement sessionTerminationLabel;

    @FindBy(xpath = "//input[@class='jenkins-input validated  ']")
    private WebElement defaultViewField;
    public UserConfigPage(UserPage statusUserPage) {
        super(statusUserPage);
    }

    @Step("Get an E-mail address")
    public String getEmailValue(String attribute) {

        return inputEmail.getAttribute(attribute);
    }

    @Step("Delete the current E-mail address and type a new one")
    public UserConfigPage enterEmail(String email) {
        inputEmail.clear();
        inputEmail.sendKeys(email);

        return this;
    }

    @Step("Checking by title if User Configuration Page is opened ")
    public boolean isConfigUserPageOpened(){
        return getWait5().until(ExpectedConditions.titleContains("Configuration [Jenkins]"));
    }

    @Step("Select 'Insensitive search tool'")
    public UserConfigPage selectInsensitiveSearch(){
        WebElement checkboxInsensitiveSearch = getWait2().until(ExpectedConditions.visibilityOf(insensitiveSearchCheckbox));
        if (checkboxInsensitiveSearch.getAttribute("checked") == null){
            TestUtils.scrollToElementByJavaScript(this, checkboxInsensitiveSearch);
            TestUtils.clickByJavaScript(this, checkboxInsensitiveSearch);
        }

        return this;
    }

    @Step("Click the ‘Delete’ button on the Side menu")
    public DeletePage<MainPage> clickDeleteUser() {
        deleteButton.click();

        return new DeletePage<>(new MainPage(getDriver()));
    }

    @Step("Enter view name to Default View field")
    public UserConfigPage enterDefaultView(String viewName) {
        TestUtils.scrollWithPauseByActions(this, sessionTerminationLabel, 100);

        getWait5().until(ExpectedConditions.visibilityOf(defaultViewField)).clear();
        defaultViewField.sendKeys(viewName);

        return this;
    }
}
