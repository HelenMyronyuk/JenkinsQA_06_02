package school.redrover.model;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.base.BaseMainHeaderPage;

import java.util.List;

public class GlobalCredentialsPage extends BaseMainHeaderPage<GlobalCredentialsPage> {

    @FindBy(css = "tbody tr td:nth-child(3)")
    private List<WebElement> globalCredentialsNamesList;

    @FindBy(css = "a[href='newCredentials']")
    private WebElement addCredentialsButton;

    public GlobalCredentialsPage(WebDriver driver) {
        super(driver);
    }

    @Step("Get amount of credentials in 'Global credentials' table")
    public int getSizeOfGlobalCredentialsNamesList() {
        return globalCredentialsNamesList.size();
    }

    @Step("Verify that credential with '{name}' exists")
    public Boolean isCredentialWithSpecificNameDisplayed(String name) {
        for (WebElement credentialName: globalCredentialsNamesList) {
            if (credentialName.getText().contains(name)) {
                return true;
            }
        }
        return false;
    }

    @Step("Click 'Add credentials' button")
    public NewCredentialsPage clickAddCredentialsButton() {
        getWait5().until(ExpectedConditions.elementToBeClickable(addCredentialsButton)).click();
        return new NewCredentialsPage(getDriver());
    }
}