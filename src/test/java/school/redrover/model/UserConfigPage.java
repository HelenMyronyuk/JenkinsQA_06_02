package school.redrover.model;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.base.baseConfig.BaseConfigPage;
import school.redrover.runner.TestUtils;

public class UserConfigPage extends BaseConfigPage<UserConfigPage,StatusUserPage> {

    @FindBy(xpath = "//input[@name='email.address']")
    private WebElement inputEmail;

    @FindBy(xpath = "//input[@name='insensitiveSearch']")
    private WebElement insensitiveSearchCheckbox;

    public UserConfigPage(StatusUserPage statusUserPage) {
        super(statusUserPage);
    }

    public String getEmailValue(String attribute) {

        return inputEmail.getAttribute(attribute);
    }

    public UserConfigPage enterEmail(String email) {
        inputEmail.clear();
        inputEmail.sendKeys(email);

        return this;
    }

    public boolean isConfigUserPageOpened(){
        return getWait5().until(ExpectedConditions.titleContains("Configuration [Jenkins]"));
    }

    public UserConfigPage selectInsensitiveSearch(){
        WebElement checkboxInsensitiveSearch = getWait2().until(ExpectedConditions.visibilityOf(insensitiveSearchCheckbox));
        if (checkboxInsensitiveSearch.getAttribute("checked") == null){
            TestUtils.scrollToElementByJavaScript(this, checkboxInsensitiveSearch);
            TestUtils.clickByJavaScript(this, checkboxInsensitiveSearch);
        }

        return this;
    }
}
